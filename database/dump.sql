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
-- Dumping data for table `entrant`
--

LOCK TABLES `entrant` WRITE;
/*!40000 ALTER TABLE `entrant` DISABLE KEYS */;
INSERT INTO `entrant` VALUES (1,4,1),(2,5,1),(3,6,1),(4,7,1),(5,8,1),(6,4,2),(7,5,2),(8,6,2),(9,7,2),(10,8,2),(11,4,3),(12,5,3),(13,6,3),(14,7,3),(15,8,3),(16,4,4),(17,5,4),(18,6,4),(19,5,9),(20,5,11),(21,5,14),(28,6,20),(29,8,20);
/*!40000 ALTER TABLE `entrant` ENABLE KEYS */;
UNLOCK TABLES;

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

LOCK TABLES `evaluation` WRITE;
/*!40000 ALTER TABLE `evaluation` DISABLE KEYS */;
INSERT INTO `evaluation` VALUES (1,6,'2021-05-11 11:00:00',782,50,506,44,2,'2021-05-11 00:08:00',2,'정상'),(2,7,'2021-05-11 11:00:00',834,60,229,142,55,'2021-05-11 00:15:00',4,'지각'),(3,8,'2021-05-11 11:00:00',375,10,794,200,11,'2021-05-11 00:05:00',1,'정상'),(4,9,'2021-05-11 11:00:00',266,1,551,513,30,'2021-05-11 00:12:00',3,'지각'),(5,10,'2021-05-11 11:00:00',0,0,0,0,0,'2021-05-11 02:00:00',1000,'결석'),(6,11,'2021-05-11 14:00:00',137,1,430,98,43,'2021-05-11 04:02:00',2,'정상'),(7,12,'2021-05-11 14:00:00',144,10,148,349,55,'2021-05-11 04:01:00',1,'정상'),(8,13,'2021-05-11 14:00:00',570,50,92,8,14,'2021-05-11 04:03:00',3,'정상'),(9,14,'2021-05-11 14:00:00',360,11,197,9,94,'2021-05-11 04:05:00',4,'정상'),(10,15,'2021-05-11 14:00:00',422,8,135,3,76,'2021-05-11 04:07:00',5,'정상'),(11,1,'2021-05-11 15:00:00',646,123,46,8,8,'2021-05-11 05:01:00',1,'정상'),(12,2,'2021-05-11 15:00:00',548,20,90,45,1,'2021-05-11 05:03:00',2,'정상'),(13,3,'2021-05-11 15:00:00',459,15,4,97,4,'2021-05-11 05:13:00',4,'지각'),(14,4,'2021-05-11 15:00:00',556,60,46,4,18,'2021-05-11 05:08:00',3,'정상'),(15,5,'2021-05-11 15:00:00',392,1,79,51,18,'2021-05-11 05:15:00',5,'지각'),(16,16,'2021-05-11 22:00:00',573,98,815,21,7,'2021-05-11 11:02:00',1,'정상'),(17,17,'2021-05-11 22:00:00',701,50,534,17,8,'2021-05-11 11:15:00',3,'지각'),(18,18,'2021-05-11 22:00:00',959,175,256,44,1,'2021-05-11 11:03:00',2,'정상'),(19,1,'2021-05-12 15:00:00',289,20,291,39,5,'2021-05-12 05:08:00',4,'정상'),(20,2,'2021-05-12 15:00:00',422,25,171,43,0,'2021-05-12 05:07:00',3,'정상'),(21,3,'2021-05-12 15:00:00',0,0,0,0,0,'2021-05-12 06:00:00',1000,'결석'),(22,4,'2021-05-12 15:00:00',565,60,116,3,12,'2021-05-12 05:02:00',2,'정상'),(23,5,'2021-05-12 15:00:00',483,21,134,90,1,'2021-05-12 05:01:00',1,'정상'),(24,6,'2021-05-12 11:00:00',661,52,713,36,6,'2021-05-12 00:02:00',2,'정상'),(25,7,'2021-05-12 11:00:00',1092,99,324,7,5,'2021-05-12 00:01:00',1,'정상'),(26,8,'2021-05-12 11:00:00',684,10,526,36,14,'2021-05-12 00:15:00',4,'지각'),(27,9,'2021-05-12 11:00:00',127,2,1084,79,18,'2021-05-12 00:11:00',3,'지각'),(28,10,'2021-05-12 11:00:00',920,100,279,16,9,'2021-05-12 00:18:00',5,'지각'),(29,11,'2021-05-12 14:00:00',524,1,144,27,1,'2021-05-12 04:02:00',2,'정상'),(30,12,'2021-05-12 14:00:00',308,10,351,46,3,'2021-05-12 04:01:00',1,'정상'),(31,13,'2021-05-12 14:00:00',344,20,286,54,0,'2021-05-12 04:03:00',3,'정상'),(32,14,'2021-05-12 14:00:00',474,21,166,19,1,'2021-05-12 04:05:00',4,'정상'),(33,15,'2021-05-12 14:00:00',342,8,276,8,10,'2021-05-12 04:07:00',5,'정상'),(34,16,'2021-05-12 22:00:00',0,0,0,0,0,'2021-05-12 13:00:00',1000,'결석'),(35,17,'2021-05-12 22:00:00',1375,111,21,31,1,'2021-05-12 11:01:00',1,'정상'),(36,18,'2021-05-12 22:00:00',1109,155,214,67,14,'2021-05-12 11:03:00',2,'정상'),(37,1,'2021-05-13 14:00:00',448,15,204,30,14,'2021-05-13 04:02:00',2,'정상'),(38,2,'2021-05-13 14:00:00',523,111,94,88,3,'2021-05-13 04:01:00',1,'정상'),(39,3,'2021-05-13 14:00:00',56,1,411,85,12,'2021-05-13 04:13:00',4,'지각'),(40,4,'2021-05-13 14:00:00',261,5,381,8,10,'2021-05-13 04:05:00',3,'정상'),(41,5,'2021-05-13 14:00:00',0,0,0,0,0,'2021-05-13 05:00:00',1000,'결석'),(42,6,'2021-05-13 17:00:00',1147,108,95,89,13,'2021-05-13 06:08:00',3,'정상'),(43,7,'2021-05-13 17:00:00',101,8,1117,23,19,'2021-05-13 06:15:00',5,'지각'),(44,8,'2021-05-13 17:00:00',705,15,611,55,9,'2021-05-13 06:05:00',1,'정상'),(45,9,'2021-05-13 17:00:00',780,1,418,98,0,'2021-05-13 06:12:00',4,'지각'),(46,10,'2021-05-13 17:00:00',982,25,274,95,5,'2021-05-13 06:07:00',2,'정상'),(47,16,'2021-05-13 21:00:00',1110,21,223,56,15,'2021-05-13 10:03:00',2,'정상'),(48,17,'2021-05-13 21:00:00',950,20,308,70,4,'2021-05-13 10:09:00',3,'정상'),(49,18,'2021-05-13 21:00:00',20,1,1310,91,7,'2021-05-13 10:01:00',1,'정상'),(50,1,'2021-05-14 15:00:00',518,22,136,23,19,'2021-05-14 05:02:00',2,'정상'),(51,2,'2021-05-14 15:00:00',413,10,266,21,8,'2021-05-14 05:01:00',1,'정상'),(52,3,'2021-05-14 15:00:00',451,48,72,82,19,'2021-05-14 05:08:00',3,'정상'),(53,4,'2021-05-14 15:00:00',227,8,362,17,6,'2021-05-14 05:09:00',4,'정상'),(54,5,'2021-05-14 15:00:00',0,0,0,0,0,'2021-05-14 06:00:00',1000,'결석'),(55,11,'2021-05-14 11:00:00',604,25,32,69,3,'2021-05-14 01:01:00',1,'정상'),(56,12,'2021-05-14 11:00:00',585,20,48,52,11,'2021-05-14 01:02:00',2,'정상'),(57,13,'2021-05-14 11:00:00',313,15,363,8,0,'2021-05-14 01:03:00',3,'정상'),(58,14,'2021-05-14 11:00:00',0,0,0,0,0,'2021-05-14 02:00:00',1000,'결석'),(59,15,'2021-05-14 11:00:00',0,0,0,0,0,'2021-05-14 02:00:00',1000,'결석'),(75,28,'2021-05-20 22:45:50',52,0,68,0,2,'2021-05-20 13:45:50',1000,'정상'),(76,29,'2021-05-20 22:46:02',139,0,17,5,0,'2021-05-20 13:46:02',1000,'정상');
/*!40000 ALTER TABLE `evaluation` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'professor1@professor.com','$2a$10$eWPqUNxqyYziRZeCNfgWWO1ES7IfWxo706dIRY3nPvuvM/ZyfnqPm','김두만','ROLE_USER'),(2,'professor2@professor.com','$2a$10$CqJpfUNfy3rAo6Ur/56fjeCKftolxLM06YvB2ppFWIbvk5gAuEc7i','이지영','ROLE_USER'),(3,'professor3@professor.com','$2a$10$iY1x1OKEN/tGiB0zpcfaDuURmj..iLEMFv8LyTbTN6oTNSFaFVLwO','김미경','ROLE_USER'),(4,'user1@user.com','$2a$10$uBTnyL.DmnF24c3AXdhRUOvgR/Z8fHzXN.BQo9B/VeXo4zwd0NfnO','이혜지','ROLE_USER'),(5,'user2@user.com','$2a$10$Nb9c1KnHcap9XMPAHAtutejG6pJeA/nzgaOtUbEmyxMOmKQliATIu','구민진','ROLE_USER'),(6,'user3@user.com','$2a$10$J36vAUBqxlppwMFFYME4W.6juDrdX3awpnaOn57y8GF2UZ.Rvg7R.','김두상','ROLE_USER'),(7,'user4@user.com','$2a$10$IoiEzbIwOO5mrpSCvQvhFOysxiwD1M4WqQrU9n3l0OX7r5qMdVu3S','윤예준','ROLE_USER'),(8,'user5@user.com','$2a$10$poRWtzsBT8L.W1PM9Gj2MOc9iNxxu6HGX743jmz8k4v8CU3081Yci','현진혁','ROLE_USER'),(9,'user6@user.com','$2a$10$ehDmxJDdUbtf693.T.pmkOYI6pJxsK.l1oh.cZiipv7qFdqfCJt36','박종철','ROLE_USER');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,1,'JAVA 프로그래밍',NULL,'2021-05-20 12:45:18','2021-05-20 12:49:18','준비','공개'),(2,2,'알고리즘',NULL,'2021-05-07 04:00:00','2021-05-07 06:00:00','진행','공개'),(3,3,'vue','vue','2021-05-07 00:00:00','2021-05-07 02:00:00','완료','비공개'),(4,1,'재무관리원론',NULL,'2021-05-07 00:00:00','2021-05-20 13:29:09','준비','공개'),(5,2,'FastAPI','python','2021-05-24 08:00:00','2021-05-24 09:00:00','준비','비공개'),(6,3,'JPA',NULL,'2021-05-25 05:00:00','2021-05-25 06:00:00','준비','공개'),(7,3,'인공지능','AI','2021-05-21 06:00:00','2021-05-21 08:00:00','준비','비공개'),(8,1,'데이터베이스',NULL,'2021-05-24 04:00:00','2021-05-24 06:00:00','준비','공개'),(9,2,'Docker와 배포','어렵다','2021-05-24 02:00:00','2021-05-24 03:00:00','준비','비공개'),(10,2,'웹 프로그래밍',NULL,'2021-05-21 04:00:00','2021-05-21 05:00:00','준비','공개'),(11,2,'운영체제',NULL,'2021-05-21 00:00:00','2021-05-22 02:00:00','준비','공개'),(12,2,'네트워크','네트워크','2021-05-21 07:00:00','2021-05-22 09:00:00','준비','비공개'),(13,2,'Python 프로그래밍',NULL,'2021-05-21 01:00:00','2021-05-23 02:00:00','준비','공개'),(14,2,'안드로이드 프로그래밍',NULL,'2021-05-21 04:00:00','2021-05-23 05:00:00','준비','공개'),(20,1,'테스트2245','1234','2021-05-20 13:45:30','2021-05-20 13:49:13','준비','비공개');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Dumping data for table `uploadfile`
--

LOCK TABLES `uploadfile` WRITE;
/*!40000 ALTER TABLE `uploadfile` DISABLE KEYS */;
INSERT INTO `uploadfile` VALUES (1,'Matteo-Catanese-photo-1518391846015-55a9cc003b25.jpg','2021/03/29/fdd405ff1608416ea64b95917dc0e86b.jpg','/volumes/2021/03/29/fdd405ff1608416ea64b95917dc0e86b.jpg','image/jpeg',157864,'2021-04-29 09:57:07'),(2,'ILLEUM.png','profile/9.png','/volumes/profile/9.png','image/png',26025,'2021-04-29 10:05:58'),(3,'강원도.jpg','profile/11.jpg','/volumes/profile/11.jpg','image/jpeg',55740,'2021-05-02 09:19:07'),(4,'123.gif','profile/1.gif','/volumes/profile/1.gif','image/gif',583072,'2021-05-02 09:19:17'),(5,'icons8-auction-100.png','profile/1.png','/volumes/profile/1.png','image/png',3145,'2021-05-02 09:21:38'),(6,'icons8-auction-100.png','profile/8.png','/volumes/profile/8.png','image/png',3145,'2021-05-02 09:21:57'),(7,'icons8-auction-100.png','profile/1.png','/volumes/profile/1.png','image/png',3145,'2021-05-02 09:22:42'),(8,'icons8-auction-100.png','profile/1.png','/volumes/profile/1.png','image/png',3145,'2021-05-02 09:36:48'),(9,'강원도.jpg','profile/11.jpg','/volumes/profile/11.jpg','image/jpeg',55740,'2021-05-02 09:39:32'),(10,'강원도.jpg','profile/11.jpg','/volumes/profile/11.jpg','image/jpeg',55740,'2021-05-02 09:39:55'),(11,'123.gif','profile/7.gif','/volumes/profile/7.gif','image/gif',583072,'2021-05-02 09:46:34'),(12,'123.gif','profile/7.gif','/volumes/profile/7.gif','image/gif',583072,'2021-05-02 09:49:53'),(13,'123.gif','profile/7.gif','/volumes/profile/7.gif','image/gif',583072,'2021-05-02 09:52:15'),(14,'123.gif','profile/7.gif','/volumes/profile/7.gif','image/gif',583072,'2021-05-02 09:53:59'),(15,'22.jpg','profile/2.jpg','/volumes/profile/2.jpg','image/jpeg',92074,'2021-05-02 09:55:57'),(16,'zzz.jpg','profile/2.jpg','/volumes/profile/2.jpg','image/jpeg',231701,'2021-05-02 09:56:52'),(17,'22.jpg','profile/2.jpg','/volumes/profile/2.jpg','image/jpeg',92074,'2021-05-02 10:06:30'),(18,'22.jpg','profile/2.jpg','/volumes/profile/2.jpg','image/jpeg',92074,'2021-05-02 10:07:24'),(19,'22.jpg','profile/2.jpg','/volumes/profile/2.jpg','image/jpeg',92074,'2021-05-02 10:08:20'),(20,'22.jpg','profile/11.jpg','/volumes/profile/11.jpg','image/jpeg',92074,'2021-05-02 20:22:51'),(21,'22.jpg','profile/11.jpg','/volumes/profile/11.jpg','image/jpeg',92074,'2021-05-02 20:23:34'),(22,'과거사진34.jpg','profile/11.jpg','/volumes/profile/11.jpg','image/jpeg',37439,'2021-05-10 03:30:52'),(23,'과거사진34.jpg','profile/11.jpg','/volumes/profile/11.jpg','image/jpeg',37439,'2021-05-10 03:31:07'),(24,'20200518131620255_1681297571-ok.jpg','profile/12.jpg','/volumes/profile/12.jpg','image/jpeg',42862,'2021-05-11 09:25:54'),(25,'20200518131620255_1681297571-ok.jpg','profile/12.jpg','/volumes/profile/12.jpg','image/jpeg',42862,'2021-05-11 09:29:21'),(26,'profile_120x140.jpg','profile/12.jpg','/volumes/profile/12.jpg','image/jpeg',8044,'2021-05-11 09:45:16'),(27,'강원도.jpg','profile/2.jpg','/volumes/profile/2.jpg','image/jpeg',55740,'2021-05-12 21:59:51'),(28,'20200518131620255_1681297571-ok.jpg','profile/12.jpg','/volumes/profile/12.jpg','image/jpeg',42862,'2021-05-13 01:37:26'),(29,'20200518131620255_1681297571-ok.jpg','profile/12.jpg','/volumes/profile/12.jpg','image/jpeg',42862,'2021-05-13 01:43:51'),(30,'20200518131620255_1681297571-ok.jpg','profile/12.jpg','/volumes/profile/12.jpg','image/jpeg',42862,'2021-05-13 01:44:13'),(31,'20200518131620255_1681297571-ok.jpg','profile/12.jpg','/volumes/profile/12.jpg','image/jpeg',42862,'2021-05-13 01:54:21'),(32,'20200518131620255_1681297571-ok.jpg','profile/12.jpg','/volumes/profile/12.jpg','image/jpeg',42862,'2021-05-13 01:59:25'),(33,'profile_120x140.jpg','profile/12.jpg','/volumes/profile/12.jpg','image/jpeg',8044,'2021-05-13 02:17:21'),(34,'20200518131620255_1681297571-ok.jpg','profile/26.jpg','/volumes/profile/26.jpg','image/jpeg',42862,'2021-05-14 10:13:40'),(35,'51357786-짜증-이모티콘.jpg','profile/10.jpg','/volumes/profile/10.jpg','image/jpeg',117983,'2021-05-16 04:27:04'),(36,'51357786-짜증-이모티콘.jpg','profile/10.jpg','/volumes/profile/10.jpg','image/jpeg',117983,'2021-05-16 04:27:06'),(37,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-16 17:23:28'),(38,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-16 17:23:32'),(39,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-16 17:23:33'),(40,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-16 17:23:33'),(41,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-16 17:23:34'),(42,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-16 17:23:39'),(43,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-16 17:24:40'),(44,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-16 17:24:45'),(45,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 00:12:58'),(46,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 00:13:30'),(47,'logo.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',25125,'2021-05-17 00:13:53'),(48,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-17 00:14:30'),(49,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-17 00:14:45'),(50,'ILLEUM.png','profile/6.png','/volumes/profile/6.png','image/png',26025,'2021-05-17 00:20:38'),(51,'구미_1반_구민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',208765,'2021-05-17 00:21:49'),(52,'구미_1반_구민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',208765,'2021-05-17 00:22:06'),(53,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 00:22:48'),(54,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 00:23:02'),(55,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 00:23:23'),(56,'SH_and_share.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',142700,'2021-05-17 00:23:46'),(57,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 00:24:59'),(58,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 00:25:32'),(59,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 00:25:42'),(60,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 00:26:17'),(61,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-17 00:27:12'),(62,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-17 00:27:17'),(63,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-17 00:27:19'),(64,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-17 00:27:20'),(65,'ILLEUM.png','profile/5.png','/volumes/profile/5.png','image/png',26025,'2021-05-17 09:44:18'),(66,'민진.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',1755659,'2021-05-17 09:46:57'),(67,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 09:48:00'),(68,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 09:49:06'),(69,'usb.png','profile/5.png','/volumes/profile/5.png','image/png',298912,'2021-05-17 09:49:27'),(70,'베니.png','profile/4.png','/volumes/profile/4.png','image/png',17403,'2021-05-17 10:27:46'),(71,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-17 22:20:47'),(72,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-17 22:22:29'),(73,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-17 22:23:01'),(74,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-17 22:23:44'),(75,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-17 22:24:10'),(76,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-17 22:24:22'),(77,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-17 22:24:44'),(78,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-17 22:25:12'),(79,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-18 09:08:07'),(80,'logo.jpg','profile/5.jpg','/volumes/profile/5.jpg','image/jpeg',25125,'2021-05-18 09:08:45'),(81,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-18 09:11:09'),(82,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-18 09:11:21'),(83,'화면 캡처 2021-05-17 222037.png','profile/5.png','/volumes/profile/5.png','image/png',325272,'2021-05-18 09:12:13'),(84,'123.gif','profile/1.gif','/volumes/profile/1.gif','image/gif',583072,'2021-05-20 14:10:23'),(85,'화면 캡처 2021-01-16 185649.png','profile/1.png','/volumes/profile/1.png','image/png',67383,'2021-05-20 14:14:57'),(86,'KakaoTalk_20210125_205351157.jpg','profile/1.jpg','/volumes/profile/1.jpg','image/jpeg',78713,'2021-05-20 14:16:21');
/*!40000 ALTER TABLE `uploadfile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-20 23:02:01
