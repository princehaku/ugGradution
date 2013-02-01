<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>选题详情</h2>
            </div>
            <div class="contentbox">
                <div>选题开始后这里可以看到属于您的题目</div>
                <div>&nbsp;</div>
                <form action="#"  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                            <th>题目</th>
                            <th>&nbsp;</th>
                            <th>可选人数/人数上限</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${papers}">
                    	<tr class="alt">
                            <td><a onclick="viewSubject(<c:out value="${vo.paperinfo.paper.id}"/>)" href="javascript:void(0);"><c:out value="${vo.paperinfo.paper.title}"/></a></td>
                            <td>&nbsp;</td>
                            <td><c:out value="${vo.papersel.leftsolts}"/>/
                                <c:out value="${vo.papersel.maxslots}"/>
                            </td>
                            <td>
                                <input onclick="viewSelection(<c:out value="${vo.papersel.id}"/>)" type="button" class="btn" value="查看选入情况" />
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                </div>
                </form>
                <c:out value="${pager}" escapeXml="false"/>
                <div style="clear: both;"></div>
                注意 : 在选题未结束前以上列表会即时变化
            </div>
            
        </div>