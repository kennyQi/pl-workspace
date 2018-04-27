<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>中智票量</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 公共样式 -->
<link rel="stylesheet" href="${ctx }/css/base.2.0.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/css/ticket.css">
 <script type="text/javascript" src="${ctx}/js/jquery.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script> 	 <script type="text/javascript" src="${ctx}/js/json2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jsbox.js"></script> 	 <script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script><script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>	 <script type="text/javascript" src="${ctx}/js/hsl.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$.pop.lock(true);
		$.pop.load(true, '努力加载中...');
		$.ajax({
			url:'${ctx}/mp/ajaxList?openid=${openid}',
			data:{pageNo:1,pageSize:6,hot:true},
			type:'post',
			timeout:60000,
			dataType:'json',
			error:function() {
			    $.pop.load(false, function() {
	    			$.pop.lock(false);
			    	setTimeout(function() {
			    		$.pop.tips('系统繁忙,请稍候');
			    	}, 666);
			    });
	    	},
			success:function(data) {
				$.pop.load(false, function() {
					$.pop.lock(false);
					if (data.result == -1) {
						$("#noDataTip").show();
					} else if (data.result == 1) {
						load(data.scenicSpots);
					} else {
						setTimeout(function() {
				    		$.pop.tips('系统繁忙,请稍候');
				    	}, 666);
					}
				});
			}
		});
	});
	function load(scenicSpots) {
		var temp = $("#temp").html();
		for (var i = 0; i < scenicSpots.length; i++) {
			var scenicSpot = scenicSpots[i];
			var child = temp;
			child = child.replace(/#link#/gi, 
				"${ctx}/mp/detail?openid=${openid}&scenicSpotId=" + scenicSpot.id)
				.replace(/#image#/gi, scenicSpot.scenicSpotBaseInfo.image)
				.replace(/#name#/gi, scenicSpot.scenicSpotBaseInfo.name);
			$("#addlist").append($(child));
		}
	}
	function search() {
		var comb=/^[a-zA-Z0-9\u4e00-\u9fa5\s]{0,100}$/;
		if(!comb.test($("#content").val())){
			$.pop.tips("景点名字不正确");
			return false;
		}
		$("#myform").submit();
	}
	function clear() {
		$("#content").val("");
	}
</script>
</head>
<body>
	<!-- 公共页头  -->
	<header class="header">
            <h1>景点门票</h1>

            <div class="left-head">
                <a id="goBack" href="${ctx}/index?openid=${openid}" class="tc_back head-btn">
                    <span class="inset_shadow">
                        <span class="head-return"></span>
                    </span>
                </a>
            </div>

            <%--<div class="right-head">
                <a href="${ctx}/index?openid=${openid}" class="head-btn fn-hide">
                    <span class="inset_shadow">
                        <span class="head-home"></span>
                    </span>
                </a>
            </div>--%>
    </header>
    <!-- 页面内容 -->
	<div class="content ticket">
    	<!--start 搜索-->
    	<form id="myform" action="${ctx}/mp/list?openid=${openid}" method="post">
	    	<div class="search">
	        	<input id="content" name="content" type="text" class="text_input" 
	        			style="line-height: normal;" placeholder="城市名/景点名/主题" />
	        	<a href="javascript:clear();" style="position: absolute; right: 17%; font-size: 18px; color: #666;">×</a>
	        	<a class="btn" href="javascript:search();"></a>
	        </div>
        </form>
        <!--end 搜索-->
        <!--start 门票列表-->
        <div class="list_ticket">
        	<h3>他们都爱去</h3>
        	<ul id="addlist"></ul>
        </div>
        <div id="noDataTip" style="font-size:16px; font-weight: bold; text-align: center; display: none;">未搜索到热门景点...</div>
        <!--end 门票列表-->
    </div>
    
    <div id="temp" style="display: none;">
    	<li>
        	<a href="#link#">
                <img src="#image#">
                <p>#name#</p>
            </a>
        </li>
    </div>
    
</body>
</html>
