<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>票量旅游</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
 <!-- 公共样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/circuit.css" />
</head>
<body>
	<!-- 页头  -->
	<header class="header">
	  <h1>行程详情</h1>
	  <div class="left-head"> <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn"> <span class="inset_shadow"> <span class="head-return"></span> </span> </a> </div>	 
	</header>
	<!-- 页头 -->
	<section>
	<div class="c_view ntop">
	<!-- 行程开始 -->
	<c:forEach items="${line.routeInfo.dayRouteList}" var="dayRoute">	 
  	<div class="c_v_list">
  		<div class="c_v_lsit_title">  			
  				<i></i>
	  			<span>${dayRoute.starting}
	  			<c:forEach items="${fn:split(dayRoute.vehicle,',')}" begin="0" end="0" var="vehicle">
					<c:if test="${vehicle == '1'}">
						<em class="iconair"></em>
					</c:if>
					<c:if test="${vehicle == '2'}">
						<em class="iconbus"></em>
					</c:if>
					<c:if test="${vehicle == '3'}">
						<em class="iconsteamer"></em>
					</c:if>
					<c:if test="${vehicle == '4'}">
						<em class="icontrain"></em>
					</c:if>
				</c:forEach>
	  			${dayRoute.destination}</span>
	  			<em class="c_time blue">第${dayRoute.days}天</em>  			
  		</div>
  		<div class="c_v_info">
  			${dayRoute.schedulingDescription}
  		</div>
  		<div class="c_v_break bordert">
  			<div class="c_bareak_up">
  				<p><i></i><label>住宿：</label>
				<c:forEach items="${dayRoute.hotelList}" var="hotel" varStatus="i">
				<c:if test="${i.count == fn:length(dayRoute.hotelList)}">
  						${hotel.hotelName}
 					</c:if>
 					<c:if test="${i.count != fn:length(dayRoute.hotelList)}">
  						${hotel.hotelName} 或
  					</c:if>
 				</c:forEach>
				<c:if test="${fn:length(dayRoute.hotelList) > 0}">
				或 同级
  				</c:if>
				</p>
  			</div>
  			<div class="c_bareak_zzw clear">
  				<span><i></i><label>早餐：</label> 
					<c:if test="${dayRoute.includeBreakfast}">
	  					包含
	  				</c:if> 
	  				<c:if test="${!dayRoute.includeBreakfast}">
	  					敬请自理
	  				</c:if> 
	  				</span> <span><i></i><label>午餐：</label> 
	  				<c:if test="${dayRoute.includeLunch}">
	  					包含
	  				</c:if> 
	  				<c:if test="${!dayRoute.includeLunch}">
	  					敬请自理
	  				</c:if> </span> <span><i></i><label>晚餐：</label>
	  				 <c:if test="${dayRoute.includeDinner}">
	  					包含
	  				</c:if> <c:if test="${!dayRoute.includeDinner}">
	  					敬请自理
	  				</c:if> 
				</span>
  			</div>
  		</div>
  	</div>  
  	</c:forEach>  	
  	<!-- 行程结束 -->
  </div>
 </section>
	

</body>
</html>