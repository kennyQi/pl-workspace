<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>我的资料</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/company.css"/>
</head>
<body class="personal">
<!-- 页头  -->
<header class="header">
	  <h1>我的资料</h1>	  
	  <div class="left-head"> 
	    <a id="goBack" href="${ctx}/user/center?openid=" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	</header>
<!-- 页头 -->
<a href="${ctx}/user/editInfoView">
	<section class="hd_myprofile">
	 	<c:if test="${!empty headimgurl}">
	 		<img src="${headimgurl }" alt="头像" id="headImage" class="hd_data_p radius3"/>
	 	</c:if>
	 	<c:if test="${empty headimgurl}">
	 		<img src="${ctx}/img/defaultHeadImage.png" alt="头像" id="headImage" class="hd_data_p radius3"/>
	 	</c:if>
	 	<h2 style="color:#3f3f3f;position: absolute;color: #3f3f3f;left: 95px;font-size: 15px;">账号：${loginName}</h2>
		<em id="upfileimg" class="m_dlog"></em>
	</section>
</a>
	<!--start 页面内容 -->
	<section class="plist pmyfile">
		<a href="javascript:;" class="i06"><div><i></i>${mobile }<em></em></div></a>
 		<a href="${ctx}/user/editInfoView" class="i07"><div><i></i>${nickName }<em></em></div></a>
	    <a href="${ctx}/user/editInfoView" class="i08"><div><i></i>${email }<em></em></div></a>
	    <a href="${ctx}/user/editInfoView" class="i09"><div><i></i>${province } - ${city }<em></em></div></a>
 	</section>
	<!--end 页面内容 -->
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
</body>
</html>