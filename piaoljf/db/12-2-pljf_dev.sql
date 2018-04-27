/*
Navicat MySQL Data Transfer

Source Server         : pljfdev-mysql
Source Server Version : 50619
Source Host           : 192.168.2.34:3306
Source Database       : pljf_dev

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2014-12-02 17:15:37
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
-- Records of cal_flow
-- ----------------------------
INSERT INTO `cal_flow` VALUES ('8adbea1fc885488c8ceea278878fdf6b', null, 'ruleSign001', '2014-11-19 00:00:01', '{\"date\":\"20141119\",\"tradeName\":\"sign\",\"user\":\"tom\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolSignLogic.compute(PiaolSignLogic.java:53)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('9a8ed36521344762a20f5553307b093e', null, 'ruleSign001', '2014-11-19 10:00:00', '{\"date\":\"20141119\",\"tradeName\":\"sign\",\"user\":\"tom\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolSignLogic.compute(PiaolSignLogic.java:56)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('3d3739322af642d79aaa7d9dbca099f9', null, 'ruleSign001', '2014-11-19 10:00:00', '{\"date\":\"20141119\",\"tradeName\":\"sign\",\"user\":\"tom\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolSignLogic.compute(PiaolSignLogic.java:56)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('d689d0180b1e44e78acdf6bb44440232', 'tom', 'ruleSign001', '2014-11-19 10:00:00', '{\"date\":\"20141119\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '100', 'Y', '普通会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('c44517260a434aeda35c7df3666bc1db', 'tom', 'ruleSign001', '2014-11-19 01:00:00', '{\"date\":\"20140110\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"silver\"}', '100', 'Y', '白银会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('bf02324413524694b4284b7bdb9076e6', 'tom', 'ruleSign001', '2014-11-19 10:00:00', '{\"date\":\"20140110\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"silver\"}', '0', 'Y', '白银会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('3055ed592d8f4082b6170bfbc5d11d75', 'tom', 'ruleSign001', '2014-11-19 01:00:00', '{\"date\":\"20141120\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '100', 'Y', '普通会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('9d2f9d47c006444883ed99928da34182', 'tom', 'ruleSign001', '2014-11-20 10:05:26', '{\"date\":\"20141120\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('761fe1540014416799bb01e965d41637', 'tom', 'ruleXDXF', '2014-11-19 17:06:27', '{\"date\":\"20140110\",\"birthday\":\"20141011\",\"tradeName\":\"consumption\",\"user\":\"tom\",\"money\":\"100\",\"level\":\"silver\",\"tradeWay\":\"computer\"}', '50', 'Y', '下单消费：100.0');
INSERT INTO `cal_flow` VALUES ('d63cd80c87c9423b82feac18a9f9b33c', 'pel', 'ruleXDXF', '2014-11-19 17:07:22', '{\"date\":\"20140110\",\"birthday\":\"20141011\",\"tradeName\":\"consumption\",\"user\":\"pel\",\"money\":\"100\",\"level\":\"silver\",\"tradeWay\":\"computer\"}', '50', 'Y', '下单消费：100.0');
INSERT INTO `cal_flow` VALUES ('1636998d524946c48a43834e14accbc5', 'pel', 'ruleDeal001', '2014-11-19 17:16:46', '{\"date\":\"20140110\",\"tradeName\":\"deal\",\"user\":\"pel\"}', '0', 'Y', '2014年1月,消费天数：1');
INSERT INTO `cal_flow` VALUES ('12ffb6ca0c9e4f4e95ff12a30c409ddc', 'pel', 'ruleDeal001', '2014-11-19 17:17:31', '{\"date\":\"20140111\",\"tradeName\":\"deal\",\"user\":\"pel\"}', '0', 'Y', '2014年1月,消费天数：2');
INSERT INTO `cal_flow` VALUES ('e16bc096fe7549d09da9ea0c84ce76e8', 'pel', 'ruleDeal001', '2014-11-19 17:17:38', '{\"date\":\"20140111\",\"tradeName\":\"deal\",\"user\":\"pel\"}', '0', 'Y', '在20140111,已经买过东西，不计入购物天数。');
INSERT INTO `cal_flow` VALUES ('103b99ac55c5410aa2597428c14cd645', null, 'ruleXDXF', '2014-11-19 17:54:20', '{\"date\":\"20140111\",\"user\":\"pel\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('3322ed6f0e014cb3b9bb0e54ede69f67', null, 'ruleDeal001', '2014-11-19 17:54:20', '{\"date\":\"20140111\",\"user\":\"pel\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('c0e81e30fcee41bdbe3b2bad6a8915ed', null, 'ruleXDXF', '2014-11-19 18:07:03', '{\"date\":\"20140111\",\"user\":\"pel\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('62cf068fe04f4423b2cc7f4f1be7a670', null, 'ruleDeal001', '2014-11-19 18:07:03', '{\"date\":\"20140111\",\"user\":\"pel\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('a6ecff33c2a042958866f535594b961f', null, 'ruleXDXF', '2014-11-19 18:10:34', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('09bc4d9e9075401a8ffe8c3009a08620', null, 'ruleDeal001', '2014-11-19 18:10:34', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('3ab800bfb40e4478a34d364f1589d533', null, 'ruleXDXF', '2014-11-19 18:12:47', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('307b177cd03c4b24925a481fedcbbb9a', null, 'ruleDeal001', '2014-11-19 18:12:56', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('a0a1137a56ed4e7d818bfe045c2ff77b', null, 'ruleXDXF', '2014-11-19 18:13:11', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('53d75d2772d24ae1b288ced00ca1605e', null, 'ruleDeal001', '2014-11-19 18:13:36', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('d19c80347e094b498f38c285552a5f1e', null, 'ruleXDXF', '2014-11-19 18:26:58', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('93fd13d652a94157ac64a4def907253d', null, 'ruleDeal001', '2014-11-19 18:26:58', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('29fa3b6b64194847ba36c7b5c4875b98', null, 'ruleXDXF', '2014-11-19 18:28:14', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('2636c186032c463ab773fab4aa06ea17', null, 'ruleDeal001', '2014-11-19 18:28:14', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('021f779fbf9b4966a3919f29d4c41c5f', null, 'ruleXDXF', '2014-11-19 18:28:42', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('eaaea109854a4ec7bc808a48b840656b', null, 'ruleDeal001', '2014-11-19 18:29:01', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('eee02133b3ec4093a626dcde8475ff08', null, 'ruleXDXF', '2014-11-19 18:29:48', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('e0c3bf85ea754585b696a63d8bc03a16', null, 'ruleDeal001', '2014-11-19 18:29:48', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('eeae6341c99d468b9e2b43919a6f414d', null, 'ruleXDXF', '2014-11-19 18:31:27', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('b3ad38f4d80342c78aacb7305d6f8fbc', null, 'ruleDeal001', '2014-11-19 18:31:27', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('9ef7e64c6b30476082c27c1320f60a37', null, 'ruleXDXF', '2014-11-19 18:31:49', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('2cb4ba0c95d140ddad14a170320ad204', null, 'ruleDeal001', '2014-11-19 18:31:49', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('69585c2cf48d46eabd3d9e26420589d9', null, 'ruleXDXF', '2014-11-19 18:34:50', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('5f01bf2734ff4f269f29bd2f097ff38a', null, 'ruleDeal001', '2014-11-19 18:34:50', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('cf025df95788493f847749fb3c641816', null, 'ruleXDXF', '2014-11-19 18:37:20', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('0dd6af7e892b4426b6e69adc519fe272', null, 'ruleDeal001', '2014-11-19 18:37:20', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('85ecbba5c6a34623b29d69b78a93a176', null, 'ruleXDXF', '2014-11-19 18:38:47', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('21d56346a57e41d98d210c54dda166af', null, 'ruleDeal001', '2014-11-19 18:38:47', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('b50cb3f9d255467d9c95b5a51c282735', null, 'ruleXDXF', '2014-11-19 18:39:55', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('e23839f37fdb4b5d9c886e58564617f8', null, 'ruleDeal001', '2014-11-19 18:39:55', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('3e662e0dd54449349a88dd0894925792', null, 'ruleXDXF', '2014-11-19 18:40:57', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('014d8f3183b94e1bafbb33ef04513ac2', null, 'ruleDeal001', '2014-11-19 18:40:57', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('3bc040bca6384d4dba9a4a6367e8a871', 'tom', 'sign', '2014-11-19 18:46:37', '{\"date\":\"20141120\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '5', 'Y', '普通会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('4897c86c16004d63ac1ffb8c4efff144', 'tom', 'sign', '2014-11-19 18:48:36', '{\"date\":\"20141120\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('b9bbb518db934f3fb6ead5f6db93a9f1', 'tom', 'sign', '2014-11-19 18:49:37', '{\"date\":\"20141120\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('67e5e5f79e5e4e35ac80805723615c9b', 'tom', 'sign', '2014-11-19 18:49:45', '{\"date\":\"20141121\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '5', 'Y', '普通会员，本月连续签到天数：2');
INSERT INTO `cal_flow` VALUES ('18408e37051f4472b2b892a4f383bdd0', null, 'ruleXDXF', '2014-11-19 18:49:52', '{\"date\":\"20141121\",\"tradeName1\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('dc0753b5c9a5499c9856c15207118471', null, 'ruleDeal001', '2014-11-19 18:49:52', '{\"date\":\"20141121\",\"tradeName1\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('96d5e57b67114e8093ae8ff8fa758d9c', null, 'ruleXDXF', '2014-11-19 18:55:02', '{\"date\":\"20141121\",\"tradeName1\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic.compute(PiaolConsumptionLogic.java:47)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocatio');
INSERT INTO `cal_flow` VALUES ('fce13f616bae4c10a75046d506029195', null, 'ruleDeal001', '2014-11-19 18:55:02', '{\"date\":\"20141121\",\"tradeName1\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('7fa2042361804a5e87d89015963b5e6f', 'tom', 'sign', '2014-11-19 18:55:11', '{\"date\":\"20141121\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：2');
INSERT INTO `cal_flow` VALUES ('38325b831578425991b4c1c9e855ca94', null, 'sign', '2014-11-20 09:35:59', '{\"date\":\"20140110\",\"tradeName\":\"sign\",\"user\":\"tom\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolSignLogic.compute(PiaolSignLogic.java:56)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('5b5315cd10e941429475f4841b7b004c', 'tom', 'sign', '2014-11-20 09:36:45', '{\"date\":\"20141121\",\"tradeName\":\"sign\",\"user\":\"tom\",\"level\":\"normal\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：2');
INSERT INTO `cal_flow` VALUES ('6e71f53e18d94e909f4491d22e539be0', 'tom1', 'sign', '2014-11-20 09:36:59', '{\"date\":\"20141121\",\"tradeName\":\"sign\",\"user\":\"tom1\",\"level\":\"normal\"}', '5', 'Y', '普通会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('ec58c0df2eb740f3978ae55badb3adac', 'pel', 'ruleXDXF', '2014-11-21 10:05:49', '{\"date\":\"20140110\",\"birthday\":\"20141011\",\"tradeName\":\"consumption\",\"user\":\"pel\",\"money\":\"100\",\"level\":\"silver\",\"tradeWay\":\"computer\"}', '50', 'Y', '下单消费：100.0');
INSERT INTO `cal_flow` VALUES ('b1597211cbc847f09c7772a0d1f9afe4', null, 'sign', '2014-11-26 17:37:39', '{\"date\":\"20140111\",\"tradeName\":\"sign\",\"user\":\"pel\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolSignLogic.compute(PiaolSignLogic.java:56)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('7928200dce654ab7b3132c77773a206e', null, '333333333', '2014-11-26 17:37:39', '{\"date\":\"20140111\",\"tradeName\":\"sign\",\"user\":\"pel\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolSignLogic.compute(PiaolSignLogic.java:56)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('cdd286f1d19f4f7991d40be57850c9c9', null, 'dsfdsf', '2014-11-26 17:37:39', '{\"date\":\"20140111\",\"tradeName\":\"sign\",\"user\":\"pel\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolSignLogic.compute(PiaolSignLogic.java:56)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('0f33ab2ae8404d5f96d59682beab78c4', 'pel', 'sign', '2014-11-26 17:43:53', '{\"date\":\"20140111\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('d8d7e934f8e5419498532c4788bc221f', 'pel', '333333333', '2014-11-26 17:43:53', '{\"date\":\"20140111\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('08a47a79d7fe4a7383ab91f8a6b7cc4f', null, 'dsfdsf', '2014-11-26 17:43:53', '{\"date\":\"20140111\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '0', 'N', 'java.lang.NumberFormatException: For input string: \"\"\r\n	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)\r\n	at java.lang.Integer.parseInt(Integer.java:504)\r\n	at java.lang.Integer.parseInt(Integer.java:527)\r\n	at hgtech.jf.piaol.rulelogic.PiaolSignLogic.compute(PiaolSignLogic.java:73)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastCl');
INSERT INTO `cal_flow` VALUES ('22e421e0f1ad4959bc1e5845dc909db1', 'pel', 'sign', '2014-11-26 17:49:23', '{\"date\":\"20140111\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('fb729f63b1774ccdb97c99b8b7bd561b', 'pel', '333333333', '2014-11-26 17:49:23', '{\"date\":\"20140111\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('82866d81524c4b419c755d3c918dc973', 'pel', 'sign', '2014-11-26 17:49:43', '{\"date\":\"20141126\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('6e5793b1b32c4002b44f5b6ea3e835e3', 'pel', '333333333', '2014-11-26 17:49:43', '{\"date\":\"20141126\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('fc8b317eb47f4b239d21dee43f94c993', 'pel', 'sign', '2014-11-26 17:56:41', '{\"date\":\"20141126\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('9fcc9afbed584ddfb34c2458af398fb4', 'pel', '333333333', '2014-11-26 17:56:41', '{\"date\":\"20141126\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('b4ecddf302884038acfc9eed2556e073', 'pel', 'sign', '2014-11-26 18:09:54', '{\"date\":\"20141127\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：2');
INSERT INTO `cal_flow` VALUES ('fda7c74e621847ef88c868f4a33680dd', 'pel', '333333333', '2014-11-26 18:09:54', '{\"date\":\"20141127\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：2');
INSERT INTO `cal_flow` VALUES ('7b0ee1f2b5434467a4ee0e3c74b2589c', 'pel', 'sign', '2014-11-26 18:15:11', '{\"date\":\"20141128\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：3');
INSERT INTO `cal_flow` VALUES ('2e2311a236e943c4a8659053b6509e78', 'pel', '333333333', '2014-11-26 18:15:11', '{\"date\":\"20141128\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：3');
INSERT INTO `cal_flow` VALUES ('4ed82830d5ff49a8a1a9f4c3e542d034', 'pel', 'sign', '2014-11-26 18:23:03', '{\"date\":\"20141128\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：3');
INSERT INTO `cal_flow` VALUES ('3a9fd821684348f78b288a3be538473f', 'pel', '333333333', '2014-11-26 18:23:03', '{\"date\":\"20141128\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：3');
INSERT INTO `cal_flow` VALUES ('211d670d165343f4855dd8db6a493c8d', 'pel', 'sign', '2014-11-26 18:23:08', '{\"date\":\"20141129\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：4');
INSERT INTO `cal_flow` VALUES ('308199e0eae9457e9c70b9833086a435', 'pel', '333333333', '2014-11-26 18:23:08', '{\"date\":\"20141129\",\"tradeName\":\"sign\",\"user\":\"pel\":\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：4');
INSERT INTO `cal_flow` VALUES ('c28f48f948d247febeeb5a0daf6c79ac', 'a', 'sign', '2014-11-26 18:26:49', '{\"date\":\"20141129\",\"tradeName\":\"sign\",\"user\":\"a\":\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('386586e9715e46e995b8071aee1bd4cd', 'a', '333333333', '2014-11-26 18:26:49', '{\"date\":\"20141129\",\"tradeName\":\"sign\",\"user\":\"a\":\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('f0e73750c6ec4ed4b66ef4267ad6ad1d', null, 'ruleDeal002', '2014-12-01 14:56:25', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"c\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolDealLogic.compute(PiaolDealLogic.java:51)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpo');
INSERT INTO `cal_flow` VALUES ('51debc8a41d6462bba379ddf4b48eb96', null, '222222', '2014-12-01 14:56:25', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"c\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolInviteFriendsLogic.compute(PiaolInviteFriendsLogic.java:20)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvoc');
INSERT INTO `cal_flow` VALUES ('fb9ec29c53d345efabc7f5216e4f004e', null, '565675675', '2014-12-01 14:56:25', '{\"date\":\"20141120\",\"tradeName4\":\"sign\",\"user\":\"c\",\"level\":\"normal\"}', '0', 'N', 'java.lang.NullPointerException\r\n	at hgtech.jf.piaol.rulelogic.PiaolInviteFriendsLogic.compute(PiaolInviteFriendsLogic.java:20)\r\n	at hgtech.jfcal.model.CalModel.cal(CalModel.java:56)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp.calAndCommit(JfCalServiceImp.java:148)\r\n	at hgtech.jfadmin.service.imp.JfCalServiceImp$$FastClassByCGLIB$$7f19e33a.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvoc');
INSERT INTO `cal_flow` VALUES ('a6095240bab840a08c26e30e84eda6b3', 'c', 'S1', '2014-12-01 14:58:46', '{\"date\":\"20141120\",\"tradeName\":\"sign\",\"user\":\"c\",\"level\":\"normal\"}', '5', 'Y', '普通会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('02972310e72343f18c5db4a5a2859905', 'c', 'S1', '2014-12-01 15:02:19', '{\"date\":\"20141120\",\"tradeName\":\"sign\",\"user\":\"c\",\"level\":\"normal\"}', '0', 'Y', '普通会员，当天已经签到，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('aa209dd006bd44d4b7301cd12a606272', 'c', 'S1', '2014-12-01 15:02:38', '{\"date\":\"20141121\",\"tradeName\":\"sign\",\"user\":\"c\",\"level\":\"normal\"}', '5', 'Y', '普通会员，本月连续签到天数：2');
INSERT INTO `cal_flow` VALUES ('98c55ab8da484062a53c21937d253a48', 'c', 'S1', '2014-12-01 15:02:46', '{\"date\":\"20141122\",\"tradeName\":\"sign\",\"user\":\"c\",\"level\":\"normal\"}', '5', 'Y', '普通会员，本月连续签到天数：3');
INSERT INTO `cal_flow` VALUES ('cecb20be050a4911a14db2ae62b71f33', 'd', 'S1', '2014-12-02 14:05:03', '{\"date\":\"20140110\",\"tradeName\":\"sign\",\"user\":\"d\",\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：1');
INSERT INTO `cal_flow` VALUES ('17415a7198bc4fb1a36171a7514e542c', 'd', 'S1', '2014-12-02 14:05:13', '{\"date\":\"20140111\",\"tradeName\":\"sign\",\"user\":\"d\",\"level\":\"nor\"}', '5', 'Y', '普通会员，本月连续签到天数：2');

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
-- Records of cal_rule
-- ----------------------------
INSERT INTO `cal_rule` VALUES ('ruleSign001', 'ruleSign001', '2014-11-14', '2014-11-21', 'N', '{\"siljf\":\"100\",\"goljf\":\"120\",\"ptjf\":\"140\",\"diajf\":\"160\",\"norjf\":\"100\",\"jfRatio1\":\"1.5\",\"jfRatio2\":\"2.0\"}', 'hgtech.jf.piaol.rulelogic.PiaolSignLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('ruleEvent001', 'ruleEvent001', '2014-10-28', '2014-11-27', 'N', '{\"phBindingIncrement\":\"100\",\"perfectInfoIncrement\":\"200\",\"mailBindingIncrement\":\"300\",\"registerIncrement\":\"1000\"}', 'hgtech.jf.piaol.rulelogic.PiaolEventLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('ruleSign002', 'ruleSign002', '2014-10-28', '2014-11-06', 'Y', '{\"siljf\":\"100\",\"goljf\":\"120\",\"ptjf\":\"140\",\"diajf\":\"160\",\"norjf\":\"100\",\"jfRatio1\":\"1.5\",\"jfRatio2\":\"2.0\"}', 'hgtech.jf.piaol.rulelogic.PiaolSignLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('ruleDeal002', 'ruleDeal002', '2014-11-27', '2014-12-05', 'Y', '{\"totleDays\":\"5\",\"IntegralIncrement\":\"1000\"}', 'hgtech.jf.piaol.rulelogic.PiaolDealLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('ruleXDXF', 'ruleXDXF', '2014-11-18', '2014-11-28', 'Y', '{\"wirelessRatio\":\"2\",\"privilegeRatio\":\"2\",\"jfRatio\":\"0.5\",\"birthdayRatio\":\"2\"}', 'hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('ruleDeal001', 'ruleDeal001', '2014-11-12', '2014-11-29', 'Y', '{\"totleDays\":\"3\",\"IntegralIncrement\":\"1000\"}', 'hgtech.jf.piaol.rulelogic.PiaolDealLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('sign', 'signname', '2014-11-19', '2014-11-29', 'Y', '{\"siljf\":\"1\",\"goljf\":\"2\",\"ptjf\":\"3\",\"diajf\":\"4\",\"norjf\":\"5\",\"jfRatio1\":\"6\",\"jfRatio2\":\"7\"}', 'hgtech.jf.piaol.rulelogic.PiaolSignLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('sadfsafsa', 'sdfsafsafsdf', '2014-11-05', '2014-11-28', 'N', '{\"wirelessRatio\":\"2\",\"privilegeRatio\":\"\",\"jfRatio\":\"\",\"birthdayRatio\":\"\"}', 'hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('sdfsdfs', 'sdfsfsdfsdf', '2014-11-11', '2014-12-05', 'N', '{\"wirelessRatio\":\"2\",\"privilegeRatio\":\"\",\"jfRatio\":\"\",\"birthdayRatio\":\"\"}', 'hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('w3rwerwrwer', 'werwerwerw', '2014-11-27', '2014-11-27', 'Y', '{\"wirelessRatio\":\"3\",\"privilegeRatio\":\"33\",\"jfRatio\":\"3\",\"birthdayRatio\":\"3\"}', 'hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('333333333', '33333333333333333', '2014-11-21', '2014-11-29', 'Y', '{\"siljf\":\"54\",\"goljf\":\"5\",\"ptjf\":\"5\",\"diajf\":\"55\",\"norjf\":\"5\",\"jfRatio1\":\"5\",\"jfRatio2\":\"5\"}', 'hgtech.jf.piaol.rulelogic.PiaolSignLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('dsfdsf', 'sfsfsdfsdfs', '2014-10-28', '2014-12-03', 'N', '{\"siljf\":\"1\",\"goljf\":\"\",\"ptjf\":\"\",\"diajf\":\"\",\"norjf\":\"\",\"jfRatio1\":\"\",\"jfRatio2\":\"\"}', 'hgtech.jf.piaol.rulelogic.PiaolSignLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('dfsadfsf', 'sdfsdfs', '2014-11-04', '2014-11-20', 'Y', '{\"wirelessRatio\":\"1\",\"privilegeRatio\":\"\",\"jfRatio\":\"\",\"birthdayRatio\":\"\"}', 'hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('222222', '2222222222222', '2014-11-26', '2014-12-05', 'Y', '{\"integralIncrement\":\"2\"}', 'hgtech.jf.piaol.rulelogic.PiaolInviteFriendsLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('23434234', 'werwerwr', '2014-11-07', '2014-11-21', 'Y', '{\"siljf\":\"2\",\"goljf\":\"\",\"ptjf\":\"\",\"diajf\":\"\",\"norjf\":\"\",\"jfRatio1\":\"\",\"jfRatio2\":\"\"}', 'hgtech.jf.piaol.rulelogic.PiaolSignLogic', 'piaol__consume');
INSERT INTO `cal_rule` VALUES ('565675675', '7567567567', '2014-11-04', '2014-12-05', 'Y', '{\"integralIncrement\":\"5\"}', 'hgtech.jf.piaol.rulelogic.PiaolInviteFriendsLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('123321333', '123321333', '2014-11-28', '2014-11-29', 'Y', '{\"integralIncrement\":\"1\"}', 'hgtech.jf.piaol.rulelogic.PiaolInviteFriendsLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('34534535345345', '345345345', '2014-11-11', '2014-11-26', 'Y', '{\"wirelessRatio\":\"4\",\"privilegeRatio\":\"\",\"jfRatio\":\"\",\"birthdayRatio\":\"\"}', 'hgtech.jf.piaol.rulelogic.PiaolConsumptionLogic', 'piaol__grow');
INSERT INTO `cal_rule` VALUES ('S1', '不要删除，签到规则', '2014-12-01', '2014-12-30', 'Y', '{\"siljf\":\"1\",\"goljf\":\"2\",\"ptjf\":\"3\",\"diajf\":\"4\",\"norjf\":\"5\",\"jfRatio1\":\"6\",\"jfRatio2\":\"7\"}', 'hgtech.jf.piaol.rulelogic.PiaolSignLogic', 'piaol__grow');

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
-- Records of cal_session
-- ----------------------------
INSERT INTO `cal_session` VALUES ('ruleSign001', 'tom', '{\"@type\":\"java.util.HashMap\",\"countDays\":1,\"lastDate\":new Date(1416412800000)}');
INSERT INTO `cal_session` VALUES ('ruleDeal001', 'pel', '{\"@type\":\"java.util.HashMap\",\"countDays1\":0,\"countDays2\":2,\"recentDate\":new Date(1389283200000),\"shoppingDate\":[new Date(1389283200000),new Date(1389369600000)]}');
INSERT INTO `cal_session` VALUES ('sign', 'tom', '{\"@type\":\"java.util.HashMap\",\"countDays\":2,\"lastDate\":new Date(1416499200000)}');
INSERT INTO `cal_session` VALUES ('sign', 'tom1', '{\"@type\":\"java.util.HashMap\",\"countDays\":1,\"lastDate\":new Date(1416499200000)}');
INSERT INTO `cal_session` VALUES ('sign', 'pel', '{\"@type\":\"java.util.HashMap\",\"countDays\":4,\"lastDate\":new Date(1417190400000)}');
INSERT INTO `cal_session` VALUES ('333333333', 'pel', '{\"@type\":\"java.util.HashMap\",\"countDays\":4,\"lastDate\":new Date(1417190400000)}');
INSERT INTO `cal_session` VALUES ('sign', 'a', '{\"@type\":\"java.util.HashMap\",\"countDays\":1,\"lastDate\":new Date(1417190400000)}');
INSERT INTO `cal_session` VALUES ('333333333', 'a', '{\"@type\":\"java.util.HashMap\",\"countDays\":1,\"lastDate\":new Date(1417190400000)}');
INSERT INTO `cal_session` VALUES ('S1', 'c', '{\"@type\":\"java.util.HashMap\",\"countDays\":3,\"lastDate\":new Date(1416585600000)}');
INSERT INTO `cal_session` VALUES ('S1', 'd', '{\"@type\":\"java.util.HashMap\",\"countDays\":2,\"lastDate\":new Date(1389369600000)}');

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
-- Records of jf_account
-- ----------------------------
INSERT INTO `jf_account` VALUES ('', '﻿naruto', 'piaol__grow', '200', '2014-11-24');
INSERT INTO `jf_account` VALUES ('76813b5b2ddc4021837be355590a8e1c', 'sacaka', 'piaol__grow', '400', '2014-11-24');
INSERT INTO `jf_account` VALUES ('5e41d27a5a99487cade38e715bd195b8', 'you', 'piaol__grow', '1200', '2014-11-26');
INSERT INTO `jf_account` VALUES ('eccebf7b70c54cbabd9f3ec63d8c0a54', 'sakula', 'piaol__grow', '1000', '2014-11-24');
INSERT INTO `jf_account` VALUES ('fff5881e603b4155af1faab8c5645c70', 'naruto', 'piaol__grow', '-100', '2014-11-24');
INSERT INTO `jf_account` VALUES ('3518ae190c4b4dbdabbd2cad3e939447', 'naruto', 'piaol__consume', '-200', '2014-11-26');
INSERT INTO `jf_account` VALUES ('33c58d913fad48d0b8f44cc0289ba813', 'sacaka', 'piaol__consume', '400', '2014-11-26');
INSERT INTO `jf_account` VALUES ('56be29e0f7ab4cf9b035cbf950ad8ef5', 'you', 'piaol__consume', '1000', '2014-11-26');
INSERT INTO `jf_account` VALUES ('3002e2bb930d4f11b368b553e64df139', 'sakula', 'piaol__consume', '1000', '2014-11-26');
INSERT INTO `jf_account` VALUES ('f43a3e1ca00c4b70b8d050ea933a089a', '1', 'piaol__consume', '1', '2014-11-26');
INSERT INTO `jf_account` VALUES ('02a02cc195044b12b9af23f19c6e0bf8', '22223', 'piaol__grow', '32', '2014-11-26');
INSERT INTO `jf_account` VALUES ('60dc7a0e15854bc9a4d45cc2fe38b7ff', '34234234', 'piaol__grow', '22', '2014-11-26');
INSERT INTO `jf_account` VALUES ('8941ca0c9b414873a8fa7ab9a48fe5c2', 'pel', 'piaol__grow', '30', '2014-12-02');
INSERT INTO `jf_account` VALUES ('965a7f620131412c87d68204e2b524b3', 'a', 'piaol__grow', '5', '2014-12-01');
INSERT INTO `jf_account` VALUES ('7cba406fcc814bf186ea1912dfa753ae', 'b', 'piaol__grow', '5', '2014-11-27');
INSERT INTO `jf_account` VALUES ('19d12fdfcd9345499bf7df61b7bb2410', 'c', 'piaol__grow', '10', '2014-12-02');
INSERT INTO `jf_account` VALUES ('bba645d23fc443178456c92ece20b3fe', 'd', 'piaol__grow', '5', '2014-12-02');

-- ----------------------------
-- Table structure for jf_flow
-- ----------------------------
DROP TABLE IF EXISTS `jf_flow`;
CREATE TABLE `jf_flow` (
  `id` varchar(50) NOT NULL COMMENT '流水ID',
  `acct_id` varchar(50) NOT NULL COMMENT '积分账户',
  `flow_time` datetime DEFAULT NULL COMMENT '流水生成时间',
  `trade_type` varchar(10) NOT NULL COMMENT '积分交易类型',
  `trade_amount` decimal(10,0) DEFAULT NULL COMMENT '积分值',
  `trade_remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `batch_no` varchar(20) DEFAULT NULL COMMENT '批次号',
  `ref_flow_id` varchar(20) DEFAULT NULL COMMENT '依据的流水ID',
  `valid_date` date DEFAULT NULL COMMENT '失效日期',
  `user` varchar(30) NOT NULL COMMENT '用户名',
  `use_amount` decimal(10,0) DEFAULT NULL COMMENT '已使用积分',
  `status` varchar(5) DEFAULT NULL COMMENT '撤销：UNDO\r\n失效：EXP\r\n正常：(null)或NOR\r\n',
  `update_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='积分账户流水';

-- ----------------------------
-- Records of jf_flow
-- ----------------------------
INSERT INTO `jf_flow` VALUES ('b6dbd45dfd7743c5a341b9e4e8e3a88d', 'd1c73202d13c448b8edf08be55196edf', '2014-11-24 10:25:32', 'ADJ', '100', '', '1111234234', null, null, '﻿naruto', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('95e6aaee4dce4a47bb849ac5e1d17bab', '19d12fdfcd9345499bf7df61b7bb2410', '2014-12-01 15:09:17', 'ADJ', '1', '', null, null, '2017-12-01', 'c', '0', 'UNDO', '2014-12-02 11:05:41');
INSERT INTO `jf_flow` VALUES ('9c0de015792a4a4fb4d62d1af1a55ef0', '5e41d27a5a99487cade38e715bd195b8', '2014-11-24 10:25:32', 'ADJ', '500', '', '1111234234', null, null, 'you', '1', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('be6c310e3d9b413383714c7de0bb1702', 'eccebf7b70c54cbabd9f3ec63d8c0a54', '2014-11-24 10:25:32', 'ADJ', '500', '', '1111234234', null, null, 'sakula', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('8190a3275e824b79af199afab3a9eeec', 'fff5881e603b4155af1faab8c5645c70', '2014-11-24 10:39:50', 'ADJ', '-100', '', 'sdfsdfsdf', null, null, 'naruto', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('56e96b181e8a4af2a6056414d7202753', '19d12fdfcd9345499bf7df61b7bb2410', '2014-12-01 15:15:03', 'EX', '-3', 'duij', null, null, null, 'c', '0', 'UNDO', '2014-12-01 17:00:54');
INSERT INTO `jf_flow` VALUES ('aea953f6ca59471abe2b44931d37cbbf', '5e41d27a5a99487cade38e715bd195b8', '2014-11-24 10:39:51', 'ADJ', '500', '', 'sdfsdfsdf', null, null, 'you', null, 'NOR', null);
INSERT INTO `jf_flow` VALUES ('e45b7bf5975c451388fa6d67a314ae83', 'eccebf7b70c54cbabd9f3ec63d8c0a54', '2014-11-24 10:39:51', 'ADJ', '500', '', 'sdfsdfsdf', null, null, 'sakula', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('ed3757a7078a40a59a9f6e88718abc77', '5e41d27a5a99487cade38e715bd195b8', '2014-11-24 16:41:02', 'ADJ', '1', '', null, null, null, 'you', null, 'NOR', null);
INSERT INTO `jf_flow` VALUES ('8870a1262d8241e3bd17dd5cacf304ce', '5e41d27a5a99487cade38e715bd195b8', '2014-11-24 17:12:42', 'ADJ', '10', '', null, null, null, 'you', '1', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('2ebb3a1a5d314a0699b1acd46d191ee8', '5e41d27a5a99487cade38e715bd195b8', '2014-11-25 10:31:12', 'ADJ', '-11', '这个世界', null, null, null, 'you', '1', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('530fd61010ec4968a79cb0d44ce24923', '5e41d27a5a99487cade38e715bd195b8', '2014-11-25 14:29:56', 'ADJ', '11', '', null, null, null, 'you', '1', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('f60eafdaf424418a8f8d6886e2966d1d', '5e41d27a5a99487cade38e715bd195b8', '2014-11-25 14:31:10', 'ADJ', '11', '', null, null, null, 'you', '1', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('380f21996fec467299156cd711deb622', '5e41d27a5a99487cade38e715bd195b8', '2014-11-25 14:31:46', 'ADJ', '-11', '', null, null, null, 'you', '1', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('20731b0682e64f06a98ed3fb160e1785', '5e41d27a5a99487cade38e715bd195b8', '2014-11-25 16:31:29', 'ADJ', '11', '', null, null, null, 'you', '1', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('bc792c16089848b08d46be285e1e5665', '5e41d27a5a99487cade38e715bd195b8', '2014-11-26 09:34:55', 'ADJ', '-22', '', null, null, null, 'you', '1', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('ef7982369ff04906b9ae4cb9cebeb507', '5e41d27a5a99487cade38e715bd195b8', '2014-11-26 09:35:37', 'ADJ', '200', '', null, null, null, 'you', '1', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('6c25f85cf89c4b58bab17e9b22b3dd5a', '3518ae190c4b4dbdabbd2cad3e939447', '2014-11-26 17:23:10', 'ADJ', '-100', '积分调整', '2222222', null, null, ' ', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('1740a2d8eb5b476a89388d15d61c5c16', '19d12fdfcd9345499bf7df61b7bb2410', '2014-12-02 11:07:57', 'EX', '-1', 'duij', null, null, null, 'c', '0', 'UNDO', '2014-12-02 11:08:18');
INSERT INTO `jf_flow` VALUES ('cef574d020e141d0980d1d8f0d3ce6a0', '56be29e0f7ab4cf9b035cbf950ad8ef5', '2014-11-26 17:23:10', 'ADJ', '500', '积分调整', '2222222', null, null, 'you', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('6bfaea2b75554e22aa59d95375a81d24', '3002e2bb930d4f11b368b553e64df139', '2014-11-26 17:23:10', 'ADJ', '500', '积分调整', '2222222', null, null, 'sakula', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('5b7223a2b58947418d006a59e3568385', 'f43a3e1ca00c4b70b8d050ea933a089a', '2014-11-26 17:31:38', 'ADJ', '1', '12', null, null, null, '1', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('a7e8343b0d2c40fcab876881a0bd7461', '02a02cc195044b12b9af23f19c6e0bf8', '2014-11-26 17:35:58', 'ADJ', '32', '2342', null, null, null, '22223', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('d882e8483e8a465698b06cb001419457', '60dc7a0e15854bc9a4d45cc2fe38b7ff', '2014-11-26 17:36:25', 'ADJ', '22', '22', null, null, null, '34234234', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('e021cef25ff747a8a85f60c04b94b293', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:43:53', 'CAL', '0', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('68c28be1e7434a0aa42b452c09c6e2a2', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:43:53', 'CAL', '0', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('4ba7f1c7657f4ff798f670799da36659', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:49:23', 'CAL', '0', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('f038e8f7fdef41b89760400011ed4de1', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:49:23', 'CAL', '0', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('f15b8854f66d430795b2a6a6b83713ba', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:49:43', 'CAL', '5', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('887efbff88b942a6a7c718d175c141e5', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:49:43', 'CAL', '5', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('40fd04bba08c4003a483012bf370627f', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:52:40', 'ADJ', '8', '调整积分，测试 应该一笔扣5，一笔扣3', null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('786af38b449449a2afbd68d1d5d321ab', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:53:24', 'ADJ', '-8', '调整积分，测试 应该一笔扣5，一笔扣3 ', null, null, null, 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('313fdd4d1bac4230b608fc259f258700', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:57:09', 'CAL', '0', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('0362c190fddf4b0ab601c0715160ecd4', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:57:10', 'CAL', '0', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('0b692056c4334579ac0f2d9f3b20fb03', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 17:57:59', 'ADJ', '-8', '调整积分，测试 应该一笔扣5，一笔扣3 ', null, null, null, 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('74c491598d5d40c29df4d972107f3a3b', '3518ae190c4b4dbdabbd2cad3e939447', '2014-11-26 18:00:53', 'ADJ', '-100', '每个人都有', '3333333', null, null, 'naruto', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('7c1b71c3bb7e4240838738fdde2d3a2a', '19d12fdfcd9345499bf7df61b7bb2410', '2014-12-02 11:14:13', 'ADJ', '1', '', null, null, '2017-12-02', 'c', '0', 'UNDO', '2014-12-02 11:14:21');
INSERT INTO `jf_flow` VALUES ('f093c030df874445957021f2faf37f66', '56be29e0f7ab4cf9b035cbf950ad8ef5', '2014-11-26 18:00:53', 'ADJ', '500', '每个人都有', '3333333', null, null, 'you', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('809177539d4b446188f7aa5f6aea149e', '3002e2bb930d4f11b368b553e64df139', '2014-11-26 18:00:53', 'ADJ', '500', '每个人都有', '3333333', null, null, 'sakula', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('93ef8aed7afc4315be069f0cfcb51c78', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:09:58', 'CAL', '5', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('6b93cf07b0634fa0880a5c2d81d7d570', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:09:58', 'CAL', '5', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('c32dee7298d44c13b7f995cc7638d139', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:10:25', 'ADJ', '-8', '', null, null, null, 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('4aea22412e424b90b1897c142ba4a128', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:15:11', 'CAL', '5', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('007668032b0542768e967851527ac4f0', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:15:11', 'CAL', '5', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('048ffa092e95401fa3c01d56446c7ddc', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:15:22', 'ADJ', '-8', '', null, null, null, 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('29e2ecd0688047939f33de1dc31290d7', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:23:03', 'CAL', '0', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('a31468e9631248baa17ef2bf335221c2', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:23:03', 'CAL', '0', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('8c61148bd02646968521ec1c83261649', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:23:08', 'CAL', '5', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('6e5aadcb2f2c429fb96ce525fd0e13f8', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:23:08', 'CAL', '5', null, null, null, '2017-11-26', 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('2c8c28aee67648f1b8026edce6d2fcdf', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-11-26 18:23:18', 'ADJ', '-8', '', null, null, null, 'pel', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('9270fd6c0f624328af4c27ddb43baee6', '965a7f620131412c87d68204e2b524b3', '2014-11-26 18:26:49', 'CAL', '5', null, null, null, '2017-11-26', 'a', '5', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('50897dfeeda34839ac06cdec42245dd8', '965a7f620131412c87d68204e2b524b3', '2014-11-26 18:26:49', 'CAL', '5', null, null, null, '2017-11-26', 'a', '5', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('aaa20cffb38c4f22a6c19bea4654998b', '965a7f620131412c87d68204e2b524b3', '2014-11-26 18:26:58', 'ADJ', '-8', '', null, null, null, 'a', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('24843d98587847fb8a52908a0827fc90', '965a7f620131412c87d68204e2b524b3', '2014-11-27 11:31:26', 'ADJ', '-2', '', null, null, null, 'a', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('a2964fe0094b468d898cc779cf19c0fa', '965a7f620131412c87d68204e2b524b3', '2014-11-27 13:39:11', 'ADJ', '8', '', null, null, '2017-11-27', 'a', '8', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('6cf482b2e88f43a7ba1c68c7b9db1acc', '965a7f620131412c87d68204e2b524b3', '2014-11-27 13:41:36', 'ADJ', '-8', '', null, null, null, 'a', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('a744a741d74d49b0bd786efe52ddf0fe', '965a7f620131412c87d68204e2b524b3', '2014-11-27 13:49:09', 'ADJ', '10', '', null, null, '2017-11-27', 'a', '10', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('e5bf237dcde54788bd5e0be152fad737', '965a7f620131412c87d68204e2b524b3', '2014-11-27 13:49:17', 'ADJ', '-8', '', null, null, null, 'a', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('6d9f35160eb04cb5af09d98fe30992ce', '965a7f620131412c87d68204e2b524b3', '2014-11-27 13:52:06', 'ADJ', '8', '', null, null, '2017-11-27', 'a', '5', 'NOR', '2014-12-01 14:53:17');
INSERT INTO `jf_flow` VALUES ('91b1f48491714fc09a9e0356b174b1b4', '965a7f620131412c87d68204e2b524b3', '2014-11-27 13:52:16', 'ADJ', '-6', '', null, null, null, 'a', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('b5a888b23bc34911afff210dcbf17e30', '965a7f620131412c87d68204e2b524b3', '2014-11-27 13:59:54', 'ADJ', '2', '', null, null, '2017-11-27', 'a', '0', 'UNDO', '2014-11-27 14:00:05');
INSERT INTO `jf_flow` VALUES ('63ebed65bfdd4b4b98cc74f22425f1de', '965a7f620131412c87d68204e2b524b3', '2014-11-27 14:00:05', 'ADJ', '-6', '', null, null, null, 'a', '0', 'UNDO', null);
INSERT INTO `jf_flow` VALUES ('1eb3cb3d001b4c8d9dd6626e7771d610', '7cba406fcc814bf186ea1912dfa753ae', '2014-11-27 17:20:03', 'ADJ', '10', '', null, null, '2017-11-27', 'b', '10', 'NOR', '2014-11-27 17:21:15');
INSERT INTO `jf_flow` VALUES ('934809d1311f42a0acb562f48d5c3ba6', '7cba406fcc814bf186ea1912dfa753ae', '2014-11-27 17:20:08', 'ADJ', '10', '', null, null, '2017-11-27', 'b', '5', 'NOR', '2014-11-27 17:21:15');
INSERT INTO `jf_flow` VALUES ('796a88e6701d479db0321620c90c82a6', '7cba406fcc814bf186ea1912dfa753ae', '2014-11-27 17:21:15', 'ADJ', '-15', '', null, null, null, 'b', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('2e417fc989ff46eeb49233bd03231dcf', '965a7f620131412c87d68204e2b524b3', '2014-12-01 14:48:37', 'ADJ', '1', '', null, null, '2017-12-01', 'a', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('a1f499a12ca941afaa0d3a088b375c9f', '965a7f620131412c87d68204e2b524b3', '2014-12-01 14:53:17', 'ADJ', '-1', '', null, null, null, 'a', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('080058b0141a48edb94abbaf53f9a013', '965a7f620131412c87d68204e2b524b3', '2014-12-01 14:53:22', 'ADJ', '1', '', null, null, '2017-12-01', 'a', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('01a5af5f6ec34562a881290b0833189d', '19d12fdfcd9345499bf7df61b7bb2410', '2014-12-01 15:02:19', 'CAL', '5', null, null, null, '2017-12-01', 'c', '0', 'NOR', '2014-12-02 11:07:57');
INSERT INTO `jf_flow` VALUES ('05d5cd056dc04ab498b67cc64defb51f', '19d12fdfcd9345499bf7df61b7bb2410', '2014-12-01 15:02:38', 'CAL', '5', null, null, null, '2017-12-01', 'c', '0', 'NOR', null);
INSERT INTO `jf_flow` VALUES ('e8b29ac5e63a41d18c96fad9ce4a751c', '19d12fdfcd9345499bf7df61b7bb2410', '2014-12-01 15:02:46', 'CAL', '5', null, null, null, '2017-12-01', 'c', '0', 'UNDO', '2014-12-01 16:51:35');
INSERT INTO `jf_flow` VALUES ('e94a563fe95642089af997a1e6593832', '19d12fdfcd9345499bf7df61b7bb2410', '2014-12-02 11:16:08', 'ADJ', '1', '', null, null, '2017-12-02', 'c', '0', 'UNDO', '2014-12-02 11:16:15');
INSERT INTO `jf_flow` VALUES ('089c671e06e848b7b859c17e9a228d9c', 'bba645d23fc443178456c92ece20b3fe', '2014-12-02 14:05:03', 'CAL', '5', null, null, null, '2017-12-02', 'd', '0', 'UNDO', '2014-12-02 14:24:16');
INSERT INTO `jf_flow` VALUES ('18f73868b04742d88028f4879f704874', 'bba645d23fc443178456c92ece20b3fe', '2014-12-02 14:05:13', 'CAL', '5', null, null, null, '2017-12-02', 'd', '0', 'NOR', '2014-12-02 14:07:18');
INSERT INTO `jf_flow` VALUES ('443937a3958d42e1a671b9a3f975bd6a', 'bba645d23fc443178456c92ece20b3fe', '2014-12-02 14:07:18', 'EX', '-8', 'duij', null, null, null, 'd', '0', 'UNDO', '2014-12-02 14:18:19');
INSERT INTO `jf_flow` VALUES ('ed8810d7e6ce4f4a9c3117a528e92d6d', '8941ca0c9b414873a8fa7ab9a48fe5c2', '2014-12-02 15:47:08', 'ADJ', '22', '', null, null, '2017-12-02', 'pel', '0', 'NOR', null);

-- ----------------------------
-- Table structure for jf_use
-- ----------------------------
DROP TABLE IF EXISTS `jf_use`;
CREATE TABLE `jf_use` (
  `use_flow_id` varchar(50) NOT NULL COMMENT '积分消耗明细号',
  `get_flow_id` varchar(50) NOT NULL COMMENT '积分获得明细号',
  `jf` decimal(10,0) DEFAULT NULL COMMENT '使用积分',
  `status` varchar(5) DEFAULT NULL COMMENT 'NOR:正常 UNDO:撤销',
  PRIMARY KEY (`use_flow_id`,`get_flow_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='积分消耗积分明细对应表';

-- ----------------------------
-- Records of jf_use
-- ----------------------------
INSERT INTO `jf_use` VALUES ('aaa20cffb38c4f22a6c19bea4654998b', '9270fd6c0f624328af4c27ddb43baee6', '5', 'NOR');
INSERT INTO `jf_use` VALUES ('aaa20cffb38c4f22a6c19bea4654998b', '50897dfeeda34839ac06cdec42245dd8', '3', 'NOR');
INSERT INTO `jf_use` VALUES ('24843d98587847fb8a52908a0827fc90', '9270fd6c0f624328af4c27ddb43baee6', '0', 'NOR');
INSERT INTO `jf_use` VALUES ('24843d98587847fb8a52908a0827fc90', '50897dfeeda34839ac06cdec42245dd8', '2', 'NOR');
INSERT INTO `jf_use` VALUES ('6cf482b2e88f43a7ba1c68c7b9db1acc', '9270fd6c0f624328af4c27ddb43baee6', '0', 'NOR');
INSERT INTO `jf_use` VALUES ('6cf482b2e88f43a7ba1c68c7b9db1acc', '50897dfeeda34839ac06cdec42245dd8', '0', 'NOR');
INSERT INTO `jf_use` VALUES ('6cf482b2e88f43a7ba1c68c7b9db1acc', 'a2964fe0094b468d898cc779cf19c0fa', '8', 'NOR');
INSERT INTO `jf_use` VALUES ('e5bf237dcde54788bd5e0be152fad737', '9270fd6c0f624328af4c27ddb43baee6', '0', 'NOR');
INSERT INTO `jf_use` VALUES ('e5bf237dcde54788bd5e0be152fad737', '50897dfeeda34839ac06cdec42245dd8', '0', 'NOR');
INSERT INTO `jf_use` VALUES ('e5bf237dcde54788bd5e0be152fad737', 'a2964fe0094b468d898cc779cf19c0fa', '0', 'NOR');
INSERT INTO `jf_use` VALUES ('e5bf237dcde54788bd5e0be152fad737', 'a744a741d74d49b0bd786efe52ddf0fe', '8', 'NOR');
INSERT INTO `jf_use` VALUES ('91b1f48491714fc09a9e0356b174b1b4', 'a744a741d74d49b0bd786efe52ddf0fe', '2', 'NOR');
INSERT INTO `jf_use` VALUES ('91b1f48491714fc09a9e0356b174b1b4', '6d9f35160eb04cb5af09d98fe30992ce', '4', 'NOR');
INSERT INTO `jf_use` VALUES ('63ebed65bfdd4b4b98cc74f22425f1de', '6d9f35160eb04cb5af09d98fe30992ce', '4', 'UNDO');
INSERT INTO `jf_use` VALUES ('63ebed65bfdd4b4b98cc74f22425f1de', 'b5a888b23bc34911afff210dcbf17e30', '2', 'UNDO');
INSERT INTO `jf_use` VALUES ('796a88e6701d479db0321620c90c82a6', '1eb3cb3d001b4c8d9dd6626e7771d610', '10', 'NOR');
INSERT INTO `jf_use` VALUES ('796a88e6701d479db0321620c90c82a6', '934809d1311f42a0acb562f48d5c3ba6', '5', 'NOR');
INSERT INTO `jf_use` VALUES ('a1f499a12ca941afaa0d3a088b375c9f', '6d9f35160eb04cb5af09d98fe30992ce', '1', 'NOR');
INSERT INTO `jf_use` VALUES ('56e96b181e8a4af2a6056414d7202753', '95e6aaee4dce4a47bb849ac5e1d17bab', '1', 'UNDO');
INSERT INTO `jf_use` VALUES ('56e96b181e8a4af2a6056414d7202753', '01a5af5f6ec34562a881290b0833189d', '2', 'UNDO');
INSERT INTO `jf_use` VALUES ('1740a2d8eb5b476a89388d15d61c5c16', '01a5af5f6ec34562a881290b0833189d', '1', 'UNDO');
INSERT INTO `jf_use` VALUES ('443937a3958d42e1a671b9a3f975bd6a', '089c671e06e848b7b859c17e9a228d9c', '5', 'UNDO');
INSERT INTO `jf_use` VALUES ('443937a3958d42e1a671b9a3f975bd6a', '18f73868b04742d88028f4879f704874', '3', 'UNDO');

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
INSERT INTO `jf_user` VALUES ('﻿naruto', null);
INSERT INTO `jf_user` VALUES ('sacaka', null);
INSERT INTO `jf_user` VALUES ('you', null);
INSERT INTO `jf_user` VALUES ('sakula', null);
INSERT INTO `jf_user` VALUES ('naruto', null);
INSERT INTO `jf_user` VALUES ('1', null);
INSERT INTO `jf_user` VALUES ('22223', null);
INSERT INTO `jf_user` VALUES ('34234234', null);
INSERT INTO `jf_user` VALUES ('pel', null);
INSERT INTO `jf_user` VALUES ('a', null);
INSERT INTO `jf_user` VALUES ('b', null);
INSERT INTO `jf_user` VALUES ('c', null);
INSERT INTO `jf_user` VALUES ('d', null);

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
