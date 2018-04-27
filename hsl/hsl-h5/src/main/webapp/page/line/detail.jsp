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
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 公共样式 -->
<%@ include file="/page/common/jscommon.jsp"%>
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/circuit.css" />
<link rel="stylesheet" href="${ctx}/css/mCal.css" />
<script src="${ctx}/js/iscroll.js" type="text/javascript"></script>
<script src="${ctx}/js/main.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/js/ticket.js"></script>
<script type="text/javascript" src="${ctx}/js/rili_line_price.js"></script>
<script type="text/javascript">
	var myScroll;
	function loaded() {
		myScroll = new iScroll(
				'wrapper',
				{
					snap : true,
					momentum : false,
					hScrollbar : false,
					onScrollEnd : function() {
						document.querySelector('#indicator > li.active').className = '';
						document.querySelector('#indicator > li:nth-child('
								+ (this.currPageX + 1) + ')').className = 'active';
						//页码到了最大，重置为负值重新开始
						  if(this.currPageX >= this.pagesX.length-1){
								this.currPageX = -2;
							}
					}
				});

	}
	document.addEventListener('DOMContentLoaded', loaded, false);
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		WeixinJSBridge.call('hideToolbar');
	});
	/*页面加载函数
	 *初始化价格日历
	 */
	$(function() {
		overHide();
		dateHide();
		var datePrices = $.parseJSON('${datePrices}');
		console.info("价格日历：" + '${datePrices}');
		rili(datePrices);//日历
		dateTime();//日历显示隐藏
		selectDay();//选择日期
	});
	function dateHide() {
		$("#gohide").click(function() {
			$("#calContainer").hide();
			$("#contWrap").show();
		});
	}
	//选着日期
	function selectDay() {
		$("#calContainer .low_calendar tr").each(function() {
			$(this).delegate("td", "click", function() {
				var date = $(this).attr("date");
				$(".low_calendar tr td").removeClass("active");
				$(this).addClass("active");//选中的日期
				var index = $(this).index();
				var price = $(this).find("span").html();
				if (price != null && price != '') {
					price = parseInt(price.substring(1, price.length));
					$("#priceVal").html(price.toFixed(2));
				}
				$("#travelDate").val(date);
				//		    var arr=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"]; 
				//		    var weekday=arr[index];
				//		    date=date+" &nbsp;"+weekday;
				var a = $("#adultPrice").val();
				var c = $("#childPrice").val();
				date = date + " &nbsp;成人价：" + a + "&nbsp;儿童价：" + c;
				$(".select span").html(date);
				$("#calContainer").hide();
				$("#contWrap").show();
			});
		});
	}
</script>
</head>
<body>
	<!-- 页头  -->
	<div id="contWrap">
		<header class="header">
			<h1>线路详情</h1>
			<div class="left-head">
				<a id="goBack" href="${ctx}/line/list" class="tc_back head-btn">
					<span class="inset_shadow"> <span class="head-return"></span>
				</span>
				</a>
			</div>
		</header>
		<!-- 页头 -->
		<section id="tickerContent">
			<section>
				<div class="circuit">
					<div class="banner" style="margin:0px; padding:0px;">
						<div style="overflow: hidden;" id="wrapper">
							<div id="scroller">
								<ul id="thelist">
									<c:if test="${!empty pictureSiteLists}">
										<c:forEach items="${pictureSiteLists}" var="pictureSite">
											<li><a href="#"> <img src="${pictureSite}"></a></li>
										</c:forEach>
									</c:if>
								</ul>
							</div>
						</div>
						<div id="nav">
							<ul id="indicator">
								<c:if test="${fn:length(pictureSiteLists) > 0}">
									<c:forEach begin="1" end="${fn:length(pictureSiteLists)}" var="folder" varStatus="i">
										<c:if test="${i.count == 1}">
											<li class="active">${i.count}</li>										
										</c:if>
										<c:if test="${i.count > 1}">
											<li class="">${i.count}</li>										
										</c:if>
									</c:forEach>
								</c:if>
							</ul>
						</div>
						<div class="clr"></div>
					</div>
					<div class="circuit_list mt10 clear circuitview borderb">
						<a href="#">
							<div class="c-route-title">${line.baseInfo.name}</div>
							<div class="c-route nowrap">
								<strong>行程概览:</strong> <span> <c:if
										test="${!empty line.baseInfo.destinationCity}">
					出发地<i>></i>
										<c:forEach
											items="${fn:split(line.baseInfo.destinationCity,',')}"
											var="destination" varStatus="i"> 
						${cityMap[destination]}<i>></i>
										</c:forEach>
					出发地<i></i>
									</c:if>
								</span>
							</div>
							<div class="c-sp">
								<div class="star fn-left">
									<p>
										<c:if test="${line.baseInfo.type == 1}">
											<span class="radius">跟团游</span>
											<i class="s s${line.baseInfo.recommendationLevel}"></i>
										</c:if>
										<c:if test="${line.baseInfo.type == 2}">
											<span class="radius">自助游</span>
											<i class="s s${line.baseInfo.recommendationLevel}"></i>
										</c:if>
									</p>
									<p class="bianhao">编号${line.baseInfo.number}</p>
								</div>
								<div class="c_price fn-right">
									<i>￥</i><span>${line.minPrice==null?0:line.minPrice}</span><i>/人起</i>
								</div>
							</div>
							<div class="c_text bordert clear pt10 ">
								<p>本产品由杭州票量旅行社及具有资质的合作旅行社提供相关服务</p>
							</div>
						</a>
					</div>
					<div id="tickerContent">
						<ul class="c_text_list mt10 time">
							<li class="select"><a><i></i><span>出行价格日历</span><em></em></a></li>
							<li><a href="${ctx}/line/favorable?id=${line.id}"><i
									class="sale"></i><span>优惠活动</span><em></em></a></li>
							<li class="last"><a href="${ctx}/line/feature?id=${line.id}"><i
									class="xianlvtese"></i><span>线路特色</span><em></em></a></li>
						</ul>
					</div>
					<div class="c_view">
						<div class="c_view_title">
							<h3>行程详情</h3>
							<i><a href="${ctx}/line/allDayRoute?id=${line.id}">全部行程</a></i>
						</div>
						<!-- 行程开始  -->
						<c:forEach items="${line.routeInfo.dayRouteList}" var="dayRoute">
							<c:if test="${dayRoute.days == 1}">
								<div class="c_v_list">
									<div class="c_v_lsit_title">
										<i></i> <span>${dayRoute.starting} <c:forEach
												items="${fn:split(dayRoute.vehicle,',')}" begin="0" end="0"
												var="vehicle">
												<c:if test="${vehicle == '1'}">
													<em class="iconair"></em>
												</c:if>
												<c:if test="${vehicle == '2'}">
													<em class="iconbus"></em>
												</c:if>
												<c:if test="${vehicle == '3'}">
													<em class="iconsteamer"></em>
												</c:if>
												<c:if test="${vehicle == '4'}">
													<em class="icontrain"></em>
												</c:if>
											</c:forEach> ${dayRoute.destination}
										</span> <em class="c_time blue"> 第1天 </em>
									</div>
									<div class="c_v_info">
										${dayRoute.schedulingDescription}
										<!-- <p><label>出&nbsp发&nbsp地</label>：杭州千岛湖</p>
	  			<p><label>集合时间：</label>12:00-17：00</p>
	  			<p><label>家庭入住：</label>杭州千岛湖滨江希尔顿酒店（五星）。</p>
	  			<p class="info_about">今日没有行程安排，酒店一般在下午14点开始办理入住，如您抵达的时间较早， 则可将行李寄存在酒店前台，自行至锦里古街，去搜罗美味</p> -->
									</div>
									<div class="c_v_break bordert">
										<div class="c_bareak_up">
											<p>
												<i></i><label>住宿：</label>
												<c:forEach items="${dayRoute.hotelList}" var="hotel"
													varStatus="i">
													<c:if test="${i.count == fn:length(dayRoute.hotelList)}">
								  						${hotel.hotelName}
								  					</c:if>
								  					<c:if test="${i.count != fn:length(dayRoute.hotelList)}">
								  						${hotel.hotelName} 或
								  					</c:if>
								  				</c:forEach>
												<c:if test="${fn:length(dayRoute.hotelList) > 0}">
												或 同级
								  				</c:if>
											</p>
										</div>
										<div class="c_bareak_zzw clear">
											<span><i></i><label>早餐：</label> <c:if
													test="${dayRoute.includeBreakfast}">
							  					包含
							  				</c:if> <c:if test="${!dayRoute.includeBreakfast}">
							  					敬请自理
							  				</c:if> </span> <span><i></i><label>午餐：</label> <c:if
													test="${dayRoute.includeLunch}">
							  					包含
							  				</c:if> <c:if test="${!dayRoute.includeLunch}">
							  					敬请自理
							  				</c:if> </span> <span><i></i><label>晚餐：</label> <c:if
													test="${dayRoute.includeDinner}">
							  					包含
							  				</c:if> <c:if test="${!dayRoute.includeDinner}">
							  					敬请自理
							  				</c:if> </span>
										</div>
									</div>
								</div>
							</c:if>
							<c:if test="${dayRoute.days == 2}">
								<div class="c_v_list">
									<div class="c_v_lsit_title">
										<i></i><span>${dayRoute.starting} <c:forEach
												items="${fn:split(dayRoute.vehicle,',')}" begin="0" end="0"
												var="vehicle">
												<c:if test="${vehicle == '1'}">
													<em class="iconair"></em>
												</c:if>
												<c:if test="${vehicle == '2'}">
													<em class="iconbus"></em>
												</c:if>
												<c:if test="${vehicle == '3'}">
													<em class="iconsteamer"></em>
												</c:if>
												<c:if test="${vehicle == '4'}">
													<em class="icontrain"></em>
												</c:if>
											</c:forEach> ${dayRoute.destination}
										</span> <em class="c_time blue"> 第2天 </em>
									</div>
									<div class="c_v_info">
										${dayRoute.schedulingDescription}
										<!-- <p><label>出&nbsp发&nbsp地</label>：杭州千岛湖</p>
	  			<p><label>集合时间：</label>12:00-17：00</p>
	  			<p><label>家庭入住：</label>杭州千岛湖滨江希尔顿酒店（五星）。</p>
	  			<p class="info_about">今日没有行程安排，酒店一般在下午14点开始办理入住，如您抵达的时间较早， 则可将行李寄存在酒店前台，自行至锦里古街，去搜罗美味</p> -->
									</div>
									<div class="c_v_break bordert">
										<div class="c_bareak_up">
											<p>
												<i></i><label>住宿：</label>
												<c:forEach items="${dayRoute.hotelList}" var="hotel"
													varStatus="i">
													<c:if test="${i.count == fn:length(dayRoute.hotelList)}">
									  						${hotel.hotelName}
								  					</c:if>
								  					<c:if test="${i.count != fn:length(dayRoute.hotelList)}">
								  						${hotel.hotelName} 或
								  					</c:if>
								  				</c:forEach>
												<c:if test="${fn:length(dayRoute.hotelList) > 0}">
												或 同级
								  				</c:if>
											</p>
										</div>
										<div class="c_bareak_zzw clear">
											<span><i></i><label>早餐：</label> <c:if
													test="${dayRoute.includeBreakfast}">
							  					包含
							  				</c:if> <c:if test="${!dayRoute.includeBreakfast}">
							  					敬请自理
							  				</c:if> </span> <span><i></i><label>午餐：</label> <c:if
													test="${dayRoute.includeLunch}">
							  					包含
							  				</c:if> <c:if test="${!dayRoute.includeLunch}">
							  					敬请自理
							  				</c:if> </span> <span><i></i><label>晚餐：</label> <c:if
													test="${dayRoute.includeDinner}">
							  					包含
							  				</c:if> <c:if test="${!dayRoute.includeDinner}">
							  					敬请自理
							  				</c:if> </span>
										</div>
									</div>
								</div>
							</c:if>
						</c:forEach>
						<!-- 行程结束  -->
						<div class="c_text_list c_text_view clear">
							<a href="${ctx}/line/traffic?id=${line.id}"><i
								class="icont01"></i><span>交通信息</span></a> <a
								href="${ctx}/line/notice?id=${line.id}"><i class="icont02"></i><span>提示信息</span></a>
							<a href="${ctx}/line/fee?id=${line.id}"><i class="icont03"></i><span>费用说明</span></a>
							<a href="${ctx}/line/book?id=${line.id}" class="last"><i
								class="icont04"></i><span>预定须知</span></a>
						</div>
					</div>
				</div>
			</section>
			<section id="f_tel" style="display:none">
				<div class="f_tel_title">
					<div class="f_tel_hd">
						<a href="tel:057123288372">0571-28280815</a> <a id="J_hide">取消</a>
					</div>
				</div>
			</section>
			<section class="pb60">
				<a class="tel_yd" id="J_phone" href="#">电话预订</a>
			</section>
		</section>
	</div>
	<script TYPE="text/javascript">
		$(window).load(function() {
			$("#J_hide").on("click", function() {
				$("#f_tel").hide();
				$("body").removeClass("f_tel");
				$("#f_tel").removecss();

			});

			$("#J_phone").click(function() {
				var dh = $("#bf").scrollTop();
				$("#f_tel").show();
				$("body").addClass("f_tel");
				$("#f_tel").css({
					"top" : dh,
					"position" : "absolute"
				});
			});
		});
	</script>
	<script type="text/javascript">
		var count = document.getElementById("thelist").getElementsByTagName(
				"img").length;
		var count2 = document.getElementsByClassName("menuimg").length;
		for (i = 0; i < count; i++) {
			document.getElementById("thelist").getElementsByTagName("img")
					.item(i).style.cssText = " width:"
					+ document.body.clientWidth + "px";
		}
		document.getElementById("scroller").style.cssText = " width:"
				+ document.body.clientWidth * count + "px";
		setInterval(function() {
			myScroll.scrollToPage('next', 0, 400, count);
		}, 3500);
		window.onresize = function() {
			for (i = 0; i < count; i++) {
				document.getElementById("thelist").getElementsByTagName("img")
						.item(i).style.cssText = " width:"
						+ document.body.clientWidth + "px";
			}
			document.getElementById("scroller").style.cssText = " width:"
					+ document.body.clientWidth * count + "px";
		}
		$("#J_navfooter li").on("touchend", function() {
			$(this).addClass("hover").siblings().removeClass("hover")
		});
	</script>
	<!-- 日历 -->
	<div id="calContainer" class="slider" style="display:none;">
		<header class="header">
			<h1>选择游玩时间</h1>
			<div class="left-head">
				<a id="gohide" href="javascript:;" class="tc_back head-btn"> <span
					class="inset_shadow"> <span class="head-return"></span>
				</span>
				</a>
			</div>
		</header>
	</div>
	<!-- end 日历 -->
	<!--end 页面内容 -->
</body>
</html>