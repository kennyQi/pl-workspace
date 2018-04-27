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
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/ticket.css">
</head>
<body class="orderDetails">
	<!--start 公共页头  -->
	<header class="header">
	  <h1>订单详情</h1>

	  <div class="left-head"> 
	    <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	</header>
	<!--end 公共页头  -->
	<!-- 页面内容 -->
	<div class ="content">
		<h3>订单状态：<span>
			<c:if test="${order.status.cancel }">已取消</c:if>
			<c:if test="${order.status.outOfDate }">已过期</c:if>
			<c:if test="${order.status.prepared }">待游玩</c:if>
			<c:if test="${order.status.used }">已游玩</c:if>
		</span></h3>
		<div class="detail">
			<dl>
            	<dt><img src="${order.scenicSpot.scenicSpotBaseInfo.image }"></dt>
                <dd><span>数量： ${order.number }</span>${order.scenicSpot.scenicSpotBaseInfo.name }</dd>
                <dd>${order.scenicSpot.scenicSpotGeographyInfo.address }</dd>
                <dd>${order.scenicSpot.scenicSpotBaseInfo.intro }</dd>
            </dl>
		</div>
		<div class="time">
			<p>订单号：<span>${order.dealerOrderCode }</span></p>
			<p>支付方式：<span>景区现付</span></p>
			<p>支付金额：<span>&yen;<fmt:formatNumber value="${order.price }" pattern="0.00" /></span></p>
			<p>下单时间：<span>${orderDate }</span></p>
			<p>游玩时间：<span>${order.travelDate }</span></p>
		</div>
		<div class="people">
			<p>联系人：<span>${order.takeTicketUser.name }</span></p>
			<p>手机号：<span>${order.takeTicketUser.mobile }</span></p>
		</div>
		<div class="notes">
			<h3>订票须知</h3>
            <div class="info_notes">
            	<p>
            		<c:forEach var="noticeType" items="${order.mpPolicy.noticeTypes }">
            			<div style="font-size: 15px; font-weight: bold;">${noticeType.typeName }</div>
            			<c:forEach var="noticeItem" items="${noticeType.noticeItems }">
            				<span style="font-weight: bold;">${noticeItem.name }:</span>${noticeItem.content }<br>
            			</c:forEach>
            		</c:forEach>
            	</p>
            </div>
		</div>
	</div>


</body>
</html>
