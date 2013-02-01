<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>修改选题</h2>
            </div>
            <div class="contentbox">
                <form action="<%=request.getContextPath() %>/paper.do?c=save"  onsubmit="return checktrunk();"  method="post">
                    <input type="hidden" name="id" value="${id}"> <br>
            	    <p>
                        <label><strong>选题上限:</strong></label>
                        <input type="text" name="maxsel" value="${py.maxsel}"  class="inputbox smallbox"><br>
                        <span class="smltxt">每个用户最多选入的数量</span>
                    </p>
            	    <p>
                        <label><strong>开始日期:</strong></label>
                        <input type="text" name="stdate" value="${py.dtstart}"  class="inputbox smallbox datetime"><br>
                        <span class="smltxt">(格式 xxxx-xx-xx)</span>
                    </p>
                    <p>
                        <label><strong>结束日期:</strong></label>
                        <input type="text" name="eddate" value="${py.dtend}" class="inputbox smallbox datetime"><br>
                        <span class="smltxt">(格式 xxxx-xx-xx)</span>
                    </p>
                    <input type="submit" class="btn" value="保存">
                </form>         
            </div>
</div>
<script>
    initMajor(<c:out default="${user.mid}" value="${paper.mid}" />);
</script>