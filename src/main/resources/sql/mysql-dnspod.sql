-- MySQL dump 10.13  Distrib 5.7.33, for Win64 (x86_64)
--
-- Host: localhost    Database: dnspod
-- ------------------------------------------------------
-- Server version	5.7.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dns_pod_log`
--

DROP TABLE IF EXISTS `dns_pod_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dns_pod_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `domain_id` int(11) DEFAULT NULL COMMENT '域名id',
  `sub_domain_id` int(11) DEFAULT NULL COMMENT '子域名id',
  `domain_value` varchar(255) DEFAULT NULL COMMENT '域名',
  `result` int(2) DEFAULT NULL COMMENT '结果状态',
  `message` varchar(255) DEFAULT NULL COMMENT '返回结果信息',
  `old_ip_address` varchar(100) DEFAULT NULL COMMENT '原ip地址',
  `new_ip_address` varchar(100) DEFAULT NULL COMMENT '新ip地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='动态域名解析日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dns_pod_log`
--

LOCK TABLES `dns_pod_log` WRITE;
/*!40000 ALTER TABLE `dns_pod_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `dns_pod_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domain`
--

DROP TABLE IF EXISTS `domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `domain` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `domain_value` varchar(100) DEFAULT NULL COMMENT '域名',
  `state` int(2) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='动态域名解析域名表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domain`
--

LOCK TABLES `domain` WRITE;
/*!40000 ALTER TABLE `domain` DISABLE KEYS */;
/*!40000 ALTER TABLE `domain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `param_config`
--

DROP TABLE IF EXISTS `param_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `param_config` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_key` varchar(50) NOT NULL COMMENT '参数名',
  `config_value` text COMMENT '参数值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `state` int(2) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_config_key` (`config_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `param_config`
--

LOCK TABLES `param_config` WRITE;
/*!40000 ALTER TABLE `param_config` DISABLE KEYS */;
INSERT INTO `param_config` VALUES (1,'token','12345,7676f344eaeaea9074c123451234512d','DNSPOD域名解析Token',0,'2023-05-04 09:53:08','2023-05-04 10:36:03'),(2,'cron','0 0 0/3 * * ?','cron表达式',0,'2023-05-04 09:53:23','2023-05-04 11:44:20');
/*!40000 ALTER TABLE `param_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_domain`
--

DROP TABLE IF EXISTS `sub_domain`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_domain` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `domain_id` int(11) NOT NULL COMMENT '域名id',
  `sub_domain_value` varchar(100) NOT NULL COMMENT '子域名',
  `state` int(2) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='动态域名解析子域名表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_domain`
--

LOCK TABLES `sub_domain` WRITE;
/*!40000 ALTER TABLE `sub_domain` DISABLE KEYS */;
/*!40000 ALTER TABLE `sub_domain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'dnspod'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-04 11:45:32
