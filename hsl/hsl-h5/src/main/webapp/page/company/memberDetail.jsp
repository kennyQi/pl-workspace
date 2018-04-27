<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>个人版个人资料</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx }/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx }/css/company.css"/>
</head>
<body class="personal">
<!-- 页头  -->
<header class="header">
	  <h1>成员信息</h1>	  
	  <div class="left-head"> 
	    <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	</header>
<!-- 页头 -->

<section class="p_myprofile">
	 	<c:if test="${!empty headimgurl}">
	 		<img style="width:90px;" src="${headimgurl }" alt="头像"/>
	 	</c:if>
	 	<c:if test="${empty headimgurl}">
	 		<img style="width:90px;" src="${ctx}/img/defaultHeadImage.png" alt="头像"/>
	 	</c:if>
	 	<div class="p_name">
      <h3>${memberResult.member.name }</h3>
      <p>${memberResult.member.job }</p>
    </div>
	</section>
 

	<!--start 页面内容 -->
	<section class="plist pmyfile">
		<a href="javascript:;" class="i06"><div><i></i>${memberResult.member.mobilePhone }<em></em></div></a>
 		<a href="javascript:;" class="i10"><div><i></i>${memberResult.companyName }-${memberResult.departName }<em></em></div></a>
    <a href="javascript:;" class="i11"><div><i></i>${memberResult.member.certificateID }<em></em></div></a>
  	</section>
	<!--end 页面内容 -->

</body>
</html>