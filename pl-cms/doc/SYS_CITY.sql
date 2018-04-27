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

Date: 2015-03-11 16:55:41
*/


-- ----------------------------
-- Table structure for SYS_CITY
-- ----------------------------
DROP TABLE "HSL_DE"."SYS_CITY";
CREATE TABLE "HSL_DE"."SYS_CITY" (
"ID" VARCHAR2(64 CHAR) NOT NULL ,
"CODE" VARCHAR2(8 CHAR) NULL ,
"NAME" VARCHAR2(32 CHAR) NULL ,
"PARENT" VARCHAR2(8 CHAR) NULL ,
"AIR_CODE" VARCHAR2(64 CHAR) NULL ,
"CITY_AIR_CODE" VARCHAR2(64 CHAR) NULL ,
"CITY_JIAN_PIN" VARCHAR2(8 BYTE) NULL ,
"SORT" NUMBER NULL ,
"CITY_QUAN_PIN" VARCHAR2(64 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_CITY
-- ----------------------------
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('120', '120', '遵义', '8', 'ZYI', 'ZYI', 'ZY', '7', 'ZUNYI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('163', '163', '郑州', '11', 'CGO', 'CGO', 'ZZ', '8', 'ZHENGZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('354', '354', '博尔塔拉', '29', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('355', '355', '昌吉', '29', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('360', '360', '克孜勒苏柯尔克孜', '29', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('361', '361', '石河子', '29', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('362', '362', '图木舒克', '29', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('363', '363', '吐鲁番', '29', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('365', '365', '五家渠', '29', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('366', '366', '伊犁', '29', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('368', '368', '楚雄', '30', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('370', '370', '德宏', '30', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('372', '372', '红河', '30', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('376', '376', '怒江', '30', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('377', '377', '曲靖', '30', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('381', '381', '玉溪', '30', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('384', '384', '湖州', '31', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('385', '385', '嘉兴', '31', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('100', '100', '崇左', '7', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('101', '101', '防城港', '7', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('103', '103', '贵港', '7', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('104', '104', '河池', '7', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('105', '105', '贺州', '7', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('106', '106', '来宾', '7', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('109', '109', '钦州', '7', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('111', '111', '玉林', '7', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('113', '113', '毕节', '8', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('115', '115', '六盘水', '8', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('116', '116', '黔东南', '8', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('117', '117', '黔南', '8', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('118', '118', '黔西南', '8', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('121', '121', '白沙', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('122', '122', '保亭', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('123', '123', '昌江', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('124', '124', '澄迈县', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('125', '125', '定安县', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('126', '126', '东方', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('128', '128', '乐东', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('129', '129', '临高县', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('130', '130', '陵水', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('131', '131', '琼海', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('132', '132', '琼中', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('134', '134', '屯昌县', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('135', '135', '万宁', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('136', '136', '文昌', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('137', '137', '五指山', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('138', '138', '儋州', '9', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('139', '139', '保定', '10', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('140', '140', '沧州', '10', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('141', '141', '承德', '10', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('143', '143', '衡水', '10', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('144', '144', '廊坊', '10', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('149', '149', '张家口', '10', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('151', '151', '鹤壁', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('152', '152', '济源', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('153', '153', '焦作', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('154', '154', '开封', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('157', '157', '平顶山', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('158', '158', '三门峡', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('159', '159', '商丘', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('160', '160', '新乡', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('161', '161', '信阳', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('162', '162', '许昌', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('164', '164', '周口', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('165', '165', '驻马店', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('166', '166', '漯河', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('167', '167', '濮阳', '11', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('169', '169', '大兴安岭', '12', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('171', '171', '鹤岗', '12', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('176', '176', '七台河', '12', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('178', '178', '双鸭山', '12', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('179', '179', '绥化', '12', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('3143', '3143', '乌苏里江', '12', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('181', '181', '鄂州', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('183', '183', '黄冈', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('184', '184', '黄石', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('185', '185', '荆门', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('186', '186', '荆州', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('187', '187', '潜江', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('188', '188', '神农架林区', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('189', '189', '十堰', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('190', '190', '随州', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('191', '191', '天门', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('193', '193', '仙桃', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('194', '194', '咸宁', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('196', '196', '孝感', '13', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('200', '200', '郴州', '14', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('203', '203', '娄底', '14', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('204', '204', '邵阳', '14', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('205', '205', '湘潭', '14', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('206', '206', '湘西', '14', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('207', '207', '益阳', '14', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('209', '209', '岳阳', '14', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('211', '211', '株洲', '14', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('212', '212', '白城', '15', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('213', '213', '白山', '15', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('216', '216', '辽源', '15', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('217', '217', '四平', '15', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('218', '218', '松原', '15', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('220', '220', '延边', '15', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('227', '227', '宿迁', '16', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('228', '228', '泰州', '16', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('232', '232', '扬州', '16', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('233', '233', '镇江', '16', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('234', '234', '抚州', '17', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('240', '240', '萍乡', '17', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('241', '241', '上饶', '17', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('242', '242', '新余', '17', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('243', '243', '宜春', '17', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('244', '244', '鹰潭', '17', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('246', '246', '本溪', '18', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('250', '250', '抚顺', '18', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('251', '251', '阜新', '18', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('252', '252', '葫芦岛', '18', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('254', '254', '辽阳', '18', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('255', '255', '盘锦', '18', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('257', '257', '铁岭', '18', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('258', '258', '营口', '18', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('259', '259', '阿拉善盟', '19', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('260', '260', '巴彦淖尔市', '19', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('265', '265', '呼伦贝尔', '19', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('268', '268', '乌兰察布市', '19', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('269', '269', '锡林郭勒盟', '19', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('270', '270', '兴安盟', '19', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('272', '272', '石嘴山', '20', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('273', '273', '吴忠', '20', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('275', '275', '果洛', '21', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('276', '276', '海北', '21', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('277', '277', '海东', '21', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('278', '278', '海南藏族', '21', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('279', '279', '海西', '21', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('280', '280', '黄南', '21', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('283', '283', '滨州', '22', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('284', '284', '德州', '22', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('286', '286', '菏泽', '22', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('289', '289', '莱芜', '22', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('290', '290', '聊城', '22', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('293', '293', '日照', '22', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('294', '294', '泰安', '22', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('298', '298', '枣庄', '22', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('299', '299', '淄博', '22', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('302', '302', '晋城', '23', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('303', '303', '晋中', '23', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('304', '304', '临汾', '23', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('305', '305', '吕梁', '23', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('306', '306', '朔州', '23', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('308', '308', '忻州', '23', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('309', '309', '阳泉', '23', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('312', '312', '宝鸡', '24', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('314', '314', '商洛', '24', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('315', '315', '铜川', '24', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('316', '316', '渭南', '24', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('322', '322', '阿坝', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('323', '323', '巴中', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('326', '326', '德阳', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('327', '327', '甘孜', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('328', '328', '广安', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('330', '330', '乐山', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('331', '331', '凉山', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('332', '332', '眉山', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('335', '335', '内江', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('337', '337', '遂宁', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('338', '338', '雅安', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('340', '340', '资阳', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('341', '341', '自贡', '26', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('344', '344', '阿里', '28', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('348', '348', '那曲', '28', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('349', '349', '日喀则', '28', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('350', '350', '山南', '28', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('352', '352', '阿拉尔', '29', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('353', '353', '巴音郭楞', '29', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('91', '91', '深圳', '6', 'SZX', 'SZX', 'SZ', '8', 'SHENZHEN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('266', '266', '通辽', '19', 'TGO', 'TGO', 'TL', '4', 'TONGLIAO');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('147', '147', '唐山', '10', 'TVS', 'TVS', 'TS', '7', 'TANGSHAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('50', '50', '芜湖', '2', 'WHU', 'WHU', 'WH', '3', 'WUHU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('379', '379', '文山', '30', 'WNH', 'WNH', 'WS', '7', 'wenshan');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('317', '317', '西安', '24', 'SIA', 'SIA', 'XA', '1', 'XIAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('380', '380', '西双版纳', '30', 'JHG', 'JHG', 'XSBN', '5', '景洪/西双版纳');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('230', '230', '徐州', '16', 'XUZ', 'XUZ', 'XZ', '9', 'XUZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('310', '310', '运城', '23', 'YCU', 'YCU', 'YC', '5', 'YUNCHENG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('282', '282', '玉树', '21', 'YUS', 'YUS', 'YS', '9', 'YUSHU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('94', '94', '湛江', '6', 'ZHA', 'ZHA', 'ZJ', '2', 'ZHANJIANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('382', '382', '昭通', '30', 'ZAT', 'ZAT', 'ZT', '5', 'ZHAOTONG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('386', '386', '金华', '31', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('387', '387', '丽水', '31', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('389', '389', '绍兴', '31', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('395', '395', '香港', '33', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('396', '396', '澳门', '34', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('397', '397', '高雄', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('398', '398', '花莲', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('399', '399', '基隆', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('400', '400', '嘉义', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('401', '401', '台北', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('402', '402', '台东', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('403', '403', '台南', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('404', '404', '台中', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('5114', '5114', '新竹', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('5115', '5115', '宜兰', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('5116', '5116', '桃园', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('5117', '5117', '苗栗', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('5118', '5118', '彰化', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('5119', '5119', '南投', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('5120', '5120', '云林', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('5121', '5121', '屏东', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('5127', '5127', '金门', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('5130', '5130', '澎湖', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('6571', '6571', '新北', '35', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('38', '38', '巢湖', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('39', '39', '池州', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('40', '40', '滁州', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('43', '43', '淮北', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('44', '44', '淮南', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('46', '46', '六安', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('47', '47', '马鞍山', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('48', '48', '宿州', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('49', '49', '铜陵', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('51', '51', '宣城', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('52', '52', '亳州', '2', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('56', '56', '南平', '4', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('57', '57', '宁德', '4', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('58', '58', '莆田', '4', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('59', '59', '泉州', '4', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('60', '60', '三明', '4', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('62', '62', '漳州', '4', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('63', '63', '白银', '5', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('64', '64', '定西', '5', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('65', '65', '甘南', '5', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('67', '67', '金昌', '5', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('70', '70', '临夏', '5', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('71', '71', '陇南', '5', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('72', '72', '平凉', '5', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('75', '75', '武威', '5', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('76', '76', '张掖', '5', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('78', '78', '东莞', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('81', '81', '河源', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('82', '82', '惠州', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('83', '83', '江门', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('84', '84', '揭阳', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('85', '85', '茂名', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('86', '86', '梅州', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('87', '87', '清远', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('89', '89', '汕尾', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('90', '90', '韶关', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('92', '92', '阳江', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('93', '93', '云浮', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('95', '95', '肇庆', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('96', '96', '中山', '6', null, null, null, null, null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('311', '311', '安康', '24', 'AKA', 'AKA', 'AK', '1', 'ANKANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('351', '351', '阿克苏', '29', 'AKU', 'AKU', 'AKS', '2', 'AKESU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('3114', '3114', '阿勒泰', '29', 'AAT', 'AAT', 'ALT', '3', 'ALETAI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('36', '36', '安庆', '2', 'AQG', 'AQG', 'AQ', '4', 'ANQING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('245', '245', '鞍山', '18', 'AOG', 'AOG', 'AS', '5', 'ANSHAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('112', '112', '安顺', '8', 'AVA', 'AVA', 'AS', '6', null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('150', '150', '安阳', '11', 'AYN', 'AYN', 'AY', '7', 'ANYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('37', '37', '蚌埠', '2', 'BFU', 'BFU', 'BB', '1', 'BANGBU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('99', '99', '北海', '7', 'BHY', 'BHY', 'BH', '2', 'BEIHAI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('53', '53', '北京', '3', 'PEK', 'PEK', 'BJ', '3', 'BEIJING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('98', '98', '百色', '7', 'AEB', 'AEB', 'BS', '4', 'BAISE');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('367', '367', '保山', '30', 'BSD', 'BSD', 'BS', '5', 'BAOSHAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('261', '261', '包头', '19', 'BAV', 'BAV', 'BT', '6', 'BAOTOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('41', '41', '阜阳', '2', 'FUG', 'FUG', 'BY', '7', 'FUYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('4569', '4569', '长白山', '15', 'NBS', 'NBS', 'CBS', '1', null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('214', '214', '长春', '15', 'CGQ', 'CGQ', 'CC', '2', 'CHANGCHUN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('345', '345', '昌都', '28', 'BPX', 'BPX', 'CD', '3', 'CHANGDU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('198', '198', '常德', '14', 'CGD', 'CGD', 'CD', '4', 'CHANGDE');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('324', '324', '成都', '26', 'CTU', 'CTU', 'CD', '5', 'CHENGDU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('262', '262', '赤峰', '19', 'CIF', 'CIF', 'CF', '6', 'CHIFENG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('394', '394', '重庆', '32', 'CKG', 'CKG', 'CQ', '7', 'CHONGQING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('199', '199', '长沙', '14', 'CSX', 'CSX', 'CS', '8', 'CHANGSHA');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('247', '247', '朝阳', '18', 'CHG', 'CHG', 'CY', '9', 'CHAOYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('77', '77', '潮州', '6', 'CCC', 'CCC', 'CZ', '10', 'CHAOZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('300', '300', '长治', '23', 'CIH', 'CIH', 'CZ', '11', 'CHANGZHI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('221', '221', '常州', '16', 'CZX', 'CZX', 'CZ', '12', 'CHANGZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('249', '249', '丹东', '18', 'DDG', 'DDG', 'DD', '1', 'DANDONG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('248', '248', '大连', '18', 'DLC', 'DLC', 'DL', '2', 'DALIAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('369', '369', '大理', '30', 'DLU', 'DLU', 'DL', '3', 'DALI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('371', '371', '迪庆', '30', 'DIG', 'DIG', 'DQ', '4', 'DIQING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('168', '168', '大庆', '12', 'DQA', 'DQA', 'DQ', '5', 'DAQING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('301', '301', '大同', '23', 'DAT', 'DAT', 'DT', '6', 'DATONG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('285', '285', '东营', '22', 'DOY', 'DOY', 'DY', '7', 'DONGYING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('325', '325', '达州', '26', 'DAX', 'DAX', 'DZ', '8', 'DAXIAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('263', '263', '鄂尔多斯', '19', 'DSN', 'DSN', 'ERDS', '1', 'EERDUOSHI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('182', '182', '恩施', '13', 'ENH', 'ENH', 'ES', '2', 'ENSHI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('79', '79', '佛山', '6', 'FUO', 'FUO', 'FS', '1', 'FOSHAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('54', '54', '福州', '4', 'FOC', 'FOC', 'FZ', '2', 'FUZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('102', '102', '桂林', '7', 'KWL', 'KWL', 'GL', '1', 'GUILIN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('329', '329', '广元', '26', 'GYS', 'GYS', 'GY', '2', null);
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('271', '271', '固原', '20', 'GYU', 'GYU', 'GY', '3', 'guyuan');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('114', '114', '贵阳', '8', 'KWE', 'KWE', 'GY', '4', 'GUIYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('80', '80', '广州', '6', 'CAN', 'CAN', 'GZ', '5', 'GUANGZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('235', '235', '赣州', '17', 'KOW', 'KOW', 'GZ', '6', 'GANZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('222', '222', '淮安', '16', 'HIA', 'HIA', 'HA', '1', 'huaian');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('142', '142', '邯郸', '10', 'HDG', 'HDG', 'HD', '2', '邯郸');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('170', '170', '哈尔滨', '12', 'HRB', 'HRB', 'HEB', '3', 'HAERBIN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('42', '42', '合肥', '2', 'HFE', 'HFE', 'HF', '4', 'HEFEI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('172', '172', '黑河', '12', 'HEK', 'HEK', 'HH', '5', 'HEIHE');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('202', '202', '怀化', '14', 'HJJ', 'HJJ', 'HH', '6', 'ZHIJIANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('264', '264', '呼和浩特', '19', 'HET', 'HET', 'HHHT', '7', 'HUHEHAOTE');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('127', '127', '海口', '9', 'HAK', 'HAK', 'HK', '8', 'HAIKOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('356', '356', '哈密', '29', 'HMI', 'HMI', 'HM', '9', 'HAMI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('45', '45', '黄山', '2', 'TXN', 'TXN', 'HS', '10', 'HUANGSHAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('357', '357', '和田', '29', 'HTN', 'HTN', 'HT', '11', 'HETIAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('201', '201', '衡阳', '14', 'HNY', 'HNY', 'HY', '12', 'HENGYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('383', '383', '杭州', '31', 'HGH', 'HGH', 'HZ', '13', 'HANGZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('313', '313', '汉中', '24', 'HZG', 'HZG', 'HZ', '14', 'HANZHONG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('236', '236', '吉安', '17', 'KNC', 'KNC', 'JA', '1', 'JIAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('237', '237', '景德镇', '17', 'JDZ', 'JDZ', 'JDZ', '2', 'JINGDEZHEN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('238', '238', '九江', '17', 'JIU', 'JIU', 'JJ', '3', 'JIUJIANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('215', '215', '吉林', '15', 'JIL', 'JIL', 'JL', '4', 'JILIN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('174', '174', '佳木斯', '12', 'JMU', 'JMU', 'JMS', '5', 'JIAMUSI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('288', '288', '济宁', '22', 'JNG', 'JNG', 'JN', '6', 'JINING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('287', '287', '济南', '22', 'TNA', 'TNA', 'JN', '7', 'JINAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('68', '68', '酒泉', '5', 'CHW', 'CHW', 'JQ', '8', 'JIUQUAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('173', '173', '鸡西', '12', 'JXA', 'JXA', 'JX', '9', 'jixi');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('66', '66', '嘉峪关', '5', 'JGN', 'JGN', 'JYG', '10', 'JIAYUGUAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('253', '253', '锦州', '18', 'JNZ', 'JNZ', 'JZ', '11', 'JINZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('359', '359', '克拉玛依', '29', 'KRY', 'KRY', 'KLMY', '1', 'KELAMAYI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('373', '373', '昆明', '30', 'KMG', 'KMG', 'KM', '2', 'KUNMING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('358', '358', '喀什', '29', 'KHG', 'KHG', 'KS', '3', 'KASHEN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('375', '375', '临沧', '30', 'LNJ', 'LNJ', 'LC', '1', 'Lincang');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('374', '374', '丽江', '30', 'LJG', 'LJG', 'LJ', '2', 'LIJIANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('346', '346', '拉萨', '28', 'LXA', 'LXA', 'LS', '3', 'LASA');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('55', '55', '龙岩', '4', 'LCX', 'LCX', 'LY', '4', 'longyan');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('155', '155', '洛阳', '11', 'LYA', 'LYA', 'LY', '5', 'LUOYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('291', '291', '临沂', '22', 'LYI', 'LYI', 'LY', '6', 'LINYI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('223', '223', '连云港', '16', 'LYG', 'LYG', 'LYG', '7', 'LIANYUNGANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('69', '69', '兰州', '5', 'LHW', 'LHW', 'LZ', '8', 'LANZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('107', '107', '柳州', '7', 'LZH', 'LZH', 'LZ', '9', 'LIUZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('342', '342', '泸州', '26', 'LZO', 'LZO', 'LZ', '10', 'LUZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('347', '347', '林芝', '28', 'LZY', 'LZY', 'LZ', '11', 'LINZHI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('333', '333', '绵阳', '26', 'MIG', 'MIG', 'MY', '1', 'MIANYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('388', '388', '宁波', '31', 'NGB', 'NGB', 'NB', '1', 'NINGBO');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('239', '239', '南昌', '17', 'KHN', 'KHN', 'NC', '2', 'NANCHANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('334', '334', '南充', '26', 'NAO', 'NAO', 'NC', '3', 'NANCHONG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('175', '175', '牡丹江', '12', 'MDG', 'MDG', 'NDJ', '4', 'MUDANJIANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('224', '224', '南京', '16', 'NKG', 'NKG', 'NJ', '5', 'NANJING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('108', '108', '南宁', '7', 'NNG', 'NNG', 'NN', '6', 'NANNING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('225', '225', '南通', '16', 'NTG', 'NTG', 'NT', '7', 'NANTONG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('156', '156', '南阳', '11', 'NNY', 'NNY', 'NY', '8', 'NANYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('378', '378', '普洱', '30', 'SYM', 'SYM', 'PE', '1', 'SAIMAO');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('336', '336', '攀枝花', '26', 'PZI', 'PZI', 'PZH', '2', 'PANZHIHUA');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('292', '292', '青岛', '22', 'TAO', 'TAO', 'QD', '1', 'QINGDAO');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('145', '145', '秦皇岛', '10', 'SHP', 'SHP', 'QHD', '2', 'QINHUANGDAO');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('177', '177', '齐齐哈尔', '12', 'NDG', 'NDG', 'QQHE', '3', 'QIQIHAER');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('73', '73', '庆阳', '5', 'IQN', 'IQN', 'QY', '4', 'QINGYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('393', '393', '衢州', '31', 'JUZ', 'JUZ', 'QZ', '5', 'QUZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('321', '321', '上海', '25', 'SHA', 'SHA', 'SH', '1', 'SHANGHAI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('146', '146', '石家庄', '10', 'SJW', 'SJW', 'SJZ', '2', 'SHIJIAZHUANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('6321', '6321', '三沙市', '9', 'SHS', 'SHS', 'SSS', '3', 'SHASHI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('88', '88', '汕头', '6', 'SWA', 'SWA', 'ST', '4', 'SHANTOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('256', '256', '沈阳', '18', 'SHE', 'SHE', 'SY', '5', 'SHENYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('133', '133', '三亚', '9', 'SYX', 'SYX', 'SY', '6', 'SANYA');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('226', '226', '苏州', '16', 'SZV', 'SZV', 'SZ', '7', 'SUZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('3113', '3113', '塔城', '29', 'TCG', 'TCG', 'TC', '1', 'TACHENG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('219', '219', '通化', '15', 'TNH', 'TNH', 'TH', '2', 'TONGHUA');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('343', '343', '天津', '27', 'TSN', 'TSN', 'TJ', '3', 'TIANJIN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('119', '119', '铜仁', '8', 'TEN', 'TEN', 'TR', '5', 'TONGREN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('74', '74', '天水', '5', 'THQ', 'THQ', 'TS', '6', 'tianshui');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('307', '307', '太原', '23', 'TYN', 'TYN', 'TY', '8', 'TAIYUAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('390', '390', '台州', '31', 'HYN', 'HYN', 'TZ', '9', 'LUQIAO');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('296', '296', '潍坊', '22', 'WEF', 'WEF', 'WF', '1', 'WEIFANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('295', '295', '威海', '22', 'WEH', 'WEH', 'WH', '2', 'WEIHAI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('267', '267', '乌海', '19', 'WUA', 'WUA', 'WH', '4', 'WUHAI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('192', '192', '武汉', '13', 'WUH', 'WUH', 'WH', '5', 'WUHAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('364', '364', '乌鲁木齐', '29', 'URC', 'URC', 'WLMQ', '6', 'WULUMUQI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('229', '229', '无锡', '16', 'WUX', 'WUX', 'WX', '8', 'WUXI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('391', '391', '温州', '31', 'WNZ', 'WNZ', 'WZ', '9', 'WENZHOU');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('110', '110', '梧州', '7', 'WUZ', 'WUZ', 'WZ', '10', 'CHANGZHOUDAO');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('4580', '4580', '兴城', '18', 'XEN', 'XEN', 'XC', '2', 'XINGCHENG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('61', '61', '厦门', '4', 'XMN', 'XMN', 'XM', '3', 'SHAMEN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('281', '281', '西宁', '21', 'XNN', 'XNN', 'XN', '4', 'XINING');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('148', '148', '邢台', '10', 'XNT', 'XNT', 'XT', '6', 'XINGTAI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('195', '195', '襄阳', '13', 'XFN', 'XFN', 'XY', '7', 'XIANGFAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('318', '318', '咸阳', '24', 'XIY', 'XIY', 'XY', '8', 'XIANYANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('319', '319', '延安', '24', 'ENY', 'ENY', 'YA', '1', 'YANAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('339', '339', '宜宾', '26', 'YBP', 'YBP', 'YB', '2', 'YIBIN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('274', '274', '银川', '20', 'INC', 'INC', 'YC', '3', 'YINCHUAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('180', '180', '伊春', '12', 'LDS', 'LDS', 'YC', '4', 'YICHUN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('197', '197', '宜昌', '13', 'YIH', 'YIH', 'YC', '6', 'YICHANG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('231', '231', '盐城', '16', 'YNZ', 'YNZ', 'YC', '7', 'YANCHENG');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('320', '320', '榆林', '24', 'UYN', 'UYN', 'YL', '8', 'YULIN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('297', '297', '烟台', '22', 'YNT', 'YNT', 'YT', '10', 'YANTAI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('208', '208', '永州', '14', 'LLF', 'LLF', 'YZ', '11', 'yongzhou');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('97', '97', '珠海', '6', 'ZUH', 'ZUH', 'ZH', '1', 'ZHUHAI');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('210', '210', '张家界', '14', 'DYG', 'DYG', 'ZJJ', '3', 'ZHANGJIAJIE');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('392', '392', '舟山', '31', 'HSN', 'HSN', 'ZS', '4', 'ZHOUSHAN');
INSERT INTO "HSL_DE"."SYS_CITY" VALUES ('3105', '3105', '中卫', '20', 'ZHY', 'ZHY', 'ZW', '6', 'zhongwei');

-- ----------------------------
-- Indexes structure for table SYS_CITY
-- ----------------------------

-- ----------------------------
-- Checks structure for table SYS_CITY
-- ----------------------------
ALTER TABLE "HSL_DE"."SYS_CITY" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "HSL_DE"."SYS_CITY" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "HSL_DE"."SYS_CITY" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "HSL_DE"."SYS_CITY" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "HSL_DE"."SYS_CITY" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SYS_CITY
-- ----------------------------
ALTER TABLE "HSL_DE"."SYS_CITY" ADD PRIMARY KEY ("ID");
