<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>用户管理</h2>
            </div>
            <div class="contentbox">
                <form action="<%=request.getContextPath() %>/user.do?c=delete" onsubmit="return checkbulk();"  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                            <th>学号/工号</th>
                            <th>用户名</th>
                            <th>操作</th>
                            <th><input type="checkbox" id="checkboxall" value="" name="">全选</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${users}">
                    	<tr class="alt">
                            <td><a href="?c=useredit&id=<c:out value="${vo.user.id}"/>"><c:out value="${vo.user.sid}"/><a></td>
                            <td><c:out value="${vo.uf.name}"/></td>
                            <td>
                                    <a title="" href="?c=useredit&id=<c:out value="${vo.user.id}"/>"><img alt="Edit" src="img/icons/icon_edit.png">编辑</a>
                            </td>
                            <td><input type="checkbox" name="checkall[]" value="<c:out value="${vo.user.id}"/>"></td>
                        </tr>
                        <input type="hidden" name="id[]" value="<c:out value="${vo.user.id}"/>" />
                        </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                     <ul>
                            <li><a href="?c=useredit"><img border="0" alt="Add" src="img/icons/icon_add.png"> 添加</a></li>
                            <li><a href="?c=userimport"><img border="0" alt="Add" src="img/icons/icon_add.png"> 导入EXCEL</a></li>
                            <li><a href="<%=request.getContextPath() %>/user.do?c=userexport"><img border="0" alt="Add" src="img/icons/icon_add.png"> 导出EXCEL</a></li>
                    </ul>
                    <div class="bulkactions">
                        <input type="submit" class="btn" value="删除">
                    </div>
                </div>
                </form>
                <c:out value="${pager}" escapeXml="false"/>
                <div style="clear: both;"></div>
            </div>
            
        </div>