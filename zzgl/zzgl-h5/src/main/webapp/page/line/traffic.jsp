<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/circuit.css" />
</head>
<body>
	<!-- 页头  -->
	<header class="header">
	  <h1>交通信息</h1>
	  <div class="left-head"> <a id="goBack" href="${ctx}/line/detail?id=${line.id}" class="tc_back head-btn"> <span class="inset_shadow"> <span class="head-return"></span> </span> </a> </div>	 
	</header>
	<!-- 页头 -->
	<section class="descipition c_dv_info">
		<p>
		${line.baseInfo.trafficDescription}
		</p>
	</section>
</body>
</html>