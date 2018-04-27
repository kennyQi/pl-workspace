/*
Navicat MySQL Data Transfer

Source Server         : pljfdev-mysql
Source Server Version : 50619
Source Host           : 192.168.2.34:3306
Source Database       : pljf_dev

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2014-12-15 16:41:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Records of auth_user
-- ----------------------------
BEGIN;
INSERT INTO `auth_user` VALUES ('034cef04b50a4d8da92e336535a04089', null, '1', 'admin', 'e10adc3949ba59abbe56e057f20f883e');
COMMIT;

-- ----------------------------
-- Records of sys_staff
-- ----------------------------
BEGIN;
INSERT INTO `sys_staff` VALUES ('034cef04b50a4d8da92e336535a04089', null, null, 'admin', null);
COMMIT;
