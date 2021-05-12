import json


class QueueMessage:
    result: str
    message: str
    data: str

    def __init__(self, result, message, data):
        self.result = result
        self.message = message
        self.data = data

    def __str__(self) -> str:
        return self.__dict__.__str__()

    def __repr__(self) -> str:
        return self.__str__()

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, sort_keys=True,  ensure_ascii=False)