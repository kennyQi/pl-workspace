<%@ include file="/page/common/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="${ctx }/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx }/css/hgsl_base.css">
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/showtip.js"></script>
<%@ include file="/page/common/jscommon.jsp"%>
<script type="text/javascript">
function doSave(){
	var regSpecial =new RegExp(/[(\~)(\!)(\！)(\@)(\#)(\￥)(\%)(\……)(\&)(\*)(\（)(\）)(\—)(\+)(\=)(\-)(\、)(\|)(\\)(\/)(\？)(\》)(\《)(\.)(\,)(\。)(\，)(\>)(\<)(\：)(\;)(\")(\‘)(\’)(\;)(\:)(\")(\')]+/);
	//var reg =/^[a-zA-Z0-9]{6,20}$/;
	var newPwd = $("#newPwd").val();
	if(regSpecial.test(newPwd)){
		showTip("密码不能包含特殊字符","好的");
		$("#newPwd").focus();
		return false;
	}
	if(newPwd.length <6 || newPwd.length >20){
		showTip("请输入6-20位字符密码","好的");
		$("#newPwd").focus();
		return false;
	}else {
		$.pop.lock(true);
		$.pop.load(true, '正在重置中...');
		$("#queryForm").ajaxSubmit({
			timeout:60000,
			dataType:'json',
			error:function() {
			    $.pop.load(false, function() {
	    			$.pop.lock(false);
			    	setTimeout(function() {
			    		showTip("系统繁忙,请稍候","好的");
			    	}, 666);
			    });
	    	},
			success:function(data) {
           		$.pop.load(false, function() {
           			$.pop.lock(false);
           			setTimeout(function() {
           				if (data.result == "1") {	//密码重置成功
           					$.pop.tips('密码重置成功', 1, function() {
           						if ('${isWCBrowser}' == 'true') {
           							var url = Js.decodeURL($("#redirectUrl").val());
					            	if (url == null || url == '') {
					            		url = "${ctx}/index?openid=${openid}";
					            	}
					            	location.href = url;
           						} else {
           							var redirectUrl = encodeURIComponent($("#redirectUrl").val());
	    		            		location.href = "${ctx}/user/login?openid=${openid}&redirectUrl=" + redirectUrl;
           						}
           					});
    		           	} else {
           					showTip(data.message,"好的");
    		           	}
           			}, 666);
           		});
			}
		});
	}
}
</script>	
</head>
<body>
	<!-- 公共页头  -->
	<header class="header">
	  <h1>重置密码</h1>
	  
	  <div class="left-head"> 
	    <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	</header>
	<div class="content">
		<p>&nbsp;请填写新密码</p>
		<form action="${ctx}/user/updatePwd" id="queryForm" class="listForm" method="post">
			<input type="hidden" id="redirectUrl" value="${redirectUrl }" />
			<input type="hidden" id="validToken" name="validToken" value="${validToken }">
			<article class="circle_b bottom_c radius" id="payInfo">
	            <section class="secure" style="position: relative;">
	                <span class="password"></span>
	                <span class="fRight">
	                   <input class="registration"  name="newPwd" id="newPwd" placeholder="密码" type="password">
	                </span>
	            </section>           
	        </article>    
	        <div class="col_div">
	            <a href="javascript:doSave()" id="btn_click" class="btn btn-green radius" title="确定">确定</a>
	        </div>
	    </form>
	</div>
</body>
</html>