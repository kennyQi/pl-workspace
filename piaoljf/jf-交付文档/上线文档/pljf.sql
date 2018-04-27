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
  `cal_id` varchar(50) NOT NULL COMMENT '计算流水ID',
  `user` varchar(50) DEFAULT NULL COMMENT '用户编号',
  `rule` varchar(50) NOT NULL COMMENT '规则编号',
  `cal_time` timestamp NULL DEFAULT NULL COMMENT '计算时间',
  `trade_flow` varchar(1024) NOT NULL COMMENT '依据的计算流水',
  `jf` decimal(10,0) DEFAULT NULL COMMENT '输出的积分',
  `result_code` varchar(2) DEFAULT NULL COMMENT '计算结果码',
  `result_text` varchar(1024) DEFAULT NULL COMMENT '计算结果信息',
  PRIMARY KEY (`cal_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='计算流水';

-- ----------------------------
-- Table structure for cal_rule
-- ----------------------------
DROP TABLE IF EXISTS `cal_rule`;
CREATE TABLE `cal_rule` (
  `code` varchar(30) DEFAULT NULL COMMENT '规则ID',
  `name` varchar(100) DEFAULT NULL COMMENT '规则名称',
  `start_date` date DEFAULT NULL COMMENT '规则生效日期',
  `end_date` date DEFAULT NULL COMMENT '规则失效日期',
  `rule_status` varchar(1) DEFAULT NULL COMMENT '规则状态',
  `props` varchar(1024) DEFAULT NULL COMMENT '规则参数',
  `template_name` varchar(1024) DEFAULT NULL COMMENT '规则模版名称',
  `account_type` varchar(30) DEFAULT NULL COMMENT '规则产生积分类型',
  UNIQUE KEY `code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='计算规则';

-- ----------------------------
-- Table structure for cal_session
-- ----------------------------
DROP TABLE IF EXISTS `cal_session`;
CREATE TABLE `cal_session` (
  `rule_code` varchar(50) NOT NULL COMMENT '规则编号',
  `user_code` varchar(50) NOT NULL COMMENT '用户编号',
  `props` varchar(1024) DEFAULT NULL COMMENT '中间数据',
  PRIMARY KEY (`rule_code`,`user_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='中间数据';

-- ----------------------------
-- Table structure for jf_account
-- ----------------------------
DROP TABLE IF EXISTS `jf_account`;
CREATE TABLE `jf_account` (
  `id` varchar(50) NOT NULL COMMENT '账户ID',
  `user` varchar(50) DEFAULT NULL COMMENT '积分用户名',
  `acct_type` varchar(20) DEFAULT NULL COMMENT '账户类型',
  `amount` decimal(10,0) DEFAULT NULL COMMENT '积分值',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='积分账户';

-- ----------------------------
-- Table structure for jf_flow
-- ----------------------------
DROP TABLE IF EXISTS `jf_flow`;
CREATE TABLE `jf_flow` (
  `id` varchar(50) NOT NULL COMMENT '流水ID',
  `acct_id` varchar(50) DEFAULT NULL COMMENT '积分账户',
  `flow_time` datetime DEFAULT NULL COMMENT '流水生成时间',
  `trade_type` varchar(10) DEFAULT NULL COMMENT '积分交易类型',
  `trade_amount` decimal(10,0) DEFAULT NULL COMMENT '积分值',
  `trade_remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `batch_no` varchar(20) DEFAULT NULL COMMENT '批次号',
  `ref_flow_id` varchar(20) DEFAULT NULL COMMENT '依据的流水ID',
  `valid_date` date DEFAULT NULL COMMENT '依据的流水数据',
  `user` varchar(30) DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='积分账户流水';

-- ----------------------------
-- Table structure for jf_user
-- ----------------------------
DROP TABLE IF EXISTS `jf_user`;
CREATE TABLE `jf_user` (
  `code` varchar(50) NOT NULL COMMENT '用户id',
  `name` varchar(50) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '用户名',
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='积分用户';

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
