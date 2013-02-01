<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>毕业设计选题系统</title>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/wysiwyg.css" rel="stylesheet" type="text/css" />
<link href="themes/blue/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="contentcontainer" style="width:750px;">
            <div class="headings">
                <h2>用户查看</h2>
            </div>
            <div class="contentbox">
                <br />
                <p>
                        <label><strong>学号/工号:</strong></label>
                        <c:out value="${uf.user.sid}" />
                    </p>
            	    <p>
                        <label><strong>姓名:</strong></label>
                        <c:out value="${uf.uf.name}" />
                    </p> 
                    <p>
                        <label for="textfield"><strong>个人简介:</strong></label>
                        <p style="scroll-y:auto;height:150px;"><c:out escapeXml="false" value="${uf.uf.text}" /></p>
                    </p>
                <p>手机号码 :<br /><c:out value="${uf.uf.tel}" escapeXml="false"/></p>
                <p>EMAIL :<br /><c:out value="${uf.uf.email}" escapeXml="false"/></p>
                <!--<p>带题上限 :<br /><c:out value="${uf.uf.maxpapers}" escapeXml="false"/></p>-->
                <p>注册时间 :<br /><c:out value="${uf.uf.regtime}" escapeXml="false"/></p>
                <p>最后登录时间 :<br /><c:out value="${uf.uf.lastlogintime}" escapeXml="false"/></p>
                <div style="clear: both;"></div>
            </div>
            
        </div>
</body>
</html>