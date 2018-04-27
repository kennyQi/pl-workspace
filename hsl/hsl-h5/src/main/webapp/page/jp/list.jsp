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
	<link rel="Stylesheet" href="${ctx}/css/flight.css">
	<link rel="stylesheet" href="${ctx}/css/hgsl_fly.css">
	<%@ include file="/page/common/jscommon.jsp"%>
	<script type="text/javascript" src="${ctx}/js/rili.js"></script>
	<script type="text/javascript" src="${ctx}/js/fly.js"></script>
	<script type="text/javascript">
		var tab_price_desc = false;
		var tab_date_desc = false;
		var first = true;
		$(document).ready(function() {
			$('#tab_price').click(function() {
				$(".tab_by li").removeClass("hover");
				$(this).addClass("hover");
				var icon = $(this).find("em").html();
				if (icon == "↑"){
		        	$(this).find("em").html("↓");
		        } else if (icon=="↓"){
		         	$(this).find("em").html("↑");
		        }
				tab_price_desc = !tab_price_desc;
				ajaxLoad('price', tab_price_desc);
			});
			$('#tab_date').click(function() {
				$(".tab_by li").removeClass("hover");
				$(this).addClass("hover");
				var icon = $(this).find("em").html();
				if (first) {
					first = !first;
				} else {
					if (icon == "↑"){
			        	$(this).find("em").html("↓");
			        } else if (icon=="↓"){
			         	$(this).find("em").html("↑");
			        }
			        tab_date_desc = !tab_date_desc;
				}
				ajaxLoad('date', tab_date_desc);
			});
			ajaxLoad('price', tab_price_desc);
		});
		function ajaxLoad(sort, desc) {
			$("#addlist").empty();
			$.pop.lock(true);
		    $.pop.load(true, '努力加载中...');
		    $.ajax({
		    	url:'${ctx}/jp/ajaxList?openid=${openid}&sort=' + sort + '&desc=' + desc,
		    	data:{from:'${condition.from}',arrive:'${condition.arrive}',date:'${condition.date}'},
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
				    		$("#flightCount").html(data.flightList.length);
			    			load(data.flightList);
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
		}
		function load(flights) {
			var flightTemp = $("#flightTemp").html();
			var classTemp = $("#classTemp").html();
			for (var i = 0; i < flights.length; i++) {
				var flight = flights[i];
				var flightChildren = flightTemp;
				flightChildren = flightChildren.replace(/#startTime#/gi, flight.startTime)
					.replace(/#endTime#/gi, flight.endTime).replace(/#airCompName#/gi, flight.airCompName)
					.replace(/#flightNo#/gi, flight.flightNo).replace(/#aircraftName#/gi, flight.aircraftName)
					.replace(/#cheapestFlightClass.settleAccounts#/gi, parseInt(flight.cheapestFlightClass.settleAccounts))
					.replace(/#cheapestFlightClass.classType#/gi, JP.getClassType(flight.cheapestFlightClass.classCode))
					.replace(/#cheapestFlightClass.lastSeat#/gi, JP.getJPNumber(flight.cheapestFlightClass.lastSeat))
					.replace(/#cheapestFlightClass.discount#/gi, flight.cheapestFlightClass.discount / 10);
				var classInfos = flight.flightClassList;
				var classes = "";
				for (var j = 0; j < classInfos.length; j++) {
					var classInfo = classInfos[j];
					var classChildren = classTemp;
					classChildren = classChildren.replace(/#flightNo#/gi, flight.flightNo)
						.replace(/#classCode#/gi, classInfo.classCode).replace(/#classType#/gi, JP.getClassType(classInfo.classCode))
						.replace(/#lastSeat#/gi, JP.getJPNumber(classInfo.lastSeat)).replace(/#discount#/gi, (classInfo.discount / 10))
						.replace(/#settleAccounts#/gi, parseInt(classInfo.settleAccounts) + "起");
					classes += classChildren;
				}
				flightChildren = flightChildren.replace(/#flight.classes#/gi, classes);
				$("#addlist").append($(flightChildren));
			}
			$(".f_detail").hide();
			$(".flight-listfold").each(function(){
				$(this).click(function() {
					$(this).parent().parent().find(".f_detail").slideToggle();
					if($(this).hasClass("flight-packup")){
						$(this).removeClass("flight-packup");
						$(this).addClass("flight-packdown");
					}else{
						$(this).removeClass("flight-packdown");
						$(this).addClass("flight-packup");
					}
				});
			})
		}
		function order(li) {
			var flightNo = $(li).find("input[name='flightNo']").val();
			var classCode = $(li).find("input[name='classCode']").val();
			var discount = $(li).find("input[name='discount']").val();
			var date = $("#flightDate").html();
			location.href = "${ctx}/jpo/settle?openid=${openids}&flightNo=" + flightNo + 
					"&date=" + date + "&classCode=" + classCode + "&discount=" + discount + "&${params}";
		}
	</script>
</head>
<body>
	<!-- 公共页头  -->
	<header class="header">
		<h1 class="small">
			<span class="city">${startCityName } — ${endCityName }</span>
			<br/>
			（
			<span id="flightDate" class="date">${condition.date }</span>
			）
		</h1>
		<div class="left-head">
			<a id="goBack" href="${ctx}/jp/init?openid=${openid}" class="tc_back head-btn">
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
        </div> --%>
		<div class="right-head">
			<p>共<span id="flightCount"></span>条结果</p>
		</div>
	</header>
	<section>
		<!-- 页面内容块 -->
		<ul class="tab_by" style="width:100%;">
			<li id="tab_price" data-type="price" style="width:50%;" class="byprice hover">
				价格 <em>↑</em>
			</li>
			<li id="tab_date" data-type="time" style="width:50%;" class="bytime">
				时间 <em >↑</em>
			</li>
		</ul>
		<div id="addlist"></div>
		<div id="noDataTip" style="text-align: center; font-size: 15px; color: #666;
			padding-top: 50px; font-weight: bold; display: none;">未搜索到相关航班信息...</div>
	</section>
	<div id="flightTemp" style="display: none;">
		<div class="list">
			<ul class="f_list_tab js_flight_item" data-key="0">
				<li class="f_info"> <i><strong>#startTime#</strong>
						-#endTime#</i>&nbsp;&nbsp;#cheapestFlightClass.discount#折
					<%-- #startCity#-#endCity# --%>
					<p>#airCompName# #flightNo# &nbsp;#aircraftName#</p>
				</li>
				<li class="f_price"> <dfn>¥</dfn> <strong>#cheapestFlightClass.settleAccounts#起</strong>
					<p>
						#cheapestFlightClass.classType#
						<em class="f_num">#cheapestFlightClass.lastSeat#张</em>
					</p>
				</li>
				<li class="flight-listfold js_subswitch flight-packup"></li>
			</ul>
			<ul class="f_detail js_sublist">#flight.classes#</ul>
		</div>
	</div>
	<div id="classTemp" style="display: none;">
		<li onclick="order(this);">
			<input type="hidden" name="flightNo" value="#flightNo#">
			<input type="hidden" name="classCode" value="#classCode#">
			<input type="hidden" name="discount" value="#discount#">
			<span class="xq_info">
				#classType#
				<em>#lastSeat#张</em>
				<em>#discount#折</em>
			</span>
			<span class="xq_price"> <dfn>¥</dfn>
				<strong>#settleAccounts#</strong>
			</span>
		</li>
	</div>
</body>
</html>