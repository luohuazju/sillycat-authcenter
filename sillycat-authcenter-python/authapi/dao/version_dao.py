#!/usr/bin/env python
# -*- coding: utf-8 -*-

import psycopg2
import sys

con = None

try:
    con = psycopg2.connect(host="localhost", port="5432", database='postgres', user='postgres',
        password='123456')
    cur = con.cursor()
    cur.execute('SELECT version()')
    version = cur.fetchone()[0]
    print(version)

except psycopg2.DatabaseError as e:
    print(f'Error {e}')
    sys.exit(1)
finally:
    if con:
        con.close()
