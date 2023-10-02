-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: ifamily_backend
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
-- Current Database: `ifamily_backend`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ifamily_backend` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ifamily_backend`;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '鐢ㄦ埛鍚?,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '瀵嗙爜',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '绠＄悊鍛樻樀绉?,
  `signature` varchar(32) DEFAULT NULL COMMENT '绠＄悊鍛樹釜鎬х鍚?,
  `avatar` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '绠＄悊鍛樺ご鍍忓浘鐗囧湴鍧€',
  `status` tinyint unsigned NOT NULL COMMENT '璐﹀彿绂佺敤鐘舵€侊細[0]鍚敤 [1]绂佺敤',
  `last_login` datetime NOT NULL COMMENT '涓婃鐧诲綍鏃堕棿',
  `created` datetime NOT NULL COMMENT '鍒涘缓鏃堕棿',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '淇敼鏃堕棿',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '鏄惁鍒犻櫎锛歔0]鍚?[1]鏄?,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1663461154552160258 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1663453677777534978,'root','$2a$10$TNW5JYp0myBZs/iEifK4vu/5DLGFgLlAXJSD89.dA8SxBks7ftruq','浣犱笉鎳傛垜&鎴戜笉鎬綘','璺极婕叾淇繙鍏紝鍚惧皢涓婁笅鑰屾眰绱€?,'',0,'2023-10-02 10:09:13','2023-05-30 15:53:57','2023-10-02 10:09:13',0),(1663461154552160257,'admin','$2a$10$QUEvuqype3N0lyS.kvol2.YMNnyJ2qtdWKkZRCKfRO2lxow3qjoaW','Spring-_-Bear','璺极婕叾淇繙鍏紝鍚惧皢涓婁笅鑰屾眰绱€?,'',0,'2023-05-30 16:46:22','2023-05-30 16:23:39','2023-10-02 09:08:56',0);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_login_log`
--

DROP TABLE IF EXISTS `admin_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_login_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ip` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP 鍦板潃',
  `location` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP 褰掑睘鍦?,
  `device` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '鐧诲綍璁惧鍚嶇О',
  `login_datetime` datetime NOT NULL COMMENT '鐧诲綍鏃堕棿',
  `created` datetime NOT NULL COMMENT '鍒涘缓鏃堕棿',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '淇敼鏃堕棿',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '鏄惁鍒犻櫎锛歔0]鍚?[1]鏄?,
  `admin_id` bigint unsigned NOT NULL COMMENT '绠＄悊鍛?ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1708665407803572226 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_login_log`
--

LOCK TABLES `admin_login_log` WRITE;
/*!40000 ALTER TABLE `admin_login_log` DISABLE KEYS */;
INSERT INTO `admin_login_log` VALUES (1663453775236341762,'26.26.26.1','缇庡浗/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 15:54:20','2023-05-30 15:54:20','2023-05-30 15:54:20',0,1663453677777534978),(1663460035281162241,'26.26.26.1','鏈煡鍥藉/鏈煡鍦板尯/鏈煡鍩庡競','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:19:13','2023-05-30 16:19:13','2023-05-30 16:19:13',0,1663453677777534978),(1663461410991902722,'26.26.26.1','鏈煡鍥藉/鏈煡鍦板尯/鏈煡鍩庡競','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:24:41','2023-05-30 16:24:41','2023-05-30 16:24:41',0,1663461154552160257),(1663461582945783810,'26.26.26.1','缇庡浗/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:25:22','2023-05-30 16:25:22','2023-05-30 16:25:22',0,1663461154552160257),(1663461908738347009,'26.26.26.1','鏈煡鍥藉/鏈煡鍦板尯/鏈煡鍩庡競','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:26:39','2023-05-30 16:26:39','2023-05-30 16:26:39',0,1663453677777534978),(1663462428844625921,'26.26.26.1','缇庡浗/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:28:43','2023-05-30 16:28:43','2023-05-30 16:28:43',0,1663461154552160257),(1663463207387140098,'26.26.26.1','缇庡浗/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:31:49','2023-05-30 16:31:49','2023-05-30 16:31:49',0,1663453677777534978),(1663463690877145089,'26.26.26.1','缇庡浗/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:33:44','2023-05-30 16:33:44','2023-05-30 16:33:44',0,1663461154552160257),(1663466867806666754,'26.26.26.1','缇庡浗/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:46:22','2023-05-30 16:46:22','2023-05-30 16:46:22',0,1663461154552160257),(1663468823530930178,'26.26.26.1','缇庡浗/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:54:08','2023-05-30 16:54:08','2023-05-30 16:54:08',0,1663453677777534978),(1663508831419727873,'26.26.26.1','缇庡浗/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 19:33:07','2023-05-30 19:33:07','2023-05-30 19:33:07',0,1663453677777534978),(1663549695231660034,'26.26.26.1','缇庡浗/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 22:15:29','2023-05-30 22:15:29','2023-05-30 22:15:29',0,1663453677777534978),(1708665407803572225,'169.254.219.235','XX/XX/鍐呯綉IP','Windows/Windows 10 or Windows Server 2016/Chrome','2023-10-02 10:09:13','2023-10-02 10:09:13','2023-10-02 10:09:13',0,1663453677777534978);
/*!40000 ALTER TABLE `admin_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_table`
--

DROP TABLE IF EXISTS `db_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_table` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(128) NOT NULL COMMENT '鍚嶇О',
  `description` varchar(128) NOT NULL COMMENT '鎻忚堪',
  `created` datetime NOT NULL COMMENT '鍒涘缓鏃堕棿',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '淇敼鏃堕棿',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '鏄惁鍒犻櫎锛歔0]鍚?[1]鏄?,
  `parent_id` bigint unsigned DEFAULT NULL COMMENT '鏁版嵁搴?ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_db_table` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1663460313418067973 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_table`
--

LOCK TABLES `db_table` WRITE;
/*!40000 ALTER TABLE `db_table` DISABLE KEYS */;
INSERT INTO `db_table` VALUES (1663460311803260930,'ifamily_acl','鏉冮檺璁块棶鎺у埗鏁版嵁搴?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460312830865410,'admin_role_relation','绠＄悊鍛?- 瑙掕壊鍏崇郴琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312830865411,'menu','鍓嶅彴璺敱鑿滃崟琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312897974274,'permission','鍚庡彴鎺ュ彛鏉冮檺琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312897974275,'role','绯荤粺瑙掕壊琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312965083137,'role_menu_relation','瑙掕壊 - 鑿滃崟鍏崇郴琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312969277442,'role_permission_relation','瑙掕壊 - 鏉冮檺鍏崇郴琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312969277443,'user_role_relation','鐢ㄦ埛 - 瑙掕壊鍏崇郴琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312969277444,'ifamily_backend','鍚庡彴淇℃伅绠＄悊鏁版嵁搴?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460313032192001,'admin','绠＄悊鍛樺熀鏈俊鎭〃','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460312969277444),(1663460313032192002,'admin_login_log','绠＄悊鍛樼櫥褰曡褰曡〃','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460312969277444),(1663460313032192003,'db_table','鐧惧璋辩郴缁熸暟鎹簱琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460312969277444),(1663460313095106561,'ifamily_genealogy','瀹舵棌鏁版嵁搴?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460313095106562,'genealogy','瀹舵棌鍩烘湰淇℃伅琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313095106563,'genealogy_memorabilia','瀹舵棌澶т簨琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313095106564,'genealogy_notice','瀹舵棌鍏憡琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313158021121,'genealogy_people','瀹舵棌浜哄憳淇℃伅琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313158021122,'genealogy_photo','瀹舵棌鐩稿唽琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313158021123,'genealogy_profile','瀹舵棌鎴愬憳姒傚喌琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313225129985,'genealogy_revision_log','瀹舵棌淇氨鏃ュ織琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313225129986,'genealogy_visitor_log','瀹舵棌璁块棶璁板綍琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313225129987,'user_genealogy_relation','鐢ㄦ埛 - 瀹舵棌鍏崇郴琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313225129988,'ifamily_manager','绗笁鏂规湇鍔℃暟鎹簱','2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460313292238850,'code_send_log','楠岃瘉鐮佸彂閫佽褰曡〃','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313225129988),(1663460313292238851,'qiniu_token_log','涓冪墰浜戜护鐗岃幏鍙栬褰曡〃','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313225129988),(1663460313292238852,'ifamily_social','鐢ㄦ埛绀句氦鏁版嵁搴?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460313292238853,'moment','鍔ㄦ€佸熀鏈俊鎭〃','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313292238852),(1663460313355153409,'moment_comment','鍔ㄦ€佽瘎璁哄唴瀹硅〃','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313292238852),(1663460313355153410,'moment_like','鍔ㄦ€佺偣璧炵敤鎴疯〃','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313292238852),(1663460313355153411,'moment_photo','鍔ㄦ€佸浘鐗囪〃','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313292238852),(1663460313355153412,'ifamily_user','鐢ㄦ埛鏁版嵁搴?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460313418067970,'user','鐢ㄦ埛鍩烘湰淇℃伅琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313355153412),(1663460313418067971,'user_login_log','鐢ㄦ埛鐧诲綍璁板綍琛?,'2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313355153412),(1663460313418067972,'username_update_log','鐢ㄦ埛鐢ㄦ埛鍚嶄慨鏀硅褰曡〃','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313355153412);
/*!40000 ALTER TABLE `db_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-02 10:11:53
