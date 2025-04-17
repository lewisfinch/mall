/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80300
 Source Host           : localhost:3306
 Source Schema         : mall

 Target Server Type    : MySQL
 Target Server Version : 80300
 File Encoding         : 65001

 Date: 14/04/2025 08:46:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `item_id` int NOT NULL,
  `item_num` int NOT NULL,
  `item_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` int NOT NULL,
  `item_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id` ASC) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `itemid`(`item_id` ASC) USING BTREE,
  CONSTRAINT `itemid` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (3, 1, 1, 3, 'iPhone', 500, 'iPhone.jpg');

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `price` int NOT NULL,
  `stock` int NOT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sold` int NULL DEFAULT 0,
  `isAD` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (1, 'iPhone', 500, 2, 'iPhone.jpg', 'Electronics', 3, 0);
INSERT INTO `item` VALUES (2, 'Laptop', 1500, 2, 'Laptop.jpg', 'Electronics', 1, 1);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `total_fee` int NOT NULL COMMENT '订单总金额（单位：分）',
  `user_id` int NOT NULL COMMENT '下单用户ID',
  `status` int NOT NULL COMMENT '订单状态，如 UNPAID/PAID/SHIPPED/CLOSED',
  `create_time` datetime NOT NULL COMMENT '订单创建时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `ship_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `close_time` datetime NULL DEFAULT NULL COMMENT '订单关闭时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orderuserid`(`user_id` ASC) USING BTREE,
  CONSTRAINT `orderuserid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 500, 1, 0, '2025-04-05 18:45:48', NULL, NULL, NULL);
INSERT INTO `order` VALUES (2, 6500, 1, 1, '2025-04-08 07:07:30', NULL, NULL, NULL);
INSERT INTO `order` VALUES (3, 6500, 1, 1, '2025-04-08 07:07:43', NULL, NULL, NULL);
INSERT INTO `order` VALUES (4, 6500, 1, 1, '2025-04-08 07:09:56', NULL, NULL, NULL);
INSERT INTO `order` VALUES (5, 6500, 1, 1, '2025-04-08 07:20:19', NULL, NULL, NULL);
INSERT INTO `order` VALUES (6, 6500, 1, 1, '2025-04-08 07:23:54', NULL, NULL, NULL);
INSERT INTO `order` VALUES (7, 2000, 1, 1, '2025-04-08 07:25:49', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单详情ID',
  `order_id` int NOT NULL COMMENT '对应订单ID',
  `item_id` int NOT NULL COMMENT '商品ID',
  `item_num` int NOT NULL COMMENT '购买数量',
  `item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `price` int NOT NULL COMMENT '商品单价（单位：分）',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1, 6, 1, 7, 'iPhone', 3500, 'iPhone.jpg');
INSERT INTO `order_detail` VALUES (2, 6, 2, 2, 'Laptop', 3000, 'Laptop.jpg');
INSERT INTO `order_detail` VALUES (3, 7, 1, 1, 'iPhone', 500, 'iPhone.jpg');
INSERT INTO `order_detail` VALUES (4, 7, 2, 1, 'Laptop', 1500, 'Laptop.jpg');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `fname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `balance` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'jack', 'WHUye17UoBtnVsVTz3Id/okHGKBmcwQ+3JZlXPf7eA0=', 'white', 'jack', 'jack@gmail.com', 1000000);

SET FOREIGN_KEY_CHECKS = 1;
