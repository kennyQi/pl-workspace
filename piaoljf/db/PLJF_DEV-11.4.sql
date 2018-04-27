/*
Navicat Oracle Data Transfer
Oracle Client Version : 11.2.0.3.0

Source Server         : pljfdev
Source Server Version : 100200
Source Host           : 192.168.2.32:1521
Source Schema         : PLJF_DEV

Target Server Type    : ORACLE
Target Server Version : 100200
File Encoding         : 65001

Date: 2014-11-04 12:29:17
*/


-- ----------------------------
-- Table structure for AUTH_PERM
-- ----------------------------
DROP TABLE "PLJF_DEV"."AUTH_PERM";
CREATE TABLE "PLJF_DEV"."AUTH_PERM" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"DISPLAY_NAME" VARCHAR2(96 CHAR) NULL ,
"PARENT_ID" VARCHAR2(64 CHAR) NULL ,
"PERM_ROLE" VARCHAR2(96 CHAR) NULL ,
"PERM_TYPE" NUMBER(5) NULL ,
"URL" VARCHAR2(512 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of AUTH_PERM
-- ----------------------------
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('d9355db5003445f19c87e2e748d4edc5', '333', null, '333', '0', '333');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('d94bc83f3bb5402c953366474be67de2', 'cx', null, 'c', '0', 'c');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('fe7f07f526e4459f983b62404d98c3e2', '测试2', null, '测试2', '0', '测试2');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('9465c7f1df1c4020ba2a896cb5dc055b', '张振斌测试资源1', '28a88834dd2c4f258b76675eb6775b99', '2222', '1', 'www.baidu.com');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('8b8fe2ad50b64ae19aaf4f66d6f2ddd0', '张振斌测试资源1', '28a88834dd2c4f258b76675eb6775b99', '2222', '1', 'www.baidu.com');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('fb290cd84a214b90b2341f62b32d7aae', '33', null, '33', '0', '33');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('7736254e91654655aa3f67eebb24e929', '正式测试数据1', '1a5a16106b214be780dc10326e5f1d9c', '正式测试数据1', '1', 'wwww.baidu.com');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('f2d5006cea1e4b6f838e9bc07580df95', '22', null, '22', '0', '22');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('d4b798e38f9e407fb8fd931c97b00159', '2', null, '3', '0', '3');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('8eae3fe8c117441382066f7b768057e6', 'dfd', null, null, '0', 'a/b');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('63bb143d2b164c91b99dccb909789eed', '下级', 'fe7f07f526e4459f983b62404d98c3e2', null, '0', 'a.b.c.d');

-- ----------------------------
-- Table structure for AUTH_ROLE
-- ----------------------------
DROP TABLE "PLJF_DEV"."AUTH_ROLE";
CREATE TABLE "PLJF_DEV"."AUTH_ROLE" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"DISPLAY_NAME" VARCHAR2(96 CHAR) NULL ,
"ROLE_NAME" VARCHAR2(64 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of AUTH_ROLE
-- ----------------------------

-- ----------------------------
-- Table structure for AUTH_ROLE_PERM
-- ----------------------------
DROP TABLE "PLJF_DEV"."AUTH_ROLE_PERM";
CREATE TABLE "PLJF_DEV"."AUTH_ROLE_PERM" (
"ROLE_ID" VARCHAR2(64 CHAR) NOT NULL ,
"PERM_ID" VARCHAR2(64 CHAR) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of AUTH_ROLE_PERM
-- ----------------------------

-- ----------------------------
-- Table structure for AUTH_USER
-- ----------------------------
DROP TABLE "PLJF_DEV"."AUTH_USER";
CREATE TABLE "PLJF_DEV"."AUTH_USER" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"DISPLAY_NAME" VARCHAR2(96 CHAR) NULL ,
"ENABLE" NUMBER(5) NULL ,
"LOGIN_NAME" VARCHAR2(64 CHAR) NULL ,
"PASSWD" VARCHAR2(128 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of AUTH_USER
-- ----------------------------
INSERT INTO "PLJF_DEV"."AUTH_USER" VALUES ('eb7c196a8be24e3fb29405503730d729', null, '1', '444444', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO "PLJF_DEV"."AUTH_USER" VALUES ('1', null, '1', 'admin', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO "PLJF_DEV"."AUTH_USER" VALUES ('940834a4409a4176a149c71e7f2ee755', null, '1', 'xinglj', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO "PLJF_DEV"."AUTH_USER" VALUES ('dde0d8ae1273441d8d7fba842bf03e70', null, '1', '1111111111111113', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO "PLJF_DEV"."AUTH_USER" VALUES ('2f3470beda7a4519b4d9f430210a773c', null, '1', '11111', 'e10adc3949ba59abbe56e057f20f883e');

-- ----------------------------
-- Table structure for AUTH_USER_ROLE
-- ----------------------------
DROP TABLE "PLJF_DEV"."AUTH_USER_ROLE";
CREATE TABLE "PLJF_DEV"."AUTH_USER_ROLE" (
"USER_ID" VARCHAR2(64 CHAR) NOT NULL ,
"ROLE_ID" VARCHAR2(64 CHAR) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of AUTH_USER_ROLE
-- ----------------------------

-- ----------------------------
-- Table structure for CAL_FLOW
-- ----------------------------
DROP TABLE "PLJF_DEV"."CAL_FLOW";
CREATE TABLE "PLJF_DEV"."CAL_FLOW" (
"cal_id" VARCHAR2(50 BYTE) NOT NULL ,
"user" VARCHAR2(50 BYTE) NULL ,
"rule" VARCHAR2(50 BYTE) NOT NULL ,
"cal_time" DATE NULL ,
"trade_flow" VARCHAR2(1024 BYTE) NOT NULL ,
"jf" NUMBER(10) NULL ,
"result_code" VARCHAR2(2 BYTE) NULL ,
"result_text" VARCHAR2(1024 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of CAL_FLOW
-- ----------------------------
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('ba70ebe7855f422c90a7bea31a0027ac', 'tom', 'XLJCal1', TO_DATE('2014-10-31 14:08:25', 'YYYY-MM-DD HH24:MI:SS'), '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, 'dsfdf');
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('c4a4a4482b6c4c9893dcdf354118ada4', 'tom', 'XLJCal1', TO_DATE('2014-11-01 14:08:32', 'YYYY-MM-DD HH24:MI:SS'), '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, 'test null');
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('096fe03794fb46d88afb621537bc7e62', 'xiaoying', 'XLJCal1', TO_DATE('2014-10-29 14:08:38', 'YYYY-MM-DD HH24:MI:SS'), '{"date":"20140110","tradeName":"sign","user":"xiaoying"}', '555', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('05e91956aefd4056941b11bf9510670b', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('5f71d43a897c41e5a8f2bb8b3d6837e8', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('2f7b9dfe8c454c40971fc13dd6c0e668', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('33be4b0eabe74db9bb6563497b3afc14', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('fb201a2b76ed44cda9616ce0d4d953cd', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('7d67a9ee84574ac197f85c8197aeca2d', 'tom', 'XLJCal1', TO_DATE('2014-11-03 14:14:22', 'YYYY-MM-DD HH24:MI:SS'), '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', 'Y', null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('4257b24888d84c0290fad0d57411334c', 'tom', 'XLJCal1', TO_DATE('2014-11-03 14:15:20', 'YYYY-MM-DD HH24:MI:SS'), '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', 'Y', null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('592740a78ff8464b9e12c5e2730c4a98', 'tom', 'XLJCal1', TO_DATE('2014-11-03 14:15:52', 'YYYY-MM-DD HH24:MI:SS'), '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', 'Y', null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('574b0225dcf44fab9f680e2901cd3e8c', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('fc9eab52263846b48a7315f01544bf2e', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('059a2afe507943099449308c454c2e43', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('b79b207bf0ad497293cee9c75dd12376', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('472192b648724c8c9314827389aff783', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('82afc0d2c3cc4cebb86d144c5ddfe8c2', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('82881794057540e588320cbb80b7523f', 'xiaoying', 'XLJCal1', TO_DATE('2014-11-03 15:59:59', 'YYYY-MM-DD HH24:MI:SS'), '{"date":"20140110","tradeName":"sign","user":"xiaoying"}', '0', 'Y', null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('52716d6d783649c4a7f33b3a9997d14a', 'erlong', 'XLJCal1', TO_DATE('2014-11-03 16:00:11', 'YYYY-MM-DD HH24:MI:SS'), '{"date":"20140110","tradeName":"sign","user":"erlong"}', '555', 'Y', null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('e57d32a6cb054220994b137285eadb69', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);
INSERT INTO "PLJF_DEV"."CAL_FLOW" VALUES ('0f513deb2eff4fe09d49da958a23f3f1', 'tom', 'XLJCal1', null, '{"date":"20140110","tradeName":"sign","user":"tom"}', '0', null, null);

-- ----------------------------
-- Table structure for CAL_RULE
-- ----------------------------
DROP TABLE "PLJF_DEV"."CAL_RULE";
CREATE TABLE "PLJF_DEV"."CAL_RULE" (
"code" VARCHAR2(30 BYTE) NULL ,
"name" VARCHAR2(100 BYTE) NULL ,
"start_date" DATE NULL ,
"end_date" DATE NULL ,
"rule_status" VARCHAR2(1 BYTE) NULL ,
"props" VARCHAR2(1024 BYTE) NULL ,
"template_name" VARCHAR2(1024 BYTE) NULL ,
"account_type" VARCHAR2(30 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of CAL_RULE
-- ----------------------------
INSERT INTO "PLJF_DEV"."CAL_RULE" VALUES ('XLJCal1', '测试计算，不要删', TO_DATE('2014-10-23 15:19:30', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2014-11-08 15:19:35', 'YYYY-MM-DD HH24:MI:SS'), 'Y', '{"norjf":"555","jfRatio1":"1","jfRatio2":"1"}', 'hgtech.jf.piaol.rulelogic.PiaolSignLogic', 'piaol__grow');
INSERT INTO "PLJF_DEV"."CAL_RULE" VALUES ('222222222222222', '2222222222222222222222', TO_DATE('2014-10-30 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2014-11-08 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'N', '{"integralIncrement":"21"}', 'hgtech.jf.piaol.rulelogic.PiaolInviteFriendsLogic', 'piaol__grow');

-- ----------------------------
-- Table structure for CAL_SESSION
-- ----------------------------
DROP TABLE "PLJF_DEV"."CAL_SESSION";
CREATE TABLE "PLJF_DEV"."CAL_SESSION" (
"rule_code" VARCHAR2(50 BYTE) NOT NULL ,
"user_code" VARCHAR2(50 BYTE) NOT NULL ,
"props" VARCHAR2(1024 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of CAL_SESSION
-- ----------------------------
INSERT INTO "PLJF_DEV"."CAL_SESSION" VALUES ('XLJCal1', 'tom', '{"@type":"java.util.HashMap","countDays":1,"lastDate":new Date(1389283200000)}');
INSERT INTO "PLJF_DEV"."CAL_SESSION" VALUES ('XLJCal1', 'xiaoying', '{"@type":"java.util.HashMap","countDays":1,"lastDate":new Date(1389283200000)}');
INSERT INTO "PLJF_DEV"."CAL_SESSION" VALUES ('XLJCal1', 'tom1', '{"@type":"java.util.HashMap","countDays":1,"lastDate":new Date(1389283200000)}');
INSERT INTO "PLJF_DEV"."CAL_SESSION" VALUES ('XLJCal1', 'erlong', '{"@type":"java.util.HashMap","countDays":1,"lastDate":new Date(1389283200000)}');

-- ----------------------------
-- Table structure for JF_ACCOUNT
-- ----------------------------
DROP TABLE "PLJF_DEV"."JF_ACCOUNT";
CREATE TABLE "PLJF_DEV"."JF_ACCOUNT" (
"id" VARCHAR2(50 BYTE) NOT NULL ,
"user" VARCHAR2(50 BYTE) NULL ,
"acct_type" VARCHAR2(20 BYTE) NULL ,
"amount" NUMBER NULL ,
"update_time" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of JF_ACCOUNT
-- ----------------------------
INSERT INTO "PLJF_DEV"."JF_ACCOUNT" VALUES ('7765f5d64bc34b839fc6f8ea44eb2ff2', 'xiaoying', 'piaol__grow', '721', TO_DATE('2014-11-04 11:14:23', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "PLJF_DEV"."JF_ACCOUNT" VALUES ('00174a8886844f1495f6a1aa4dc99878', 'werwrewrw', 'piaol__grow', '12', TO_DATE('2014-11-03 18:43:11', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "PLJF_DEV"."JF_ACCOUNT" VALUES ('cbfc77804cf548dc8c9c71032d35b685', 'xiaoying', 'piaol__consume', '230', TO_DATE('2014-11-04 11:11:30', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "PLJF_DEV"."JF_ACCOUNT" VALUES ('6bb44162bb7841dc9295f1a25ff2b204', 'tom', 'piaol__grow', '0', TO_DATE('2014-11-03 13:40:48', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "PLJF_DEV"."JF_ACCOUNT" VALUES ('884f754b1459482882d8ab39b31ad2b2', 'xlj', 'piaol__grow', '-500', TO_DATE('2014-11-03 13:47:43', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "PLJF_DEV"."JF_ACCOUNT" VALUES ('596adba320dc4b3393a5df0f86c254ca', 'erlong', 'piaol__grow', '555', TO_DATE('2014-11-03 16:00:11', 'YYYY-MM-DD HH24:MI:SS'));

-- ----------------------------
-- Table structure for JF_FLOW
-- ----------------------------
DROP TABLE "PLJF_DEV"."JF_FLOW";
CREATE TABLE "PLJF_DEV"."JF_FLOW" (
"id" VARCHAR2(500 BYTE) NOT NULL ,
"acct_id" VARCHAR2(50 BYTE) NULL ,
"flow_time" DATE NULL ,
"trade_type" VARCHAR2(10 BYTE) NULL ,
"trade_amount" NUMBER NULL ,
"trade_remark" VARCHAR2(512 BYTE) NULL ,
"batch_no" VARCHAR2(20 BYTE) NULL ,
"ref_flow_id" VARCHAR2(20 BYTE) NULL ,
"valid_date" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "PLJF_DEV"."JF_FLOW"."flow_time" IS '取值范围：
CAL累积(calculate)
EX兑换(exchange)
ADJ调整(adjust)
IN转入
OUT转出
UN 计算撤销
EXP过期(expire)
';
COMMENT ON COLUMN "PLJF_DEV"."JF_FLOW"."valid_date" IS '有效截止日期';

-- ----------------------------
-- Records of JF_FLOW
-- ----------------------------
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('cc549eace0434e9bb542ce8dc7614709', '00174a8886844f1495f6a1aa4dc99878', TO_DATE('2014-11-03 18:43:11', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '12', 'werw			  ', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('3bcc38a4135c4135b0946c9be8d88871', 'cbfc77804cf548dc8c9c71032d35b685', TO_DATE('2014-11-03 18:45:40', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '100', '12324234234234		2342dfsdfsdfs		
				  ', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('8818133a65284324934215ab30ee353a', '7765f5d64bc34b839fc6f8ea44eb2ff2', TO_DATE('2014-11-02 11:51:01', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '555', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('abc35493c4de44b38789248d9295cfab', '6bb44162bb7841dc9295f1a25ff2b204', TO_DATE('2014-11-03 12:39:01', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '0', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('17ea606fbfe147f8b0a6011126fe632d', '7765f5d64bc34b839fc6f8ea44eb2ff2', TO_DATE('2014-11-04 09:22:40', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '122', '调整分值122分			
				  ', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('6b23c476269b4fe68cdef3b3f5f7d7f9', 'cbfc77804cf548dc8c9c71032d35b685', TO_DATE('2014-11-04 11:11:30', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '30', 'dfasfsaf				
				  ', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('dcd90a34e6464ecd9f22f353f35862e9', '7765f5d64bc34b839fc6f8ea44eb2ff2', TO_DATE('2014-11-04 11:13:35', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '12', '12123123				
				  ', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('b3e6e3efc9874d76ad08146ab4712514', 'cbfc77804cf548dc8c9c71032d35b685', TO_DATE('2014-11-04 11:08:43', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '100', 'sdfsdfsafsafasf				
				  ', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('e3577889aad841c2b1c9b3cf5e196854', '7765f5d64bc34b839fc6f8ea44eb2ff2', TO_DATE('2014-11-04 11:10:01', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '20', 'sfsafsfsf			  ', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('cdbc3fd7d34b4bc89a05257a6d796166', '6bb44162bb7841dc9295f1a25ff2b2040', TO_DATE('2014-11-01 14:41:06', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '0', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('dda2e2c49edf488089b0256a047d9d2f', '884f754b1459482882d8ab39b31ad2b2', TO_DATE('2014-11-02 15:24:00', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '-100', '？？？？￥？？o？', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('49ba05a8f1fc46f990f5e09632312df5', '884f754b1459482882d8ab39b31ad2b2', TO_DATE('2014-11-02 15:59:19', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '-100', '？？？？￥？？o？', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('dc5790d2c1de483ea6f0fdd1c4a63b1c', '884f754b1459482882d8ab39b31ad2b2', TO_DATE('2014-11-02 15:59:44', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '-100', '？？？？￥？？o？', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('740ac3fe59684f8b99522ffaeef6c602', '884f754b1459482882d8ab39b31ad2b2', TO_DATE('2014-11-02 15:59:57', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '-100', '？？？？￥？？o？', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('465f04a1f0b24d0a84f24c304c436f33', '6bb44162bb7841dc9295f1a25ff2b204', TO_DATE('2014-11-03 10:42:21', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '0', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('d23f1e312e8a46ba9d67bfbfd9ef6ee9', '6bb44162bb7841dc9295f1a25ff2b204', TO_DATE('2014-11-03 10:54:54', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '0', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('3634d911a96c44b6aca1729d4ac6895c', '6bb44162bb7841dc9295f1a25ff2b204', TO_DATE('2014-11-03 10:56:49', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '0', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('90d361f38eb148a4b0558d167d98ffab', '6bb44162bb7841dc9295f1a25ff2b204', TO_DATE('2014-11-03 10:58:01', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '0', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('695cef1788714d588256f5a5aabdec05', '6bb44162bb7841dc9295f1a25ff2b204', TO_DATE('2014-11-03 10:58:09', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '0', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('9e26f38dc56d4bbf94d73d68bd812a81', '7765f5d64bc34b839fc6f8ea44eb2ff2', TO_DATE('2014-11-03 15:59:59', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '0', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('a3e622f258c641119297ed53971802cd', '596adba320dc4b3393a5df0f86c254ca', TO_DATE('2014-11-03 16:00:11', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '555', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('7ae8dfae12de493ba3b928f57b3e2b53', '6bb44162bb7841dc9295f1a25ff2b204', TO_DATE('2014-11-03 12:32:24', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '0', null, null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('60f91a6fceb047f29733250904d79a30', '6bb44162bb7841dc9295f1a25ff2b204', TO_DATE('2014-11-03 13:40:48', 'YYYY-MM-DD HH24:MI:SS'), 'CAL', '0', null, null, null, TO_DATE('2017-11-03 13:40:48', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('cabfdea6578c45d3a28578fd260a7c06', '884f754b1459482882d8ab39b31ad2b2', TO_DATE('2014-11-03 13:47:43', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '-100', 'duij', null, null, null);
INSERT INTO "PLJF_DEV"."JF_FLOW" VALUES ('8b4b6072834e4b68ab3165f063a7771b', '7765f5d64bc34b839fc6f8ea44eb2ff2', TO_DATE('2014-11-04 11:14:23', 'YYYY-MM-DD HH24:MI:SS'), 'ADJ', '12', '12121				
				  ', null, null, null);

-- ----------------------------
-- Table structure for JF_USER
-- ----------------------------
DROP TABLE "PLJF_DEV"."JF_USER";
CREATE TABLE "PLJF_DEV"."JF_USER" (
"code" VARCHAR2(50 BYTE) NOT NULL ,
"name" VARCHAR2(50 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of JF_USER
-- ----------------------------
INSERT INTO "PLJF_DEV"."JF_USER" VALUES ('xiaoying', null);
INSERT INTO "PLJF_DEV"."JF_USER" VALUES ('werwrewrw', null);
INSERT INTO "PLJF_DEV"."JF_USER" VALUES ('tom', null);
INSERT INTO "PLJF_DEV"."JF_USER" VALUES ('xlj', null);
INSERT INTO "PLJF_DEV"."JF_USER" VALUES ('erlong', null);

-- ----------------------------
-- Table structure for JF_USER_copy
-- ----------------------------
DROP TABLE "PLJF_DEV"."JF_USER_copy";
CREATE TABLE "PLJF_DEV"."JF_USER_copy" (
"code" VARCHAR2(50 BYTE) NOT NULL ,
"name" VARCHAR2(50 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of JF_USER_copy
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_ADDR_PROJECTION
-- ----------------------------
DROP TABLE "PLJF_DEV"."SYS_ADDR_PROJECTION";
CREATE TABLE "PLJF_DEV"."SYS_ADDR_PROJECTION" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"ADDR_CODE" VARCHAR2(64 CHAR) NULL ,
"ADDR_NAME" VARCHAR2(64 CHAR) NULL ,
"ADDR_TYPE" NUMBER(10) NULL ,
"CHANNEL_ADDR_CODE" VARCHAR2(64 CHAR) NULL ,
"CHANNEL_TYPE" NUMBER(10) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_ADDR_PROJECTION
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_ALBUM
-- ----------------------------
DROP TABLE "PLJF_DEV"."SYS_ALBUM";
CREATE TABLE "PLJF_DEV"."SYS_ALBUM" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"OWNER_ID" VARCHAR2(60 CHAR) NULL ,
"PROJECT_ID" VARCHAR2(60 CHAR) NULL ,
"REMARK" CLOB NULL ,
"TITLE" VARCHAR2(512 CHAR) NULL ,
"USE_TYPE" NUMBER(7) NULL ,
"PARENT_ID" VARCHAR2(64 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_ALBUM
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_AREA
-- ----------------------------
DROP TABLE "PLJF_DEV"."SYS_AREA";
CREATE TABLE "PLJF_DEV"."SYS_AREA" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"CODE" VARCHAR2(8 CHAR) NULL ,
"NAME" VARCHAR2(32 CHAR) NULL ,
"PARENT" VARCHAR2(8 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_AREA
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_BACKLOG
-- ----------------------------
DROP TABLE "PLJF_DEV"."SYS_BACKLOG";
CREATE TABLE "PLJF_DEV"."SYS_BACKLOG" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"CREATE_DATE" TIMESTAMP(6)  NULL ,
"DESCRIPTION" VARCHAR2(255 CHAR) NULL ,
"TYPE" VARCHAR2(255 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_BACKLOG
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_CITY
-- ----------------------------
DROP TABLE "PLJF_DEV"."SYS_CITY";
CREATE TABLE "PLJF_DEV"."SYS_CITY" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"AIR_CODE" VARCHAR2(8 CHAR) NULL ,
"CITY_AIR_CODE" VARCHAR2(8 CHAR) NULL ,
"CITY_JIAN_PIN" VARCHAR2(8 CHAR) NULL ,
"CITY_QUAN_PIN" VARCHAR2(64 CHAR) NULL ,
"CODE" VARCHAR2(8 CHAR) NULL ,
"NAME" VARCHAR2(32 CHAR) NULL ,
"PARENT" VARCHAR2(8 CHAR) NULL ,
"SORT" NUMBER(10) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_CITY
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_IMAGE
-- ----------------------------
DROP TABLE "PLJF_DEV"."SYS_IMAGE";
CREATE TABLE "PLJF_DEV"."SYS_IMAGE" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"CREATE_DATE" DATE NULL ,
"OWNER_ID" VARCHAR2(60 CHAR) NULL ,
"REMARK" CLOB NULL ,
"TITLE" VARCHAR2(512 CHAR) NULL ,
"ALBUM_ID" VARCHAR2(64 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_IMAGE
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_IMAGE_SPEC
-- ----------------------------
DROP TABLE "PLJF_DEV"."SYS_IMAGE_SPEC";
CREATE TABLE "PLJF_DEV"."SYS_IMAGE_SPEC" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"FILE_INFO" VARCHAR2(512 CHAR) NULL ,
"KEY" VARCHAR2(60 CHAR) NULL ,
"IMAGE_ID" VARCHAR2(64 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_IMAGE_SPEC
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_KVCONFIG
-- ----------------------------
DROP TABLE "PLJF_DEV"."SYS_KVCONFIG";
CREATE TABLE "PLJF_DEV"."SYS_KVCONFIG" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"DATA_KEY" VARCHAR2(64 CHAR) NULL ,
"DATA_VALUE" VARCHAR2(4000 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_KVCONFIG
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_PROVINCE
-- ----------------------------
DROP TABLE "PLJF_DEV"."SYS_PROVINCE";
CREATE TABLE "PLJF_DEV"."SYS_PROVINCE" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"CODE" VARCHAR2(8 CHAR) NULL ,
"NAME" VARCHAR2(32 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_PROVINCE
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_STAFF
-- ----------------------------
DROP TABLE "PLJF_DEV"."SYS_STAFF";
CREATE TABLE "PLJF_DEV"."SYS_STAFF" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"EMAIL" VARCHAR2(128 CHAR) NULL ,
"MOBILE" VARCHAR2(64 CHAR) NULL ,
"REAL_NAME" VARCHAR2(64 CHAR) NULL ,
"TEL" VARCHAR2(64 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_STAFF
-- ----------------------------
INSERT INTO "PLJF_DEV"."SYS_STAFF" VALUES ('eb7c196a8be24e3fb29405503730d729', '444444444444444', '44444444444444', '444', '444444444444444444');
INSERT INTO "PLJF_DEV"."SYS_STAFF" VALUES ('1', 'admin@qq.com', '110', '鹳狸猿', '110');
INSERT INTO "PLJF_DEV"."SYS_STAFF" VALUES ('940834a4409a4176a149c71e7f2ee755', 'a@qq.com', '153', 'xinglj', '153');
INSERT INTO "PLJF_DEV"."SYS_STAFF" VALUES ('dde0d8ae1273441d8d7fba842bf03e70', '3333333333', '33333', '3423423', '333');
INSERT INTO "PLJF_DEV"."SYS_STAFF" VALUES ('de4c43c8f32744f4a441d275874fa212', 'd', '135123255', 'xyy', '13645522222');
INSERT INTO "PLJF_DEV"."SYS_STAFF" VALUES ('2f3470beda7a4519b4d9f430210a773c', null, null, '1111', null);

-- ----------------------------
-- Table structure for UC_ACCESS_TOKEN
-- ----------------------------
DROP TABLE "PLJF_DEV"."UC_ACCESS_TOKEN";
CREATE TABLE "PLJF_DEV"."UC_ACCESS_TOKEN" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"ACCESS_TOKEN" VARCHAR2(64 CHAR) NULL ,
"CREATE_DATE" DATE NULL ,
"END_DATE" DATE NULL ,
"CLIENT_ID" VARCHAR2(64 CHAR) NULL ,
"REFRESH_TOKEN_ID" VARCHAR2(64 CHAR) NULL ,
"USER_ID" VARCHAR2(64 CHAR) NULL ,
"CALLBACK_URL" VARCHAR2(256 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of UC_ACCESS_TOKEN
-- ----------------------------
INSERT INTO "PLJF_DEV"."UC_ACCESS_TOKEN" VALUES ('e5b7d8eebc8e407b8ba4252b2335361a', 'd457e68120c045c48346c6be1348adea', TO_DATE('2014-09-22 15:58:02', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2014-09-23 15:58:02', 'YYYY-MM-DD HH24:MI:SS'), '1', '29b86348c2e342f2a6341ef99ce9b3d6', '85a92473e3d64cf294788ec8f8072f7c', 'http://www.baidu.com');

-- ----------------------------
-- Table structure for UC_CLIENT
-- ----------------------------
DROP TABLE "PLJF_DEV"."UC_CLIENT";
CREATE TABLE "PLJF_DEV"."UC_CLIENT" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"NAME" VARCHAR2(64 CHAR) NULL ,
"NEED_PERM_ITEMS" VARCHAR2(1024 CHAR) NULL ,
"REMARK" VARCHAR2(4000 CHAR) NULL ,
"REQUIRE_PERM_ITEMS" VARCHAR2(1024 CHAR) NULL ,
"SECRET_KEY" VARCHAR2(64 CHAR) NULL ,
"VALID" CHAR(1 CHAR) NULL ,
"HOME_URL" VARCHAR2(256 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of UC_CLIENT
-- ----------------------------
INSERT INTO "PLJF_DEV"."UC_CLIENT" VALUES ('1', '景区联盟', ',1,2,3,4,', '测试', ',1,', '1', 'Y', 'http://www.jqlm.com');

-- ----------------------------
-- Table structure for UC_MONEY_ACCOUNT
-- ----------------------------
DROP TABLE "PLJF_DEV"."UC_MONEY_ACCOUNT";
CREATE TABLE "PLJF_DEV"."UC_MONEY_ACCOUNT" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"CARD_NO" VARCHAR2(64 CHAR) NULL ,
"DELETED" CHAR(1 CHAR) NULL ,
"NAME" VARCHAR2(64 CHAR) NULL ,
"SHORTCUT" CHAR(1 CHAR) NULL ,
"TYPE" VARCHAR2(8 CHAR) NULL ,
"USER_ID" VARCHAR2(64 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of UC_MONEY_ACCOUNT
-- ----------------------------

-- ----------------------------
-- Table structure for UC_PERSON
-- ----------------------------
DROP TABLE "PLJF_DEV"."UC_PERSON";
CREATE TABLE "PLJF_DEV"."UC_PERSON" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"BIRTHDAY" VARCHAR2(64 CHAR) NULL ,
"NUMBER_" VARCHAR2(64 CHAR) NULL ,
"TYPE_" NUMBER(7) NULL ,
"GENDER" NUMBER(7) NULL ,
"NAME" VARCHAR2(64 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of UC_PERSON
-- ----------------------------

-- ----------------------------
-- Table structure for UC_REFRESH_TOKEN
-- ----------------------------
DROP TABLE "PLJF_DEV"."UC_REFRESH_TOKEN";
CREATE TABLE "PLJF_DEV"."UC_REFRESH_TOKEN" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"END_DATE" DATE NULL ,
"GRANT_DATE" DATE NULL ,
"REFRESH_TOKEN" VARCHAR2(255 CHAR) NULL ,
"PERM_ITEMS" VARCHAR2(1024 CHAR) NULL ,
"VALID" CHAR(1 CHAR) NULL ,
"CLIENT_ID" VARCHAR2(64 CHAR) NULL ,
"USER_ID" VARCHAR2(64 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of UC_REFRESH_TOKEN
-- ----------------------------
INSERT INTO "PLJF_DEV"."UC_REFRESH_TOKEN" VALUES ('29b86348c2e342f2a6341ef99ce9b3d6', TO_DATE('2024-09-22 19:19:26', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2014-09-22 19:19:26', 'YYYY-MM-DD HH24:MI:SS'), 'edf5e702589d4d138c0e32d6024c32f9', ',1,3,', 'Y', '1', '85a92473e3d64cf294788ec8f8072f7c');

-- ----------------------------
-- Table structure for UC_USER
-- ----------------------------
DROP TABLE "PLJF_DEV"."UC_USER";
CREATE TABLE "PLJF_DEV"."UC_USER" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"LOGIN_NAME" VARCHAR2(64 CHAR) NULL ,
"PASSWORD" VARCHAR2(64 CHAR) NULL ,
"BIRTHDAY" VARCHAR2(32 CHAR) NULL ,
"CREATE_TIME" DATE NULL ,
"GENDER" NUMBER(7) NULL ,
"IDCARD_NO" VARCHAR2(64 CHAR) NULL ,
"NAME" VARCHAR2(32 CHAR) NULL ,
"EMAIL" VARCHAR2(64 CHAR) NULL ,
"IM" VARCHAR2(64 CHAR) NULL ,
"MOBILE" VARCHAR2(32 CHAR) NULL ,
"BIND_EMAIL" CHAR(1 CHAR) NULL ,
"BIND_MOBILE" CHAR(1 CHAR) NULL ,
"LAST_LOGIN_TIME" DATE NULL ,
"REAL_PERSON" CHAR(1 CHAR) NULL ,
"FROM_CLIENT_ID" VARCHAR2(64 CHAR) NULL ,
"PERSON_ID" VARCHAR2(64 CHAR) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of UC_USER
-- ----------------------------
INSERT INTO "PLJF_DEV"."UC_USER" VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, TO_DATE('2014-09-22 16:28:52', 'YYYY-MM-DD HH24:MI:SS'), null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO "PLJF_DEV"."UC_USER" VALUES ('85a92473e3d64cf294788ec8f8072f7c', 'asfasdf', '912ec803b2ce49e4a541068d495ab570', null, TO_DATE('2014-09-22 19:19:26', 'YYYY-MM-DD HH24:MI:SS'), null, null, null, null, null, null, null, null, null, null, '1', null);
INSERT INTO "PLJF_DEV"."UC_USER" VALUES ('a1cd6e2760ae4ee5a5d2d5c16001a370', 'admin111', '698d51a19d8a121ce581499d7b701668', null, TO_DATE('2014-09-22 19:17:39', 'YYYY-MM-DD HH24:MI:SS'), null, null, null, null, null, null, null, null, null, null, '1', null);
INSERT INTO "PLJF_DEV"."UC_USER" VALUES ('2', 'haha', '827ccb0eea8a706c4c34a16891f84e7b', '2014-09-25', TO_DATE('2014-09-23 09:36:56', 'YYYY-MM-DD HH24:MI:SS'), '0', null, 'xixi', '123456@qq.com', null, '13758132687', null, null, null, null, '1', null);

-- ----------------------------
-- Indexes structure for table AUTH_PERM
-- ----------------------------

-- ----------------------------
-- Checks structure for table AUTH_PERM
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_PERM" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."AUTH_PERM" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table AUTH_PERM
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_PERM" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table AUTH_ROLE
-- ----------------------------

-- ----------------------------
-- Checks structure for table AUTH_ROLE
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_ROLE" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."AUTH_ROLE" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table AUTH_ROLE
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_ROLE" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table AUTH_ROLE_PERM
-- ----------------------------

-- ----------------------------
-- Checks structure for table AUTH_ROLE_PERM
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_ROLE_PERM" ADD CHECK ("ROLE_ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."AUTH_ROLE_PERM" ADD CHECK ("PERM_ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."AUTH_ROLE_PERM" ADD CHECK ("ROLE_ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."AUTH_ROLE_PERM" ADD CHECK ("PERM_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table AUTH_ROLE_PERM
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_ROLE_PERM" ADD PRIMARY KEY ("PERM_ID", "ROLE_ID");

-- ----------------------------
-- Indexes structure for table AUTH_USER
-- ----------------------------

-- ----------------------------
-- Checks structure for table AUTH_USER
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_USER" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."AUTH_USER" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table AUTH_USER
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_USER" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table AUTH_USER_ROLE
-- ----------------------------

-- ----------------------------
-- Checks structure for table AUTH_USER_ROLE
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_USER_ROLE" ADD CHECK ("USER_ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."AUTH_USER_ROLE" ADD CHECK ("ROLE_ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."AUTH_USER_ROLE" ADD CHECK ("USER_ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."AUTH_USER_ROLE" ADD CHECK ("ROLE_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table AUTH_USER_ROLE
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_USER_ROLE" ADD PRIMARY KEY ("ROLE_ID", "USER_ID");

-- ----------------------------
-- Indexes structure for table CAL_FLOW
-- ----------------------------

-- ----------------------------
-- Checks structure for table CAL_FLOW
-- ----------------------------
ALTER TABLE "PLJF_DEV"."CAL_FLOW" ADD CHECK ("cal_id" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."CAL_FLOW" ADD CHECK ("rule" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."CAL_FLOW" ADD CHECK ("trade_flow" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table CAL_FLOW
-- ----------------------------
ALTER TABLE "PLJF_DEV"."CAL_FLOW" ADD PRIMARY KEY ("cal_id");

-- ----------------------------
-- Uniques structure for table CAL_RULE
-- ----------------------------
ALTER TABLE "PLJF_DEV"."CAL_RULE" ADD UNIQUE ("code");

-- ----------------------------
-- Indexes structure for table CAL_SESSION
-- ----------------------------

-- ----------------------------
-- Checks structure for table CAL_SESSION
-- ----------------------------
ALTER TABLE "PLJF_DEV"."CAL_SESSION" ADD CHECK ("rule_code" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."CAL_SESSION" ADD CHECK ("user_code" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table CAL_SESSION
-- ----------------------------
ALTER TABLE "PLJF_DEV"."CAL_SESSION" ADD PRIMARY KEY ("rule_code", "user_code");

-- ----------------------------
-- Indexes structure for table JF_ACCOUNT
-- ----------------------------

-- ----------------------------
-- Checks structure for table JF_ACCOUNT
-- ----------------------------
ALTER TABLE "PLJF_DEV"."JF_ACCOUNT" ADD CHECK ("id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table JF_ACCOUNT
-- ----------------------------
ALTER TABLE "PLJF_DEV"."JF_ACCOUNT" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table JF_FLOW
-- ----------------------------

-- ----------------------------
-- Checks structure for table JF_FLOW
-- ----------------------------
ALTER TABLE "PLJF_DEV"."JF_FLOW" ADD CHECK ("id" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table JF_FLOW
-- ----------------------------
ALTER TABLE "PLJF_DEV"."JF_FLOW" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table JF_USER
-- ----------------------------

-- ----------------------------
-- Checks structure for table JF_USER
-- ----------------------------
ALTER TABLE "PLJF_DEV"."JF_USER" ADD CHECK ("code" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table JF_USER
-- ----------------------------
ALTER TABLE "PLJF_DEV"."JF_USER" ADD PRIMARY KEY ("code");

-- ----------------------------
-- Indexes structure for table JF_USER_copy
-- ----------------------------

-- ----------------------------
-- Checks structure for table JF_USER_copy
-- ----------------------------
ALTER TABLE "PLJF_DEV"."JF_USER_copy" ADD CHECK ("code" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."JF_USER_copy" ADD CHECK ("code" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table JF_USER_copy
-- ----------------------------
ALTER TABLE "PLJF_DEV"."JF_USER_copy" ADD PRIMARY KEY ("code");

-- ----------------------------
-- Indexes structure for table SYS_ADDR_PROJECTION
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_ADDR_PROJECTION
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_ADDR_PROJECTION" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."SYS_ADDR_PROJECTION" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_ADDR_PROJECTION
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_ADDR_PROJECTION" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SYS_ALBUM
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_ALBUM
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_ALBUM" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."SYS_ALBUM" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_ALBUM
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_ALBUM" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SYS_AREA
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_AREA
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_AREA" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."SYS_AREA" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_AREA
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_AREA" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SYS_BACKLOG
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_BACKLOG
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_BACKLOG" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."SYS_BACKLOG" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_BACKLOG
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_BACKLOG" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SYS_CITY
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_CITY
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_CITY" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."SYS_CITY" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_CITY
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_CITY" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SYS_IMAGE
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_IMAGE
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_IMAGE" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."SYS_IMAGE" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_IMAGE
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_IMAGE" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SYS_IMAGE_SPEC
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_IMAGE_SPEC
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_IMAGE_SPEC" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."SYS_IMAGE_SPEC" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_IMAGE_SPEC
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_IMAGE_SPEC" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SYS_KVCONFIG
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_KVCONFIG
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_KVCONFIG" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."SYS_KVCONFIG" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_KVCONFIG
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_KVCONFIG" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SYS_PROVINCE
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_PROVINCE
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_PROVINCE" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."SYS_PROVINCE" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_PROVINCE
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_PROVINCE" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SYS_STAFF
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_STAFF
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_STAFF" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."SYS_STAFF" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_STAFF
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_STAFF" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table UC_ACCESS_TOKEN
-- ----------------------------

-- ----------------------------
-- Checks structure for table UC_ACCESS_TOKEN
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_ACCESS_TOKEN" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."UC_ACCESS_TOKEN" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table UC_ACCESS_TOKEN
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_ACCESS_TOKEN" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table UC_CLIENT
-- ----------------------------

-- ----------------------------
-- Checks structure for table UC_CLIENT
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_CLIENT" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."UC_CLIENT" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table UC_CLIENT
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_CLIENT" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table UC_MONEY_ACCOUNT
-- ----------------------------

-- ----------------------------
-- Checks structure for table UC_MONEY_ACCOUNT
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_MONEY_ACCOUNT" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."UC_MONEY_ACCOUNT" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table UC_MONEY_ACCOUNT
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_MONEY_ACCOUNT" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table UC_PERSON
-- ----------------------------

-- ----------------------------
-- Checks structure for table UC_PERSON
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_PERSON" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."UC_PERSON" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table UC_PERSON
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_PERSON" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table UC_REFRESH_TOKEN
-- ----------------------------

-- ----------------------------
-- Checks structure for table UC_REFRESH_TOKEN
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_REFRESH_TOKEN" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."UC_REFRESH_TOKEN" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table UC_REFRESH_TOKEN
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_REFRESH_TOKEN" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table UC_USER
-- ----------------------------

-- ----------------------------
-- Checks structure for table UC_USER
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_USER" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "PLJF_DEV"."UC_USER" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table UC_USER
-- ----------------------------
ALTER TABLE "PLJF_DEV"."UC_USER" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Foreign Key structure for table "PLJF_DEV"."AUTH_ROLE_PERM"
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_ROLE_PERM" ADD FOREIGN KEY ("PERM_ID") REFERENCES "PLJF_DEV"."AUTH_PERM" ("ID");
ALTER TABLE "PLJF_DEV"."AUTH_ROLE_PERM" ADD FOREIGN KEY ("ROLE_ID") REFERENCES "PLJF_DEV"."AUTH_ROLE" ("ID");

-- ----------------------------
-- Foreign Key structure for table "PLJF_DEV"."AUTH_USER_ROLE"
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_USER_ROLE" ADD FOREIGN KEY ("USER_ID") REFERENCES "PLJF_DEV"."AUTH_USER" ("ID");
ALTER TABLE "PLJF_DEV"."AUTH_USER_ROLE" ADD FOREIGN KEY ("ROLE_ID") REFERENCES "PLJF_DEV"."AUTH_ROLE" ("ID");

-- ----------------------------
-- Foreign Key structure for table "PLJF_DEV"."SYS_ALBUM"
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_ALBUM" ADD FOREIGN KEY ("PARENT_ID") REFERENCES "PLJF_DEV"."SYS_ALBUM" ("ID");

-- ----------------------------
-- Foreign Key structure for table "PLJF_DEV"."SYS_IMAGE"
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_IMAGE" ADD FOREIGN KEY ("ALBUM_ID") REFERENCES "PLJF_DEV"."SYS_ALBUM" ("ID");

-- ----------------------------
-- Foreign Key structure for table "PLJF_DEV"."SYS_IMAGE_SPEC"
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_IMAGE_SPEC" ADD FOREIGN KEY ("IMAGE_ID") REFERENCES "PLJF_DEV"."SYS_IMAGE" ("ID");

-- ----------------------------
-- Foreign Key structure for table "PLJF_DEV"."SYS_STAFF"
-- ----------------------------
ALTER TABLE "PLJF_DEV"."SYS_STAFF" ADD FOREIGN KEY ("ID") REFERENCES "PLJF_DEV"."AUTH_USER" ("ID") DISABLE;
