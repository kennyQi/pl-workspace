<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" "="viewport" />
<title>联票详情</title>

<link href="${ctx}/page/mp/css/global.css" rel="stylesheet" />
<link href="${ctx}/page/mp/css/common.css" rel="stylesheet" />
<link href="${ctx}/page/mp/css/mp_detail.css" rel="stylesheet" />
<script src="${ctx}/page/mp/js/libs/zepto.min.js" type="text/javascript"></script>
<script src="${ctx}/page/mp/js/libs/frozen.js" type="text/javascript"></script>
<script src="${ctx}/page/mp/js/script/mp_detail.js" type="text/javascript"></script>
</head>

<body>
    <!-- 内容 -->
    <div class="cont contNo">
        <div class="ui-slider">
            <ul class="ui-slider-content" style="width: 300%">
            	<c:if test="${!empty scenicSpotPics}">
		        <c:forEach  items="${scenicSpotPics}" var="scenicSpotPic">
	                <li><span style="background-image:url(${scenicSpotPic.url})"></span></li>
		        </c:forEach>
		        </c:if>
            </ul>
            <div class="circle-bg">
                <span class="index"></span>/<span class="sum"></span>
            </div>
        </div>
        <script>
            window.addEventListener('load', function(){
    
                var slider = new fz.Scroll('.ui-slider', {
                    role: 'slider',
                    indicator: true,
                    autoplay: true,
                    interval: 3000
                });
            
                slider.on('beforeScrollStart', function(fromIndex, toIndex) {
                    // console.log(fromIndex,toIndex)
                    
                });
            
                slider.on('scrollEnd', function() {
                    // console.log('end')
                    var ind = $('.ui-slider-indicators li.current').index()+1;
                    $('.index').html(ind);
                });

                $('.index').html(1);
                $('.sum').html($('.ui-slider-content li').length);
            })
        </script>
        <div class="disc-wrap">
            <div class="name">
            <c:if test="${!empty groupTicket.baseInfo.name}">
            ${groupTicket.baseInfo.name}
            </c:if>
            </div>
            <div class="labels">
                <div class="scenices">
            包括景点：<c:if test="${!empty groupTicket.baseInfo.scenicSpotNameStr}">
		            ${groupTicket.baseInfo.scenicSpotNameStr}
		            </c:if>
            </div>
            </div>
        </div>
        <!-- Tab -->
        <div class="ui-tab" id="ui-tab">
            <ul class="ui-tab-nav ui-border-b">
                <li class="current">联票介绍</li>
                <li>预订须知</li>
            </ul>
            <ul class="ui-tab-content" style="width:300%">
                <!-- 门票预订 -->
                <li class="current">
                    <div class="part-detail">
                    <c:if test="${!empty groupTicket.baseInfo.intro}">
		            ${groupTicket.baseInfo.intro}
		            </c:if>
                    </div>
                </li>
                <!-- 景区介绍 -->
                <li>
                    <div class="part">
                        <div class="tit deadline">
                            有效期
                        </div>
                        <div class="detail">
                            出票后<c:if test="${!empty groupTicket.useInfo.validDays}">${groupTicket.useInfo.validDays}</c:if>天有效 
                            <span class="tip"><c:if test="${groupTicket.sellInfo.autoMaticRefund}">过期退</c:if></span>
                        </div>
                    </div>
                    <div class="part">
                        <div class="tit ydxz">
                            预订须知
                            <div class="btn-switch close"></div>
                        </div>
                        <div class="detail ov">
                        <c:if test="${!empty groupTicket.baseInfo.notice}">
			            ${groupTicket.baseInfo.notice}
			            </c:if>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <!-- js -->
        <script>
        (function() {

            var tab = new fz.Scroll('.ui-tab', {
                role: 'tab',
                autoplay: false,
                interval: 3000
            });

            tab.on('beforeScrollStart', function(from, to) {
                // console.log(from, to);
            });

            tab.on('scrollEnd', function(curPage) {
                // console.log(curPage);
            });

        })();
        </script>
        <script>
            $(window).scroll(function(){
                //获取div距离顶部的距离
                var mTop = document.getElementById('ui-tab').offsetTop;
                //减去滚动条的高度
                var sTop = document.body.scrollTop;
                var result = mTop - sTop;
                // console.log(result);
                if(result <= 0){
                    $('.ui-tab-nav').css({'position':'fixed', 'top':0, 'left':0});
                }else{
                    $('.ui-tab-nav').css({'position':'', 'top':'', 'left':''});
                }
            });
        </script>
        <!-- <div class="bottonFixed"> -->
        <div style="display: none;">
            <div class="amount-wrap">金额：<span class="amount">￥<i><c:if test="${!empty groupTicket.baseInfo.rackRate}">${groupTicket.baseInfo.rackRate}</c:if></i></span>/套</div>
            <!-- 点击前往价格日历 -->
            <a href="javascript:;" class="btn-order">立即预订</a>
        </div>
    </div>
</body>