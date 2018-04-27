<#assign contextPath=springMacroRequestContext.getContextPath() />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
		
		
		<!--
        	作者：offline
        	时间：2016-05-27
        	描述：
        	<link rel="stylesheet" href="../bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="../bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
<script src="../commonFile/jquery-1.12.0.min.js"></script>
<script src="../bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>	
<script src="../commonFile/scUtil102.js"></script>	
<script src="../commonFile/jsService.js"></script>	
        -->

<link rel="stylesheet" href="${contextPath}/resources/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}/resources/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
<script src="${contextPath}/resources/commonFile/jquery-1.12.0.min.js"></script>
<script src="${contextPath}/resources/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>	
<script src="${contextPath}/resources/commonFile/scUtil102.js"></script>	
<script src="${contextPath}/resources/commonFile/jsService.js"></script>	
	</head>

<style type="text/css"> 

.divCenter{ 
position:fixed;left:50%;top:50%;margin-left:width/2;margin-top:height/2;
} 


.inputTip{
	color: #e00;
}

.myInput{
	    width: 30%;
}
</style> 
	<body>
		
		
  <div class="container" style="padding-top: 40px;">
  	<form class="form-horizontal">
    <fieldset>
      <div id="legend" class="">
        <legend class="">初始化管理员账号</legend>
      </div>
    <div class="control-group">

          <!-- Text input-->
          <label class="control-label" for="input01">管理员登录名</label>
          <div class="controls">
            <input type="text" placeholder="4至12位英文" class="input-xlarge myInput" name="loginName" testData="admin">
            <p class="help-block inputTip" id="tipLoginName">errorMsg</p>
          </div>
        </div>

    <div class="control-group">

          <!-- Text input-->
          <label class="control-label" for="input01">登录密码</label>
          <div class="controls">
            <input type="password" placeholder="12至32位数字和英文" class="input-xlarge myInput" name="pwd" testData="aaaa123123123">
            <p class="help-block inputTip" id="tipPwd">errorMsg</p>
          </div>
        </div>

    <div class="control-group">

          <!-- Text input-->
          <label class="control-label" for="input01">确认登录密码</label>
          <div class="controls">
            <input type="password" placeholder="确认登录密码" class="input-xlarge myInput" name="pwd2" testData="aaaa123123123">
            <p class="help-block inputTip" id="tipPwd2" >errorMsg</p>
          </div>
        </div>

    <div class="control-group">
          <label class="control-label">&nbsp;</label>

          <!-- Button -->
          <div class="controls">
            <button class="btn btn-success" onclick="adminConfigService.createAdminAccount()" type="button">配置完成</button>
          </div>
        </div>

    </fieldset>
  </form>
  	<!--
  	<form>
			
		<div>
			adminLoginName
			<input value="${a}"/>

		</div>
		<div>

			pwd
			<input />
		</div>
		<div>
			rePwd
			<input />

		</div>
		<div>
			<button type="submit">submit</button>
		</div>
		</form>
      -->
  </div>

		
	</body>
	<script type="text/javascript">
setCp("${contextPath}")
</script>
	<script >

		var tipLoginName = $("#tipLoginName")
		var tipPwd2 = $("#tipPwd2")
		var tipPwd = $("#tipPwd")
		tipPwd.myHide(0)
		tipPwd2.myHide(0)
		tipLoginName.myHide(0)
		
adminConfigService.bind("createAdminAccount",function(r){
	alert(r.message)
	window.location.href = "${contextPath}/login"
},function(r){
	alert(r.message)
	if(r.code!=null && r.code == 1000){
		window.location.href = "${contextPath}/login"
	}
},function(){
	return $$.doCheck()
})
	
		
		
		
		$$.bindInput()
		$$.bindCheck("loginName",function(v){
			if(v == null || v == ""){
				tipLoginName.html("必填").myShow()
				return false
			}
			var re = /^[\da-zA-z]{4,12}$/.test(v)
			if(re){
				tipLoginName.myHide()
			}else{
				tipLoginName.html("登录名格式不正确，支持4至12位的英文").myShow()
			}
			return re
		})
		$$.bindCheck("pwd",function(v){
			if(v == null || v == ""){
				tipPwd.html("必填").myShow()
				return false
			}
			var re = /^[\da-zA-z]{12,32}$/.test(v)
			if(re){
				tipPwd.myHide()
			}else{
				tipPwd.html("密码格式不正确，支持12至32位数字和英文").myShow()
			}
			return re
		})
		$$.bindCheck("pwd2",function(v){
			
			if(v == null || v == ""){
				tipPwd2.html("必填").myShow()
				return false
			}
						
			if((v == $$.data("pwd"))){
				tipPwd2.myHide()
				return true
				
			}else{
				tipPwd2.html("两次输入的密码不一致").myShow()
				return false				
			}
		})
		
		
	</script>

</html>