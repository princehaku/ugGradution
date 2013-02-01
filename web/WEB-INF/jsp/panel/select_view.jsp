<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>毕业设计选题系统</title>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="themes/blue/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="contentcontainer" style="width:750px;">
            <div class="headings">
                <h2>选入的用户</h2>
            </div>
            <div class="contentbox">
                <table width="100%">
                	<thead>
                    	<tr>
                            <th>用户名</th>
                            <th>姓名</th>
                            <th>学号</th>
                        </tr>
                    </thead>
                    <tr>
                        <c:forEach var="vo" items="${users}">
                        <td><c:out value="${vo.user.username}"/></td>
                        <td><c:out value="${vo.uf.name}"/></td>
                        <td><c:out value="${vo.user.sid}"/></td>
                        </c:forEach>
                    </tr>
                </table>
                <div style="clear: both;"></div>
            </div>
            
        </div>
</body>
</html>