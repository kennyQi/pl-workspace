<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
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
<link rel="stylesheet" href="${ctx}/css/ticket.css"/>
</head>
<body>
	<!--start 公共页头  -->
	<header class="header">
	  <h1>服务器繁忙</h1>
	  
	  <div class="left-head"> 
	    <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
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
	<!--end 公共页头  -->

	<!--start 页面内容 -->

<section class="error500">
	<div class="content">
        <h3></h3>
        <p>服务器繁忙,请稍后！</p>
    </div>
</section>
	<!--end 页面内容 -->

</body>
</html>