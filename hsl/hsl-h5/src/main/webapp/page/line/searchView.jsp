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
<link rel="stylesheet" href="${ctx}/css/circuit.css" />
<link rel="stylesheet" href="${ctx}/css/hgsl_fly.css" />
<link rel="stylesheet" href="${ctx}/css/datetimepicker.css" />
<%@ include file="/page/common/jscommon.jsp"%>
<script type="text/javascript" src="${ctx}/js/datetimepicker.js"></script>
<script type="text/javascript" src="${ctx}/js/fly.js"></script>
<script type="text/javascript" src="${ctx}/js/rili.js"></script> 
<script type="text/javascript">
function doSearch(){
	var beginDateTime = $("#beginDateTime").val();
	var endDateTime = $("#endDateTime").val();
	if(beginDateTime != "" && endDateTime != ""){
		var arr = beginDateTime.split("-"); 
		var beginDateTimer = new Date(arr[0],arr[1],arr[2]).getTime();
		var arr2 = endDateTime.split("-"); 
		var endDateTimer = new Date(arr2[0],arr2[1],arr2[2]).getTime();
		console.info(beginDateTimer);
		console.info(endDateTimer);
		if(endDateTimer >= beginDateTimer){
			$("#c_search_id").submit();
		}else{
			alert("结束时间不能小于开始时间");
		}
	}else{
		$("#c_search_id").submit();
	}
}
</script>
</head>
<body>
    <section class="condition" id="condition">
<!-- 页头  -->
	<header class="header">
	  <h1>选择条件</h1>
	  <div class="left-head"> <a id="goBack" href="${ctx}/line/list" class="tc_back head-btn"> <span class="inset_shadow"> <span class="head-return"></span> </span> </a> </div>
	<span class="right-head"><a href="javascript:doSearch()">完成</a></span>
	</header>
<!-- 页头 -->
	<form action="${ctx}/line/list" id="c_search_id" method="post">
	<div class="c_search">		 
		<div id="c_search_id">
		<input type="hidden" id="dayCount" name="dayCount" value="${hslLineQO.dayCount}"/>
		<input type="hidden" id="level" name="level" value="${hslLineQO.level}"/>
		<div class="c_search_text">			 
			<input type="text" name="searchName" placeholder="请输入线路关键字查询" value="${hslLineQO.searchName}">
			<i></i>
		</div>	
		</div>	 
	</div>
	<div class="c_info">
		<div class="c_title">价格范围</div>
		<div class="price relative">
			<div class="price_title select-title"><i></i></div>
            <span>请选择价格范围</span>
			<select class="select_op textval" name="havePrice">
				<option value="">不限</option>
				<option value="500-1" <c:if test="${hslLineQO.havePrice == '500-1'}">selected</c:if>>500元以下</option>
				<option value="500-1000" <c:if test="${hslLineQO.havePrice == '500-1000'}">selected</c:if>>500-1000元</option>
				<option value="1000-1500" <c:if test="${hslLineQO.havePrice == '1000-1500'}">selected</c:if>>1000-1500元</option>
				<option value="1500-2000" <c:if test="${hslLineQO.havePrice == '1500-2000'}">selected</c:if>>1500-2000元</option>
				<option value="2000-2500" <c:if test="${hslLineQO.havePrice == '2000-2500'}">selected</c:if>>2000-2500元</option>
				<option value="2500-3000" <c:if test="${hslLineQO.havePrice == '2500-3000'}">selected</c:if>>2500-3000元</option>
				<option value="3000-4000" <c:if test="${hslLineQO.havePrice == '3000-4000'}">selected</c:if>>3000-4000元</option>
				<option value="4000-2" <c:if test="${hslLineQO.havePrice == '4000-2'}">selected</c:if>>4000元以上</option>	
			</select>		
		</div>
	</div>
	<div class="c_info">
		<div class="c_title">出发时间</div>
		<div class="time relative pl15 pr15 pt10 stime">
            <i></i>
            <div class="time_txt"><input type="text" id="beginDateTime" name="beginDateTime" value="${beginDateTime}"  readonly="readonly"></div>	
             <em>至</em>
            <div class="time_txt"><input type="text" id="endDateTime" name="endDateTime" value="${endDateTime}" readonly="readonly" onchange="validDate()"></div>	 	
		</div>
	</div>
	<div class="c_info">
		<div class="c_title">行程天数</div>
		<div class="time relative">
			 <ul class="search_star" id="search_day">
                <li <c:if test="${empty hslLineQO.dayCount}">class="sel"</c:if> dayCount=""><a href="">不限</a></li>
                <li <c:if test="${hslLineQO.dayCount == 1}">class="sel"</c:if> dayCount="1"><a href="">一天</a></li>
                <li <c:if test="${hslLineQO.dayCount == 2}">class="sel"</c:if> dayCount="2"><a href="">二天</a></li>
                <li <c:if test="${hslLineQO.dayCount == 3}">class="sel"</c:if> dayCount="3"><a href="">三天</a></li>
                <li <c:if test="${hslLineQO.dayCount == 4}">class="sel"</c:if> dayCount="4"><a href="">四天</a></li>
                <li <c:if test="${hslLineQO.dayCount == 5}">class="sel"</c:if> dayCount="5"><a href="">五天</a></li>
                <li <c:if test="${hslLineQO.dayCount == 6}">class="sel"</c:if> dayCount="6"><a href="">六天</a></li>
                <li <c:if test="${hslLineQO.dayCount == 7}">class="sel"</c:if> dayCount="7"><a href="">七天</a></li>
                <li <c:if test="${hslLineQO.dayCount == 8}">class="sel"</c:if> dayCount="8"><a href="">八天</a></li>
                <li <c:if test="${hslLineQO.dayCount == 9}">class="sel"</c:if> dayCount="9"><a href="">九天</a></li>
                <li <c:if test="${hslLineQO.dayCount == 10}">class="sel"</c:if> dayCount="10"><a href="">十天以上</a></li>
            </ul>   		
		</div>
	</div>
	<div class="c_info">
		<div class="c_title">酒店等级</div>
		<div class="time">
 			<ul class="search_star" id="search_hotel">
				<li <c:if test="${empty hslLineQO.level}">class="sel"</c:if> level=""><a href="">不限</a></li>
				<li <c:if test="${hslLineQO.level == 3}">class="sel"</c:if> level="3"><a href="">五星</a></li>
				<li <c:if test="${hslLineQO.level == 2}">class="sel"</c:if> level="2"><a href="">四星</a></li>
				<li <c:if test="${hslLineQO.level == 1}">class="sel"</c:if> level="1"><a href="">三星</a></li>				
			</ul> 			
		</div>
	</div>
	<div class="c_info">
		<div class="c_title">出发城市</div>
		<div class="city_info">
			<div class="price_title select-title"><i></i></div>
			<input type="hidden" id="startCity" name="startCity" value="${hslLineQO.startCity}">
			<input type="hidden" id="startCityName" name="startCityName" value="${hslLineQO.startCityName}">
			<c:if test="${!empty hslLineQO.startCityName}">
				<span>${hslLineQO.startCityName}</span>
			</c:if>
			<c:if test="${empty hslLineQO.startCityName}">
            	<span>请选择出发城市</span>
            </c:if>
		</div>
	</div>
	</form>
 </section>

<section id="city"> 
<!-- 城市列表 -->
    <div class="qn_index_list" id="citiesPage">
        <div class="search" id="search">
          <div class="search_icon fl"></div>
          <div class="delete_icon fr" style="display: none;"></div>
          <div class="input" >
          	<div style="position:relative;width:100%;height:80%;background:#fff;margin-top:5px;">
          		<span style="position:absolute;top:7px;padding:10px;background:url(${ctx}/img/public/MagnifyingGlass.png) left center no-repeat;background-size:20px;margin-left:10px;display:inline-block;"></span>
            	<input style="position:absolute;left:30px;top:2px;" type="text" placeholder="请输入拼音/中文/简拼/三字码">
          	</div>
           </div>
        </div>
        <div class="content">
          <div class="search_result"></div>
          <dl class="tab_content" id="city1_tab">
          	  <c:forEach var="str" items="${groups}">
          	  	<dt>${str}</dt>
          	  	<c:forEach var="city" items="${cities[str] }">
              		<dd cac="${city.cityAirCode }" cjp="${city.cityJianPin }"
              			cqp="${city.cityQuanPin }" ccn="${city.name }" title="${city.code}">${city.name}</dd>
              	</c:forEach>
              </c:forEach>
          		
             
          </dl>
          <div id="search_tip" style="text-align: center; font-size: 15px; color: #666;
				padding-top: 50px; font-weight: bold; display: none;">未搜索到相关城市信息...</div>
        </div>
      </div>
</section>

<script type="text/javascript">
 $(function(){  
       $('.textval').change(function(){
           $(this).siblings("span").html($(this).find('option:selected').text());
       });   
       
       $('.textval').change();
     });  

    $('#beginDateTime,#beginDateTime1').datetimepicker({
        /* onGenerate:function( ct ){
            $(this).find('.xdsoft_date.xdsoft_weekend')
                .addClass('xdsoft_disabled');
        }, */
        //weekends:['01.01.2014','02.01.2014','03.01.2014','04.01.2014','05.01.2014','06.01.2014'],
        timepicker:false,
        format:'Y-m-d',
		formatDate:'Y-m-d',
    });
     $('#endDateTime,#endDateTime1').datetimepicker({
      /*   onGenerate:function( ct ){
            $(this).find('.xdsoft_date.xdsoft_weekend')
                .addClass('xdsoft_disabled');
        }, */
        //weekends:['01.01.2014','02.01.2014','03.01.2014','04.01.2014','05.01.2014','06.01.2014'],
        timepicker:false,
        format:'Y-m-d',
		formatDate:'Y-m-d',
    });

   $(".city_info").click(function(){    
		$("#condition").hide();
	    $("#citiesPage").show();
   })
    /**
    *重写fly.js赋值方法
    */
	$("#citiesPage dd").click(function(){
		var txt = $(this).text();
		var code = $(this).attr("title");
		var ccn = $(this).attr("ccn");
		console.info("code:"+code);
    	$(".city_info span").html(txt);
		$("#startCity").val(code);
		$("#startCityName").val(ccn);
		$("#citiesPage").hide();	 
        $("#condition").show();		
	})
	
	$(".search_result").delegate("dd", "click", function() {
		var city = $(this).html();
		var code = $(this).attr("title");
		$(".city_info span").html(city);		
		$("#citiesPage").hide();	
		$("#startCity").val(code);
        $("#condition").show();	
		$(".search_result").html("");
		$("#search input").val("");
		$("#city1_tab").show();
	});
   
    $("#search_day li").click(function(){
        $(this).addClass("sel").siblings().removeClass("sel");
        console.info($(this).attr("dayCount"));
        $("#dayCount").val($(this).attr("dayCount"));
        return false;
    })
    
    $("#search_hotel li").click(function(){
        $(this).addClass("sel").siblings().removeClass("sel");
        console.info($(this).attr("level"));
        $("#level").val($(this).attr("level"));
        return false;
    })
</script>
</body>
</html>