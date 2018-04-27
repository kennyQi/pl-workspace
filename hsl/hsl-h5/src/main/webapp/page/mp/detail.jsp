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
<link rel="stylesheet" type="text/css" href="${ctx}/css/ticket.css">
<%@ include file="/page/common/jscommon.jsp"%>
<script type="text/javascript" src="${ctx}/js/ticket.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.slides.min.js"></script>

<script>
    $(function(){
        slidesPic();//幻灯片
    });
    //轮播图
 function  slidesPic(){
    $("#picShow").slidesjs({
    	play: {//播放设置
            auto: true,//设置为自动播放
			interval:3000//设置为每次轮番的间隔时间以毫秒为单位
       }
    });
    $(".slidesjs-navigation").remove();
    $(".slidesjs-pagination li a").html("");
    $(".slidesjs-pagination li").css({"display":"inline-block"});
    
}
</script>
</head>
<body>
	<!-- 公共页头  -->
	<header class="header">
	  <h1>${scenicSpot.scenicSpotBaseInfo.name }</h1>
      <div class="left-head"> 
	    <a id="goBack" href="${ctx}/mp/list?openid=${openid}" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	  <!-- <div class="right-head">
            <a href="${ctx}/index?openid=${openid}" class="head-btn fn-hide">
                <span class="inset_shadow">
                    <span class="head-home"></span>
                </span>
            </a>
        </div> -->
	</header>
    
    <!-- 页面内容 -->
	<div class="content ticket">
    	<!--start 图片轮播区域-->
        	<div class="carousel">
            	<div class="pic" id="picShow">
            		<c:forEach var="image" items="${scenicSpot.images }">
            			<img src="${image.url }">
            		</c:forEach>
                </div>
                <p><span>共${imgCount }张图片</span>${scenicSpot.scenicSpotBaseInfo.name }</p>
            </div>
        <!--end 图片轮播区域-->
        <div class="details">
        	<ul class="tabs">
            	<li>门票预订</li>
                <li class="active">景点介绍</li>
            </ul>
             <!--start 门票预订-->
            <div id="book" class="info_content" style="display:none;">
            	<div class="notes">
                	<h3><span></span>预订须知</h3>
                    <div class="info_notes" style="display:none;"><!-- 默认不显示，点击展现 -->
                    	<p>${scenicSpot.bookInfoDTO.buyNotie }</p>
                    </div>
                </div>
                <c:forEach var="policy" items="${policies }">
                	<div class="reserve">
	                	<h3><span style="background:none;"></span>${policy.name }</h3>
	                    <div class="info_reserve">
	                    	<div class="left">
	                            <p>${policy.ticketName }</p>
	                            <p>&yen;<fmt:formatNumber value="${policy.tcPrice }" pattern="0.00" /></p>
	                        </div>
	                        <div class="right"><a href="${ctx}/mpo/settle?openid=${openid}&scenicSpotId=${scenicSpot.id}&policyId=${policy.policyId}">预 订</a></div>
	                    </div>
	                </div>
                </c:forEach>
            </div>
            <!--end 门票预订-->

            <!--start 景区介绍-->
            <div id="in" class="info_content active">
                <div class="notes open">
                    <h3>景区介绍</h3> 
                    <div class="info_notes">
                        <p>${scenicSpot.scenicSpotBaseInfo.intro }</p>
                    </div>
                </div>
                <%-- <div class="notes open">
                    <h3>交通指南</h3>
                    <div class="info_notes">
                        <p>
                        	<c:if test="${empty(scenicSpot.scenicSpotGeographyInfo.traffic ) }">
                        		<div style="text-align: center; font-size: 14px; color: #666;
										padding-top: 10px; padding-bottom: 10px; ">暂无数据</div>
                        	</c:if>
                        	<c:if test="${!empty(scenicSpot.scenicSpotGeographyInfo.traffic ) }">
                        		${scenicSpot.scenicSpotGeographyInfo.traffic }
                        	</c:if>
                        </p>
                    </div>
                </div> --%>
            </div>
            <!--end 景区介绍-->
        </div>
            
    </div>
</body>
</html>
