import psycopg2
from authapi.dao.postgre_conn import PostgreConn


class UserDAO:

    def __init__(self):
        self.conn = PostgreConn().get_conn()
        self.cur = self.conn.cursor()
        self.cur.execute("DROP TABLE IF EXISTS authapi_users")
        self.cur.execute("CREATE TABLE authapi_users(id SERIAL PRIMARY KEY, name VARCHAR(255), email VARCHAR(255), password VARCHAR(255))")

    def create_user(self, user):
        pass

    def get_user(self, email):
        pass

    def update_user(self, user):
        pass

    def delete_user(self, email):
        pass
