/*
SQLyog Ultimate v8.53 
MySQL - 5.5.29-enterprise-commercial-advanced : Database - wangpan
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wangpan` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wangpan`;

/*Table structure for table `files` */

DROP TABLE IF EXISTS `files`;

CREATE TABLE `files` (
  `fid` varchar(50) NOT NULL DEFAULT '',
  `url` varchar(255) DEFAULT NULL,
  `md5` varchar(255) DEFAULT NULL,
  `uploadTime` datetime DEFAULT NULL,
  `alreadySize` int(11) DEFAULT NULL,
  `allSize` int(11) DEFAULT NULL,
  `isFinish` int(11) DEFAULT NULL,
  `uid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `files` */

insert  into `files`(`fid`,`url`,`md5`,`uploadTime`,`alreadySize`,`allSize`,`isFinish`,`uid`) values ('f7e26d7d-c2d6-4171-b9d8-8d1bb7fc21e5','H:\\upload\\4be2b444-c425-4607-9ae7-3df21332c997.exe','3ea4ae44e80394cdfa7a369488f7ee95','2019-06-27 16:49:13',0,101314344,1,'0');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` varchar(50) NOT NULL DEFAULT '',
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`uid`,`username`,`password`,`nickname`) values ('0','root','root','超级管理员');

/*Table structure for table `userfiles` */

DROP TABLE IF EXISTS `userfiles`;

CREATE TABLE `userfiles` (
  `ufid` varchar(50) NOT NULL DEFAULT '',
  `uid` varchar(50) DEFAULT NULL,
  `fid` varchar(50) DEFAULT NULL,
  `uploadTime` datetime DEFAULT NULL,
  `isSecondPass` int(11) DEFAULT NULL,
  PRIMARY KEY (`ufid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `userfiles` */

insert  into `userfiles`(`ufid`,`uid`,`fid`,`uploadTime`,`isSecondPass`) values ('22a70467-d820-44f9-b6d1-f9b25a321c9f','0','f7e26d7d-c2d6-4171-b9d8-8d1bb7fc21e5','2019-06-27 16:49:13',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
