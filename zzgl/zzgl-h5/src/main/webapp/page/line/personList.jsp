<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>选择游玩人</title>
		<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/person_list.css" rel="stylesheet" />
		
		<script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
		<script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
		<script src="${ctx}/resources/js/person_list.js" language="javascript"></script>
	</head>
	<body>
		
		<div class="body">
		
			<!--游玩人列表区域  -->
			<div class="content"  id="main" >
			    <!--新增游玩人表单  -->
			    <form action="${ctx}/hslH5/line/addTraveler" id="personListAddForm"><div id="addpersonHiddenValue" style="display:none;"></div></form>
			    <!--编辑游玩人表单  -->
			    <%-- <form action="${ctx}/hslH5/line/editTravelerPage" id="personListEditForm"><div id="EditpersonHiddenValue" style="display:none;"></div></form>
			     --%>
			    <ul class="ui-list ui-list-text ui-border-tb  m_addPerson">
			        <li class="ui-border-t" id="addPerson">
			            <div class="ui-list-info">
			               <h4><i class="iconfont">&#xf014d;</i>新增游玩人</h4>
			            </div>
			            <div class="ui-list-action add_btn"></div>
			        </li>
			    </ul>
			    <ul class="ui-list ui-list-text ui-list-link ui-border-b m-subTitle m_person_list" id="personList">
			        <c:forEach items="${travelers}" var="traveler">
				        <li class="ui-border-t box" rel="${traveler.id}" choose="false" id="${traveler.id}">
				            <label class="choose" id="${traveler.id}">
				                <i class="iconfont gou">&#xe68a;</i>
				                <i class="iconfont">&#xe643;</i>
				            </label>
				            <h4 class="ui-nowrap person">
				                <input type="hidden" value="${traveler.id}" id="travelerId">
				                <span class="di">
				                	${traveler.name}
				                	<c:if test="${traveler.type == 1}">（成人）</c:if>
				                	<c:if test="${traveler.type == 2}">（儿童）</c:if>
				                </span>
				                <label class="di"><i>手机号</i>：<i class="num mobile">${traveler.mobile}</i></label>
				                <label class="di"><i class="type" rel="1">身份证</i>：<i class="num cardid">${traveler.cardNo}</i></label>
				            </h4>
				            <form action="${ctx}/hslH5/line/editTravelerPage" id="personListEditForm"  style="display:none;"><div id="EditpersonHiddenValue" style="display:none;"></div>
			    
				            <button href="#" class="editor pa"  rel="${traveler.id}">编辑</button>
				            </form>
				        </li>
			        </c:forEach>
			    </ul>
			</div>
		    <div class="fixedBtn box">
		        <div class="leftBtn">已选择<i class="adult">0</i>成人<i class="chil">0</i>儿童</div>
		        <div class="next-btn">确定</div>
		    </div>
		    <!--表单区域  -->
		    <form action="${ctx}/hslH5/line/savePerson" id="personListSubmitForm">
		        <div id="userIds" style="display:none;">
		        	<c:forEach items="${lineOrderCommand.travelerIds}" var="travelerId">
						<input value="${travelerId}" name="travelerIds" type="hidden">
					</c:forEach>
		        </div>
		    	<!--表单隐藏值区域  -->
				<input value="${lineOrderCommand.userId}" name="userId" type="hidden">
				<input value="${lineOrderCommand.lineId}" name="lineId" type="hidden">
				<input value="<fmt:formatDate value="${lineOrderCommand.travelDate}" pattern="yyyy-MM-dd"/>" name="travelDate" type="hidden">
				<input value="${lineOrderCommand.adultNo}" name="adultNo" type="hidden">
				<input value="${lineOrderCommand.childNo}" name="childNo" type="hidden">
				<input value="${lineOrderCommand.linkName}" name="linkName" type="hidden">
				<input value="${lineOrderCommand.linkMobile}" name="linkMobile" type="hidden">
				<input value="${lineOrderCommand.linkEmail}" name="linkEmail" type="hidden">
		    </form>
		    <c:if test="${empty message}"><input value="1" id="message1" type="hidden"></c:if>
		    <c:if test="${not empty message}"><input value="${message}" id="message1" type="hidden"></c:if>
		</div>
	</body>
</html>
