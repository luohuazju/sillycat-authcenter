import psycopg2
from authapi.util.log import logger


class _PostgreConn:
    def __init__(self):
        self.conn = None
        self.postgre_connected = False

    def get_conn(self):
        try:
            self.conn = psycopg2.connect(host="postgres", port="5432", database='postgres', user='postgres',
                                   password='123456')
            self.postgre_connected = True
            return self.conn
        except psycopg2.DatabaseError as e:
            logger.error(f'Error {e}')

    def disconnect(self):
        if self.conn:
            self.conn.close()
        self.postgre_connected = False


class PostgreConn(object):
    instance = None

    def __new__(cls):
        if not PostgreConn.instance:
            PostgreConn.instance = _PostgreConn()
        return PostgreConn.instance

    def __getattr__(self, name):
        return getattr(self.instance, name)

    def __setattr__(self, name, value):
        return setattr(self.instance, name, value)
