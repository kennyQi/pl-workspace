
-- ----------------------------
-- Table structure for AUTH_PERM
-- ----------------------------

CREATE TABLE AUTH_PERM (
ID varchar(64 ) NOT NULL ,
DISPLAY_NAME varchar(96 ) NULL ,
PARENT_ID varchar(64 ) NULL ,
PERM_ROLE varchar(96 ) NULL ,
PERM_TYPE numeric(5) NULL ,
URL varchar(512 ) NULL 
)




;

-- ----------------------------
-- Records of AUTH_PERM
-- ----------------------------

-- ----------------------------
-- Table structure for AUTH_ROLE
-- ----------------------------

CREATE TABLE AUTH_ROLE (
ID varchar(64 ) NOT NULL ,
DISPLAY_NAME varchar(96 ) NULL ,
ROLE_NAME varchar(64 ) NULL 
)




;

-- ----------------------------
-- Records of AUTH_ROLE
-- ----------------------------

-- ----------------------------
-- Table structure for AUTH_ROLE_PERM
-- ----------------------------

CREATE TABLE AUTH_ROLE_PERM (
ROLE_ID varchar(64 ) NOT NULL ,
PERM_ID varchar(64 ) NOT NULL 
)




;

-- ----------------------------
-- Records of AUTH_ROLE_PERM
-- ----------------------------

-- ----------------------------
-- Table structure for AUTH_USER
-- ----------------------------

CREATE TABLE AUTH_USER (
ID varchar(64 ) NOT NULL ,
DISPLAY_NAME varchar(96 ) NULL ,
ENABLE numeric(5) NULL ,
LOGIN_NAME varchar(64 ) NULL ,
PASSWD varchar(128 ) NULL 
)




;

-- ----------------------------
-- Records of AUTH_USER
-- ----------------------------
INSERT INTO AUTH_USER VALUES ('034cef04b50a4d8da92e336535a04089', null, '1', 'admin', 'e10adc3949ba59abbe56e057f20f883e');

-- ----------------------------
-- Table structure for AUTH_USER_ROLE
-- ----------------------------

CREATE TABLE AUTH_USER_ROLE (
USER_ID varchar(64 ) NOT NULL ,
ROLE_ID varchar(64 ) NOT NULL 
)




;

-- ----------------------------
-- Records of AUTH_USER_ROLE
-- ----------------------------

-- ----------------------------
-- Table structure for CAL_FLOW
-- ----------------------------

CREATE TABLE CAL_FLOW (
cal_id varchar(50) NOT NULL ,
user varchar(50) NULL ,
rule varchar(50) NOT NULL ,
cal_time DATE NULL ,
trade_flow varchar(1024) NOT NULL ,
jf numeric(10) NULL ,
result_code varchar(2) NULL ,
result_text varchar(1024) NULL 
)




;

-- ----------------------------
-- Records of CAL_FLOW
-- ----------------------------

-- ----------------------------
-- Table structure for CAL_RULE
-- ----------------------------

CREATE TABLE CAL_RULE (
code varchar(30) NULL ,
name varchar(100) NULL ,
start_date DATE NULL ,
end_date DATE NULL ,
rule_status varchar(1) NULL ,
props varchar(1024) NULL ,
template_name varchar(1024) NULL ,
account_type varchar(30) NULL 
)




;

-- ----------------------------
-- Records of CAL_RULE
-- ----------------------------

-- ----------------------------
-- Table structure for CAL_SESSION
-- ----------------------------

CREATE TABLE CAL_SESSION (
rule_code varchar(50) NOT NULL ,
user_code varchar(50) NOT NULL ,
props varchar(1024) NULL 
)




;

-- ----------------------------
-- Records of CAL_SESSION
-- ----------------------------

-- ----------------------------
-- Table structure for JF_ACCOUNT
-- ----------------------------

CREATE TABLE JF_ACCOUNT (
id varchar(50) NOT NULL ,
user varchar(50) NULL ,
acct_type varchar(20) NULL ,
amount numeric NULL ,
update_time DATE NULL 
)




;

-- ----------------------------
-- Records of JF_ACCOUNT
-- ----------------------------

-- ----------------------------
-- Table structure for JF_FLOW
-- ----------------------------

CREATE TABLE JF_FLOW (
id varchar(50) NOT NULL ,
acct_id varchar(50) NULL ,
flow_time DATE NULL ,
trade_type varchar(10) NULL ,
trade_amount numeric NULL ,
trade_remark varchar(512) NULL ,
batch_no varchar(20) NULL ,
ref_flow_id varchar(20) NULL ,
valid_date DATE NULL ,
user varchar(30) NULL 
)




;

-- ----------------------------
-- Records of JF_FLOW
-- ----------------------------

-- ----------------------------
-- Table structure for JF_USER
-- ----------------------------

CREATE TABLE JF_USER (
code varchar(50) NOT NULL ,
name varchar(50) NULL 
)




;

-- ----------------------------
-- Records of JF_USER
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_ADDR_PROJECTION
-- ----------------------------

CREATE TABLE SYS_ADDR_PROJECTION (
ID varchar(64 ) NOT NULL ,
ADDR_CODE varchar(64 ) NULL ,
ADDR_NAME varchar(64 ) NULL ,
ADDR_TYPE numeric(10) NULL ,
CHANNEL_ADDR_CODE varchar(64 ) NULL ,
CHANNEL_TYPE numeric(10) NULL 
)




;

-- ----------------------------
-- Table structure for SYS_AREA
-- ----------------------------

CREATE TABLE SYS_AREA (
ID varchar(64 ) NOT NULL ,
CODE varchar(8 ) NULL ,
NAME varchar(32 ) NULL ,
PARENT varchar(8 ) NULL 
)




;

-- ----------------------------
-- Records of SYS_AREA
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_BACKLOG
-- ----------------------------

CREATE TABLE SYS_BACKLOG (
ID varchar(64 ) NOT NULL ,
CREATE_DATE TIMESTAMP(6)  NULL ,
DESCRIPTION varchar(255 ) NULL ,
TYPE varchar(255 ) NULL 
)




;

-- ----------------------------
-- Records of SYS_BACKLOG
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_CITY
-- ----------------------------

CREATE TABLE SYS_CITY (
ID varchar(64 ) NOT NULL ,
AIR_CODE varchar(8 ) NULL ,
CITY_AIR_CODE varchar(8 ) NULL ,
CITY_JIAN_PIN varchar(8 ) NULL ,
CITY_QUAN_PIN varchar(64 ) NULL ,
CODE varchar(8 ) NULL ,
NAME varchar(32 ) NULL ,
PARENT varchar(8 ) NULL ,
SORT numeric(10) NULL 
)




;


-- ----------------------------
-- Records of SYS_IMAGE_SPEC
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_KVCONFIG
-- ----------------------------

CREATE TABLE SYS_KVCONFIG (
ID varchar(64 ) NOT NULL ,
DATA_KEY varchar(64 ) NULL ,
DATA_VALUE varchar(4000 ) NULL 
)




;

-- ----------------------------
-- Records of SYS_KVCONFIG
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_PROVINCE
-- ----------------------------

CREATE TABLE SYS_PROVINCE (
ID varchar(64 ) NOT NULL ,
CODE varchar(8 ) NULL ,
NAME varchar(32 ) NULL 
)




;

-- ----------------------------
-- Records of SYS_PROVINCE
-- ----------------------------

-- ----------------------------
-- Table structure for SYS_STAFF
-- ----------------------------

CREATE TABLE SYS_STAFF (
ID varchar(64 ) NOT NULL ,
EMAIL varchar(128 ) NULL ,
MOBILE varchar(64 ) NULL ,
REAL_NAME varchar(64 ) NULL ,
TEL varchar(64 ) NULL 
)




;

-- ----------------------------
-- Records of SYS_STAFF
-- ----------------------------
INSERT INTO SYS_STAFF VALUES ('034cef04b50a4d8da92e336535a04089', null, null, 'admin', null);

-- ----------------------------
-- Table structure for UC_ACCESS_TOKEN
-- ----------------------------

CREATE TABLE UC_ACCESS_TOKEN (
ID varchar(64 ) NOT NULL ,
ACCESS_TOKEN varchar(64 ) NULL ,
CREATE_DATE DATE NULL ,
END_DATE DATE NULL ,
CLIENT_ID varchar(64 ) NULL ,
REFRESH_TOKEN_ID varchar(64 ) NULL ,
USER_ID varchar(64 ) NULL ,
CALLBACK_URL varchar(256 ) NULL 
)




;


-- ----------------------------
-- Table structure for UC_CLIENT
-- ----------------------------

CREATE TABLE UC_CLIENT (
ID varchar(64 ) NOT NULL ,
NAME varchar(64 ) NULL ,
NEED_PERM_ITEMS varchar(1024 ) NULL ,
REMARK varchar(4000 ) NULL ,
REQUIRE_PERM_ITEMS varchar(1024 ) NULL ,
SECRET_KEY varchar(64 ) NULL ,
VALID varchar(1 ) NULL ,
HOME_URL varchar(256 ) NULL 
)




;

-- ----------------------------
-- Records of UC_CLIENT
-- ----------------------------
INSERT INTO UC_CLIENT VALUES ('1', '景区联盟', ',1,2,3,4,', '测试', ',1,', '1', 'Y', 'http://www.jqlm.com');

-- ----------------------------
-- Table structure for UC_MONEY_ACCOUNT
-- ----------------------------

CREATE TABLE UC_MONEY_ACCOUNT (
ID varchar(64 ) NOT NULL ,
CARD_NO varchar(64 ) NULL ,
DELETED char(1 ) NULL ,
NAME varchar(64 ) NULL ,
SHORTCUT char(1 ) NULL ,
TYPE varchar(8 ) NULL ,
USER_ID varchar(64 ) NULL 
)




;

-- ----------------------------
-- Records of UC_MONEY_ACCOUNT
-- ----------------------------

-- ----------------------------
-- Table structure for UC_PERSON
-- ----------------------------

CREATE TABLE UC_PERSON (
ID varchar(64 ) NOT NULL ,
BIRTHDAY varchar(64 ) NULL ,
numeric_ varchar(64 ) NULL ,
TYPE_ numeric(7) NULL ,
GENDER numeric(7) NULL ,
NAME varchar(64 ) NULL 
)




;

-- ----------------------------
-- Records of UC_PERSON
-- ----------------------------

-- ----------------------------
-- Table structure for UC_REFRESH_TOKEN
-- ----------------------------

CREATE TABLE UC_REFRESH_TOKEN (
ID varchar(64 ) NOT NULL ,
END_DATE DATE NULL ,
GRANT_DATE DATE NULL ,
REFRESH_TOKEN varchar(255 ) NULL ,
PERM_ITEMS varchar(1024 ) NULL ,
VALID varchar(1 ) NULL ,
CLIENT_ID varchar(64 ) NULL ,
USER_ID varchar(64 ) NULL 
)




;

-- ----------------------------
-- Records of UC_REFRESH_TOKEN
-- ----------------------------

-- ----------------------------
-- Table structure for UC_USER
-- ----------------------------

CREATE TABLE UC_USER (
ID varchar(64 ) NOT NULL ,
LOGIN_NAME varchar(64 ) NULL ,
PASSWORD varchar(64 ) NULL ,
BIRTHDAY varchar(32 ) NULL ,
CREATE_TIME DATE NULL ,
GENDER numeric(7) NULL ,
IDCARD_NO varchar(64 ) NULL ,
NAME varchar(32 ) NULL ,
EMAIL varchar(64 ) NULL ,
IM varchar(64 ) NULL ,
MOBILE varchar(32 ) NULL ,
BIND_EMAIL char(1 ) NULL ,
BIND_MOBILE char(1 ) NULL ,
LAST_LOGIN_TIME DATE NULL ,
REAL_PERSON char(1 ) NULL ,
FROM_CLIENT_ID varchar(64 ) NULL ,
PERSON_ID varchar(64 ) NULL 
)




;

-- ----------------------------
-- Records of UC_USER
-- ----------------------------
INSERT INTO UC_USER VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, DATE('2014-09-22 16:28:52'), null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Indexes structure for table AUTH_PERM
-- ----------------------------

-- ----------------------------
-- Checks structure for table AUTH_PERM
-- ----------------------------
ALTER TABLE AUTH_PERM ADD CHECK (ID IS NOT NULL);
ALTER TABLE AUTH_PERM ADD CHECK (ID IS NOT NULL);
ALTER TABLE AUTH_PERM ADD CHECK (ID IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table AUTH_PERM
-- ----------------------------
ALTER TABLE AUTH_PERM ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table AUTH_ROLE
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table AUTH_ROLE
-- ----------------------------
ALTER TABLE AUTH_ROLE ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table AUTH_ROLE_PERM
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table AUTH_ROLE_PERM
-- ----------------------------
ALTER TABLE AUTH_ROLE_PERM ADD PRIMARY KEY (PERM_ID, ROLE_ID);

-- ----------------------------
-- Indexes structure for table AUTH_USER
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table AUTH_USER
-- ----------------------------
ALTER TABLE AUTH_USER ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table AUTH_USER_copy
-- ----------------------------
 

-- ----------------------------
-- Indexes structure for table AUTH_USER_ROLE
-- ----------------------------
 

-- ----------------------------
-- Primary Key structure for table AUTH_USER_ROLE
-- ----------------------------
ALTER TABLE AUTH_USER_ROLE ADD PRIMARY KEY (ROLE_ID, USER_ID);

-- ----------------------------
-- Indexes structure for table CAL_FLOW
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table CAL_FLOW
-- ----------------------------
ALTER TABLE CAL_FLOW ADD PRIMARY KEY (cal_id);

-- ----------------------------
-- Uniques structure for table CAL_RULE
-- ----------------------------
ALTER TABLE CAL_RULE ADD UNIQUE (code);

-- ----------------------------
-- Indexes structure for table CAL_SESSION
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table CAL_SESSION
-- ----------------------------
ALTER TABLE CAL_SESSION ADD PRIMARY KEY (rule_code, user_code);

-- ----------------------------
-- Indexes structure for table JF_ACCOUNT
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table JF_ACCOUNT
-- ----------------------------
ALTER TABLE JF_ACCOUNT ADD PRIMARY KEY (id);

-- ----------------------------
-- Indexes structure for table JF_FLOW
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table JF_FLOW
-- ----------------------------
ALTER TABLE JF_FLOW ADD PRIMARY KEY (id);

-- ----------------------------
-- Indexes structure for table JF_USER
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table JF_USER
-- ----------------------------
ALTER TABLE JF_USER ADD PRIMARY KEY (code);

-- ----------------------------
-- Indexes structure for table SYS_ADDR_PROJECTION
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table SYS_ADDR_PROJECTION
-- ----------------------------
ALTER TABLE SYS_ADDR_PROJECTION ADD PRIMARY KEY (ID);


-- ----------------------------
-- Indexes structure for table SYS_AREA
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table SYS_AREA
-- ----------------------------
ALTER TABLE SYS_AREA ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table SYS_BACKLOG
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table SYS_BACKLOG
-- ----------------------------
ALTER TABLE SYS_BACKLOG ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table SYS_CITY
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table SYS_CITY
-- ----------------------------
ALTER TABLE SYS_CITY ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table SYS_IMAGE_SPEC
-- ----------------------------
 

-- ----------------------------
-- Indexes structure for table SYS_KVCONFIG
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table SYS_KVCONFIG
-- ----------------------------
ALTER TABLE SYS_KVCONFIG ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table SYS_PROVINCE
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table SYS_PROVINCE
-- ----------------------------
ALTER TABLE SYS_PROVINCE ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table SYS_STAFF
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table SYS_STAFF
-- ----------------------------
ALTER TABLE SYS_STAFF ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table UC_ACCESS_TOKEN
-- ----------------------------


-- ----------------------------
-- Primary Key structure for table UC_ACCESS_TOKEN
-- ----------------------------
ALTER TABLE UC_ACCESS_TOKEN ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table UC_CLIENT
-- ----------------------------


-- ----------------------------
-- Primary Key structure for table UC_CLIENT
-- ----------------------------
ALTER TABLE UC_CLIENT ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table UC_MONEY_ACCOUNT
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table UC_MONEY_ACCOUNT
-- ----------------------------
ALTER TABLE UC_MONEY_ACCOUNT ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table UC_PERSON
-- ----------------------------
 

-- ----------------------------
-- Primary Key structure for table UC_PERSON
-- ----------------------------
ALTER TABLE UC_PERSON ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table UC_REFRESH_TOKEN
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table UC_REFRESH_TOKEN
-- ----------------------------
ALTER TABLE UC_REFRESH_TOKEN ADD PRIMARY KEY (ID);

-- ----------------------------
-- Indexes structure for table UC_USER
-- ----------------------------
 
-- ----------------------------
-- Primary Key structure for table UC_USER
-- ----------------------------
ALTER TABLE UC_USER ADD PRIMARY KEY (ID);

-- ----------------------------
-- Foreign Key structure for table AUTH_ROLE_PERM
-- ----------------------------
ALTER TABLE AUTH_ROLE_PERM ADD FOREIGN KEY (PERM_ID) REFERENCES AUTH_PERM (ID);
 
-- ----------------------------
-- Foreign Key structure for table AUTH_USER_ROLE
-- ----------------------------
ALTER TABLE AUTH_USER_ROLE ADD FOREIGN KEY (USER_ID) REFERENCES AUTH_USER (ID);
 
-- ----------------------------
-- Foreign Key structure for table SYS_ALBUM
-- ----------------------------


-- ----------------------------
-- Foreign Key structure for table SYS_IMAGE
-- ----------------------------

-- ----------------------------
-- Foreign Key structure for table SYS_IMAGE_SPEC
-- ----------------------------

-- ----------------------------
-- Foreign Key structure for table SYS_STAFF
-- ----------------------------
ALTER TABLE SYS_STAFF ADD FOREIGN KEY (ID) REFERENCES AUTH_USER (ID) ;
