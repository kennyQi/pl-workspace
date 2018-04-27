<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>选择游玩人</title>
	    <script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
	    <script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
	    <script src="${ctx}/resources/js/order_pay.js" language="javascript"></script>
		<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/order_pay.css" rel="stylesheet" />
	</head>
	<body>
		<div class="content">
		    <h1 class="trip_title">${lineDto.baseInfo.name}</h1>
		    <div class="trip_date">
		        <span class="chooseDate"><i class="iconfont">&#xf0092;</i>出游日期：<fmt:formatDate value="${lineOrderCommand.travelDate}" pattern="yyyy-MM-dd"/></span>
		        <span>
		            <i class="iconfont">&#xe613;</i>
		            <label>成人数： ${lineOrderCommand.adultNo}人</label>
		            <label>儿童数：${lineOrderCommand.childNo}人</label>
		        </span>
		        <span>
		            <i class="iconfont">&#xe634;</i>
		            <label>成人价：￥${lineSalePrice.adultPrice}</label>
		            <label>儿童价：￥${lineSalePrice.childPrice}</label>
		        </span>
		    </div>
		    <div class="trip_member">
		    	<form action="${ctx}/hslH5/line/addPersonPage" id="addPersonForm">
		    		<div class="box title">
			            <h2>游玩人添加</h2>
			            <i class="iconfont">&#xf014d;</i>
			        </div>
			        <div id="addpersonHiddenValue" style="display:none;"></div>
			        <input value="${lineOrderCommand.token}" name="token" type="hidden">
		    	</form>
		        <ul class="pList pl">
		        	<c:set var="index1" value="0" /> 
		        	<c:set var="index2" value="0" /> 
			        <c:forEach items="${travelers}" var="traveler" varStatus="num1">
			        	<c:if test="${traveler.baseInfo.type == 1}">
			        		<c:set var="index1" value="${index1+1}" /> 
				        	<li class="box" left="0" id="box">
				                <div class="left">
				                    <span class="t">成人${index1}</span>
				                    <span>姓   名： ${traveler.baseInfo.name}</span>
				                    <span>手机号：${traveler.baseInfo.mobile}</span>
				                    <span>身份证：${traveler.baseInfo.idNo}</span>
				                </div>
				            </li>
			            </c:if>
			        </c:forEach>
			        <c:forEach items="${travelers}" var="traveler" varStatus="num2">
			        	<c:if test="${traveler.baseInfo.type == 2}">
			        		<c:set var="index2" value="${index2+1}" /> 
				        	<li class="box" left="0" id="box">
				                <div class="left">
				                    <span class="t"> 儿童${index2}</span>
				                    <span>姓   名： ${traveler.baseInfo.name}</span>
				                    <span>手机号：${traveler.baseInfo.mobile}</span>
				                    <span>身份证：${traveler.baseInfo.idNo}</span>
				                </div>
				            </li>
			            </c:if>
			        </c:forEach>
		        </ul>
		    </div>
		    <form action="${ctx}/hslH5/line/creatLineOrder" id="createOrderForm">
			    <div class="order">
			        <div class="box title">
			            <h2>联系人信息：</h2>
			            <i class="iconfont" id="reset" >&#xf0059;</i>
			        </div>
			        <div class="input"><i class="iconfont">&#xe614;</i><span>姓&nbsp;&nbsp;&nbsp;&nbsp;名:</span><input type="text" placeholder="必填 姓名" name="linkName" value="${lineOrderCommand.linkName}"/></div>
			        <div class="input"><i class="iconfont">&#xe60b;</i><span>手机号:</span><input type="text" placeholder="必填 用于接收短信" name="linkMobile" value="${lineOrderCommand.linkMobile}"/></div>
			        <div class="input"><i class="iconfont">&#xe6c6;</i><span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱:</span><input type="text" placeholder="必填 用于接收协议" name="linkEmail" value="${lineOrderCommand.linkEmail}"/></div>
			    </div>
			    <!--隐藏值区域  -->
				<input value="${lineOrderCommand.userId}" name="userId" type="hidden">
				<input value="${lineOrderCommand.lineId}" name="lineId" type="hidden">
				<input value="<fmt:formatDate value="${lineOrderCommand.travelDate}" pattern="yyyy-MM-dd"/>" name="travelDate" type="hidden">
				<input value="${lineOrderCommand.adultNo}" name="adultNo" type="hidden">
				<input value="${lineOrderCommand.childNo}" name="childNo" type="hidden">
				<input value="${lineOrderCommand.token}" name="token" type="hidden">
				<c:forEach items="${lineOrderCommand.travelerIds}" var="travelerId">
					<input value="${travelerId}" name="travelerIds" type="hidden">
				</c:forEach>
		    </form>
		    <c:if test="${not empty message}">
				<input id="message" value="${message}" type="hidden">
			</c:if>
			<c:if test="${empty message}">
				<input id="message" value="1" type="hidden">
			</c:if>
		</div>
		<!--订单详情-->
		<div class="order_modal" style="display: none;">
		    <div class="modal_cont">
			    <h2>订单明细<label id="modal_close"></label></h2>
			    <div class="de">
			        <span class="l">成人价：</span><label class="r">￥${lineSalePrice.adultPrice} x ${lineOrderCommand.adultNo}</label>
			    </div>
			    <div class="de">
			        <span class="l">儿童价：</span><label class="r">￥${lineSalePrice.childPrice} x ${lineOrderCommand.childNo}</label>
			    </div>
			    <div class="de">
			        <span class="l">预定金(${bookRate}%)：</span><label class="r">￥${bookMoney}</label>
		        </div>
			    <div class="de">
			        <span class="l">尾款(${retainageRate}%)：</span><label class="r">￥${retainageMoney}</label>
		        </div>
	        </div>
		</div>
		<div class="fixedBtn box">
		    <div class="leftBtn" id="order_detail">订单金额：￥${totalMoney}&nbsp;&nbsp;详情<i class="iconfont2">&#xe616;</i></div>
		    <div class="next-btn">提交订单</div>
		</div>
	</body>
</html>
