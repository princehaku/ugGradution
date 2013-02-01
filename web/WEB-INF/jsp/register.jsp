<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : register
    Created on : 2011-8-29, 9:07:45
    Author     : princehaku
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户注册</title>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/register.css" rel="stylesheet" type="text/css" />
<link href="themes/blue/styles.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" ></script>
<script language="javascript" src="<%=request.getContextPath() %>/js/common.js"  ></script>
<script language="javascript" src="<%=request.getContextPath() %>/js/check.js"  ></script>
</head>
<body onload="fetchMajor();">
	<div id="registercontainer">
    	<div id="registerbox">

        	<div id="registerheader">
            	&nbsp;
            </div>
            <div id="innerregister">
                <form id="registerform" onsubmit="return checkuserreg();" action="<%=request.getContextPath() %>/register.do?action=submit" method="POST">
                     <p>请输入您的学号:</p>
                     <input name="sid" id="sid" class="registerinput" value="${sid}"/>
                    <p>请指定您的密码:</p>
                	<input type="password" name="password1" id="password1" class="registerinput"  value="${password1}"/>
                     <p>请再输入一次您的密码:</p>
                	<input  type="password" name="password2" id="password2" class="registerinput"/>
                     <p>请输入您的邮箱:</p>
                	<input name="email"  id="email" class="registerinput"  value="${email}"/><br />
                     <p>学院:
                         <select name="col" onchange="fetchMajor();" id="col" style="width:150px;">
                             <option value="0">请选择学院</option>
                             <c:forEach var="vow" items="${colleague}">
                                 <option value="<c:out value="${vow.id}"/>">
                                     <c:out value="${vow.name}"/></option>
                             </c:forEach>
                         </select><br /><br /><div id='majordiv' style="color:#DDDDDD;text-shadow: 1px 1px 1px #222222;">  专业:<select name="major" id="major" style="width:150px;">
                            <option value="0">请选择专业</option></select></div>
                     </p>
                        <p>&nbsp;</p>
                        <p>
                	<div id="errorMsg" class="errorbox">
                            <c:out value="${msg}" />
                        </div>
                        </p>
                        <p>&nbsp;</p>
                        <input type="submit" class="registerbtn" value="注册" /><br />
                </form>
            </div>
        </div>
    </div>

</body>
</html>