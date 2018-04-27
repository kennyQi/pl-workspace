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
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 公共样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/circuit.css" />
<script TYPE="text/javascript" src="${ctx}/js/jquery.js"></script>
</head>
<body>
	<!-- 页头  -->
	<header class="header">
		<h1>热门线路</h1>
		<div class="left-head">
			<a id="goBack" href="${ctx}/init" class="tc_back head-btn"> <span
				class="inset_shadow"> <span class="head-return"></span>
			</span>
			</a>
		</div>
		<span class="right-head"><a href="javascript:SearchView()">筛选</a></span>
	</header>
	<!-- 页头 -->
	<section id="wrapper">
		<div class="circuit" id="scroll">
			<form action="${ctx}/line/searchView" method="post" id="searchForm">
				<input type="hidden" id="searchName" name="searchName"
					value="${hslLineQO.searchName}" /> <input type="hidden"
					id="havePrice" name="havePrice" value="${hslLineQO.havePrice}" /> <input
					type="hidden" id="beginDateTime" name="beginDateTime"
					value="${beginDateTime}" /> <input type="hidden" id="endDateTime"
					name="endDateTime" value="${endDateTime}" /> <input type="hidden"
					id="dayCount" name="dayCount" value="${hslLineQO.dayCount}" /> <input
					type="hidden" id="startCity" name="startCity"
					value="${hslLineQO.startCity}" /> <input type="hidden"
					id="startCityName" name="startCityName"
					value="${hslLineQO.startCityName}" /> <input type="hidden"
					id="level" name="level" value="${hslLineQO.level}" />
			</form>
			<div id="lists">
				<c:forEach items="${pagination.list}" var="line">
					<div class="circuit_list mb10">
						<a href="${ctx}/hslH5/line/lineDetail?id=${line.id}">
							<div class="c_img">
								<c:if test="${!empty line.lineImageList}">
									<c:forEach items="${line.lineImageList}" var="lineImage" varStatus="status">
										<c:if test="${status.index == 0 && !empty lineImage.urlMaps}">
											<img src="${image_host}${imageBaseUrl}${lineImage.urlMaps['H5']}">
										</c:if>
									</c:forEach>
								</c:if>
								<span class="nowrap">${line.baseInfo.name}</span>
							</div>
							<div class="c-route nowrap">
								<strong>行程概览:</strong> <span> <c:if
										test="${!empty line.baseInfo.destinationCity}">
					出发地<i>></i>
										<c:forEach items="${fn:split(line.baseInfo.destinationCity,',')}"
											var="destination" varStatus="i"> 
						${cityMap[destination]}<i>></i>
										</c:forEach>
					出发地<i></i>
									</c:if>
								</span>
							</div>
							<div class="c-sp">
								<c:if test="${line.baseInfo.type == 1}">
									<div class="star fn-left">
										<span class="radius">跟团游</span><i
											class="s s${line.baseInfo.recommendationLevel}"></i>
									</div>
								</c:if>
								<c:if test="${line.baseInfo.type == 2}">
									<div class="star fn-left">
										<span class="radius">自助游</span><i
											class="s s${line.baseInfo.recommendationLevel}"></i>
									</div>
								</c:if>
								<div class="c_price fn-right">
									<i>￥</i><span> ${line.minPrice} </span><i>/人起</i>
								</div>
							</div>
						</a>
					</div>
				</c:forEach>
			</div>
			<div id="pullUp" onclick="pullUpList()">
				<span class="pullUpIcon"></span><span class="pullUpLabel">点击加载更多...</span>
			</div>
		</div>
		</div>
	</section>
	<script type="text/javascript">
		var pageNo=${pagination.pageNo},pageSize=${pagination.pageSize};
		var pullUpEl = document.getElementById('pullUp');	
		var pullUpOffset = pullUpEl.offsetHeight;
		$(function(){
			if(!${haveMore}){
		 		pullUpEl.querySelector('.pullUpLabel').innerHTML = '已无更多线路';
		 		$("#pullUp").attr("onclick","");
		 	}
		});
		
		function pullUpList(){
			pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
			pageNo++;
			var searchName = $("searchName").val();
			var havePrice = $("#havePrice").val();
			var beginDateTime = $("#beginDateTime").val();
			var endDateTime = $("#endDateTime").val();
			var dayCount = $("#dayCount").val();
			var startCity = $("#startCity").val();
			var startCityName = $("#startCityName").val();
			var level = $("#level").val();
			$.ajax({
			   type: "POST",
			   url: "${ctx}/line/pullUpList",
			   cache: false,
			   data: {
				   "pageNo" : pageNo, "pageSize" : pageSize, "searchName" : searchName, "havePrice" : havePrice,
				   "beginDateTime" : beginDateTime, "endDateTime" : endDateTime, "dayCount" : dayCount, "startCity" : startCity,
				   "startCityName" : startCityName, "level" : level
			   },
			   success: function(msg){
				    var dats = eval('('+msg+')');
				    var lists = dats.pagination.list;
				    pageNo = dats.pagination.pageNo;
				    var cityMap_js = dats.cityMap;
				    var listCount = dats.pagination.pageNo * dats.pagination.pageSize;
				    if(!msg.haveMore){
				    	pullUpEl.querySelector('.pullUpLabel').innerHTML = '已无更多线路';
				    	$("#pullUp").attr("onclick","");
				    }else{
				    	pullUpEl.querySelector('.pullUpLabel').innerHTML = '点击加载更多...';
				    }
					for(var i = 0; i < lists.length; i++){
						var htmlStr = "";
						htmlStr = htmlStr + "<div class='circuit_list mb10'>";
						htmlStr = htmlStr + "<a href='${ctx}/hslH5/line/lineDetail?id="+lists[i].id+"'>";
						htmlStr = htmlStr + "<div class='c_img'>";
						var lineObj = lists[i];
						if (lineObj.lineImageList != undefined && lineObj.lineImageList.length > 0
								&& lineObj.lineImageList[0]['urlMaps'] != undefined
								&& lineObj.lineImageList[0]['urlMaps']['H5'] != undefined) {
							htmlStr = htmlStr + "<img src='${image_host}${imageBaseUrl}" + lineObj.lineImageList[0]['urlMaps']['H5'] + "'>";
						} else {
							htmlStr = htmlStr + "<img src=''>";
						}
						htmlStr = htmlStr + "<span class='nowrap'>"+lists[i].baseInfo.name+"</span></div>";
						htmlStr = htmlStr + "<div class='c-route nowrap'><strong>行程概览:</strong><span>";
						var destination = lists[i].baseInfo.destinationCity;
						var destinations = [];
						if(destination){
							destinations = destination.split(",");
						}
//						console.info(destinations);
						if(destinations.length > 0){
							var destinationsStr = "出发地<i>&gt;</i>";
							for(var j = 0; j < destinations.length; j++){
								destinationsStr = destinationsStr + ""+cityMap_js[destinations[j]]+"<i>&gt;</i>";
							}
							destinationsStr = destinationsStr + "出发地";
							htmlStr = htmlStr + destinationsStr;
						}
						htmlStr = htmlStr + "</span></div>";
						htmlStr = htmlStr + "<div class='c-sp'>";
						if(lists[i].baseInfo.type == 1){
							htmlStr = htmlStr + "<div class='star fn-left'><span class='radius'>跟团游</span><i class='s s"+lists[i].baseInfo.recommendationLevel+"'></i></div> ";
						}
						if(lists[i].baseInfo.type == 2){
							htmlStr = htmlStr + "<div class='star fn-left'><span class='radius'>自助游</span><i class='s s"+lists[i].baseInfo.recommendationLevel+"'></i></div> ";
						}
						<%--
						if(null !=lists[i].dateSalePriceList && lists[i].dateSalePriceList.length > 0){
							htmlStr = htmlStr + "<div class='c_price fn-right'><i>￥</i><span id='minPrice'>"+lists[i].dateSalePriceList[0].adultPrice+"</span><i>/人起</i></div>";
						}else{
							htmlStr = htmlStr + "<div class='c_price fn-right'><i>￥</i><span id='minPrice'></span><i>/人起</i></div>";
						}
						--%>
						htmlStr = htmlStr + "<div class='c_price fn-right'><i>￥</i><span id='minPrice'>"+(lineObj.minPrice).toFixed(1)+"</span><i>/人起</i></div>";
						htmlStr = htmlStr + "</div></a></div>";
						//console.info(htmlStr);
						var line=$(htmlStr);
						$("#lists").append(line);
					}
				}
			   ,error: function(){
				   pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载失败，点击重新加载';
				   console.info("error");
				   pageNo--;			   
			   }
			});
		}
		
		function SearchView(){
			$("#searchForm").submit();
		}
	</script>
</body>
</html>