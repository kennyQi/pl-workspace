<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>票量旅游</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 公共样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/hgsl_base.css">
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
		btn.style.color = "#ccc";
	} else {
		if (!sended) {
			btn.disabled = false;
			btn.style.color = "green";
		}
	}
	$("#mobile").keyup(function() {
		if ($(this).val() == '' || !allow) {
			btn.disabled = true;
			btn.style.color = "#ccc";
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
		btn.style.color = "#ccc";
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
		$("#mobile").focus();
		allow = true;
	}
	
}

function triggerValidCodeSender() {
	var count = 60;
	var btn = document.getElementById("btn");
	btn.disabled = true;
	btn.style.color = "#ccc";
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
		if(validCode == null || validCode == ''){
			showTip("请输入验证码","好的");
			$("#validCode").focus();
			return false;
		}
		//业务场景 1 注册时验证(已放注册页验证) 2 找回密码验证 3解绑手机 4绑定新手机号
		if ($("#scene").val() == '2') {
			$.pop.lock(true);
			$.pop.load(true, '正在验证中...');
			$("#listForm").ajaxSubmit({
				timeout:60000,
				dataType : 'json',
				success : function(data) {
					$.pop.load(false, function() {
	           			$.pop.lock(false);
	           			setTimeout(function() {
	           				if (data.result == "1") {	//验证成功
	           					$.pop.tips('验证成功', 1, function() {
		    		            	location.href = '${ctx}/user/successProving?openid=${openid}&redirectUrl=' + 
			    						encodeURIComponent($("#redirectUrl").val()) + '&validToken=' + $("#validToken").val();
	           					});
	    		           	} else {
	    		           		$.pop.tips(data.message);
	    		           	}
	           			}, 666);
	           		});
				}
			});
		}else{ 
			location.href = '${ctx}/user/repwd?openid=${openid}&redirectUrl=' + 
					encodeURIComponent($("#redirectUrl").val()) + '&validCode=' + 
					$("#validCode").val() + '&validToken=' + $("#validToken").val();
		} 
	} else {
		showTip("请输入正确的手机号码","好的");
		$("#mobile").focus();
	}
	
}
</script>
</head>
<body>
	<!-- 公共页头  -->
	<header class="header">
	  <h1>${scene == 1 ? '注册验证' : '忘记密码' }</h1>
	  
	  <div class="left-head"> 
	    <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	</header>
	<div class="login" page="login">
		<form action="${ctx}/user/checkValidCode" class="listForm" method="post" id="listForm">
			<input type="hidden" id="openid" name="openid" value="${openid}" />
     	    <input type="hidden" id="scene" name="scene" value="${scene}" />
     	    <input type="hidden" id="validToken" name="validToken" value="" />
     	    <input type="hidden" id="redirectUrl" value="${redirectUrl}" />
        <article class="circle_b bottom_c radius" id="payInfo">
            <section class="secure selectBank" style="position: relative;">
                <span class="username"></span>
                <span class="fRight">
                    <input name="mobile" id="mobile" placeholder="请输入您的手机号码" type="text" value="" maxlength="11">
                </span>
            </section>
            <section class="secure LoginName_dic" style="position: relative;">
                <span class="LoginName_edir"></span>
                <span class="fRight">
                    <input class="registration"  name="validCode" id="validCode" placeholder="请输入短信验证码" type="text">
                    <input id="btn" type="button" value="获取验证码" onclick="sendValidCode();"></input>
                </span>
            </section>
        </article>         

        <div class="log_ele clear">
        	<p>验证码会以短信形式发送到您手机上</p>             
        </div>

        <div class="col_div">
            <a href="javascript:doSave();" id="btn_click" class="btn btn-green radius" title="确定">下一步，设置新密码</a>
        </div>
    </form>
	</div>
</body>
</html>