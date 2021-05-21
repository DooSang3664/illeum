-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: k4d106.p.ssafy.io    Database: illeum
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `regdate` datetime(6) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `updatedate` datetime(6) DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q0uja26qgu1atulenwup9rxyr` (`email`),
  UNIQUE KEY `UK_gex1lmaqpg0ir5g1f5eftyaa1` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrant`
--

DROP TABLE IF EXISTS `entrant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrant` (
  `eid` int NOT NULL AUTO_INCREMENT COMMENT '참여자 번호',
  `uid` int NOT NULL COMMENT '멤버 번호',
  `rid` int NOT NULL COMMENT '방 번호',
  PRIMARY KEY (`eid`),
  UNIQUE KEY `uniq_by_rid_uid` (`rid`,`uid`),
  KEY `FK_entrant_uid_member_uid` (`uid`),
  CONSTRAINT `FK_entrant_rid_room_rid` FOREIGN KEY (`rid`) REFERENCES `room` (`rid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_entrant_uid_member_uid` FOREIGN KEY (`uid`) REFERENCES `member` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3 COMMENT='방 참여자 리스트';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluation` (
  `vid` int NOT NULL AUTO_INCREMENT COMMENT '평가 번호',
  `eid` int NOT NULL COMMENT '참여자 번호',
  `eval_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '평가 당일',
  `attention` int NOT NULL DEFAULT '0' COMMENT '집중',
  `participation` int NOT NULL DEFAULT '0' COMMENT '채팅참여도',
  `distracted` int NOT NULL DEFAULT '0' COMMENT '딴짓',
  `asleep` int NOT NULL DEFAULT '0' COMMENT '졸음',
  `afk` int NOT NULL DEFAULT '0' COMMENT '자리비움',
  `attend_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '시작시간',
  `ranking` int NOT NULL DEFAULT '1000' COMMENT '출석순위',
  `attend` varchar(20) NOT NULL COMMENT '출석',
  PRIMARY KEY (`vid`),
  KEY `FK_evaluation_eid_entrant_eid` (`eid`),
  CONSTRAINT `FK_evaluation_eid_entrant_eid` FOREIGN KEY (`eid`) REFERENCES `entrant` (`eid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb3 COMMENT='평가';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation`
--

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `uid` int NOT NULL AUTO_INCREMENT COMMENT '멤버 번호',
  `email` varchar(200) NOT NULL COMMENT '이메일',
  `password` varchar(255) NOT NULL COMMENT '비밀번호',
  `name` varchar(20) NOT NULL COMMENT '이름',
  `role` varchar(100) NOT NULL DEFAULT 'ROLE_USER' COMMENT '권한',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COMMENT='멤버';
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `rid` int NOT NULL AUTO_INCREMENT COMMENT '방 번호',
  `uid` int NOT NULL COMMENT '개설자 uid',
  `room_name` varchar(45) NOT NULL COMMENT '방이름',
  `room_password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '방 비밀번호',
  `start_time` timestamp NOT NULL COMMENT '시작시간',
  `end_time` timestamp NOT NULL COMMENT '종료시간',
  `room_state` varchar(10) NOT NULL DEFAULT '준비' COMMENT '방의 상태',
  `room_type` varchar(10) NOT NULL DEFAULT '공개' COMMENT '방의 타입',
  PRIMARY KEY (`rid`),
  KEY `FK_room_uid_member_uid` (`uid`),
  CONSTRAINT `FK_room_uid_member_uid` FOREIGN KEY (`uid`) REFERENCES `member` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 COMMENT='방';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `uploadfile`
--

DROP TABLE IF EXISTS `uploadfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uploadfile` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '파일번호',
  `origin_file_name` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '원파일명',
  `save_file_name` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '저장파일명',
  `file_path` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '경로',
  `content_type` char(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '컨텐츠타입',
  `size` bigint DEFAULT NULL COMMENT '크기',
  `reg_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일자',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb3 COMMENT='파일업로드';
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-20 23:02:01
