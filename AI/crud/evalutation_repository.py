from typing import Optional, Tuple

from cachetools import cached
from utils.CustomTTLCache import CustomTTLCache

from database.db import db


@cached(cache=CustomTTLCache(maxsize=1024, ttl=3600))
def select_member_name_by_uid(uid: int) -> Optional[str]:
    with db.query("SELECT name FROM member WHERE uid = %s", (uid,)) as cursor:
        data = cursor.fetchone()
        if data:
            return data[0]
    return None


@cached(cache=CustomTTLCache(maxsize=1024, ttl=3600))
def select_room_name_founder_uid_by_rid(rid: int) -> Optional[Tuple[str, int]]:
    with db.query("SELECT room_name, uid FROM room WHERE rid = %s", (rid,)) as cursor:
        data = cursor.fetchone()
        if data:
            return data
    return None


@cached(cache=CustomTTLCache(maxsize=1024, ttl=600))
def select_entrant_eid_by_uid_and_rid(uid: int, rid: int) -> Optional[int]:
    with db.query("SELECT eid FROM entrant WHERE uid = %s AND rid = %s", (uid, rid,)) as cursor:
        data = cursor.fetchone()
        if data:
            return data[0]
    return None


@cached(cache=CustomTTLCache(maxsize=1024, ttl=600))
def select_evaluation_vid_recent_by_eid(eid: int):
    with db.query("SELECT vid FROM evaluation WHERE eid = %s ORDER BY eval_date DESC LIMIT 1", (eid,)) as cursor:
        data = cursor.fetchone()
        if data:
            return data[0]
    return None


def update_evaluation_increase_column_by_eid(column: str, eid: int) -> None:
    db.query(f"UPDATE evaluation SET {column} = {column} + 1 WHERE eid = %s", (eid,))


def update_evaluation_increase_attention_by_eid(eid: int) -> None:
    update_evaluation_increase_column_by_eid("attention", eid)


def update_evaluation_increase_distracted_by_eid(eid: int) -> None:
    update_evaluation_increase_column_by_eid("distracted", eid)


def update_evaluation_increase_asleep_by_eid(eid: int) -> None:
    update_evaluation_increase_column_by_eid("asleep", eid)


def update_evaluation_increase_afk_by_eid(eid: int) -> None:
    update_evaluation_increase_column_by_eid("afk", eid)


if __name__ == "__main__":
    rid = 2
    uid = 2
    # 2번 방에 2번 멤버가 소속해 있는 번호를 확인 (소속이 아닌 경우 None 반환)
    id = select_entrant_eid_by_uid_and_rid(rid, uid)
    id = select_entrant_eid_by_uid_and_rid(rid, uid)
    id = select_entrant_eid_by_uid_and_rid(rid, uid)
    print(f"{rid}번 방의 {uid}번 멤버의 참가 번호 : {id}")
    if id is None:  # 소속이 안되어 있으므로 하단 과정은 패스
        pass
    else:
        # 해당 참가자가 가지고 있는 최근 평가 번호를 출력
        id = select_evaluation_vid_recent_by_eid(id)
        print(f"해당 참가자의 최근 평가 번호 : {id}")
        # 해당 참가자의 attention 포인트를 1 증가
        update_evaluation_increase_attention_by_eid(id)
        print(f"평가 {id}번호의 attention 포인트를 1 증가")
