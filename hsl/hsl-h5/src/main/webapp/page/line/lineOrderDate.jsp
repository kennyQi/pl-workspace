<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>选择日期</title>
		<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/order_date.css" rel="stylesheet" />
		
		<script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
		<script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
		<script src="${ctx}/resources/js/createDate.js" language="javascript"></script>
	</head>
	
	<body>
	    <div class="body dateMobile" id="body"></div>
	
		<!--后台传各日期的价格数据-->
		<select id="dateMoney" style="display:none;">
			<c:forEach items="${datePrices}" var="datePrice">
			   <option value="<fmt:formatDate value="${datePrice.saleDate}" pattern="yyyy-MM-dd"/>" for="${datePrice.childPrice}">¥${datePrice.adultPrice}</option>
			</c:forEach>
		</select>
		
		<!--表单区域-->
		<form action="${ctx}/hslH5/line/lineOrderPage" id="lineOrderDateForm">
		    <span><input name="lineId" value="${lineId}"><input value="${lineOrderCommand.token}" name="token" type="hidden"></span>
			<span id="travelDate"></span>
			<span id="adultNo"></span>
			<span id="childNo"></span>
		</form>
		
		<!--展示区域-->
		<div class="numBox">
		    <div class="person" id="adultPrice">
		        <div class="numChange">
		                        成人：
		            <div class="tool">
		                <span class="left">-</span><span class="num" for="adult">1</span><span class="right">+</span>
		            </div>
		        </div>
		        <span class="price">成人价：0</span>
		    </div>
		    <div class="person" id="childrenPrice">
		        <div class="numChange">
		                        儿童：
		            <div class="tool">
		                <span class="left">-</span><span class="num" for="children">0</span><span class="right">+</span>
		            </div>
		        </div>
		        <span class="price">儿童价：0</span>
		    </div>
		</div>
		<div class="fixedBtn box">
		    <div class="leftBtn">
		        <span>
		            	成人：<label id="adult">1</label>&nbsp;&nbsp;&nbsp;儿童：<label id="children">0</label>
		        </span>
		        <span>
		            	游玩日期：<label id="chooseDay"></label>
		        </span>
		    </div>
		    <div class="next-btn">下一步</div>
		</div>
	
	</body>
</html>
