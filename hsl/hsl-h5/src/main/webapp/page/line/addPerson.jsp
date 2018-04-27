<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>添加游玩人</title>
		<!--公共样式部分-->
		<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/person_list.css" rel="stylesheet" />
		<script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
		<script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
		<script src="${ctx}/resources/js/idCard.js" language="javascript"></script>
		<script src="${ctx}/resources/js/person_list.js" language="javascript"></script>
	</head>
	
	<body>
		<div class="body">
			<div class="g_addPerson content">
				<!--表单值区域  -->
			    <form id="addPersonForm" method="POST" action="${ctx}/hslH5/line/addPerson">
					<div class="ui-form-item ui-border-b m_input">
				            <label for="#"> 姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</label>
				            <input type="text" placeholder="姓名" id="person_name" name="name"/>
			        </div>
				    <div class="ui-form-item ui-border-b m_input">
				        <label for="#">手&nbsp;&nbsp;机&nbsp;&nbsp;号：</label>
				        <input type="text" placeholder="手机号" id="person_mobile" name="mobile" />
				    </div>
				    <div class="ui-form-item ui-border-b m_input m_select">
				            <label for="#">证件类型：</label>
				            <select class="select " id="person_select" name="idType" >
				           		<option value="1">身份证</option>
				            </select>
			        </div>
			       <div class="ui-form-item ui-border-b m_input">
			            <label for="#">证件号码：</label>
			            <input type="text" placeholder="输入证件号码"  id="person_num" name="idNo"/>
			        </div>
					<div class="m_btn" id="addPersonSure" >提&nbsp;&nbsp;&nbsp;&nbsp;交</div>
					
					<!--隐藏值区域  -->
					<input value="${lineOrderCommand.userId}" name="fromUserId" type="hidden">
					<input value="${lineOrderCommand.userId}" name="userId" type="hidden">
					<input value="${lineOrderCommand.lineId}" name="lineId" type="hidden">
					<input value="<fmt:formatDate value="${lineOrderCommand.travelDate}" pattern="yyyy-MM-dd"/>" name="travelDate" type="hidden">
					<input value="${lineOrderCommand.adultNo}" name="adultNo" type="hidden">
					<input value="${lineOrderCommand.childNo}" name="childNo" type="hidden">
					<input value="${lineOrderCommand.linkName}" name="linkName" type="hidden">
					<input value="${lineOrderCommand.linkMobile}" name="linkMobile" type="hidden">
					<input value="${lineOrderCommand.linkEmail}" name="linkEmail" type="hidden">
					<input value="${lineOrderCommand.token}" name="token" type="hidden">
					<c:forEach items="${lineOrderCommand.travelerIds}" var="travelerId">
						<input value="${travelerId}" name="travelerIds" type="hidden">
					</c:forEach>
		        </form>
		        <input value="1" id="message1" type="hidden">
			</div>
			<div class="g_date content" id="date"  style="display:none">
		        <div id="dateMobile" class="dateMobile"></div>
				<div class="m_btn" id="sureDate">确&nbsp;&nbsp;&nbsp;&nbsp;定</div>
			</div>
			<div id="contextPathAdd" name="${ctx}">
			</div>
		</div>
	</body>
</html>
