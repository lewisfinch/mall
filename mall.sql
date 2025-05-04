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

 Date: 04/05/2025 01:44:59
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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (3, 'iPhone', 500, 10, 'iPhone.jpg', 'Electronics', 0, 0);
INSERT INTO `item` VALUES (4, 'Laptop', 1500, 10, 'Laptop.jpg', 'Electronics', 0, 1);
INSERT INTO `item` VALUES (5, 'Smart Watch', 200, 15, 'SmartWatch.jpg', 'Electronics', 5, 0);
INSERT INTO `item` VALUES (6, 'Wireless Earbuds', 120, 20, 'Earbuds.jpg', 'Electronics', 12, 1);
INSERT INTO `item` VALUES (7, 'Tablet', 350, 8, 'Tablet.jpg', 'Electronics', 3, 0);
INSERT INTO `item` VALUES (8, 'Bluetooth Speaker', 80, 25, 'Speaker.jpg', 'Electronics', 7, 0);
INSERT INTO `item` VALUES (9, 'Gaming Mouse', 65, 30, 'GamingMouse.jpg', 'Electronics', 9, 1);
INSERT INTO `item` VALUES (10, 'External SSD', 130, 12, 'ExternalSSD.jpg', 'Electronics', 4, 0);
INSERT INTO `item` VALUES (11, 'DSLR Camera', 800, 10, 'DSLRCamera.jpg', 'Electronics', 2, 1);
INSERT INTO `item` VALUES (12, 'Smart Home Hub', 150, 18, 'SmartHomeHub.jpg', 'Electronics', 6, 0);
INSERT INTO `item` VALUES (13, 'Fitness Tracker', 90, 22, 'FitnessTracker.jpg', 'Electronics', 11, 0);
INSERT INTO `item` VALUES (14, 'VR Headset', 400, 7, 'VRHeadset.jpg', 'Electronics', 3, 1);
INSERT INTO `item` VALUES (15, 'iPhone', 500, 10, 'iPhone.jpg', 'Electronics', 0, 0);
INSERT INTO `item` VALUES (16, 'Laptop', 1500, 10, 'Laptop.jpg', 'Electronics', 0, 1);
INSERT INTO `item` VALUES (17, 'Smart Watch', 200, 15, 'SmartWatch.jpg', 'Electronics', 5, 0);
INSERT INTO `item` VALUES (18, 'Wireless Earbuds', 120, 20, 'Earbuds.jpg', 'Electronics', 12, 1);
INSERT INTO `item` VALUES (19, 'Tablet', 350, 8, 'Tablet.jpg', 'Electronics', 3, 0);
INSERT INTO `item` VALUES (20, 'Bluetooth Speaker', 80, 25, 'Speaker.jpg', 'Electronics', 7, 0);
INSERT INTO `item` VALUES (21, 'Gaming Mouse', 65, 30, 'GamingMouse.jpg', 'Electronics', 9, 1);
INSERT INTO `item` VALUES (22, 'External SSD', 130, 12, 'ExternalSSD.jpg', 'Electronics', 4, 0);
INSERT INTO `item` VALUES (23, 'DSLR Camera', 800, 5, 'DSLRCamera.jpg', 'Electronics', 2, 1);
INSERT INTO `item` VALUES (24, 'Smart Home Hub', 150, 18, 'SmartHomeHub.jpg', 'Electronics', 6, 0);
INSERT INTO `item` VALUES (25, 'Fitness Tracker', 90, 22, 'FitnessTracker.jpg', 'Electronics', 11, 0);
INSERT INTO `item` VALUES (26, 'VR Headset', 400, 7, 'VRHeadset.jpg', 'Electronics', 3, 1);
INSERT INTO `item` VALUES (27, 'Cotton T-Shirt', 25, 50, 'TShirt.jpg', 'Clothing', 30, 0);
INSERT INTO `item` VALUES (28, 'Denim Jeans', 60, 35, 'Jeans.jpg', 'Clothing', 15, 1);
INSERT INTO `item` VALUES (29, 'Running Shoes', 85, 20, 'RunningShoes.jpg', 'Clothing', 12, 0);
INSERT INTO `item` VALUES (30, 'Winter Jacket', 120, 15, 'WinterJacket.jpg', 'Clothing', 8, 1);
INSERT INTO `item` VALUES (31, 'Coffee Table', 150, 10, 'CoffeeTable.jpg', 'Furniture', 5, 0);
INSERT INTO `item` VALUES (32, 'Desk Lamp', 45, 25, 'DeskLamp.jpg', 'Furniture', 10, 0);
INSERT INTO `item` VALUES (33, 'Sofa', 600, 5, 'Sofa.jpg', 'Furniture', 3, 1);
INSERT INTO `item` VALUES (34, 'Rice Cooker', 70, 18, 'RiceCooker.jpg', 'Home', 7, 0);
INSERT INTO `item` VALUES (35, 'Blender', 55, 22, 'Blender.jpg', 'Home', 9, 0);
INSERT INTO `item` VALUES (36, 'Chocolate Bar', 3, 100, 'Chocolate.jpg', 'Food', 45, 1);
INSERT INTO `item` VALUES (37, 'Organic Coffee', 12, 40, 'Coffee.jpg', 'Food', 18, 0);
INSERT INTO `item` VALUES (38, 'Novel - Mystery', 15, 30, 'MysteryBook.jpg', 'Books', 12, 0);
INSERT INTO `item` VALUES (39, 'Science Textbook', 80, 15, 'ScienceBook.jpg', 'Books', 5, 0);
INSERT INTO `item` VALUES (40, 'Yoga Mat', 35, 25, 'YogaMat.jpg', 'Sports', 10, 1);
INSERT INTO `item` VALUES (41, 'Dumbbell Set', 65, 12, 'Dumbbell.jpg', 'Sports', 6, 0);
INSERT INTO `item` VALUES (42, 'Lipstick', 22, 40, 'Lipstick.jpg', 'Beauty', 18, 1);
INSERT INTO `item` VALUES (43, 'Face Cream', 30, 35, 'FaceCream.jpg', 'Beauty', 15, 0);
INSERT INTO `item` VALUES (44, 'Kids Toy Set', 45, 28, 'ToySet.jpg', 'Toys', 9, 0);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'OrderID',
  `total_fee` int NOT NULL COMMENT 'TotalFee',
  `user_id` int NOT NULL COMMENT 'UserID',
  `status` int NOT NULL COMMENT 'OrderStatus UNPAID/PAID/SHIPPED/CLOSED',
  `create_time` datetime NOT NULL COMMENT 'OrderCreateTime',
  `pay_time` datetime NULL DEFAULT NULL COMMENT 'OrderPayTime',
  `ship_time` datetime NULL DEFAULT NULL COMMENT 'OrderShipTime',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT 'OrderCancelTime',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `orderuserid`(`user_id` ASC) USING BTREE,
  CONSTRAINT `orderuserid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'OrderTable' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (14, 1520, 1, 4, '2025-05-03 23:20:59', '2025-05-03 23:20:59', NULL, '2025-05-03 23:21:19');
INSERT INTO `order` VALUES (15, 4000, 1, 4, '2025-05-03 23:22:12', '2025-05-03 23:22:12', NULL, '2025-05-03 23:23:42');
INSERT INTO `order` VALUES (16, 4000, 1, 4, '2025-05-03 23:24:14', '2025-05-03 23:24:14', NULL, '2025-05-03 23:30:43');
INSERT INTO `order` VALUES (17, 6400, 1, 4, '2025-05-03 23:30:59', '2025-05-03 23:30:59', NULL, '2025-05-03 23:31:06');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'OrderDetailID',
  `order_id` int NOT NULL COMMENT 'OrderID',
  `item_id` int NOT NULL COMMENT 'ItemID',
  `item_num` int NOT NULL COMMENT 'ItemNum',
  `item_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'ItemName',
  `price` int NOT NULL COMMENT 'Price',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ItemImage',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'OrderDetailTable' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (15, 14, 5, 2, 'Smart Watch', 400, 'SmartWatch.jpg');
INSERT INTO `order_detail` VALUES (16, 14, 8, 4, 'Bluetooth Speaker', 320, 'Speaker.jpg');
INSERT INTO `order_detail` VALUES (17, 14, 11, 1, 'DSLR Camera', 800, 'DSLRCamera.jpg');
INSERT INTO `order_detail` VALUES (18, 15, 11, 5, 'DSLR Camera', 4000, 'DSLRCamera.jpg');
INSERT INTO `order_detail` VALUES (19, 16, 11, 5, 'DSLR Camera', 4000, 'DSLRCamera.jpg');
INSERT INTO `order_detail` VALUES (20, 17, 11, 8, 'DSLR Camera', 6400, 'DSLRCamera.jpg');

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'jack', 'WHUye17UoBtnVsVTz3Id/okHGKBmcwQ+3JZlXPf7eA0=', 'white', 'jack', 'jack@gmail.com', 10000);
INSERT INTO `user` VALUES (2, 'sam', 'kAB41luNkv8rsTzCjHZEH8X2AiuabxPfpRFrfT+S8Lc=', 'sam', 'walker', 'sam@666.com', 10000);
INSERT INTO `user` VALUES (3, 'jimmy', 'z5TPi+V5qoealYP6aWWLwruN/eEAfcl6j2l/LmUwOUY=', 'jimmy', 'yang', 'jimmy@yes.com', 10000);

SET FOREIGN_KEY_CHECKS = 1;
