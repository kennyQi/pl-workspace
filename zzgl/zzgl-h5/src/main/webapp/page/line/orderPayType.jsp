<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
	<title>支付方式</title>
	<!--公共样式部分-->
	<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
	<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
	<link href="${ctx}/resources/css/order_success.css" rel="stylesheet" />
	<script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
	<script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
	<script src="${ctx}/resources/js/orderPayType.js" language="javascript"></script>
</head>
<body>
<input type="hidden" name="contextPath" id="contextPath" value="${ctx}"/>


<h2 class="title">${lineDto.baseInfo.name}</h2>
<!--选择卡券表单  -->
<form action="${ctx}/hslH5/line/userCouponList" id="selectCouponForm"><div id="hiddenValue" style="display:none;"></div></form>
<!--支付提交表单  -->
<form action="${ctx}/hslH5/line/payLineOrder" id="payLineOrderForm">
	<ul class="total">
		<!--使用优惠券后-->
		<li>
			<i class="iconfont">&#xe62b;</i>订单金额
			<c:if test="${status == 3}">
				<label class="total_price use_youhui">￥${totalMoney}</label>
				<label class="off_price">￥${discountTotalMoney}</label>
			</c:if>
			<c:if test="${status != 3}">
				<label class="deposit">￥${totalMoney}</label>
			</c:if>
		</li>
		<c:if test="${status == 1}">
			<li>
				<i class="iconfont">&#xe661;</i>预定金(${bookRate}%):
				<c:if test="${not empty couponId}">
					<label class="total_price use_youhui">￥${bookMoney}</label>
					<label class="off_price">￥${discountBookMoney}</label>
				</c:if>
				<c:if test="${empty couponId}">
					<label>￥${bookMoney}</label>
				</c:if>
			</li>
		</c:if>
		<c:if test="${status == 2}">
			<li>
				<i class="iconfont">&#xe630;</i>尾款(${retainageRate}%):
				<c:if test="${not empty couponId}">
					<label class="total_price use_youhui">￥${retainageBfore}</label>
					<label class="off_price">￥${retainageMoney}</label>
				</c:if>

				<c:if test="${empty couponId}">
					<label>￥${retainageMoney}</label>
				</c:if>
			</li>
		</c:if>
	</ul>
	<div class="input payOff" id="sels">
		<i class="iconfont">&#xe625;</i>
		<span>优惠券:</span>
		<div class="pay_money">
			<c:if test="${empty discount}">不使用优惠</c:if>
			<c:if test="${not empty discount}">
				<c:if test="${discount ==0}">不使用优惠</c:if>
				<c:if test="${discount !=0}">已使用${discount}元优惠</c:if>
			</c:if>
		</div>
		<i class="iconfont r" id="selectCoupon">&#xe6f1;</i>
	</div>

	<div class="input payOff balance">


		<label class="ui-radio">
			<input type="checkbox">
			使用余额(余额:<b id="balance">0.0</b>元)
		</label>
		<label class="h-user-moeny">0.00</label>
	</div>

	<div class="tips">如未达到本团最低成团人数，旅行社将根据实际情况在出发前3天通
		知您不成团，并为您推荐其他出发班期或更换其他同类产品，如您不
		接受上述方案，我们将全额退还您支付的费用。由此给您造成的不便，
		尽请谅解！
	</div>
	<c:if test="${type == 3 || type == 4 || type == 5}">
		<a href="javascript:" class="order_btn alipay" id="alipay" onclick="submitOrdersForm();"></a>
		<input value="2" name="payType" type="hidden">
	</c:if>
	<c:if test="${type == 2}">
		<a href="javascript:" class="order_btn submit" onclick="submitOrdersForm();">确认支付</a>
		<input value="99" name="payType" type="hidden" >
	</c:if>

	<!--隐藏值区域  -->
	<c:if test="${not empty couponId}">
		<input  type="hidden"  id="couponId"  value="${couponId}" name="couponIds">
	</c:if>
	<input value="0.0" id="balanceMoney" name="balanceMoney" type="hidden">
	<input value="${lineOrderCommand.userId}" name="userId" type="hidden">
	<input value="${lineOrderCommand.lineId}" name="lineId" type="hidden">
	<input value="${lineOrderId}" name="lineOrderId" type="hidden">
	<input value="<fmt:formatDate value="${lineOrderCommand.travelDate}" pattern="yyyy-MM-dd"/>" name="travelDate" type="hidden">
	<input value="${lineOrderCommand.adultNo}" name="adultNo" type="hidden">
	<input value="${lineOrderCommand.childNo}" name="childNo" type="hidden">
	<!-- 防止重复提交 -->
	<input type="hidden" id="SPRING_TOKEN" name="SPRING_TOKEN" value="${SPRING_TOKEN}"/>
</form>

<!--跳转到支付成功页面  -->
<form action="${ctx}/hslH5/line/success" id="successForm"><input value="${dealerOrderNo}" name="dealerOrderNo" type="hidden"></form>


<!-- 消息提示 -->
<c:if test="${not empty message}">
	<input id="message" value="${message}" type="hidden">
</c:if>
<c:if test="${empty message}">
	<input id="message" value="1" type="hidden">
</c:if>

</body>
</html>
