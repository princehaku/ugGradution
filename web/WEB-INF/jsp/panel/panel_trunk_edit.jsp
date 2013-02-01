<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>题目管理</h2>
            </div>
            <div class="contentbox">
                <form action="<%=request.getContextPath() %>/trunk.do?c=save"  onsubmit="return checktrunk();"  method="post">
                        <input type="hidden" name="id" value="${id}"> <br>
            	    <p>
                        <label for="textfield"><strong>课题名称:</strong></label>
                        <input type="text" name="title" value="<c:out value="${paper.title}" />" class="inputbox" id="textfield"> <br>
                        <span class="smltxt">(比如:基于服务的数据集成共享平台)</span>
                    </p>
                    <p>学院:
                         <select name="col" onchange="fetchMajor();" id="col" style="width:150px;">
                             <option value="0">请选择学院</option>
                             <c:forEach var="vow" items="${colleague}">
                                 <option value="<c:out value="${vow.id}"/>">
                                     <c:out value="${vow.name}"/></option>
                             </c:forEach>
                         </select><br /><br /><div id='majordiv' style="">  专业:<select name="major" id="major" style="width:150px;">
                            <option value="0">请选择专业</option></select></div>
                     </p>
            	    <p>
                        <label for="textfield"><strong>课题描述:</strong></label>
                       <textarea cols="75" rows="10" name="desp" id="wysiwyg" class="text-input textarea"><c:out value="${paper.desp}" /></textarea>
                    </p>
                    <p>
                        <label for="smallbox"><strong>标签:</strong></label>
                        <input type="text" name="tags"  value="<c:out value="${paper.tags}" />" class="inputbox smallbox" id="smallbox"><br>
                        <span class="smltxt">(请用逗号分隔  比如:java,移动设备)</span>
                    </p>
                    <p>
                    <!--附件:
                    <input type="file" name="file" id="uploader"> -->
                    </p>
                    <input type="submit" class="btn" value="保存">
                </form>         
            </div>
</div>
<script>
    initMajor(<c:out default="${user.mid}" value="${paper.mid}" />);
</script>