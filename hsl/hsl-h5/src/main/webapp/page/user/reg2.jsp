<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>双十一--注册</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<link rel="stylesheet" href="${ctx}/resources/css/double_one_regist.css" type="text/css">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 公共样式 -->
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/showtip.js"></script>
<%@ include file="/page/common/jscommon.jsp"%>
<script type="text/javascript">
var sended = false;
var allow = true;
$(document).ready(function() {
	var btn = document.getElementById("btn");
	if ($("#mobile").val() == null || $("#mobile").val() == '') {
		btn.disabled = true;
		btn.style.color = "#666";
	} else {
		if (!sended) {
			btn.disabled = false;
			btn.style.color = "green";
		}
	}
	$("#mobile").keyup(function() {
		if ($(this).val() == '' || !allow) {
			btn.disabled = true;
			btn.style.color = "#666";
		} else {
			btn.disabled = false;
			btn.style.color = "green";
		}
	});
});
/** 短信验证 **/
function sendValidCode() {
	var mobile = $("#mobile").val();
	var scene = $("#scene").val();
	if (mobile.match(/1[34578][0-9]{9}/)) {
		var url = "${ctx}/user/sendValidCode?openid=${openid}&mobile="
				+ mobile + "&scene=" + scene + "&t="+new Date().getTime();
		var btn = document.getElementById("btn");
		btn.disabled = true;
		btn.style.color = "#666";
		btn.value = "验证码发送中...";
		$.ajax({
			url: url, 
			timeout:60000,
			dataType: "json", 
			error: function(){
				allow = true;
				showTip("系统繁忙,请稍后","好的");
				btn.disabled = false;
				btn.style.color = "green";
				btn.value = "重新获取";
			}, 
			success: function(data) {
				showTip(data.message,"好的");
				if(data.result == "1") {
					allow = false;
					triggerValidCodeSender();
					sended = true;
					$("#validToken").val(data.token);
				} else {
					allow = true;
					btn.disabled = false;
					btn.style.color = "green";
					btn.value = "重新获取";
				}
			}
		});
	} else {
		showTip("请输入正确的手机号码","好的");
		allow = true;
		$("#mobile").focus();
	}
	
}

function triggerValidCodeSender() {
	var count = 60;
	var btn = document.getElementById("btn");
	btn.disabled = true;
	btn.style.color = "#666";
	var timer = setInterval(function() {
		if (count > 1) {
			count--;
			btn.value = count + "秒后重新获取";
		} else {
			clearInterval(timer);
			btn.disabled = false;
			btn.value = "重新获取";
			btn.style.color = "green";
			sended = false;
			allow = true;
		}
	}, 1000);
}

function doSave() {
	var validCode=$("#validCode").val();
	if ($("#mobile").val().match(/1[3458][0-9]{9}/)) {
		if(validCode==null || validCode==''){
			showTip("请输入验证码","好的");
			$("#validCode").focus();
			return;
		}
		//密码校验
		var regSpecial =new RegExp(/[(\~)(\!)(\！)(\@)(\#)(\￥)(\%)(\……)(\&)(\*)(\（)(\）)(\—)(\+)(\=)(\-)(\、)(\|)(\\)(\/)(\？)(\》)(\《)(\.)(\,)(\。)(\，)(\>)(\<)(\：)(\;)(\")(\‘)(\’)(\;)(\:)(\")(\')]+/);
		//var reg =/^[a-zA-Z0-9]{6,20}$/;
		var pwd = $("#password").val();
		if(regSpecial.test(pwd)){
			showTip("密码不能包含特殊字符","好的");
			$("#password").focus();
			return false;
		}
		if(pwd.length <6 || pwd.length >20) {
			showTip("请输入6-20位字符密码","好的");
			$("#password").focus();
			return false;
		}
		
/* 		if ($("#scene").val() == '1') {
 */			$.pop.lock(true);
			$.pop.load(true, '正在注册中...');
			$("#queryForm").ajaxSubmit({
				timeout:60000,
				dataType : 'json',
				success : function(data) {
					$.pop.load(false, function() {
	           			$.pop.lock(false);
	           			setTimeout(function() {
	           				if (data.result == "1") {	//注册成功
	           					$.pop.tips('注册成功，Enjoy Your Life！', 1, function() {
	           						var url = Js.decodeURL($("#redirectUrl").val());
		    		            	if (url == null || url == '') {
		    		            		url = "${ctx}/index?openid=${openid}";
		    		            	}
		    		            	location.href = url;
	           					});
	    		           	} else {
	    		           		showTip(data.message,"好的");
	    		           	}
	           			}, 666);
	           		});
				}
			});
		/* } else {
			location.href = '${ctx}/user/repwd?openid=${openid}&redirectUrl=' + 
					encodeURIComponent($("#redirectUrl").val()) + '&identify=' + 
					$("#validCode").val() + '&validToken=' + $("#validToken").val();
		} */
	} else {
		showTip("请输入正确的手机号码","好的");
		$("#mobile").focus();
	} 
	
}

function bind() {
	var redirectUrl = encodeURIComponent($("#redirectUrl").val());
	location.href = "${ctx}/user/bind?openid=${openid}&redirectUrl=" + redirectUrl;
}
</script>
</head>
<body ontouchstart>
<section class="content">
	<div class="text_wrap">
		<img src="${ctx}/resources/images/text.png" width="100%">
	</div>
	<!--页面内容-->
  <div page="login" class="login">
    <form action="${ctx}/user/register" class="listForm" id="queryForm" method="post">
      <input name="redirectUrl" type="hidden" id="redirectUrl" value="${redirectUrl }">        
      <input name="openid" type="hidden" id="openid" value="${openid }">
      <input name="validToken" type="hidden" id="validToken" value="">
      <input name="scene" id="scene" type="hidden" value="1">
		<article class="circle_b bottom_c radius" id="payInfo">
			<section class="input_wrap" style="position: relative;">
                <span class="input">
                    <input class="registration" name="mobile" id="mobile" placeholder="请输入11位有效手机" type="text" maxlength="11" value="">
                </span>
			</section>
			<section class="input_wrap" style="position: relative;">
                <span class="input">
                    <input class="registration" name="password" id="password" placeholder="密码" type="password">
                </span>
			</section>
			<section class="input_wrap code_wrap" style="position: relative;">
                <span class="input">
                    <input class="registration" name="validCode" id="validCode" placeholder="请输入短信验证码" type="text">
                    <div class="btn">
                    	<input id="btn" type="button" value="获取验证码" onclick="sendValidCode();" disabled="" style="width:94px;position:absolute;top:0;right:0;">
                    </div>
                </span>
			</section>
		</article>
		<div class="btn_go_wrap">
			<a href="javascript:doSave()" class="btn_go" id="btn_click">开启脱光之旅&gt;&gt;</a>
		</div>

    </form>
  </div>
	<div id="cli_dialog_div"></div>
	<div class="logo_wrap">
		<img src="${ctx}/resources/images/logo2.png" width="100%">
	</div>
</section>
</body>
</html>