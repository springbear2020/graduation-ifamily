-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: ifamily_acl
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
-- Current Database: `ifamily_acl`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ifamily_acl` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ifamily_acl`;

--
-- Table structure for table `admin_role_relation`
--

DROP TABLE IF EXISTS `admin_role_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_role_relation` (
  `admin_id` bigint unsigned NOT NULL COMMENT '绠＄悊鍛?ID',
  `role_id` bigint unsigned NOT NULL COMMENT '瑙掕壊 ID',
  PRIMARY KEY (`admin_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_role_relation`
--

LOCK TABLES `admin_role_relation` WRITE;
/*!40000 ALTER TABLE `admin_role_relation` DISABLE KEYS */;
INSERT INTO `admin_role_relation` VALUES (1663453677777534978,1663457665461772290),(1663461154552160257,1663457578576764929);
/*!40000 ALTER TABLE `admin_role_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `path` varchar(64) NOT NULL COMMENT '璺敱璺緞',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '鑿滃崟鍚嶇О',
  `description` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '鑿滃崟鎻忚堪',
  `status` tinyint NOT NULL COMMENT '鏄惁宸茬鐢細[0]鍚?[1]鏄?,
  `created` datetime NOT NULL COMMENT '鍒涘缓鏃堕棿',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '淇敼鏃堕棿',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_path` (`path`) COMMENT '鑿滃崟璺緞鍞竴'
) ENGINE=InnoDB AUTO_INCREMENT=1663456650570227715 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1663456326258253825,'/data/**','鏁版嵁绠＄悊','绯荤粺鏁版嵁绠＄悊锛屽澶囦唤銆佸鍏ュ拰瀵煎嚭鏁版嵁',0,'2023-05-30 16:04:28','2023-05-30 16:04:28'),(1663456471246954498,'/user/**','鐢ㄦ埛绠＄悊','鐢ㄦ埛淇℃伅绠＄悊锛屽寘鍚垎閰嶈鑹茬瓑',0,'2023-05-30 16:05:03','2023-05-30 16:05:03'),(1663456650570227714,'/system/**','鏉冮檺绠＄悊','绯荤粺鏉冮檺瀹夊叏绠＄悊锛屽寘鍚郴缁熻鑹层€佽彍鍗曡祫婧愬拰鏉冮檺璧勬簮绛?,0,'2023-05-30 16:05:46','2023-05-30 16:05:46');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `path` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '鏉冮檺璺緞',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '鏉冮檺鍚嶇О',
  `description` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '鏉冮檺鎻忚堪',
  `status` tinyint unsigned NOT NULL COMMENT '鏉冮檺绂佺敤鐘舵€侊細[0]鍚敤 [1]绂佺敤',
  `created` datetime NOT NULL COMMENT '鍒涘缓鏃堕棿',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '淇敼鏃堕棿',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_path` (`path`) COMMENT '鏉冮檺璺緞鍞竴'
) ENGINE=InnoDB AUTO_INCREMENT=1663455274888847363 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1663454481729183745,'/**/feign/**','鏈嶅姟璋冪敤','鍚庡彴鏈嶅姟闂磋繙绋嬫湇鍔¤皟鐢?,0,'2023-05-30 15:57:09','2023-05-30 15:57:09'),(1663454774193807361,'/acl/admin/**','绯荤粺鏉冮檺','绯荤粺鏉冮檺璧勬簮鎺у埗绠＄悊',0,'2023-05-30 15:58:18','2023-05-30 16:00:32'),(1663455119745736705,'/backend/admin/**','鍚庡彴绠＄悊','鍚庡彴绯荤粺璧勬簮绠＄悊',1,'2023-05-30 15:59:41','2023-05-30 22:04:37'),(1663455274888847362,'/genealogy/admin/**','瀹舵棌绠＄悊','瀹舵棌璧勬簮鏁版嵁绠＄悊',0,'2023-05-30 16:00:18','2023-05-30 16:00:18');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '瑙掕壊鍚嶇О',
  `description` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '瑙掕壊鎻忚堪',
  `status` tinyint unsigned NOT NULL COMMENT '瑙掕壊绂佺敤鐘舵€侊細[0]鍚敤 [1]绂佺敤',
  `created` datetime NOT NULL COMMENT '鍒涘缓鏃堕棿',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '淇敼鏃堕棿',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`) COMMENT '瑙掕壊鍚嶇О鍞竴'
) ENGINE=InnoDB AUTO_INCREMENT=1663457665461772291 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1663457181044826114,'GENEALOGY_CREATOR','瀹舵棌鍒涘缓鑰?,0,'2023-05-30 16:07:52','2023-05-30 16:54:22'),(1663457418547290114,'GENEALOGY_ADMIN','瀹舵棌绠＄悊鍛?,0,'2023-05-30 16:08:49','2023-05-30 16:54:25'),(1663457578576764929,'PLATFORM_ADMIN','骞冲彴绠＄悊鍛?,0,'2023-05-30 16:09:27','2023-05-30 16:09:27'),(1663457665461772290,'ROOT','瓒呯骇绠＄悊鍛?,0,'2023-05-30 16:09:48','2023-05-30 16:09:48');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu_relation`
--

DROP TABLE IF EXISTS `role_menu_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menu_relation` (
  `role_id` bigint unsigned NOT NULL COMMENT '瑙掕壊 ID',
  `menu_id` bigint unsigned NOT NULL COMMENT '鑿滃崟 ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu_relation`
--

LOCK TABLES `role_menu_relation` WRITE;
/*!40000 ALTER TABLE `role_menu_relation` DISABLE KEYS */;
INSERT INTO `role_menu_relation` VALUES (1663457578576764929,1663456471246954498),(1663457665461772290,1663456326258253825),(1663457665461772290,1663456471246954498),(1663457665461772290,1663456650570227714);
/*!40000 ALTER TABLE `role_menu_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission_relation`
--

DROP TABLE IF EXISTS `role_permission_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission_relation` (
  `role_id` bigint unsigned NOT NULL COMMENT '瑙掕壊 ID',
  `permission_id` bigint unsigned NOT NULL COMMENT '鏉冮檺 ID',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission_relation`
--

LOCK TABLES `role_permission_relation` WRITE;
/*!40000 ALTER TABLE `role_permission_relation` DISABLE KEYS */;
INSERT INTO `role_permission_relation` VALUES (1663457181044826114,1663455274888847362),(1663457418547290114,1663455274888847362),(1663457578576764929,1663455119745736705),(1663457578576764929,1663455274888847362),(1663457665461772290,1663454481729183745),(1663457665461772290,1663454774193807361),(1663457665461772290,1663455119745736705),(1663457665461772290,1663455274888847362);
/*!40000 ALTER TABLE `role_permission_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_relation`
--

DROP TABLE IF EXISTS `user_role_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_relation` (
  `user_id` bigint unsigned NOT NULL COMMENT '鐢ㄦ埛 ID',
  `role_id` bigint unsigned NOT NULL COMMENT '瑙掕壊 ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_relation`
--

LOCK TABLES `user_role_relation` WRITE;
/*!40000 ALTER TABLE `user_role_relation` DISABLE KEYS */;
INSERT INTO `user_role_relation` VALUES (1663340942339338242,1663457181044826114),(1663379813617889281,1663457418547290114),(1663387580080582658,1663457418547290114);
/*!40000 ALTER TABLE `user_role_relation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-02 10:11:25
