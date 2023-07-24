/*
 Navicat Premium Data Transfer

 Source Server         : 服务器
 Source Server Type    : MySQL
 Source Server Version : 80027 (8.0.27)
 Source Host           : 120.78.75.230:3306
 Source Schema         : ar_animal

 Target Server Type    : MySQL
 Target Server Version : 80027 (8.0.27)
 File Encoding         : 65001

 Date: 12/07/2023 13:46:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_activity
-- ----------------------------
DROP TABLE IF EXISTS `t_activity`;
CREATE TABLE `t_activity`  (
  `activity_id` bigint NOT NULL AUTO_INCREMENT COMMENT '活动主键id',
  `publish_uid` bigint NOT NULL COMMENT '活动的发布者id',
  `activity_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动标题',
  `activity_describe` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动详情描述',
  `activity_classify` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动分类',
  `activity_place` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动地址',
  `cover_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动封面',
  `request_time` bigint NOT NULL COMMENT '申请时间',
  `start_time` bigint NOT NULL COMMENT '活动开始时间',
  `end_time` bigint NOT NULL COMMENT '活动截止时间',
  `update_time` bigint NULL DEFAULT NULL COMMENT '修改时间',
  `people_ceiling` int NOT NULL COMMENT '活动参与人数的上限',
  `audit_state` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '审核状态：待审核、审核通过、审核不通过',
  `audit_reason` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核所要给的原因',
  `audit_time` bigint NULL DEFAULT NULL COMMENT '管理员最新的审核时间',
  PRIMARY KEY (`activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_activity_custom_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_custom_tag`;
CREATE TABLE `t_activity_custom_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签主键id',
  `tag_describe` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动的自定义标签名称',
  `activity_id` bigint NOT NULL COMMENT '活动主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1660611348179070979 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_activity_join
-- ----------------------------
DROP TABLE IF EXISTS `t_activity_join`;
CREATE TABLE `t_activity_join`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '参见活动主键id',
  `user_id` bigint NOT NULL COMMENT '参加活动的用户id',
  `activity_id` bigint NOT NULL COMMENT '活动id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_adminer
-- ----------------------------
DROP TABLE IF EXISTS `t_adminer`;
CREATE TABLE `t_adminer`  (
  `adminer_id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `adminer_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员昵称',
  `adminer_account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员账号',
  `adminer_pwd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员密码',
  `adminer_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员头像',
  `is_delete` int NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`adminer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_animal
-- ----------------------------
DROP TABLE IF EXISTS `t_animal`;
CREATE TABLE `t_animal`  (
  `animal_id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `animal_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动物名称',
  `alias` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该动物的别名',
  `animal_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'http://120.78.75.230:80/' COMMENT '该动物的图片地址',
  `is_vertebrates` int NOT NULL DEFAULT 1 COMMENT '1为脊椎动物，0为非脊椎动物',
  `is_suckle` int NOT NULL DEFAULT 0 COMMENT '1为哺乳动物，0为非哺乳动物',
  `is_birds` int NOT NULL DEFAULT 0 COMMENT '1为鸟类动物，0为非鸟类动物',
  `is_creep` int NOT NULL DEFAULT 0 COMMENT '1为爬行动物，0为非爬行动物',
  `is_fish` int NULL DEFAULT 0 COMMENT '1为鱼类，0为非鱼类',
  `is_amphibian` int NULL DEFAULT 0 COMMENT '1为两栖类，0为非两栖类',
  `animal_classification` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '界门纲目科属种',
  `distribution_area` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分布区域',
  `protect_grade` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保护等级',
  PRIMARY KEY (`animal_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_animal_introduce
-- ----------------------------
DROP TABLE IF EXISTS `t_animal_introduce`;
CREATE TABLE `t_animal_introduce`  (
  `animal_id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `morphology` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '形态特征',
  `habitat` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '栖息环境',
  `habits` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '生活习性',
  `population_status` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '种群现状',
  `protection_level` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '保护级别',
  `else_info` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '其他相关信息',
  PRIMARY KEY (`animal_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_animal_label
-- ----------------------------
DROP TABLE IF EXISTS `t_animal_label`;
CREATE TABLE `t_animal_label`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `animal_id` bigint NULL DEFAULT NULL COMMENT '动物id',
  `animal_label` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动物标签',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 128 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_animal_rescue_phone
-- ----------------------------
DROP TABLE IF EXISTS `t_animal_rescue_phone`;
CREATE TABLE `t_animal_rescue_phone`  (
  `phone_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rescue_center` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '救助中心',
  `rescue_phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '救助电话',
  `rescue_center_province` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '救护中心所在的省',
  PRIMARY KEY (`phone_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_answer_question
-- ----------------------------
DROP TABLE IF EXISTS `t_answer_question`;
CREATE TABLE `t_answer_question`  (
  `answer_id` bigint NOT NULL AUTO_INCREMENT COMMENT '问题回答的id',
  `user_id` bigint NOT NULL COMMENT '该回答的用户id',
  `question_id` bigint NOT NULL COMMENT '被回答的问题id',
  `answer_content` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该回答的内容',
  `is_perfect_answer` int NULL DEFAULT NULL COMMENT '是否被问题作者选为最佳答案',
  PRIMARY KEY (`answer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `author_account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者账号',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '简介',
  `body_id` bigint NULL DEFAULT NULL COMMENT '内容id',
  `category_id` int NULL DEFAULT NULL COMMENT '圈子id',
  `article_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '原创' COMMENT '原创或转载',
  `view_counts` int NULL DEFAULT 0 COMMENT '浏览数量',
  `support_counts` int NULL DEFAULT 0 COMMENT '点赞数量',
  `save_counts` int NULL DEFAULT 0 COMMENT '收藏数量',
  `comment_counts` int NULL DEFAULT 0 COMMENT '评论数量',
  `weight` int NULL DEFAULT 0 COMMENT '是否置顶',
  `create_date` bigint NOT NULL COMMENT '创建时间',
  `visit_permission` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '全部可见' COMMENT '访问权限：全部可见、仅我可见、关注可见',
  `audit_state` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '审核状态：待审核、审核通过、审核不通过',
  `audit_reason` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核所要给的原因',
  `is_delete` int NULL DEFAULT 0 COMMENT '逻辑删除',
  `is_read` int NULL DEFAULT 0 COMMENT '是否已经展示',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1663534106798108674 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_article_body
-- ----------------------------
DROP TABLE IF EXISTS `t_article_body`;
CREATE TABLE `t_article_body`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `content_html` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '存放html格式的文章信息',
  `article_id` bigint NOT NULL COMMENT '文章id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1663534106936520706 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_article_cover_img
-- ----------------------------
DROP TABLE IF EXISTS `t_article_cover_img`;
CREATE TABLE `t_article_cover_img`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `article_id` bigint NOT NULL COMMENT '文章id',
  `cover_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '该文章的封面url地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1663534107016212483 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_article_custom_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_article_custom_tag`;
CREATE TABLE `t_article_custom_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `custom_tag_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章的自定义标签名称',
  `article_id` bigint NOT NULL COMMENT '文章id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1663534106894577666 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `theme_id` bigint NOT NULL COMMENT '该圈子所属的主题的主键id',
  `category_label` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章圈子的标签',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文章圈子名称',
  `article_count` bigint NULL DEFAULT 0 COMMENT '该圈子现有文章数量',
  `category_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章圈子的图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_category_theme
-- ----------------------------
DROP TABLE IF EXISTS `t_category_theme`;
CREATE TABLE `t_category_theme`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `theme_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '圈子主题名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `content` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
  `create_date` bigint NOT NULL COMMENT '评论发表时间',
  `article_id` bigint NOT NULL COMMENT '文章id',
  `author_account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论的作者账号',
  `parent_id` bigint NOT NULL COMMENT '父评论的评论id',
  `to_uid` bigint NOT NULL COMMENT '父评论的用户id',
  `level` int NOT NULL COMMENT '评论的层数',
  `support_counts` int NULL DEFAULT 0 COMMENT '点赞数量',
  `is_delete` int NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1646799995543801872 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_forest_police_phone
-- ----------------------------
DROP TABLE IF EXISTS `t_forest_police_phone`;
CREATE TABLE `t_forest_police_phone`  (
  `phone_id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `forest_police` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当地的森林公安',
  `forest_police_phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当地的森林公安电话',
  `forest_police_province` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '当地的森林公安所在省',
  PRIMARY KEY (`phone_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_knowledge_question
-- ----------------------------
DROP TABLE IF EXISTS `t_knowledge_question`;
CREATE TABLE `t_knowledge_question`  (
  `knowledge_question_id` bigint NOT NULL AUTO_INCREMENT COMMENT '知识问题的主键id',
  `question_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题的内容',
  `question_type_id` bigint NOT NULL COMMENT '该问题的类型',
  `correct_option` int NOT NULL COMMENT '该问题的正确选项',
  `content_of_option_a` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该问题的选项a的内容',
  `content_of_option_b` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该问题的选项b的内容',
  `content_of_option_c` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该问题的选项c的内容',
  `content_of_option_d` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该问题的选项d的内容',
  `is_delete` int NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`knowledge_question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_knowledge_question_type
-- ----------------------------
DROP TABLE IF EXISTS `t_knowledge_question_type`;
CREATE TABLE `t_knowledge_question_type`  (
  `question_type_id` int NOT NULL AUTO_INCREMENT COMMENT '问题类型的id',
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型描述',
  PRIMARY KEY (`question_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_knowledge_question_user
-- ----------------------------
DROP TABLE IF EXISTS `t_knowledge_question_user`;
CREATE TABLE `t_knowledge_question_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int NOT NULL COMMENT '用户id',
  `knowledge_question_id` bigint NOT NULL COMMENT '知识问题id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_opinion
-- ----------------------------
DROP TABLE IF EXISTS `t_opinion`;
CREATE TABLE `t_opinion`  (
  `opinion_id` bigint NOT NULL AUTO_INCREMENT COMMENT '意见的id',
  `user_id` bigint NOT NULL COMMENT '发表该意见的用户id',
  `opinion_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '意见内容',
  `submit_time` bigint NOT NULL COMMENT '提交时间',
  `update_time` bigint NULL DEFAULT NULL COMMENT '修改时间',
  `is_delete` int NULL DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`opinion_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_opinion_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_opinion_reply`;
CREATE TABLE `t_opinion_reply`  (
  `opinion_reply_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户意见回复的主键id',
  `reply_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回复的内容',
  `opinion_id` bigint NOT NULL COMMENT '对应的用户意见id',
  `is_delete` int NULL DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`opinion_reply_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_question
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question`  (
  `question_id` bigint NOT NULL AUTO_INCREMENT COMMENT '问题id',
  `user_id` int NOT NULL COMMENT '发表该问题的用户',
  `question_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题的标题',
  `question_describe` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问题的描述',
  `publish_time` bigint NULL DEFAULT NULL COMMENT '发表问题的时间',
  `update_time` bigint NULL DEFAULT NULL COMMENT '修改问题的时间',
  `is_urgent` int NULL DEFAULT 0 COMMENT '是否是紧急的问题',
  `is_finish` int NULL DEFAULT 0 COMMENT '是否以完成',
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1655420242319601669 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_question_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_question_tag`;
CREATE TABLE `t_question_tag`  (
  `question_tag_id` bigint NOT NULL AUTO_INCREMENT COMMENT '问题标签id',
  `question_id` bigint NOT NULL COMMENT '问题id',
  `tag_info` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签信息',
  PRIMARY KEY (`question_tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_recommend_article
-- ----------------------------
DROP TABLE IF EXISTS `t_recommend_article`;
CREATE TABLE `t_recommend_article`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `recommend_factor` int NULL DEFAULT NULL COMMENT '推荐系数',
  `article_id` bigint NULL DEFAULT NULL COMMENT '文章id',
  `recommend_time` bigint NULL DEFAULT NULL COMMENT '推荐时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1677990732309360643 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_recommend_category
-- ----------------------------
DROP TABLE IF EXISTS `t_recommend_category`;
CREATE TABLE `t_recommend_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `recommend_factor` int NOT NULL DEFAULT 0 COMMENT '推荐系数',
  `category_id` bigint NOT NULL COMMENT '文章圈子id',
  `category_label` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章圈子标签',
  `category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章圈子名称',
  `article_count` int NULL DEFAULT 0 COMMENT '该圈子现有文章数量',
  `category_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章圈子的图标',
  `recommend_time` bigint NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1677984349891489795 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_recommend_user
-- ----------------------------
DROP TABLE IF EXISTS `t_recommend_user`;
CREATE TABLE `t_recommend_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `recommend_factor` int NOT NULL DEFAULT 0 COMMENT '推荐系数',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `user_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户头像',
  `recommend_time` bigint NOT NULL COMMENT '推荐时间',
  INDEX `id`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1677982242652188674 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_scheduled
-- ----------------------------
DROP TABLE IF EXISTS `t_scheduled`;
CREATE TABLE `t_scheduled`  (
  `cron_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cron_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '定时任务的名称',
  `cron` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '定时任务的频率和时间',
  PRIMARY KEY (`cron_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_support_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_support_answer`;
CREATE TABLE `t_support_answer`  (
  `support_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int NOT NULL COMMENT '点赞该回答的用户id',
  `answer_id` bigint NOT NULL COMMENT '被点赞的回答id',
  PRIMARY KEY (`support_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_support_question
-- ----------------------------
DROP TABLE IF EXISTS `t_support_question`;
CREATE TABLE `t_support_question`  (
  `support_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int NOT NULL COMMENT '点赞该问题的用户id',
  `question_id` bigint NOT NULL COMMENT '被点赞的问题id',
  PRIMARY KEY (`support_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `user_account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `user_pwd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `user_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'http://120.78.75.230:80/xmTV4A-49.jpg' COMMENT '用户头像',
  `create_time` bigint NULL DEFAULT NULL COMMENT '用户创建的时间',
  `is_delete` int NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `user_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户定位地址',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_article
-- ----------------------------
DROP TABLE IF EXISTS `t_user_article`;
CREATE TABLE `t_user_article`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '收藏的用户id',
  `article_id` bigint NOT NULL COMMENT '被收藏的文章id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1658385182992384002 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_carer
-- ----------------------------
DROP TABLE IF EXISTS `t_user_carer`;
CREATE TABLE `t_user_carer`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `carer_id` bigint NOT NULL COMMENT '被该用户关注的用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1674605971624837123 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_support
-- ----------------------------
DROP TABLE IF EXISTS `t_user_support`;
CREATE TABLE `t_user_support`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '点赞的用户id',
  `article_id` bigint NULL DEFAULT NULL COMMENT '为null说明不是点赞文章',
  `comment_id` bigint NULL DEFAULT NULL COMMENT '为null说明不是点赞评论',
  `question_id` bigint NULL DEFAULT NULL COMMENT '为null说明不是点赞问题',
  `answer_id` bigint NULL DEFAULT NULL COMMENT '为null说明不是点赞回答',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1618630720656347138 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
