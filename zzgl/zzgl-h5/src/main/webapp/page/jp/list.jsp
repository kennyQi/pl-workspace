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
	<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
	<link rel="Stylesheet" href="${ctx}/css/fbase.css">
	<link rel="Stylesheet" href="${ctx}/css/flight.css">
	<link rel="stylesheet" href="${ctx}/css/hgsl_fly.css">
	 <script type="text/javascript" src="${ctx}/js/jquery.js"></script> 
	 	 <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 
	 	 	 <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script> 	
	 	 	  <script type="text/javascript" src="${ctx}/js/json2.js"></script> 	
	 	 	   <script type="text/javascript" src="${ctx}/js/jsbox.js"></script> 	
	<script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script>
	<script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>
	 	 	    	 <script type="text/javascript" src="${ctx}/js/hsl.js"></script>
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
			try{
			ajaxLoad('price', tab_price_desc);
			}catch(e){
				alert(e);
			}
		});
		function ajaxLoad(sort, desc) {
			$("#addlist").empty();
			$.pop.lock(true);
		    $.pop.load(true, '努力加载中...');
		    $.ajax({
		    	url:'${ctx}/jp/ajaxList?openid=${openid}&sort=' + sort + '&desc=' + desc,
		    	data:{orgCity:'${condition.orgCity}',dstCity:'${condition.dstCity}',startDate:'${condition.startDate}'},
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
				    		$("#flightCount").html(data.flightCount);
			    			load(data.flightList,data.buildFeeAndOilFee);
					    } else if (data.result == "-1") {
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
		function load(flights,buildFeeAndOilFee) {
			
			var flightTemp = $("#flightTemp").html();
			var classTemp = $("#classTemp").html();
			var flightObj=eval("("+flights+")");
			for (var i = 0; i < flightObj.length; i++) {
				var flight = flightObj[i];
				var flightChildren = flightTemp;
				flightChildren = flightChildren
					.replace(/#startTime#/gi, format(flight.startTime,'HH:mm'))
					.replace(/#endTime#/gi, format(flight.endTime,'HH:mm'))
					.replace(/#airCompanyName#/gi, flight.airCompanyName)
					.replace(/#flightno#/gi, flight.flightno)
					//.replace(/#airComp#/gi, flight.airComp)
					//.replace(/#stopNumber#/gi, flight.stopNumber)
					
					.replace(/#planeType#/gi, flight.planeType)
					.replace(/#minPic#/gi, parseInt(flight.minPic))
					.replace(/#buildFeeAndOilFee#/gi, buildFeeAndOilFee);
					/* .replace(/#cabinSales#/gi, classInfos[classInfos.length-1].cabinSales)
					.replace(/#cabinName#/gi, classInfos[classInfos.length-1].cabinName)
					.replace(/#cabinDiscount#/gi, (classInfos[classInfos.length-1].cabinDiscount / 10)); */
					
				var classInfos = flight.cabinList;
				var classes = "";
				for (var j = classInfos.length-1; j >=0; j--) {
					var classInfo = classInfos[j];
					var classChildren = classTemp;
					classChildren = classChildren.replace(/#flightno#/gi, flight.flightno)
						.replace(/#airComp#/gi, flight.airComp)
						.replace(/#stopNumber#/gi, flight.stopNumber)
						.replace(/#planeType#/gi, flight.planeType)
						.replace(/#airCompanyName#/gi, flight.airCompanyName)
						.replace(/#departTerm#/gi, flight.departTerm)
						.replace(/#arrivalTerm#/gi, flight.arrivalTerm)	
						.replace(/#startTime#/gi, format(flight.startTime,'yyyy-MM-dd HH:mm:ss'))
					    .replace(/#endTime#/gi, format(flight.endTime,'yyyy-MM-dd HH:mm:ss'))
					    .replace(/#orgCityName#/gi, flight.orgCityName)
					    .replace(/#dstCityName#/gi, flight.dstCityName)
						
						.replace(/#cabinCode#/gi, classInfo.cabinCode)
						.replace(/#cabinSales#/gi, classInfo.cabinSales)
						.replace(/#cabinName#/gi, classInfo.cabinName)
						.replace(/#cabinDiscount#/gi, (classInfo.cabinDiscount / 10))
						.replace(/#ibePrice#/gi, classInfo.ibePrice)
						.replace(/#encryptString#/gi, classInfo.encryptString);
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
			});
		}
		
		//机票预定
		function order(li) {
			var flightNo = $(li).find("input[name='flightno']").val();
			var cabinCode = $(li).find("input[name='cabinCode']").val();
			var cabinName = $(li).find("input[name='cabinName']").val();
			var cabinDiscount = $(li).find("input[name='cabinDiscount']").val();
			var date = $("#flightDate").html();
			var encryptString=$(li).find("input[name='encryptString']").val();
			
			var airComp=$(li).find("input[name='airComp']").val();
			var planeType=$(li).find("input[name='planeType']").val();
			var stopNumber=$(li).find("input[name='stopNumber']").val();
			var departTerm=$(li).find("input[name='departTerm']").val();
			var airCompanyName=$(li).find("input[name='airCompanyName']").val();
			var arrivalTerm=$(li).find("input[name='arrivalTerm']").val();
			var startTime=$(li).find("input[name='startTime']").val();
			var endTime=$(li).find("input[name='endTime']").val();
			var departAirport=$(li).find("input[name='departAirport']").val();
			var arrivalAirport=$(li).find("input[name='arrivalAirport']").val();
			
			var orderParams="&flightNO="+ flightNo + "&startDate=" + date + "&cabinCode=" + cabinCode 
					+"&cabinName=" + cabinName
					+"&encryptString="+encryptString+"&cabinDiscount=" +cabinDiscount + "&${params}"
					+"&airCompanyName="+airCompanyName+"&airComp="+airComp+"&planeType="+planeType
					+"&stopNumber="+stopNumber+"&departTerm="+departTerm+"&arrivalTerm="+arrivalTerm
					+"&departAirport="+departAirport+"&arrivalAirport="+arrivalAirport+"&startTime="+startTime+"&endTime="+endTime;
			//alert(orderParams);		
			location.href = "${ctx}/jpo/settle?openid=${openid}"+ orderParams;
					
					
		}
		
		//将毫秒时间格式化
		var format = function(time, format){
		    var t = new Date(time);
		    var tf = function(i){return (i < 10 ? '0' : '') + i};
		    return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
		        switch(a){
		            case 'yyyy':
		                return tf(t.getFullYear());
		                break;
		            case 'MM':
		                return tf(t.getMonth() + 1);
		                break;
		            case 'mm':
		                return tf(t.getMinutes());
		                break;
		            case 'dd':
		                return tf(t.getDate());
		                break;
		            case 'HH':
		                return tf(t.getHours());
		                break;
		            case 'ss':
		                return tf(t.getSeconds());
		                break;
		        }
		    })
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
			<span id="flightDate" class="date">${condition.startDate }</span>
			）
		</h1>
		<div class="left-head">
			<a id="goBack" href="${ctx}/jp/init?openid=${openid}" class="tc_back head-btn">
				<span class="inset_shadow">
					<span class="head-return"></span>
				</span>
			</a>
		</div>
		
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
			
			
				<li class="f_info"> 
					<i><strong>#startTime#</strong>-<strong>#endTime#</strong></i>
					&nbsp;&nbsp;机型：#planeType#<!-- #cabinDiscount#折 -->
					<p>#airCompanyName# &nbsp;#flightno# </p>
				</li>
				<li class="f_price"> <dfn>¥</dfn> <strong>#minPic#起</strong>
				<%--	<p>
						机建+燃油:
						<em class="f_num">#buildFeeAndOilFee#</em>
						<!-- #cabinName#
						<em>#cabinSales#张</em> -->
					</p>--%>
					<span class="fly_order_btn"></span>
				</li>
				<li class="flight-listfold js_subswitch flight-packup"></li>
			</ul>
			<ul class="f_detail js_sublist">#flight.classes#</ul>
		</div>
	</div>
	
	<div id="classTemp" style="display: none;">
		<li onclick="order(this);">
			<input type="hidden" name="flightno" value="#flightno#">
			<input type="hidden" name="cabinCode" value="#cabinCode#">
			<input type="hidden" name="cabinName" value="#cabinName#">
			<input type="hidden" name="cabinDiscount" value="#cabinDiscount#">
			<input type="hidden" name="encryptString" value="#encryptString#">
			
			<input type="hidden" name="airComp" value="#airComp#"/>
			<input type="hidden" name="planeType" value="#planeType#"/>
			<input type="hidden" name="stopNumber" value="#stopNumber#"/>
			<input type="hidden" name="airCompanyName" value="#airCompanyName#"/>
			<input type="hidden" name="departTerm" value="#departTerm#"/>
			<input type="hidden" name="arrivalTerm" value="#arrivalTerm#"/>
			<input type="hidden" name="startTime" value="#startTime#"/>
			<input type="hidden" name="endTime" value="#endTime#"/>
			<input type="hidden" name="departAirport" value="#orgCityName#"/>
			<input type="hidden" name="arrivalAirport" value="#dstCityName#"/>
			
			
			<span class="xq_info">
				#cabinName#
				<!-- <em>#cabinSales#张</em> -->
				<em>#cabinDiscount#折</em>
				<em>退改签</em>
			</span>
			<span class="xq_price"> <dfn>¥</dfn>
				<strong>#ibePrice#起</strong>
			</span>
			<span class="xq_price_btn">

			</span>
		</li>
	</div>
	<style>
	.f_info strong{font-weight:normal;font-size:14px;}
	</style>
</body>
</html>