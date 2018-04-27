<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>个人中心</title>
<!-- meta信息，可维护 -->
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta name="version" content="票量旅游v1.3">
<meta http-equiv="Cache-Control" content="must-revalidate,no-cache">
<meta http-equiv="x-dns-prefetch-control" content="on" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/company.css"/>
<style>
	html{ background: #fff}
</style>
</head>
<body>
<!-- 页头  -->
<header class="header headerblue">
	  <h1>个人中心</h1>	  
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
			<div class="p_photo">
			<div class="p_photo1" style="width:128px; margin:0 auto">
			<a href="" class="photo_mask"></a>				
				<img src="${ctx}/img/member/myp_icon.png" alt="姓名" title="姓名">
			</div>
			</div>	
			 	
	 	<div class="p_login">
        <a href="${ctx}/user/login" class="max_bth m_Rose radius">登录</a> <a href="${ctx}/user/reg" class="max_bth m_org radius">注册</a>
         </div>
	</section>
 

	<!--start 页面内容 -->
	<section class="plist pmyfile pt10">
    <a href="${ctx}/user/userDetailInfo" class="i01"><div><i></i>我的资料<em></em></div></a>
    <a href="${ctx }/user/repwdd" class="i02"><div><i></i>密码修改<em></em></div></a>
    <a href="${ctx}/user/jpos?openid=${openid}" class="i03"><div><i></i>我的订单<em></em></div></a>
    <a href="${ctx}/hslH5/line/lineOrderListPage?openid=${openid}" class="i03"><div><i></i>线路订单<em></em></div></a>
    <a href="${ctx}/company/couponQuery?isAvailable=true" class="i05"><div><i></i>我的卡券<em></em></div></a>
 	</section>
	<!--end 页面内容 -->

</body>
</html>