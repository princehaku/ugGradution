<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="panel_head.jsp" %>
     
    <!-- Right Side/Main Content Start -->
    <div id="rightside">
        
        <div id="noticesuccess" class="status success" style="display:none">
            <p class="closestatus"><a href="javascript:void(0)" onclick="$('#noticesuccess').hide();" title="Close">x</a></p>
        	<p class="content" style="color: green"><img src="img/icons/icon_success.png" alt="Success"><span>Success!</span></p>
        </div>
        
        <div id="noticeerror" class="status error" style="display:none">
        	<p class="closestatus"><a href="javascript:void(0)" onclick="$('#noticeerror').hide();" title="Close">x</a></p>
        	<p class="content" style="color: red"><img src="img/icons/icon_error.png" alt="Error"><span>Error!</span></p>
        </div>
        <% 
            String relativeUrlPath="panel_"+(String)request.getAttribute("module")+".jsp";
            pageContext.include(relativeUrlPath); 
        %>
        <div id="footer">
            &copy; Copyright 2011 信息科学与技术学院<br />powered by <a href="http://3haku.net">princehaku</a>  
        </div> 
          
    </div>
    <!-- Right Side/Main Content End -->
    <!--[if IE 6]>
    <style>#leftside{overflow:hidden;}</style>
    <![endif]--> 
        <!-- Left Dark Bar Start -->
    <div id="leftside">
    	<div class="user">
        	<img src="img/avatar.png" width="44" height="44" class="hoverimg" alt="Avatar" />
            <p>您好 :</p>
            <p class="username"><c:out value="${user.username}"/>&nbsp;&nbsp;<c:out value="${ug.desp}"/></p>
            <p class="userbtn"><a href="?c=myprofile" title="">我的资料</a></p>
            <p class="userbtn"><a href="login.do" title="">退出登录</a></p>
        </div>
        <div class="notifications">
        	<p class="notifycount"><a href="" title="" class="notifypop"><c:out value="${noticesize}"/></a></p>
            <p><a href="" title="" class="notifypop">条新通知</a></p>
            <p class="smltxt"><a href="" title="" class="notifypop">(点击查看)</a></p>
        </div>
        ${leftbar}
    </div>
    <!-- Left Dark Bar End --> 
    
    <!-- Notifications Box/Pop-Up Start --> 
    <div id="notificationsbox">
        <h4>通知</h4>
        <ul>
            <c:if test="${notice}=null">
                米有通知哇
            </c:if>
            <c:forEach var="vow" items="${notice}">
                <li>
                    <a href="#${vow.id}" title=""><img src="img/icons/icon_square_close.png" alt="Close" class="closenot" /></a>
                    <h5><a href="#${vow.id}" title="">${vow.text}</a></h5>
                    <p>${vow.time}</p>
                </li>             
            </c:forEach>
        </ul>
    </div>
    <%@include file="panel_foot.jsp" %>