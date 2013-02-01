<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>题库管理</h2>
            </div>
            <div class="contentbox">
                <form action="<%=request.getContextPath() %>/trunk.do?c=delete" onsubmit="return checkbulk();"  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                            <th>题目</th>
                            <th>指导老师</th>
                            <th>操作</th>
                            <th><input type="checkbox" id="checkboxall" value="" name="">全选</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${trunks}">
                    	<tr class="alt">
                            <td><a href="?c=trunkedit&id=<c:out value="${vo.paper.id}"/>"><c:out value="${vo.paper.title}"/><a></td>
                            <td><c:out value="${vo.user.name}"/></td>
                                <td>
                                    <a title="" href="?c=trunkedit&id=<c:out value="${vo.paper.id}"/>"><img alt="Edit" src="img/icons/icon_edit.png">编辑</a>
                            </td>
                            <td><input type="checkbox" name="checkall[]" value="<c:out value="${vo.paper.id}"/>"></td>
                        </tr>
                        <input type="hidden" name="id[]" value="<c:out value="${vo.paper.id}"/>" />
                        </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                	<ul>
                            <li><a href="?c=trunkedit"><img border="0" alt="Add" src="img/icons/icon_add.png"> 添加</a></li>
                            <li><a href="?c=trunkimport"><img border="0" alt="Add" src="img/icons/icon_add.png"> 导入EXCEL</a></li>
                            <li><a href="trunk.do?c=trunkexport"><img border="0" alt="Add" src="img/icons/icon_add.png"> 导出清单</a></li>
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