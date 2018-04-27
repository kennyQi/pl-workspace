<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>票量旅游</title>
  <meta charset="UTF-8" />
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta name="apple-mobile-web-app-status-bar-style" content="black" />
  <meta content="telephone=no" name="format-detection" />
  <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
  <link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
  <link rel="stylesheet" href="${ctx}/css/flightsearch.css" />
  <link rel="stylesheet" href="${ctx}/css/mCal.css" />
  <link rel="stylesheet" href="${ctx}/css/hgsl_fly.css">
  <%@ include file="/page/common/jscommon.jsp"%>
  <script type="text/javascript" src="${ctx}/js/fly.js"></script>
  <script type="text/javascript" src="${ctx}/js/rili.js"></script>
  <script type="text/javascript">
  	$(function(){
  		gohide();
  	});
	function back() {
		$("#citiesPage").hide();
	    $("#indexContent").show();
	}
  	function search() {
  		var from = $("#goCity_hid").val();
  		if (from == null || from == '') {
  			$.pop.tips("请选择出发城市");
  			return;
  		}
  		var arrive = $("#backCity_hid").val();
  		if (arrive == null || arrive == '') {
  			$.pop.tips("请选择到达城市");
  			return;
  		}
  		if (from == arrive) {
  			$.pop.tips("出发城市和到达城市不能相同");
  			return;
  		}
  		var startCityName = encodeURIComponent($("#goCity").val());
  		startCityName = startCityName.replace(/%/gi, "-");
  		var endCityName = encodeURIComponent($("#backCity").val());
  		endCityName = endCityName.replace(/%/gi, "-");
  		location.href = "${ctx}/jp/list?openid=${openid}&from=" + from + 
  			"&arrive=" + arrive + "&date=" + $("#traveldate").val() + 
  			"&startCityName=" + startCityName + "&endCityName=" + endCityName;
  	}
  	function gohide(){
  		$("#gohide").click(function(){
  			$("#calContainer").hide();
  			$("#indexContent").show();
  		});
  	}
  </script>
  <style>
	body {
	  padding-bottom:50px;
	}
  </style>
</head>
<body style="position:relative;width:100%;">
  
  <!-- 主页面 -->
  <div class="content"  style="overflow-x:hidden;">
    <div id="indexContent">
    <!-- 公共页头  -->
    <header class="header">
      <h1>机票查询</h1>
      <div class="left-head">
        <a id="goBack" href="${ctx}/index?openid=${openid}" class="tc_back head-btn fn-hide">
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
      <div id="main" class="b_padd">
        <table class="city gray_b"   cellpadding="0" cellspacing="0">
          <tr>
            <td class="from" style="padding-top:10px;">
              <p class="city">
                <span class="width_1">出发城市</span>
                <span class="g_Color in">
                  <input type="text" id="goCity" class="c_name" placeholder="请选择出发城市" />
                  <input type="hidden" name="from" id="goCity_hid" />
                  <input type="hidden" id="goCity_hid_search" />
                  <span id="fromCity" class="tra_left02">
                    <span class="tra_left" style="top:8px;"></span>
                  </span>
                </span>
              </p>
            </td>
            <td rowspan="2" class="pic_change">
              <span class="img_change"></span>
            </td>
          </tr>
          <tr>
            <td class="arr">
              <p class="city">
                <span class="width_1">到达城市</span>
                <span class="g_Color in">
                  <input type="text" id="backCity" class="c_name" placeholder="请选择到达城市" />
                  <input type="hidden" name="arrive" id="backCity_hid" />
                  <input type="hidden" id="backCity_hid_search" />
                  <span id="arrCity" class="tra_left02">
                    <span class="tra_left" style="top:8px;"></span>
                  </span>
                </span>
              </p>
            </td>
          </tr>
        </table>
        <p class="gray_b date" id="date">
          <span class="width_1">出发日期&nbsp;&nbsp;</span>
          <span id="select_Date"></span>
          <span class="tra_left"></span>
          <input type="hidden" name="date" value="${date }" id="traveldate" />
        </p>
        <a href="javascript:search();" style="text-align:center;"  class="search">查询</a>
      </div>
 <!--  <div id="calContainer" class="slider"></div> -->
    <div class="zx">
      <ul class="fn-clear">
        <li> <em class="flight"></em>
          <span>100%</span>
          <br />
          航协认证
        </li>
        <li> <em class="lose"></em>
          <span>100%</span>
          <br />
          出票
        </li>
        <li>
          <em class="time"></em>
          <span>7x24</span>
          <br />
          保障服务
        </li>
      </ul>
      <div class="linebox">
        <span>有保障的低价</span>
        <p></p>
      </div>
    </div>
    </div>

    <!-- 城市列表 -->
    <!-- 城市列表 -->
    <div class="qn_page active" id="citiesPage" style="-webkit-transform: translate3d(0px, 0px, 0px);">
      <div class="qn_header">
        <div class="back">
          <a style="margin:5px 0 0 10px;padding:10px;background:url(${ctx}/img/public/icon_back.png) center center no-repeat;background-size:11px 17px;" href="javascript:back();"></a>
        </div>
        <div class="title" style="margin-left:-5px;font-family: microsoft yahei;">选择城市</div>
      </div>
      <div class="qn_index_list">
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
              			cqp="${city.cityQuanPin }" ccn="${city.cityName }">${city.cityName }</dd>
              	</c:forEach>
              </c:forEach>
          		
             
          </dl>
          <div id="search_tip" style="text-align: center; font-size: 15px; color: #666;
				padding-top: 50px; font-weight: bold; display: none;">未搜索到相关城市信息...</div>
        </div>
      </div>
    </div>
    <!-- 出发日历 -->
    <!-- 日历 -->
    <div id="calContainer" class="slider" style="display:none;padding-bottom:10px;">
    <header class="header">
      <h1>出发日期选择</h1>
      <div class="left-head">
        <a id="gohide" href="javascript:;" class="tc_back head-btn fn-hide">
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
    </div>
  </div>
  <!-- 公共页脚  -->
  <!-- 公共页脚  -->
</body>
</html>