<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

	$("#mobile").keyup(function(){
		if(!$("#mobile").val()||!$("#mobile").val().match(/1[34578][0-9]{9}/)){
			btn.disabled = true;
			btn.style.color = "#ccc";
		}else{
			btn.disabled = false;
			btn.style.color = "green";
		}
	})
	/*if ($("#mobile").val() == null || $("#mobile").val() == '') {
	 btn.disabled = true;
	 btn.style.color = "#ccc";
	 } else {
	 if (!sended) {
	 btn.disabled = false;
	 btn.style.color = "green";
	 }
	 }
	 $("#mobile").blur(function() {
	 if ($(this).val() == '' || !allow) {
	 btn.disabled = true;
	 btn.style.color = "#ccc";
	 } else {
	 btn.disabled = false;
	 btn.style.color = "green";
	 }
	 });*/
});
/** 短信验证 **/
function sendValidCode() {
	var imgCode=$("#image_code").val();
	var mobile = $("#mobile").val();
	var scene = $("#scene").val();
	if(!imgCode){
		showTip("请输入图形验证码","好的");
		return;
	}
	if (mobile.match(/1[34578][0-9]{9}/)) {
		var url = "${ctx}/user/sendValidCode?openid=${openid}&mobile="
				+ mobile + "&scene=" + scene + "&t="+new Date().getTime()+"&imgcode="+imgCode;
		var btn = document.getElementById("btn");
		btn.disabled = true;
		btn.style.color = "#ccc";
		btn.value = "验证码发送中...";
		$.ajax({
			url: url, 
			timeout:60000,
			dataType: "json", 
			error: function(){
				$('#imgcode').prop('src','${ctx}/user/captcha-image?'+new Date());
				allow = true;
				showTip("系统繁忙,请稍后","好的");
				btn.disabled = false;
				btn.style.color = "green";
				btn.value = "重新获取";
			}, 
			success: function(data) {
				$('#imgcode').prop('src','${ctx}/user/captcha-image?'+new Date());
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
<body>
	<!-- 公共页头  -->
<header class="header">
  <h1>免费注册</h1>
  <div class="left-head"> 
    <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
        <span class="inset_shadow">
            <span class="head-return"></span>
        </span>
    </a> 
  </div>
</header>
<!--页面内容-->
  <div page="login" class="login">
    <form action="${ctx}/user/register" class="listForm" id="queryForm" method="post">
      <input name="redirectUrl" type="hidden" id="redirectUrl" value="${redirectUrl }">        
      <input name="openid" type="hidden" id="openid" value="${openid }">
      <input name="validToken" type="hidden" id="validToken" value="">
      <input name="scene" id="scene" type="hidden" value="1">
        
      <article class="circle_b bottom_c radius" id="payInfo">
          <section class="secure selectBank" style="position: relative;">
              <span class="LoginName_info"></span>
              <span class="fRight">
                  <input class="registration"  name="mobile" id="mobile" placeholder="请输入11位有效手机" type="text" maxlength="11" value="">
              </span>
          </section>
		  <section class="secure selectBank" style="position: relative;">
			  <span class="LoginName_edir"></span>
              <span class="fRight">
				   <input class="registration imgcodeInput"  name="image_code" id="image_code" placeholder="请输入图片验证码" type="text">
                  <img src="${ctx}/user/captcha-image" id="imgcode" class="imgcode" onclick="$(this).prop('src','${ctx}/user/captcha-image?'+new Date());">
              </span>
		  </section>
          <section class="secure selectBank LoginName_dic" style="position: relative;">
              <span class="LoginName_edir"></span>
              <span class="fRight">
                  <input class="registration"  name="validCode" id="validCode" placeholder="请输入短信验证码" type="text" />
                  <input id="btn" type="button" value="获取验证码" onclick="sendValidCode();" />
              </span>
          </section>
          <section style="position: relative;">
              <span class="password"></span>
              <span class="fRight">
                  <input class="registration" name="password" id="password" placeholder="密码" type="password">
              </span>
          </section>
      </article>
        
        <div class="log_ele clear">
        	<c:if test="${isWCBrowser }">
	            <a href="javascript:bind();">绑定票量账号</a>
        	</c:if>
        	<c:if test="${!isWCBrowser}">
        	<a href="${ctx }/user/login" style="float:right">直接登录</a>
        	</c:if>
	    </div>
	    
        <div class="col_div">
            <a href="javascript:doSave()" class="btn btn-green radius" id="btn_click">注册</a>
        </div>

    </form>
  </div>

</body>
</html>