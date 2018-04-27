<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
﻿<!DOCTYPE html>
<html>
<head>
<title>票量旅游</title>
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
<%@ include file="/page/common/jscommon.jsp"%>
<script type="text/javascript"><!--
	var statusMap = '${statusMap}';
	statusMap = eval("("+statusMap+")");

	var pageNo = 1;
	$(document).ready(function() {
		$.pop.lock(true);
	    $.pop.load(true, '努力加载中...');
	     ajaxLoad(true, true);
	});
	
	function ajaxLoad(isFir, isAsync) {
		scrollBottomOff();
		$.ajax({
	    	url:'${ctx}/jpo/ajaxList?pageNo=' + pageNo + '&pageSize=15&openid=${openid}',
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
			    		load(data.jpOrders);
			    		if (data.jpOrders.length <15) {
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
			//alert(order.status+order.payStatus);
			var status = "";
			if(!order.payStatus||!order.status){
				status = "无结果";
			}else{
				status = ''+order.status+order.payStatus;
				if(statusMap[status]){
					status = statusMap[status];
				}else{
					status = '状态异常';
				}
			}
			var child = temp;
			child = child.replace(/#orderNo#/gi, order.dealerOrderCode)
				.replace(/#orderSt#/gi, status)
				.replace(/#startDate#/gi, order.flightDTO.startDate)
				.replace(/#airCompName#/gi, order.flightDTO.airCompName)
				.replace(/#flightNo#/gi, order.flightDTO.flightNo)
				.replace(/#aircraftName#/gi, order.flightDTO.aircraftName)
				.replace(/#startTime#/gi, order.flightDTO.startTime)
				.replace(/#startPortName#/gi, order.flightDTO.startPortName)
				.replace(/#endTime#/gi, order.flightDTO.endTime)
				.replace(/#endPortName#/gi, order.flightDTO.endPortName)
				.replace(/#payAmount#/gi, order.payAmount);
			$("#addlist").append($(child));
		}
	}
	function detail(orderId) {
		location.href = "${ctx}/jpo/detail?openid=${openid}&orderId=" + orderId;
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
--></script>
<style>
	#pullUp {
        height:40px;
        line-height:40px;
        margin-bottom: 5px;
        font-weight:bold;
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
		<h1>机票订票</h1>
	 	<div class="left-head">
                <a id="goBack" href="${ctx }/user/center" class="tc_back head-btn">
                    <span class="inset_shadow">
                        <span class="head-return"></span>
                    </span>
                </a>
            </div>

            <div class="right-head">
                <a href="${ctx}/index?openid=${openid}" class="head-btn fn-hide">
                    <span class="inset_shadow">
                        <span class="head-home"></span>
                    </span>
                </a>
            </div>
	</header>
	<div class="table">
	  	<a class="active" href="${ctx}/user/jpos?openid=${openid}">机票订单</a>
	  	<a href="${ctx}/user/mpos?openid=${openid}">门票订单</a>
	</div>
	<!--end 页头  -->

	<!--start 页面内容 -->

	<section id="addlist" class="lists"></section>
	<div id="noDataTip" style="text-align: center; font-size: 15px; color: #666;
		padding-top: 50px; font-weight: bold; display: none;">未搜索到相关机票订单信息...</div>
    <div id="pullUp" class="loading" style="display:none;">
        <span class="pullUpIcon"></span>
        <span class="pullUpLabel">Loading...</span>
    </div>
	<!--end 页面内容 -->

	<div id="temp" style="display: none;">
		<div class="list">
	        <h3>订单号：<span>#orderNo#</span> <span>#orderSt#</span></h3>
	        <div class="planeMsg" onclick="detail('#orderNo#')">
	        	<div class="plane">
		        	<p>
		        		<span></span>
		        		<strong class="date">#startDate#</strong> 
		        		<span class="com">#airCompName#</span>
		        		<span class="model">#flightNo#<br/>#aircraftName#</span>
		        	</p>
		        </div>
		        <div class="time">
		        	<p>#startTime# <span>#startPortName#</span></p>
		        	<p>#endTime# <span>#endPortName#</span></p>
		        </div>
		        <div class="price"><p>&yen;#payAmount#</p></div>
	        </div>
	    </div>
	</div>
</body>
</html>