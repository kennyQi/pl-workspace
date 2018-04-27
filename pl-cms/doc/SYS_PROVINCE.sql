/*
Navicat Oracle Data Transfer
Oracle Client Version : 11.2.0.3.0

Source Server         : hsl_dev
Source Server Version : 100200
Source Host           : 192.168.2.32:1521
Source Schema         : HSL_DE

Target Server Type    : ORACLE
Target Server Version : 100200
File Encoding         : 65001

Date: 2015-03-11 16:55:33
*/


-- ----------------------------
-- Table structure for SYS_PROVINCE
-- ----------------------------
DROP TABLE "HSL_DE"."SYS_PROVINCE";
CREATE TABLE "HSL_DE"."SYS_PROVINCE" (
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
-- Records of SYS_PROVINCE
-- ----------------------------
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('2', '2', '安徽', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('3', '3', '北京', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('4', '4', '福建', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('5', '5', '甘肃', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('6', '6', '广东', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('7', '7', '广西', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('8', '8', '贵州', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('9', '9', '海南', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('10', '10', '河北', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('11', '11', '河南', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('12', '12', '黑龙江', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('13', '13', '湖北', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('14', '14', '湖南', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('15', '15', '吉林', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('16', '16', '江苏', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('17', '17', '江西', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('18', '18', '辽宁', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('19', '19', '内蒙古', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('20', '20', '宁夏', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('21', '21', '青海', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('22', '22', '山东', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('23', '23', '山西', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('24', '24', '陕西', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('25', '25', '上海', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('26', '26', '四川', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('27', '27', '天津', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('28', '28', '西藏', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('29', '29', '新疆', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('30', '30', '云南', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('31', '31', '浙江', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('32', '32', '重庆', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('33', '33', '香港', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('34', '34', '澳门', null);
INSERT INTO "HSL_DE"."SYS_PROVINCE" VALUES ('35', '35', '台湾', null);

-- ----------------------------
-- Indexes structure for table SYS_PROVINCE
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_PROVINCE
-- ----------------------------
ALTER TABLE "HSL_DE"."SYS_PROVINCE" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_PROVINCE
-- ----------------------------
ALTER TABLE "HSL_DE"."SYS_PROVINCE" ADD PRIMARY KEY ("ID");
