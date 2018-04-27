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

Date: 2014-10-17 10:29:21
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
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('1b7326ace55948de85ea8b9f49e5dcd0', 'd', null, 'd', '0', 'd');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('d9355db5003445f19c87e2e748d4edc5', '333', null, '333', '0', '333');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('28a88834dd2c4f258b76675eb6775b99', 'test', null, null, '0', '/abc/d.html');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('9954b4ce9b534bdc83f2e15650e74f28', 'test2', '28a88834dd2c4f258b76675eb6775b99', null, '0', '/abc/e.html');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('20a8d0ea0e8f4797a97662f0b1d56b74', '2211', null, '11', '0', '22');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('bf389f95441d4aebb56682973b8caa6d', 'cx', null, 'c', '0', 'c');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('d94bc83f3bb5402c953366474be67de2', 'cx', null, 'c', '0', 'c');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('64833bdfadd340baad0004052d2c60cb', '22', null, '22', '0', '222');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('1a5a16106b214be780dc10326e5f1d9c', 'ss', null, 'ss', '0', 'ss');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('7d7f3f3f9e574bc791e53fe73c8a9e14', 'ee', null, 'ee', '0', 'ee');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('d30477133ab34def90a6db01d9ab5db3', '123', null, '123', '0', '123');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('fe7f07f526e4459f983b62404d98c3e2', '测试2', null, '测试2', '0', '测试2');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('a1e7d44b6dc7458f88faf9f6b91307c1', '3', null, '3', '0', '3');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('74c0d74ba5ca43808eea48eeb0e94fd2', '3', null, '3', '0', '3');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('9465c7f1df1c4020ba2a896cb5dc055b', '张振斌测试资源1', '28a88834dd2c4f258b76675eb6775b99', '2222', '1', 'www.baidu.com');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('d8885dd7f82e43cab02474850eb1688e', '张振斌测试资源1', '28a88834dd2c4f258b76675eb6775b99', '2222', '1', 'www.baidu.com');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('8b8fe2ad50b64ae19aaf4f66d6f2ddd0', '张振斌测试资源1', '28a88834dd2c4f258b76675eb6775b99', '2222', '1', 'www.baidu.com');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('fb290cd84a214b90b2341f62b32d7aae', '33', null, '33', '0', '33');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('3ff513f601834f409356410f7c943bdb', '33', null, '33', '0', '33');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('7077dbf65af94754b10bcc5004217f11', 'ff', null, 'ff', '0', 'ff');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('7736254e91654655aa3f67eebb24e929', '正式测试数据1', '1a5a16106b214be780dc10326e5f1d9c', '正式测试数据1', '1', 'wwww.baidu.com');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('36f9a7eb647a408b9b0a34e79f878f4f', '22', null, '22', '0', '22');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('f2d5006cea1e4b6f838e9bc07580df95', '22', null, '22', '0', '22');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('cdae51a4f0e24825ad9603ade57a8939', '2', null, '3', '0', '3');
INSERT INTO "PLJF_DEV"."AUTH_PERM" VALUES ('d4b798e38f9e407fb8fd931c97b00159', '2', null, '3', '0', '3');

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
INSERT INTO "PLJF_DEV"."AUTH_ROLE" VALUES ('3c4976c92bcd49e38034540fe50ce256', 'testrole', 'testRole');

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
INSERT INTO "PLJF_DEV"."AUTH_ROLE_PERM" VALUES ('3c4976c92bcd49e38034540fe50ce256', '28a88834dd2c4f258b76675eb6775b99');
INSERT INTO "PLJF_DEV"."AUTH_ROLE_PERM" VALUES ('3c4976c92bcd49e38034540fe50ce256', '9954b4ce9b534bdc83f2e15650e74f28');

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
INSERT INTO "PLJF_DEV"."AUTH_USER" VALUES ('1', null, '1', 'admin', 'e10adc3949ba59abbe56e057f20f883e');

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
INSERT INTO "PLJF_DEV"."SYS_STAFF" VALUES ('1', 'admin@qq.com', '110', '鹳狸猿', '110');

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

-- ----------------------------
-- Primary Key structure for table AUTH_USER_ROLE
-- ----------------------------
ALTER TABLE "PLJF_DEV"."AUTH_USER_ROLE" ADD PRIMARY KEY ("ROLE_ID", "USER_ID");

-- ----------------------------
-- Indexes structure for table SYS_ADDR_PROJECTION
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_ADDR_PROJECTION
-- ----------------------------
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
ALTER TABLE "PLJF_DEV"."SYS_STAFF" ADD FOREIGN KEY ("ID") REFERENCES "PLJF_DEV"."AUTH_USER" ("ID");
