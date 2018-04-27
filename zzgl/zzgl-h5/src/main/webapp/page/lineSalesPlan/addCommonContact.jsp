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
		<script src="${ctx}/resources/js/commonContactList.js" language="javascript"></script>
	</head>
	
	<body>
		<div class="body">
			<div class="g_addPerson content">
				<!--表单值区域  -->
			    <form id="addPersonForm" method="POST" action="${ctx}/lineSalesPlan/addTravelerPage">
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
				            <select class="select " id="person_select" name="cardType" >
				           		<option value="1">身份证</option>
				            </select>
			        </div>
			       <div class="ui-form-item ui-border-b m_input">
			            <label for="#">证件号码：</label>
			            <input type="text" placeholder="输入证件号码"  id="person_num" name="cardNo"/>
			        </div>
					<div class="m_btn" id="addPersonSure" >提&nbsp;&nbsp;&nbsp;&nbsp;交</div>
					
					<!--隐藏值区域  -->
					<input value="${lspOrderCommand.lspId}" name="lspId" type="hidden">
					<input value="<fmt:formatDate value="${lspOrderCommand.travelDate}" pattern="yyyy-MM-dd"/>" name="travelDate" type="hidden">
					<input value="${lspOrderCommand.adultNo}" name="adultNo" type="hidden">
					<input value="${lspOrderCommand.linkName}" name="linkName" type="hidden">
					<input value="${lspOrderCommand.linkMobile}" name="linkMobile" type="hidden">
					<input value="${lspOrderCommand.email}" name="email" type="hidden">
					<c:forEach items="${lspOrderCommand.travelerIds}" var="travelerId">
						<input value="${travelerId}" name="travelerIds" type="hidden">
					</c:forEach>
					<input value="${lspOrderCommand.lastNum}" name="lastNum" type="hidden">
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
