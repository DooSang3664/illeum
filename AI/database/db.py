from ctypes import Union

from mysql.connector import connect, Error

# 데이터 베이스 정보 및 사용자 정보
user_name = "illeum"
user_pwd = "illeum123@" # NOSONAR
db_host = "k4d106.p.ssafy.io"
db_name = "illeum"


# DB 연결
class DB:

    def connect(self):
        self.conn = connect(
            host=db_host,
            user=user_name,
            password=user_pwd,
            database=db_name,
            autocommit=True
        )
        self.conn.ping(reconnect=True, attempts=100, delay=3)
        self.conn.time_zone = "+09:00"

    def query(self, sql, params=None):
        try:
            cursor = self.conn.cursor()
            cursor.execute(sql, params)
        except Error:
            self.connect()
            cursor = self.conn.cursor()
            cursor.execute(sql, params)
        return cursor

    def __init__(self):
        self.connect()


db = DB()

if __name__ == "__main__":
    print("these lines are 'db.py' test codes...")
    with db.query("SELECT * FROM member WHERE uid = 1") as cursor:
        print(cursor.fetchone())
    with db.query("SELECT * FROM member WHERE uid = %s", (1,)) as cursor:
        print(cursor.fetchone())