
/**初始化时间选择器
	 * 
	 */
function initTimePicker(){

    $('.datetime').datepicker({
        dateFormat: 'yy-mm-dd',	
        monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
        currentText: '现在'	,
        closeText: '完成',
        prevText: '上月',
        nextText: '下月',
        timeText: '时间',
        hourText: '小时',
        minuteText: '分钟'
    });

}
/**正则匹配是否满足
 */
function matchIt(s,regxp)
{
    var patrn=new RegExp(regxp);
    if (patrn.exec(s)!=s) return false
    return true
} 
/**根据value初始化选择某opition
 */
function initopition(opitionobj,value){
    opitionobj.find("option").each(function(){
        if(this.value==value){
            $(this).attr("selected","selected");
        }else{
            
    }
                
    });
}
/**根据专业编号获取所在学院的其他专业信息
 */
function initMajor(id){
    if(id==null){
        return;
    }
    $.ajax({
        type:"post",
        url: "global.do?fetchMajorById&r=" + Math.random(),
        data:{
            "id":id
        },
        error: function(){
            alert("获取专业失败,请与管理员联系");
        },
        success: function(data){
            var obj=null;
            try{
                obj=$.parseJSON(data);
            }catch(e){
                alert(e);
                return;
            }	 
            source="  专业:<select name=\"major\" id=\"major\" style=\"width:150px;\"><option value=0>请选择专业</option>";
            for(i=0;i<obj.length;i++){
                if(obj[i].id!=id){
                    source=source+"<option value='"+obj[i].id+"'>"+obj[i].name+"</option>";
                }else{
                    source=source+"<option selected='true' value='"+obj[i].id+"'>"+obj[i].name+"</option>";
                }
            }
            source=source+"</select>";
            $("#majordiv").html(source);
            
            $("#col").find("option").each(function(){
                if(this.value==obj.cid){
                    $(this).attr("selected","true");
                }else{
                    $(this).attr("selected","");
                }
                
            });
        }
    });
}
/**根据学院号码获取专业信息
 *
 */
function fetchMajor(){
    id=$("#col").val();
    $.ajax({
        type:"post",
        url: "global.do?fetchMajor&r=" + Math.random(),
        data:{
            "id":id
        },
        error: function(){
            alert("获取专业列表失败,请与管理员联系");
        },
        success: function(data){
            var obj=null;
            try{
                obj=$.parseJSON(data);
            }catch(e){
                alert(e);
                return;
            }	 
            source="  专业:<select name=\"major\" id=\"major\" style=\"width:150px;\"><option value=0>请选择专业</option>";
            for(i=0;i<obj.length;i++){
                source=source+"<option value='"+obj[i].id+"'>"+obj[i].name+"</option>";
            }
            source=source+"</select>";
            $("#majordiv").html(source);
        }
    });
}
/**显示新闻
 * 
 */
function viewNews(id){
    $.facebox.close();
    $("<a href='news.do?c=view&id="+id+"&r=" + Math.random()+"'>查看</a>").facebox({
        type:'ajax'
    }).click(); 
}
/**显示题库
 * 
 */
function viewSubject(id){
    $.facebox.close();
    $("<a href='trunk.do?c=view&id="+id+"&r=" + Math.random()+ "'>查看</a>").facebox({
        type:'ajax'
    }).click(); 
}
/**查看选入情况
 *
 */
function viewSelection(id){
    $.facebox.close();
    $("<a href='selection.do?c=view&id="+id+"&r=" + Math.random() + "'>查看</a>").facebox({
        type:'ajax'
    }).click(); 
}
/**显示用户
 * 
 */
function viewUser(id){
    $.facebox.close();
    $("<a href='user.do?c=view&id="+id+"&r=" + Math.random() +"'>查看</a>").facebox({
        type:'ajax'
    }).click(); 
}
/**显示权限设置
 * 
 */
function setPermission(id,string){
    $.facebox.close();
    $("<a href='permission.do?c=show&id="+id+"&string="+string+"&r=" + Math.random() + "'>查看</a>").facebox({
        type:'ajax'
    }).click(); 
}

function in_array(key,array){
    for(i=0;i<array.length;i++){
        if(array[i]!=""&&key!=""&&array[i]==key){
            return true;
        }
    }
    return false;
}

var t;
/**显示成功消息
 * 
 */
function success(txt,time){
    clearTimeout(t);
    text="<img src='img/icons/icon_success.png' alt='Success'><span>Info : </span>"+txt;
    $("#noticesuccess .content").html(text);
    $("#noticeerror").hide();
    $("#noticesuccess").show();
    t=setTimeout(function(){
        $("#noticesuccess").fadeOut(300);
    },time);
    alert(txt);
}
/**显示错误消息
 * 
 */
function error(text,time){
   return success(text,time);
//    clearTimeout(t);
//    text="<img src='img/icons/icon_error.png' alt='Error'><span>Error!</span>"+text;
//    $("#noticeerror .content").html(text);
//    $("#noticesuccess").hide();
//    $("#noticeerror").show();
//    t=setTimeout(function(){
//        $("#noticeerror").fadeOut(300);
//    },time);
}
/**初始化设置
 * 
 */
$(document).ready(function() {
    initTimePicker();
});
/**取消选题
 * 
 */
function deselectThis(id){
    
    $("#img"+id).html('<img alt="Approve" src="facebox/loading.gif">');
    $.ajax({
        type:"post",
        url: "selection.do?c=deselect&r=" + Math.random(),
        data:{
            "id":id
        },
        error: function(){
            error("取消失败",3000);
        },
        success: function(data){
            var obj=null;
            try{
                obj=$.parseJSON(data);
            }catch(e){
                alert(e);
                return;
            }	 
            if(obj.statu==1){
                $("#img"+id).parent().remove();
                success(obj.msg,3000);
            }else{
                error(obj.msg,3000);
            }
        }
    });
}
/**选入题目
 * 
 */
function selectThis(id){
    $("#img"+id).html('<img alt="Approve" src="facebox/loading.gif">');
    $.ajax({
        type:"post",
        url: "selection.do?c=select&r=" + Math.random(),
        data:{
            "id":id
        },
        error: function(){
            error("选入失败 服务器连接失败",3000);
        },
        success: function(data){
            var obj=null;
            try{
                obj=$.parseJSON(data);
            }catch(e){
                alert(e);
                return;
            }	 
            if(obj.statu==1){
                $("#img"+id).html('<img alt="Approve" src="img/icons/icon_approve.png">');
                success(obj.msg,3000);
                lefts=$("#img"+id).prev().html().split("/");
                s=((lefts[0]-1));
                $("#img"+id).prev().html(s+"/"+lefts[1]);
            }else{
                error(obj.msg,3000);
                $("#img"+id).html('<input type="button" onclick="selectThis('+id+');" class="btn" value="选入" />');
                lefts=$("#img"+id).prev().html().split("/");
                if(lefts[0]!=null&&lefts[0]==0){
                    error("选入失败  已达上限",3000);
                }
            }
        }
    });
}
/**保存选题
 * 
 */
function saveSel(id) {
    $("#savesel"+id).html('<img alt="Approve" src="facebox/loading.gif">');
    max=$("#saveseltext"+id).val();
    $.ajax({
        type:"post",
        url: "selection.do?c=savesel&r=" + Math.random(),
        data:{
            "id":id,
            "max":max
        },
        error: function(){
            error("保存失败",3000);
        },
        success: function(data){
            var obj=null;
            try{
                obj=$.parseJSON(data);
            }catch(e){
                alert(e);
                return;
            }	 
            if(obj.statu==0){
                error(obj.msg,3000);
            }else{
                success(obj.msg,3000);
                left=$("#saveleft"+id).html(obj.left);
            }
            $("#savesel"+id).html('<input type="button" onclick="saveSel('+id+');" class="btn" value="保存" />');
            
        }
    });
}
/**导出清单
 * 
 */
function export_selection(can_export, paper_id) {
    if (can_export < 2) {
        alert('对不起，选题结束之前不能导出');
    } else {
        window.location.href = 'selection.do?c=selectexport&mid=' + paper_id;
    }
}
/**改变选题状态
 * 
 */
function change_paperyear_status(paperyear_id, statu) {
    
    $.ajax({
        type:"post",
        url: "selection.do?c=updatestatus&r=" + Math.random(),
        data:{
            "pid":paperyear_id,
            "s":statu
        },
        error: function(){
            error("更新失败",3000);
        },
        success: function(data){
            var obj=null;
            try{
                obj=$.parseJSON(data);
            }catch(e){
                alert(e);
                return;
            }	 
            if(obj.statu==0){
                error(obj.msg,3000);
            }else{
                success(obj.msg,3000);
                left=$("#saveleft"+id).html(obj.left);
            }
            
        }
    });
}
/**强制撤销某生选题
 * 
 */
function unapprove(paperselid,userid,aobj) {
    
    $.ajax({
        type:"post",
        url: "selection.do?c=unapprove&r=" + Math.random(),
        data:{
            "pid":paperselid,
            "uid":userid
        },
        error: function(){
            error("取消失败",3000);
        },
        success: function(data){
            var obj=null;
            try{
                obj=$.parseJSON(data);
            }catch(e){
                alert(e);
                return;
            }
            if(obj.statu==1){
                lefts = $("#saveleft"+paperselid).html();
                s = parseInt(lefts) + 1;
                $("#saveleft"+paperselid).html(s);
                $(aobj).parent().remove();
                success(obj.msg,3000);
            }else{
                error(obj.msg,3000);
            }
        }
    });
}
/**强制选入某生选题
 * 
 */
function approve(paperselid,aobj) {
    sid = $("#sidappending"+paperselid).val();
    if (sid=="") {
        alert('请输入需要加入的学号');
        return;
    }
    $.ajax({
        type:"post",
        url: "selection.do?c=approve&r=" + Math.random(),
        data:{
            "pid":paperselid,
            "sid":sid
        },
        error: function(){
            error("加入失败",3000);
        },
        success: function(data){
            var obj=null;
            try{
                obj=$.parseJSON(data);
            }catch(e){
                alert(e);
                return;
            }
            if(obj.statu==1){
                lefts = $("#saveleft"+paperselid).html();
                s = parseInt(lefts) - 1;
                $("#saveleft"+paperselid).html(s);
                $(aobj).parent().prepend(obj.txt);
                success(obj.msg,3000);
            }else{
                error(obj.msg,3000);
            }
        }
    });
}