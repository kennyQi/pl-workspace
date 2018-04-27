<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
﻿<!DOCTYPE html>
<html>
<head>
<title>中智票量</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/order.css"/>
<!-- javascript -->
 <script type="text/javascript" src="${ctx}/js/jquery.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script> 	 <script type="text/javascript" src="${ctx}/js/json2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jsbox.js"></script> 	 <script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script><script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>	 <script type="text/javascript" src="${ctx}/js/hsl.js"></script>
<script type="text/javascript">
	var pageNo = 1;
	$(document).ready(function() {
		$.pop.lock(true);
	    $.pop.load(true, '努力加载中...');
	    ajaxLoad(true, true);
	});
	
	function ajaxLoad(isFir, isAsync) {
		scrollBottomOff();
		$.ajax({
	    	url:'${ctx}/mpo/ajaxList?pageNo=' + pageNo + '&pageSize=15&openid=${openid}',
	    	type:'post',
	    	async:isAsync,
	    	timeout:60000,
	    	dataType:'json',
	    	error:function() {
			    $.pop.load(false, function() {
	    			$.pop.lock(false);
	    			if (!isFir) { scrollBottomOn(); }
			    	setTimeout(function() {
			    		$.pop.tips('系统繁忙,请稍候');
			    	}, 666);
			    });
	    	},
	    	success:function(data) {
			    $.pop.load(false, function() {
			    	$.pop.lock(false);
			    	if (data.result == '1') {
			    		if (isFir) {
							$("#pullUp").show();
							$("#noDataTip").hide();
						}
			    		load(data.orders);
			    		if (data.orders.length <15) {
							$("#pullUp").hide();
						} else {
							scrollBottomOn();
						}
						pageNo++;
				    } else if (data.result == '-1') {
				    	if (isFir) {
							$("#pullUp").hide();
							$("#noDataTip").show();
						} 
				    } else {
				    	scrollBottomOff();
				    	setTimeout(function() {
				    		$.pop.tips('系统繁忙,请稍候');
				    	}, 666);
				    }
			    });
	    	}
	    });
	}
	
	function load(orders) {
		var temp = $("#temp").html();
		for (var i = 0; i < orders.length; i++) {
			var order = orders[i];
			var child = temp;
			var status = "";
			if (order.status.cancel) { status = "已取消"; }
			if (order.status.outOfDate) { status = "已过期"; }
			if (order.status.prepared) { status = "待游玩"; }
			if (order.status.used) { status = "已游玩"; }
			var orderId = order.dealerOrderCode, orderCode = orderId;
			if (orderCode.length > 18) { orderCode = orderCode.substring(0, 18) + "..."; }
			child = child.replace(/#orderCode#/gi, orderCode).replace(/#orderId#/gi, orderId)
				.replace(/#status#/gi, status).replace(/#number#/gi, order.number)
				.replace(/#image#/gi, order.scenicSpot.scenicSpotBaseInfo.image)
				.replace(/#name#/gi, order.scenicSpot.scenicSpotBaseInfo.name)
				.replace(/#address#/gi, order.scenicSpot.scenicSpotGeographyInfo.address)
				.replace(/#intro#/gi, order.scenicSpot.scenicSpotBaseInfo.intro);
			$("#addlist").append($(child));
		}
	}
	
	function scrollBottomOn() {
		$(window).scroll(function() {
			var scrollTop = $(this).scrollTop();
			var scrollHeight = $(document).height();
			var windowHeight = window.screen.height;
			if (scrollTop + windowHeight >= scrollHeight-15) {
			
				$(".pullUpLabel").html("Loading...");
				$("#pullUp").addClass("loading");
				ajaxLoad(false, false);
			}
		});
	}
	function scrollBottomOff() {
		$(window).scroll(function() {
			return;
		});
	}
</script>
<style>
	#pullUp {
        height:40px;
        line-height:40px;
        font-weight:bold;
        margin-bottom: 5px;
        font-size:14px;
        color:#888;
    }
    #pullUp .pullUpIcon  {
        display:block;margin-left:36%;
        width:40px; height:40px;
        background:url(${ctx}/img/pull-icon.png) 0 0 no-repeat;
        -webkit-background-size:40px 80px; background-size:40px 80px;
        -webkit-transition-property:-webkit-transform;
        -webkit-transition-duration:250ms;
    }
    #pullUp span{float:left;}
    
    #pullUp .pullUpIcon  {
        -webkit-transform:rotate(-180deg) translateZ(0);
    }

    
    #pullUp.flip .pullUpIcon {
        -webkit-transform:rotate(0deg) translateZ(0);
    }
    
    #pullUp.loading .pullUpIcon {
        background-position:0 100%;
        -webkit-transform:rotate(0deg) translateZ(0); 
        -webkit-transition-duration:0ms; 

        -webkit-animation-name:loading;
        -webkit-animation-duration:2s;
        -webkit-animation-iteration-count:infinite;
        -webkit-animation-timing-function:linear;
    }

    @-webkit-keyframes loading {
        from { -webkit-transform:rotate(0deg) translateZ(0); }
        to { -webkit-transform:rotate(360deg) translateZ(0); }
    }
</style>
</head>
<body class="myorder">
	<!--start 页头  -->
	<header class="header" >
		<h1>门票订票</h1>
	 	<div class="left-head">
                <a id="goBack" href="${ctx }/user/center" class="tc_back head-btn">
                    <span class="inset_shadow">
                        <span class="head-return"></span>
                    </span>
                </a>
            </div>

            <div class="right-head">
                <a href="${ctx}/jp/init?openid=${openid}" class="head-btn fn-hide">
                    <span class="inset_shadow">
                        <span class="head-home"></span>
                    </span>
                </a>
            </div>
	</header>
	<!--end 页头  -->
	<div class="table">
		<a href="${ctx}/user/jpos?openid=${openid}">机票订单</a>
	  	<a class="active" href="${ctx}/user/mpos?openid=${openid}">门票订单</a>
	</div>

	<!--start 页面内容 -->
	<section id="addlist" class="lists"></section>
	<div id="noDataTip" style="text-align: center; font-size: 15px; color: #666;
		padding-top: 50px; font-weight: bold; display: none;">未搜索到相关门票订单信息...</div>
    <div id="pullUp" class="loading" style="display: none;">
        <span class="pullUpIcon"></span>
        <span class="pullUpLabel">Loading...</span>
    </div>
	<!--end 页面内容 -->
	
	<div id="temp" style="display: none;">
		<div class="list">
	        <h3>订单号：<span>#orderCode#</span> <span>#status#</span></h3>
	        <div class="detail">
				<a href="${ctx}/mpo/detail?openid=${openid}&orderId=#orderId#"><dl>
	            	<dt><img src="#image#"></dt>
	                <dd><span>数量:#number#</span>#name#</dd>
	                <dd>#address#</dd>
	                <dd>#intro#</dd>
	            </dl></a>
			</div>
	    </div>
	</div>
</body>
</html>