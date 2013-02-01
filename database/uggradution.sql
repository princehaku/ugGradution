-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 11, 2012 at 12:24 PM
-- Server version: 5.0.51
-- PHP Version: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `uggradution`
--

-- --------------------------------------------------------

--
-- Table structure for table `colleague`
--

CREATE TABLE IF NOT EXISTS `colleague` (
  `id` int(5) unsigned NOT NULL auto_increment,
  `name` char(128) NOT NULL,
  `statu` int(1) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `colleague`
--

INSERT INTO `colleague` (`id`, `name`, `statu`) VALUES
(1, '信息科学与技术学院', 1);

-- --------------------------------------------------------

--
-- Table structure for table `major`
--

CREATE TABLE IF NOT EXISTS `major` (
  `id` int(5) unsigned NOT NULL auto_increment,
  `cid` int(5) NOT NULL COMMENT '学院号码',
  `name` char(128) NOT NULL,
  `statu` int(1) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `major`
--

INSERT INTO `major` (`id`, `cid`, `name`, `statu`) VALUES
(1, 1, '计算机科学与技术', 1),
(2, 1, '软件工程', 1),
(3, 1, '电子信息工程', 1),
(4, 1, '通信工程', 1);

-- --------------------------------------------------------

--
-- Table structure for table `news`
--

CREATE TABLE IF NOT EXISTS `news` (
  `id` int(32) unsigned NOT NULL auto_increment,
  `title` varchar(120) NOT NULL COMMENT '标题',
  `text` text NOT NULL,
  `addtime` datetime NOT NULL,
  `authoruid` int(10) unsigned NOT NULL,
  `statu` int(1) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `authoruid` (`authoruid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `news`
--

INSERT INTO `news` (`id`, `title`, `text`, `addtime`, `authoruid`, `statu`) VALUES
(1, '最新通告', '11月11号开始内测wa', '2011-09-23 11:02:54', 1, 0),
(2, 'asdasd', 'asdasd', '2011-10-04 15:06:45', 4, 0),
(3, '瓦嗲爱的', '挖的阿我对', '2011-10-07 16:32:19', 5, 0),
(4, '测试的通告', '123123123', '2011-10-07 16:33:14', 5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `notice`
--

CREATE TABLE IF NOT EXISTS `notice` (
  `id` int(32) unsigned NOT NULL auto_increment,
  `uid` int(32) unsigned NOT NULL,
  `text` text NOT NULL,
  `time` datetime NOT NULL,
  `statu` int(1) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `papersel`
--

CREATE TABLE IF NOT EXISTS `papersel` (
  `id` int(32) unsigned NOT NULL auto_increment,
  `pid` int(32) NOT NULL,
  `yid` int(5) NOT NULL,
  `owneruid` int(32) NOT NULL COMMENT '所有者的id',
  `leftsolts` int(4) NOT NULL default '-1',
  `maxslots` int(4) NOT NULL default '-1',
  `isdel` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `yid` (`yid`),
  KEY `pid` (`pid`),
  KEY `tid` (`owneruid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=35 ;

--
-- Dumping data for table `papersel`
--

INSERT INTO `papersel` (`id`, `pid`, `yid`, `owneruid`, `leftsolts`, `maxslots`, `isdel`) VALUES
(1, 4, 1, 3, 1, 1, 1),
(2, 2, 1, 3, 1, 1, 1),
(3, 3, 1, 3, 1, 1, 1),
(4, 4, 2, 3, 0, 1, 1),
(5, 2, 2, 3, 1, 1, 1),
(6, 3, 2, 3, 0, 1, 1),
(7, 4, 3, 3, 5, 5, 1),
(8, 2, 3, 3, 4, 5, 1),
(9, 3, 3, 3, 4, 5, 1),
(10, 7, 5, 5, 5, 5, 1),
(11, 2, 5, 3, 5, 5, 1),
(12, 4, 5, 3, 5, 5, 1),
(13, 3, 5, 3, 5, 5, 1),
(14, 7, 6, 5, 4, 5, 1),
(15, 2, 6, 3, 5, 5, 1),
(16, 4, 6, 3, 5, 5, 1),
(17, 3, 6, 3, 5, 5, 1),
(18, 7, 7, 5, 5, 5, 1),
(19, 2, 7, 3, 2, 3, 1),
(20, 4, 7, 3, 3, 3, 1),
(21, 3, 7, 3, 3, 3, 1),
(22, 7, 8, 5, 1, 1, 1),
(23, 2, 8, 3, 1, 1, 1),
(24, 4, 8, 3, 1, 1, 1),
(25, 3, 8, 3, 1, 1, 1),
(26, 7, 9, 5, 1, 1, 1),
(27, 2, 9, 3, 1, 1, 1),
(28, 4, 9, 3, 0, 1, 1),
(29, 3, 9, 3, 1, 1, 1),
(30, 7, 10, 5, 1, 1, 1),
(31, 2, 10, 3, 0, 1, 1),
(32, 4, 10, 3, 1, 1, 1),
(33, 3, 10, 3, 1, 1, 1),
(34, 10, 11, 3, 0, 2, 0);

-- --------------------------------------------------------

--
-- Table structure for table `papertrunk`
--

CREATE TABLE IF NOT EXISTS `papertrunk` (
  `id` int(32) unsigned NOT NULL auto_increment,
  `mid` int(5) NOT NULL,
  `uid` int(32) NOT NULL,
  `tags` text NOT NULL,
  `title` varchar(128) NOT NULL,
  `shortdesp` varchar(120) NOT NULL,
  `desp` text NOT NULL,
  `addtime` datetime NOT NULL,
  `statu` int(1) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `mid` (`mid`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `papertrunk`
--

INSERT INTO `papertrunk` (`id`, `mid`, `uid`, `tags`, `title`, `shortdesp`, `desp`, `addtime`, `statu`) VALUES
(1, 1, 3, '基础', '基于服务的数据集成共享平台', '基于服务的数据集成共享平台哇&nbsp; 真的', '<span class="smltxt">基于服务的数据集成共享平台<br>哇&nbsp; 真的<br></span>', '2011-09-19 11:44:00', 0),
(2, 1, 3, '基础,java', '基于服务的数据集成共享平台', '基于服务的数据集成共享平台哇', '<span class="smltxt">基于服务的数据集成共享平台哇</span>', '2011-10-07 16:30:41', 0),
(3, 1, 3, 'awdawd', 'awdawd', 'dawdawdawd', 'dawdawdawd', '2011-09-20 10:19:05', 0),
(4, 1, 3, '移动设备', '未激活的答疑者', '嗲阿我打我的', '嗲阿我打我的', '2011-10-04 15:35:34', 0),
(5, 1, 3, '标签!', '哇哇', '哈哈', '哈哈', '2011-09-30 15:08:43', 0),
(6, 1, 4, '', '', '', '', '2011-10-04 15:32:46', 0),
(7, 1, 5, '123', '23', '123123', '123123', '2011-10-08 09:16:57', 0),
(8, 1, 1, '手机', 'wa ', '描述阿斯顿阿斯顿阿斯顿阿斯顿阿斯顿撒的阿斯顿撒的', '描述阿斯顿阿斯顿阿斯顿阿斯顿阿斯顿撒的阿斯顿撒的', '2012-01-11 15:53:46', 0),
(9, 1, 1, '手机', 'wa 222', '描述阿斯顿阿斯顿阿斯顿阿斯顿阿斯顿撒的阿斯顿撒的\n描述阿斯顿阿斯顿阿', '描述阿斯顿阿斯顿阿斯顿阿斯顿阿斯顿撒的阿斯顿撒的\n描述阿斯顿阿斯顿阿', '2012-01-11 15:53:46', 0),
(10, 1, 3, 'java', 'wda', 'awdawdad', 'awdawdad', '2012-01-11 18:02:11', 1);

-- --------------------------------------------------------

--
-- Table structure for table `paperyear`
--

CREATE TABLE IF NOT EXISTS `paperyear` (
  `id` int(5) unsigned NOT NULL auto_increment,
  `mid` int(5) NOT NULL COMMENT '专业id',
  `maxsel` int(5) NOT NULL COMMENT '每个用户最大选入数量',
  `dtstart` date NOT NULL,
  `dtend` date NOT NULL,
  `statu` int(1) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `paperyear`
--

INSERT INTO `paperyear` (`id`, `mid`, `maxsel`, `dtstart`, `dtend`, `statu`) VALUES
(1, 1, 0, '2011-09-26', '2011-09-30', 0),
(2, 1, 0, '2011-09-26', '2011-10-15', 0),
(3, 1, 0, '2011-10-10', '2011-10-15', 0),
(4, 2, 0, '2011-10-07', '2011-10-31', 0),
(5, 1, 1, '2011-10-14', '2011-10-21', 0),
(6, 1, 2, '2011-10-20', '2011-10-30', 0),
(7, 1, 2, '2011-10-02', '2011-10-03', 0),
(8, 1, 5, '2011-12-05', '2011-12-10', 0),
(9, 1, 1, '2012-01-02', '2012-01-26', 0),
(10, 1, 1, '2012-01-09', '2012-01-11', 0),
(11, 1, 1, '2012-01-10', '2012-01-12', 1);

-- --------------------------------------------------------

--
-- Table structure for table `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(32) unsigned NOT NULL auto_increment,
  `desp` varchar(300) NOT NULL,
  `key` varchar(300) NOT NULL,
  `statu` int(1) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `key` (`key`(255))
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=30 ;

--
-- Dumping data for table `permission`
--

INSERT INTO `permission` (`id`, `desp`, `key`, `statu`) VALUES
(3, '题库编辑', 'trunk_edit', 1),
(4, '题库添加', 'trunk_add', 1),
(5, '题库删除', 'trunk_delete', 1),
(6, '题库浏览', 'trunk_list', 1),
(7, '用户列表查看', 'user_list', 1),
(8, '用户录入', 'user_add', 1),
(9, ' 编辑用户', 'user_edit', 1),
(10, '删除用户', 'user_delete', 1),
(11, '修改用户权限', 'user_change_permission', 1),
(12, '管理学院', 'manage_colleague', 1),
(13, '用户组查看', 'group_list', 1),
(14, '新增用户组', 'group_add', 1),
(15, ' 编辑用户组', 'group_edit', 1),
(16, '删除用户组', 'group_delete', 1),
(17, '显示公告', 'news_list', 1),
(18, '新增新闻', 'news_add', 1),
(19, ' 编辑新闻', 'news_edit', 1),
(20, '删除新闻', 'news_delete', 1),
(21, '选题管理', 'select_manager', 1),
(22, '新增选题', 'select_add', 1),
(23, '删除选题', 'select_delete', 1),
(24, '编辑选题', 'select_edit', 1),
(25, '进行选题', 'select', 1),
(26, '查看自己选题', 'my_select', 1),
(27, '查看选题结果', 'select_list', 1),
(28, '修改自己资料', 'my_profile', 1),
(29, '管理专业', 'manage_major', 1);

-- --------------------------------------------------------

--
-- Table structure for table `selection`
--

CREATE TABLE IF NOT EXISTS `selection` (
  `id` int(32) unsigned NOT NULL auto_increment,
  `uid` int(32) unsigned NOT NULL,
  `sid` int(32) unsigned NOT NULL,
  `statu` int(1) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `uid` (`uid`),
  KEY `pid` (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=64 ;

--
-- Dumping data for table `selection`
--

INSERT INTO `selection` (`id`, `uid`, `sid`, `statu`) VALUES
(1, 1, 4, 0),
(2, 1, 6, 0),
(3, 1, 5, 0),
(4, 1, 4, 0),
(5, 1, 6, 0),
(6, 1, 5, 0),
(7, 1, 6, 0),
(8, 1, 5, 0),
(9, 1, 5, 0),
(10, 1, 4, 0),
(11, 1, 6, 0),
(12, 2, 5, 0),
(13, 2, 6, 0),
(14, 2, 5, 0),
(15, 2, 6, 0),
(16, 2, 4, 0),
(17, 2, 4, 0),
(18, 2, 5, 0),
(19, 2, 4, 0),
(20, 2, 6, 0),
(21, 2, 5, 0),
(22, 2, 6, 0),
(23, 1, 5, 0),
(24, 1, 5, 0),
(25, 1, 5, 0),
(26, 2, 8, 1),
(27, 2, 7, 0),
(28, 2, 9, 1),
(29, 2, 10, 0),
(30, 2, 13, 0),
(31, 2, 11, 0),
(32, 2, 10, 0),
(33, 2, 12, 0),
(34, 2, 10, 0),
(35, 2, 11, 0),
(36, 2, 12, 0),
(37, 2, 10, 0),
(38, 2, 11, 0),
(39, 2, 10, 0),
(40, 2, 10, 0),
(41, 2, 10, 0),
(42, 2, 15, 0),
(43, 2, 14, 0),
(44, 2, 14, 1),
(45, 2, 19, 1),
(46, 2, 23, 0),
(47, 2, 25, 0),
(48, 2, 22, 0),
(49, 2, 23, 0),
(50, 2, 28, 0),
(51, 2, 28, 1),
(52, 2, 31, 0),
(53, 2, 31, 1),
(54, 2, 34, 0),
(55, 2, 34, 0),
(56, 2, 34, 0),
(57, 2, 34, 0),
(58, 2, 34, 0),
(59, 2, 34, 0),
(60, 2, 34, 0),
(61, 2, 34, 0),
(62, 2, 34, 0),
(63, 2, 34, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(32) unsigned NOT NULL auto_increment,
  `gid` int(1) NOT NULL COMMENT '用户组',
  `sid` char(32) NOT NULL COMMENT '学号或者工号',
  `mid` int(5) NOT NULL,
  `permissionset` text NOT NULL COMMENT '附加的权限设置',
  `username` char(16) NOT NULL,
  `password` char(32) NOT NULL,
  `type` int(1) unsigned NOT NULL,
  `statu` int(1) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `sid` (`sid`),
  KEY `username` (`username`),
  KEY `mid` (`mid`),
  KEY `gid` (`gid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `gid`, `sid`, `mid`, `permissionset`, `username`, `password`, `type`, `statu`) VALUES
(1, 99, '1', 1, '', 'admin', '7488e331b8b64e5794da3fa4eb10ad5d', 3, 1),
(2, 1, '123', 1, '', 'user', '80ec08504af83331911f5882349af59d', 0, 1),
(3, 2, '1234', 1, '', 'teacher', 'be0e0f05ccad9a5de677a36061e0c789', 3, 1),
(5, 3, '123456', 1, '', 'major', '2519e8e717bcc1cd5e8fa83e63f3d53d', 5, 1),
(7, 3, '123123123', 1, '', '22222', '4297f44b13955235245b2497399d7a93', 1, 0),
(8, 4, '2704', 1, '', 'ouou', '090bf277dbfbfc3ac7dbc9c72803605b', 4, 0),
(9, 1, '1234567', 1, '', '23qwe', 'b2ca678b4c936f905fb82f2733f5297f', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `userfields`
--

CREATE TABLE IF NOT EXISTS `userfields` (
  `id` int(32) unsigned NOT NULL auto_increment,
  `uid` int(32) NOT NULL,
  `name` varchar(32) default NULL,
  `text` text COMMENT '用户自我简介',
  `maxpapers` int(32) NOT NULL default '0',
  `tel` varchar(32) default NULL,
  `email` varchar(64) default NULL,
  `regtime` datetime NOT NULL,
  `regip` varchar(32) NOT NULL,
  `lastlogintime` datetime NOT NULL,
  `lastloginip` varchar(32) NOT NULL,
  `type` int(1) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `userfields`
--

INSERT INTO `userfields` (`id`, `uid`, `name`, `text`, `maxpapers`, `tel`, `email`, `regtime`, `regip`, `lastlogintime`, `lastloginip`, `type`) VALUES
(1, 1, 'admin', 'wa', 1, '15902817267', 'baizhongwei@163.com', '2011-09-15 15:00:34', '127.0.0.1', '2012-01-11 18:02:42', '127.0.0.1', 0),
(2, 2, 'user', '懂啊', 0, '', 'baizhongwei@163.com', '2011-09-20 09:09:06', '127.0.0.1', '2012-01-11 18:21:43', '127.0.0.1', 0),
(3, 3, 'teacher', '老师', 3, '028-2137812', '123123@asd.com', '2011-09-20 15:55:33', '127.0.0.1', '2012-01-11 18:01:17', '127.0.0.1', 0),
(4, 5, 'major', '<font class="Apple-style-span" face="Arial, Helvetica, sans-serif" size="2"><span class="Apple-style-span" style="line-height: 19px;">专业管理员</span></font>', 1, '', '', '2011-10-04 15:04:51', '127.0.0.1', '2011-10-08 09:16:04', '127.0.0.1', 0),
(5, 6, '', '123123', 0, '', '', '2012-01-10 13:31:23', '127.0.0.1', '2012-01-10 13:31:23', '127.0.0.1', 0),
(6, 7, '', '123123', 3, '', '', '2012-01-10 13:57:15', '127.0.0.1', '2012-01-10 13:57:15', '127.0.0.1', 0),
(7, 8, 'ouou', '', 12, '', '', '2012-01-11 15:43:32', '127.0.0.1', '2012-01-11 15:43:32', '127.0.0.1', 0),
(8, 9, '23qwe', '', 0, '', '', '2012-01-11 18:32:43', '127.0.0.1', '2012-01-11 18:32:43', '127.0.0.1', 0);

-- --------------------------------------------------------

--
-- Table structure for table `usergroup`
--

CREATE TABLE IF NOT EXISTS `usergroup` (
  `id` int(32) unsigned NOT NULL auto_increment,
  `permissionset` text NOT NULL,
  `overgid` varchar(50) NOT NULL COMMENT '允许直接管理的组',
  `desp` varchar(300) NOT NULL,
  `home` varchar(50) NOT NULL COMMENT '登录后跳转的页面',
  `statu` int(1) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=100 ;

--
-- Dumping data for table `usergroup`
--

INSERT INTO `usergroup` (`id`, `permissionset`, `overgid`, `desp`, `home`, `statu`) VALUES
(1, '25,17,26,28,', '0', '同学', '/student.do', 1),
(2, '6,3,5,4,21,27,24,23,22,17,28,', '0', '教师', '/teacher.do', 1),
(3, '7,9,10,8,6,3,5,4,21,27,24,23,22,17,19,20,18,28,29,', '', '专业管理员', '/colleague.do', 1),
(4, '7,9,10,8,6,3,5,4,21,27,24,23,22,17,19,20,18,28,12,', '', '学院管理员', '/colleague.do', 1),
(99, '*', '0', '管理员', '/admin.do', 1);
