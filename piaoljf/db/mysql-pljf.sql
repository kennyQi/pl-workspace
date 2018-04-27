/*
Navicat MySQL Data Transfer

Source Server         : pljfdev-mysql
Source Server Version : 50619
Source Host           : 192.168.2.34:3306
Source Database       : pljf_dev

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2014-11-18 20:08:24

11.19 update_time`,flow_time`,cal_time` 改为 timestamp
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth_perm
-- ----------------------------
DROP TABLE IF EXISTS `auth_perm`;
CREATE TABLE `auth_perm` (
  `ID` varchar(64) NOT NULL,
  `DISPLAY_NAME` varchar(96) DEFAULT NULL,
  `PARENT_ID` varchar(64) DEFAULT NULL,
  `PERM_ROLE` varchar(96) DEFAULT NULL,
  `PERM_TYPE` decimal(5,0) DEFAULT NULL,
  `URL` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_perm
-- ----------------------------

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `ID` varchar(64) NOT NULL,
  `DISPLAY_NAME` varchar(96) DEFAULT NULL,
  `ROLE_NAME` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_role
-- ----------------------------

-- ----------------------------
-- Table structure for auth_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_perm`;
CREATE TABLE `auth_role_perm` (
  `ROLE_ID` varchar(64) NOT NULL,
  `PERM_ID` varchar(64) NOT NULL,
  PRIMARY KEY (`PERM_ID`,`ROLE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_role_perm
-- ----------------------------

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `ID` varchar(64) NOT NULL,
  `DISPLAY_NAME` varchar(96) DEFAULT NULL,
  `ENABLE` decimal(5,0) DEFAULT NULL,
  `LOGIN_NAME` varchar(64) DEFAULT NULL,
  `PASSWD` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES ('034cef04b50a4d8da92e336535a04089', null, '1', 'admin', 'e10adc3949ba59abbe56e057f20f883e');

-- ----------------------------
-- Table structure for auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role` (
  `USER_ID` varchar(64) NOT NULL,
  `ROLE_ID` varchar(64) NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`USER_ID`),
  KEY `USER_ID` (`USER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for cal_flow
-- ----------------------------
DROP TABLE IF EXISTS `cal_flow`;
CREATE TABLE `cal_flow` (
  `cal_id` varchar(50) NOT NULL,
  `user` varchar(50) DEFAULT NULL,
  `rule` varchar(50) NOT NULL,
  `cal_time` timestamp DEFAULT NULL,
  `trade_flow` varchar(1024) NOT NULL,
  `jf` decimal(10,0) DEFAULT NULL,
  `result_code` varchar(2) DEFAULT NULL,
  `result_text` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`cal_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cal_flow
-- ----------------------------

-- ----------------------------
-- Table structure for cal_rule
-- ----------------------------
DROP TABLE IF EXISTS `cal_rule`;
CREATE TABLE `cal_rule` (
  `code` varchar(30) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `rule_status` varchar(1) DEFAULT NULL,
  `props` varchar(1024) DEFAULT NULL,
  `template_name` varchar(1024) DEFAULT NULL,
  `account_type` varchar(30) DEFAULT NULL,
  UNIQUE KEY `code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cal_rule
-- ----------------------------

-- ----------------------------
-- Table structure for cal_session
-- ----------------------------
DROP TABLE IF EXISTS `cal_session`;
CREATE TABLE `cal_session` (
  `rule_code` varchar(50) NOT NULL,
  `user_code` varchar(50) NOT NULL,
  `props` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`rule_code`,`user_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cal_session
-- ----------------------------

-- ----------------------------
-- Table structure for jf_account
-- ----------------------------
DROP TABLE IF EXISTS `jf_account`;
CREATE TABLE `jf_account` (
  `id` varchar(50) NOT NULL,
  `user` varchar(50) DEFAULT NULL,
  `acct_type` varchar(20) DEFAULT NULL,
  `amount` decimal(10,0) DEFAULT NULL,
  `update_time` timestamp DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jf_account
-- ----------------------------

-- ----------------------------
-- Table structure for jf_flow
-- ----------------------------
DROP TABLE IF EXISTS `jf_flow`;
CREATE TABLE `jf_flow` (
  `id` varchar(50) NOT NULL,
  `acct_id` varchar(50) DEFAULT NULL,
  `flow_time` timestamp DEFAULT NULL,
  `trade_type` varchar(10) DEFAULT NULL,
  `trade_amount` decimal(10,0) DEFAULT NULL,
  `trade_remark` varchar(512) DEFAULT NULL,
  `batch_no` varchar(20) DEFAULT NULL,
  `ref_flow_id` varchar(20) DEFAULT NULL,
  `valid_date` date DEFAULT NULL,
  `user` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jf_flow
-- ----------------------------

-- ----------------------------
-- Table structure for jf_user
-- ----------------------------
DROP TABLE IF EXISTS `jf_user`;
CREATE TABLE `jf_user` (
  `code` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jf_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_staff
-- ----------------------------
DROP TABLE IF EXISTS `sys_staff`;
CREATE TABLE `sys_staff` (
  `ID` varchar(64) NOT NULL,
  `EMAIL` varchar(128) DEFAULT NULL,
  `MOBILE` varchar(64) DEFAULT NULL,
  `REAL_NAME` varchar(64) DEFAULT NULL,
  `TEL` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_staff
-- ----------------------------
INSERT INTO `sys_staff` VALUES ('034cef04b50a4d8da92e336535a04089', null, null, 'admin', null);
