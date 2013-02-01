<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>资料管理</h2>
    </div>
    <div class="contentbox">
        <form action="<%=request.getContextPath()%>/user.do?c=save"  onsubmit="return checkuseradd();"  method="post">
            <input type="hidden" name="id" value="${id}"> <br>
            <p>
                <label><strong>学号/工号:</strong></label>
                <input type="text" id="sid" name="sid" value="<c:out value="${uf.user.sid}" />" class="inputbox" /> <br />
                <span class="smltxt">(学号 或者 工号)</span>
            </p>
            <p>
                <label><strong>用户名:</strong></label>
                <input type="text" name="username" value="<c:out value="${uf.user.username}" />" class="inputbox" /> <br />
            </p> 
            <p>
                <label for="textfield"><strong>个人简介:</strong></label>
                <textarea cols="75" rows="10" name="desp" id="wysiwyg" class="text-input textarea"><c:out value="${uf.uf.text}" /></textarea>
            </p>
            <p>
                <label><strong>密码:</strong></label>
                <input type="text" name="password" id="password" value="" class="inputbox"> <br>
            </p>
            <!--<p>
                <label><strong>姓名:</strong></label>
                <input type="text" name="nickname" value="<c:out value="${uf.uf.name}" />" class="inputbox"> <br>
            </p> -->
            <p>
                <label><strong>EMAIL:</strong></label>
                <input type="text" name="email" value="<c:out value="${uf.uf.email}" />" class="inputbox"> <br>
            </p> 
            <p>
                <label><strong>电话号码:</strong></label>
                <input type="text" name="phone" value="<c:out value="${uf.uf.tel}" />" class="inputbox"> <br>
            </p> 
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
                <label><strong>用户类型:</strong></label>
                <select id="usertype" name="type" style="width:150px;">
                    <option value="0">无特殊类型</option>
                    <option value="1">助教</option>
                    <option value="2">讲师</option>
                    <option value="3">副教授</option>
                    <option value="4">教授</option>
                    <option value="5">博士生导师</option>
                </select>
                <span class="smltxt">(有职称的老师可以在这里定义)</span>
            </p>
            <p>
                <label><strong>带题上限:</strong></label>
                <input type="text" id="maxPapers"  name="maxpapers" value="<c:out default="0" value="${uf.uf.maxpapers}" />" class="inputbox"> <br>
                <span class="smltxt" style="color: red">(当选题量超过此上限后，您的其他题目均不可再被学生选入)</span>
            </p>
            <input type="submit" class="btn" value="保存">
        </form>         
    </div>
</div>
<script>
    initMajor(<c:out value="${uf.user.mid}" />);
    initopition($("#group"),<c:out default="0" value="${uf.user.gid}" />);
    initopition($("#usertype"),<c:out default="0" value="${uf.user.type}" />);
    function setPermissionText(id,text){
        $.facebox.close();
        $("#permissionset").val(text);
    }
</script>