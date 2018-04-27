<#assign contextPath=springMacroRequestContext.getContextPath() />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title></title>
		
		
		

        	<link rel="stylesheet" href="../../../resources/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="../../../resources/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
<script src="../../../resources/commonFile/jquery-1.12.0.min.js"></script>
<script src="../../../resources/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>	
<script src="../../../resources/commonFile/scUtil102.js"></script>	
<script src="../../../resources/commonFile/jsService.js"></script>	
        

<!--


<link rel="stylesheet" href="${contextPath}/resources/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${contextPath}/resources/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
<script src="${contextPath}/resources/commonFile/jquery-1.12.0.min.js"></script>
<script src="${contextPath}/resources/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>	
<script src="${contextPath}/resources/commonFile/scUtil102.js"></script>	
<script src="${contextPath}/resources/commonFile/jsService.js"></script>
-->
	</head>

<style type="text/css"> 

.divCenter{ 
position:fixed;left:50%;top:50%;margin-left:width/2;margin-top:height/2;
} 


.inputTip{
	color: #e00;
}

.myInput{
	    width: 70%;
}
</style> 
	<body>
		
		
  <div class="container" style="padding-top: 40px;">
  	<form class="form-horizontal">
    <fieldset>
      <div id="legend" class="">
        <legend class="">初始化数据库配置</legend>
      </div>
    <div class="control-group">

          <!-- Text input-->
          <label class="control-label" for="input01">数据库位置jdbc_url</label>
          <div class="controls">
            <input type="text" placeholder="例如“jdbc:mysql://192.168.2.211:3306/hg-project-test?characterEncoding=utf8”" class="input-xlarge myInput" name="jdbcUrl" testData="admin">
            <p class="help-block inputTip" id="tipJdbcUrl">errorMsg</p>
          </div>
        </div>

    <div class="control-group">

          <!-- Text input-->
          <label class="control-label" for="input01">数据库用户名db_username</label>
          <div class="controls">
            <input type="text" placeholder="数字、英文、下划线" class="input-xlarge myInput" name="userName" testData="aaaa123123123">
            <p class="help-block inputTip" id="tipUserName">errorMsg</p>
          </div>
        </div>

    <div class="control-group">

          <!-- Text input-->
          <label class="control-label" for="input01">数据库登录密码db_password</label>
          <div class="controls">
            <input type="password" placeholder="数字、英文、下划线" class="input-xlarge myInput" name="pwd" testData="aaaa123123123">
            <p class="help-block inputTip" id="tipPwd" >errorMsg</p>
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

		var tipUserName = $("#tipUserName")
		var tipJdbcUrl = $("#tipJdbcUrl")
		var tipPwd = $("#tipPwd")
		tipUserName.myHide(0)
		tipJdbcUrl.myHide(0)
		tipPwd.myHide(0)
		
adminConfigService.bind("createAdminAccount",function(r){
	alert(r.message)
	window.location.href = "${contextPath}/login"
},function(r){
	alert(r.message)
},function(){
	return $$.doCheck()
})
	
		
		
		
		$$.bindInput()
		$$.bindCheck("jdbcUrl",function(v){
			if(v == null || v == ""){
				tipJdbcUrl.html("必填").myShow()
				return false
			}
			var re = /^[^\u4e00-\u9fa5]*$/.test(v)
			if(re){
				tipJdbcUrl.myHide()
			}else{
				tipJdbcUrl.html("jdbc_url格式不正确，不能含有中文").myShow()
			}
			return re
		})
		$$.bindCheck("userName",function(v){
			if(v == null || v == ""){
				tipUserName.html("必填").myShow()
				return false
			}
			var re = /^[\da-zA-z_]*$/.test(v)
			if(re){
				tipUserName.myHide()
			}else{
				tipUserName.html("格式不正确，支持数字、英文、下划线").myShow()
			}
			return re
		})
		$$.bindCheck("pwd",function(v){
			if(v == null || v == ""){
				tipPwd.html("必填").myShow()
				return false
			}
			var re = /^[\da-zA-z_]*$/.test(v)
			if(re){
				tipPwd.myHide()
			}else{
				tipPwd.html("格式不正确，支持数字、英文、下划线").myShow()
			}
			return re
		})
		
		
	</script>

</html>