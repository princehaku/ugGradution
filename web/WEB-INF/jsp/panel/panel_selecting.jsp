<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>题库</h2>
            </div>
            <div class="contentbox">
                
            	<table width="100%">
                	<thead>
                    	<tr>
                            <th>题目</th>
                            <th>可选人数/人数上限</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="vo" items="${papers}">
                    	<tr class="alt">
                            <td><a onclick="viewSubject(<c:out value="${vo.paperinfo.paper.id}"/>)" href="javascript:void(0);"><c:out value="${vo.paperinfo.paper.title}"/><a></td>
                            <td><c:out value="${vo.papersel.leftsolts}"/>/<c:out value="${vo.papersel.maxslots}"/>
                            <td id="img${vo.papersel.id}">
                            <c:if test="${vo.selected ==1 }">
                                <img alt="Approve" src="img/icons/icon_approve.png"/>
                                </c:if>
                            <c:if test="${vo.selected ==0 }">
                                <input type="button" onclick="selectThis(${vo.papersel.id});" class="btn" value="选入" />
                                </c:if>
                            </td>
                            </tr>
                      <input type="hidden" name="id[]" value="<c:out value="${vo.papersel.id}"/>" />
                    </c:forEach>
                    </tbody>
                </table>
                <div class="extrabottom">
                    <!--<ul>
                        <li><img alt="Approve" src="img/icons/icon_approve.png">  已经选入</li>
                    </ul>-->
                </div>
                <c:out value="${pager}" escapeXml="false"/>
                <div style="clear: both;"></div>
            </div>
            
        </div>