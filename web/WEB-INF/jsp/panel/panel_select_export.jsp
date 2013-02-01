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
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${paperyears}">
                    	<tr class="alt">
                            <td><c:out value="${vo.paperyear.id}"/></td>
                            <td><c:out escapeXml="false" value="${vo.major.name}"/></td>
                            <td><c:out value="${vo.paperyear.dtstart}"/></td>
                            <td><c:out value="${vo.paperyear.dtend}"/></td>
                             <td>
                                 <input type="button" class="btn" onclick="export_selection(${vo.paperyear.statu},${vo.paperyear.id});" value="导出"/>
                            </td>
                        </tr>
                        <input type="hidden" name="id[]" value="<c:out value="${vo.paperyear.id}"/>" />
                        </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                    <ul>
                    </ul>
                    <div class="bulkactions">
                    </div>
                </div>
                </form>
                <c:out value="${pager}" escapeXml="false"/>
                <div style="clear: both;"></div>
            </div>
            
        </div>