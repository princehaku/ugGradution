<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
            <div class="headings altheading">
                <h2>选题详情</h2>
            </div>
            <div class="contentbox">
                <!--<div style="color:black;">
                    <form action="<%=request.getContextPath() %>/paper.do?c=autofill" method="post">自动填充每个题上限    
                    <input type="hidden" value="0" name="utype[]"/>助教
                    <input style="width:30px"  value="1" maxlength="3" name="utype[]"/>
                    讲师<input style="width:30px" value="1" maxlength="3" name="utype[]"/>
                    副教授<input style="width:30px" value="1" maxlength="3" name="utype[]"/>
                    教授<input style="width:30px" value="1" maxlength="3" name="utype[]"/>
                    博导<input style="width:30px" value="1" maxlength="3" name="utype[]"/>
                    <input type="hidden" name="yid" value="<%=request.getParameter("id")%>"/>
                    <input type="submit" class="btn" value="填充" /></form>
                </div>-->
                <form action="<%=request.getContextPath() %>/paper.do?c=deletesel" onsubmit="return checkbulk();"  method="post">
            	<table width="100%">
                	<thead>
                    	<tr>
                            <th>题目</th>
                            <th>指导老师</th>
                            <th>可选人数/人数上限</th>
                            <th>选入学生</th>
                            <th><input type="checkbox" id="checkboxall" value="" name="">全选</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="vo" items="${papers}">
                    	<tr class="alt">
                            <td><a onclick="viewSubject(<c:out value="${vo.paperinfo.paper.id}"/>)" href="javascript:void(0);"><c:out value="${vo.paperinfo.paper.title}"/><a></td>
                            <td><a onclick="viewUser(<c:out value="${vo.paperinfo.user.uid}"/>)" href="javascript:void(0);"><c:out escapeXml="false" value="${vo.paperinfo.user.name}"/></a></td>
                            <td><span id="saveleft<c:out value="${vo.papersel.id}"/>"><c:out value="${vo.papersel.leftsolts}"/></span>/<input id="saveseltext<c:out value="${vo.papersel.id}"/>" style="width:30px" maxlength="3" value="<c:out value="${vo.papersel.maxslots}"/>" name="maxslots[]"/>
                                <span id="savesel<c:out value="${vo.papersel.id}"/>"> <input onclick="saveSel(<c:out value="${vo.papersel.id}"/>)"  type="button" class="btn" value="保存"></span>
                            </td>
                            <td>
                                <c:forEach var="uo" items="${vo.selectedUsers}">
                                    <div>学号:<c:out value="${uo.sid}"/> 姓名:<c:out value="${uo.username}"/> <a onclick="if(confirm('确定撤销该生此项选题?')){unapprove(${vo.papersel.id},${uo.id},this);}" href="javascript:void(null);"><img alt="Unapprove" src="img/icons/icon_unapprove.png">[撤选]</a></div>
                                </c:forEach>
                                    <div>学号:<input name="sid" id="sidappending<c:out value="${vo.papersel.id}"/>" value=""/><input onclick="approve(${vo.papersel.id},this)" type="button" class="btn" value="加入"></div>
                            </td>
                            <td><input type="checkbox"  name="checkall[]" value="<c:out value="${vo.papersel.id}"/>"></td>
                        </tr>
                        <input type="hidden" name="id[]" value="<c:out value="${vo.papersel.id}"/>" />
                        <input type="hidden" name="usertype[]" value="<c:out value="${vo.paperinfo.user.type}"/>" />
                        </c:forEach>
                        <input type="hidden" name="sid" value="<%=request.getParameter("id")%>" />
                    </tbody>
                </table>
                <div class="extrabottom">
                	<ul>
                           <!--
                           <li><img alt="Delete" src="img/icons/icon_delete.png"><input type="submit"  class="btn" value="删除"/></li>
                           -->
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