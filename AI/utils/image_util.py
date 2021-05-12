import base64
import cv2
import numpy as np


def data_uri_to_cv2_img(encoded_data: str) -> np.ndarray:
    nparr = np.frombuffer(base64.b64decode(encoded_data), np.uint8)
    img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    return img


if __name__ == "__main__":
    img = cv2.imread('../assets/blue_box.png', cv2.IMREAD_COLOR)
    cv2.imwrite('../assets/blue_box_2.png', img)  # 비교 대상
    jpg_img = cv2.imencode('.jpg', img)
    b64_string = base64.b64encode(jpg_img[1]).decode('utf-8')
    # print(b64_string)
    img3 = data_uri_to_cv2_img(b64_string)
    # print(image2)
    cv2.imwrite('../assets/blue_box_3.png', img3)  # 타겟 대상
    cv2.waitKey(0)
    cv2.destroyAllWindows()
