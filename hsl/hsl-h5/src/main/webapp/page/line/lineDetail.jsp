<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>票量旅游</title>
		    <link href="${ctx}/resources/amazeui/2.4.2/css/amazeui.css" rel="stylesheet" />
		    
		    <link href="${ctx}/resources/css/style.css" rel="stylesheet" />
		    <link href="${ctx}/resources/css/common.css" rel="stylesheet" />
		    
		    <script src="${ctx}/resources/js/jquery-1.10.2.js" language="javascript"></script>
		    
		    <script src="${ctx}/resources/amazeui/2.4.2/js/amazeui.js" language="javascript"></script>
		    <script src="${ctx}/resources/amazeui/2.4.2/js/amazeui.min.js" language="javascript"></script>
		    <script src="${ctx}/resources/amazeui/2.4.2/js/amazeui.ie8polyfill.js" language="javascript"></script>
		    <script src="${ctx}/resources/amazeui/2.4.2/js/amazeui.ie8polyfill.min.js" language="javascript"></script>
		    <script src="${ctx}/resources/amazeui/2.4.2/js/amazeui.widgets.helper.js" language="javascript"></script>
		    <script src="${ctx}/resources/amazeui/2.4.2/js/amazeui.widgets.helper.min.js" language="javascript"></script>
		    
		    <script src="${ctx}/resources/js/detail.js" language="javascript"></script>
	
	</head>
	<body>
		<div class="am-slider am-slider-default" data-am-flexslider id="demo-slider-0">
		    <ul class="am-slides">
				<c:forEach items="${lineImages}" var="lineImage">
					<li><img src="${fileUploadPath}/${lineImage.urlMaps['H5']}"></li>
				</c:forEach>
		    </ul>
		    
		    <div class="banner_tip pa">
		        <label class="type">
			        <c:if test="${line.baseInfo.type == 1}"><i class="iconfont2">&#xe60f;</i>跟团游</c:if>
					<c:if test="${line.baseInfo.type == 2}"><i class="iconfont2">&#xe60f;</i>自助游</c:if>
		        </label>
		        <label class="add"><i class="iconfont2">&#xebed;</i>${cityMap[line.baseInfo.destinationCity]}出发</label>
		    </div>
		    
		    <div class="banner_bg pa"></div>
		</div>
		<h1 class="detail_t">${line.baseInfo.name}</h1>
		<a class="handle" href="${ctx}/hslH5/line/dateDetail?lineId=${line.id}"><i class="iconfont2"  style="color:#8dd849">&#xe606;</i>出行价格日历</a>
		<a class="handle h_data" id="takeTel" href="tel:0571-28280813"><i class="iconfont2"  style="color:#8dd849">&#xe634;</i>咨询电话</a>
		
		<div class="detailC pl">
		        <div class="nav">
		            <span  class="on">行程概要</span>
		            <span>行程详情</span>
		        </div>
		        
		    <!--行程概要-->
		    <div class="detailTab">
		    
		        <div class="list pl">
		            <div class="listIcon listIcon_line"><i class="iconfont2">&#xeb79;</i></div>
		            <div class="listCont">
		                <div class="mark">
		                  &nbsp;&nbsp;线路特色
		                </div>
		                <div class="lineD">${line.baseInfo.featureDescription}</div>
		                <span class="more" style="display:none;"><i class="iconfont2">&#xe616;</i></span>
		            </div>
		        </div>
		        
		        <div class="list pl">
		            <div class="listIcon listIcon_money"><i class="iconfont2">&#xe610;</i></div>
		            <div class="listCont">
		                <div class="mark">&nbsp;&nbsp;优惠活动</div>
		                <div class="lineD">${line.baseInfo.favorableDescription}</div>
		                <span class="more" style="display:none;"><i class="iconfont2">&#xe616;</i></span>
		            </div>
		        </div>
		        
		        <div class="list pl">
		            <div class="listIcon listIcon_tips"><i class="iconfont2">&#xe6f4;</i></div>
		            <div class="listCont">
		                <div class="mark">
		                    &nbsp;&nbsp;提示信息
		                </div>
		                <div class="lineD">${line.baseInfo.noticeDescription}</div>
		                <span class="more" style="display:none;"><i class="iconfont2">&#xe616;</i></span>
		            </div>
		        </div>
		
		        <div class="list pl">
		            <div class="listIcon listIcon_money"><i class="iconfont2">&#xe610;</i></div>
		            <div class="listCont">
		                <div class="mark">&nbsp;&nbsp;费用说明</div>
		                <div  class="lineD">${line.baseInfo.feeDescription}</div>
		                <span class="more" style="display:none;"><i class="iconfont2">&#xe616;</i></span>
		            </div>
		        </div>
		
		        <div class="list pl">
		            <div class="listIcon listIcon_money"><i class="iconfont2">&#xe610;</i></div>
		            <div class="listCont">
		                <div class="mark">&nbsp;&nbsp;预定须知</div>
		                <div  class="lineD">${line.baseInfo.bookDescription}<br></div>
		                <span class="more" style="display:none;"><i class="iconfont2">&#xe616;</i></span>
		            </div>
		        </div>
		
		    </div>
		
		    <!--行程详情-->
		    <div class="detailTab" style="display:none;">
		        <!--按天区分行程-->
		        <c:forEach items="${line.routeInfo.dayRouteList}" var="dayRoute">
					<div class="list pl">
			            <div class="listIcon"><i class="iconfont2">&#xe624;</i></div>
			            <div class="listCont">
			                <div class="mark">
			                    <span class="time">Day${dayRoute.days}</span>
			                    <span class="station">${dayRoute.starting}</span>
			                    <c:forEach
									items="${fn:split(dayRoute.vehicle,',')}" begin="0" end="0"
									var="vehicle">
									<c:if test="${vehicle == '1'}">
										<span class="icon fly"><i class="iconfont2">&#xe67c;</i></span>
									</c:if>
									<c:if test="${vehicle == '2'}">
										<span class="icon bus"><i class="iconfont2">&#xec3d;</i></span>
									</c:if>
									<c:if test="${vehicle == '3'}">
										<span class="icon ship"><i class="iconfont2">&#xf0005;</i></span>
									</c:if>
									<c:if test="${vehicle == '4'}">
										<span class="icon trip"><i class="iconfont2">&#xe68b;</i></span>
									</c:if>
								</c:forEach>
			                    <span class="station">${dayRoute.destination}</span>
			                </div>
			                ${dayRoute.schedulingDescription}<br>
			                <p>
				                <span>
						                     早餐：<c:if test="${dayRoute.includeBreakfast}">包含</c:if> <c:if test="${!dayRoute.includeBreakfast}">敬请自理</c:if><br>
						                     午餐：<c:if test="${dayRoute.includeLunch}">包含</c:if> <c:if test="${!dayRoute.includeLunch}">敬请自理</c:if><br>
						                     晚餐：<c:if test="${dayRoute.includeDinner}">包含</c:if> <c:if test="${!dayRoute.includeDinner}">敬请自理</c:if>
				                </span>
			                </p>
			                <c:forEach items="${dayRoute.lineImageList}" var="dayRouteImage">
			                	<img src="${fileUploadPath}/${dayRouteImage.urlMaps['H5']}" />
			                </c:forEach>
			                <span class="more" style="display:none;"><i class="iconfont2">&#xe616;</i></span>
			            </div>
		            </div>
				</c:forEach>
		    </div>
		</div>
		<form action="${ctx}/hslH5/line/dateDetail" id="bookOrderForm">
			<input name="lineId" value="${line.id}" type="hidden">
		</form>
		<div class="fixedBtn box">
		    <div class="leftBtn">￥${line.minPrice}人/起</div>
		    <div class="next-btn">立即预定</div>
		</div>
	</body>
</html>
