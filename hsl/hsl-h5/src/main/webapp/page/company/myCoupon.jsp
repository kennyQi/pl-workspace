<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>我的卡券</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/kajuan.css"/>

<!-- javascript -->
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
</head>
<body class="personal">
<!-- 页头  -->
<header class="header">

  <h1>我的卡券</h1>
  <div class="left-head"> <a id="goBack" href="${ctx }/user/center" class="tc_back head-btn"> <span class="inset_shadow"> <span class="head-return"></span> </span> </a> </div>
</header>
<!-- 页头 -->

<section class="kajuan_nav">
    <a href="${ctx }/company/couponQuery?isAvailable=true" class="<c:if test="${index == 0}">sel </c:if>k_icon" id="k01">
        <div><i></i>可用代金券</div>
    </a>
    <a href="${ctx }/company/couponQuery?isAvailable=false" class="<c:if test="${index == 1}">sel </c:if>k_icon" id="k02">
        <div><i></i>不可用代金券</div>
    </a>
</section>
<section class="kajuan_info">
    <c:if test="${index==0}">

        <div id="kinfo01">
            <ul>
                <c:forEach items="${couponQueryResult.couponDTOList }" var="coupon">
                    <li>
                        <a href="${ctx }/company/couponDetail?id=${coupon.id}">
                            <div class="ka_left"></div>
                            <div class="kalist">
                                <div class="ka_title">
                                    <span class="ka_price_t">优惠券</span>
                                    <span class="ka_price">满<fmt:parseNumber type="number" value="${coupon.baseInfo.couponActivity.useConditionInfo.minOrderPrice }"/>
                                        减<fmt:parseNumber type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue }"/></span>
                                </div>
                                <div class="ka_info">
                                    <span class="ka_data"><i></i>有效期</span>
                                    <span class="ka_time"><fmt:formatDate value="${coupon.baseInfo.couponActivity.useConditionInfo.endDate}" pattern="yyyy-MM-dd"/></span>
                                </div>
                            </div>
                            <div class="ka_right"></div>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <c:if test="${index==1}">
        <div id="kinfo02">
            <ul>
                <c:forEach items="${couponQueryResult.couponDTOList }" var="coupon">
                <li>
                    <a href="${ctx }/company/couponDetail?id=${coupon.id}">
                        <div class="ka_left"></div>
                        <div class="kalist kalistgq">
                            <div class="ka_title">
                                <span class="ka_price_t">优惠券</span>
                                <span class="ka_price">满<fmt:parseNumber type="number"
                                                                         value="${coupon.baseInfo.couponActivity.useConditionInfo.minOrderPrice }"/>
                                    减<fmt:parseNumber type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue }"/></span>
                            </div>
                            <div class="ka_info">
                                <span class="ka_data"><i></i>有效期</span>
                                <span class="ka_time"><fmt:formatDate value="${coupon.baseInfo.couponActivity.useConditionInfo.endDate}" pattern="yyyy-MM-dd"/></span>
                                <c:if test="${coupon.status.currentValue == 2}">
                                    <i class="kalistzf_icon"></i>
                                </c:if>
                                <c:if test="${coupon.status.currentValue == 3}">
                                    <i class="kalistgq_icon"></i>
                                </c:if>
                                <c:if test="${coupon.status.currentValue == 4}">
                                    <i class="kalistsy_icon"></i>
                                </c:if>
                            </div>
                            <div class="ka_right"></div>
                        </div>
                        </c:forEach>
            </ul>
        </div>
    </c:if>
</section>
</body>
</html>