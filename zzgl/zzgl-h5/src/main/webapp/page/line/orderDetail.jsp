<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>订单详情</title>
	    <script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
	    <script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
	    <script src="${ctx}/resources/js/line_oder_detail.js" language="javascript"></script>
		<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/order_pay.css" rel="stylesheet" />
	</head>
	<body>
		<c:if test="${not empty lineOrder}">
		<div class="content">
		    <h1 class="trip_title">${lineOrder.lineSnapshot.line.baseInfo.name}</h1>
		    <div class="trip_date">
		        <span class="chooseDate"><i class="iconfont">&#xe676;</i>
		            出游日期：<fmt:formatDate value="${lineOrder.baseInfo.travelDate}" pattern="yyyy-MM-dd"/></span>
		        <span>
		            <i class="iconfont">&#xe614;</i>
		            <label>成人数：${lineOrder.baseInfo.adultNo}人</label>
		            <label>儿童数：${lineOrder.baseInfo.childNo}人</label>
		        </span>
		        <span>
		            <i class="iconfont">&#xe634;</i>
		            <label>成人价：￥${lineOrder.baseInfo.adultUnitPrice}</label>
		            <label>儿童价：￥${lineOrder.baseInfo.childUnitPrice}</label>
		        </span>
		    </div>
		    <div class="trip_date">
		        <span class="chooseDate">
		        	<i class="iconfont">&#xe724;</i>
		           	 订单编号：${lineOrder.baseInfo.dealerOrderNo}</span>
		        <span>
		            <i class="iconfont">&#xe6fb;</i>
	            	下单时间：<fmt:formatDate value="${lineOrder.baseInfo.createDate}" type="both" />
		        </span>
		    </div>
		    <div class="trip_member">
		        <div class="box title">
		            <h2>游玩人信息</h2>
		        </div>
		        <ul class="pList pl">
		        	<c:set var="index1" value="0" /> 
		        	<c:set var="index2" value="0" /> 
		            <c:forEach items="${lineOrder.travelerList}" var="traveler">
			            <c:if test="${traveler.type == 1}">
			              <c:set var="index1" value="${index1+1}" /> 
			              <li class="box" left="0" id="box">
				                <div class="left">
				                    <span class="type">
				                        <c:choose>
				                               <c:when test="${traveler.lineOrderStatus.orderStatus == 2001 && ((traveler.lineOrderStatus.payStatus == 2102) || (traveler.lineOrderStatus.payStatus == 2105))}">
											   		待定位
											   </c:when>
											   <c:when test="${traveler.lineOrderStatus.orderStatus == 2004}">
												   		已取消
											   </c:when>      
											   <c:otherwise> 
											        <c:if test="${traveler.lineOrderStatus.payStatus == 2101}">待支付定金</c:if>
								                    <c:if test="${traveler.lineOrderStatus.payStatus == 2103}">待支付尾款</c:if>
								                    <c:if test="${traveler.lineOrderStatus.payStatus == 2104}">已收到全款代付分销</c:if>
								                    <c:if test="${traveler.lineOrderStatus.payStatus == 2105}">交易成功</c:if>
								                    <c:if test="${traveler.lineOrderStatus.payStatus == 2106}">等待退款</c:if>
								                    <c:if test="${traveler.lineOrderStatus.payStatus == 2108}">退款成功</c:if>
											   </c:otherwise>  
				                        </c:choose>
				                    </span><input type="hidden" value="${traveler.lineOrderStatus.payStatus}" name="payStatus">
				                    <input type="hidden" value="${traveler.lineOrderStatus.orderStatus}" name="orderStatus">
				                    <span class="t">成人${index1}</span>
				                    <span>姓   名： ${traveler.name}</span>
				                    <span>手机号：${traveler.mobile}</span>
				                    <span>身份证：${traveler.idNo}</span>
				                </div>
				            </li>
			            </c:if>
		            </c:forEach>
		            
		              <c:forEach items="${lineOrder.travelerList}" var="traveler">
			                <c:if test="${traveler.type == 2}">
			              	<c:set var="index2" value="${index2+1}" />  
				              <li class="box" left="0" id="box">
					                <div class="left">
					                    <span class="type">
						                      <c:choose>
					                               <c:when test="${traveler.lineOrderStatus.orderStatus == 2001 && ((traveler.lineOrderStatus.payStatus == 2102) ||(traveler.lineOrderStatus.payStatus == 2105))}">  
												   		待定位
												   </c:when>
												   <c:when test="${traveler.lineOrderStatus.orderStatus == 2004}">
												   		已取消
												   </c:when>   
												   <c:otherwise> 
												        <c:if test="${traveler.lineOrderStatus.payStatus == 2101}">待支付定金</c:if>
									                    <c:if test="${traveler.lineOrderStatus.payStatus == 2103}">待支付尾款</c:if>
									                    <c:if test="${traveler.lineOrderStatus.payStatus == 2104}">已收到全款代付分销</c:if>
									                    <c:if test="${traveler.lineOrderStatus.payStatus == 2105}">交易成功</c:if>
									                    <c:if test="${traveler.lineOrderStatus.payStatus == 2106}">等待退款</c:if>
									                    <c:if test="${traveler.lineOrderStatus.payStatus == 2108}">退款成功</c:if>
												   </c:otherwise>  
				                        	</c:choose>
				                    	</span>
					                    <span class="t">儿童${index2}</span>
					                    <span>姓   名： ${traveler.name}</span>
					                    <span>手机号：${traveler.mobile}</span>
					                    <span>身份证：${traveler.idNo}</span>
					                </div>
				                </li>
			              </c:if>
		            </c:forEach>
		        </ul>
		    </div>
		    
		    <div class="order">
		        <form id="order">
			        <div class="box title">
			            <h2>联系人信息：</h2>
			        </div>
			        <div class="input"><i class="iconfont">&#xe613;</i><span>姓&nbsp;&nbsp;&nbsp;&nbsp;名:</span><label>${lineOrder.linkInfo.linkName}</label></div>
			        <div class="input"><i class="iconfont">&#xe60b;</i><span>手机号:</span><label>${lineOrder.linkInfo.linkMobile}</label></div>
			        <div class="input"><i class="iconfont">&#xe6c6;</i><span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱:</span><label>${lineOrder.linkInfo.email}</label></div>
	            </form>
		    </div>
		</div>
		
		<div class="order_modal" style="display: none;">
		    <div class="modal_cont">
		    <h2>订单明细<label id="modal_close"></label></h2>
		    <div class="de">
		        <span class="l">成人价：</span><label class="r">￥${lineOrder.baseInfo.adultUnitPrice} x ${lineOrder.baseInfo.adultNo}</label>
		    </div>
		    <div class="de">
		        <span class="l">儿童价：</span><label class="r">￥${lineOrder.baseInfo.childUnitPrice} x ${lineOrder.baseInfo.childNo}</label>
		    </div>
		    <div class="de">
		        <span class="l">优惠券减免：</span><label class="r">-￥${discountMoney}</label></div>
		    <div class="de">
		        <span class="l">预定金(${bookRate}%)：</span><label class="r">￥${bookMoney}</label></div>
		    <div class="de">
		        <span class="l">尾款(${retainageRate}%)：</span><label class="r">￥${retainageBfore}</label></div>
		        </div>
		</div>
		<div class="fixedBtn box">
		    <div class="leftBtn" id="order_detail">订单金额：${lineOrder.baseInfo.salePrice}元&nbsp;&nbsp;详情</div>
		    <c:if test="${lineStatus ==1}">
		        <c:if test="${lineOrder.travelerList[0].lineOrderStatus.payStatus == 2101}"><div class="next-btn">支付定金</div></c:if>
		        <c:if test="${lineOrder.travelerList[0].lineOrderStatus.payStatus == 2103}"><div class="next-btn">支付尾款</div></c:if>
		    </c:if>
		</div>
		
		<form action="${ctx}/hslH5/line/detailToPayPage" id="orderDetailForm"><input name="orderId" type="hidden" value="${lineOrder.id}"></form>
		</c:if>
		<!-- 消息提示 -->
		 <c:if test="${not empty message}">
				<input id="message" value="${message}" type="hidden">
		</c:if>
		<c:if test="${empty message}">
			<input id="message" value="1" type="hidden">
		</c:if>
		
	</body>
</html>
