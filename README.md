# 一、介绍
本项目是利用DNSPod实现动态域名解析，采用**SpringBoot**整合**quartz**框架实现定时任务，定时执行获取本机的外网IP地址，对比DNSPod所记录的IP地址，如果不一致，则调用DNSPod的Api接口修记录的IP地址，来实现动态域名解析

# 二、部署步骤
### 1.创建数据库
本项目使用的Mysql作为数据库，首先在mysql新建一个数据库，如 **“dnspod”**，执行以下SQL语句创建表：

系统配置表（param_config）
``` mysql

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

INSERT INTO `param_config` VALUES (1,'token','12345,7676f344eaeaea9074c123451234512d','DNSPOD域名解析Token',0,'2023-05-04 09:53:08','2023-05-04 10:36:03'),(2,'cron','0 0 0/3 * * ?','cron表达式',0,'2023-05-04 09:53:23','2023-05-04 11:44:20');

```

域名表（domain）
``` mysql

CREATE TABLE `domain` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `domain_value` varchar(100) DEFAULT NULL COMMENT '域名',
  `state` int(2) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='动态域名解析域名表';

```

子域名表（sub_domain）
``` mysql

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

```

域名解析日志表（dns_pod_log）
``` mysql

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

```

### 2.运行项目
通过maven打包，打包成war包，放到tomcat的**webapps**路径下，运行tomcat服务器，即可运行项目。
访问地址：`http://localhost:8080/dnspod`
页面效果展示：
![](https://raw.githubusercontent.com/wei-xuan-4919/DNSPod/master/images/index.png)
