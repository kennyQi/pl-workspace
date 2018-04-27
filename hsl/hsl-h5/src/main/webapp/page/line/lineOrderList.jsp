<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>订单列表</title>
		<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/order_list.css" rel="stylesheet" />
		<script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
		<script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
		<script language="javascript" src="${ctx}/resources/js/line_order_list.js"></script>
	</head>
	
	<body>
	    <!--线路订单列表区域  -->
		<div id="order_list">
		
		</div>
		<div class="tips" id="tips"><i></i>点击加载更多线路订单...</div>
		
		<input value="0" id="pageNum" type="hidden">
		<input value="0" id="canPull" type="hidden">
		<input value="${ctx}" id="contextPath" type="hidden">
		<!--没有线路订单-->
		<div class="noOrder" style="display: none">
		    <div class="tips">
		        <i></i>您没有线路订单哦!
		        <a href="${ctx}/line/list" class="choose">挑选线路</a>
		    </div>
		</div>
	</body>
</html>
