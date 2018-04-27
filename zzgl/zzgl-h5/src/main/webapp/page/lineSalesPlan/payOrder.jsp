<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
	<title>订单支付</title>
	<!--公共样式部分-->
	<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
	<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
	<link href="${ctx}/resources/css/order_success.css" rel="stylesheet" />
	<script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
	<script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
</head>
<body>
<c:if test="${not empty orderDto}">
<h2 class="title">${orderDto.lineSalesPlan.baseInfo.planName}</h2>
<input type="hidden" name="contextPath" id="contextPath" value="${ctx}"/>
<!--支付提交表单  -->
<form action="${ctx}/lineSalesPlan/payLSPOrder" id="payLineOrderForm">
	<input value="${orderDto.id}" name="lspId" type="hidden">
	<ul class="total">
		<!--使用优惠券后-->
		<li>
			<i class="iconfont">&#xe62b;</i>订单金额
			<label class="deposit">￥${orderDto.orderBaseInfo.salePrice}</label>
		</li>
		
	</ul>
	<div class="tips">如未达到本团最低成团人数，旅行社将根据实际情况在出发前3天通
		知您不成团，并为您推荐其他出发班期或更换其他同类产品，如您不
		接受上述方案，我们将全额退还您支付的费用。由此给您造成的不便，
		尽请谅解！
	</div>
	<a href="javascript:" class="order_btn alipay" id="alipay" onclick="submitOrdersForm();"></a>

	<!-- 防止重复提交 -->
	<input type="hidden" id="SPRING_TOKEN" name="SPRING_TOKEN" value="${SPRING_TOKEN}"/>
</form>
</c:if>
<c:if test="${not empty message}">
	<input id="message" value="${message}" type="hidden">
</c:if>
<c:if test="${empty message}">
	<input id="message" value="1" type="hidden" />
</c:if>
<!--跳转到支付成功页面  -->
<form action="${ctx}/lineSalesPlan/paySuccess" id="successForm">
<input type="hidden" value="${orderDto.id}" name="id" type="id" />
</form>
</body>
</html>
<script type="text/javascript">
<!--
	$(function() {
		var message = $("#message").val();
		if(message != "1"){
			showMsg(message);
		}
	});
		
		var submitOrdersFormType=true;//页面只允许点击一次
	function submitOrdersForm(){
		if(submitOrdersFormType) {
			submitOrdersFormType = false;
			var orderId = $("#payLineOrderForm input[name='lspId']").val();
			console.info(orderId);
			if (orderId == "" || orderId == null) {
				showMsg("该订单已支付");
				$("#successForm").submit();
				return false;
			}
			$("#payLineOrderForm").submit();
		}else{
			console.info("第二次点击！");
		}
	}
	
	function showMsg(msg){
		var h=$(window).height()
			,w=$(window).width()
			;
		var html='<div class="g_msg"><span>'+msg+'</span></div>';
		$("body").append(html);
		var msg_w=parseInt($(".g_msg").css("width")),msg_h=parseInt($(".g_msg").css("height"));
		$(".g_msg").css({"top":(h-msg_h)/2,"left":(w-msg_w)/2})
		$(".g_msg").show(150);
		setTimeout(function(){
			$(".g_msg").remove();
		},2000);
	}
//-->
</script>
