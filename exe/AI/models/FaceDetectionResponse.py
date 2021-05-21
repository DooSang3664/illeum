from typing import Optional

from OpenVtuber import SimpleFaceDetectionResult

class FaceDetectionResponse:
    result: str
    message: str
    data: SimpleFaceDetectionResult

    def __init__(self,
                 result: str,
                 message: str,
                 data: Optional[SimpleFaceDetectionResult] = None
                 ):
        self.result = result
        self.message = message
        self.data = data

    def __str__(self) -> str:
        return self.__dict__.__str__()

    def __repr__(self) -> str:
        return self.__str__()
