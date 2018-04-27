<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>忘记密码</title>
    <#assign contextPath=springMacroRequestContext.getContextPath() />
	<link  href="${contextPath}/resources/css/common.css" type="text/css" rel=stylesheet>
    <link  href="${contextPath}/resources/css/login.css" type="text/css" rel=stylesheet>	
	<script src="${contextPath}/resources/js/jquery-1.12.3.min.js" charset="utf-8"></script>
	<script type="text/javascript"  language="javascript" src="${contextPath}/resources/js/code.js"></script>
</head>
<style>
	.login_or_reg1{float:left;  color: #4799ef;}
	.login_or_reg2{float:right;}
	.code3{color:#fff;background-color:#4799ef;line-height:42px;  height: 100%;position:absolute;right: -132px;
	  top: -1px;  width: 132px;  text-align: center;cursor:pointer;border-top: 1px solid #4799ef;
	    border-bottom: 1px solid #4799ef;  border-right: 1px solid #4799ef;}
	.tip-show-a{  right: 0px;  top: -28px;display:none;}
	.tip-show-b{  right: 0px;  top: -90px;display:none;}
	.tip-show-a span, .tip-show-b span { margin-top: -1px;}
	.login_or_reg1:hover{text-decoration:underline;}
</style>
<script >
	function warning1(data){
		$(".login").css({"border":"1px solid #ea5657"});
		$(".tip-show-b").find(".show-text").html(data);
		$(".tip-show-b").show();
	}
	
	function warningRemove1(){
		$(".login").css({"border":"1px solid #d5d5d5"});
		$(".tip-show-b").hide();
	}
	
	function warning2(data){
		$("#password").css({"border-left":"1px solid #ea5657",
		"border-top":"1px solid #ea5657","border-bottom":"1px solid #ea5657"});
		$("#code").css({"border-right":"1px solid #ea5657",
		"border-top":"1px solid #ea5657","border-bottom":"1px solid #ea5657"});
		$(".tip-show-a").find(".show-text").html(data);
		$(".tip-show-a").show();
	}
	
	function warningRemove2(){
		$("#password").css({"border-left":"1px solid #d5d5d5",
		"border-top":"1px solid #d5d5d5","border-bottom":"1px solid #d5d5d5"});
		$("#code").css({"border-right":"1px solid #4799ef",
		"border-top":"1px solid #4799ef","border-bottom":"1px solid #4799ef"});
		$(".tip-show-a").hide();
	}
	
	$(function(){
		//请输入正确的手机号码提示
		//warning1();
		//请输入正确的手机号码提示取消
		//warningRemove1();
		//短信验证码错误请重新输入提示
		//warning2();
		//短信验证码错误请重新输入提示取消
		//warningRemove2();
		
		
	});
	
	$(function(){
		$(".login-btn").hover(function(){
			$(this).css("background","url(${contextPath}/resources/img/forget-password/next2.png) no-repeat");
		},function(){
			$(this).css("background","url(${contextPath}/resources/img/forget-password/next.png) no-repeat");
		});
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
</script>
<body>
	<div id="top">
		<div class="top-wrap">
			<div class="top-wrap-pure">
				<div class="tip-show-b"><img src="${contextPath}/resources/img/login-tip/red-warning.png"><span >请输入正确格式的电子邮件</span></div>
				<p class="tip" style="margin:34px 0 44px 0;">忘记密码</p>
				<div class="login">
					<img style=" width: 17px;  top: 10px;" src="${contextPath}/resources/img/forget-password/tel.png">
					<input type="text" id="login" placeholder="您的绑定手机号码">
					
					<div class="yellow-tip mobile" style="display:none">账号或密码输入错误</div>
				</div>
				<div class="password" style="margin:18px 0 32px;  width: 70%;">
					<img src="${contextPath}/resources/img/forget-password/code.png">
					<input type="text" style="  width: 82%;" id="password" placeholder="短信验证码">
					<div class="code3" id="code">获取验证码</div>
					
					<div class="yellow-tip smsCode" style="display:none">账号或密码输入错误</div>
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
				<a href="javascript:void(0);"><div  style="margin:22px 0 0px 0;width:434px;height:50px;background:url(${contextPath}/resources/img/forget-password/next.png) no-repeat;" class="login-btn">
				<div class="tip-show-a"><img src="${contextPath}/resources/img/login-tip/red-warning.png"><span class="show-text">短信验证码错误请重新输入</span></div>
				<div class="tip-show-b"><img src="${contextPath}/resources/img/login-tip/red-warning.png"><span class="show-text" style="margin-top: -1px">请输入正确的手机号码</span></div>
				</div></a>
				<p style="font-size:12px;color:#585858;margin: 20px 0;text-align:left;">如忘记绑定手机号码，请联系客服 400-880-8888</p>
			</div>
		</div>
		
		<div class="top-gap">
			<div class="top-gap-line"></div>
		</div>
		<p  style="width:440px;display:block;margin:0 auto;  margin: 26px auto 160px auto;"><a href="javascript:void(0);"  onclick="location.href='${contextPath}/login'"><span class="login_or_reg1"><<返回登录</span></a><!--<span class="login_or_reg2">没有账号，<a href="javascript:void(0);"><span class="reg">马上注册！</span></a></span>--></p>
	</div>
</body>
</html>
<script type="text/javascript">
	var webRoot = "${contextPath}";
	var mob_near = "${_MOB_SESSION_NEAR_!?html}";
	var token = '${token}';
</script>
<script type="text/javascript"  language="javascript" src="${contextPath}/resources/js/account/password_forget.js?ver=0000002"></script>

