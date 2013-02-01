-- phpMyAdmin SQL Dump
-- version 3.3.7
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2012 年 02 月 08 日 05:23
-- 服务器版本: 5.5.16
-- PHP 版本: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

SET time_zone = "+00:00";
DROP DATABASE IF EXISTS `uggradution`;
CREATE DATABASE `uggradution`;
GRANT ALL PRIVILEGES ON  `uggradution` . * TO  'uggradution'@'localhost' IDENTIFIED BY 'zxcasdqwe';


use `uggradution`;

--
-- 数据库: `uggradution`
--

-- --------------------------------------------------------

--
-- 表的结构 `colleague`
--

CREATE TABLE IF NOT EXISTS `colleague` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` char(128) NOT NULL,
  `statu` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `colleague`
--

INSERT INTO `colleague` (`id`, `name`, `statu`) VALUES
(1, '信息科学与技术学院', 1);

-- --------------------------------------------------------

--
-- 表的结构 `major`
--

CREATE TABLE IF NOT EXISTS `major` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(5) NOT NULL COMMENT '学院号码',
  `name` char(128) NOT NULL,
  `statu` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- 转存表中的数据 `major`
--

INSERT INTO `major` (`id`, `cid`, `name`, `statu`) VALUES
(1, 1, '计算机科学与技术', 1),
(2, 1, '软件工程', 1),
(3, 1, '电子信息工程', 1),
(4, 1, '通信工程', 1);

-- --------------------------------------------------------

--
-- 表的结构 `news`
--

CREATE TABLE IF NOT EXISTS `news` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(120) NOT NULL COMMENT '标题',
  `text` text NOT NULL,
  `addtime` datetime NOT NULL,
  `authoruid` int(10) unsigned NOT NULL,
  `statu` int(1) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `authoruid` (`authoruid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `news`
--

INSERT INTO `news` (`id`, `title`, `text`, `addtime`, `authoruid`, `statu`) VALUES
(1, '教师使用说明', '<b>一. 题库添加</b><div><b><br></b><div><b><span class="Apple-tab-span" style="white-space:pre">	</span>1）请在选题开始前将需要选的题目添加到题库&nbsp;</b></div><div><b><br></b></div><div><b><span class="Apple-tab-span" style="white-space:pre">	</span>2） 可以使用在线编辑器，或者使用导入功能批量导入题目。如果需要使用导入功能</b></div><div><b>请下载模板文件然后填充对应行列然后再将填写好的文件在上传文件区域上传</b></div><div><b><br></b><div><b>二. 选题</b></div></div><div><b><br></b></div><div><b><span class="Apple-tab-span" style="white-space:pre">	</span>1）选题开始后可以在选题详情内看到所有题的选入情况，结果是随时会变化的</b></div><div><b><br></b></div><div><b><span class="Apple-tab-span" style="white-space:pre">	</span>2）当选题结束后您可以使用导出清单功能导出您的带生清单</b></div><div><b><br></b></div><div><b>三.资料</b></div><div><b><br></b></div><div><b><span class="Apple-tab-span" style="white-space:pre">	</span>1）点击页面左上方的我的资料，可以进行您的资料变更</b></div></div><div><b><br></b></div>', '2012-01-16 21:00:27', 1, 1),
(2, '学生使用说明', '<b>一.选题</b><div><b><br></b><div><b><span style="white-space: pre;" class="Apple-tab-span">	</span>1）在选题开始后您可以在题库里面查看到属于您专业的所有选题</b></div><div><b><br></b></div><div><b><span style="white-space: pre;" class="Apple-tab-span">	</span>2）点击题目后的选入按钮即可选入题目</b></div><div><b><br></b></div><div><b><span style="white-space: pre;" class="Apple-tab-span">	</span>3）如果出现达到您的选题上限。请在我的选题里面撤销已经选入的其他题目再进行选题的变更</b></div><div><div><b><br class="Apple-interchange-newline">二.资料</b></div><div><b><br></b></div><div><b><span style="white-space: pre;" class="Apple-tab-span">	</span>1）点击页面左上方的我的资料，可以进行您的资料变更</b></div></div></div><div><b><br></b></div>', '2012-01-16 21:03:42', 1, 1);

-- --------------------------------------------------------

--
-- 表的结构 `notice`
--

CREATE TABLE IF NOT EXISTS `notice` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(32) unsigned NOT NULL,
  `text` text NOT NULL,
  `time` datetime NOT NULL,
  `statu` int(1) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `notice`
--


-- --------------------------------------------------------

--
-- 表的结构 `papersel`
--

CREATE TABLE IF NOT EXISTS `papersel` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(32) NOT NULL,
  `yid` int(5) NOT NULL,
  `owneruid` int(32) NOT NULL COMMENT '所有者的id',
  `leftsolts` int(4) NOT NULL DEFAULT '-1',
  `maxslots` int(4) NOT NULL DEFAULT '-1',
  `isdel` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `yid` (`yid`),
  KEY `pid` (`pid`),
  KEY `tid` (`owneruid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `papersel`
--


-- --------------------------------------------------------

--
-- 表的结构 `papertrunk`
--

CREATE TABLE IF NOT EXISTS `papertrunk` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `mid` int(5) NOT NULL,
  `uid` int(32) NOT NULL,
  `tags` text NOT NULL,
  `title` varchar(128) NOT NULL,
  `shortdesp` varchar(120) NOT NULL,
  `desp` longtext NOT NULL,
  `addtime` datetime NOT NULL,
  `statu` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mid` (`mid`),
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `papertrunk`
--


-- --------------------------------------------------------

--
-- 表的结构 `paperyear`
--

CREATE TABLE IF NOT EXISTS `paperyear` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `mid` int(5) NOT NULL COMMENT '专业id',
  `maxsel` int(5) NOT NULL COMMENT '每个用户最大选入数量',
  `dtstart` date NOT NULL,
  `dtend` date NOT NULL,
  `statu` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- 转存表中的数据 `paperyear`
--


-- --------------------------------------------------------

--
-- 表的结构 `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `desp` varchar(300) NOT NULL,
  `key` varchar(300) NOT NULL,
  `statu` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `key` (`key`(255))
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=30 ;

--
-- 转存表中的数据 `permission`
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
-- 表的结构 `selection`
--

CREATE TABLE IF NOT EXISTS `selection` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(32) unsigned NOT NULL,
  `sid` int(32) unsigned NOT NULL,
  `statu` int(1) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `pid` (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- 转存表中的数据 `selection`
--

INSERT INTO `selection` (`id`, `uid`, `sid`, `statu`) VALUES
(1, 2, 1, 0),
(2, 2, 11, 0),
(3, 2, 2, 1),
(4, 2, 14, 0),
(5, 2, 1, 0),
(6, 22, 2, 1),
(7, 22, 3, 1),
(8, 2, 14, 1),
(9, 2, 30, 0),
(10, 2, 41, 0);

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `gid` int(1) NOT NULL COMMENT '用户组',
  `sid` char(32) NOT NULL COMMENT '学号或者工号',
  `mid` int(5) NOT NULL,
  `permissionset` text NOT NULL COMMENT '附加的权限设置',
  `username` char(16) NOT NULL,
  `password` char(32) NOT NULL,
  `type` int(1) unsigned NOT NULL,
  `statu` int(1) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sid` (`sid`),
  UNIQUE KEY `username` (`username`),
  KEY `mid` (`mid`),
  KEY `gid` (`gid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=35 ;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `gid`, `sid`, `mid`, `permissionset`, `username`, `password`, `type`, `statu`) VALUES
(1, 99, '1', 1, '', 'admin', '7488e331b8b64e5794da3fa4eb10ad5d', 3, 1),
(2, 1, '123', 1, '', 'user', '80ec08504af83331911f5882349af59d', 0, 1),
(3, 2, '1234', 1, '', 'teacher', 'be0e0f05ccad9a5de677a36061e0c789', 3, 1),
(5, 3, '123456', 1, '', 'major', '2519e8e717bcc1cd5e8fa83e63f3d53d', 5, 1);

-- --------------------------------------------------------

--
-- 表的结构 `userfields`
--

CREATE TABLE IF NOT EXISTS `userfields` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(32) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `text` text COMMENT '用户自我简介',
  `maxpapers` int(32) NOT NULL DEFAULT '0',
  `tel` varchar(32) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `regtime` datetime NOT NULL,
  `regip` varchar(32) NOT NULL,
  `lastlogintime` datetime NOT NULL,
  `lastloginip` varchar(32) NOT NULL,
  `type` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=23 ;

--
-- 转存表中的数据 `userfields`
--

INSERT INTO `userfields` (`id`, `uid`, `name`, `text`, `maxpapers`, `tel`, `email`, `regtime`, `regip`, `lastlogintime`, `lastloginip`, `type`) VALUES
(1, 1, 'admin', 'wa', 1, '15902817267', 'baizhongwei@163.com', '2011-09-15 15:00:34', '127.0.0.1', '2012-02-08 12:45:09', '182.149.62.137', 0),
(2, 2, 'user', '懂啊', 0, '', 'baizhongwei@163.com', '2011-09-20 09:09:06', '127.0.0.1', '2012-02-08 12:43:15', '182.149.62.137', 0),
(3, 3, 'teacher', '老师', 3, '028-2137812', '123123@asd.com', '2011-09-20 15:55:33', '127.0.0.1', '2012-02-07 19:21:04', '210.41.87.39', 0),
(4, 5, 'major', '<font class="Apple-style-span" face="Arial, Helvetica, sans-serif" size="2"><span class="Apple-style-span" style="line-height: 19px;">专业管理员</span></font>', 1, '', '', '2011-10-04 15:04:51', '127.0.0.1', '2011-10-08 09:16:04', '127.0.0.1', 0);

-- --------------------------------------------------------

--
-- 表的结构 `usergroup`
--

CREATE TABLE IF NOT EXISTS `usergroup` (
  `id` int(32) unsigned NOT NULL AUTO_INCREMENT,
  `permissionset` text NOT NULL,
  `overgid` varchar(50) NOT NULL COMMENT '允许直接管理的组',
  `desp` varchar(300) NOT NULL,
  `home` varchar(50) NOT NULL COMMENT '登录后跳转的页面',
  `statu` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=100 ;

--
-- 转存表中的数据 `usergroup`
--

INSERT INTO `usergroup` (`id`, `permissionset`, `overgid`, `desp`, `home`, `statu`) VALUES
(1, '25,17,26,28,', '0', '同学', '/student.do', 1),
(2, '6,3,5,4,21,27,24,23,22,17,28,', '0', '教师', '/teacher.do', 1),
(3, '7,9,10,8,6,3,5,4,21,27,24,23,22,17,19,20,18,28,29,', '', '专业管理员', '/colleague.do', 1),
(4, '7,9,10,8,6,3,5,4,21,27,24,23,22,17,19,20,18,28,12,', '', '学院管理员', '/colleague.do', 1),
(99, '*', '0', '管理员', '/admin.do', 1);
