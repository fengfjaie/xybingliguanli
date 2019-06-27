/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : db_xybingliguanli

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-03-21 15:51:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_admin`
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,
  `adminName` varchar(255) DEFAULT NULL,
  `adminPassword` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'admin', 'admin');

-- ----------------------------
-- Table structure for `t_bumen`
-- ----------------------------
DROP TABLE IF EXISTS `t_bumen`;
CREATE TABLE `t_bumen` (
  `bumenId` int(11) NOT NULL AUTO_INCREMENT,
  `bumenName` varchar(255) DEFAULT NULL,
  `bumenMark` varchar(255) DEFAULT NULL,
  `bumenMark1` varchar(255) DEFAULT NULL,
  `bumenMark2` int(11) DEFAULT NULL,
  PRIMARY KEY (`bumenId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bumen
-- ----------------------------
INSERT INTO `t_bumen` VALUES ('1', '儿科', '儿科', null, null);
INSERT INTO `t_bumen` VALUES ('2', '内科', '内科', null, null);

-- ----------------------------
-- Table structure for `t_rizhi`
-- ----------------------------
DROP TABLE IF EXISTS `t_rizhi`;
CREATE TABLE `t_rizhi` (
  `rizhiId` int(11) NOT NULL AUTO_INCREMENT,
  `rizhiName` varchar(255) NOT NULL,
  `dengluIp` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`rizhiId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_rizhi
-- ----------------------------
INSERT INTO `t_rizhi` VALUES ('1', 'admin', '127.0.0.1', '2019-03-11 20:22:10');
INSERT INTO `t_rizhi` VALUES ('2', 'admin', '127.0.0.1', '2019-03-11 20:26:49');
INSERT INTO `t_rizhi` VALUES ('3', 'user1', '127.0.0.1', '2019-03-11 20:27:19');
INSERT INTO `t_rizhi` VALUES ('4', 'admin', '127.0.0.1', '2019-03-11 20:31:54');
INSERT INTO `t_rizhi` VALUES ('5', 'admin', '127.0.0.1', '2019-03-11 20:32:25');
INSERT INTO `t_rizhi` VALUES ('6', 'user1', '127.0.0.1', '2019-03-11 20:33:25');
INSERT INTO `t_rizhi` VALUES ('7', 'admin', '127.0.0.1', '2019-03-11 20:35:09');
INSERT INTO `t_rizhi` VALUES ('8', 'user1', '127.0.0.1', '2019-03-18 22:36:42');
INSERT INTO `t_rizhi` VALUES ('9', 'user1', '127.0.0.1', '2019-03-20 19:01:23');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(255) DEFAULT NULL,
  `roleMark` varchar(255) DEFAULT NULL,
  `roleMark1` varchar(255) DEFAULT NULL,
  `roleMark2` int(11) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '主管', '主管', null, null);
INSERT INTO `t_role` VALUES ('2', '医生', '医生', null, null);

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL,
  `userPassword` varchar(255) DEFAULT NULL,
  `userXingming` varchar(255) DEFAULT NULL,
  `userSex` int(11) DEFAULT NULL,
  `userAge` int(11) DEFAULT NULL,
  `userPhone` varchar(255) DEFAULT NULL,
  `userMark1` varchar(255) DEFAULT NULL,
  `userMark2` varchar(255) DEFAULT NULL,
  `userMark3` varchar(255) DEFAULT NULL,
  `userMark4` varchar(255) DEFAULT NULL,
  `userDate1` datetime DEFAULT NULL,
  `userDate2` datetime DEFAULT NULL,
  `userType1` int(11) DEFAULT NULL,
  `userType2` int(11) DEFAULT NULL,
  `userImg` varchar(255) DEFAULT NULL,
  `userImgName` varchar(255) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  `roleName` varchar(255) DEFAULT NULL,
  `bumenId` int(11) DEFAULT NULL,
  `bumenName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'user1', 'user1', 'user1', '0', '22', '13012345678', null, null, null, null, '2019-03-11 20:27:11', null, '0', null, null, null, '1', '主管', '1', '儿科');
INSERT INTO `t_user` VALUES ('2', 'user2', 'user2', 'user2', '1', '22', '13012345678', null, null, null, null, '2019-03-11 20:32:56', null, '0', null, null, null, '2', '医生', '2', '内科');

-- ----------------------------
-- Table structure for `t_yonghu`
-- ----------------------------
DROP TABLE IF EXISTS `t_yonghu`;
CREATE TABLE `t_yonghu` (
  `yonghuId` int(11) NOT NULL AUTO_INCREMENT,
  `yonghuName` varchar(255) DEFAULT NULL,
  `yonghuPassword` varchar(255) DEFAULT NULL,
  `yonghuXingming` varchar(255) DEFAULT NULL,
  `yonghuSex` int(11) DEFAULT NULL,
  `yonghuAge` int(11) DEFAULT NULL,
  `yonghuPhone` varchar(255) DEFAULT NULL,
  `yonghuMark1` varchar(255) DEFAULT NULL,
  `yonghuMark2` varchar(255) DEFAULT NULL,
  `yonghuMark3` varchar(255) DEFAULT NULL,
  `yonghuMark4` varchar(255) DEFAULT NULL,
  `yonghuDate1` datetime DEFAULT NULL,
  `yonghuDate2` datetime DEFAULT NULL,
  `yonghuType1` int(11) DEFAULT NULL,
  `yonghuType2` int(11) DEFAULT NULL,
  `yonghuImg` varchar(255) DEFAULT NULL,
  `yonghuImgName` varchar(255) DEFAULT NULL,
  `yhroleId` int(11) DEFAULT NULL,
  `yhroleName` varchar(255) DEFAULT NULL,
  `yhbumenId` int(11) DEFAULT NULL,
  `yhbumenName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`yonghuId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_yonghu
-- ----------------------------
INSERT INTO `t_yonghu` VALUES ('1', 'yonghu1', null, null, '0', '22', '13012345678', 'yonghu1', 'yonghu1', null, null, '2019-03-11 20:28:47', null, '1', null, null, null, null, null, '1', 'user1');
INSERT INTO `t_yonghu` VALUES ('2', 'yonghu2', null, null, '0', '22', '13012345678', 'yonghu2', 'yonghu2', null, null, '2019-03-11 20:29:14', null, '0', null, null, null, null, null, '1', 'user1');
INSERT INTO `t_yonghu` VALUES ('3', 'yonghu3', null, null, '0', '22', '13012345678', 'yonghu3', 'yonghu3', null, null, '2019-03-11 20:33:44', null, '1', null, null, null, null, null, '1', 'user1');

-- ----------------------------
-- Table structure for `t_yxinxi`
-- ----------------------------
DROP TABLE IF EXISTS `t_yxinxi`;
CREATE TABLE `t_yxinxi` (
  `yxinxiId` int(11) NOT NULL AUTO_INCREMENT,
  `yxinxiName` varchar(255) DEFAULT NULL,
  `yxinxiMark` varchar(255) DEFAULT NULL,
  `yxinxiMark1` varchar(255) DEFAULT NULL,
  `yxinxiMark2` varchar(255) DEFAULT NULL,
  `yxinxiImg` varchar(255) DEFAULT NULL,
  `yxinxiImgName` varchar(255) DEFAULT NULL,
  `yxinxiDate` datetime DEFAULT NULL,
  `yxinxiType` int(11) DEFAULT NULL,
  `yxinxiType1` int(11) DEFAULT NULL,
  `yxtypeId` int(11) DEFAULT NULL,
  `yxtypeName` varchar(255) DEFAULT NULL,
  `yonghuId` int(11) DEFAULT NULL,
  `yonghuName` varchar(255) DEFAULT NULL,
  `yhbumenId` int(11) DEFAULT NULL,
  `yhbumenName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`yxinxiId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_yxinxi
-- ----------------------------
INSERT INTO `t_yxinxi` VALUES ('2', '病历信息2', '病历信息2', '病历信息2病历信息2病历信息2', null, null, null, '2019-03-11 20:31:10', '1', null, '1', '类型1', '2', 'yonghu2', '1', 'user1');
INSERT INTO `t_yxinxi` VALUES ('3', '病历信息3', '病历信息3', '病历信息3病历信息', '3病历信息33病历信息3', '/file/shangchuan.doc', 'shangchuan.doc', '2019-03-11 20:34:05', '0', null, '1', '类型1', '3', 'yonghu3', '1', 'user1');

-- ----------------------------
-- Table structure for `t_yxtype`
-- ----------------------------
DROP TABLE IF EXISTS `t_yxtype`;
CREATE TABLE `t_yxtype` (
  `yxtypeId` int(11) NOT NULL AUTO_INCREMENT,
  `yxtypeName` varchar(255) DEFAULT NULL,
  `yxtypeMark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`yxtypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_yxtype
-- ----------------------------
INSERT INTO `t_yxtype` VALUES ('1', '类型1', '类型1');
