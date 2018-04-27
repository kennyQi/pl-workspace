<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>票量旅游</title>
    <!-- meta信息，可维护 -->
    <meta charset="UTF-8" />
    <title>景点搜索</title>
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta content="telephone=no" name="format-detection" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <!-- 公共样式 -->
    <link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/ticket.css">
	<%@ include file="/page/common/jscommon.jsp"%>
    <script type="text/javascript" src="${ctx}/js/ticket.js"></script>
	<script type="text/javascript">
		var pageNo = 1;
		$(document).ready(function() {
			$.pop.lock(true);
			$.pop.load(true, '努力加载中...');
			load(true, true);
		});
		function load(isFir, isAsync) {
			scrollBottomOff();
			$.ajax({
				url:'${ctx}/mp/ajaxList?openid=${openid}',
				type:'post',
				data:{'pageNo':pageNo,'pageSize':15,'hot':false,
					'byArea':true,'byName':true,'content':$("#content").val()},
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
						if (data.result != 1 &&
								data.result != -1) {
							scrollBottomOff();
							setTimeout(function() {
					    		$.pop.tips('系统繁忙,请稍候');
					    	}, 666);
						} else {
							if (data.result == -1) {
								if (isFir) {
									$("#pullUp").hide();
									$("#noDataTip").show();
								} 
							} else {
								if (isFir) {
									$("#pullUp").show();
									$("#noDataTip").hide();
								}
								list(data.scenicSpots);
								if (data.scenicSpots.length < 15) {
									$("#pullUp").hide();
								} else {
									scrollBottomOn();
								}
								pageNo++;
							}
						}
					});
				}
			});
		}
		function reload() {
			var comb=/^[a-zA-Z0-9\u4e00-\u9fa5\s]{0,100}$/;
			if(!comb.test($("#content").val())){
				$.pop.tips("景点名字不正确");
				return false;
			}
			$.pop.lock(true);
			$.pop.load(true, '努力加载中...');
			$("#addlist").empty();
			pageNo = 1;
			load(true, true);
		}
		
		function list(scenicSpots) {
			var temp = $("#temp").html();
			for (var i = 0; i < scenicSpots.length; i++) {
				var scenicSpot = scenicSpots[i];
				var child = temp;
				child = child.replace(/#link#/gi, 
					"${ctx}/mp/detail?openid=${openid}&scenicSpotId=" + scenicSpot.id)
					.replace(/#image#/gi, scenicSpot.scenicSpotBaseInfo.image)
					.replace(/#price#/gi, scenicSpot.bookInfoDTO.amountAdvice.toFixed(2))
					.replace(/#name#/gi, scenicSpot.scenicSpotBaseInfo.name)
					.replace(/#address#/gi, scenicSpot.scenicSpotGeographyInfo.address);
				$("#addlist").append($(child));
			}
		}
		function scrollBottomOn() {
			$(window).scroll(function() {
				var scrollTop = $(this).scrollTop();
				var scrollHeight = $(document).height();
				var windowHeight = $(this).height();
				if (scrollTop + windowHeight == scrollHeight) {
					$(".pullUpLabel").html("Loading...");
					$("#pullUp").addClass("loading");
					load(false, false);
				}
			});
		}
		function scrollBottomOff() {
			$(window).scroll(function() {
				return;
			});
		}
		function clear() {
			$("#content").val("");
			reload();
		}
	</script>

<style>
	#pullUp {
        background:#fff;
        height:40px;
        line-height:40px;
        padding:5px 10px;
        border-bottom:1px solid #ccc;
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
<body id="list">
    <!-- 公共页头  -->
    <header class="header" style="position:fixed;top:0;z-index:666;width:100%;">
       
        <div class="left-head">
            <a id="goBack" href="${ctx}/index?openid=${openid}" class="tc_back head-btn">
                <span class="inset_shadow">
                    <span class="head-return"></span>
                </span>
            </a>
        </div>
        <!--<div class="right-head">
            <a href="${ctx}/index?openid=${openid}" class="head-btn fn-hide">
                <span class="inset_shadow">
                    <span class="head-home"></span>
                </span>
            </a>
        </div>-->
        <form id="myform" action="${ctx}/mp/list?openid=${openid}" method="post" >
            <div class="search" >
                <input id="content" type="text" name="content" style="line-height: normal;"
                	value="${empty(content) ? '' : content }" placeholder="城市名/景点名/主题" class="text_input">
                <a href="javascript:clear();" style="position: absolute; right: 18%; font-size: 18px; color: #666;">×</a>
                <a class="btn" href="javascript:reload();"></a>
                <input name="pageNo" type="hidden" id="pageNo" value="1" />
                <input name="pageSize" value="15" type="hidden" />
        		<input name="hot" value="false" type="hidden" />
        		<input name="byArea" value="true" type="hidden" />
        		<input name="byName" value="true" type="hidden" />
            </div>
         </form>
    </header>
    <!-- 页面内容 -->
    <div id="wrapper" >
        <div class="content ticket" id="scroll">
        <!--start 门票列表-->
            <div id="pullDown">
                <span class="pullDownIcon"></span><span class="pullDownLabel"></span>
            </div>
            <div class="list_tour" style="margin-top:44px;">	<!-- <div class="list_tour" style="margin-top:53px;"> -->
                <!-- <ul class="tab">
                    <li class="active">热 门</li>
                    <li >价 格</li>
                </ul> -->
                <div class="list" id="addlist"></div>
				<div id="noDataTip" style="text-align: center; font-size: 15px; color: #666;
							padding-top: 50px; font-weight: bold; display: none;">未搜索到相关景点信息...</div>
                <div id="pullUp" class="loading" style="display: none;">
                    <span class="pullUpIcon"></span>
                    <span class="pullUpLabel">Loading...</span>
                </div>
            </div>
        <!--end 门票列表--> 
        </div>
    </div>

    <!--模板-->
    <div id="temp" style="display:none;">
        <dl>
            <a href="#link#">
                <dt>
                    <img  src="#image#"></dt>
                <dd>
                    <span>&yen; #price#起</span>
                    #name#
                </dd>
                <dd>#address#</dd>
            </a>
        </dl>
    </div>

</body>
</html>