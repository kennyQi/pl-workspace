<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
<title></title>
    <link href="${ctx}/css/common.css" rel="stylesheet" />
<!--公共部分-->
    <script src="${ctx}/js/jquery-1.10.2.js" language="javascript"></script>
<!--公共样式部分-->

    <link href="${ctx}/css/amazeui.css" rel="stylesheet" />

    <script src="${ctx}/js/amazeui.min.js" language="javascript"></script>

    <link href="${ctx}/css/style.css" rel="stylesheet" />
    <script src="${ctx}/js/detail.js" language="javascript"></script>

</head>
<body>
<div class="am-slider am-slider-default" data-am-flexslider id="demo-slider-0">
    <ul class="am-slides">
        <c:if test="${!empty headImages}">
        <c:forEach  items="${headImages}" var="headImage">
        	<li><img src="${headImage.urlMapsJSON}"/></li>
        </c:forEach>
        </c:if>
    </ul>
    <div class="banner_tip pa">
        <!-- <label class="type">跟团行</label> -->
			<c:choose>
	    		<c:when test="${line.baseInfo.type == '1'}">
	      			<label class="type"><i class="iconfont">&#xe60f;</i>跟团游</label>
	    		</c:when>
	    		<c:when test="${line.baseInfo.type == '2'}">
	      			<label class="type"><i class="iconfont">&#xe60f;</i>自助游</label>
	    		</c:when>
			  </c:choose>
			  <c:if test="${!empty city}">
			        <label class="add"><i class="iconfont">&#xebed;</i>${city.name}出发</label>
			  </c:if>
    </div>
    <div class="banner_bg pa">
        </div>
</div>
<h1 class="detail_t">
    ${line.baseInfo.name}
</h1>
<a class="handle" href="objc://fromSubject::/"><i class="iconfont" style="color:#fc8167">&#xe606;</i>
    出行价格日历<i class="iconfont iconAfter" >&#xe656;</i>
</a>
<a class="handle h_data" href="objc://callPhone::/"><i class="iconfont"  style="color:#8dd849">&#xe634;</i>
    咨询电话<i class="iconfont iconAfter" >&#xe656;</i>
</a>
<div class="detailC pl">
        <div class="nav">
            <span  class="on">行程概要</span>
            <span>行程详情</span>
        </div>
    <!--行程概要-->
    <div class="detailTab">
    	 <c:if test="${!empty line.baseInfo.featureDescription}">
        <div class="list pl">
            <div class="listIcon listIcon_line"><i class="iconfont">&#xeb79;</i></div>
            <div class="listCont">
                <div class="mark">
                  &nbsp;&nbsp;线路特色
                </div>
                <p>
                    ${line.baseInfo.featureDescription}
                </p>
                <span class="more" style="display:none;"><i class="iconfont">&#xe616;</i></span>
            </div>
        </div>
        </c:if>
         <c:if test="${!empty line.baseInfo.favorableDescription}">
        <div class="list pl">
            <div class="listIcon listIcon_money"><i class="iconfont">&#xe610;</i></div>
            <div class="listCont">
                <div class="mark">
                    &nbsp;&nbsp;优惠活动
                </div>
                <p>
                	${line.baseInfo.favorableDescription}
                </p>
                <span class="more" style="display:none;"><i class="iconfont">&#xe616;</i></span>
            </div>
        </div>
        </c:if>
         <c:if test="${!empty line.baseInfo.noticeDescription}">
        <div class="list pl">
            <div class="listIcon listIcon_tips"><i class="iconfont">&#xe6f4;</i></div>
            <div class="listCont">
                <div class="mark">
                    &nbsp;&nbsp;提示信息
                </div>
                <p>
                	${line.baseInfo.noticeDescription}
              	 </p>
                <span class="more" style="display:none;"><i class="iconfont">&#xe616;</i></span>
            </div>
        </div>
        </c:if>
         <c:if test="${!empty line.baseInfo.feeDescription}">
        <div class="list pl">
            <div class="listIcon listIcon_money"><i class="iconfont">&#xe610;</i></div>
            <div class="listCont">
                <div class="mark">
                    &nbsp;&nbsp;费用说明
                </div>
                <p>
                	${line.baseInfo.feeDescription}
                </p>
                <span class="more" style="display:none;"><i class="iconfont">&#xe616;</i></span>
            </div>
        </div>
        </c:if>
         <c:if test="${!empty line.baseInfo.bookDescription}">
        <div class="list pl">
            <div class="listIcon listIcon_money"><i class="iconfont">&#xe610;</i></div>
            <div class="listCont">
                <div class="mark">
                    &nbsp;&nbsp;预定须知
                </div>
                <p>
                	${line.baseInfo.bookDescription}
                </p>
                <span class="more" style="display:none;"><i class="iconfont">&#xe616;</i></span>
            </div>
        </div>
        </c:if>
    </div>

    <!--行程详情-->
    <div class="detailTab" style="display:none;">
        <!--按天区分行程-->
        <c:set var="day" value="0" />
        <c:forEach  items="${dayRoutes}" var="dayRoute">
        <c:set var="day" value="${day+1}" />
        <div class="list pl">
            <div class="listIcon"><i class="iconfont">&#xe624;</i></div>
            <div class="listCont">
                <div class="mark">
                    <span class="time">DAY: ${day}</span>
                    <i class="cityName">${dayRoute.starting}</i>
                    <c:set value="${ fn:split(dayRoute.vehicle, ',') }" var="vehicles" />
                    <c:forEach items="${vehicles}" var="vehicle">
	                    <c:choose>
				    		<c:when test="${vehicle == '1'}">
				      			<span class="icon fly"><i class="iconfont">&#xe67c;</i></span>
				    		</c:when>
				    		<c:when test="${vehicle == '2'}">
				      			<span class="icon bus"><i class="iconfont">&#xec3d;</i></span>
				    		</c:when>
				    		<c:when test="${vehicle == '3'}">
				      			<span class="icon ship"><i class="iconfont">&#xf0005;</i></span>
				    		</c:when>
				    		<c:when test="${vehicle == '4'}">
				      			<span class="icon trip"><i class="iconfont">&#xe68b;</i></span>
				    		</c:when>
				    		<c:when test="${vehicle == '5'}">
				      			<i class="cityName">自行前往</i>
				    		</c:when>
						 </c:choose>
                    </c:forEach>
					<i class="cityName">${dayRoute.destination}</i>
                </div>
                <p>
                    <strong>行程安排：</strong>${dayRoute.schedulingDescription}<br>
                    <c:if test="${!empty dayRoute.hotelInfoJson}">
                   <strong> 家庭入住：</strong>${dayRoute.hotelInfoJson}<br>
                   </c:if>
                    <span>
              <c:if test="${!empty dayRoute.includeBreakfast}">
                    早餐：
              <c:choose>
	    		<c:when test="${dayRoute.includeBreakfast}">
	      			<label class="type">包含</label>
	    		</c:when>
	    		<c:when test="${!dayRoute.includeBreakfast}">
	      			<label class="type">敬请自理</label>
	    		</c:when>
			  </c:choose>
			  </c:if><br>
			  <c:if test="${!empty dayRoute.includeLunch}">
                    午餐：
              <c:choose>
	    		<c:when test="${dayRoute.includeLunch}">
	      			<label class="type">包含</label>
	    		</c:when>
	    		<c:when test="${!dayRoute.includeLunch}">
	      			<label class="type">敬请自理</label>
	    		</c:when>
			  </c:choose>
			  </c:if><br>
               <c:if test="${!empty dayRoute.includeDinner}">
                    晚餐：
              <c:choose>
	    		<c:when test="${dayRoute.includeDinner}">
	      			<label class="type">包含</label>
	    		</c:when>
	    		<c:when test="${!dayRoute.includeDinner}">
	      			<label class="type">敬请自理</label>
	    		</c:when>
			  </c:choose>
			  </c:if></span>
                </p>
                <c:forEach  items="${dayRoute.lineImageList}" var="lineImageList">
                	<img src="${lineImageList.urlMapsJSON}" />
                </c:forEach>
                <span class="more" style="display:none;"><i class="iconfont">&#xe616;</i></span>
            </div>
        </div>
		</c:forEach>
    </div>

</div>
</body>
</html>
