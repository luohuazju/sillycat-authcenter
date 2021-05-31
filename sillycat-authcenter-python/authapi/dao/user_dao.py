from authapi.dao.postgre_conn import PostgreConn
from authapi.model.user_model import User
from authapi.util.log import logger
from authapi.util.md5_util import generate_md5


class UserDAO:

    def __init__(self):
        self.conn = PostgreConn().get_conn()
        cur = self.conn.cursor()
        cur.execute("""
            CREATE TABLE IF NOT EXISTS
                authapi_users(
            id SERIAL PRIMARY KEY, 
            name VARCHAR(255), 
            email VARCHAR(255), 
            password VARCHAR(255)) 
        """)
        self.conn.commit()

    def create_user(self, user):
        query = """
            INSERT INTO 
                authapi_users 
            (name, email, password) VALUES (%s, %s, %s)
        """
        cur = self.conn.cursor()
        cur.execute(query, (user.name, user.email, generate_md5(user.password)))
        self.conn.commit()

    def get_user(self, email):
        query = """
            SELECT 
                id, name, email, password
            FROM
                authapi_users
            WHERE
                email = %s
        """
        cur = self.conn.cursor()
        cur.execute(query, (email,))
        row = cur.fetchone()
        if row:
            logger.info(row)
            return User(id=row[0], name=row[1], email=row[2], password=row[3])
        return None

    def update_user(self, user):
        query = """
            UPDATE 
                authapi_users
            SET
                name = %s,
                password = %s
            WHERE
                email = %s
        """
        cur = self.conn.cursor()
        cur.execute(query, (user.name, generate_md5(user.password), user.email))
        self.conn.commit()

    def delete_user(self, email):
        query = """
            DELETE FROM
                authapi_users
            WHERE
                email = %s
        """
        cur = self.conn.cursor()
        cur.execute(query, (email,))
        self.conn.commit()
