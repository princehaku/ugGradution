<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>我的选题</h2>
            </div>
            <div class="contentbox">
                
            	<table width="100%">
                	<thead>
                    	<tr>
                            <th>题目</th>
                            <th>可选人数/人数上限</th>
                            <c:if test="${isEnd eq 1}">
                            <th>指导老师</th>
                            </c:if>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="vo" items="${papers}">
                    	<tr class="alt">
                            <td><a onclick="viewSubject(<c:out value="${vo.paperinfo.paper.id}"/>)" href="javascript:void(0);"><c:out value="${vo.paperinfo.paper.title}"/><a></td>
                            <td><c:out value="${vo.papersel.leftsolts}"/>/<c:out value="${vo.papersel.maxslots}"/>
                            <c:if test="${isEnd eq 1}">
                            <td><a onclick="viewUser(<c:out value="${vo.paperinfo.user.uid}"/>)" href="javascript:void(0);"><c:out escapeXml="false" value="${vo.paperinfo.user.name}"/></a></td>
                            </c:if>
                            <td id="img${vo.papersel.id}">
                                <input type="button" onclick="deselectThis(${vo.papersel.id});" class="btn" value="取消" />
                            </td>
                            </tr>
                      <input type="hidden" name="id[]" value="<c:out value="${vo.papersel.id}"/>" />
                    </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                	<ul>
                    </ul>
                    <div class="bulkactions" id="bulkaction">
                    </div>
                </div>
                <c:out value="${pager}" escapeXml="false"/>
                <div style="clear: both;"></div>
            </div>
            
        </div>