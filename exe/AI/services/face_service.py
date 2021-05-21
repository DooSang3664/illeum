# 모듈을 gitpython 등을 사용해서 다운로드 후, import 연결.
from typing import Optional, List

import cv2
import numpy as np

import crud.evalutation_repository as repository

import models.FaceResultType as FRT
from models.FaceDetectionResponse import FaceDetectionResponse as Response
from models.TTLStatusCounter import ttl_status_counter
from models.QueueMessage import QueueMessage
import utils.rabbitmq_util as rabbitmq
from OpenVtuber import SimpleFaceModule, SimpleFaceDetectionResult

# 서비스 코드 시작 ##
module = SimpleFaceModule()


# AFK : 인식 실패의 경우
# ASLEEP : blink 값이 0.1 보다 작아지면
# DISTRACTED : 얼굴과 눈 각도가 크게 뒤틀릴 때
# ATTENTION : 위의 상황에 포함되지 않는다면

def face_detection(image: np.ndarray) -> Response:
    global module
    result: SimpleFaceDetectionResult = module.face_detection(image)
    if result is None:
        return Response(FRT.RESULT_TYPE_AFK, '자리를 비웠거나, 얼굴이 인식되지 않는 상황입니다.', None)
    elif result.blink[0] < 0.1 and result.blink[1] < 0.1:
        return Response(FRT.RESULT_TYPE_ASLEEP, '눈을 감고 있거나, 자고 있는 상태 입니다.', result)
    elif result.mouth > 0.5 \
            or 25.0 < result.euler[0] or -25.0 > result.euler[0] \
            or 30.0 < result.euler[1] or -30.0 > result.euler[1] \
            or 0.2 < result.eye[0] or -0.2 > result.eye[0]:  # (or 0.03 < result.eye[1] or -0.03 > result.eye[1])
        return Response(FRT.RESULT_TYPE_DISTRACTED, '딴 짓을 하고 있는 상태 입니다. (하품, 시선, 고개)', result)
    else:
        return Response(FRT.RESULT_TYPE_ATTENTION, '집중 중', result)


def send_message_by_rid(rid: int, message: QueueMessage):
    rabbitmq.send_message_fanout(f'room.{rid}', message)


def send_message_by_uids(uids: List[int], message: QueueMessage):
    for uid in uids:
        rabbitmq.send_message_direct(f'member.{uid}', message)


def update_evaluation_increase_afk_by_eid(uid: int, rid: int, eid: int):
    repository.update_evaluation_increase_afk_by_eid(eid)
    scnt, total = ttl_status_counter.increase(uid, FRT.RESULT_TYPE_AFK)
    if total > 60 and scnt / total > 0.5:
        ttl_status_counter.decrease(uid, FRT.RESULT_TYPE_AFK, 10)
        room_name, founder_uid = repository.select_room_name_founder_uid_by_rid(rid)
        name: str = repository.select_member_name_by_uid(uid)
        send_message_by_uids([uid, founder_uid], QueueMessage(result="user_stats_alert",
                                                                message=f"{name} 학생이 자리를 비웠습니다.",
                                                                data={"status": FRT.RESULT_TYPE_AFK,
                                                                      "uid": uid,
                                                                      "publisher_id"
                                                                      "rid": rid,
                                                                      "eid": eid,
                                                                      "name": name,
                                                                      "room_name": room_name,
                                                                      "founder_uid": founder_uid
                                                                      }
                                                                )
                             )


def update_evaluation_increase_asleep_by_eid(uid: int, rid: int, eid: int):
    repository.update_evaluation_increase_asleep_by_eid(eid)
    scnt, total = ttl_status_counter.increase(uid, FRT.RESULT_TYPE_ASLEEP)
    if total > 60 and scnt / total > 0.5:
        ttl_status_counter.decrease(uid, FRT.RESULT_TYPE_ASLEEP, 5)
        room_name, founder_uid = repository.select_room_name_founder_uid_by_rid(rid)
        name: str = repository.select_member_name_by_uid(uid)
        send_message_by_uids([uid, founder_uid], QueueMessage(result="user_stats_alert",
                                                                message=f"{name} 학생이 졸고 있습니다.",
                                                                data={"status": FRT.RESULT_TYPE_ASLEEP,
                                                                      "uid": uid,
                                                                      "rid": rid,
                                                                      "eid": eid,
                                                                      "name": name,
                                                                      "room_name": room_name,
                                                                      "founder_uid": founder_uid
                                                                      }
                                                                )
                             )


def update_evaluation_increase_attention_by_eid(uid: int, rid: int, eid: int):
    repository.update_evaluation_increase_attention_by_eid(eid)
    ttl_status_counter.increase(uid, FRT.RESULT_TYPE_ATTENTION)


def update_evaluation_increase_distracted_by_eid(uid: int, rid: int, eid: int):
    repository.update_evaluation_increase_distracted_by_eid(eid)
    scnt, total = ttl_status_counter.increase(uid, FRT.RESULT_TYPE_DISTRACTED)
    if total > 60 and scnt / total > 0.5:
        ttl_status_counter.decrease(uid, FRT.RESULT_TYPE_DISTRACTED, 8)
        room_name, founder_uid = repository.select_room_name_founder_uid_by_rid(rid)
        name: str = repository.select_member_name_by_uid(uid)
        send_message_by_uids([uid, founder_uid], QueueMessage(result="user_stats_alert",
                                                                message=f"{name} 학생이 딴짓을 하고 있습니다.",
                                                                data={"status": FRT.RESULT_TYPE_DISTRACTED,
                                                                      "uid": uid,
                                                                      "rid": rid,
                                                                      "eid": eid,
                                                                      "name": name,
                                                                      "room_name": room_name,
                                                                      "founder_uid": founder_uid
                                                                      }
                                                                )
                             )


# 각 결과에 따라 repository 함수 매핑
repo_funcs = {
    FRT.RESULT_TYPE_AFK: update_evaluation_increase_afk_by_eid,
    FRT.RESULT_TYPE_ASLEEP: update_evaluation_increase_asleep_by_eid,
    FRT.RESULT_TYPE_ATTENTION: update_evaluation_increase_attention_by_eid,
    FRT.RESULT_TYPE_DISTRACTED: update_evaluation_increase_distracted_by_eid
}


def face_detection_task(uid: int, rid: int, content: bytes) -> Response:
    eid: Optional[int] = repository.select_entrant_eid_by_uid_and_rid(uid, rid)
    if eid is None:
        return Response(result=FRT.RESULT_TYPE_UNAUTHORIZED, message="해당 방에 가입하지 않은 사용자 입니다.")
    vid: Optional[int] = repository.select_evaluation_vid_recent_by_eid(eid)
    if vid is None:
        return Response(result=FRT.RESULT_TYPE_NOT_DEFINED_EVAL, message="아직 평가가 생성되지 않은 사용자 입니다.")
    nparr = np.frombuffer(content, np.int8)
    # print(type(nparr))
    img_np = cv2.imdecode(nparr, cv2.IMREAD_COLOR)

    resp: Response = face_detection(img_np)
    repo_funcs[resp.result](uid, rid, eid)  # 각 결과에 따라 카운트 하는 쿼리문 실행
    return resp


# 서비스 코드 종료 ##

# 테스트용 모듈 코드
if __name__ == "__main__":
    cap = cv2.VideoCapture(2)
    while True:
        _ret, _frame = cap.read()

        if not _ret:
            break
        data: Optional[SimpleFaceDetectionResult] = module.face_detection(_frame)
        print(data)

    cap.release()
    cv2.destroyAllWindows()
