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
<link rel="Stylesheet" href="${ctx}/css/fbase.css">
<link rel="Stylesheet" href="${ctx}/css/flight.css" />
<%@ include file="/page/common/jscommon.jsp"%>
<script type="text/javascript" src="${ctx }/js/rili.js"></script>
<script type="text/javascript" src="${ctx }/js/fly.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$.pop.lock(true);
	    $.pop.load(true, '努力加载中...');
	    $.ajax({
	    	url:'${ctx}/jp/detail?openid=${openid}',
	    	data:{flightNo:'${condition.flightNo}',date:'${condition.date}'},
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
	    			if (data.result == '1') {
			    		load(data.flightList[0].flightClassList);
				    } else if (data.result == '-1') {
				    	$("#noDataTip").show();
				    } else {
				    	setTimeout(function() {
				    		$.pop.tips('系统繁忙,请稍候');
				    	}, 666);
				    }
			    });
	    	}
	    });
	});
	
	function load(classes) {
		var temp = $("#temp").html();
		for (var i = 0; i < classes.length; i++) {
			var clazz = classes[i];
			var child = temp;
			child = child.replace(/#classCode#/gi, clazz.classCode)
				.replace(/#classType#/gi, JP.getClassType(clazz.classCode))
				.replace(/#lastSeat#/gi, JP.getJPNumber(clazz.lastSeat))
				.replace(/#discount#/gi, (clazz.discount / 10))
				.replace(/#settleAccounts#/gi, parseInt(clazz.settleAccounts) + "起");
			$("#addlist").append($(child));
		}
		var aLi=$("#cabinList li");
	   	aLi.each(function(){
	    	$(this).click(function(){
	        	aLi.find(".get").html("");
	         	aLi.css("border","1px solid #bcbcbc");
	         	$(this).find(".get").html("<i class='flight-icon-rgt'></i>");
	         	$(this).css("border","2px solid #3cafdc");
	         	var classCode = $(this).attr("classCode");
	         	var discount = $(this).attr("discount");
	         	setTimeout(function() {
	            	window.location.href = "${ctx}/jpo/settle?openid=${openid}&classCode=" + classCode + 
	            			"&discount=" + discount + "&flightNo=${condition.flightNo}&date=${condition.date}&${params}";
	          	},666);
	      	});
	    });
	}
</script>
<style>
body {
	padding-bottom: 50px;
}
</style>
</head>
<body>
<!-- 公共页头  -->
<header class="header">
  <h1>选择舱位</h1>
  <div class="left-head"> <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn"><span class="inset_shadow"><span class="head-return"></span></span></a> </div>
  <%--<div class="right-head"> <a href="/home/index.html" class="head-btn fn-hide"><span class="inset_shadow"><span class="head-home"></span></span></a> </div>--%>
</header>
<!-- 页面内容块 -->
<!--S H5机票_机票查询-->
<div class="pd10" id="cabinList">
    <ul id="addlist" class="flight-list-table mt0"></ul>
    <div id="noDataTip" style="text-align: center; font-size: 15px; color: #666;
			padding-top: 50px; font-weight: bold; display: none;">未搜索到相关舱位信息...</div>
</div>
<div id="temp" style="display: none;">
	<li classCode="#classCode#" discount=#discount#>
        <div class="flight-list-tablebox" data-key="0">
            <p class="canwei f_ico">#classType#&nbsp;&nbsp;#lastSeat#张</p>
            <p class="zhekou">#discount#折</p>
            <p class="price"><dfn>¥</dfn><span>#settleAccounts#</span></p>
            <p class="get"></p>
        </div>
    </li>
</div>
<!--E H5机票_机票查询-->
<!-- 公共页脚  --> 
<!-- 公共页脚  -->
</body>
</html>
