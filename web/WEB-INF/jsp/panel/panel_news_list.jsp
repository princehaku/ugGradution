<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
        <!-- Content Box Start -->
        <div class="contentcontainer">
            <div class="headings altheading">
                <h2>系统公告</h2>
            </div>
            <div class="contentbox">
                <c:forEach var="vo" items="${news}">
                <p style="margin-top: 5px;"><span style="margin-right: 20px;color:green">${vo.addtime}</span><a href="javascript:void(0);" onclick="viewNews(${vo.id});">${vo.title}</a></p>
                </c:forEach>
                <div style="clear: both;"></div>
            </div>
        </div>
        <!-- Content Box End -->