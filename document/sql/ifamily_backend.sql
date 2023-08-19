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
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '管理员昵称',
  `signature` varchar(32) DEFAULT NULL COMMENT '管理员个性签名',
  `avatar` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '管理员头像图片地址',
  `status` tinyint unsigned NOT NULL COMMENT '账号禁用状态：[0]启用 [1]禁用',
  `last_login` datetime NOT NULL COMMENT '上次登录时间',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '是否删除：[0]否 [1]是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1663461154552160258 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1663453677777534978,'root','$2a$10$TNW5JYp0myBZs/iEifK4vu/5DLGFgLlAXJSD89.dA8SxBks7ftruq','你不懂我&我不怪你','路漫漫其修远兮，吾将上下而求索。','https://wpimg.wallstcn.com/577965b9-bb9e-4e02-9f0c-095b41417191',0,'2023-05-30 22:15:29','2023-05-30 15:53:57','2023-05-30 22:15:29',0),(1663461154552160257,'admin','$2a$10$QUEvuqype3N0lyS.kvol2.YMNnyJ2qtdWKkZRCKfRO2lxow3qjoaW','Spring-_-Bear','路漫漫其修远兮，吾将上下而求索。','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',0,'2023-05-30 16:46:22','2023-05-30 16:23:39','2023-05-30 16:46:21',0);
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
  `ip` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP 地址',
  `location` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP 归属地',
  `device` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录设备名称',
  `login_datetime` datetime NOT NULL COMMENT '登录时间',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '是否删除：[0]否 [1]是',
  `admin_id` bigint unsigned NOT NULL COMMENT '管理员 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1663549695231660035 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_login_log`
--

LOCK TABLES `admin_login_log` WRITE;
/*!40000 ALTER TABLE `admin_login_log` DISABLE KEYS */;
INSERT INTO `admin_login_log` VALUES (1663453775236341762,'26.26.26.1','美国/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 15:54:20','2023-05-30 15:54:20','2023-05-30 15:54:20',0,1663453677777534978),(1663460035281162241,'26.26.26.1','未知国家/未知地区/未知城市','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:19:13','2023-05-30 16:19:13','2023-05-30 16:19:13',0,1663453677777534978),(1663461410991902722,'26.26.26.1','未知国家/未知地区/未知城市','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:24:41','2023-05-30 16:24:41','2023-05-30 16:24:41',0,1663461154552160257),(1663461582945783810,'26.26.26.1','美国/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:25:22','2023-05-30 16:25:22','2023-05-30 16:25:22',0,1663461154552160257),(1663461908738347009,'26.26.26.1','未知国家/未知地区/未知城市','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:26:39','2023-05-30 16:26:39','2023-05-30 16:26:39',0,1663453677777534978),(1663462428844625921,'26.26.26.1','美国/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:28:43','2023-05-30 16:28:43','2023-05-30 16:28:43',0,1663461154552160257),(1663463207387140098,'26.26.26.1','美国/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:31:49','2023-05-30 16:31:49','2023-05-30 16:31:49',0,1663453677777534978),(1663463690877145089,'26.26.26.1','美国/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:33:44','2023-05-30 16:33:44','2023-05-30 16:33:44',0,1663461154552160257),(1663466867806666754,'26.26.26.1','美国/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:46:22','2023-05-30 16:46:22','2023-05-30 16:46:22',0,1663461154552160257),(1663468823530930178,'26.26.26.1','美国/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 16:54:08','2023-05-30 16:54:08','2023-05-30 16:54:08',0,1663453677777534978),(1663508831419727873,'26.26.26.1','美国/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 19:33:07','2023-05-30 19:33:07','2023-05-30 19:33:07',0,1663453677777534978),(1663549695231660034,'26.26.26.1','美国/XX/XX','Windows/Windows 10 or Windows Server 2016/Chrome','2023-05-30 22:15:29','2023-05-30 22:15:29','2023-05-30 22:15:29',0,1663453677777534978);
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
  `name` varchar(128) NOT NULL COMMENT '名称',
  `description` varchar(128) NOT NULL COMMENT '描述',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '是否删除：[0]否 [1]是',
  `parent_id` bigint unsigned DEFAULT NULL COMMENT '数据库 ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_db_table` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1663460313418067973 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_table`
--

LOCK TABLES `db_table` WRITE;
/*!40000 ALTER TABLE `db_table` DISABLE KEYS */;
INSERT INTO `db_table` VALUES (1663460311803260930,'ifamily_acl','权限访问控制数据库','2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460312830865410,'admin_role_relation','管理员 - 角色关系表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312830865411,'menu','前台路由菜单表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312897974274,'permission','后台接口权限表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312897974275,'role','系统角色表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312965083137,'role_menu_relation','角色 - 菜单关系表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312969277442,'role_permission_relation','角色 - 权限关系表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312969277443,'user_role_relation','用户 - 角色关系表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460311803260930),(1663460312969277444,'ifamily_backend','后台信息管理数据库','2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460313032192001,'admin','管理员基本信息表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460312969277444),(1663460313032192002,'admin_login_log','管理员登录记录表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460312969277444),(1663460313032192003,'db_table','百家谱系统数据库表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460312969277444),(1663460313095106561,'ifamily_genealogy','家族数据库','2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460313095106562,'genealogy','家族基本信息表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313095106563,'genealogy_memorabilia','家族大事表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313095106564,'genealogy_notice','家族公告表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313158021121,'genealogy_people','家族人员信息表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313158021122,'genealogy_photo','家族相册表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313158021123,'genealogy_profile','家族成员概况表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313225129985,'genealogy_revision_log','家族修谱日志表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313225129986,'genealogy_visitor_log','家族访问记录表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313225129987,'user_genealogy_relation','用户 - 家族关系表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313095106561),(1663460313225129988,'ifamily_manager','第三方服务数据库','2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460313292238850,'code_send_log','验证码发送记录表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313225129988),(1663460313292238851,'qiniu_token_log','七牛云令牌获取记录表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313225129988),(1663460313292238852,'ifamily_social','用户社交数据库','2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460313292238853,'moment','动态基本信息表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313292238852),(1663460313355153409,'moment_comment','动态评论内容表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313292238852),(1663460313355153410,'moment_like','动态点赞用户表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313292238852),(1663460313355153411,'moment_photo','动态图片表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313292238852),(1663460313355153412,'ifamily_user','用户数据库','2023-05-30 16:20:19','2023-05-30 16:20:19',0,NULL),(1663460313418067970,'user','用户基本信息表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313355153412),(1663460313418067971,'user_login_log','用户登录记录表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313355153412),(1663460313418067972,'username_update_log','用户用户名修改记录表','2023-05-30 16:20:19','2023-05-30 16:20:19',0,1663460313355153412);
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

-- Dump completed on 2023-05-30 22:37:32
