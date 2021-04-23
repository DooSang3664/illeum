-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- member Table Create SQL
CREATE TABLE member
(
    `uid`        INT             NOT NULL    AUTO_INCREMENT COMMENT '멤버 번호', 
    `email`      VARCHAR(200)    NOT NULL    COMMENT '이메일', 
    `password`   VARCHAR(255)    NOT NULL    COMMENT '비밀번호', 
    `name`       VARCHAR(20)     NOT NULL    COMMENT '이름', 
    `role`       VARCHAR(100)    NOT NULL    DEFAULT 'ROLE_USER' COMMENT '권한', 
    `thumbnail`  VARCHAR(200)    NOT NULL    DEFAULT '' COMMENT '프로필 이미지', 
    CONSTRAINT  PRIMARY KEY (uid)
);

ALTER TABLE member COMMENT '멤버';


-- member Table Create SQL
CREATE TABLE room
(
    `rid`            INT             NOT NULL    AUTO_INCREMENT COMMENT '방 번호', 
    `uid`            INT             NOT NULL    COMMENT '개설자 uid', 
    `room_name`      VARCHAR(45)     NOT NULL    COMMENT '방이름', 
    `room_password`  VARCHAR(100)    NOT NULL    COMMENT '방 비밀번호', 
    `start_time`     TIMESTAMP       NOT NULL    COMMENT '시작시간', 
    `end_time`       TIMESTAMP       NOT NULL    COMMENT '종료시간', 
    `room_state`     VARCHAR(10)     NOT NULL    DEFAULT '준비' COMMENT '방의 상태', 
    `room_type`      VARCHAR(10)     NOT NULL    DEFAULT '공개' COMMENT '방의 타입', 
    CONSTRAINT  PRIMARY KEY (rid)
);

ALTER TABLE room COMMENT '방';

ALTER TABLE room
    ADD CONSTRAINT FK_room_uid_member_uid FOREIGN KEY (uid)
        REFERENCES member (uid) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- member Table Create SQL
CREATE TABLE entrant
(
    `eid`  INT    NOT NULL    AUTO_INCREMENT COMMENT '참여자 번호', 
    `uid`  INT    NOT NULL    COMMENT '멤버 번호', 
    `rid`  INT    NOT NULL    COMMENT '방 번호', 
    CONSTRAINT  PRIMARY KEY (eid)
);

ALTER TABLE entrant COMMENT '방 참여자 리스트';

ALTER TABLE entrant
    ADD CONSTRAINT FK_entrant_rid_room_rid FOREIGN KEY (rid)
        REFERENCES room (rid) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE entrant
    ADD CONSTRAINT FK_entrant_uid_member_uid FOREIGN KEY (uid)
        REFERENCES member (uid) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- member Table Create SQL
CREATE TABLE evaluation
(
    `vid`            INT          NOT NULL    AUTO_INCREMENT COMMENT '평가 번호', 
    `eid`            INT          NOT NULL    COMMENT '참여자 번호', 
    `eval_date`      DATETIME     NOT NULL    DEFAULT NOW() COMMENT '평가 당일', 
    `attention`      INT          NOT NULL    DEFAULT 0 COMMENT '집중', 
    `participation`  INT          NOT NULL    DEFAULT 0 COMMENT '채팅참여도', 
    `distracted`     INT          NOT NULL    DEFAULT 0 COMMENT '딴짓', 
    `asleep`         INT          NOT NULL    DEFAULT 0 COMMENT '졸음', 
    `afk`            INT          NOT NULL    DEFAULT 0 COMMENT '자리비움', 
    `start_time`     TIMESTAMP    NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '시작시간', 
    `end_time`       TIMESTAMP    NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '종료시간', 
    CONSTRAINT  PRIMARY KEY (vid)
);

ALTER TABLE evaluation COMMENT '평가';

ALTER TABLE evaluation
    ADD CONSTRAINT FK_evaluation_eid_entrant_eid FOREIGN KEY (eid)
        REFERENCES entrant (eid) ON DELETE RESTRICT ON UPDATE RESTRICT;


