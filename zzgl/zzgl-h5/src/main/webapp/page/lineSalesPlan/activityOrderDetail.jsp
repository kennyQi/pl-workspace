<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="nowDate" value="<%=new Date()%>"></c:set>

<!-- 清除缓存 -->
<% 
// 将过期日期设置为一个过去时间 
response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT"); 
// 设置 HTTP/1.1 no-cache 头 
response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate"); 
// 设置 IE 扩展 HTTP/1.1 no-cache headers， 用户自己添加 
response.addHeader("Cache-Control", "post-check=0, pre-check=0"); 
// 设置标准 HTTP/1.0 no-cache header. 
response.setHeader("Pragma", "no-cache"); 
%> 

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>订单详情</title>
	    <script src="${ctx}/resources/js/zepto.min.js" language="javascript"></script>
	    <script src="${ctx}/resources/js/frozen.js" language="javascript"></script>
	    <script src="${ctx}/resources/js/line_oder_detail.js" language="javascript"></script>
		<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/global.css" rel="stylesheet" />
		<link href="${ctx}/resources/css/order_pay.css" rel="stylesheet" />
	</head>
	<body>
		 <c:if test="${not empty orderDto}">
			<div class="content">
				<input type="hidden" name="serviceTime" id="serviceTime" value="${serviceTime}"/>
			    <h1 class="trip_title">${orderDto.lineSalesPlan.baseInfo.planName}</h1>
			    <div class="trip_date">
			    	<span>
			    		
			    		<c:if test="${orderDto.lineSalesPlan.baseInfo.planType==2}">
			           		人气秒杀
			           </c:if> 
			           <c:if test="${orderDto.lineSalesPlan.baseInfo.planType==1}">
			          		拼团
			           </c:if>
			           
			           &nbsp; &nbsp; &nbsp; &nbsp;${orderDto.lineSalesPlan.lineSalesPlanSalesInfo.soldNum}/${orderDto.lineSalesPlan.lineSalesPlanSalesInfo.provideNum}
			           &nbsp; &nbsp; &nbsp; &nbsp; ${activityStatusMap[orderDto.lineSalesPlan.lineSalesPlanStatus.status]}
			    	</span>
			    </div>
			    
			    <div class="trip_date">
			        <span class="chooseDate"><i class="iconfont">&#xe676;</i>
			            出游日期：<fmt:formatDate value="${orderDto.orderBaseInfo.travelDate}" pattern="yyyy-MM-dd"/></span>
			        <span>
			            <i class="iconfont">&#xe614;</i>
			            <label>游客人数：${orderDto.orderBaseInfo.adultNo}人</label>
			        </span>
			        <span>
			            <i class="iconfont">&#xe634;</i>
			            <label>活动价格：￥${orderDto.orderBaseInfo.adultUnitPrice}</label>
			        </span>
			    </div>
			    <div class="trip_date">
			        <span class="chooseDate">
			        	<i class="iconfont">&#xe724;</i>
			           	 订单编号：${orderDto.orderBaseInfo.dealerOrderNo}</span>
			           	 
			         <span>
			         <i class="iconfont">&#xe634;</i>
			         	支付方式：支付宝
			         </span>  	 
			        <span>
			            <i class="iconfont">&#xe6fb;</i>
		            	下单时间：<fmt:formatDate value="${orderDto.orderBaseInfo.createDate}" type="both" />
		            	
			        </span>
			        
			    </div>
			    <div class="trip_member">
			        <div class="box title">
			            <h2>游玩人信息</h2>
			        </div>
			        <ul class="pList pl">
			        	<c:set var="index1" value="0" /> 
			        	<c:set var="index2" value="0" /> 
			            <c:forEach items="${orderDto.travelerList}" var="traveler">
				            <c:if test="${traveler.type == 1}">
				              <c:set var="index1" value="${index1+1}" /> 
				              <li class="box" left="0" id="box">
					                <div class="left">
					                    <input type="hidden" value="${traveler.lspOrderStatus.payStatus}" name="payStatus">
					                    <input type="hidden" value="${traveler.lspOrderStatus.orderStatus}" name="orderStatus">
					                    <span class="t">成人${index1}</span>
					                    <span>姓   名： ${traveler.name}</span>
					                    <span>手机号：${traveler.mobile}</span>
					                    <span>身份证：${traveler.idNo}</span>
					                    <span>订单状态：<label style="color:red"> ${orderStatusMap[traveler.lspOrderStatus.orderStatus]}</label></span>
					                    <span>支付状态： <label style="color:red">${orderPayStatusMap[traveler.lspOrderStatus.payStatus]}</label></span>
					                </div>
					            </li>
				            </c:if>
			            </c:forEach>
			            
			            
			        </ul>
			    </div>
			    
			    <div class="order">
			        <form id="order">
				        <div class="box title">
				            <h2>联系人信息：</h2>
				        </div>
				        <div class="input"><i class="iconfont">&#xe613;</i><span>姓&nbsp;&nbsp;&nbsp;&nbsp;名:</span><label>${orderDto.orderLinkInfo.linkName}</label></div>
				        <div class="input"><i class="iconfont">&#xe60b;</i><span>手机号:</span><label>${orderDto.orderLinkInfo.linkMobile}</label></div>
				        <div class="input"><i class="iconfont">&#xe6c6;</i><span>邮&nbsp;&nbsp;&nbsp;&nbsp;箱:</span><label>${orderDto.orderLinkInfo.email}</label></div>
		            </form>
			    </div>
			</div>
			
			<div class="order_modal" style="display: none;">
			    <div class="modal_cont">
				    <h2>订单明细<label id="modal_close"></label></h2>
				    <div class="de">
				        <span class="l">成人价：</span><label class="r">￥${orderDto.orderBaseInfo.adultUnitPrice} x ${orderDto.orderBaseInfo.adultNo}</label>
				    </div>
			    </div>
			</div>
			<div class="fixedBtn box">
			    <div class="leftBtn" id="order_detail">订单金额：${orderDto.orderBaseInfo.salePrice}元&nbsp;&nbsp;详情</div>
			    <c:if test="${(orderDto.orderStatus.orderStatus==2001||orderDto.orderStatus.orderStatus==2005)&&(orderDto.orderStatus.payStatus ==2101)}">
			    	<div class="next-btn" time="${orderDto.orderBaseInfo.createDate}" 
			    	activityEndTime="${orderDto.lineSalesPlan.lineSalesPlanSalesInfo.endDate}">去支付</div> 
			    </c:if>
			</div>
			
			<form action="${ctx}/lineSalesPlan/detailToPayPage" id="orderDetailForm"><input name="orderId" type="hidden" value="${orderDto.id}"></form>
			
		</c:if>
		<!-- 消息提示 -->
		 <c:if test="${not empty message}">
				<input id="message" value="${message}" type="hidden">
		</c:if>
		<c:if test="${empty message}">
			<input id="message" value="1" type="hidden">
		</c:if>
		
		
	</body>
</html>
<script type="text/javascript">
<!--
$(function() {
	var message = $("#message").val();
	if(message != "1"){
		showMsg(message);
	}
	var time=$(".next-btn").attr("time");//下单时间
	
	var crossTime=60*20*1000;
	var nowTime=parseInt($("#serviceTime").val());//服务器时间
	var localTime=new Date().getTime();
	var localCross=nowTime-localTime;
	if(time){
		time=time.split(".")[0];
		time=time.replace(/-/g,"/");
		time=new Date(time).getTime();
		
		var nowTime=0,cross=0;
		setInterval(function(){
			nowTime=new Date().getTime()+localCross;;
			cross=(crossTime+time)-nowTime;
			if(cross>0){
				$(".next-btn").html("去支付<i class='orderValid'>订单失效:"+Math.floor(cross/1000/60)+"分钟"+ten(Math.floor(cross/1000%60))+"秒</i>");
			}else{
				$(".next-btn").remove();
			}
		},500);
	}
});

function showMsg(msg){
    var h=$(window).height()
        ,w=$(window).width()
        ;
    var html='<div class="g_msg"><span>'+msg+'</span></div>';
    $("body").append(html);
    var msg_w=parseInt($(".g_msg").css("width")),msg_h=parseInt($(".g_msg").css("height"));
    $(".g_msg").css({"top":(h-msg_h)/2,"left":(w-msg_w)/2})
    $(".g_msg").show(150);
    setTimeout(function(){
            $(".g_msg").remove();
    },2000);
}
function ten(num){
	num=num<10?("0"+num):num;
	return num;
}
//-->
</script>

