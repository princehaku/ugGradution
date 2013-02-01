<%-- 
    Document   : panel_head
    Created on : Sep 17, 2011, 10:01:15 AM
    Author     : princehaku
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>毕业设计选题系统</title>
<link href="<%=request.getContextPath() %>/styles/layout.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/themes/blue/styles.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/facebox/facebox.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" ></script>
<script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/facebox/facebox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/jquery-ui-timepicker-addon.js"></script>
<script language="javascript" src="<%=request.getContextPath() %>/js/common.js"></script>
<script language="javascript" src="<%=request.getContextPath() %>/js/check.js"></script>
</head>
<body id="homepage">
	<div id="header">
    	<a href="" title=""><img src="img/cp_logo.png" alt="Control Panel" class="logo" /></a>
    	<!--<div id="searcharea">
            <p class="left smltxt"><a href="#" title="">高级搜索</a></p>
            <input type="text" class="searchbox" value="搜索题库,老师...." onclick="if (this.value =='搜索题库,老师....'){this.value=''}"/>
            <input type="submit" value="Search" class="searchbtn" />
        </div>-->
    </div>
        
    <div id="breadcrumb">
    	<ul>	
        	<li><img src="img/icons/icon_breadcrumb.png" alt="Location" /></li>
        	<li><strong>Location:</strong></li>
            <li>选题系统</li>
        </ul>
    </div>