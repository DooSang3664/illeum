from fastapi import APIRouter, File, UploadFile, HTTPException, Form
from pydantic import BaseModel

import os

import base64
import cv2
import numpy as np
import services.face_service as service

router = APIRouter()


# 해당 함수는 swagger 상에서 사진을 넣었을 때에 결과가 잘 나오는지 체크하기 위한 모듈, 실제는 base64로 인코딩된 이미지를 넣을 예정
@router.post("/test_eval", summary="테스트용 - 현재 태도에 따른 태도 분석")
async def evaluate_face_background_task_test(uid: int = Form(...), rid: int = Form(...),
                                             snapshot: UploadFile = File(...)):
    # 이미지를 base64로 컨버팅
    content: bytes = await snapshot.read()
    return service.face_detection_task(uid, rid, content)


class Item(BaseModel):
    uid: int
    rid: int
    snapshot: str


# 실제 사용
@router.post("/eval", summary="현재 태도에 따른 태도 분석")
async def evaluate_face_background_task(item: Item):
    base64_string = item.snapshot[1 + item.snapshot.find(','):]
    content: bytes = base64.b64decode(base64_string)
    return service.face_detection_task(item.uid, item.rid, content)


if __name__ == "__main__":
    img = cv2.imread(r"D:\취직자료백업\프로필사진.jpg", cv2.IMREAD_COLOR)
    _, img_np = cv2.imencode('.jpg', img)

    result = service.face_detection_task(2, 2, img_np)
    print("Test result.")
    print(result)
