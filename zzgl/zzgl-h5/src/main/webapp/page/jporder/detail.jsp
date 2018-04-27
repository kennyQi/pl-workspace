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
<link href="${ctx}/resources/css/common.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="Stylesheet" href="${ctx}/css/fbase.css">
<link rel="stylesheet" href="${ctx}/css/orderView.1.0.css" />
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
	$(function(){
		var message = $("#message").val();
		if(message != "1"){
			showMsg(message);
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
	function alipay() {
		var subject = encodeURI("${subject}").replace(/%/gi, "$");
		location.href = "${ctx}/alipay/pay?out_trade_no=${order.dealerOrderCode}&total_fee=${order.payAmount}&subject=" + subject;
	}
</script>
</head>
<body style="position:relative;width:100%;overflow-y:hidden;">
<div id="contentWrap" style="position:absolute;width:100%;">
    <!-- 公共页头  -->
    <header class="header">
        <h1>订单详情</h1>
        <div class="left-head">
            <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
                <span class="inset_shadow">
                    <span class="head-return"></span>
                </span>
            </a>
        </div>
    </header>

    <!-- 页面内容块 -->
    <div id="order-box">
        <h5 class="f-tip" style="line-height: 17px; font-size: 12px;">订单状态：${status }</h5>
        <div class="o-item o-detail" style="padding: 0;">
            <div style="padding: 10px; border-bottom: 1px solid #ccc;">
                 <dl class="pr-t fn-clear mlr10">
                    <span style="font-size:20px; line-height: 30px;color:#F60">${order.flightDTO.startCity }—${order.flightDTO.endCity }</span><span style="font-size: 13px;">（单程）</span>
                </dl>
                 <dl class="pr-t fn-clear mlr10">
                    <dt>航班信息：</dt>
                    <dd style="color:#333;">${order.flightDTO.airCompName }${order.flightDTO.flightNo }</dd>
                </dl>
                    <dl class="fn-clear">
                      <dt>起飞时间：</dt>
                        <dd>${order.flightDTO.startDate } ${order.flightDTO.startTime }</dd>
                    </dl>
                    <dl class="fn-clear">
                        <dt>起降机场：</dt>
                        <dd>${order.flightDTO.startPortName } - ${order.flightDTO.endPortName }</dd>
                    </dl>
                    <dl class="fn-clear">
                      <dt>降落时间：</dt>
                        <dd>${order.flightDTO.endDate } ${order.flightDTO.endTime }</dd>
                    </dl>
                    <dl class="fn-clear">
                      <dt>飞行时间：</dt>
                        <dd><script type="text/javascript">
							var min = parseInt('${order.flightDTO.flyTime}');
							var hour = parseInt(min / 60);
							min = min % 60;
							if (hour == 0) {
								document.write(min + '分钟');
							} else {
								document.write(hour + '小时' + min + '分钟');
							}
						</script></dd>
                    </dl>
                      <dl class="fn-clear">
                      <dt>舱位信息：</dt>
                        <dd><script>document.write(JP.getClassType('${order.classCode }'));</script></dd>
                    </dl>   
                      <dl class="fn-clear">
                      <dt>里程信息：</dt>
                        <dd>${order.flightDTO.mileage }公里</dd>
                    </dl>          
            </div>
            <div class="o-info fn-hide fn_orderview" style="display: block;">
                <div class="i-item" style="padding-bottom:10px;">
                <dl class="pr-t fn-clear mlr10">
                  <dt>订单总价：</dt>
                        <dd>
                            <span class="f60">¥<fmt:formatNumber value="${order.payAmount }" pattern="0"/></span> 
                        </dd>
                </dl>
                <c:if test="${not empty backprice}">
	                <dl class="pr-t fn-clear mlr10">
	                  <dt>退费金额：</dt>
	                        <dd>
	                            <span class="f60">¥<fmt:formatNumber value="${backprice }" pattern="0"/></span> 
	                        </dd>
	                </dl>
                </c:if>
                  <dl class="fn-clear">
                      <dt>订单编号：</dt>
                        <dd>${order.orderNO }</dd>
                    </dl> 
                    <dl class="fn-clear">
                      <dt>代理商：</dt>
                        <dd>${order.agencyName }</dd>
                    </dl>          
            </div> 
            <div class="i-item per-list" style="margin-top:10px;padding:0">
            	<c:forEach var="passanger" items="${order.passangers }">
            		<dl class="fn-clear">
                        <dt>登机人：</dt>
                        <dd>${passanger.name }(<c:if test="${passanger.passangerType == 'ADT' }">成人</c:if><c:if test="${passanger.passangerType == 'CHD' }">儿童</c:if><c:if test="${passanger.passangerType == 'INF'}">婴儿</c:if><c:if test="${passanger.passangerType == 'UM' }">无陪伴儿童</c:if>)</dd>
                        <dt>身份证：</dt>
                        <dd>${passanger.cardNo }</dd>	<!-- 返回数据缺少 -->
                        <dt>联系方式：</dt>
                        <dd>${passanger.mobile }</dd>	<!-- 返回数据缺少 -->
                        <c:if test="${(order.status >= 7 && order.status <= 16) || order.status == 22 }">
                       		<dt>机票号：</dt><dd>${passanger.ticket.ticketNo }</dd>
                       	</c:if>
                    </dl>
            	</c:forEach>
            </div>
            </div> 
                      
        </div> 
        <div class="bankbox">    
            <div class="banklist">
               <p>联系客服</p>
            </div>
        </div>
        <c:if test="${order.status == 3 }">
        	<a class="config" href="javascript:alipay();">确认支付</a>
        </c:if>
    </div>

</div>

<!-- 弹窗 -->
<div id="windows" class="click">
    <div class="fix click">
        <div class="top">
            <ul>
                <li>是否拨打客服电话</li>
                <li><a href="tel:010—65912283">010—65912283</a></li>
            </ul>
        </div>
        <div class="cancel">取 消</div>
    </div>
</div>

<!-- 消息提示 -->
 <c:if test="${not empty message}">
		<input id="message" value="${message}" type="hidden">
</c:if>
<c:if test="${empty message}">
	<input id="message" value="1" type="hidden">
</c:if>
</body>
</html>
