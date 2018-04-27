<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>中智票量</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 公共样式 -->
<link rel="stylesheet" href="${ctx }/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx }/css/hgsl_base.css">
 <script type="text/javascript" src="${ctx}/js/jquery.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script> 	 <script type="text/javascript" src="${ctx}/js/json2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jsbox.js"></script> 	 <script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script><script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>	 <script type="text/javascript" src="${ctx}/js/hsl.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$(".clear_x").click(function() {
		$(this).hide().parent().find("input").val("");
	});
	if ($("#loginName").val() == null || $("#loginName").val() == '') {
		$("#loginName").parent().find(".clear_x").hide();
	} else {
		$("#loginName").parent().find(".clear_x").show();
	}
	if ($("#password").val() == null || $("#password").val() == '') {
		$("#password").parent().find(".clear_x").hide();
	} else {
		$("#password").parent().find(".clear_x").show();
	}
	if ($("#verify").val() == null || $("#verify").val() == '') {
		$("#verify").parent().find(".clear_x").hide();
	} else {
		$("#verify").parent().find(".clear_x").show();
	}
	$("#loginName,#password,#verify").keyup(function() {
		if ($(this).val() == null || $(this).val() == '') {
			$(this).parent().find(".clear_x").hide();
		} else {
			$(this).parent().find(".clear_x").show();
		}
	});
});
function doSave(){
	var name = $("#loginName").val();
	var pswd = $("#password").val();
	var verify = $("#verify").val();
	if (name == null || name == '') {
		$.pop.tips("账号不能为空");
		$("#loginName").focus();
		return;
	}
	if (pswd == null || pswd == '') {
		$.pop.tips("密码不能为空");
		$("#password").focus();
		return;
	}
	if (verify == null || verify == '') {
		$.pop.tips("验证码不能为空");
		$("#verify").focus();
		return;
	}
	$.pop.lock(true);
	$.pop.load(true, '正在绑定中...');
	$.ajax({
		url:"${ctx}/user/check/"+$("#verify").val()+"?t="+new Date().getTime(),
		timeout:60000,
		dataType:"text",
		error:function() {
		    $.pop.load(false, function() {
    			$.pop.lock(false);
		    	setTimeout(function() {
		    		$.pop.tips('系统繁忙,请稍候');
		    	}, 666);
		    });
    	},
		success: function(data){
			if (data == '1') {
				$("#myform").ajaxSubmit({
					dataType:'json',
					error:function() {
					    $.pop.load(false, function() {
			    			$.pop.lock(false);
					    	setTimeout(function() {
					    		$.pop.tips('系统繁忙,请稍候');
					    	}, 666);
					    });
			    	},
					success:function(data) {
						$.pop.load(false, function() {
		           			$.pop.lock(false);
		           			setTimeout(function() {
		           				if (data.result == "1") {	//绑定成功
		           					$.pop.tips('账户绑定成功', 1, function() {
		           						location.href = "${ctx}/jp/init?openid=${openid}";
			           				});
					           	} else {
					           		$.pop.tips(data.message);
					           	}
		           			}, 666);
		           		});
					}
				});
			} else {
				$.pop.load(false, function() {
					$.pop.lock(false);
	          		setTimeout(function() {
	          			$.pop.tips("验证码错误");
	          		}, 666);
				});
			}
		}
	});
}
function reload() {
	$("#codeImg").attr("src", $("#codeImg").attr("src") + "&t=" + new Date());
}
</script>
</head>
<body>
	<!-- 公共页头  -->
	<header class="header">
	  <h1>绑定账号</h1>
	  <div class="left-head"> 
	    <a id="goBack" href="${ctx}/jp/init?openid=${openid}" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	</header>
	<!-- 页面内容 -->
	<div class="content">
		<p>微信用户:${nickname }</p>
		<form action="${ctx}/user/bindwx" class="listForm" id="myform" method="post">
			<input type="hidden" id="wxAccountName" name="wxAccountName" value="${openid }" /> 
			<article class="circle_b bottom_c" id="payInfo" style="width:93%;margin:0 auto;">
	            <section class="secure selectBank"  style="position: relative;">
	                <span class="username"></span>
	                <span class="fRight">
	                    <input  name="loginName" id="loginName" placeholder="请输入票量账名/手机号码" type="text" value="" style="line-height: normal;" />
	                	<a class="clear_x" href="javascript:;" style="position: absolute; right: 0%; font-size: 20px; color: #666;">×</a>
	                </span>
	            </section>
	            <section class="selectBank" style="position: relative;" >
	                <span class="password"></span>
	                <span class="fRight">
	                    <input  name="password" id="password" placeholder="请输入登录密码" type="password" style="line-height: normal;" />
	                    <a class="clear_x" href="javascript:;" style="position: absolute; right: 0%; font-size: 20px; color: #666;">×</a>
	                </span>
	            </section>
	             <section class="selectBank" style="position: relative;">
	                <span class="verify"></span>
	                <span class="fRight">
	                    <input  name="verify" id="verify" placeholder="请输入下方的验证码" type="text" style="line-height: normal;">
	                    <a class="clear_x" href="javascript:;" style="position: absolute; right: 0%; font-size: 20px; color: #666;">×</a>
	                </span>
	            </section>
	            <section class="pic">
	            	<img id="codeImg" src="${ctx }/servlet/randCode?openid=${openid}" onclick="reload();" alt="点击换一张">
	            	<a style="font-size:12px;" href="javascript:reload();">换一张</a>
	            </section>
	        </article>
	        
	        <div class="log_ele clear">
	            <a href="${ctx }/user/valid/2?openid=${openid}">忘记密码？</a>
	            <a href="jsvascript:;"></a>
	        </div>

	        <div class="col_div">
	            <a href="javascript:doSave()" class="btn btn-blue" title="" style="width: 93%; margin: 0 auto;">确认提交</a>
	        </div>
		</form>
	</div>
	
</body>
</html>