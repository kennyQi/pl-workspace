/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50621
Source Host           : 127.0.0.1:3306
Source Database       : jqlm_dev

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2014-10-11 10:51:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `auth_perm`
-- ----------------------------
DROP TABLE IF EXISTS `AUTH_PERM`;
CREATE TABLE `AUTH_PERM` (
  `ID` varchar(64) NOT NULL,
  `DISPLAY_NAME` varchar(96) DEFAULT NULL,
  `PARENT_ID` varchar(64) DEFAULT NULL,
  `PERM_ROLE` varchar(96) DEFAULT NULL,
  `PERM_TYPE` smallint(6) DEFAULT NULL,
  `URL` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_perm
-- ----------------------------
INSERT INTO `AUTH_PERM` VALUES ('f550a9f5a42d4497821369cba139073c', 'ZX', null, null, '0', 'ZXZZ');

-- ----------------------------
-- Table structure for `auth_role`
-- ----------------------------
DROP TABLE IF EXISTS `AUTH_ROLE`;
CREATE TABLE `AUTH_ROLE` (
  `ID` varchar(64) NOT NULL,
  `DISPLAY_NAME` varchar(96) DEFAULT NULL,
  `ROLE_NAME` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `AUTH_ROLE` VALUES ('1', '管理员', '管理员');
INSERT INTO `AUTH_ROLE` VALUES ('2b2d4d2017b94b3e855856d3c078ac19', '非官方打包', '%￥ERFb');

-- ----------------------------
-- Table structure for `auth_role_perm`
-- ----------------------------
DROP TABLE IF EXISTS `AUTH_ROLE_PERM`;
CREATE TABLE `AUTH_ROLE_PERM` (
  `ROLE_ID` varchar(64) NOT NULL,
  `PERM_ID` varchar(64) NOT NULL,
  PRIMARY KEY (`PERM_ID`,`ROLE_ID`),
  KEY `FK_dr9fy8x239s4ugemwtt4to8mh` (`PERM_ID`),
  KEY `FK_ixwj0owdnv9ykllp46tpa7hun` (`ROLE_ID`),
  CONSTRAINT `FK_dr9fy8x239s4ugemwtt4to8mh` FOREIGN KEY (`PERM_ID`) REFERENCES `AUTH_PERM` (`ID`),
  CONSTRAINT `FK_ixwj0owdnv9ykllp46tpa7hun` FOREIGN KEY (`ROLE_ID`) REFERENCES `AUTH_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_role_perm
-- ----------------------------

-- ----------------------------
-- Table structure for `auth_user`
-- ----------------------------
DROP TABLE IF EXISTS `AUTH_USER`;
CREATE TABLE `AUTH_USER` (
  `ID` varchar(64) NOT NULL,
  `DISPLAY_NAME` varchar(96) DEFAULT NULL,
  `ENABLE` smallint(6) DEFAULT NULL,
  `LOGIN_NAME` varchar(64) DEFAULT NULL,
  `PASSWD` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `AUTH_USER` VALUES ('1', '管理员', '1', 'admin', 'e10adc3949ba59abbe56e057f20f883e');

-- ----------------------------
-- Table structure for `auth_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `AUTH_USER_ROLE`;
CREATE TABLE `AUTH_USER_ROLE` (
  `USER_ID` varchar(64) NOT NULL,
  `ROLE_ID` varchar(64) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`USER_ID`),
  KEY `FK_m2ev8fek3r52p602i5x88amyu` (`ROLE_ID`),
  KEY `FK_c94kw4ij0qgm6g2edbdl1444c` (`USER_ID`),
  CONSTRAINT `FK_c94kw4ij0qgm6g2edbdl1444c` FOREIGN KEY (`USER_ID`) REFERENCES `AUTH_USER` (`ID`),
  CONSTRAINT `FK_m2ev8fek3r52p602i5x88amyu` FOREIGN KEY (`ROLE_ID`) REFERENCES `AUTH_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
INSERT INTO `AUTH_USER_ROLE` VALUES ('1', '1');

-- ----------------------------
-- Table structure for `jqlm_advertisement`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_ADVERTISEMENT`;
CREATE TABLE `JQLM_ADVERTISEMENT` (
  `ID` varchar(64) NOT NULL,
  `CONTENT` text,
  `CREATE_DATE` datetime DEFAULT NULL,
  `OUTERLINK` char(1) DEFAULT NULL,
  `POSITION` varchar(32) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `REMARK` varchar(1024) DEFAULT NULL,
  `SORT_NUM` int(11) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `TITLE` varchar(256) DEFAULT NULL,
  `URL` varchar(512) DEFAULT NULL,
  `IMAGE_ID` varchar(64) DEFAULT NULL,
  `SCENIC_SPOT_WEB_PORTAL_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_q9rld6y2wyr98tw42q9j3trv2` (`IMAGE_ID`),
  KEY `FK_8em1san1f0u1huxf7l763ffg3` (`SCENIC_SPOT_WEB_PORTAL_ID`),
  CONSTRAINT `FK_8em1san1f0u1huxf7l763ffg3` FOREIGN KEY (`SCENIC_SPOT_WEB_PORTAL_ID`) REFERENCES `JQLM_WEB_PORTAL` (`ID`),
  CONSTRAINT `FK_q9rld6y2wyr98tw42q9j3trv2` FOREIGN KEY (`IMAGE_ID`) REFERENCES `SYS_IMAGE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_advertisement
-- ----------------------------

-- ----------------------------
-- Table structure for `jqlm_advertisement_image`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_ADVERTISEMENT_IMAGE`;
CREATE TABLE `JQLM_ADVERTISEMENT_IMAGE` (
  `ADVERTISEMENT_ID` varchar(64) NOT NULL,
  `IMAGE_ID` varchar(64) NOT NULL,
  UNIQUE KEY `UK_908hcu70cq6ufwcxaomcd1py3` (`IMAGE_ID`),
  KEY `FK_908hcu70cq6ufwcxaomcd1py3` (`IMAGE_ID`),
  KEY `FK_buyev73nrqq3gof3r41diuff0` (`ADVERTISEMENT_ID`),
  CONSTRAINT `FK_908hcu70cq6ufwcxaomcd1py3` FOREIGN KEY (`IMAGE_ID`) REFERENCES `SYS_IMAGE` (`ID`),
  CONSTRAINT `FK_buyev73nrqq3gof3r41diuff0` FOREIGN KEY (`ADVERTISEMENT_ID`) REFERENCES `JQLM_ADVERTISEMENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_advertisement_image
-- ----------------------------

-- ----------------------------
-- Table structure for `jqlm_article`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_ARTICLE`;
CREATE TABLE `JQLM_ARTICLE` (
  `ID` varchar(64) NOT NULL,
  `CONTENT` text,
  `createDate` datetime DEFAULT NULL,
  `CURRENT_STATUS` int(11) DEFAULT NULL,
  `title` varchar(64) DEFAULT NULL,
  `WEB_PORTAL_ADMIN_ID` varchar(64) DEFAULT NULL,
  `CONTENT_CHANNEL_ID` varchar(64) DEFAULT NULL,
  `WEB_PORTAL_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_57kjtgaqhg1tv5q9u814r4fc4` (`WEB_PORTAL_ADMIN_ID`),
  KEY `FK_g5petbk7u6f393vqcaumw7uwd` (`CONTENT_CHANNEL_ID`),
  KEY `FK_72jgvtytcmdwkqlm8amk6pqe4` (`WEB_PORTAL_ID`),
  CONSTRAINT `FK_57kjtgaqhg1tv5q9u814r4fc4` FOREIGN KEY (`WEB_PORTAL_ADMIN_ID`) REFERENCES `JQLM_WEB_PORTAL_ADMIN` (`ID`),
  CONSTRAINT `FK_72jgvtytcmdwkqlm8amk6pqe4` FOREIGN KEY (`WEB_PORTAL_ID`) REFERENCES `JQLM_WEB_PORTAL` (`ID`),
  CONSTRAINT `FK_g5petbk7u6f393vqcaumw7uwd` FOREIGN KEY (`CONTENT_CHANNEL_ID`) REFERENCES `JQLM_CONTENT_CHANNEL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_article
-- ----------------------------

-- ----------------------------
-- Table structure for `jqlm_article_image`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_ARTICLE_IMAGE`;
CREATE TABLE `JQLM_ARTICLE_IMAGE` (
  `ARTICLE_ID` varchar(64) NOT NULL,
  `IMAGE_ID` varchar(64) NOT NULL,
  UNIQUE KEY `UK_64e3dschcpma5yp57fyp8l2a3` (`IMAGE_ID`),
  KEY `FK_64e3dschcpma5yp57fyp8l2a3` (`IMAGE_ID`),
  KEY `FK_hs4m5ukbr7y39pv0905o9h67` (`ARTICLE_ID`),
  CONSTRAINT `FK_64e3dschcpma5yp57fyp8l2a3` FOREIGN KEY (`IMAGE_ID`) REFERENCES `SYS_IMAGE` (`ID`),
  CONSTRAINT `FK_hs4m5ukbr7y39pv0905o9h67` FOREIGN KEY (`ARTICLE_ID`) REFERENCES `JQLM_ARTICLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_article_image
-- ----------------------------

-- ----------------------------
-- Table structure for `jqlm_content_channel`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_CONTENT_CHANNEL`;
CREATE TABLE `JQLM_CONTENT_CHANNEL` (
  `ID` varchar(64) NOT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `ARTICLE_COUNT` int(11) DEFAULT NULL,
  `HIDDEN` char(1) DEFAULT NULL,
  `PARENT_CHANNEL_ID` varchar(64) DEFAULT NULL,
  `WEB_PORTAL_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_gxenao6glr13figeqype653h1` (`PARENT_CHANNEL_ID`),
  KEY `FK_eelrk3borwy812rronpjsvnjt` (`WEB_PORTAL_ID`),
  CONSTRAINT `FK_eelrk3borwy812rronpjsvnjt` FOREIGN KEY (`WEB_PORTAL_ID`) REFERENCES `JQLM_WEB_PORTAL` (`ID`),
  CONSTRAINT `FK_gxenao6glr13figeqype653h1` FOREIGN KEY (`PARENT_CHANNEL_ID`) REFERENCES `JQLM_CONTENT_CHANNEL` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_content_channel
-- ----------------------------

-- ----------------------------
-- Table structure for `jqlm_scenic_spot`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_SCENIC_SPOT`;
CREATE TABLE `JQLM_SCENIC_SPOT` (
  `ID` varchar(64) NOT NULL,
  `CONTACT_ADDRESS` varchar(1024) DEFAULT NULL,
  `CONTACT_EMAIL` varchar(128) DEFAULT NULL,
  `CONTACT_FAX` varchar(32) DEFAULT NULL,
  `CONTACT_LINK_MAN` varchar(64) DEFAULT NULL,
  `CONTACT_MOBILE` varchar(32) DEFAULT NULL,
  `CONTACT_TEL` varchar(32) DEFAULT NULL,
  `REMOVED` char(1) DEFAULT NULL,
  `SCENICSPOT_ALIAS` varchar(128) DEFAULT NULL,
  `SCENICSPOT_GRADE` varchar(64) DEFAULT NULL,
  `SCENICSPOT_INTRO` varchar(4000) DEFAULT NULL,
  `SCENICSPOT_NAME` varchar(128) DEFAULT NULL,
  `SCENICSPOT_ADDRESS` varchar(1024) DEFAULT NULL,
  `CITY_CODE` varchar(64) DEFAULT NULL,
  `PROVINCE_CODE` varchar(64) DEFAULT NULL,
  `TRAFFIC` varchar(4000) DEFAULT NULL,
  `SCENICSPOT_IMAGE` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_3oc41kfgwqirxg7bcxpsuu0x5` (`SCENICSPOT_IMAGE`),
  CONSTRAINT `FK_3oc41kfgwqirxg7bcxpsuu0x5` FOREIGN KEY (`SCENICSPOT_IMAGE`) REFERENCES `SYS_IMAGE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_scenic_spot
-- ----------------------------
INSERT INTO `JQLM_SCENIC_SPOT` VALUES ('01e6dfbe3f1a4715858c62d5c815905f', '测试景区2', null, ',', '测试景区2', '15868870214', '0570,7011800', 'Y', null, null, null, '测试景区2', null, '53', '3', null, null);
INSERT INTO `JQLM_SCENIC_SPOT` VALUES ('47421cc1cf834f8194f91417b6870cd0', '测试联系方式1', null, ',', '测试联系人1', '15868870214', '0570,7011800', 'N', null, null, null, '测试景区1', null, '70', '5', null, null);
INSERT INTO `JQLM_SCENIC_SPOT` VALUES ('7cb2f5eb0a564758aef0aeb58a71f5db', '测试景区3', null, ',', '测试景区3', '15868870214', '0570,15868870214', 'N', null, null, null, '测试景区3', null, '53', '3', null, null);
INSERT INTO `JQLM_SCENIC_SPOT` VALUES ('c4cb79ab595041c1bd83dcf149f739b6', null, 'r@qq.com', null, 'zzb', '15868870214', null, 'Y', null, null, null, '入驻景区测试名称1', null, null, null, null, null);
INSERT INTO `JQLM_SCENIC_SPOT` VALUES ('e3eac51ad71948df98ff54f195e47ba4', '测试景区地址5', 's@qq.com', '222,7011800', '测试景区联系人5', '15868870214', '110,7011800', 'N', null, '4A', '222', '222', '测试景区地址5', '56', '4', '222', null);

-- ----------------------------
-- Table structure for `jqlm_scenic_spot_image`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_SCENIC_SPOT_IMAGE`;
CREATE TABLE `JQLM_SCENIC_SPOT_IMAGE` (
  `SCENIC_SPOT_ID` varchar(64) NOT NULL,
  `IMAGE_ID` varchar(64) NOT NULL,
  UNIQUE KEY `UK_ahs1m2odwrdecuvhmtca6m5fo` (`IMAGE_ID`),
  KEY `FK_ahs1m2odwrdecuvhmtca6m5fo` (`IMAGE_ID`),
  KEY `FK_anychqenoiubyfs0d5c1085q3` (`SCENIC_SPOT_ID`),
  CONSTRAINT `FK_ahs1m2odwrdecuvhmtca6m5fo` FOREIGN KEY (`IMAGE_ID`) REFERENCES `SYS_IMAGE` (`ID`),
  CONSTRAINT `FK_anychqenoiubyfs0d5c1085q3` FOREIGN KEY (`SCENIC_SPOT_ID`) REFERENCES `JQLM_SCENIC_SPOT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_scenic_spot_image
-- ----------------------------

-- ----------------------------
-- Table structure for `jqlm_senery`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_SENERY`;
CREATE TABLE `JQLM_SENERY` (
  `ID` varchar(64) NOT NULL,
  `ADDRESS` varchar(256) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `EMAIL` varchar(256) DEFAULT NULL,
  `INTRO` text,
  `NAME` varchar(64) DEFAULT NULL,
  `REMOVED` char(1) DEFAULT NULL,
  `TEL` varchar(32) DEFAULT NULL,
  `COVER_IMAGE_ID` varchar(64) DEFAULT NULL,
  `SCENIC_SPOT_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_s1uqv6e7bjwial0gt8ylrohns` (`COVER_IMAGE_ID`),
  KEY `FK_2ko3ia08lf4dm1pbnvhcb61e0` (`SCENIC_SPOT_ID`),
  CONSTRAINT `FK_2ko3ia08lf4dm1pbnvhcb61e0` FOREIGN KEY (`SCENIC_SPOT_ID`) REFERENCES `JQLM_SCENIC_SPOT` (`ID`),
  CONSTRAINT `FK_s1uqv6e7bjwial0gt8ylrohns` FOREIGN KEY (`COVER_IMAGE_ID`) REFERENCES `SYS_IMAGE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_senery
-- ----------------------------

-- ----------------------------
-- Table structure for `jqlm_senery_image`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_SENERY_IMAGE`;
CREATE TABLE `JQLM_SENERY_IMAGE` (
  `SENERY_ID` varchar(64) NOT NULL,
  `IMAGE_ID` varchar(64) NOT NULL,
  UNIQUE KEY `UK_kmvq0ag7l7asq0mto0nqh4kv8` (`IMAGE_ID`),
  KEY `FK_kmvq0ag7l7asq0mto0nqh4kv8` (`IMAGE_ID`),
  KEY `FK_rg2eq7nh416gydlg2b0ho16f5` (`SENERY_ID`),
  CONSTRAINT `FK_kmvq0ag7l7asq0mto0nqh4kv8` FOREIGN KEY (`IMAGE_ID`) REFERENCES `SYS_IMAGE` (`ID`),
  CONSTRAINT `FK_rg2eq7nh416gydlg2b0ho16f5` FOREIGN KEY (`SENERY_ID`) REFERENCES `JQLM_SENERY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_senery_image
-- ----------------------------

-- ----------------------------
-- Table structure for `jqlm_web_portal`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_WEB_PORTAL`;
CREATE TABLE `JQLM_WEB_PORTAL` (
  `ID` varchar(64) NOT NULL,
  `ADD_DATE` datetime DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `REMOVED` char(1) DEFAULT NULL,
  `TITLE` varchar(64) DEFAULT NULL,
  `VALID` char(1) DEFAULT NULL,
  `ADMIN_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_g3pcc61860cprntviwwy48gec` (`ADMIN_ID`),
  CONSTRAINT `FK_g3pcc61860cprntviwwy48gec` FOREIGN KEY (`ADMIN_ID`) REFERENCES `JQLM_WEB_PORTAL_ADMIN` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_web_portal
-- ----------------------------
INSERT INTO `JQLM_WEB_PORTAL` VALUES ('01e6dfbe3f1a4715858c62d5c815905f', '2014-09-12 16:57:16', '2014-09-12 16:57:16', 'N', '测试景区2', 'Y', '7b30825811474d6497b0f1f5f4096a17');
INSERT INTO `JQLM_WEB_PORTAL` VALUES ('47421cc1cf834f8194f91417b6870cd0', '2014-09-12 18:39:39', '2014-09-12 16:55:50', 'N', '测试景区1', 'Y', '4e7a2fa5586e4b17976488cca9056715');
INSERT INTO `JQLM_WEB_PORTAL` VALUES ('7cb2f5eb0a564758aef0aeb58a71f5db', '2014-09-12 18:10:59', '2014-09-12 18:10:59', 'N', '测试景区3', 'Y', '96a784ea738d4a91a1dbcf25906d57e9');
INSERT INTO `JQLM_WEB_PORTAL` VALUES ('c4cb79ab595041c1bd83dcf149f739b6', '2014-09-18 17:07:23', '2014-09-18 17:07:23', 'N', '入驻景区测试名称1', 'Y', 'b8d5a2b4207243bebc8e51af83488ca2');
INSERT INTO `JQLM_WEB_PORTAL` VALUES ('e3eac51ad71948df98ff54f195e47ba4', '2014-09-15 09:12:15', '2014-09-15 09:12:15', 'N', '222', 'Y', '1344641724ec40f98bf829093e1b7a12');

-- ----------------------------
-- Table structure for `jqlm_web_portal_admin`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_WEB_PORTAL_ADMIN`;
CREATE TABLE `JQLM_WEB_PORTAL_ADMIN` (
  `ID` varchar(64) NOT NULL,
  `EMAIL` varchar(128) DEFAULT NULL,
  `LOGIN_NAME` varchar(64) DEFAULT NULL,
  `MOBILE` varchar(32) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `PASSWORD` varchar(64) DEFAULT NULL,
  `REMOVED` char(1) DEFAULT NULL,
  `WEB_PORTAL_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_pnn2dg4hgem0x71u21093b2gf` (`WEB_PORTAL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_web_portal_admin
-- ----------------------------
INSERT INTO `JQLM_WEB_PORTAL_ADMIN` VALUES ('1344641724ec40f98bf829093e1b7a12', 's@qq.com', '测试账户5', '15868870214', '测试景区联系人5', 'e10adc3949ba59abbe56e057f20f883e', 'N', 'e3eac51ad71948df98ff54f195e47ba4');
INSERT INTO `JQLM_WEB_PORTAL_ADMIN` VALUES ('4e7a2fa5586e4b17976488cca9056715', null, '测试账户名1', '15868870214', '测试联系人1', 'e10adc3949ba59abbe56e057f20f883e', 'N', '47421cc1cf834f8194f91417b6870cd0');
INSERT INTO `JQLM_WEB_PORTAL_ADMIN` VALUES ('7b30825811474d6497b0f1f5f4096a17', null, 'XNB', '15868870214', '测试景区2', 'e10adc3949ba59abbe56e057f20f883e', 'N', '01e6dfbe3f1a4715858c62d5c815905f');
INSERT INTO `JQLM_WEB_PORTAL_ADMIN` VALUES ('96a784ea738d4a91a1dbcf25906d57e9', null, '测试账户3', '15868870214', '测试景区3', 'e10adc3949ba59abbe56e057f20f883e', 'N', '7cb2f5eb0a564758aef0aeb58a71f5db');
INSERT INTO `JQLM_WEB_PORTAL_ADMIN` VALUES ('b8d5a2b4207243bebc8e51af83488ca2', 'r@qq.com', 'admin', '15868870214', 'zzb', null, 'Y', 'c4cb79ab595041c1bd83dcf149f739b6');

-- ----------------------------
-- Table structure for `jqlm_web_portal_statistics`
-- ----------------------------
DROP TABLE IF EXISTS `JQLM_WEB_PORTAL_STATISTICS`;
CREATE TABLE `JQLM_WEB_PORTAL_STATISTICS` (
  `ID` varchar(64) NOT NULL,
  `UPLOADED_MAX_SIZE` double DEFAULT NULL,
  `UPLOADED_SIZE` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jqlm_web_portal_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_addr_projection`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ADDR_PROJECTION`;
CREATE TABLE `SYS_ADDR_PROJECTION` (
  `ID` varchar(64) NOT NULL,
  `ADDR_CODE` varchar(64) DEFAULT NULL,
  `ADDR_NAME` varchar(64) DEFAULT NULL,
  `ADDR_TYPE` int(11) DEFAULT NULL,
  `CHANNEL_ADDR_CODE` varchar(64) DEFAULT NULL,
  `CHANNEL_TYPE` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_addr_projection
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_album`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ALBUM`;
CREATE TABLE `SYS_ALBUM` (
  `ID` varchar(64) NOT NULL,
  `OWNER_ID` varchar(60) DEFAULT NULL,
  `OWNER_ITEM_ID` varchar(60) DEFAULT NULL,
  `PROJECT_ID` varchar(60) DEFAULT NULL,
  `REMARK` text,
  `TITLE` varchar(512) DEFAULT NULL,
  `USE_TYPE` int(11) DEFAULT NULL,
  `PARENT_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_8b753vf3gtio4lrmthopr5gxf` (`PARENT_ID`),
  CONSTRAINT `FK_8b753vf3gtio4lrmthopr5gxf` FOREIGN KEY (`PARENT_ID`) REFERENCES `SYS_ALBUM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_album
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_area`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AREA`;
CREATE TABLE `SYS_AREA` (
  `ID` varchar(64) NOT NULL,
  `CODE` varchar(8) DEFAULT NULL,
  `NAME` varchar(32) DEFAULT NULL,
  `PARENT` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_area
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_backlog`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_BACKLOG`;
CREATE TABLE `SYS_BACKLOG` (
  `ID` varchar(64) NOT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(1024) DEFAULT NULL,
  `EXECUTE_NUM` int(11) DEFAULT NULL,
  `NAME` varchar(96) DEFAULT NULL,
  `TYPE` varchar(32) DEFAULT NULL,
  `EXECUTE_COUNT` int(11) DEFAULT NULL,
  `SUCCESS` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_backlog
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_backlog_log`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_BACKLOG_LOG`;
CREATE TABLE `SYS_BACKLOG_LOG` (
  `ID` varchar(64) NOT NULL,
  `OPERATE_CONTENT` varchar(255) DEFAULT NULL,
  `OPERATE_DATE` datetime DEFAULT NULL,
  `OPERATE_NUM` int(11) DEFAULT NULL,
  `SUCCESS` tinyint(1) DEFAULT NULL,
  `BACKLOG_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_80e04wx83d6eqkrxlwywlxtng` (`BACKLOG_ID`),
  CONSTRAINT `FK_80e04wx83d6eqkrxlwywlxtng` FOREIGN KEY (`BACKLOG_ID`) REFERENCES `SYS_BACKLOG` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_backlog_log
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_CITY`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_CITY`;
CREATE TABLE `SYS_CITY` (
  `ID` varchar(64) NOT NULL,
  `AIR_CODE` varchar(8) DEFAULT NULL,
  `CITY_AIR_CODE` varchar(8) DEFAULT NULL,
  `CITY_JIAN_PIN` varchar(8) DEFAULT NULL,
  `CITY_QUAN_PIN` varchar(64) DEFAULT NULL,
  `CODE` varchar(8) DEFAULT NULL,
  `NAME` varchar(32) DEFAULT NULL,
  `PARENT` varchar(8) DEFAULT NULL,
  `SORT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_city
-- ----------------------------
INSERT INTO `SYS_CITY` VALUES ('100', null, null, null, null, '100', '崇左', '7', null);
INSERT INTO `SYS_CITY` VALUES ('101', null, null, null, null, '101', '防城港', '7', null);
INSERT INTO `SYS_CITY` VALUES ('102', 'KWL', 'KWL', 'GL', 'GUILIN', '102', '桂林', '7', '1');
INSERT INTO `SYS_CITY` VALUES ('103', null, null, null, null, '103', '贵港', '7', null);
INSERT INTO `SYS_CITY` VALUES ('104', null, null, null, null, '104', '河池', '7', null);
INSERT INTO `SYS_CITY` VALUES ('105', null, null, null, null, '105', '贺州', '7', null);
INSERT INTO `SYS_CITY` VALUES ('106', null, null, null, null, '106', '来宾', '7', null);
INSERT INTO `SYS_CITY` VALUES ('107', 'LZH', 'LZH', 'LZ', 'LIUZHOU', '107', '柳州', '7', '9');
INSERT INTO `SYS_CITY` VALUES ('108', 'NNG', 'NNG', 'NN', 'NANNING', '108', '南宁', '7', '6');
INSERT INTO `SYS_CITY` VALUES ('109', null, null, null, null, '109', '钦州', '7', null);
INSERT INTO `SYS_CITY` VALUES ('110', 'WUZ', 'WUZ', 'WZ', 'CHANGZHOUDAO', '110', '梧州', '7', '10');
INSERT INTO `SYS_CITY` VALUES ('111', null, null, null, null, '111', '玉林', '7', null);
INSERT INTO `SYS_CITY` VALUES ('112', 'AVA', 'AVA', 'AS', 'ANSHUN', '112', '安顺', '8', '6');
INSERT INTO `SYS_CITY` VALUES ('113', null, null, null, null, '113', '毕节', '8', null);
INSERT INTO `SYS_CITY` VALUES ('114', 'KWE', 'KWE', 'GY', 'GUIYANG', '114', '贵阳', '8', '4');
INSERT INTO `SYS_CITY` VALUES ('115', null, null, null, null, '115', '六盘水', '8', null);
INSERT INTO `SYS_CITY` VALUES ('116', null, null, null, null, '116', '黔东南', '8', null);
INSERT INTO `SYS_CITY` VALUES ('117', null, null, null, null, '117', '黔南', '8', null);
INSERT INTO `SYS_CITY` VALUES ('118', null, null, null, null, '118', '黔西南', '8', null);
INSERT INTO `SYS_CITY` VALUES ('119', 'TEN', 'TEN', 'TR', 'TONGREN', '119', '铜仁', '8', '5');
INSERT INTO `SYS_CITY` VALUES ('120', 'ZYI', 'ZYI', 'ZY', 'ZUNYI', '120', '遵义', '8', '7');
INSERT INTO `SYS_CITY` VALUES ('121', null, null, null, null, '121', '白沙', '9', null);
INSERT INTO `SYS_CITY` VALUES ('122', null, null, null, null, '122', '保亭', '9', null);
INSERT INTO `SYS_CITY` VALUES ('123', null, null, null, null, '123', '昌江', '9', null);
INSERT INTO `SYS_CITY` VALUES ('124', null, null, null, null, '124', '澄迈县', '9', null);
INSERT INTO `SYS_CITY` VALUES ('125', null, null, null, null, '125', '定安县', '9', null);
INSERT INTO `SYS_CITY` VALUES ('126', null, null, null, null, '126', '东方', '9', null);
INSERT INTO `SYS_CITY` VALUES ('127', 'HAK', 'HAK', 'HK', 'HAIKOU', '127', '海口', '9', '8');
INSERT INTO `SYS_CITY` VALUES ('128', null, null, null, null, '128', '乐东', '9', null);
INSERT INTO `SYS_CITY` VALUES ('129', null, null, null, null, '129', '临高县', '9', null);
INSERT INTO `SYS_CITY` VALUES ('130', null, null, null, null, '130', '陵水', '9', null);
INSERT INTO `SYS_CITY` VALUES ('131', null, null, null, null, '131', '琼海', '9', null);
INSERT INTO `SYS_CITY` VALUES ('132', null, null, null, null, '132', '琼中', '9', null);
INSERT INTO `SYS_CITY` VALUES ('133', 'SYX', 'SYX', 'SY', 'SANYA', '133', '三亚', '9', '6');
INSERT INTO `SYS_CITY` VALUES ('134', null, null, null, null, '134', '屯昌县', '9', null);
INSERT INTO `SYS_CITY` VALUES ('135', null, null, null, null, '135', '万宁', '9', null);
INSERT INTO `SYS_CITY` VALUES ('136', null, null, null, null, '136', '文昌', '9', null);
INSERT INTO `SYS_CITY` VALUES ('137', null, null, null, null, '137', '五指山', '9', null);
INSERT INTO `SYS_CITY` VALUES ('138', null, null, null, null, '138', '儋州', '9', null);
INSERT INTO `SYS_CITY` VALUES ('139', null, null, null, null, '139', '保定', '10', null);
INSERT INTO `SYS_CITY` VALUES ('140', null, null, null, null, '140', '沧州', '10', null);
INSERT INTO `SYS_CITY` VALUES ('141', null, null, null, null, '141', '承德', '10', null);
INSERT INTO `SYS_CITY` VALUES ('142', 'HDG', 'HDG', 'HD', '邯郸', '142', '邯郸', '10', '2');
INSERT INTO `SYS_CITY` VALUES ('143', null, null, null, null, '143', '衡水', '10', null);
INSERT INTO `SYS_CITY` VALUES ('144', null, null, null, null, '144', '廊坊', '10', null);
INSERT INTO `SYS_CITY` VALUES ('145', 'SHP', 'SHP', 'QHD', 'QINHUANGDAO', '145', '秦皇岛', '10', '2');
INSERT INTO `SYS_CITY` VALUES ('146', 'SJW', 'SJW', 'SJZ', 'SHIJIAZHUANG', '146', '石家庄', '10', '2');
INSERT INTO `SYS_CITY` VALUES ('147', 'TVS', 'TVS', 'TS', 'TANGSHAN', '147', '唐山', '10', '7');
INSERT INTO `SYS_CITY` VALUES ('148', 'XNT', 'XNT', 'XT', 'XINGTAI', '148', '邢台', '10', '6');
INSERT INTO `SYS_CITY` VALUES ('149', null, null, null, null, '149', '张家口', '10', null);
INSERT INTO `SYS_CITY` VALUES ('150', 'AYN', 'AYN', 'AY', 'ANYANG', '150', '安阳', '11', '7');
INSERT INTO `SYS_CITY` VALUES ('151', null, null, null, null, '151', '鹤壁', '11', null);
INSERT INTO `SYS_CITY` VALUES ('152', null, null, null, null, '152', '济源', '11', null);
INSERT INTO `SYS_CITY` VALUES ('153', null, null, null, null, '153', '焦作', '11', null);
INSERT INTO `SYS_CITY` VALUES ('154', null, null, null, null, '154', '开封', '11', null);
INSERT INTO `SYS_CITY` VALUES ('155', 'LYA', 'LYA', 'LY', 'LUOYANG', '155', '洛阳', '11', '5');
INSERT INTO `SYS_CITY` VALUES ('156', 'NNY', 'NNY', 'NY', 'NANYANG', '156', '南阳', '11', '8');
INSERT INTO `SYS_CITY` VALUES ('157', null, null, null, null, '157', '平顶山', '11', null);
INSERT INTO `SYS_CITY` VALUES ('158', null, null, null, null, '158', '三门峡', '11', null);
INSERT INTO `SYS_CITY` VALUES ('159', null, null, null, null, '159', '商丘', '11', null);
INSERT INTO `SYS_CITY` VALUES ('160', null, null, null, null, '160', '新乡', '11', null);
INSERT INTO `SYS_CITY` VALUES ('161', null, null, null, null, '161', '信阳', '11', null);
INSERT INTO `SYS_CITY` VALUES ('162', null, null, null, null, '162', '许昌', '11', null);
INSERT INTO `SYS_CITY` VALUES ('163', 'CGO', 'CGO', 'ZZ', 'ZHENGZHOU', '163', '郑州', '11', '8');
INSERT INTO `SYS_CITY` VALUES ('164', null, null, null, null, '164', '周口', '11', null);
INSERT INTO `SYS_CITY` VALUES ('165', null, null, null, null, '165', '驻马店', '11', null);
INSERT INTO `SYS_CITY` VALUES ('166', null, null, null, null, '166', '漯河', '11', null);
INSERT INTO `SYS_CITY` VALUES ('167', null, null, null, null, '167', '濮阳', '11', null);
INSERT INTO `SYS_CITY` VALUES ('168', 'DQA', 'DQA', 'DQ', 'DAQING', '168', '大庆', '12', '5');
INSERT INTO `SYS_CITY` VALUES ('169', null, null, null, null, '169', '大兴安岭', '12', null);
INSERT INTO `SYS_CITY` VALUES ('170', 'HRB', 'HRB', 'HEB', 'HAERBIN', '170', '哈尔滨', '12', '3');
INSERT INTO `SYS_CITY` VALUES ('171', null, null, null, null, '171', '鹤岗', '12', null);
INSERT INTO `SYS_CITY` VALUES ('172', 'HEK', 'HEK', 'HH', 'HEIHE', '172', '黑河', '12', '5');
INSERT INTO `SYS_CITY` VALUES ('173', 'JXA', 'JXA', 'JX', 'jixi', '173', '鸡西', '12', '9');
INSERT INTO `SYS_CITY` VALUES ('174', 'JMU', 'JMU', 'JMS', 'JIAMUSI', '174', '佳木斯', '12', '5');
INSERT INTO `SYS_CITY` VALUES ('175', 'MDG', 'MDG', 'NDJ', 'MUDANJIANG', '175', '牡丹江', '12', '4');
INSERT INTO `SYS_CITY` VALUES ('176', null, null, null, null, '176', '七台河', '12', null);
INSERT INTO `SYS_CITY` VALUES ('177', 'NDG', 'NDG', 'QQHE', 'QIQIHAER', '177', '齐齐哈尔', '12', '3');
INSERT INTO `SYS_CITY` VALUES ('178', null, null, null, null, '178', '双鸭山', '12', null);
INSERT INTO `SYS_CITY` VALUES ('179', null, null, null, null, '179', '绥化', '12', null);
INSERT INTO `SYS_CITY` VALUES ('180', 'LDS', 'LDS', 'YC', 'YICHUN', '180', '伊春', '12', '4');
INSERT INTO `SYS_CITY` VALUES ('181', null, null, null, null, '181', '鄂州', '13', null);
INSERT INTO `SYS_CITY` VALUES ('182', 'ENH', 'ENH', 'ES', 'ENSHI', '182', '恩施', '13', '2');
INSERT INTO `SYS_CITY` VALUES ('183', null, null, null, null, '183', '黄冈', '13', null);
INSERT INTO `SYS_CITY` VALUES ('184', null, null, null, null, '184', '黄石', '13', null);
INSERT INTO `SYS_CITY` VALUES ('185', null, null, null, null, '185', '荆门', '13', null);
INSERT INTO `SYS_CITY` VALUES ('186', null, null, null, null, '186', '荆州', '13', null);
INSERT INTO `SYS_CITY` VALUES ('187', null, null, null, null, '187', '潜江', '13', null);
INSERT INTO `SYS_CITY` VALUES ('188', null, null, null, null, '188', '神农架林区', '13', null);
INSERT INTO `SYS_CITY` VALUES ('189', null, null, null, null, '189', '十堰', '13', null);
INSERT INTO `SYS_CITY` VALUES ('190', null, null, null, null, '190', '随州', '13', null);
INSERT INTO `SYS_CITY` VALUES ('191', null, null, null, null, '191', '天门', '13', null);
INSERT INTO `SYS_CITY` VALUES ('192', 'WUH', 'WUH', 'WH', 'WUHAN', '192', '武汉', '13', '5');
INSERT INTO `SYS_CITY` VALUES ('193', null, null, null, null, '193', '仙桃', '13', null);
INSERT INTO `SYS_CITY` VALUES ('194', null, null, null, null, '194', '咸宁', '13', null);
INSERT INTO `SYS_CITY` VALUES ('195', 'XFN', 'XFN', 'XY', 'XIANGFAN', '195', '襄阳', '13', '7');
INSERT INTO `SYS_CITY` VALUES ('196', null, null, null, null, '196', '孝感', '13', null);
INSERT INTO `SYS_CITY` VALUES ('197', 'YIH', 'YIH', 'YC', 'YICHANG', '197', '宜昌', '13', '6');
INSERT INTO `SYS_CITY` VALUES ('198', 'CGD', 'CGD', 'CD', 'CHANGDE', '198', '常德', '14', '4');
INSERT INTO `SYS_CITY` VALUES ('199', 'CSX', 'CSX', 'CS', 'CHANGSHA', '199', '长沙', '14', '8');
INSERT INTO `SYS_CITY` VALUES ('200', null, null, null, null, '200', '郴州', '14', null);
INSERT INTO `SYS_CITY` VALUES ('201', 'HNY', 'HNY', 'HY', 'HENGYANG', '201', '衡阳', '14', '12');
INSERT INTO `SYS_CITY` VALUES ('202', 'HJJ', 'HJJ', 'HH', 'ZHIJIANG', '202', '怀化', '14', '6');
INSERT INTO `SYS_CITY` VALUES ('203', null, null, null, null, '203', '娄底', '14', null);
INSERT INTO `SYS_CITY` VALUES ('204', null, null, null, null, '204', '邵阳', '14', null);
INSERT INTO `SYS_CITY` VALUES ('205', null, null, null, null, '205', '湘潭', '14', null);
INSERT INTO `SYS_CITY` VALUES ('206', null, null, null, null, '206', '湘西', '14', null);
INSERT INTO `SYS_CITY` VALUES ('207', null, null, null, null, '207', '益阳', '14', null);
INSERT INTO `SYS_CITY` VALUES ('208', 'LLF', 'LLF', 'YZ', 'yongzhou', '208', '永州', '14', '11');
INSERT INTO `SYS_CITY` VALUES ('209', null, null, null, null, '209', '岳阳', '14', null);
INSERT INTO `SYS_CITY` VALUES ('210', 'DYG', 'DYG', 'ZJJ', 'ZHANGJIAJIE', '210', '张家界', '14', '3');
INSERT INTO `SYS_CITY` VALUES ('211', null, null, null, null, '211', '株洲', '14', null);
INSERT INTO `SYS_CITY` VALUES ('212', null, null, null, null, '212', '白城', '15', null);
INSERT INTO `SYS_CITY` VALUES ('213', null, null, null, null, '213', '白山', '15', null);
INSERT INTO `SYS_CITY` VALUES ('214', 'CGQ', 'CGQ', 'CC', 'CHANGCHUN', '214', '长春', '15', '2');
INSERT INTO `SYS_CITY` VALUES ('215', 'JIL', 'JIL', 'JL', 'JILIN', '215', '吉林', '15', '4');
INSERT INTO `SYS_CITY` VALUES ('216', null, null, null, null, '216', '辽源', '15', null);
INSERT INTO `SYS_CITY` VALUES ('217', null, null, null, null, '217', '四平', '15', null);
INSERT INTO `SYS_CITY` VALUES ('218', null, null, null, null, '218', '松原', '15', null);
INSERT INTO `SYS_CITY` VALUES ('219', 'TNH', 'TNH', 'TH', 'TONGHUA', '219', '通化', '15', '2');
INSERT INTO `SYS_CITY` VALUES ('220', null, null, null, null, '220', '延边', '15', null);
INSERT INTO `SYS_CITY` VALUES ('221', 'CZX', 'CZX', 'CZ', 'CHANGZHOU', '221', '常州', '16', '12');
INSERT INTO `SYS_CITY` VALUES ('222', 'HIA', 'HIA', 'HA', 'huaian', '222', '淮安', '16', '1');
INSERT INTO `SYS_CITY` VALUES ('223', 'LYG', 'LYG', 'LYG', 'LIANYUNGANG', '223', '连云港', '16', '7');
INSERT INTO `SYS_CITY` VALUES ('224', 'NKG', 'NKG', 'NJ', 'NANJING', '224', '南京', '16', '5');
INSERT INTO `SYS_CITY` VALUES ('225', 'NTG', 'NTG', 'NT', 'NANTONG', '225', '南通', '16', '7');
INSERT INTO `SYS_CITY` VALUES ('226', 'SZV', 'SZV', 'SZ', 'SUZHOU', '226', '苏州', '16', '7');
INSERT INTO `SYS_CITY` VALUES ('227', null, null, null, null, '227', '宿迁', '16', null);
INSERT INTO `SYS_CITY` VALUES ('228', null, null, null, null, '228', '泰州', '16', null);
INSERT INTO `SYS_CITY` VALUES ('229', 'WUX', 'WUX', 'WX', 'WUXI', '229', '无锡', '16', '8');
INSERT INTO `SYS_CITY` VALUES ('230', 'XUZ', 'XUZ', 'XZ', 'XUZHOU', '230', '徐州', '16', '9');
INSERT INTO `SYS_CITY` VALUES ('231', 'YNZ', 'YNZ', 'YC', 'YANCHENG', '231', '盐城', '16', '7');
INSERT INTO `SYS_CITY` VALUES ('232', null, null, null, null, '232', '扬州', '16', null);
INSERT INTO `SYS_CITY` VALUES ('233', null, null, null, null, '233', '镇江', '16', null);
INSERT INTO `SYS_CITY` VALUES ('234', null, null, null, null, '234', '抚州', '17', null);
INSERT INTO `SYS_CITY` VALUES ('235', 'KOW', 'KOW', 'GZ', 'GANZHOU', '235', '赣州', '17', '6');
INSERT INTO `SYS_CITY` VALUES ('236', 'KNC', 'KNC', 'JA', 'JIAN', '236', '吉安', '17', '1');
INSERT INTO `SYS_CITY` VALUES ('237', 'JDZ', 'JDZ', 'JDZ', 'JINGDEZHEN', '237', '景德镇', '17', '2');
INSERT INTO `SYS_CITY` VALUES ('238', 'JIU', 'JIU', 'JJ', 'JIUJIANG', '238', '九江', '17', '3');
INSERT INTO `SYS_CITY` VALUES ('239', 'KHN', 'KHN', 'NC', 'NANCHANG', '239', '南昌', '17', '2');
INSERT INTO `SYS_CITY` VALUES ('240', null, null, null, null, '240', '萍乡', '17', null);
INSERT INTO `SYS_CITY` VALUES ('241', null, null, null, null, '241', '上饶', '17', null);
INSERT INTO `SYS_CITY` VALUES ('242', null, null, null, null, '242', '新余', '17', null);
INSERT INTO `SYS_CITY` VALUES ('243', null, null, null, null, '243', '宜春', '17', null);
INSERT INTO `SYS_CITY` VALUES ('244', null, null, null, null, '244', '鹰潭', '17', null);
INSERT INTO `SYS_CITY` VALUES ('245', 'AOG', 'AOG', 'AS', 'ANSHAN', '245', '鞍山', '18', '5');
INSERT INTO `SYS_CITY` VALUES ('246', null, null, null, null, '246', '本溪', '18', null);
INSERT INTO `SYS_CITY` VALUES ('247', 'CHG', 'CHG', 'CY', 'CHAOYANG', '247', '朝阳', '18', '9');
INSERT INTO `SYS_CITY` VALUES ('248', 'DLC', 'DLC', 'DL', 'DALIAN', '248', '大连', '18', '2');
INSERT INTO `SYS_CITY` VALUES ('249', 'DDG', 'DDG', 'DD', 'DANDONG', '249', '丹东', '18', '1');
INSERT INTO `SYS_CITY` VALUES ('250', null, null, null, null, '250', '抚顺', '18', null);
INSERT INTO `SYS_CITY` VALUES ('251', null, null, null, null, '251', '阜新', '18', null);
INSERT INTO `SYS_CITY` VALUES ('252', null, null, null, null, '252', '葫芦岛', '18', null);
INSERT INTO `SYS_CITY` VALUES ('253', 'JNZ', 'JNZ', 'JZ', 'JINZHOU', '253', '锦州', '18', '11');
INSERT INTO `SYS_CITY` VALUES ('254', null, null, null, null, '254', '辽阳', '18', null);
INSERT INTO `SYS_CITY` VALUES ('255', null, null, null, null, '255', '盘锦', '18', null);
INSERT INTO `SYS_CITY` VALUES ('256', 'SHE', 'SHE', 'SY', 'SHENYANG', '256', '沈阳', '18', '5');
INSERT INTO `SYS_CITY` VALUES ('257', null, null, null, null, '257', '铁岭', '18', null);
INSERT INTO `SYS_CITY` VALUES ('258', null, null, null, null, '258', '营口', '18', null);
INSERT INTO `SYS_CITY` VALUES ('259', null, null, null, null, '259', '阿拉善盟', '19', null);
INSERT INTO `SYS_CITY` VALUES ('260', null, null, null, null, '260', '巴彦淖尔市', '19', null);
INSERT INTO `SYS_CITY` VALUES ('261', 'BAV', 'BAV', 'BT', 'BAOTOU', '261', '包头', '19', '6');
INSERT INTO `SYS_CITY` VALUES ('262', 'CIF', 'CIF', 'CF', 'CHIFENG', '262', '赤峰', '19', '6');
INSERT INTO `SYS_CITY` VALUES ('263', 'DSN', 'DSN', 'ERDS', 'EERDUOSHI', '263', '鄂尔多斯', '19', '1');
INSERT INTO `SYS_CITY` VALUES ('264', 'HET', 'HET', 'HHHT', 'HUHEHAOTE', '264', '呼和浩特', '19', '7');
INSERT INTO `SYS_CITY` VALUES ('265', null, null, null, null, '265', '呼伦贝尔', '19', null);
INSERT INTO `SYS_CITY` VALUES ('266', 'TGO', 'TGO', 'TL', 'TONGLIAO', '266', '通辽', '19', '4');
INSERT INTO `SYS_CITY` VALUES ('267', 'WUA', 'WUA', 'WH', 'WUHAI', '267', '乌海', '19', '4');
INSERT INTO `SYS_CITY` VALUES ('268', null, null, null, null, '268', '乌兰察布市', '19', null);
INSERT INTO `SYS_CITY` VALUES ('269', null, null, null, null, '269', '锡林郭勒盟', '19', null);
INSERT INTO `SYS_CITY` VALUES ('270', null, null, null, null, '270', '兴安盟', '19', null);
INSERT INTO `SYS_CITY` VALUES ('271', 'GYU', 'GYU', 'GY', 'guyuan', '271', '固原', '20', '3');
INSERT INTO `SYS_CITY` VALUES ('272', null, null, null, null, '272', '石嘴山', '20', null);
INSERT INTO `SYS_CITY` VALUES ('273', null, null, null, null, '273', '吴忠', '20', null);
INSERT INTO `SYS_CITY` VALUES ('274', 'INC', 'INC', 'YC', 'YINCHUAN', '274', '银川', '20', '3');
INSERT INTO `SYS_CITY` VALUES ('275', null, null, null, null, '275', '果洛', '21', null);
INSERT INTO `SYS_CITY` VALUES ('276', null, null, null, null, '276', '海北', '21', null);
INSERT INTO `SYS_CITY` VALUES ('277', null, null, null, null, '277', '海东', '21', null);
INSERT INTO `SYS_CITY` VALUES ('278', null, null, null, null, '278', '海南藏族', '21', null);
INSERT INTO `SYS_CITY` VALUES ('279', null, null, null, null, '279', '海西', '21', null);
INSERT INTO `SYS_CITY` VALUES ('280', null, null, null, null, '280', '黄南', '21', null);
INSERT INTO `SYS_CITY` VALUES ('281', 'XNN', 'XNN', 'XN', 'XINING', '281', '西宁', '21', '4');
INSERT INTO `SYS_CITY` VALUES ('282', 'YUS', 'YUS', 'YS', 'YUSHU', '282', '玉树', '21', '9');
INSERT INTO `SYS_CITY` VALUES ('283', null, null, null, null, '283', '滨州', '22', null);
INSERT INTO `SYS_CITY` VALUES ('284', null, null, null, null, '284', '德州', '22', null);
INSERT INTO `SYS_CITY` VALUES ('285', 'DOY', 'DOY', 'DY', 'DONGYING', '285', '东营', '22', '7');
INSERT INTO `SYS_CITY` VALUES ('286', null, null, null, null, '286', '菏泽', '22', null);
INSERT INTO `SYS_CITY` VALUES ('287', 'TNA', 'TNA', 'JN', 'JINAN', '287', '济南', '22', '7');
INSERT INTO `SYS_CITY` VALUES ('288', 'JNG', 'JNG', 'JN', 'JINING', '288', '济宁', '22', '6');
INSERT INTO `SYS_CITY` VALUES ('289', null, null, null, null, '289', '莱芜', '22', null);
INSERT INTO `SYS_CITY` VALUES ('290', null, null, null, null, '290', '聊城', '22', null);
INSERT INTO `SYS_CITY` VALUES ('291', 'LYI', 'LYI', 'LY', 'LINYI', '291', '临沂', '22', '6');
INSERT INTO `SYS_CITY` VALUES ('292', 'TAO', 'TAO', 'QD', 'QINGDAO', '292', '青岛', '22', '1');
INSERT INTO `SYS_CITY` VALUES ('293', null, null, null, null, '293', '日照', '22', null);
INSERT INTO `SYS_CITY` VALUES ('294', null, null, null, null, '294', '泰安', '22', null);
INSERT INTO `SYS_CITY` VALUES ('295', 'WEH', 'WEH', 'WH', 'WEIHAI', '295', '威海', '22', '2');
INSERT INTO `SYS_CITY` VALUES ('296', 'WEF', 'WEF', 'WF', 'WEIFANG', '296', '潍坊', '22', '1');
INSERT INTO `SYS_CITY` VALUES ('297', 'YNT', 'YNT', 'YT', 'YANTAI', '297', '烟台', '22', '10');
INSERT INTO `SYS_CITY` VALUES ('298', null, null, null, null, '298', '枣庄', '22', null);
INSERT INTO `SYS_CITY` VALUES ('299', null, null, null, null, '299', '淄博', '22', null);
INSERT INTO `SYS_CITY` VALUES ('300', 'CIH', 'CIH', 'CZ', 'CHANGZHI', '300', '长治', '23', '11');
INSERT INTO `SYS_CITY` VALUES ('301', 'DAT', 'DAT', 'DT', 'DATONG', '301', '大同', '23', '6');
INSERT INTO `SYS_CITY` VALUES ('302', null, null, null, null, '302', '晋城', '23', null);
INSERT INTO `SYS_CITY` VALUES ('303', null, null, null, null, '303', '晋中', '23', null);
INSERT INTO `SYS_CITY` VALUES ('304', null, null, null, null, '304', '临汾', '23', null);
INSERT INTO `SYS_CITY` VALUES ('305', null, null, null, null, '305', '吕梁', '23', null);
INSERT INTO `SYS_CITY` VALUES ('306', null, null, null, null, '306', '朔州', '23', null);
INSERT INTO `SYS_CITY` VALUES ('307', 'TYN', 'TYN', 'TY', 'TAIYUAN', '307', '太原', '23', '8');
INSERT INTO `SYS_CITY` VALUES ('308', null, null, null, null, '308', '忻州', '23', null);
INSERT INTO `SYS_CITY` VALUES ('309', null, null, null, null, '309', '阳泉', '23', null);
INSERT INTO `SYS_CITY` VALUES ('310', 'YCU', 'YCU', 'YC', 'YUNCHENG', '310', '运城', '23', '5');
INSERT INTO `SYS_CITY` VALUES ('3105', 'ZHY', 'ZHY', 'ZW', 'zhongwei', '3105', '中卫', '20', '6');
INSERT INTO `SYS_CITY` VALUES ('311', 'AKA', 'AKA', 'AK', 'ANKANG', '311', '安康', '24', '1');
INSERT INTO `SYS_CITY` VALUES ('3113', 'TCG', 'TCG', 'TC', 'TACHENG', '3113', '塔城', '29', '1');
INSERT INTO `SYS_CITY` VALUES ('3114', 'AAT', 'AAT', 'ALT', 'ALETAI', '3114', '阿勒泰', '29', '3');
INSERT INTO `SYS_CITY` VALUES ('312', null, null, null, null, '312', '宝鸡', '24', null);
INSERT INTO `SYS_CITY` VALUES ('313', 'HZG', 'HZG', 'HZ', 'HANZHONG', '313', '汉中', '24', '14');
INSERT INTO `SYS_CITY` VALUES ('314', null, null, null, null, '314', '商洛', '24', null);
INSERT INTO `SYS_CITY` VALUES ('3143', null, null, null, null, '3143', '乌苏里江', '12', null);
INSERT INTO `SYS_CITY` VALUES ('315', null, null, null, null, '315', '铜川', '24', null);
INSERT INTO `SYS_CITY` VALUES ('316', null, null, null, null, '316', '渭南', '24', null);
INSERT INTO `SYS_CITY` VALUES ('317', 'SIA', 'SIA', 'XA', 'XIAN', '317', '西安', '24', '1');
INSERT INTO `SYS_CITY` VALUES ('318', 'XIY', 'XIY', 'XY', 'XIANYANG', '318', '咸阳', '24', '8');
INSERT INTO `SYS_CITY` VALUES ('319', 'ENY', 'ENY', 'YA', 'YANAN', '319', '延安', '24', '1');
INSERT INTO `SYS_CITY` VALUES ('320', 'UYN', 'UYN', 'YL', 'YULIN', '320', '榆林', '24', '8');
INSERT INTO `SYS_CITY` VALUES ('321', 'SHA', 'SHA', 'SH', 'SHANGHAI', '321', '上海', '25', '1');
INSERT INTO `SYS_CITY` VALUES ('322', null, null, null, null, '322', '阿坝', '26', null);
INSERT INTO `SYS_CITY` VALUES ('323', null, null, null, null, '323', '巴中', '26', null);
INSERT INTO `SYS_CITY` VALUES ('324', 'CTU', 'CTU', 'CD', 'CHENGDU', '324', '成都', '26', '5');
INSERT INTO `SYS_CITY` VALUES ('325', 'DAX', 'DAX', 'DZ', 'DAXIAN', '325', '达州', '26', '8');
INSERT INTO `SYS_CITY` VALUES ('326', null, null, null, null, '326', '德阳', '26', null);
INSERT INTO `SYS_CITY` VALUES ('327', null, null, null, null, '327', '甘孜', '26', null);
INSERT INTO `SYS_CITY` VALUES ('328', null, null, null, null, '328', '广安', '26', null);
INSERT INTO `SYS_CITY` VALUES ('329', 'GYS', 'GYS', 'GY', 'GUANGYUAN', '329', '广元', '26', '2');
INSERT INTO `SYS_CITY` VALUES ('330', null, null, null, null, '330', '乐山', '26', null);
INSERT INTO `SYS_CITY` VALUES ('331', null, null, null, null, '331', '凉山', '26', null);
INSERT INTO `SYS_CITY` VALUES ('332', null, null, null, null, '332', '眉山', '26', null);
INSERT INTO `SYS_CITY` VALUES ('333', 'MIG', 'MIG', 'MY', 'MIANYANG', '333', '绵阳', '26', '1');
INSERT INTO `SYS_CITY` VALUES ('334', 'NAO', 'NAO', 'NC', 'NANCHONG', '334', '南充', '26', '3');
INSERT INTO `SYS_CITY` VALUES ('335', null, null, null, null, '335', '内江', '26', null);
INSERT INTO `SYS_CITY` VALUES ('336', 'PZI', 'PZI', 'PZH', 'PANZHIHUA', '336', '攀枝花', '26', '2');
INSERT INTO `SYS_CITY` VALUES ('337', null, null, null, null, '337', '遂宁', '26', null);
INSERT INTO `SYS_CITY` VALUES ('338', null, null, null, null, '338', '雅安', '26', null);
INSERT INTO `SYS_CITY` VALUES ('339', 'YBP', 'YBP', 'YB', 'YIBIN', '339', '宜宾', '26', '2');
INSERT INTO `SYS_CITY` VALUES ('340', null, null, null, null, '340', '资阳', '26', null);
INSERT INTO `SYS_CITY` VALUES ('341', null, null, null, null, '341', '自贡', '26', null);
INSERT INTO `SYS_CITY` VALUES ('342', 'LZO', 'LZO', 'LZ', 'LUZHOU', '342', '泸州', '26', '10');
INSERT INTO `SYS_CITY` VALUES ('343', 'TSN', 'TSN', 'TJ', 'TIANJIN', '343', '天津', '27', '3');
INSERT INTO `SYS_CITY` VALUES ('344', null, null, null, null, '344', '阿里', '28', null);
INSERT INTO `SYS_CITY` VALUES ('345', 'BPX', 'BPX', 'CD', 'CHANGDU', '345', '昌都', '28', '3');
INSERT INTO `SYS_CITY` VALUES ('346', 'LXA', 'LXA', 'LS', 'LASA', '346', '拉萨', '28', '3');
INSERT INTO `SYS_CITY` VALUES ('347', 'LZY', 'LZY', 'LZ', 'LINZHI', '347', '林芝', '28', '11');
INSERT INTO `SYS_CITY` VALUES ('348', null, null, null, null, '348', '那曲', '28', null);
INSERT INTO `SYS_CITY` VALUES ('349', null, null, null, null, '349', '日喀则', '28', null);
INSERT INTO `SYS_CITY` VALUES ('350', null, null, null, null, '350', '山南', '28', null);
INSERT INTO `SYS_CITY` VALUES ('351', 'AKU', 'AKU', 'AKS', 'AKESU', '351', '阿克苏', '29', '2');
INSERT INTO `SYS_CITY` VALUES ('352', null, null, null, null, '352', '阿拉尔', '29', null);
INSERT INTO `SYS_CITY` VALUES ('353', null, null, null, null, '353', '巴音郭楞', '29', null);
INSERT INTO `SYS_CITY` VALUES ('354', null, null, null, null, '354', '博尔塔拉', '29', null);
INSERT INTO `SYS_CITY` VALUES ('355', null, null, null, null, '355', '昌吉', '29', null);
INSERT INTO `SYS_CITY` VALUES ('356', 'HMI', 'HMI', 'HM', 'HAMI', '356', '哈密', '29', '9');
INSERT INTO `SYS_CITY` VALUES ('357', 'HTN', 'HTN', 'HT', 'HETIAN', '357', '和田', '29', '11');
INSERT INTO `SYS_CITY` VALUES ('358', 'KHG', 'KHG', 'KS', 'KASHEN', '358', '喀什', '29', '3');
INSERT INTO `SYS_CITY` VALUES ('359', 'KRY', 'KRY', 'KLMY', 'KELAMAYI', '359', '克拉玛依', '29', '1');
INSERT INTO `SYS_CITY` VALUES ('36', 'AQG', 'AQG', 'AQ', 'ANQING', '36', '安庆', '2', '4');
INSERT INTO `SYS_CITY` VALUES ('360', null, null, null, null, '360', '克孜勒苏柯尔克孜', '29', null);
INSERT INTO `SYS_CITY` VALUES ('361', null, null, null, null, '361', '石河子', '29', null);
INSERT INTO `SYS_CITY` VALUES ('362', null, null, null, null, '362', '图木舒克', '29', null);
INSERT INTO `SYS_CITY` VALUES ('363', null, null, null, null, '363', '吐鲁番', '29', null);
INSERT INTO `SYS_CITY` VALUES ('364', 'URC', 'URC', 'WLMQ', 'WULUMUQI', '364', '乌鲁木齐', '29', '6');
INSERT INTO `SYS_CITY` VALUES ('365', null, null, null, null, '365', '五家渠', '29', null);
INSERT INTO `SYS_CITY` VALUES ('366', null, null, null, null, '366', '伊犁', '29', null);
INSERT INTO `SYS_CITY` VALUES ('367', 'BSD', 'BSD', 'BS', 'BAOSHAN', '367', '保山', '30', '5');
INSERT INTO `SYS_CITY` VALUES ('368', null, null, null, null, '368', '楚雄', '30', null);
INSERT INTO `SYS_CITY` VALUES ('369', 'DLU', 'DLU', 'DL', 'DALI', '369', '大理', '30', '3');
INSERT INTO `SYS_CITY` VALUES ('37', 'BFU', 'BFU', 'BB', 'BANGBU', '37', '蚌埠', '2', '1');
INSERT INTO `SYS_CITY` VALUES ('370', null, null, null, null, '370', '德宏', '30', null);
INSERT INTO `SYS_CITY` VALUES ('371', 'DIG', 'DIG', 'DQ', 'DIQING', '371', '迪庆', '30', '4');
INSERT INTO `SYS_CITY` VALUES ('372', null, null, null, null, '372', '红河', '30', null);
INSERT INTO `SYS_CITY` VALUES ('373', 'KMG', 'KMG', 'KM', 'KUNMING', '373', '昆明', '30', '2');
INSERT INTO `SYS_CITY` VALUES ('374', 'LJG', 'LJG', 'LJ', 'LIJIANG', '374', '丽江', '30', '2');
INSERT INTO `SYS_CITY` VALUES ('375', 'LNJ', 'LNJ', 'LC', 'Lincang', '375', '临沧', '30', '1');
INSERT INTO `SYS_CITY` VALUES ('376', null, null, null, null, '376', '怒江', '30', null);
INSERT INTO `SYS_CITY` VALUES ('377', null, null, null, null, '377', '曲靖', '30', null);
INSERT INTO `SYS_CITY` VALUES ('378', 'SYM', 'SYM', 'PE', 'SAIMAO', '378', '普洱', '30', '1');
INSERT INTO `SYS_CITY` VALUES ('379', 'WNH', 'WNH', 'WS', 'wenshan', '379', '文山', '30', '7');
INSERT INTO `SYS_CITY` VALUES ('38', null, null, null, null, '38', '巢湖', '2', null);
INSERT INTO `SYS_CITY` VALUES ('380', 'JHG', 'JHG', 'XSBN', '景洪/西双版纳', '380', '西双版纳', '30', '5');
INSERT INTO `SYS_CITY` VALUES ('381', null, null, null, null, '381', '玉溪', '30', null);
INSERT INTO `SYS_CITY` VALUES ('382', 'ZAT', 'ZAT', 'ZT', 'ZHAOTONG', '382', '昭通', '30', '5');
INSERT INTO `SYS_CITY` VALUES ('383', 'HGH', 'HGH', 'HZ', 'HANGZHOU', '383', '杭州', '31', '13');
INSERT INTO `SYS_CITY` VALUES ('384', null, null, null, null, '384', '湖州', '31', null);
INSERT INTO `SYS_CITY` VALUES ('385', null, null, null, null, '385', '嘉兴', '31', null);
INSERT INTO `SYS_CITY` VALUES ('386', null, null, null, null, '386', '金华', '31', null);
INSERT INTO `SYS_CITY` VALUES ('387', null, null, null, null, '387', '丽水', '31', null);
INSERT INTO `SYS_CITY` VALUES ('388', 'NGB', 'NGB', 'NB', 'NINGBO', '388', '宁波', '31', '1');
INSERT INTO `SYS_CITY` VALUES ('389', null, null, null, null, '389', '绍兴', '31', null);
INSERT INTO `SYS_CITY` VALUES ('39', null, null, null, null, '39', '池州', '2', null);
INSERT INTO `SYS_CITY` VALUES ('390', 'HYN', 'HYN', 'TZ', 'LUQIAO', '390', '台州', '31', '9');
INSERT INTO `SYS_CITY` VALUES ('391', 'WNZ', 'WNZ', 'WZ', 'WENZHOU', '391', '温州', '31', '9');
INSERT INTO `SYS_CITY` VALUES ('392', 'HSN', 'HSN', 'ZS', 'ZHOUSHAN', '392', '舟山', '31', '4');
INSERT INTO `SYS_CITY` VALUES ('393', 'JUZ', 'JUZ', 'QZ', 'QUZHOU', '393', '衢州', '31', '5');
INSERT INTO `SYS_CITY` VALUES ('394', 'CKG', 'CKG', 'CQ', 'CHONGQING', '394', '重庆', '32', '7');
INSERT INTO `SYS_CITY` VALUES ('395', null, null, null, null, '395', '香港', '33', null);
INSERT INTO `SYS_CITY` VALUES ('396', null, null, null, null, '396', '澳门', '34', null);
INSERT INTO `SYS_CITY` VALUES ('397', null, null, null, null, '397', '高雄', '35', null);
INSERT INTO `SYS_CITY` VALUES ('398', null, null, null, null, '398', '花莲', '35', null);
INSERT INTO `SYS_CITY` VALUES ('399', null, null, null, null, '399', '基隆', '35', null);
INSERT INTO `SYS_CITY` VALUES ('40', null, null, null, null, '40', '滁州', '2', null);
INSERT INTO `SYS_CITY` VALUES ('400', null, null, null, null, '400', '嘉义', '35', null);
INSERT INTO `SYS_CITY` VALUES ('401', null, null, null, null, '401', '台北', '35', null);
INSERT INTO `SYS_CITY` VALUES ('402', null, null, null, null, '402', '台东', '35', null);
INSERT INTO `SYS_CITY` VALUES ('403', null, null, null, null, '403', '台南', '35', null);
INSERT INTO `SYS_CITY` VALUES ('404', null, null, null, null, '404', '台中', '35', null);
INSERT INTO `SYS_CITY` VALUES ('41', 'FUG', 'FUG', 'BY', 'FUYANG', '41', '阜阳', '2', '7');
INSERT INTO `SYS_CITY` VALUES ('42', 'HFE', 'HFE', 'HF', 'HEFEI', '42', '合肥', '2', '4');
INSERT INTO `SYS_CITY` VALUES ('43', null, null, null, null, '43', '淮北', '2', null);
INSERT INTO `SYS_CITY` VALUES ('44', null, null, null, null, '44', '淮南', '2', null);
INSERT INTO `SYS_CITY` VALUES ('45', 'TXN', 'TXN', 'HS', 'HUANGSHAN', '45', '黄山', '2', '10');
INSERT INTO `SYS_CITY` VALUES ('4569', 'NBS', 'NBS', 'CBS', 'QIQIHAER', '4569', '长白山', '15', '1');
INSERT INTO `SYS_CITY` VALUES ('4580', 'XEN', 'XEN', 'XC', 'XINGCHENG', '4580', '兴城', '18', '2');
INSERT INTO `SYS_CITY` VALUES ('46', null, null, null, null, '46', '六安', '2', null);
INSERT INTO `SYS_CITY` VALUES ('47', null, null, null, null, '47', '马鞍山', '2', null);
INSERT INTO `SYS_CITY` VALUES ('48', null, null, null, null, '48', '宿州', '2', null);
INSERT INTO `SYS_CITY` VALUES ('49', null, null, null, null, '49', '铜陵', '2', null);
INSERT INTO `SYS_CITY` VALUES ('50', 'WHU', 'WHU', 'WH', 'WUHU', '50', '芜湖', '2', '3');
INSERT INTO `SYS_CITY` VALUES ('51', null, null, null, null, '51', '宣城', '2', null);
INSERT INTO `SYS_CITY` VALUES ('5114', null, null, null, null, '5114', '新竹', '35', null);
INSERT INTO `SYS_CITY` VALUES ('5115', null, null, null, null, '5115', '宜兰', '35', null);
INSERT INTO `SYS_CITY` VALUES ('5116', null, null, null, null, '5116', '桃园', '35', null);
INSERT INTO `SYS_CITY` VALUES ('5117', null, null, null, null, '5117', '苗栗', '35', null);
INSERT INTO `SYS_CITY` VALUES ('5118', null, null, null, null, '5118', '彰化', '35', null);
INSERT INTO `SYS_CITY` VALUES ('5119', null, null, null, null, '5119', '南投', '35', null);
INSERT INTO `SYS_CITY` VALUES ('5120', null, null, null, null, '5120', '云林', '35', null);
INSERT INTO `SYS_CITY` VALUES ('5121', null, null, null, null, '5121', '屏东', '35', null);
INSERT INTO `SYS_CITY` VALUES ('5127', null, null, null, null, '5127', '金门', '35', null);
INSERT INTO `SYS_CITY` VALUES ('5130', null, null, null, null, '5130', '澎湖', '35', null);
INSERT INTO `SYS_CITY` VALUES ('52', null, null, null, null, '52', '亳州', '2', null);
INSERT INTO `SYS_CITY` VALUES ('53', 'PEK', 'PEK', 'BJ', 'BEIJING', '53', '北京', '3', '3');
INSERT INTO `SYS_CITY` VALUES ('54', 'FOC', 'FOC', 'FZ', 'FUZHOU', '54', '福州', '4', '2');
INSERT INTO `SYS_CITY` VALUES ('55', 'LCX', 'LCX', 'LY', 'longyan', '55', '龙岩', '4', '4');
INSERT INTO `SYS_CITY` VALUES ('56', null, null, null, null, '56', '南平', '4', null);
INSERT INTO `SYS_CITY` VALUES ('57', null, null, null, null, '57', '宁德', '4', null);
INSERT INTO `SYS_CITY` VALUES ('58', null, null, null, null, '58', '莆田', '4', null);
INSERT INTO `SYS_CITY` VALUES ('59', null, null, null, null, '59', '泉州', '4', null);
INSERT INTO `SYS_CITY` VALUES ('60', null, null, null, null, '60', '三明', '4', null);
INSERT INTO `SYS_CITY` VALUES ('61', 'XMN', 'XMN', 'XM', 'SHAMEN', '61', '厦门', '4', '3');
INSERT INTO `SYS_CITY` VALUES ('62', null, null, null, null, '62', '漳州', '4', null);
INSERT INTO `SYS_CITY` VALUES ('63', null, null, null, null, '63', '白银', '5', null);
INSERT INTO `SYS_CITY` VALUES ('6321', 'SHS', 'SHS', 'SSS', 'SHASHI', '6321', '三沙市', '9', '3');
INSERT INTO `SYS_CITY` VALUES ('64', null, null, null, null, '64', '定西', '5', null);
INSERT INTO `SYS_CITY` VALUES ('65', null, null, null, null, '65', '甘南', '5', null);
INSERT INTO `SYS_CITY` VALUES ('6571', null, null, null, null, '6571', '新北', '35', null);
INSERT INTO `SYS_CITY` VALUES ('66', 'JGN', 'JGN', 'JYG', 'JIAYUGUAN', '66', '嘉峪关', '5', '10');
INSERT INTO `SYS_CITY` VALUES ('67', null, null, null, null, '67', '金昌', '5', null);
INSERT INTO `SYS_CITY` VALUES ('68', 'CHW', 'CHW', 'JQ', 'JIUQUAN', '68', '酒泉', '5', '8');
INSERT INTO `SYS_CITY` VALUES ('69', 'LHW', 'LHW', 'LZ', 'LANZHOU', '69', '兰州', '5', '8');
INSERT INTO `SYS_CITY` VALUES ('70', null, null, null, null, '70', '临夏', '5', null);
INSERT INTO `SYS_CITY` VALUES ('71', null, null, null, null, '71', '陇南', '5', null);
INSERT INTO `SYS_CITY` VALUES ('72', null, null, null, null, '72', '平凉', '5', null);
INSERT INTO `SYS_CITY` VALUES ('73', 'IQN', 'IQN', 'QY', 'QINGYANG', '73', '庆阳', '5', '4');
INSERT INTO `SYS_CITY` VALUES ('74', 'THQ', 'THQ', 'TS', 'tianshui', '74', '天水', '5', '6');
INSERT INTO `SYS_CITY` VALUES ('75', null, null, null, null, '75', '武威', '5', null);
INSERT INTO `SYS_CITY` VALUES ('76', null, null, null, null, '76', '张掖', '5', null);
INSERT INTO `SYS_CITY` VALUES ('77', 'CCC', 'CCC', 'CZ', 'CHAOZHOU', '77', '潮州', '6', '10');
INSERT INTO `SYS_CITY` VALUES ('78', null, null, null, null, '78', '东莞', '6', null);
INSERT INTO `SYS_CITY` VALUES ('79', 'FUO', 'FUO', 'FS', 'FOSHAN', '79', '佛山', '6', '1');
INSERT INTO `SYS_CITY` VALUES ('80', 'CAN', 'CAN', 'GZ', 'GUANGZHOU', '80', '广州', '6', '5');
INSERT INTO `SYS_CITY` VALUES ('81', null, null, null, null, '81', '河源', '6', null);
INSERT INTO `SYS_CITY` VALUES ('82', null, null, null, null, '82', '惠州', '6', null);
INSERT INTO `SYS_CITY` VALUES ('83', null, null, null, null, '83', '江门', '6', null);
INSERT INTO `SYS_CITY` VALUES ('84', null, null, null, null, '84', '揭阳', '6', null);
INSERT INTO `SYS_CITY` VALUES ('85', null, null, null, null, '85', '茂名', '6', null);
INSERT INTO `SYS_CITY` VALUES ('86', null, null, null, null, '86', '梅州', '6', null);
INSERT INTO `SYS_CITY` VALUES ('87', null, null, null, null, '87', '清远', '6', null);
INSERT INTO `SYS_CITY` VALUES ('88', 'SWA', 'SWA', 'ST', 'SHANTOU', '88', '汕头', '6', '4');
INSERT INTO `SYS_CITY` VALUES ('89', null, null, null, null, '89', '汕尾', '6', null);
INSERT INTO `SYS_CITY` VALUES ('90', null, null, null, null, '90', '韶关', '6', null);
INSERT INTO `SYS_CITY` VALUES ('91', 'SZX', 'SZX', 'SZ', 'SHENZHEN', '91', '深圳', '6', '8');
INSERT INTO `SYS_CITY` VALUES ('92', null, null, null, null, '92', '阳江', '6', null);
INSERT INTO `SYS_CITY` VALUES ('93', null, null, null, null, '93', '云浮', '6', null);
INSERT INTO `SYS_CITY` VALUES ('94', 'ZHA', 'ZHA', 'ZJ', 'ZHANJIANG', '94', '湛江', '6', '2');
INSERT INTO `SYS_CITY` VALUES ('95', null, null, null, null, '95', '肇庆', '6', null);
INSERT INTO `SYS_CITY` VALUES ('96', null, null, null, null, '96', '中山', '6', null);
INSERT INTO `SYS_CITY` VALUES ('97', 'ZUH', 'ZUH', 'ZH', 'ZHUHAI', '97', '珠海', '6', '1');
INSERT INTO `SYS_CITY` VALUES ('98', 'AEB', 'AEB', 'BS', 'BAISE', '98', '百色', '7', '4');
INSERT INTO `SYS_CITY` VALUES ('99', 'BHY', 'BHY', 'BH', 'BEIHAI', '99', '北海', '7', '2');

-- ----------------------------
-- Table structure for `SYS_IMAGE`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_IMAGE`;
CREATE TABLE `SYS_IMAGE` (
  `ID` varchar(64) NOT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `OWNER_ID` varchar(60) DEFAULT NULL,
  `REMARK` text,
  `SRC_FILE_SIZE` int(11) DEFAULT NULL,
  `TITLE` varchar(512) DEFAULT NULL,
  `ALBUM_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_d0k8d7bt1nep4ruo6jl1mtcgs` (`ALBUM_ID`),
  CONSTRAINT `FK_d0k8d7bt1nep4ruo6jl1mtcgs` FOREIGN KEY (`ALBUM_ID`) REFERENCES `SYS_ALBUM` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_image
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_IMAGE_SPEC`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_IMAGE_SPEC`;
CREATE TABLE `SYS_IMAGE_SPEC` (
  `ID` varchar(64) NOT NULL,
  `FILE_INFO` varchar(512) DEFAULT NULL,
  `KEY_` varchar(60) DEFAULT NULL,
  `IMAGE_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_p9pd1kijos95py2tj01vhxk5q` (`IMAGE_ID`),
  CONSTRAINT `FK_p9pd1kijos95py2tj01vhxk5q` FOREIGN KEY (`IMAGE_ID`) REFERENCES `SYS_IMAGE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_image_spec
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_KVCONFIG`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_KVCONFIG`;
CREATE TABLE `SYS_KVCONFIG` (
  `ID` varchar(64) NOT NULL,
  `DATA_KEY` varchar(64) DEFAULT NULL,
  `DATA_VALUE` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_kvconfig
-- ----------------------------

-- ----------------------------
-- Table structure for `SYS_PROVINCE`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_PROVINCE`;
CREATE TABLE `SYS_PROVINCE` (
  `ID` varchar(64) NOT NULL,
  `CODE` varchar(8) DEFAULT NULL,
  `NAME` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_province
-- ----------------------------
INSERT INTO `SYS_PROVINCE` VALUES ('10', '10', '河北');
INSERT INTO `SYS_PROVINCE` VALUES ('11', '11', '河南');
INSERT INTO `SYS_PROVINCE` VALUES ('12', '12', '黑龙江');
INSERT INTO `SYS_PROVINCE` VALUES ('13', '13', '湖北');
INSERT INTO `SYS_PROVINCE` VALUES ('14', '14', '湖南');
INSERT INTO `SYS_PROVINCE` VALUES ('15', '15', '吉林');
INSERT INTO `SYS_PROVINCE` VALUES ('16', '16', '江苏');
INSERT INTO `SYS_PROVINCE` VALUES ('17', '17', '江西');
INSERT INTO `SYS_PROVINCE` VALUES ('18', '18', '辽宁');
INSERT INTO `SYS_PROVINCE` VALUES ('19', '19', '内蒙古');
INSERT INTO `SYS_PROVINCE` VALUES ('2', '2', '安徽');
INSERT INTO `SYS_PROVINCE` VALUES ('20', '20', '宁夏');
INSERT INTO `SYS_PROVINCE` VALUES ('21', '21', '青海');
INSERT INTO `SYS_PROVINCE` VALUES ('22', '22', '山东');
INSERT INTO `SYS_PROVINCE` VALUES ('23', '23', '山西');
INSERT INTO `SYS_PROVINCE` VALUES ('24', '24', '陕西');
INSERT INTO `SYS_PROVINCE` VALUES ('25', '25', '上海');
INSERT INTO `SYS_PROVINCE` VALUES ('26', '26', '四川');
INSERT INTO `SYS_PROVINCE` VALUES ('27', '27', '天津');
INSERT INTO `SYS_PROVINCE` VALUES ('28', '28', '西藏');
INSERT INTO `SYS_PROVINCE` VALUES ('29', '29', '新疆');
INSERT INTO `SYS_PROVINCE` VALUES ('3', '3', '北京');
INSERT INTO `SYS_PROVINCE` VALUES ('30', '30', '云南');
INSERT INTO `SYS_PROVINCE` VALUES ('31', '31', '浙江');
INSERT INTO `SYS_PROVINCE` VALUES ('32', '32', '重庆');
INSERT INTO `SYS_PROVINCE` VALUES ('33', '33', '香港');
INSERT INTO `SYS_PROVINCE` VALUES ('34', '34', '澳门');
INSERT INTO `SYS_PROVINCE` VALUES ('35', '35', '台湾');
INSERT INTO `SYS_PROVINCE` VALUES ('4', '4', '福建');
INSERT INTO `SYS_PROVINCE` VALUES ('5', '5', '甘肃');
INSERT INTO `SYS_PROVINCE` VALUES ('6', '6', '广东');
INSERT INTO `SYS_PROVINCE` VALUES ('7', '7', '广西');
INSERT INTO `SYS_PROVINCE` VALUES ('8', '8', '贵州');
INSERT INTO `SYS_PROVINCE` VALUES ('9', '9', '海南');

-- ----------------------------
-- Table structure for `sys_staff`
-- ----------------------------
DROP TABLE IF EXISTS `SYS_STAFF`;
CREATE TABLE `SYS_STAFF` (
  `ID` varchar(64) NOT NULL,
  `EMAIL` varchar(128) DEFAULT NULL,
  `MOBILE` varchar(64) DEFAULT NULL,
  `REAL_NAME` varchar(64) DEFAULT NULL,
  `TEL` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_9bi1t3ynljjre8yn4745u9ynj` (`ID`),
  CONSTRAINT `FK_9bi1t3ynljjre8yn4745u9ynj` FOREIGN KEY (`ID`) REFERENCES `AUTH_USER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_staff
-- ----------------------------
INSERT INTO `SYS_STAFF` VALUES ('1', 'a', '15868870214', '张振斌', null);

-- ----------------------------
-- Table structure for `u_bindaccount`
-- ----------------------------
DROP TABLE IF EXISTS `U_BINDACCOUNT`;
CREATE TABLE `U_BINDACCOUNT` (
  `ID` varchar(64) NOT NULL,
  `ACCOUNT_TYPE` int(11) DEFAULT NULL,
  `BIND_ACCOUNT_ID` varchar(64) DEFAULT NULL,
  `BIND_ACCOUNT_NAME` varchar(64) DEFAULT NULL,
  `BINDING` char(1) DEFAULT NULL,
  `BINDING_DATE` datetime DEFAULT NULL,
  `USER_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_l5mjwaygbwf3nur2irj4yt6nq` (`USER_ID`),
  CONSTRAINT `FK_l5mjwaygbwf3nur2irj4yt6nq` FOREIGN KEY (`USER_ID`) REFERENCES `U_USER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_bindaccount
-- ----------------------------
INSERT INTO `U_BINDACCOUNT` VALUES ('0f23f9b5531b445d91974e52b785eec3', '1', '12345678934', 'zzb', 'Y', '2014-09-18 13:33:12', 'c56b0d7fb5e2462db6ab987f59cd0c00');
INSERT INTO `U_BINDACCOUNT` VALUES ('9680c0d9ed894838bb109ca529264b4c', '2', '12345678934', 'zzb', 'Y', '2014-09-19 15:47:03', 'aaaaaaaaaaaaaaaaaad123');
INSERT INTO `U_BINDACCOUNT` VALUES ('995210533f114663ad02fb42d193234d', '1', '12345678933', 'zzb', 'Y', '2014-09-18 09:56:22', 'aaaaaaaaaaaaaaaaaad123');

-- ----------------------------
-- Table structure for `u_client`
-- ----------------------------
DROP TABLE IF EXISTS `U_CLIENT`;
CREATE TABLE `U_CLIENT` (
  `ID` varchar(64) NOT NULL,
  `HOME_URL` varchar(256) DEFAULT NULL,
  `NAME` varchar(64) DEFAULT NULL,
  `NEED_PERM_ITEMS` varchar(1024) DEFAULT NULL,
  `REMARK` varchar(4000) DEFAULT NULL,
  `REQUIRE_PERM_ITEMS` varchar(1024) DEFAULT NULL,
  `SECRET_KEY` varchar(64) DEFAULT NULL,
  `VALID` char(1) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_client
-- ----------------------------
INSERT INTO `U_CLIENT` VALUES ('1', 'http://localhost:19999/jqlm-application/jq', '景区联盟', null, null, null, '1', '1');

-- ----------------------------
-- Table structure for `u_smsvalidatecode`
-- ----------------------------
DROP TABLE IF EXISTS `U_SMSVALIDATECODE`;
CREATE TABLE `U_SMSVALIDATECODE` (
  `ID` varchar(64) NOT NULL,
  `CURRENT_TIMES` int(11) DEFAULT NULL,
  `MOBILE` varchar(11) DEFAULT NULL,
  `OVER_TIME` int(11) DEFAULT NULL,
  `SCENE` int(11) DEFAULT NULL,
  `SEND_DATE` datetime DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `VALID_TIMES` int(11) DEFAULT NULL,
  `VALUE` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_smsvalidatecode
-- ----------------------------

-- ----------------------------
-- Table structure for `U_USER`
-- ----------------------------
DROP TABLE IF EXISTS `U_USER`;
CREATE TABLE `U_USER` (
  `ID` varchar(64) NOT NULL,
  `LOGINNAME` varchar(64) DEFAULT NULL,
  `PASSWORD` varchar(64) DEFAULT NULL,
  `BIRTHDAY` datetime DEFAULT NULL,
  `CARD_NO` varchar(30) DEFAULT NULL,
  `CARD_TYPE` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `GENDER` int(11) DEFAULT NULL,
  `NAME` varchar(10) DEFAULT NULL,
  `NICK_NAME` varchar(20) DEFAULT NULL,
  `SOURCE` varchar(64) DEFAULT NULL,
  `EMAIL` varchar(64) DEFAULT NULL,
  `IM` varchar(64) DEFAULT NULL,
  `MOBILE` varchar(11) DEFAULT NULL,
  `ACTIVATED` char(1) DEFAULT NULL,
  `LAST_LOGIN_TIME` datetime DEFAULT NULL,
  `FROM_CLIENT_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_4pjlsrqhp3lon35kjk0kh6fmy` (`FROM_CLIENT_ID`),
  CONSTRAINT `FK_4pjlsrqhp3lon35kjk0kh6fmy` FOREIGN KEY (`FROM_CLIENT_ID`) REFERENCES `U_CLIENT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of u_user
-- ----------------------------
INSERT INTO `U_USER` VALUES ('aaaaaaaaaaaaaaaaaad123', 'zzb2', 'e10adc3949ba59abbe56e057f20f883e', '2014-04-05 00:00:00', '33038119920405293x', null, '2014-09-18 09:56:22', '0', 'modify_张振斌', 'modify_Nick', '2', 'modify_email@qq.com', 'modify_im@qq.com', '110120', 'Y', null, '1');
INSERT INTO `U_USER` VALUES ('c56b0d7fb5e2462db6ab987f59cd0c00', 'zzb3', 'e10adc3949ba59abbe56e057f20f883e', '1992-04-05 00:00:00', null, null, '2014-09-18 13:33:12', '2', '测试3', 'nick', '3', '739934487@qq.com', '158642455', '15868870215', 'Y', null, '1');
INSERT INTO `U_USER` VALUES ('fdfdad', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('fdfds', 'ssss', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('fdfdsds', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('fdsfdfdds', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('fdssd', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('ffdsaddssdsdsds', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('ffdsfdssd', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('ffdss', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('ffdssdsdsds', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('ffdssdss', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('sds22Ssaffd', 'ssss', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('sdsd2Ssas', 'ssss', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('sdsdsa', 'ssss', null, null, null, null, null, null, 'ssdsd', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('sdsdSsa', 'ssss', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('sSSsa', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('sSSsasd', 'ssss', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('s到底是', 'ssss', null, null, null, null, null, null, 'xsxsxsx', null, null, null, null, null, null, null, '1');
INSERT INTO `U_USER` VALUES ('ZZ', 'zzb9', 'e10adc3949ba59abbe56e057f20f883e', '1992-04-05 00:00:00', null, null, '2014-09-18 09:55:54', '2', '测试3', 'nick', '1', '739934487@qq.com', '158642455', '15868870215', 'Y', null, '1');
