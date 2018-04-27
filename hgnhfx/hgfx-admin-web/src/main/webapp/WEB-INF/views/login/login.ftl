<#assign contextPath=springMacroRequestContext.getContextPath() />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${name}系统运营后台</title>
    <link type="text/css" href="${contextPath}/resources/css/login.css" rel="stylesheet">
    
    <script src="${contextPath}/resources/commonFile/jquery-1.12.0.min.js"></script>
	<script src="${contextPath}/resources/commonFile/jsConfig.js"></script>
	<script src="${contextPath}/resources/commonFile/scUtil102.js"></script>	
	<script src="${contextPath}/resources/commonFile/jsService.js"></script>	
    <script type="text/javascript">

        var localHref=document.location.href;
        if(localHref.indexOf('/index')>=0){
            document.location.href = "${contextPath}/login";
        }

        function toLogin(){
            var pwd =document.getElementById("pwd").value;
            document.getElementById("password").value = hex_md5(pwd);

            alert(hex_md5(pwd));
        }
    </script>
    
    
    
</head>

<body>
<div class="header"></div>
<div class="content">
    <div class="con-top">
        <div class="top-n"><span ><img  class="line"src="${contextPath}/resources/image/line.jpg"></span><span class="con-z">系统运营后台</span> <span><img   class="line"src="${contextPath}/resources/image/line.jpg"></span>
        </div></div>
    <div class="loginbar">
        <img src="${contextPath}/resources/image/login_pc.jpg">
        <form class="myform" name="loginForm" method="post" action="${contextPath}/login/loginpost" onsubmit="" >
            <div class="pt">
                <input type="text" placeholder="用户名" name="loginName" testData="admin2"/>
            </div>
            <div class="pt">
                <input type="password" placeholder="密码" id="password"  testData="aaaa123123123" name="password"/>
            </div>
            <div class="pt">
                <input name="validcode" placeholder="验证码" type="text" size="5" />
                <span class="yz_y" style="margin-left: 15px;"><img id="validate_code" src="${contextPath}/login/valid-code-image.jpg" width="75" height="24" onclick="$(this).attr('src', '${contextPath}/login/valid-code-image.jpg?'+new Date());" /><a onclick="$('#validate_code').attr('src', '${contextPath}/login/valid-code-image.jpg?'+new Date());" href="javascript:void(0)">换一张</a></span>
            </div>
        ${(message)!}
           <!--  <div class="rempd">
                <input type="checkbox" />记住密码
            </div> -->
            <div class="pt dl">
                <input type="submit" value="登录"  />
            </div>
        </form></div>

</div>
<div class="footer"> <div class="con-ftr"><img src="${contextPath}/resources/image/login_bg.jpg"></div></div>
</body>


<script type="text/javascript">
setCp("${contextPath}")
if(!adminConfigService.adminIsCreated().result){
	alert("系统未初始化，现在跳转到初始化配置")
	window.location.href = "${contextPath}/adminConfig/toCreateAdminAccount"
}
</script>
</html>
