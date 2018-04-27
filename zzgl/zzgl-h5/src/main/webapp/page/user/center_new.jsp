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
<meta name="version" content="票量旅游 v1.3 ">
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
	    <a id="goBack" href="${ctx}/init?openid=${openid}" class="tc_back head-btn">
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
			<a  class="photo_mask"></a>	
			<c:if test="${!empty headimgurl}">
				<img src="${headimgurl}" alt="头像" title="头像" id="headImage">
	 		</c:if>			
	 		<c:if test="${empty headimgurl}">
	 			<img src="${ctx}/img/defaultHeadImage.png" alt="头像" title="头像" id="headImage">
	 		</c:if>
			</div>	
			</div>	 	
	 	<div class="p_name">
	 	  		 <h3><i></i><span>${loginName}</span></h3>
	 	  		 <c:if test="${userType == 2}">
	 	  		 <p class="p_companyname"><i></i><span>${name}</span></p>
				 </c:if>				  	  		 
	 	</div> 
	</section>
 

	<!--start 页面内容 -->
    <section class="plist pmyfile pt10">
    <a href="${ctx}/user/userDetailInfo" class="i01"><div><i></i>我的资料<em></em></div></a>
    <a href="${ctx }/user/balance" class="i05"><div><i></i>账户余额<em></em></div>
    <a href="${ctx }/user/repwdd" class="i02"><div><i></i>密码修改<em></em></div></a>
    <a href="${ctx}/user/jpos?openid=${openid}" class="i03"><div><i></i>机票订单<em></em></div></a>
    <a href="${ctx}/hslH5/line/lineOrderListPage?openid=${openid}" class="i03"><div><i></i>线路订单<em></em></div></a>
     <a href="${ctx}/lineSalesPlan/lspOrderList?openid=${openid}" class="i03"><div><i></i>线路活动订单<em></em></div></a>
	<a href="${ctx}/company/queryMembers" class="i04"><div><i></i>联系人<em></em></div></a> 
    <a href="${ctx }/company/couponQuery?isAvailable=true" class="i05"><div><i></i>我的卡券<em></em></div></a>
 	</section>
	<!--end 页面内容 -->
<script src="${ctx}/js/jquery-1.10.2.js"></script>
</body>
</html>