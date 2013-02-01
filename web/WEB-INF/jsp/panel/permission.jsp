<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>毕业设计选题系统</title>
        <link href="styles/layout.css" rel="stylesheet" type="text/css" />
        <link href="styles/wysiwyg.css" rel="stylesheet" type="text/css" />
        <link href="themes/blue/styles.css" rel="stylesheet" type="text/css" />
        <script>
            function insertToParent(){
                text="";
                $(".contentbox span input").each(function(){
                    if(this.checked=="checked"||this.checked==true){
                        text=text+this.value+",";
                    }
                });
                parent.window.setPermissionText(<c:out default="0" value="${pageid}" />,text);
            }
            function checkDefault(){
                params='<c:out default="" value="${permissionset}" />';
                params=params.split(",");
                $(".contentbox span input").each(function(i){
                    if(in_array(this.value,params)){
                       this.checked="checked";
                    }
                });
            }
        </script>
    </head>
    <body>
        <div class="contentcontainer" style="width:750px;">
            <div class="headings">
                <h2>权限</h2>
            </div>
            <div class="contentbox">
                <c:forEach var="vow" items="${permission}">
                    <span style="widht:200px;float:left;display:block">
                        <label for="per${vow.permission.id}" ><input type="checkbox" value="<c:out value="${vow.permission.id}"/>" id="per${vow.permission.id}" />
                        <c:out value="${vow.permission.desp}"/></label>
                    </span>
                </c:forEach>
                <br clear="both" /><br />
                <input type="button" onclick="insertToParent()" style="float:right;margin-right: 20px;" class="btn" value="确定" />
                <br clear="both" /><br />
            </div>

        </div>
    </body>
        <script>checkDefault();</script>
</html>