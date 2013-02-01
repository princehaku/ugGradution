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
<title>毕业设计选题系统</title>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/register.css" rel="stylesheet" type="text/css" />
<link href="themes/blue/styles.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js" ></script>
<script language="javascript" src="<%=request.getContextPath() %>/js/common.js"  ></script>
</head>
<body>
	<div id="registercontainer">
    	<div id="registerbox">

            <div id="registerheader">
            	&nbsp;
            </div>
            <div id="innerregister">
                <form id="registerform" action="<%=request.getContextPath() %>/login.do?action=submit" method="POST">
                     <p>用户名:</p>
                     <input name="username" id="sid" class="registerinput" value="${sid}"/>
                     <p>密码:</p>
                	<input type="password" name="password" id="password" class="registerinput"  value="${password1}"/>
                       <p>&nbsp;</p>
                        <input type="submit" class="registerbtn" value="登录" /><br />
                </form>
            </div>
        </div>
    </div>

</body>
</html>