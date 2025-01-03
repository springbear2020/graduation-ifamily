-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: ifamily_user
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Current Database: `ifamily_user`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ifamily_user` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ifamily_user`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '用户头像图片地址',
  `signature` varchar(32) DEFAULT NULL COMMENT '个性签名',
  `status` tinyint unsigned NOT NULL COMMENT '账号禁用状态：[0]启用 [1]禁用',
  `last_login` datetime NOT NULL COMMENT '上次登录时间',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '是否删除：[0]否 [1]是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_username` (`username`) USING BTREE,
  UNIQUE KEY `uk_phone` (`phone`) USING BTREE,
  UNIQUE KEY `uk_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1663418387998437379 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1663340942339338242,'jiabaoyu',NULL,'jiabaoyu@163.com','$2a$10$884MsI.taNQXEokPwg5LFOdnfXwxcgpY9POOquFG7k/T7HQPOry8K','怡红院-贾宝玉','','富贵不知乐业,贫穷难耐凄凉；可怜辜负好韶光,于国于家无望.',0,'2023-10-02 09:57:03','2023-05-30 08:25:59','2023-10-02 09:57:03',0),(1663379813617889281,'xuebaochai',NULL,'xuebaochai@163.com','$2a$10$XxYiY/7gd6gBGS09S9Xweeyg.xi.4H11/aS.u5T3T6CAOW4B1fzr6','蘅芜苑-薛宝钗','','可叹停机德，堪怜咏絮才。玉带林中挂，金簪雪里埋。',0,'2023-05-30 17:17:42','2023-05-30 11:00:26','2023-10-02 09:10:59',0),(1663387580080582658,'lindaiyu',NULL,'lindaiyu@163.com','$2a$10$tP1wfUOZWCXVOQ1aHg9Jw.27BymSehJ9WlTDEH6otjx.hHK0CtJmC','潇湘馆-林黛玉','','可叹停机德，堪怜咏絮才。玉带林中挂，金簪雪里埋。',0,'2023-05-30 11:31:18','2023-05-30 11:31:18','2023-10-02 09:10:59',0),(1663414672302272513,'qinkeqing',NULL,'qinkeqing@163.com','$2a$10$NckGJdMMe8Grf03RewP3AubKt0xTBi0HMFPcWYRmCz4J4d3gKjSeG','秦可卿','','情天情海幻情身，情既相逢必主淫。漫言不肖皆荣出，造衅开端实在',0,'2023-05-30 13:18:57','2023-05-30 13:18:57','2023-10-02 09:10:59',0),(1663415340069023746,'jiayuanchun',NULL,'jiayuanchun@163.com','$2a$10$2JR29cYAHHU96DJulvgbley5DhIqlwjbYjBrGXZBiOuSqrSUZyFIa','贾元春','','二十年来辨是非，榴花开处照宫闱。三春争及初春景，虎兕相逢大梦',0,'2023-05-30 13:24:03','2023-05-30 13:21:36','2023-10-02 09:10:59',0),(1663416082070761474,'jiatanchun',NULL,'jiatanchun@163.com','$2a$10$Awd.hzK4.CiXCfUxfDY7SOY6dYvVxzVfKogX60Wjr17bRErvr318a','贾探春','','才自精明志自高，生于末世运偏消。清明涕送江边望，千里东风一梦',0,'2023-05-30 13:47:37','2023-05-30 13:24:33','2023-10-02 09:10:59',0),(1663416614751563778,'wangxifeng',NULL,'wangxifeng@163.com','$2a$10$ETlEtUhF7wsrFxg35yjEcOax8bjUpP7462uEodZ463uVT7AncScGG','王熙凤','','凡鸟偏从末世来，都知爱慕此生才。一从二令三人木，哭向金陵事更',0,'2023-05-30 13:26:40','2023-05-30 13:26:40','2023-10-02 09:10:59',0),(1663417074715717634,'jiaxichun',NULL,'jiaxichun@163.com','$2a$10$KCu0ZL0GJzG/K4e.6p9Sc.I7KbBajzSNHLLVd.no2CVWZUcWEZILu','贾惜春','','勘破三春景不长，缁衣顿改昔年妆。可怜绣户侯门女，独卧青灯古佛',0,'2023-05-30 17:17:58','2023-05-30 13:28:30','2023-10-02 09:10:59',0),(1663417464697909249,'jiayingchun',NULL,'jiayingchun@163.com','$2a$10$4eSa2ntpBS2xvi/KSf8dqOHKj.1I1F7sCdeVzXUdyvhnPkLOv/I5K','贾迎春','','子系中山狼，得志便猖狂。金闺花柳质，一载赴黄粱。',0,'2023-05-30 13:30:03','2023-05-30 13:30:03','2023-10-02 09:10:59',0),(1663417933994389506,'jiaqiaojie',NULL,'jiaqiaojie@163.com','$2a$10$Q2VIONxP7yFyopazF0nK9u.QnAGxAufmcrNu0UySnmkWYLnu8oW/K','贾巧姐','','事败休云贵，家亡莫论亲。偶因济刘氏，巧得遇恩人。',0,'2023-05-30 13:32:51','2023-05-30 13:31:55','2023-10-02 09:10:59',0),(1663418387998437378,'liwan',NULL,'liwan@163.com','$2a$10$vDt.AYWxe2NXpIso8gUokeVdB2kFJxI09lyIaZHdaIlaXl7LKCab6','李纨','','桃李春风结子完，到头谁似一盆兰。如冰水好空相妒，枉与他人作笑',0,'2023-05-30 13:34:50','2023-05-30 13:33:43','2023-10-02 09:10:59',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_login_log`
--

DROP TABLE IF EXISTS `user_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_login_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ip` char(16) NOT NULL COMMENT 'IP 地址',
  `location` varchar(128) NOT NULL COMMENT 'IP 归属地',
  `device` varchar(128) NOT NULL COMMENT '登录设备名称',
  `login_datetime` datetime NOT NULL COMMENT '登录时间',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '是否删除：[0]否 [1]是',
  `user_id` bigint unsigned NOT NULL COMMENT '用户 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1708662345470857218 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login_log`
--

LOCK TABLES `user_login_log` WRITE;
/*!40000 ALTER TABLE `user_login_log` DISABLE KEYS */;
INSERT INTO `user_login_log` VALUES (1663340950002331650,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 08:26:01','2023-05-30 08:26:01','2023-05-30 08:26:01',0,1663340942339338242),(1663342866807648257,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 08:33:38','2023-05-30 08:33:38','2023-05-30 08:33:38',0,1663340942339338242),(1663343013876723713,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 08:34:13','2023-05-30 08:34:13','2023-05-30 08:34:13',0,1663340942339338242),(1663379815069118466,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 11:00:27','2023-05-30 11:00:27','2023-05-30 11:00:27',0,1663379813617889281),(1663380112189419522,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 11:01:38','2023-05-30 11:01:38','2023-05-30 11:01:38',0,1663379813617889281),(1663387583524106241,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 11:31:19','2023-05-30 11:31:19','2023-05-30 11:31:19',0,1663387580080582658),(1663414681714290690,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:19:00','2023-05-30 13:19:00','2023-05-30 13:19:00',0,1663414672302272513),(1663415344112332801,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:21:37','2023-05-30 13:21:37','2023-05-30 13:21:37',0,1663415340069023746),(1663415954895269889,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:24:03','2023-05-30 13:24:03','2023-05-30 13:24:03',0,1663415340069023746),(1663416086743216129,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:24:35','2023-05-30 13:24:35','2023-05-30 13:24:35',0,1663416082070761474),(1663416616194404354,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:26:41','2023-05-30 13:26:41','2023-05-30 13:26:41',0,1663416614751563778),(1663417080533217281,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:28:31','2023-05-30 13:28:31','2023-05-30 13:28:31',0,1663417074715717634),(1663417470658015234,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:30:04','2023-05-30 13:30:04','2023-05-30 13:30:04',0,1663417464697909249),(1663418168887996417,'26.26.26.1','未知国家/未知地区/未知城市','iPhone/iPhone/Safari','2023-05-30 13:32:51','2023-05-30 13:32:51','2023-05-30 13:32:51',0,1663417933994389506),(1663418670212182018,'26.26.26.1','美国/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:34:50','2023-05-30 13:34:50','2023-05-30 13:34:50',0,1663418387998437378),(1663419299626217473,'10.128.148.39','XX/XX/内网IP','Android/Android/MicroMessenger','2023-05-30 13:37:21','2023-05-30 13:37:21','2023-05-30 13:37:21',0,1663417074715717634),(1663421885787881473,'10.128.148.39','XX/XX/内网IP','Android/Android/MicroMessenger','2023-05-30 13:47:37','2023-05-30 13:47:37','2023-05-30 13:47:37',0,1663416082070761474),(1663422133008547842,'10.129.190.209','XX/XX/内网IP','iPhone/iPhone/Safari','2023-05-30 13:48:36','2023-05-30 13:48:36','2023-05-30 13:48:36',0,1663340942339338242),(1663422172195930113,'10.129.190.209','XX/XX/内网IP','iPhone/iPhone/Safari','2023-05-30 13:48:45','2023-05-30 13:48:45','2023-05-30 13:48:45',0,1663340942339338242),(1663469331062677505,'10.129.190.209','XX/XX/内网IP','iPhone/iPhone/Safari','2023-05-30 16:56:09','2023-05-30 16:56:09','2023-05-30 16:56:09',0,1663340942339338242),(1663470696786767874,'10.129.190.209','未知地点','iPhone/iPhone/Safari','2023-05-30 17:01:35','2023-05-30 17:01:35','2023-05-30 17:01:35',0,1663340942339338242),(1663474014867132417,'10.129.190.209','XX/XX/内网IP','iPhone/iPhone/Safari','2023-05-30 17:14:46','2023-05-30 17:14:46','2023-05-30 17:14:46',0,1663417074715717634),(1663474753018499073,'10.129.190.209','XX/XX/内网IP','iPhone/iPhone/Safari','2023-05-30 17:17:42','2023-05-30 17:17:42','2023-05-30 17:17:42',0,1663379813617889281),(1663474823424086017,'10.129.190.209','XX/XX/内网IP','iPhone/iPhone/Safari','2023-05-30 17:17:58','2023-05-30 17:17:58','2023-05-30 17:17:58',0,1663417074715717634),(1708650025042595842,'169.254.219.235','未知国家/未知地区/未知城市','iPhone/iPhone/Safari','2023-10-02 09:08:06','2023-10-02 09:08:06','2023-10-02 09:08:06',0,1663340942339338242),(1708662345470857217,'169.254.219.235','XX/XX/内网IP','iPhone/iPhone/Safari','2023-10-02 09:57:03','2023-10-02 09:57:03','2023-10-02 09:57:03',0,1663340942339338242);
/*!40000 ALTER TABLE `user_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `username_update_log`
--

DROP TABLE IF EXISTS `username_update_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `username_update_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `old_username` varchar(32) NOT NULL COMMENT '更新前的用户名',
  `new_username` varchar(32) NOT NULL COMMENT '更新后的用户名',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '是否删除：[0]否 [1]是',
  `user_id` bigint unsigned NOT NULL COMMENT '用户 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1663380069390741507 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `username_update_log`
--

LOCK TABLES `username_update_log` WRITE;
/*!40000 ALTER TABLE `username_update_log` DISABLE KEYS */;
INSERT INTO `username_update_log` VALUES (1663342924777123842,'e48d2d8a791e402293bd16f11c9e4de8','jiabaoyu','2023-05-30 08:33:51','2023-05-30 08:33:51',0,1663340942339338242),(1663380069390741506,'0931f623ef824cb8992c5d68833d2de9','xuebaochai','2023-05-30 11:01:27','2023-05-30 11:01:27',0,1663379813617889281);
/*!40000 ALTER TABLE `username_update_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-02 17:40:09
