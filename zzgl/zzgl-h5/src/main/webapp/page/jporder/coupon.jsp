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
		  <!-- 
		  		t1:只能单选（代金券不能和现金券叠加使用或者只能单独使用的现金券）
		  		t2:能和t4一起选（代金券）
		  		t3:能和t4,t3一起选（现金券不能和代金券叠加）
		  		t4:能和t2,t3,t4一起选（现金券）
		   -->
      	  <c:forEach  items="${couponList}"  var="coupon">     
      	  		<c:choose>
	      	  		<c:when test="${coupon.baseInfo.couponActivity.useConditionInfo.isShareNotSameType == false
	      	  		 && coupon.baseInfo.couponActivity.baseInfo.couponType==1}">
				    <li type="t1">
				    <div class="ka_left"></div>    
				      <div class="kalist pl40">			      
					        <div class="ka_title">
					        <span class="ka_price_t"><fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元代金券</span>
				           <span class="ka_price">(不能和现金券叠加使用)
				           <c:if test="${coupon.baseInfo.couponActivity.useConditionInfo.condition == 1}">
					             满<fmt:parseNumber type="number" value="${coupon.baseInfo.couponActivity.useConditionInfo.minOrderPrice}"/>减<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元 
					           </c:if>  
					           </span>
					        </div>
					        <div class="ka_info">
					            <span class="ka_data"><i></i>有效期</span>      				      				          
					             <span class="ka_time"><fmt:formatDate  value="${coupon.baseInfo.couponActivity.useConditionInfo.endDate}"  pattern="yyyy-MM-dd"/></span>
					        </div>
					        <div class="ka_sel">
					        	<i></i>
					        	<input type="checkbox"  name="ka_radio" class="kj"  value="<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>" data-id="${coupon.id}" >
					        </div>
				       </div>
				       <div class="ka_right"></div>
				    </li>
				    </c:when>
				    <c:when test="${coupon.baseInfo.couponActivity.useConditionInfo.isShareNotSameType == false
	      	  		 && coupon.baseInfo.couponActivity.baseInfo.couponType==2 && coupon.baseInfo.couponActivity.useConditionInfo.isShareSameKind == false}">
				    <li type="t1">
				    <div class="ka_left"></div>    
				      <div class="kalist pl40">			      
					        <div class="ka_title">
					        <span class="ka_price_t"><fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元现金券</span>
				           <span class="ka_price">(不能和现金券叠加使用)
				           <c:if test="${coupon.baseInfo.couponActivity.useConditionInfo.condition == 1}">
					             满<fmt:parseNumber type="number" value="${coupon.baseInfo.couponActivity.useConditionInfo.minOrderPrice}"/>减<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元 
					           </c:if>  
					           </span>
					        </div>
					        <div class="ka_info">
					            <span class="ka_data"><i></i>有效期</span>      				      				          
					             <span class="ka_time"><fmt:formatDate  value="${coupon.baseInfo.couponActivity.useConditionInfo.endDate}"  pattern="yyyy-MM-dd"/></span>
					        </div>
					        <div class="ka_sel">
					        	<i></i>
					        	<input type="checkbox"  name="ka_radio" class="kj"  value="<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>" data-id="${coupon.id}" >
					        </div>
				       </div>
				       <div class="ka_right"></div>
				    </li>
				    </c:when>
				    <c:when test="${coupon.baseInfo.couponActivity.useConditionInfo.isShareNotSameType == true
	      	  		 && coupon.baseInfo.couponActivity.baseInfo.couponType==1}">
				    <li type="t2">
				    <div class="ka_left"></div>    
				      <div class="kalist pl40">			      
					        <div class="ka_title">
					        <span class="ka_price_t"><fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元代金券</span>
				           	
				           <c:if test="${coupon.baseInfo.couponActivity.useConditionInfo.condition == 1}">
					             <span class="ka_price">满<fmt:parseNumber type="number" value="${coupon.baseInfo.couponActivity.useConditionInfo.minOrderPrice}"/>减<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元</span> 
					           </c:if>  
					           </span>
					        </div>
					        <div class="ka_info">
					            <span class="ka_data"><i></i>有效期</span>      				      				          
					             <span class="ka_time"><fmt:formatDate  value="${coupon.baseInfo.couponActivity.useConditionInfo.endDate}"  pattern="yyyy-MM-dd"/></span>
					        </div>
					        <div class="ka_sel">
					        	<i></i>
					        	<input type="checkbox"  name="ka_radio" class="kj"  value="<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>" data-id="${coupon.id}" >
					        </div>
				       </div>
				       <div class="ka_right"></div>
				    </li>
				    </c:when>
				    <c:when test="${coupon.baseInfo.couponActivity.useConditionInfo.isShareNotSameType == false
	      	  		 && coupon.baseInfo.couponActivity.baseInfo.couponType==2}">
				    <li type="t3">
				    <div class="ka_left"></div>    
				      <div class="kalist pl40">			      
					        <div class="ka_title">
					        <span class="ka_price_t"><fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元现金券</span>
				           	<span class="ka_price">(不能和代金券叠加使用)
				           <c:if test="${coupon.baseInfo.couponActivity.useConditionInfo.condition == 1}">
					             满<fmt:parseNumber type="number" value="${coupon.baseInfo.couponActivity.useConditionInfo.minOrderPrice}"/>减<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元
					           </c:if>  
					           </span>
					        </div>
					        <div class="ka_info">
					            <span class="ka_data"><i></i>有效期</span>      				      				          
					             <span class="ka_time"><fmt:formatDate  value="${coupon.baseInfo.couponActivity.useConditionInfo.endDate}"  pattern="yyyy-MM-dd"/></span>
					        </div>
					        <div class="ka_sel">
					        	<i></i>
					        	<input type="checkbox"  name="ka_radio" class="kj"  value="<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>" data-id="${coupon.id}" >
					        </div>
				       </div>
				       <div class="ka_right"></div>
				    </li>
				    </c:when>
				    <c:when test="${coupon.baseInfo.couponActivity.baseInfo.couponType==2}">
				    <li type="t4">
				    <div class="ka_left"></div>    
				      <div class="kalist pl40">			      
					        <div class="ka_title">
					        <span class="ka_price_t"><fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元现金券
				           <c:if test="${coupon.baseInfo.couponActivity.useConditionInfo.condition == 1}">
					             	<span class="ka_price">满<fmt:parseNumber type="number" value="${coupon.baseInfo.couponActivity.useConditionInfo.minOrderPrice}"/>减<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>元 </span>
					           </c:if>  
					         </span>
					        </div>
					        <div class="ka_info">
					            <span class="ka_data"><i></i>有效期</span>      				      				          
					             <span class="ka_time"><fmt:formatDate  value="${coupon.baseInfo.couponActivity.useConditionInfo.endDate}"  pattern="yyyy-MM-dd"/></span>
					        </div>
					        <div class="ka_sel">
					        	<i></i>
					        	<input type="checkbox"  name="ka_radio" class="kj"  value="<fmt:parseNumber  type="number" value="${coupon.baseInfo.couponActivity.baseInfo.faceValue}"/>" data-id="${coupon.id}" >
					        </div>
				       </div>
				       <div class="ka_right"></div>
				    </li>
				    </c:when>
				   
			    </c:choose> 
	      </c:forEach>
	  	  </ul>
	 </div>
</c:if>
</section>
<script type="text/javascript">
/* 
	t1：只能单选（代金券不能和现金券叠加使用）
	t2:能和t4一起选（代金券）
	t3:能和t4,t3一起选（现金券不能和代金券叠加）
	t4:能和t2,t3,t4一起选（现金券）
*/
 $(".kajuan_info ul li").on("click",function(){
	 var type=$(this).attr("type");
	 if($(this).hasClass("hover")){
		 $(this).removeClass("hover").find('.kj').prop("checked",false);
	 }else{
		 if(type=="t1"){
			 $("input[name=ka_radio]").prop("checked",false);
			 $(this).find('.kj').prop("checked",true);
			 $(this).addClass("hover").siblings().removeClass("hover");
		 }else if(type=="t2"){
			
			 $(".kajuan_info ul li[type^='t4']").removeClass("hover").find('.kj').prop("checked",false);
		 }else if(type=="t3"){
			 $(this).addClass("hover").find('.kj').prop("checked",true);
			 $(".kajuan_info ul li[type='t1'],.kajuan_info ul li[type='t2']").removeClass("hover").find('.kj').prop("checked",false);
		 }else if(type=="t4"){
			 $(this).addClass("hover").find('.kj').prop("checked",true);
			 $(".kajuan_info ul li[type='t1']").removeClass("hover").find('.kj').prop("checked",false);
		 }
	 }
	 
	 
	 //$(this).addClass("hover").siblings().removeClass("hover");
	 //$("input[name=ka_radio]").attr("checked",false); 
	 //$(this).find('.kj')[0].attr("checked",true);
})

 //选择完卡券之后，点击完成回到订单支付页面
 $("#kajuan_secc").on("click",function(){
	 
	 var youh =0;
	 var couponId="";
	 var offPrice=0;
	 for(var i=0;i<$("input[name=ka_radio]:checked").length;i++){
		 youh+=parseFloat($("input[name=ka_radio]:checked").eq(i).val());
		 couponId+=","+$("input[name=ka_radio]:checked").eq(i).attr("data-id");
	 }
	 offPrice=(${order.flightPriceInfo.payAmount}-youh)>=0?youh:${order.flightPriceInfo.payAmount};
	 couponId=couponId.slice(1);
	 if(youh!=null && couponId!=null){//非空判断
		 window.location.href = "${ctx}/jpo/pay?openid=${openid}&orderId=${order.id}&youh="+youh+"&id="+couponId+"&offPrice="+offPrice;
	 }else{
		 window.location.href ="${ctx}/jpo/pay?openid=${openid}&orderId=${order.id}";
	 }
 });
</script>
</body>
</html>