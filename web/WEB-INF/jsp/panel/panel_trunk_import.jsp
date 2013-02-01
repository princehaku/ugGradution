<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>题库导入</h2>
    </div>
    <div class="contentbox">
        <form action="<%=request.getContextPath()%>/trunk.do?c=import" enctype="multipart/form-data"  onsubmit="return checkuserimport();"  method="post">
            
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
                <label><strong>上传文件:</strong></label>
                <input type="file" name="file" id="file" value="<c:out value="${uf.user.username}" />" class="inputbox" /> <br />
                <span class="smltxt">(请务必在Excel模板<span><a style="color:green" href="<%=request.getContextPath()%>/files/trunk_template.xls"><b> (点击下载) </b></a></span>的基础上修改)</span>
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