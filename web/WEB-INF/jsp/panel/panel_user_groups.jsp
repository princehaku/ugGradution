<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="contentcontainer">
    <div class="headings altheading">
        <h2>组管理</h2>
    </div>
    <div class="contentbox">
        <form action="<%=request.getContextPath()%>/groups.do?c=delete" onsubmit="return checkbulk();"  method="post">
            <table width="100%">
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>名称</th>
                        <th>KEY</th>
                        <th>操作</th>
                        <th><input type="checkbox" id="checkboxall" value="" name=""></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="vo" items="${groups}">
                        <tr class="alt">
                            <td><c:out value="${vo.id}"/></td>
                            <td><c:out escapeXml="false" value="${vo.desp}"/></td>
                            <td>
                                <input type="text" id="permissionset<c:out default="0" value="${vo.id}" />" name="permissionset[]" value="${vo.permissionset}" /> 
                                <input type="button" onclick="setPermission(<c:out default="0" value="${vo.id}" />,$('#permissionset<c:out default="0" value="${vo.id}" />').val())" class="btn" value="设置权限">
                            </td>
                            <td>
                                <a onclick="if(confirm('确定删除?')){window.location.href='groups.do?c=delete&id=<c:out value="${vo.id}"/>'}" href="javascript:void(null);"><img alt="Unapprove" src="img/icons/icon_unapprove.png"></a>
                            </td>
                            <td><input type="checkbox" name="checkall[]" value="<c:out value="${vo.id}"/>"></td>
                        </tr>
                    <input type="hidden" name="id[]" value="<c:out value="${vo.id}"/>" />
                </c:forEach>
                </tbody>
            </table>
            <div class="extrabottom">
                <ul>
                    <!--<li><a href="?c=useredit"><img border="0" alt="Add" src="img/icons/icon_add.png"> 添加</a></li>-->
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
<script>
    function setPermissionText(id,text){
        $.facebox.close();
        
        $.ajax({
            type:"post",
            url: "groups.do?c=save",
            data:{
                "id":id,
                "set":text
            },
            error: function(){
                error("权限设置失败",3000);
            },
            success:function(data){
                var obj=null;
                try{
                    obj=$.parseJSON(data);
                }catch(e){
                    alert(e);
                    return;
                }	 
                if(obj.statu==1){
                    success("权限设置成功",3000);
                    $("#permissionset"+id).val(text);
                }else{
                    error(obj.msg,3000);
                }
            }
        });
    }
</script>