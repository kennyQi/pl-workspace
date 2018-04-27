 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>我的卡劵</title>
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
  <h1>我的卡劵</h1>
  <div class="left-head"> <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn"> <span class="inset_shadow"> <span class="head-return"></span> </span> </a> </div>
  <span id="kajuan_secc"><a href="#">完成</a></span>
</header>
<!-- 页头 -->

 
<section class="kajuan_info pt20">
<c:if  test="${not empty couponList}">
 	<div>
		  <ul>
      	  <c:forEach  items="${couponList}"  var="coupon">     
			    <li>
			    <div class="ka_left"></div>    
			      <div class="kalist pl40">			      
				        <div class="ka_title">
				        <span class="ka_price_t"><fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元优惠券</span>
			           <c:if test="${coupon.baseInfo.couponActivity.useConditionInfo.condition == 1}">
				             <span class="ka_price">满<fmt:parseNumber type="number" value="${coupon.baseInfo.couponActivity.useConditionInfo.minOrderPrice}"/>减<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元</span> 
				           </c:if>  
				        </div>
				        <div class="ka_info">
				            <span class="ka_data"><i></i>有效期</span>      				      				          
				             <span class="ka_time"><fmt:formatDate  value="${coupon.baseInfo.couponActivity.useConditionInfo.endDate}"  pattern="yyyy-MM-dd"/></span>
				        </div>
				        <div class="ka_sel">
				        	<i></i>
				        	<input type="radio"  name="ka_radio" class="kj"  value="<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>" data-id="${coupon.id}" >
				        </div>
			       </div>
			       <div class="ka_right"></div>
			    </li>
	      </c:forEach>
	  	  </ul>
	 </div>
</c:if>
</section>
<script type="text/javascript">
 $(".kajuan_info ul li").on("click",function(){
	 $(this).addClass("hover").siblings().removeClass("hover");
	 console.info($("input[name=ka_radio]"));
	 $("input[name=ka_radio]").attr("checked",false); 
	 $($(this).find('.kj')[0]).attr("checked",true);
})

 //选择完卡券之后，点击完成回到订单支付页面
 $("#kajuan_secc").on("click",function(){
	 var youh =$("input[name=ka_radio]:radio:checked").val();
	 var couponId=$("input[name=ka_radio]:radio:checked").attr("data-id");
	 if(youh!=null && couponId!=null){//非空判断
		 window.location.href = "${ctx}/jpo/pay?openid=${openid}&orderId=${order.dealerOrderCode}&youh="+youh+"&id="+couponId;
	 }else{
		 window.location.href ="${ctx}/jpo/pay?openid=${openid}&orderId=${order.dealerOrderCode}";
	 }
 })
</script>
</body>
</html>