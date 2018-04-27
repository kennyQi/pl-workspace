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
<!--页面样式-->
<link rel="stylesheet" href="${ctx }/css/hgsl_base.css">
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/showtip.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/showtip.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/jsbox.js"></script>
<script type="text/javascript" src="${ctx}/js/pop.ups/init.js"></script>
<script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script>
<script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>
<script type="text/javascript">
	$(function(){
		$("#loginName").val("");
		$("#password").val("");
	})
	function doSave() {
		var regSpecial =new RegExp(/[(\~)(\!)(\！)(\@)(\#)(\￥)(\%)(\……)(\&)(\*)(\（)(\）)(\—)(\+)(\=)(\-)(\、)(\|)(\\)(\/)(\？)(\》)(\《)(\.)(\,)(\。)(\，)(\>)(\<)(\：)(\;)(\")(\‘)(\’)(\;)(\:)(\")(\')]+/);
		//var reg =/^[a-zA-Z0-9]{6,20}$/;
		var name = $("#loginName").val();
		var pwd = $("#password").val();
		if (name == null || name=='') {
			showTip("用户名不能为空","好的");
			$("#loginName").focus();
			return false;
		}
		console.info(pwd);
		console.info(regSpecial.test(pwd));
		if(regSpecial.test(pwd)){
			showTip("密码不能包含特殊字符","好的");
			$("#password").focus();
			return false;
		}
		if (pwd.length <6 || pwd.length >20) {
			showTip("请输入6-20位字符密码","好的");
			$("#password").focus();
			return false;
		}
		try{
		$.pop.lock(true);
		$.pop.load(true, '正在登录中...');
		$("#queryForm").ajaxSubmit({
			timeout:60000,
			dataType:'json',
			error:function() {
			    $.pop.load(false, function() {
	    			$.pop.lock(false);
			    	setTimeout(function() {
			    		showTip('系统繁忙,请稍候','好的');
			    	}, 666);
			    });
	    	},
			success:function(data) {
				$.pop.load(false, function() {
           			$.pop.lock(false);
           			setTimeout(function() {
           				if (data.result == "1") {	//绑定成功
           					$.pop.tips('登录成功', 1, function() {
           						var url = Js.decodeURL($("#redirectUrl").val());
				            	if (url == null || url == '') {
				            		url = "${ctx}/init?openid=${openid}";
				            	}
				            	console.info(url);
				            	location.href = url;
	           				});
			           	} else {
			           		showTip(data.message,"好的");
			           	}
           			}, 666);
           		});
			}
		});
		}catch(e){
			alert(e);
		}
	}
	function reg() {
		var redirectUrl = encodeURIComponent($("#redirectUrl").val());
		location.href = "${ctx}/user/reg?openid=${openid}&redirectUrl=" + redirectUrl;
	}
	function repwd() {
		var redirectUrl = encodeURIComponent($("#redirectUrl").val());
		location.href = "${ctx}/user/valid/2?openid=${openid}&redirectUrl=" + redirectUrl;
	}
	
	function clear_x(){
		$("#loginName").val("");
	}
</script>
</head>
<body>
	<!-- 公共页头  -->
<header class="header">
  <h1>登录</h1>
  <div class="left-head"> 
    <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
        <span class="inset_shadow">
            <span class="head-return"></span>
        </span>
    </a> 
  </div>
</header>
<!--页面内容-->
  <div  page="login" class="login">
    <form action="${ctx}/user/check?openid=${openid}" class="listForm" id="queryForm" method="post">
      	<input id="redirectUrl" type="hidden" value="${redirectUrl }">        
        <article class="circle_b bottom_c radius" id="payInfo">
            <section class="secure selectBank" style="position: relative;">
                <span class="username"></span>
                <span class="fRight">
                    <input name="loginName" id="loginName" placeholder="请输入您的用户名/手机号" type="text" value="" maxlength="11">
                	<a class="clear_x" onclick="clear_x()" href="#" style="position: absolute; right: 0%; font-size: 20px; color: #666;">×</a>
                </span>
            </section>
            <section style="position: relative;">
                <span class="password"></span>
                <span class="fRight">
                    <input id="password" name="password"  placeholder="请输入您的密码" type="password">
                </span>
            </section>
        </article>
        <%--<div class="err"><p>会员登录失败：密码错误!!!</p>
        <p>如果您忘记了密码，请点击右下角忘记密码以找回</p>
        </div>--%>

        <div class="log_ele clear">
            <a href="javascript:reg();">免费注册</a>
            <a href="javascript:repwd();">找回密码</a>
        </div>

        <div class="col_div">
            <a href="javascript:doSave();" id="btn_click" class="btn btn-green radius" title="会员登录">登录</a>
        </div>

    </form>
  </div>

</body>
</html>