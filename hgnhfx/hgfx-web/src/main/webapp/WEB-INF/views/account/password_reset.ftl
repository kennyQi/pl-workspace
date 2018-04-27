<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>重置密码</title>
    <#assign contextPath=springMacroRequestContext.getContextPath() />
	<link  href="${contextPath}/resources/css/common.css" type="text/css" rel=stylesheet>
    <link  href="${contextPath}/resources/css/login.css" type="text/css" rel=stylesheet>	
	<script src="${contextPath}/resources/js/jquery-1.12.3.min.js" charset="utf-8"></script>
</head>
<style>
	.tip-show-a{display:none;  top: -91px;  right: 2px;}
	.tip-show-b{display:none;  top: -28px;}
	.password.warnning{border: 1px solid #ea5657;}
	.login.warnning{border: 1px solid #ea5657;}
	.tip-show-a span, .tip-show-b span { margin-top: -1px;}
</style>
<script >
	function warning1(data){
		$(".password")
		.addClass("warnning");
		$(".tip-show-a").find(".show-text").html(data);
		$(".tip-show-a").show();
	}
	
	function warningRemove1(){
		$(".password")
		.removeClass("warnning");
		$(".tip-show-a").hide();
	}
	
	function warning2(){
		$(".login")
		.addClass("warnning");
		$(".tip-show-b").show();
	}
	
	function warningRemove2(data){
		$(".login")
		.removeClass("warnning");
		$(".tip-show-b").find(".show-text").html(data);
		$(".tip-show-b").hide();
	}
	$(function(){
		//验证码错误提示
		//warning1();
		//验证码错误提示取消
		//warningRemove1();
		
		//密码不能为空提示
		//warning2();
		
		//密码不能为空提示取消
		//warningRemove2();
		
		
	});
	
	$(function(){
		document.getElementById("login").onfocus=function(){
			this.parentNode.style.cssText+="border:1px solid #4799ef";
		};
		document.getElementById("login").onblur=function(){
			this.parentNode.style.cssText+="border:1px solid #d5d5d5;";
		};
		document.getElementById("password").onfocus=function(){
			this.parentNode.style.cssText+="border:1px solid #4799ef";
		};
		document.getElementById("password").onblur=function(){
			this.parentNode.style.cssText+="border:1px solid #d5d5d5;";
		};
	});
	
	$(function(){
		$(".login-btn").hover(function(){
			$(this).css("background","url(${contextPath}/resources/img/forget-password/mod.png) no-repeat");
		},function(){
			$(this).css("background","url(${contextPath}/resources/img/forget-password/modify.png) no-repeat");
		});
	});
</script>
<body>
	<div id="top">
		<div class="top-wrap">
			<div class="top-wrap-pure">
				<div class="tip-show-b" style="display:none;"><img src="${contextPath}/resources/img/login-tip/red-warning.png"><span >请输入正确格式的电子邮件</span></div>
				<p class="tip" style="margin:34px 0 44px 0;">重置密码</p>
				
				<div class="password" style="margin:18px 0 20px 0;">
					<img src="${contextPath}/resources/img/login/lock.png">
					<input type="password" id="password" placeholder="8~20位由字符/数字/符号组成的新密码">
					
					<div class="yellow-tip newPass" style="display:none">账号或密码输入错误</div>
				</div>
				<div class="login">
					<img style="  width: 24px;  top: 8px;" src="${contextPath}/resources/img/forget-password/pass.png">
					<input type="password" id="login" placeholder="请再次输入您的新密码">
					
					<div class="yellow-tip againPass" style="display:none">账号或密码输入错误</div>
				</div>
				<div class="test-code" style="margin:18px 0 26px 0;display:none;">
					<div class="test-code-abc">
						<div class="test-code-ab ">
							<input type="text" id="test_code_b" class="test-code-a" placeholder="输入右侧图形验证码">
						</div>
						
					</div>
					
					<div class="test-code-b"></div>
					<a href="javascript:void(0);"><div class="test-code-c"></div></a>
				</div>
				<div class="select" style="display:none;">
					<input class="select-ab"  type="checkbox">
					<span class="select-a" style="color:#454545">记住账号</span>
					<a href="javascript:void(0);"><span class="select-b" style="color:#4799ef">忘记密码？</span></a>
				</div>
				<a href="javascript:void(0);"><div  style="margin:32px 0 55px 0;width:434px;height:50px;background:url(${contextPath}/resources/img/forget-password/modify.png) no-repeat;" class="login-btn">
				<div class="tip-show-a"><img src="${contextPath}/resources/img/login-tip/red-warning.png"><span class="show-text">请输入正确的密码</span></div>
				<div class="tip-show-b"><img src="${contextPath}/resources/img/login-tip/red-warning.png"><span class="show-text">两次输入不一致,请重输</span></div>
				</div></a>
			</div>
		</div>
		
		<div class="top-gap">
			<div class="top-gap-line"></div>
		</div>
		<p  style="margin:26px auto 140px auto;text-align:left;width:440px;"><span><a href="javascript:void(0);"  onclick="location.href='${contextPath}/login'"><span class="reg"><<返回登录</span></a></span></p>
	</div>
</body>
</html>
<script type="text/javascript"  language="javascript" src="${contextPath}/resources/js/account/password_reset.js?ver=0000001"></script>
<script type="text/javascript">
	var webRoot = "${contextPath}";
	var token = '${token}';
</script>
