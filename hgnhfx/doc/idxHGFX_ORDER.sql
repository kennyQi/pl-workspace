/*
Navicat Oracle Data Transfer
Oracle Client Version : 11.2.0.3.0

Source Server         : hgfx oracle dev
Source Server Version : 100200
Source Host           : 192.168.2.32:1521
Source Schema         : HGFX_DEV

Target Server Type    : ORACLE
Target Server Version : 100200
File Encoding         : 65001

Date: 2016-06-17 13:40:40
*/




-- ----------------------------
-- Indexes structure for table HGFX_ORDER
-- ----------------------------
CREATE INDEX "HGFX_DEV"."idxCreateDate"
ON "HGFX_DEV"."HGFX_ORDER" ("CREATE_DATE" ASC)
LOGGING;
CREATE INDEX "HGFX_DEV"."idxOrderUnique"
ON "HGFX_DEV"."HGFX_ORDER" ("MER_ID" ASC, "ORDER_NO" ASC)
LOGGING;
