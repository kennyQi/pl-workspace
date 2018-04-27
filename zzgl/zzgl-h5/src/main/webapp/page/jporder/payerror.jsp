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
<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/base.2.0.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/ticket.css">
<script type="text/javascript" src="${ctx}/js/jquery.js"></script> 	 
<script type="text/javascript">
$(function(){
	var message = $("#message").val();
	if(message != "1"){
		showMsg(message);
	}
});

function showMsg(msg){
    var h=$(window).height()
        ,w=$(window).width()
        ;
    var html='<div class="g_msg"><span>'+msg+'</span></div>';
    $("body").append(html);
    var msg_w=parseInt($(".g_msg").css("width")),msg_h=parseInt($(".g_msg").css("height"));
    $(".g_msg").css({"top":(h-msg_h)/2,"left":(w-msg_w)/2})
    $(".g_msg").show(150);
    setTimeout(function(){
            $(".g_msg").remove();
    },2000);
}

</script>

</head>
<body class="ticketSuc">
	<!-- 公共页头  -->
	<header class="header">
	  <h1>支付结果</h1>
	  
	  <div class="left-head"> 
	    <a id="goBack" href="${ctx}/jp/init?openid=${openid}" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>

	  <!-- <div class="right-head">
	    <a href="/home/index.html" class="head-btn fn-hide">
	        <span class="inset_shadow">
	            <span class="head-home"></span>
	        </span>
	    </a> 
	  </div> -->
	</header>
	<div class="success">
		<h3>很遗憾，支付失败！</h3>
		
		<div class="btn">
			<a href="${ctx}/init?openid=${openid}">返回首页</a>
			
		</div>
	</div>
  <!-- 消息提示 -->
	 <c:if test="${not empty message}">
			<input id="message" value="${message}" type="hidden">
	</c:if>
	<c:if test="${empty message}">
		<input id="message" value="1" type="hidden">
	</c:if>  

</body>
</html>




