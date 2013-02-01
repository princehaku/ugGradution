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
                            <th>描述</th>
                            <th>指导老师</th>
                            <th>操作</th>
                            <th><input type="checkbox" id="checkboxall" value="" name=""></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${trunks}">
                    	<tr class="alt">
                            <td><a onclick="viewSubject(<c:out value="${vo.paper.id}"/>)" href="javascript:void(0);"><c:out value="${vo.paper.title}"/><a></td>
                            <td><c:out escapeXml="false" value="${vo.paper.shortdesp}"/></td>
                            <td><c:out value="${vo.user.name}"/></td>
                                <td>
                                    <a title="" href="?c=trunkedit&id=<c:out value="${vo.paper.id}"/>"><img alt="Edit" src="img/icons/icon_edit.png"></a>
                                    <a onclick="if(confirm('确定删除?')){window.location.href='trunk.do?c=delete&id=<c:out value="${vo.paper.id}"/>'}" href="javascript:void(null);"><img alt="Unapprove" src="img/icons/icon_unapprove.png"></a>
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
                            <li><img alt="Edit" src="img/icons/icon_edit.png"> 编辑</li>
                            <li><img alt="Delete" src="img/icons/icon_delete.png"> 删除</li>
                    </ul>
                    <div class="bulkactions">
                    	<select id="bulkaction">
                        	<option value="0">批量操作</option>
                        	<option value="1">删除</option>
                        </select>
                        <input type="submit" class="btn" value="确认">
                    </div>
                </div>
                </form>
                <c:out value="${pager}" escapeXml="false"/>
                <div style="clear: both;"></div>
            </div>
            
        </div>