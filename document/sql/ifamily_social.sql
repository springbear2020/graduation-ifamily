-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: ifamily_social
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
-- Current Database: `ifamily_social`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ifamily_social` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ifamily_social`;

--
-- Table structure for table `moment`
--

DROP TABLE IF EXISTS `moment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `moment` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` varchar(1024) NOT NULL COMMENT '动态内容',
  `who_can_see` tinyint unsigned NOT NULL COMMENT '权限设置：[1]仅自己可见 [2]家庭成员可见 [3]默认家族成员可见',
  `scheduled` datetime DEFAULT NULL COMMENT '定时发表时间',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '是否删除：[0]否 [1]是',
  `post_user_id` bigint unsigned NOT NULL COMMENT '发布者用户 ID',
  `genealogy_id` bigint unsigned NOT NULL COMMENT '可查看当前动态的人员家族',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1663391618838753282 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moment`
--

LOCK TABLES `moment` WRITE;
/*!40000 ALTER TABLE `moment` DISABLE KEYS */;
INSERT INTO `moment` VALUES (1663372336125898754,'《西江月·批宝玉二首》\n无故寻愁觅恨，有时似傻如狂。\n纵然生得好皮囊，腹内原来草莽。\n潦倒不通世务，愚顽伯读文章。\n行为偏僻性乖张，那管世人诽谤。\n富贵不知乐业，贫穷难耐凄凉。\n可怜辜负好时光，于国于家无望。\n天下无能第一，古今不肖无双。\n寄言执挎与膏粱，莫效此儿形状。',1,'2023-02-15 10:35:00','2023-05-30 10:30:44','2023-05-30 11:04:39',0,1663340942339338242,1663345120033869825),(1663372903820750850,'《警幻仙姑歌辞》\n春梦随云散，飞花逐水流。\n寄言众儿女，何必觅闲愁。 ',1,'2023-03-15 10:32:00','2023-05-30 10:32:59','2023-05-30 11:04:45',0,1663340942339338242,1663345120033869825),(1663373386681610242,'《警幻仙子赋》\n方离柳坞，乍出花房。但行处，鸟惊庭 树;将到时，影度回廊。仙抉乍飘今，闻麝 兰之馥郁;荷衣欲动今，听环佩之铿锵。届 笑春桃今，云堆翠髻;唇绽樱颗今，榴齿含 香。纤腰之楚楚兮，回风舞雪;珠翠之辉辉 今。满额鹅黄。出没花问今，宜嗅宜喜;徘徊 池上今。若飞若扬。蛾眉颦笑今，将言而未语; 莲步乍移今，待止而欲行。羡彼之良质今，冰 清玉润;慕彼之华服今，闪灼文章。爱彼之貌 容今，香培玉琢;美彼之态度今，凤翥龙 翔。其素若何，春梅绽雪。其洁若何，秋菊披 霜。其静若何，松生空谷。其艳若何，霞映澄塘。 其文若何，龙游曲招。其神若何，月射寒江。 应惭西子，实愧王嫱。奇矣哉，生于孰地，来 自伺方?信矣乎，瑶池不二，紫府无双。果何 人哉?如斯之美也。',1,'2023-04-15 10:34:00','2023-05-30 10:34:54','2023-05-30 11:04:50',0,1663340942339338242,1663345120033869825),(1663378018480877569,'《春夜即事》\n霞绡云幄任铺陈，隔巷蟆更听未真。\n枕上轻寒窗外雨，眼前春色梦中人。\n盈盈烛泪因谁泣，点点花愁为我嗔。\n自是小鬟娇懒惯，拥衾不耐笑言频。',1,'2023-05-15 10:53:00','2023-05-30 10:53:18','2023-05-30 11:04:56',0,1663340942339338242,1663345120033869825),(1663382115045011457,'《更香谜》\n朝罢谁携两袖烟，琴边衾里总无缘。 \n晓筹不用鸡人报，五夜无烦侍女添。\n焦首朝朝还暮暮，煎心日日复年年。 \n光阴荏苒须当惜，风雨阴晴任变迁。',1,'2023-02-17 11:09:00','2023-05-30 11:09:35','2023-05-30 11:20:01',0,1663379813617889281,1663345120033869825),(1663382700087504897,'《白海棠咏》\n珍重芳姿昼掩门，自携手瓮灌苔盆。 \n胭脂洗出秋阶影，冰雪招来露砌魂。\n淡极始知花更艳，愁多焉得玉无痕。 \n欲偿白帝凭清洁，不语婷婷日又昏。',1,'2023-03-22 11:11:00','2023-05-30 11:11:55','2023-05-30 11:20:09',0,1663379813617889281,1663345120033869825),(1663383228724998145,'《忆菊》\n怅望西风抱闷思，蓼红苇白断肠时。\n空篱旧圃秋无迹，瘦月清霜梦有知。\n念念心随归雁远，寥寥坐听晚砧痴。\n谁怜为我黄花病，慰语重阳会有期。',1,'2023-04-01 11:14:00','2023-05-30 11:14:01','2023-05-30 11:20:14',0,1663379813617889281,1663345120033869825),(1663384004071452674,'《螃蟹咏》\n桂霭桐阴坐举觞，长安涎口盼重阳。\n眼前道路无经纬，皮里春秋空黑黄。\n酒未敌腥还用菊，性防积冷定需姜。\n于今落釜成何益？月浦空余禾黍香。',1,'2023-05-25 11:17:00','2023-05-30 11:17:05','2023-05-30 11:20:17',0,1663379813617889281,1663345120033869825),(1663384564266889217,'《临江仙•柳絮》\n        白玉堂前春解舞，东风卷得均匀。 蜂团蝶阵乱纷纷。 几曾随逝水，岂必委芳尘。\n　　万缕千丝终不改，任他随聚随分。 韶华休笑本无根， 好风凭借力，送我上青云！',1,'2023-05-15 11:19:00','2023-05-30 11:19:19','2023-05-30 11:20:23',0,1663379813617889281,1663345120033869825),(1663389135986946049,'《葬花辞》\n花谢花飞花满天，红消香断有谁怜？\n游丝软系飘春树，落絮轻沾扑绣帘。\n闺中女儿惜春暮，愁绪满怀无释处。\n手把花锄出绣闺，忍踏落花来复去？\n柳丝榆英自芳菲，不管桃飘与李飞。\n桃李明年能再发，明年闺中知有谁？\n三月香巢初垒成，梁间燕子太无情。\n明年花发虽可啄，却不道人去梁空巢也倾。\n一年三百六十日，风刀霜剑严相逼。\n明媚鲜研能几时，一朝飘泊难寻觅。\n花开易见落难寻，阶前愁杀葬花人。\n独倚花锄泪暗洒，洒上空枝见血痕。\n杜鹃无语正黄昏，荷锄归去掩重门。\n青灯照壁入初睡，冷雨敲窗被未温。\n怪侬底事倍伤神，半为怜春半恼春：\n怜春忽至恼忽去，至又无言去不闻。\n昨宵庭外悲歌发，知是花魂与鸟魂？\n花魂鸟魂总难留，鸟自无言花自羞。\n愿侬胁下生双翼，随花飞到天尽头。\n天尽头,何处有香丘？\n未若锦囊收艳骨，一抔净土掩风流。\n质本洁来还洁去，强于污淖陷渠沟。\n尔今死去侬收葬，未卜侬身何日丧？\n侬今葬花人笑痴，他年葬侬知是谁？\n试看春残花渐落，便是红颜老死时。\n一朝春尽红颜老，花落人亡两不知！',1,'2023-02-22 11:37:00','2023-05-30 11:37:29','2023-05-30 11:47:40',0,1663387580080582658,1663345120033869825),(1663389668130877441,'《秋窗风雨夕》\n秋花惨淡秋草黄,耿耿秋灯秋夜长。\n已觉秋窗秋不尽,那堪风雨助凄凉！\n助秋风雨来何速!惊破秋窗秋梦绿。\n抱得秋情不忍眠,自向秋屏移泪烛。\n泪烛摇摇若短檠,牵愁照恨动离情。\n谁家秋院无风入? 何处秋窗无雨声？\n罗衾不奈秋风力,残漏声催秋雨急。\n连宵脉脉复飕飕,灯前似伴离人泣。\n寒烟小院转萧条,疏竹虚窗时滴沥。\n不知风雨几时休,已教泪洒窗纱湿。',1,'2023-03-03 11:39:00','2023-05-30 11:39:36','2023-05-30 11:47:45',0,1663387580080582658,1663345120033869825),(1663389932195868673,'《咏白海棠》\n半卷湘帘半掩门，碾冰为土玉为盆。\n偷来梨蕊三分白，借得梅花一缕魂。\n月窟仙人缝缟抉，秋闺怨女拭啼痕。\n娇羞默默同谁诉，倦倚西风夜已昏。',1,'2023-04-04 11:40:00','2023-05-30 11:40:39','2023-05-30 11:47:50',0,1663387580080582658,1663345120033869825),(1663390359117295617,'《咏菊》\n无赖诗魔昏晓侵，绕篱欹石自沉音。\n毫端蕴秀临霜写，口角噙香对月吟。\n满纸自怜题素怨，片言谁解诉秋心。\n一从陶令平章后，千古高风说到今。',1,'2023-05-05 11:42:00','2023-05-30 11:42:21','2023-05-30 11:47:54',0,1663387580080582658,1663345120033869825),(1663391058559430657,'《世外仙源匾额》\n名园筑何处,仙境别红尘。\n借得山川秀,添来景物新。\n香融金谷酒,花媚玉堂人。\n何幸邀恩宠,宫车过往频。',1,'2023-05-25 11:45:00','2023-05-30 11:45:07','2023-05-30 11:47:59',0,1663387580080582658,1663345120033869825),(1663391618838753281,'《杏帘在望》\n杏帘招客饮,在望有山庄。\n菱荇鹅儿水,桑榆燕子梁。\n一畦春韭绿,十里稻花香。\n盛世无饥馁,何须耕织忙。',1,'2023-04-18 11:47:00','2023-05-30 11:47:21','2023-05-30 11:48:04',0,1663387580080582658,1663345120033869825);
/*!40000 ALTER TABLE `moment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moment_comment`
--

DROP TABLE IF EXISTS `moment_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `moment_comment` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` varchar(1024) NOT NULL COMMENT '评论内容',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '是否删除：[0]否 [1]是',
  `moment_id` bigint unsigned NOT NULL COMMENT '动态 ID',
  `source_user_id` bigint unsigned NOT NULL COMMENT '评论者用户 ID',
  `target_user_id` bigint unsigned NOT NULL COMMENT '被回复者用户 ID',
  `parent_id` bigint unsigned DEFAULT NULL COMMENT '父级评论 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1658460246680494082 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moment_comment`
--

LOCK TABLES `moment_comment` WRITE;
/*!40000 ALTER TABLE `moment_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `moment_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moment_like`
--

DROP TABLE IF EXISTS `moment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `moment_like` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '点赞用户用户名',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '点赞用户昵称',
  `avatar` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '点赞用户头像图片地址',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '是否删除：[0]否 [1]是',
  `user_id` bigint unsigned NOT NULL COMMENT '动态点赞用户 ID',
  `moment_id` bigint unsigned NOT NULL COMMENT '动态 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1663423186970013699 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moment_like`
--

LOCK TABLES `moment_like` WRITE;
/*!40000 ALTER TABLE `moment_like` DISABLE KEYS */;
INSERT INTO `moment_like` VALUES (1663423186970013698,'jiabaoyu','怡红院-贾宝玉','http://rveziz3xc.hn-bkt.clouddn.com/img/avatar/2023/05/30/047b2559-59a4-4169-9d82-a7c7778dc6b2.jpeg','2023-05-30 13:52:47','2023-05-30 13:52:47',0,1663340942339338242,1663391058559430657);
/*!40000 ALTER TABLE `moment_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moment_photo`
--

DROP TABLE IF EXISTS `moment_photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `moment_photo` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动态图片地址',
  `created` datetime NOT NULL COMMENT '创建时间',
  `modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint unsigned NOT NULL COMMENT '是否删除：[0]否 [1]是',
  `moment_id` bigint unsigned NOT NULL COMMENT '动态 ID',
  `uploader_user_id` bigint unsigned NOT NULL COMMENT '发布者用户 ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1663391618889084935 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moment_photo`
--

LOCK TABLES `moment_photo` WRITE;
/*!40000 ALTER TABLE `moment_photo` DISABLE KEYS */;
INSERT INTO `moment_photo` VALUES (1663372336243339266,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/f4410bcf-9c34-4800-9158-8ee393aa4cbd.webp','2023-05-30 10:30:44','2023-05-30 10:35:30',1,1663372336125898754,1663340942339338242),(1663372903841722369,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/a3fb2730-7654-4500-9069-fe77f08d1777.jpeg','2023-05-30 10:32:59','2023-05-30 10:32:59',0,1663372903820750850,1663340942339338242),(1663372903841722370,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/e662fe9c-2ed6-4f65-9108-733ef1deaf1f.jpeg','2023-05-30 10:32:59','2023-05-30 10:32:59',0,1663372903820750850,1663340942339338242),(1663372903841722371,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/c4ebf5de-d2be-4989-b25d-d7c833ffb778.webp','2023-05-30 10:32:59','2023-05-30 10:32:59',0,1663372903820750850,1663340942339338242),(1663373386744524801,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/0c06880f-0cdd-4e42-83a2-f5b4605467bb.webp','2023-05-30 10:34:54','2023-05-30 10:34:54',0,1663373386681610242,1663340942339338242),(1663373386744524802,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/ea06d2ed-0532-4828-bb44-229e3ddbd5fd.jpeg','2023-05-30 10:34:54','2023-05-30 10:34:54',0,1663373386681610242,1663340942339338242),(1663373386744524803,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/01bbe7d4-b661-4211-a999-a313bde984a6.jpeg','2023-05-30 10:34:54','2023-05-30 10:34:54',0,1663373386681610242,1663340942339338242),(1663373386744524804,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/cba4b665-0772-4408-a13a-a37eb6fd8d72.webp','2023-05-30 10:34:54','2023-05-30 10:34:54',0,1663373386681610242,1663340942339338242),(1663373540235079681,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/f4410bcf-9c34-4800-9158-8ee393aa4cbd.webp','2023-05-30 10:35:31','2023-05-30 10:35:31',0,1663372336125898754,1663340942339338242),(1663378018560569346,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/b0e1577a-f1ab-4609-add8-c609df5edb87.jpeg','2023-05-30 10:53:18','2023-05-30 10:53:32',1,1663378018480877569,1663340942339338242),(1663378076244832258,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/b0e1577a-f1ab-4609-add8-c609df5edb87.jpeg','2023-05-30 10:53:32','2023-05-30 10:53:32',0,1663378018480877569,1663340942339338242),(1663382115112120322,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/b8dc71fc-8c64-44ab-9a59-00693b07aff9.webp','2023-05-30 11:09:35','2023-05-30 11:09:35',0,1663382115045011457,1663379813617889281),(1663382700150419457,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/cff6ef52-bf56-4e22-9be7-fe19e83a78d0.webp','2023-05-30 11:11:55','2023-05-30 11:11:55',0,1663382700087504897,1663379813617889281),(1663382700150419458,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/f3398d69-ca3f-4147-93cf-7bcfcd78b47b.webp','2023-05-30 11:11:55','2023-05-30 11:11:55',0,1663382700087504897,1663379813617889281),(1663383228724998146,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/3adb6cc4-eaa7-41c9-9fb2-99fdafb3932a.jpeg','2023-05-30 11:14:01','2023-05-30 11:14:01',0,1663383228724998145,1663379813617889281),(1663383228787912705,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/1fb07e17-9eec-4a24-a6b1-5c6c9f13c62e.webp','2023-05-30 11:14:01','2023-05-30 11:14:01',0,1663383228724998145,1663379813617889281),(1663383228787912706,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/13946f90-b105-4a24-b7f6-83c216aecc34.webp','2023-05-30 11:14:01','2023-05-30 11:14:01',0,1663383228724998145,1663379813617889281),(1663384004125978625,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/8dd092c3-f6ba-4f78-bfc5-8206480fdc64.jpeg','2023-05-30 11:17:05','2023-05-30 11:17:05',0,1663384004071452674,1663379813617889281),(1663384004125978626,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/de4c3701-78d6-4d5c-a41e-28651be2d216.webp','2023-05-30 11:17:05','2023-05-30 11:17:05',0,1663384004071452674,1663379813617889281),(1663384004125978627,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/63141aae-d5b3-4327-aab4-46e0d882aa3f.jpeg','2023-05-30 11:17:05','2023-05-30 11:17:05',0,1663384004071452674,1663379813617889281),(1663384004125978628,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/e2e5aa2e-7054-4fcd-828d-1add2f0ec01d.webp','2023-05-30 11:17:05','2023-05-30 11:17:05',0,1663384004071452674,1663379813617889281),(1663384564329803777,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/0d83fb3f-48c8-4002-82b9-73ef86464928.jpeg','2023-05-30 11:19:19','2023-05-30 11:19:28',1,1663384564266889217,1663379813617889281),(1663384564329803778,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/7dad7fd9-644c-46bc-baf3-8954da4cfccf.webp','2023-05-30 11:19:19','2023-05-30 11:19:28',1,1663384564266889217,1663379813617889281),(1663384564329803779,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/164c2eb5-4276-44db-9877-3fc1ad27b060.jpeg','2023-05-30 11:19:19','2023-05-30 11:19:28',1,1663384564266889217,1663379813617889281),(1663384564329803780,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/48836861-6858-43bc-838c-a7a25f4716ee.jpeg','2023-05-30 11:19:19','2023-05-30 11:19:28',1,1663384564266889217,1663379813617889281),(1663384564329803781,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/49470f77-e1ca-45cb-90b4-84c495cf1234.jpeg','2023-05-30 11:19:19','2023-05-30 11:19:28',1,1663384564266889217,1663379813617889281),(1663384605601755137,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/0d83fb3f-48c8-4002-82b9-73ef86464928.jpeg','2023-05-30 11:19:29','2023-05-30 11:19:29',0,1663384564266889217,1663379813617889281),(1663384605601755138,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/7dad7fd9-644c-46bc-baf3-8954da4cfccf.webp','2023-05-30 11:19:29','2023-05-30 11:19:29',0,1663384564266889217,1663379813617889281),(1663384605601755139,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/164c2eb5-4276-44db-9877-3fc1ad27b060.jpeg','2023-05-30 11:19:29','2023-05-30 11:19:29',0,1663384564266889217,1663379813617889281),(1663384605601755140,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/48836861-6858-43bc-838c-a7a25f4716ee.jpeg','2023-05-30 11:19:29','2023-05-30 11:19:29',0,1663384564266889217,1663379813617889281),(1663384605601755141,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/49470f77-e1ca-45cb-90b4-84c495cf1234.jpeg','2023-05-30 11:19:29','2023-05-30 11:19:29',0,1663384564266889217,1663379813617889281),(1663389136049860609,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/0ac5e945-de44-462c-84b5-62ce8908fc59.webp','2023-05-30 11:37:29','2023-05-30 11:37:29',0,1663389135986946049,1663387580080582658),(1663389668223152130,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/a46554d0-3010-4260-9ef3-caa0229b268e.jpeg','2023-05-30 11:39:36','2023-05-30 11:39:36',0,1663389668130877441,1663387580080582658),(1663389668223152131,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/0bb5de53-66f6-4315-a97d-dd5e819f5dc7.jpeg','2023-05-30 11:39:36','2023-05-30 11:39:36',0,1663389668130877441,1663387580080582658),(1663389932258783234,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/17510fc9-4b8c-4571-a55c-5be7c04f37c2.jpeg','2023-05-30 11:40:39','2023-05-30 11:40:39',0,1663389932195868673,1663387580080582658),(1663389932258783235,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/64076e33-44e4-4fb3-97e3-82c496e5e2c9.jpeg','2023-05-30 11:40:39','2023-05-30 11:40:39',0,1663389932195868673,1663387580080582658),(1663389932258783236,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/d979f349-1175-4478-a006-98ecd366e59d.jpeg','2023-05-30 11:40:39','2023-05-30 11:40:39',0,1663389932195868673,1663387580080582658),(1663390359146655746,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/8e3355af-3b85-4390-b7c8-54d539651e1f.webp','2023-05-30 11:42:21','2023-05-30 11:42:21',0,1663390359117295617,1663387580080582658),(1663390359146655747,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/1f94c87b-691b-486e-bfa6-73e43dccc7b6.webp','2023-05-30 11:42:21','2023-05-30 11:42:21',0,1663390359117295617,1663387580080582658),(1663390359146655748,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/c4f9e5b8-d20d-4ad3-bdda-e2606e7a63bf.webp','2023-05-30 11:42:21','2023-05-30 11:42:21',0,1663390359117295617,1663387580080582658),(1663390359146655749,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/cb160009-9e17-4bd2-bbf6-d37a385ae3ad.webp','2023-05-30 11:42:21','2023-05-30 11:42:21',0,1663390359117295617,1663387580080582658),(1663391058639122433,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/ec0eabce-ec78-422b-99a6-b736146dae88.jpeg','2023-05-30 11:45:07','2023-05-30 11:45:07',0,1663391058559430657,1663387580080582658),(1663391058693648386,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/610c31da-8bb1-4bf0-815d-ca7a3ce91947.webp','2023-05-30 11:45:07','2023-05-30 11:45:07',0,1663391058559430657,1663387580080582658),(1663391058693648387,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/0b276902-d423-450b-aaa4-8841f3d658dd.webp','2023-05-30 11:45:07','2023-05-30 11:45:07',0,1663391058559430657,1663387580080582658),(1663391058693648388,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/7592be13-58f0-4883-8e3d-90687f9eb810.webp','2023-05-30 11:45:07','2023-05-30 11:45:07',0,1663391058559430657,1663387580080582658),(1663391058693648389,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/b6efb699-f483-4405-8c40-03253f30350d.webp','2023-05-30 11:45:07','2023-05-30 11:45:07',0,1663391058559430657,1663387580080582658),(1663391618889084929,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/46ee6f1e-469d-4039-9bc4-3afade7d009c.jpeg','2023-05-30 11:47:21','2023-05-30 11:47:21',0,1663391618838753281,1663387580080582658),(1663391618889084930,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/8eaa5158-df20-4dca-ab81-aa927cab9767.webp','2023-05-30 11:47:21','2023-05-30 11:47:21',0,1663391618838753281,1663387580080582658),(1663391618889084931,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/1e6ff284-2d41-40f8-8e02-a2a5ba7b2805.webp','2023-05-30 11:47:21','2023-05-30 11:47:21',0,1663391618838753281,1663387580080582658),(1663391618889084932,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/71562cb3-66db-4292-a0bd-03ecfdd9190b.webp','2023-05-30 11:47:21','2023-05-30 11:47:21',0,1663391618838753281,1663387580080582658),(1663391618889084933,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/69f87d59-3ed6-4369-a24a-18fcbfba069c.webp','2023-05-30 11:47:21','2023-05-30 11:47:21',0,1663391618838753281,1663387580080582658),(1663391618889084934,'http://rveziz3xc.hn-bkt.clouddn.com/img/moment/2023/05/30/ceaebfb9-e555-4a5a-9dea-88fd534320ef.jpeg','2023-05-30 11:47:21','2023-05-30 11:47:21',0,1663391618838753281,1663387580080582658);
/*!40000 ALTER TABLE `moment_photo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-30 22:37:35
