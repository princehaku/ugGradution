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
<title>系统信息</title>
<link href="styles/layout.css" rel="stylesheet" type="text/css" />
<link href="styles/register.css" rel="stylesheet" type="text/css" />
<link href="themes/blue/styles.css" rel="stylesheet" type="text/css" />
<meta http-equiv='refresh' content="<c:out value="${time}"/>;url='<c:out value="${url}"/>'" />
</head>
<body>
	<div id="registercontainer">
    	<div id="registerbox">

        	<div id="registerheader">
            	&nbsp;
            </div>
            <div id="innerregister"  style="size:18px;color:#DDDDDD;text-shadow: 1px 1px 1px #222222;">
                <c:out escapeXml="false" value="${msg}"/>
            </div>
        </div>
    </div>

</body>
</html>