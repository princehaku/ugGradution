<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>用户导入</h2>
    </div>
    <div class="contentbox">
        <form action="<%=request.getContextPath()%>/user.do?c=import" enctype="multipart/form-data"  onsubmit="return checkuserimport();"  method="post">
            
            <p>学院:
                <select name="col" onchange="fetchMajor();" id="col" style="width:150px;">
                    <option value="0">请选择学院</option>
                    <c:forEach var="vow" items="${colleague}">
                        <option value="<c:out value="${vow.id}"/>">
                            <c:out value="${vow.name}"/></option>
                        </c:forEach>
                </select><br /><br /><div id='majordiv' style="">专业:</label>
                <select name="major" id="major" style="width:150px;">
                    <option value="0">请选择专业</option></select></div>
            </p>
            <p>
                <label><strong>用户组:</strong></label>
                <select name="group" id="group" style="width:150px;">
                    <option value="0">请选择用户组</option>
                    <c:forEach var="vow" items="${usergroup}">
                        <option value="<c:out value="${vow.id}"/>">
                            <c:out value="${vow.desp}"/></option>
                        </c:forEach>
                </select>
                <input type="button" onclick="setPermission(<c:out default="0" value="${uf.user.id}" />,$('#permissionset').val())" class="btn" value="设置附加权限">
                <input type="hidden" id="permissionset" name="permissionset" value="${uf.user.permissionset}" />
                <span class="smltxt" style="color: red">(附加权限将和用户组的权限同时生效)</span>
            </p> 
            <p>
                <label><strong>初始密码:</strong></label>
                <input type="type" id="password" name="password" />
                <span class="smltxt">(所有导入的用户的默认密码)</span>
            </p>
            <p>
                <label><strong>上传文件:</strong></label>
                <input type="file" name="file" id="file" value="<c:out value="${uf.user.username}" />" class="inputbox" /> <br />
                <span class="smltxt">(请务必在Excel模板<span><a style="color:green" href="<%=request.getContextPath()%>/files/user_template.xls"><b> (点击下载) </b></a></span>的基础上修改)</span>
            </p> 
            <p>
                <span class="smltxt">一次只能按一个专业导入,如果需要一次导入多个专业,请分开导入</span>
            </p> 
            <input type="submit" class="btn" value="开始导入">
        </form>         
    </div>
</div>
<script>
    fetchMajor();
    function setPermissionText(id,text){
        $.facebox.close();
        $("#permissionset").val(text);
    }
</script>