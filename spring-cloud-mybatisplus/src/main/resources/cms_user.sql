/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 123.56.72.90:3306
 Source Schema         : yuexiang

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 05/06/2020 14:14:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cms_user
-- ----------------------------
DROP TABLE IF EXISTS `cms_user`;
CREATE TABLE `cms_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一标识',
  `nick_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `tag_id` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签ID',
  `create_date` datetime(0) NOT NULL COMMENT '创建日期',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `modify_date` datetime(0) NULL DEFAULT NULL COMMENT '最后修改时间',
  `huanxin_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '环信ID',
  `org_id` int(11) NULL DEFAULT NULL COMMENT '机构ID',
  `attention_associator_id` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '关注用户列表',
  `fans_associator_id` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '粉丝用户列表',
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `disabled` tinyint(1) NOT NULL DEFAULT 0 COMMENT '已禁用',
  `org_type` int(1) NULL DEFAULT 0 COMMENT '0普通用户；1机构用户；2僵尸用户',
  `fans_count` int(11) NULL DEFAULT NULL COMMENT '粉丝总数（目前只有机构用到）',
  `client_uuid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机uuid',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `huanxin_id`(`huanxin_id`) USING BTREE,
  INDEX `org_type_index`(`org_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 511953 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `cms_user`(`id`, `uuid`, `nick_name`, `tag_id`, `create_date`, `avatar`, `modify_date`, `huanxin_id`, `org_id`, `attention_associator_id`, `fans_associator_id`, `description`, `disabled`, `org_type`, `fans_count`, `client_uuid`) VALUES (1, '0c33dd8d-5617-435b-bead-74018b1e28b3', '     __待我长发及腰° ❀', '1,2,3,4,5', '2016-11-21 11:50:09', 'https://yuexiang-video.oss-cn-beijing.aliyuncs.com/2019/04/04/15-43-270404-1432391974.jpg', '2019-04-04 15:44:55', 'Huanxin1-1523183201804', NULL, NULL, '264653', '', 0, 1, 164907, '157895');
