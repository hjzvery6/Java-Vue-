

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS `common-market`;
CREATE DATABASE `common-market`;
use `common-market`;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '联系方式',
  `userId` int NULL DEFAULT NULL COMMENT '用户ID',
  `role` int NULL DEFAULT NULL COMMENT '用户角色',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货地址',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '收货地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (5, 1, 2, '张三的爸爸', '北京', '12255558888');
INSERT INTO `address` VALUES (6, 1, 2, '张三的妈妈', '上海', '1888844444');
INSERT INTO `address` VALUES (7, 1, 2, '张三的公司', '合肥', '1444666666');
INSERT INTO `address` VALUES (8, 2, 2, '李四的家', '合肥市蜀山区666号', '16599998888');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `role` int NOT NULL DEFAULT 1 COMMENT '角色标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '123456', 'http://localhost:8080/files/1695214476088-柯基.jpeg', '18899990000', '男', 1);

-- ----------------------------
-- Table structure for advertising
-- ----------------------------
DROP TABLE IF EXISTS `advertising`;
CREATE TABLE `advertising`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '广告位展示图片',
  `goodsId` int NULL DEFAULT NULL COMMENT '商品Id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主标题',
  `introducing` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '广告为展示信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of advertising
-- ----------------------------
INSERT INTO `advertising` VALUES (15, 'http://localhost:8080/files/1710921487614-图怪兽_a7ef453b18fe87a6a788dfdfaec51e6f_85049.png', 15, '超级新机驾到', '超级新品 非凡体验');
INSERT INTO `advertising` VALUES (16, 'http://localhost:8080/files/1710923173629-包包.png', 16, '包你喜欢', '新款式名牌包包');
INSERT INTO `advertising` VALUES (17, 'http://localhost:8080/files/1710921695192-图怪兽_343bc0c152796cf6e11b4348663c2244_99325.png', 20, '厨房蒸烤系列', '蒸烤好帮手真的好');
INSERT INTO `advertising` VALUES (18, 'http://localhost:8080/files/1710923011074-钟表.png', 21, '你最时尚', '春夏时尚单品 ');
INSERT INTO `advertising` VALUES (19, 'http://localhost:8080/files/1710922667176-微信图片_20240320161732.png', 26, '经典凌驾潮流', '2024新概念轻跑鞋');

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '轮播图图片',
  `goodsId` int NULL DEFAULT NULL COMMENT '商品Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '轮播图管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of banner
-- ----------------------------
INSERT INTO `banner` VALUES (20, 'http://localhost:8080/files/1711009691740-lun-1.jpg', 15);
INSERT INTO `banner` VALUES (21, 'http://localhost:8080/files/1711009702152-lun-2.jpg', 17);

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `goodsId` int NULL DEFAULT NULL COMMENT '商品ID',
  `userId` int NULL DEFAULT NULL COMMENT '用户ID',
  `num` int NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '购物车表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cart
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '评论内容',
  `userId` int NULL DEFAULT NULL COMMENT '用户ID',
  `role` int NULL DEFAULT NULL COMMENT '用户角色',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论时间',
  `parentId` int NULL DEFAULT NULL COMMENT '父ID',
  `relId` int NULL DEFAULT NULL COMMENT '关联ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (4, '衣服非常非常非常时尚 穿上很漂亮 很喜欢', 1, 2, '2024-03-25 17:24:33', 0, 17);
INSERT INTO `comment` VALUES (5, '真的很好好看啊 ', 1, 2, '2024-03-25 17:24:54', 4, 17);
INSERT INTO `comment` VALUES (6, '很好的', 1, 2, '2024-04-02 22:08:43', 0, 17);
INSERT INTO `comment` VALUES (7, '回复评价', 1, 2, '2024-04-02 22:09:07', 6, 17);
INSERT INTO `comment` VALUES (8, '1', 1, 2, '2024-04-06 21:39:50', 4, 17);
INSERT INTO `comment` VALUES (9, '123', 1, 2, '2024-04-06 21:40:03', 0, 16);
INSERT INTO `comment` VALUES (10, '345', 1, 2, '2024-04-06 21:40:15', 9, 16);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品图片',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品地区',
  `price` double(10, 2) NULL DEFAULT 0.00 COMMENT '商品价格',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '商品介绍',
  `typeId` int NULL DEFAULT NULL COMMENT '商品分类ID',
  `praise` int NULL DEFAULT 0 COMMENT '点赞数',
  `goodsnum` int NULL DEFAULT 0 COMMENT '库存',
  `ishot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '否' COMMENT '是否热门',
  `goodsmodel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '否' COMMENT '型号',
  `originalprice` double(10, 2) NULL DEFAULT 0.00 COMMENT '商品原价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (15, 'http://localhost:8080/files/1711014826497-ANMANI恩曼琳24春法式撞色翻领修身鱼尾针织连衣裙女.jpg', 'ANMANI恩曼琳24春法式撞色翻领修身鱼尾针织连衣裙女', '北京', 678.00, '<p><img src=\"http://localhost:8080/files/1711014861334\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 1, 0, 1198, '是', '160/80A', 1000.00);
INSERT INTO `goods` VALUES (16, 'http://localhost:8080/files/1711015262128-ANMANI恩曼琳24春新品优雅蕾丝拼接V领鱼尾连衣裙礼服女.jpg', 'ANMANI恩曼琳24春新品优雅蕾丝拼接V领鱼尾连衣裙礼服女', '上海', 789.00, '<p><img src=\"http://localhost:8080/files/1711015294129\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 1, 0, 995, '是', 'A/S', 999.00);
INSERT INTO `goods` VALUES (17, 'http://localhost:8080/files/1711015828574-【复古千鸟格】春秋时尚经典中长款A字连衣裙.jpg', '【复古千鸟格】春秋时尚经典中长款A字连衣裙', '北京', 189.00, '<p><img src=\"http://localhost:8080/files/1711015857190\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 1, 0, 996, '是', '88756', 499.00);
INSERT INTO `goods` VALUES (18, 'http://localhost:8080/files/1711015970336-罗衣魔衣2024春装气质镂空收腰连衣裙女时尚拼接雪纺中长裙.jpg', '罗衣魔衣2024春装气质镂空收腰连衣裙女时尚拼接雪纺中长裙', '北京', 899.00, '<p data-we-empty-p=\"\" style=\"text-align:center;\"><img src=\"http://localhost:8080/files/1711015996801\" style=\"max-width:100%;\" contenteditable=\"false\"/><br/><img src=\"http://localhost:8080/files/1711016007021\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 1, 0, 999, '是', 'B12344566', 1099.00);
INSERT INTO `goods` VALUES (19, 'http://localhost:8080/files/1711016171770-【加绒保暖】冬季牛皮革圆头厚底增高超轻甜酷马丁靴短靴.jpg', '【加绒保暖】冬季牛皮革圆头厚底增高超轻甜酷马丁靴短靴', '上海', 199.00, '<p><img src=\"http://localhost:8080/files/1711016198122\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 2, 0, 1000, '是', 'Y709', 599.00);
INSERT INTO `goods` VALUES (20, 'http://localhost:8080/files/1711016279843-马丁靴女秋冬季系带简约厚底百搭舒适圆头短靴女时尚职业通勤靴女.jpg', '马丁靴女秋冬季系带简约厚底百搭舒适圆头短靴女时尚职业通勤靴女', 'shanghai', 88.00, '<p><img src=\"http://localhost:8080/files/1711016303633\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 2, 0, 1000, '是', 'U99', 399.00);
INSERT INTO `goods` VALUES (21, 'http://localhost:8080/files/1711016385405-马丁靴男潮流英伦风休闲男靴秋冬款百搭潮流男士靴子短靴靴子.jpg', '马丁靴男潮流英伦风休闲男靴秋冬款百搭潮流男士靴子短靴靴子', 'beijing', 598.00, '<p><img src=\"http://localhost:8080/files/1711016411466\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 2, 0, 1000, '是', 'I99', 899.00);
INSERT INTO `goods` VALUES (22, 'http://localhost:8080/files/1711350830637-男士一脚套饰扣柔软牛皮革开车鞋豆豆鞋懒人鞋休闲鞋皮鞋.jpg', '男士一脚套饰扣柔软牛皮革开车鞋豆豆鞋懒人鞋休闲鞋皮鞋', '浙江', 899.00, '<p><img src=\"http://localhost:8080/files/1711350864454\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 2, 0, 2000, '是', 'R4054', 0.00);
INSERT INTO `goods` VALUES (23, 'http://localhost:8080/files/1711351578178-买1享11 神经酰胺水乳套装 深层补水保湿化妆品护肤品套装.jpg', '买1享11 神经酰胺水乳套装 深层补水保湿化妆品护肤品套装', '合肥', 655.00, '<p><img src=\"http://localhost:8080/files/1711351612576\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 3, 0, 2000, '是', 'E678', 0.00);
INSERT INTO `goods` VALUES (24, 'http://localhost:8080/files/1711351771968-【舒缓补水5件】珍珠舒缓润养洁水乳眼霜精华修护礼盒（眼霜版).jpg', '【舒缓补水5件】珍珠舒缓润养洁水乳眼霜精华修护礼盒（眼霜版）', '上海', 88.00, '<p><img src=\"http://localhost:8080/files/1711351794449\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 3, 0, 1999, '是', 'Y777', 0.00);
INSERT INTO `goods` VALUES (25, 'http://localhost:8080/files/1711351986642-【改善干燥 补水保湿】雪域水乳90mL护肤套装补水保湿舒缓.jpg', '【改善干燥 补水保湿】雪域水乳90mL护肤套装补水保湿舒缓', '开封', 299.00, '<p><img src=\"http://localhost:8080/files/1711352012356\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 3, 0, 1000, '是', 'Q999', 0.00);
INSERT INTO `goods` VALUES (26, 'http://localhost:8080/files/1711352141647-【补水保湿】水感透亮护肤品套装水乳保湿美肌水养清透.jpg', '【补水保湿】水感透亮护肤品套装水乳保湿美肌水养清透', '成都', 2229.00, '<p><img src=\"http://localhost:8080/files/1711352172691\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 3, 0, 2000, '是', 'T985', 0.00);
INSERT INTO `goods` VALUES (27, 'http://localhost:8080/files/1711352311722-舒适保暖 男女中大童款棉质柔软经典大LOGO长袖连帽儿童卫衣.jpg', '舒适保暖 男女中大童款棉质柔软经典大LOGO长袖连帽儿童卫衣', '西安', 299.00, '<p><img src=\"http://localhost:8080/files/1711352390466\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 6, 0, 1999, '是', 'B789', 0.00);
INSERT INTO `goods` VALUES (28, 'http://localhost:8080/files/1711352450740-QIAODAN春季男童圆领套头小童撞色卫衣长袖运动上衣.jpg', 'QIAODAN春季男童圆领套头小童撞色卫衣长袖运动上衣', '北京', 200.00, '<p><img src=\"http://localhost:8080/files/1711352480273\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 4, 0, 1000, '是', 'B788', 0.00);
INSERT INTO `goods` VALUES (29, 'http://localhost:8080/files/1711352551955-QIAODAN男童针织上衣秋季儿童卫衣时尚圆领打底衫.jpg', 'QIAODAN男童针织上衣秋季儿童卫衣时尚圆领打底衫', '合肥', 299.00, '<p><img src=\"http://localhost:8080/files/1711352580948\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 4, 0, 999, '是', 'Y900', 0.00);
INSERT INTO `goods` VALUES (30, 'http://localhost:8080/files/1711352831250-【福利特卖】女童卫衣春季2024童装中大童休闲百搭连帽儿童卫衣.jpg', '【福利特卖】女童卫衣春季2024童装中大童休闲百搭连帽儿童卫衣', '合肥', 199.00, '<p><img src=\"http://localhost:8080/files/1711352855147\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 4, 0, 1000, '是', 'U809', 0.00);
INSERT INTO `goods` VALUES (31, 'http://localhost:8080/files/1711353107119-FindN3 Flip【官方正品】自由折叠5G八核拍照手机.jpg', 'FindN3 Flip【官方正品】自由折叠5G八核拍照手机', '上海', 5369.00, '<p><img src=\"http://localhost:8080/files/1711353147715\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 5, 0, 1000, '是', 'R3996', 0.00);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '公告内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布时间',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公告标题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '上线啦上线啦！', '2023-10-12 11:34:01', '网上商城正式上线啦');

-- ----------------------------
-- Table structure for orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userId` int NULL DEFAULT NULL COMMENT '用户ID',
  `orderId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '下单时间',
  `price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单总价格',
  `goodsId` int NULL DEFAULT NULL COMMENT '商品ID',
  `num` int NULL DEFAULT NULL COMMENT '数量',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单状态',
  `addressId` int NULL DEFAULT NULL COMMENT '地址ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orderinfo
-- ----------------------------
INSERT INTO `orderinfo` VALUES (7, 2, '20240222123326', '2024-02-22 00:33:26', '59.0', 3, 1, '退款完成', 3);
INSERT INTO `orderinfo` VALUES (8, 1, '20240222110637', '2024-02-22 11:06:37', '59.0', 3, 1, '退款完成', 1);
INSERT INTO `orderinfo` VALUES (9, 2, '20240222113441', '2024-02-22 11:34:41', '297.0', 1, 3, '待发货', 3);
INSERT INTO `orderinfo` VALUES (10, 2, '20240222113442', '2024-02-22 11:34:42', '178.0', 2, 2, '待发货', 3);
INSERT INTO `orderinfo` VALUES (11, 3, '20240222125733', '2024-02-22 12:57:33', '99.0', 4, 1, '待发货', 4);
INSERT INTO `orderinfo` VALUES (12, 3, '20240222125755', '2024-02-22 12:57:55', '89.0', 5, 1, '待发货', 4);
INSERT INTO `orderinfo` VALUES (13, 3, '20240222125756', '2024-02-22 12:57:56', '118.0', 6, 2, '待发货', 4);
INSERT INTO `orderinfo` VALUES (14, 1, '20240222013313', '2024-02-22 13:33:13', '89.0', 2, 1, '待发货', 1);
INSERT INTO `orderinfo` VALUES (15, 1, '20240322040718', '2024-03-22 16:07:18', '378.0', 17, 2, '已完成', 5);
INSERT INTO `orderinfo` VALUES (18, 1, '20240322051325', '2024-03-22 17:13:25', '189.0', 17, 1, '已发货', 5);
INSERT INTO `orderinfo` VALUES (23, 1, '20240322052925', '2024-03-22 17:29:25', '789.0', 16, 1, '已完成', 5);
INSERT INTO `orderinfo` VALUES (25, 1, '20240322053944', '2024-03-22 17:39:44', '899.0', 18, 1, '已发货', 5);
INSERT INTO `orderinfo` VALUES (26, 2, '20240325040940', '2024-03-25 16:09:40', '598.0', 21, 1, '待发货', 8);
INSERT INTO `orderinfo` VALUES (27, 2, '20240325041042', '2024-03-25 16:10:42', '88.0', 24, 1, '待发货', 8);
INSERT INTO `orderinfo` VALUES (28, 2, '20240325041042', '2024-03-25 16:10:42', '299.0', 29, 1, '待发货', 8);
INSERT INTO `orderinfo` VALUES (29, 2, '20240325041042', '2024-03-25 16:10:42', '299.0', 27, 1, '待发货', 8);
INSERT INTO `orderinfo` VALUES (30, 1, '20240402100713', '2024-04-02 22:07:13', '189.0', 17, 1, '待发货', 5);
INSERT INTO `orderinfo` VALUES (31, 1, '20240402100713', '2024-04-02 22:07:13', '789.0', 16, 1, '待发货', 5);
INSERT INTO `orderinfo` VALUES (32, 1, '20240402100713', '2024-04-02 22:07:13', '678.0', 15, 1, '待发货', 5);
INSERT INTO `orderinfo` VALUES (33, 1, '20240406093505', '2024-04-06 21:35:05', '678.0', 15, 1, '待发货', 5);

-- ----------------------------
-- Table structure for praise
-- ----------------------------
DROP TABLE IF EXISTS `praise`;
CREATE TABLE `praise`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `userId` int NULL DEFAULT NULL COMMENT '用户ID',
  `role` int NULL DEFAULT NULL COMMENT '用户角色',
  `relId` int NULL DEFAULT NULL COMMENT '关联id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '点赞信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of praise
-- ----------------------------

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '女装/男装/内衣', '女装');
INSERT INTO `type` VALUES (2, '女鞋/男鞋/箱包', '这是分类二');
INSERT INTO `type` VALUES (3, '护肤彩妆/个护', '这是分类三');
INSERT INTO `type` VALUES (4, '运动户外', '运动户外');
INSERT INTO `type` VALUES (5, '家电数码', '家电数码');
INSERT INTO `type` VALUES (6, '母婴童装', '这是母婴童装');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `age` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '年龄',
  `role` int NOT NULL DEFAULT 2 COMMENT '角色标识',
  `account` double(10, 2) NULL DEFAULT 0.00 COMMENT '余额',
  `realname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '真实姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '张三', '123456', 'http://localhost:8080/files/1695214476088-柯基.jpeg', '18899998888', '男', '23', 2, 4899.00, NULL);
INSERT INTO `user` VALUES (2, '李四', '123456', 'http://localhost:8080/files/1694617681909-拉布拉多.jpeg', '18877776666', '女', '23', 2, 9383.00, NULL);

SET FOREIGN_KEY_CHECKS = 1;
