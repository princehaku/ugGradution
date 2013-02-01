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
                <h2>题库查看</h2>
            </div>
            <div class="contentbox">
                <h3><c:out value="${pt.paper.title}" /></h3><br />
                <p>标签 :<br /><c:out value="${pt.paper.tags}" escapeXml="false"/></p>
                <p style="scroll-y:auto;height:250px;">描述 :<br /><c:out value="${pt.paper.desp}" escapeXml="false"/></p>
                <p>标签 :<br /><c:out value="${pt.paper.tags}" escapeXml="false"/></p>
                <p>发布时间 :<br /><c:out value="${pt.paper.addtime}" escapeXml="false"/></p>
                <div style="clear: both;"></div>
            </div>
            
        </div>
</body>
</html>