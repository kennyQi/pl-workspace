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
<link rel="stylesheet" href="${ctx}/css/base.2.0.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/ticket.css">
</head>
<body class="ticketSuc">
	<!-- 公共页头  -->
	<header class="header">
	  <h1>预订结果</h1>
	  
	  <div class="left-head"> 
	    <a id="goBack" href="${ctx}/index?openid=${openid}" class="tc_back head-btn">
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
	<div  class="success">
		<h3>恭喜您，已购买成功！</h3>
		<div class="cont">
			<ul>
				<li>预订项目：<span>${orderInfo}</span></li>
				<li>订单状态：<span>预订成功</span></li>
			</ul>
		</div>
		<div class="btn">
			<a href="${ctx}/index?openid=${openid}">返回首页</a>
			<a href="${ctx}/mpo/detail?openid=${openid}&orderId=${orderId}">查看订单</a>
		</div>
	</div>


</body>
</html>