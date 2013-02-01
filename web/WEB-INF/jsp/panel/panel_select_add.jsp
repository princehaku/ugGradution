<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>添加选题</h2>
            </div>
            <div class="contentbox">
                <form action="<%=request.getContextPath() %>/paper.do?c=savenew"  onsubmit="return checktrunk();"  method="post">
                    <input type="hidden" name="id" value="${id}"> <br>
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
                        <label><strong>选题上限:</strong></label>
                        <input type="text" name="maxsel"  class="inputbox smallbox"><br>
                        <span class="smltxt">每个用户最多选入的数量</span>
                    </p>
            	    <p>
                        <label><strong>开始日期:</strong></label>
                        <input type="text" name="stdate"  class="inputbox smallbox datetime"><br>
                        <span class="smltxt">(格式 xxxx-xx-xx)</span>
                    </p>
                    <p>
                        <label><strong>结束日期:</strong></label>
                        <input type="text" name="eddate" class="inputbox smallbox datetime"><br>
                        <span class="smltxt">(格式 xxxx-xx-xx)</span>
                    </p>
                    <input type="submit" class="btn" value="新建">
                </form>         
            </div>
</div>
<script>
    initMajor(<c:out default="${user.mid}" value="${paper.mid}" />);
</script>