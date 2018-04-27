<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>预定成功</title>
		<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/order_success.css" rel="stylesheet" />
		<script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
		<script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
	</head>
	
	<body>
		<h2 class="title">${lineDto.baseInfo.name}</h2>
		<ul class="total">
		    <li><i class="iconfont">&#xe62b;</i>订单金额：￥${totalMoney}</li>
		    <c:if test="${status == 1}">
				<li><i class="iconfont">&#xe661;</i>预定金(${bookRate}%): ￥${bookMoney}</li>
			</c:if>
		    <c:if test="${status == 2}">
				<li><i class="iconfont">&#xe630;</i>尾款(${retainageRate}%): ￥${retainageBfore}</li>
			</c:if>
		</ul>
		<div class="tips">如未达到本团最低成团人数，旅行社将根据实际情况在出发前3天通
		    知您不成团，并为您推荐其他出发班期或更换其他同类产品，如您不
		    接受上述方案，我们将全额退还您支付的费用。由此给您造成的不便，
		    尽请谅解！
		</div>
		<div class="success">
		    <i class="iconfont">&#xe652;</i>恭喜支付成功
		</div>
		<a href="${ctx}/" class="order_btn"><i class="iconfont type1">&#xe614;</i>返回首页</a>
		<a href="${ctx}/hslH5/line/lineOrderDetail?dealerOrderNo=${dealerOrderNo}" class="order_btn"><i class="iconfont">&#xe604;</i>查看详情</a>
	</body>
</html>
