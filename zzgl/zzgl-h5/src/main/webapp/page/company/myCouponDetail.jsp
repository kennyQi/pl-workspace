<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>代金券详情</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx }/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx }/css/kajuan.css"/>

<!-- javascript -->
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
</head>
<body class="personal">
<!-- 页头  -->
<header class="header">
  <h1>代金券详情</h1>
  <div class="left-head"> <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn"> <span class="inset_shadow"> <span class="head-return"></span> </span> </a> </div>
</header>
<!-- 页头 --> 
<section class="kajuan_info">
    <article>
      ${couponDTO.baseInfo.couponActivity.baseInfo.ruleIntro} 
    </article>     
 </section>  
</body>
</html>