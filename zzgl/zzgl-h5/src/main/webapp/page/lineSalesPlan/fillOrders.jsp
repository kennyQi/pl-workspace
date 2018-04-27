<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>选择游玩人</title>
	    <script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
	    <script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
	    <script src="${ctx}/resources/js/fillOrders.js" language="javascript"></script>
		<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/order_pay.css" rel="stylesheet" />
	</head>
	<body>
		<div class="content">
		    <h1 class="trip_title">${planDto.line.baseInfo.name}</h1>
		    <div class="trip_date">
		        <span class="chooseDate"><i class="iconfont">&#xf0092;</i>
		        出游日期：<fmt:formatDate value="${planDto.lineSalesPlanSalesInfo.travelDate}" pattern="yyyy-MM-dd"/></span>
		        <span>
		            <i class="iconfont">&#xe613;</i>
		            <label>成人数：  ${lspOrderCommand.adultNo}/${lspOrderCommand.lastNum}人</label>
		            
		        </span>
		        <span>
		            <i class="iconfont">&#xe634;</i>
		            <label>成人价：￥${planDto.lineSalesPlanSalesInfo.planPrice}</label>
		        </span>
		    </div>
		    <div class="trip_member">
		    	<form action="${ctx}/lineSalesPlan/addTravelerPage" id="addPersonForm">
		    		<div class="box title">
			            <h2>游玩人添加</h2>
			            <i class="iconfont">&#xf014d;</i>
			        </div>
			       
			        <div id="addpersonHiddenValue" style="display:none;"></div>
		    	</form>
		        <ul class="pList pl">
		        	<c:set var="index1" value="0" /> 
		        	<c:set var="index2" value="0" /> 
			        <c:forEach items="${travelers}" var="traveler" varStatus="num1">
			        	<c:if test="${traveler.type == 1}">
			        		<c:set var="index1" value="${index1+1}" /> 
				        	<li class="box" left="0" id="box">
				                <div class="left">
				                    <span class="t">成人${index1}</span>
				                    <span>姓   名： ${traveler.name}</span>
				                    <span>手机号：${traveler.mobile}</span>
				                    <span>身份证：${traveler.cardNo}</span>
				                </div>
				            </li>
			            </c:if>
			        </c:forEach>
		        </ul>
		    </div>
		    <form action="${ctx}/lineSalesPlan/createLSPOrder" id="createOrderForm">
			    <div class="order">
			        <div class="box title">
			            <h2>联系人信息：</h2>
			            <i class="iconfont" id="reset" >&#xf0059;</i>
			        </div>
			        <div class="input"><i class="iconfont">&#xe614;</i><span>姓&nbsp;&nbsp;&nbsp;&nbsp;名:</span>
			        <input type="text" placeholder="必填 姓名" name="linkName" value="${lspOrderCommand.linkName}"/>
			        </div>
			        <div class="input"><i class="iconfont">&#xe60b;</i><span>手机号:</span>
			        <input type="text" placeholder="必填 用于接收短信" name="linkMobile" value="${lspOrderCommand.linkMobile}"/>
			        </div>
			        <div class="input"><i class="iconfont">&#xe6c6;</i><span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱:</span>
			        <input type="text" placeholder="必填 用于接收协议" name="email" value="${lspOrderCommand.email}"/>
			        </div>
			    </div>
			    <!--隐藏值区域  -->
				<input value="${lspOrderCommand.lspId}" name="lspId" type="hidden">
				<input value="${lspOrderCommand.lastNum}" name="lastNum" type="hidden">
				<input value="${lspOrderCommand.adultNo}" name="adultNo" type="hidden">
				<input value="${lspOrderCommand.salePrice}" name="salePrice" type="hidden">
				<c:forEach items="${lspOrderCommand.travelerIds}" var="travelerId">
					<input value="${travelerId}" name="travelerIds" type="hidden">
				</c:forEach>
				
				<!-- 防止重复提交 -->
				<input type="hidden" id="SPRING_TOKEN" name="SPRING_TOKEN" value="${SPRING_TOKEN}"/>
		    </form>
		    <c:if test="${not empty message}">
				<input id="message" value="${message}" type="hidden">
			</c:if>
			<c:if test="${empty message}">
				<input id="message" value="1" type="hidden">
			</c:if>
		</div>
		<div class="fixedBtn box">
		    <div class="leftBtn" id="order_detail">订单金额：
		    <c:if test="${!empty lspOrderCommand.salePrice}">
		    ${lspOrderCommand.salePrice}
		    </c:if>
		    
		    <c:if test="${empty lspOrderCommand.salePrice}">
		   0.0
		    </c:if>
		    
		    元&nbsp;&nbsp;</div>
		    <div class="next-btn">提交订单</div>
		</div>
	</body>
</html>
