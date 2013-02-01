
function checkuserreg(){
    $("#errorMsg").html("");
    
    if(!matchIt($("#sid").val(),"\\d*")||$("#sid").val()==""){
        $("#errorMsg").html("请输入正确的学号");
        return false;
    }
    if($("#password1").val()==""){
        $("#errorMsg").html("请输入一个密码");
        return false;
    }
    if($("#password1").val()!=$("#password2").val()){
        $("#errorMsg").html("两次输入密码不一致");
        return false;
    }
    if(!matchIt($("#email").val(),".*?@.*")){
        $("#errorMsg").html("请输入您的email");
        return false;
    }
    if($("#col").val()=="0"){
        $("#errorMsg").html("请选择您的学院");
        return false;
    }
    if($("#major").val()=="0"){
        $("#errorMsg").html("请选择您的专业");
        return false;
    }
    return true;
}

function checkbulk(){
//    if($("#bulkaction").val()==0){
//       alert("请选择操作"); 
//       return false;
//    }
    return true;
}

function checktrunk(){
    
    if($("#major").val()=="0"){
        alert("请选择专业");
        return false;
    }
    return true;
}

function checkuserimport(){
    if($("#major").val()=="0"){
        alert("请选择专业");
        return false;
    }
    if($("#password").val()==""){
        alert("请输入一个初始密码");
        return false;
    }
    if($("#group").val()=="0"){
        alert("请选择用户组");
        return false;
    }
    if($("#file").val()==""){
        alert("请选择上传的文件");
        return false;
    }
    return true;
}
function checkusersave(){
    if(!matchIt($("#sid").val(),"\\d*")||$("#sid").val()==""){
        alert("请输入正确的学号");
        return false;
    }
    if($("#major").val()=="0"){
        alert("请选择专业");
        return false;
    }
    if($("#group").val()=="0"){
        alert("请选择用户组");
        return false;
    }
    if($("#group").val()>1 && $("#maxPapers").val()=="0"){
        alert("请选择带生上限");
        return false;
    }
    return true;
}
function checkuseradd(){
    if(!matchIt($("#sid").val(),"\\d*")||$("#sid").val()==""){
        alert("请输入正确的学号");
        return false;
    }
    if($("#major").val()=="0"){
        alert("请选择专业");
        return false;
    }
    if($("#password").val()==""){
        alert("请输入一个密码");
        return false;
    }
    if($("#group").val()=="0"){
        alert("请选择用户组");
        return false;
    }
    if($("#group").val()>1 && $("#maxPapers").val()=="0"){
        alert("请选择带生上限");
        return false;
    }
    return true;
}