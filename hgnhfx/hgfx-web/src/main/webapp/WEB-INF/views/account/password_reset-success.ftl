<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>密码重置</title>
    <#assign contextPath=springMacroRequestContext.getContextPath() />
	<link  href="${contextPath}/resources/css/common.css" type="text/css" rel=stylesheet>
    <link  href="${contextPath}/resources/css/password-reset.css" type="text/css" rel=stylesheet>	
</head>
<style>

</style>
<body>
	<div id="top">
		<div class="top-wrap">
			<div class="top-wrap-pure" style="  padding-bottom: 140px;">
				<div class="tip1" style="margin:126px 0 66px 0;">
					<img src="${contextPath}/resources/img/password-reset/child.png">
					<span>恭喜您密码重置成功！</span>
				</div>
				
				<div id="btn">
					<a href="javascript:void(0);"><div class="btn-a-b" onclick="location.href='${contextPath}/login'"></div></a>
				</div>
			</div>
		</div>
		
	</div>
</body>
</html>
