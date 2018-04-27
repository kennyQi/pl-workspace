ALTER TABLE `jf_account`
ADD COLUMN `amount_todo`  decimal(10,0) NULL DEFAULT 0 COMMENT '在途、未结算' AFTER `update_time`;

ALTER TABLE `cal_flow`
ADD COLUMN `is_cal`  tinyint NULL DEFAULT 1 COMMENT '是否是计算：1-是，0-否' AFTER `result_text`;

ALTER TABLE `cal_flow`
MODIFY COLUMN `rule`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '规则编号' AFTER `user`;

