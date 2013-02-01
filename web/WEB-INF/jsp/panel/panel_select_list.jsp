<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>选题列表</h2>
            </div>
            <div class="contentbox">
                <form action="<%=request.getContextPath() %>/paper.do?c=deleteyear" onsubmit="return checkbulk();"  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                            <th>ID</th>
                            <th>专业</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>选题上限</th>
                            <th>状态</th>
                            <th>操作</th>
                            <th><input type="checkbox" id="checkboxall" value="" name="">全选</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${paperyears}">
                    	<tr class="alt">
                            <td><a href="?c=selectdetail&id=<c:out value="${vo.paperyear.id}"/>"><c:out value="${vo.paperyear.id}"/></a></td>
                            <td><a href="?c=selectdetail&id=<c:out value="${vo.paperyear.id}"/>"><c:out escapeXml="false" value="${vo.major.name}"/></a></td>
                            <td><c:out value="${vo.paperyear.dtstart}"/></td>
                            <td><c:out value="${vo.paperyear.dtend}"/></td>
                            <td><c:out value="${vo.paperyear.maxsel}"/></td>
                             <td>
                                 状态
                                 <select onchange="change_paperyear_status(<c:out value="${vo.paperyear.id}"/>,this.value)">
                                     <option value="1" <c:if test="${vo.paperyear.statu == 1}">selected="selected"</c:if>>
                                        进行中
                                    </option>
                                    <option value="2"  <c:if test="${vo.paperyear.statu ==2 }">selected="selected"</c:if>>
                                        选题结束
                                    </option>
                                 </select>
                            </td>
                            <td>
                               <a title="" href="?c=selectedit&id=<c:out value="${vo.paperyear.id}"/>"><img alt="Edit" src="img/icons/icon_edit.png">编辑</a>
                            </td>
                            <td><input type="checkbox" name="checkall[]" value="<c:out value="${vo.paperyear.id}"/>"></td>
                        </tr>
                        <input type="hidden" name="id[]" value="<c:out value="${vo.paperyear.id}"/>" />
                        </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                    <ul>
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