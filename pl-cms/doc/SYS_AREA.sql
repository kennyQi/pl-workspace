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

Date: 2015-03-11 16:55:16
*/


-- ----------------------------
-- Table structure for SYS_AREA
-- ----------------------------
DROP TABLE "HSL_DE"."SYS_AREA";
CREATE TABLE "HSL_DE"."SYS_AREA" (
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
-- Indexes structure for table SYS_AREA
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_AREA
-- ----------------------------
ALTER TABLE "HSL_DE"."SYS_AREA" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_AREA
-- ----------------------------
ALTER TABLE "HSL_DE"."SYS_AREA" ADD PRIMARY KEY ("ID");
