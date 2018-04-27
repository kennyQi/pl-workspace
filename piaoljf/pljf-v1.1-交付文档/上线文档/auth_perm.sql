/*
Navicat MySQL Data Transfer

Source Server         : pljfdev-mysql
Source Server Version : 50619
Source Host           : 192.168.2.34:3306
Source Database       : pljf_dev

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2015-01-08 17:07:45
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
INSERT INTO `auth_perm` VALUES ('496dedbd6f2141ce8f6e43e3287d038c', '积分查询', '', '', '0', '/account/jf/query');
INSERT INTO `auth_perm` VALUES ('50b8c68012434cb68c7376f5a3b52f6a', '调整(已存在账户)', '496dedbd6f2141ce8f6e43e3287d038c', '', '0', '/account/jf/toAdjust');
INSERT INTO `auth_perm` VALUES ('04637d0002bf4061b5bf1d1f0fec51f8', '调整(任意账户)', '496dedbd6f2141ce8f6e43e3287d038c', '', '0', '/service/toAdjustJF');
INSERT INTO `auth_perm` VALUES ('0da84c254541440397762d656a7b5686', '批量调整', '496dedbd6f2141ce8f6e43e3287d038c', '', '0', '/service/toBatchAdjustJF');
INSERT INTO `auth_perm` VALUES ('2f12ae5ef3314cb99afc547d90e9a2e7', '添加资源', 'b04efb407ab9445cbc58917429ccc984', '', '0', '/auth/perm/add');
INSERT INTO `auth_perm` VALUES ('0ef8f7c298a74794bbe1135a254b6518', '积分流水查询', '', '', '0', '/account/jf/flow');
INSERT INTO `auth_perm` VALUES ('0cc7523259d7415bb1475f13b72d4f5f', '积分对账单查询', '', '', '0', '/account/jf/check');
INSERT INTO `auth_perm` VALUES ('9a9eb90f5b0a4e5d8ae26481b48619fd', '模版管理', '', '', '0', '/template/list');
INSERT INTO `auth_perm` VALUES ('14500d42b0ee414881bdd81e129ce195', '规则管理', '', '', '0', '/rule/list');
INSERT INTO `auth_perm` VALUES ('0527367ebc52446bbe734456acb80936', '计算管理', '', '', '0', '/cal/list');
INSERT INTO `auth_perm` VALUES ('d8ab1955d00d43efabc257fa1c725f56', '添加操作员', '5658d99ed0744d249d79bf6e8541847c', '', '0', '/system/operator/add');
INSERT INTO `auth_perm` VALUES ('b04efb407ab9445cbc58917429ccc984', '资源管理', '', '', '0', '/auth/perm/index');
INSERT INTO `auth_perm` VALUES ('03418b8dfeb546c3be3943a7e8f0ea97', '角色管理', '', '', '0', '/auth/role/list');
INSERT INTO `auth_perm` VALUES ('5658d99ed0744d249d79bf6e8541847c', '操作员管理', '', '', '0', '/system/operator/list');
INSERT INTO `auth_perm` VALUES ('3746b35fef8d40ad91e115f08a9553f9', '添加模版', '9a9eb90f5b0a4e5d8ae26481b48619fd', '', '0', '/template/to_add');
INSERT INTO `auth_perm` VALUES ('29ce7936bf774a15bd7831ff3fe3934b', '下载模版', '9a9eb90f5b0a4e5d8ae26481b48619fd', '', '0', '/template/download');
INSERT INTO `auth_perm` VALUES ('b311c085a9c04b758a8600a7a5e02f0f', '删除模版', '9a9eb90f5b0a4e5d8ae26481b48619fd', '', '0', '/template/delete');
INSERT INTO `auth_perm` VALUES ('b496c238caa24b78b340531f1bace440', '删除资源', 'b04efb407ab9445cbc58917429ccc984', '', '0', '/auth/perm/del');
INSERT INTO `auth_perm` VALUES ('d0bdfc1594d447f39fb27b0b79fe0c0c', '编辑资源', 'b04efb407ab9445cbc58917429ccc984', '', '0', '/auth/perm/edit');
INSERT INTO `auth_perm` VALUES ('acb712a893c54746a9d8c7f7ea7d0bea', '增加资源', '03418b8dfeb546c3be3943a7e8f0ea97', '', '0', '/auth/perm/add');
INSERT INTO `auth_perm` VALUES ('da1f09c5a733463e818502c613940aaa', '修改角色', '03418b8dfeb546c3be3943a7e8f0ea97', '', '0', '/auth/role/edit');
INSERT INTO `auth_perm` VALUES ('3b09a8ef8d0a4e1ea4ded77e7faa47d5', '删除角色', '03418b8dfeb546c3be3943a7e8f0ea97', '', '0', '/auth/role/del');
INSERT INTO `auth_perm` VALUES ('8e1f7870e3bc4fcda0d6f19e9d703f35', '计算日志查看', '0527367ebc52446bbe734456acb80936', '', '0', '/cal/list');
INSERT INTO `auth_perm` VALUES ('49ab7df278774fdaacec8f0b391ef0f3', '中间数据查看', '0527367ebc52446bbe734456acb80936', '', '0', '/session/list');
INSERT INTO `auth_perm` VALUES ('dce6bfd77ecc43e29c6ccc075e93d18a', '新建规则', '14500d42b0ee414881bdd81e129ce195', '', '0', '/rule/create');
INSERT INTO `auth_perm` VALUES ('012bed7247bc41708ca11e0566316a6e', '编辑规则', '14500d42b0ee414881bdd81e129ce195', '', '0', '/rule/toEdit');
INSERT INTO `auth_perm` VALUES ('021b2cb590d94d65a4b5557aec569de6', '升级规则', '14500d42b0ee414881bdd81e129ce195', '', '0', '/rule/toUpgrade');
INSERT INTO `auth_perm` VALUES ('2a1908568d0f47739e572fed86f92c1a', '作废规则', '14500d42b0ee414881bdd81e129ce195', '', '0', '/rule/cancel');
INSERT INTO `auth_perm` VALUES ('938ea74f968e47d58040f18e45dd3c38', '删除规则', '14500d42b0ee414881bdd81e129ce195', '', '0', '/rule/delete');
INSERT INTO `auth_perm` VALUES ('1cd7a5c73aaf4378bd7d4cd7a399dbd7', '重置密码', '5658d99ed0744d249d79bf6e8541847c', '', '0', '/system/operator/resetPwd');
INSERT INTO `auth_perm` VALUES ('f5230d2654304c3d8b95774b4b42f40e', '编辑操作员', '5658d99ed0744d249d79bf6e8541847c', '', '0', '/system/operator/edit');
INSERT INTO `auth_perm` VALUES ('36f49a61bdd848e38f2f27289741a635', '删除操作员', '5658d99ed0744d249d79bf6e8541847c', '', '0', '/system/operator/del');
INSERT INTO `auth_perm` VALUES ('c6dbd2ee364741e2af23c4ba058758d3', '启禁用操作员', '5658d99ed0744d249d79bf6e8541847c', '', '0', '/system/operator/modifyEnable');

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
INSERT INTO `auth_role` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'admin', 'admin');

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
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '012bed7247bc41708ca11e0566316a6e');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '021b2cb590d94d65a4b5557aec569de6');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '03418b8dfeb546c3be3943a7e8f0ea97');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '04637d0002bf4061b5bf1d1f0fec51f8');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '0527367ebc52446bbe734456acb80936');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '0cc7523259d7415bb1475f13b72d4f5f');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '0da84c254541440397762d656a7b5686');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '0ef8f7c298a74794bbe1135a254b6518');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '14500d42b0ee414881bdd81e129ce195');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '1cd7a5c73aaf4378bd7d4cd7a399dbd7');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '29ce7936bf774a15bd7831ff3fe3934b');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '2a1908568d0f47739e572fed86f92c1a');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '2f12ae5ef3314cb99afc547d90e9a2e7');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '36f49a61bdd848e38f2f27289741a635');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '3746b35fef8d40ad91e115f08a9553f9');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '3b09a8ef8d0a4e1ea4ded77e7faa47d5');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '496dedbd6f2141ce8f6e43e3287d038c');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '49ab7df278774fdaacec8f0b391ef0f3');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '50b8c68012434cb68c7376f5a3b52f6a');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '5658d99ed0744d249d79bf6e8541847c');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '8e1f7870e3bc4fcda0d6f19e9d703f35');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '938ea74f968e47d58040f18e45dd3c38');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', '9a9eb90f5b0a4e5d8ae26481b48619fd');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'acb712a893c54746a9d8c7f7ea7d0bea');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'b04efb407ab9445cbc58917429ccc984');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'b311c085a9c04b758a8600a7a5e02f0f');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'b496c238caa24b78b340531f1bace440');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'c6dbd2ee364741e2af23c4ba058758d3');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'd0bdfc1594d447f39fb27b0b79fe0c0c');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'd8ab1955d00d43efabc257fa1c725f56');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'da1f09c5a733463e818502c613940aaa');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'dce6bfd77ecc43e29c6ccc075e93d18a');
INSERT INTO `auth_role_perm` VALUES ('1b52167a3b2f4c42a7bcd307dfb5ff62', 'f5230d2654304c3d8b95774b4b42f40e');

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
INSERT INTO `auth_user_role` VALUES ('034cef04b50a4d8da92e336535a04089', '1b52167a3b2f4c42a7bcd307dfb5ff62');
