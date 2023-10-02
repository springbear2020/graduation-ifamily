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
  `username` varchar(32) NOT NULL COMMENT '鐢ㄦ埛鍚?,
  `phone` varchar(32) DEFAULT NULL COMMENT '鎵嬫満',
  `email` varchar(128) DEFAULT NULL COMMENT '閭',
  `password` varchar(64) NOT NULL COMMENT '瀵嗙爜',
  `nickname` varchar(32) DEFAULT NULL COMMENT '鐢ㄦ埛鏄电О',
  `avatar` varchar(256) DEFAULT NULL COMMENT '鐢ㄦ埛澶村儚鍥剧墖鍦板潃',
  `signature` varchar(32) DEFAULT NULL COMMENT '涓€х鍚?,
  `status` tinyint unsigned NOT NULL COMMENT '璐﹀彿绂佺敤鐘舵€侊細[0]鍚敤 [1]绂佺敤',
  `last_login` datetime NOT NULL COMMENT '涓婃鐧诲綍鏃堕棿',
  `created` datetime NOT NULL COMMENT '鍒涘缓鏃堕棿',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '淇敼鏃堕棿',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '鏄惁鍒犻櫎锛歔0]鍚?[1]鏄?,
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
INSERT INTO `user` VALUES (1663340942339338242,'jiabaoyu',NULL,'jiabaoyu@163.com','$2a$10$884MsI.taNQXEokPwg5LFOdnfXwxcgpY9POOquFG7k/T7HQPOry8K','鎬＄孩闄?璐惧疂鐜?,'','瀵岃吹涓嶇煡涔愪笟,璐┓闅捐€愬噭鍑夛紱鍙€滆緶璐熷ソ闊跺厜,浜庡浗浜庡鏃犳湜.',0,'2023-10-02 09:57:03','2023-05-30 08:25:59','2023-10-02 09:57:03',0),(1663379813617889281,'xuebaochai',NULL,'xuebaochai@163.com','$2a$10$XxYiY/7gd6gBGS09S9Xweeyg.xi.4H11/aS.u5T3T6CAOW4B1fzr6','铇呰姕鑻?钖涘疂閽?,'','鍙徆鍋滄満寰凤紝鍫€滃拸绲墠銆傜帀甯︽灄涓寕锛岄噾绨洩閲屽煁銆?,0,'2023-05-30 17:17:42','2023-05-30 11:00:26','2023-10-02 09:10:59',0),(1663387580080582658,'lindaiyu',NULL,'lindaiyu@163.com','$2a$10$tP1wfUOZWCXVOQ1aHg9Jw.27BymSehJ9WlTDEH6otjx.hHK0CtJmC','娼囨箻棣?鏋楅粵鐜?,'','鍙徆鍋滄満寰凤紝鍫€滃拸绲墠銆傜帀甯︽灄涓寕锛岄噾绨洩閲屽煁銆?,0,'2023-05-30 11:31:18','2023-05-30 11:31:18','2023-10-02 09:10:59',0),(1663414672302272513,'qinkeqing',NULL,'qinkeqing@163.com','$2a$10$NckGJdMMe8Grf03RewP3AubKt0xTBi0HMFPcWYRmCz4J4d3gKjSeG','绉﹀彲鍗?,'','鎯呭ぉ鎯呮捣骞绘儏韬紝鎯呮棦鐩搁€㈠繀涓绘帆銆傛极瑷€涓嶈倴鐨嗚崳鍑猴紝閫犺寮€绔疄鍦?,0,'2023-05-30 13:18:57','2023-05-30 13:18:57','2023-10-02 09:10:59',0),(1663415340069023746,'jiayuanchun',NULL,'jiayuanchun@163.com','$2a$10$2JR29cYAHHU96DJulvgbley5DhIqlwjbYjBrGXZBiOuSqrSUZyFIa','璐惧厓鏄?,'','浜屽崄骞存潵杈ㄦ槸闈烇紝姒磋姳寮€澶勭収瀹棻銆備笁鏄ヤ簤鍙婂垵鏄ユ櫙锛岃檸鍏曠浉閫㈠ぇ姊?,0,'2023-05-30 13:24:03','2023-05-30 13:21:36','2023-10-02 09:10:59',0),(1663416082070761474,'jiatanchun',NULL,'jiatanchun@163.com','$2a$10$Awd.hzK4.CiXCfUxfDY7SOY6dYvVxzVfKogX60Wjr17bRErvr318a','璐炬帰鏄?,'','鎵嶈嚜绮炬槑蹇楄嚜楂橈紝鐢熶簬鏈笘杩愬亸娑堛€傛竻鏄庢稌閫佹睙杈规湜锛屽崈閲屼笢椋庝竴姊?,0,'2023-05-30 13:47:37','2023-05-30 13:24:33','2023-10-02 09:10:59',0),(1663416614751563778,'wangxifeng',NULL,'wangxifeng@163.com','$2a$10$ETlEtUhF7wsrFxg35yjEcOax8bjUpP7462uEodZ463uVT7AncScGG','鐜嬬啓鍑?,'','鍑￠笩鍋忎粠鏈笘鏉ワ紝閮界煡鐖辨厱姝ょ敓鎵嶃€備竴浠庝簩浠や笁浜烘湪锛屽摥鍚戦噾闄典簨鏇?,0,'2023-05-30 13:26:40','2023-05-30 13:26:40','2023-10-02 09:10:59',0),(1663417074715717634,'jiaxichun',NULL,'jiaxichun@163.com','$2a$10$KCu0ZL0GJzG/K4e.6p9Sc.I7KbBajzSNHLLVd.no2CVWZUcWEZILu','璐炬儨鏄?,'','鍕樼牬涓夋槬鏅笉闀匡紝缂佽。椤挎敼鏄斿勾濡嗐€傚彲鎬滅唬鎴蜂警闂ㄥコ锛岀嫭鍗ч潚鐏彜浣?,0,'2023-05-30 17:17:58','2023-05-30 13:28:30','2023-10-02 09:10:59',0),(1663417464697909249,'jiayingchun',NULL,'jiayingchun@163.com','$2a$10$4eSa2ntpBS2xvi/KSf8dqOHKj.1I1F7sCdeVzXUdyvhnPkLOv/I5K','璐捐繋鏄?,'','瀛愮郴涓北鐙硷紝寰楀織渚跨寲鐙傘€傞噾闂鸿姳鏌宠川锛屼竴杞借荡榛勭脖銆?,0,'2023-05-30 13:30:03','2023-05-30 13:30:03','2023-10-02 09:10:59',0),(1663417933994389506,'jiaqiaojie',NULL,'jiaqiaojie@163.com','$2a$10$Q2VIONxP7yFyopazF0nK9u.QnAGxAufmcrNu0UySnmkWYLnu8oW/K','璐惧阀濮?,'','浜嬭触浼戜簯璐碉紝瀹朵骸鑾浜层€傚伓鍥犳祹鍒樻皬锛屽阀寰楅亣鎭╀汉銆?,0,'2023-05-30 13:32:51','2023-05-30 13:31:55','2023-10-02 09:10:59',0),(1663418387998437378,'liwan',NULL,'liwan@163.com','$2a$10$vDt.AYWxe2NXpIso8gUokeVdB2kFJxI09lyIaZHdaIlaXl7LKCab6','鏉庣酣','','妗冩潕鏄ラ缁撳瓙瀹岋紝鍒板ご璋佷技涓€鐩嗗叞銆傚鍐版按濂界┖鐩稿锛屾瀴涓庝粬浜轰綔绗?,0,'2023-05-30 13:34:50','2023-05-30 13:33:43','2023-10-02 09:10:59',0);
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
  `ip` char(16) NOT NULL COMMENT 'IP 鍦板潃',
  `location` varchar(128) NOT NULL COMMENT 'IP 褰掑睘鍦?,
  `device` varchar(128) NOT NULL COMMENT '鐧诲綍璁惧鍚嶇О',
  `login_datetime` datetime NOT NULL COMMENT '鐧诲綍鏃堕棿',
  `created` datetime NOT NULL COMMENT '鍒涘缓鏃堕棿',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '淇敼鏃堕棿',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '鏄惁鍒犻櫎锛歔0]鍚?[1]鏄?,
  `user_id` bigint unsigned NOT NULL COMMENT '鐢ㄦ埛 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1708662345470857218 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login_log`
--

LOCK TABLES `user_login_log` WRITE;
/*!40000 ALTER TABLE `user_login_log` DISABLE KEYS */;
INSERT INTO `user_login_log` VALUES (1663340950002331650,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 08:26:01','2023-05-30 08:26:01','2023-05-30 08:26:01',0,1663340942339338242),(1663342866807648257,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 08:33:38','2023-05-30 08:33:38','2023-05-30 08:33:38',0,1663340942339338242),(1663343013876723713,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 08:34:13','2023-05-30 08:34:13','2023-05-30 08:34:13',0,1663340942339338242),(1663379815069118466,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 11:00:27','2023-05-30 11:00:27','2023-05-30 11:00:27',0,1663379813617889281),(1663380112189419522,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 11:01:38','2023-05-30 11:01:38','2023-05-30 11:01:38',0,1663379813617889281),(1663387583524106241,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 11:31:19','2023-05-30 11:31:19','2023-05-30 11:31:19',0,1663387580080582658),(1663414681714290690,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:19:00','2023-05-30 13:19:00','2023-05-30 13:19:00',0,1663414672302272513),(1663415344112332801,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:21:37','2023-05-30 13:21:37','2023-05-30 13:21:37',0,1663415340069023746),(1663415954895269889,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:24:03','2023-05-30 13:24:03','2023-05-30 13:24:03',0,1663415340069023746),(1663416086743216129,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:24:35','2023-05-30 13:24:35','2023-05-30 13:24:35',0,1663416082070761474),(1663416616194404354,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:26:41','2023-05-30 13:26:41','2023-05-30 13:26:41',0,1663416614751563778),(1663417080533217281,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:28:31','2023-05-30 13:28:31','2023-05-30 13:28:31',0,1663417074715717634),(1663417470658015234,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:30:04','2023-05-30 13:30:04','2023-05-30 13:30:04',0,1663417464697909249),(1663418168887996417,'26.26.26.1','鏈煡鍥藉/鏈煡鍦板尯/鏈煡鍩庡競','iPhone/iPhone/Safari','2023-05-30 13:32:51','2023-05-30 13:32:51','2023-05-30 13:32:51',0,1663417933994389506),(1663418670212182018,'26.26.26.1','缇庡浗/XX/XX','iPhone/iPhone/Safari','2023-05-30 13:34:50','2023-05-30 13:34:50','2023-05-30 13:34:50',0,1663418387998437378),(1663419299626217473,'10.128.148.39','XX/XX/鍐呯綉IP','Android/Android/MicroMessenger','2023-05-30 13:37:21','2023-05-30 13:37:21','2023-05-30 13:37:21',0,1663417074715717634),(1663421885787881473,'10.128.148.39','XX/XX/鍐呯綉IP','Android/Android/MicroMessenger','2023-05-30 13:47:37','2023-05-30 13:47:37','2023-05-30 13:47:37',0,1663416082070761474),(1663422133008547842,'10.129.190.209','XX/XX/鍐呯綉IP','iPhone/iPhone/Safari','2023-05-30 13:48:36','2023-05-30 13:48:36','2023-05-30 13:48:36',0,1663340942339338242),(1663422172195930113,'10.129.190.209','XX/XX/鍐呯綉IP','iPhone/iPhone/Safari','2023-05-30 13:48:45','2023-05-30 13:48:45','2023-05-30 13:48:45',0,1663340942339338242),(1663469331062677505,'10.129.190.209','XX/XX/鍐呯綉IP','iPhone/iPhone/Safari','2023-05-30 16:56:09','2023-05-30 16:56:09','2023-05-30 16:56:09',0,1663340942339338242),(1663470696786767874,'10.129.190.209','鏈煡鍦扮偣','iPhone/iPhone/Safari','2023-05-30 17:01:35','2023-05-30 17:01:35','2023-05-30 17:01:35',0,1663340942339338242),(1663474014867132417,'10.129.190.209','XX/XX/鍐呯綉IP','iPhone/iPhone/Safari','2023-05-30 17:14:46','2023-05-30 17:14:46','2023-05-30 17:14:46',0,1663417074715717634),(1663474753018499073,'10.129.190.209','XX/XX/鍐呯綉IP','iPhone/iPhone/Safari','2023-05-30 17:17:42','2023-05-30 17:17:42','2023-05-30 17:17:42',0,1663379813617889281),(1663474823424086017,'10.129.190.209','XX/XX/鍐呯綉IP','iPhone/iPhone/Safari','2023-05-30 17:17:58','2023-05-30 17:17:58','2023-05-30 17:17:58',0,1663417074715717634),(1708650025042595842,'169.254.219.235','鏈煡鍥藉/鏈煡鍦板尯/鏈煡鍩庡競','iPhone/iPhone/Safari','2023-10-02 09:08:06','2023-10-02 09:08:06','2023-10-02 09:08:06',0,1663340942339338242),(1708662345470857217,'169.254.219.235','XX/XX/鍐呯綉IP','iPhone/iPhone/Safari','2023-10-02 09:57:03','2023-10-02 09:57:03','2023-10-02 09:57:03',0,1663340942339338242);
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
  `old_username` varchar(32) NOT NULL COMMENT '鏇存柊鍓嶇殑鐢ㄦ埛鍚?,
  `new_username` varchar(32) NOT NULL COMMENT '鏇存柊鍚庣殑鐢ㄦ埛鍚?,
  `created` datetime NOT NULL COMMENT '鍒涘缓鏃堕棿',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '淇敼鏃堕棿',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '鏄惁鍒犻櫎锛歔0]鍚?[1]鏄?,
  `user_id` bigint unsigned NOT NULL COMMENT '鐢ㄦ埛 ID',
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

-- Dump completed on 2023-10-02 10:13:05
