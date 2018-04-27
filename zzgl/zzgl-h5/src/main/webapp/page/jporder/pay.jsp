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
    <link rel="stylesheet" href="${ctx}/css/airplane_list.css" />
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
			/**********************************************查询帐号余额***************************************************/
			$.ajax({
				type : "post",
				async : false,
				url : '${ctx}/accountController/queryAccount',
				success : function(data) {
					$("#balance").html(parseFloat(data).toFixed(2));
				}
			})
		})
		function alipay() {
			var couponId = $("#couponId").val();//卡券的id
			var dealerOrderId =$("#dealerOrderId").val();//订单号
			var payPrice ='${order.flightPriceInfo.payAmount}';//最低限额
			var balance=$(".h-user-moeny").html();
			
			  if(couponId!=""){
					//提交订单之前验证用户选择的卡券是否可用
				    $.ajax({
				    	type:"post",
				    	async:false,
				    	url:'${ctx}/alipay/getCouponUsed',
				    	dataType:"text",
				    	data:{'useCouponIDs':couponId,'dealerOrderId':dealerOrderId,'payPrice':payPrice},
				    	success:function(data){
				    		if(data!="success"){
				    			$.pop.tips("卡券不可使用");
					    		return false;
				    		}else{
					    		
				    			location.href = "${ctx}/alipay/pay?out_trade_no=${order.orderNO}&payAmount=${order.flightPriceInfo.payAmount}&id="+couponId+"&total_fee=${order.flightPriceInfo.payAmount - youhui}&subject="
				    					+ '${subject}'+"&couponPrice=${youhui}&balance="+balance+"&userId=${userId}&userId=${userId}&SPRING_TOKEN=${SPRING_TOKEN}";
				    		}
				    	},
				    	error:function(msg){
				    			alert(JSON.stringify(msg));
				    	}
				    });
				}else{
					//var subject = encodeURI("${subject}").replace(/%/gi, "$");
	    			location.href = "${ctx}/alipay/pay?out_trade_no=${order.orderNO}&payAmount=${order.flightPriceInfo.payAmount}&id=${couponId}&total_fee=${order.flightPriceInfo.payAmount - youhui}&subject="
	    					+ '${subject}'+"&balance="+balance+"&userId=${userId}&SPRING_TOKEN=${SPRING_TOKEN}";
				}
			  
		}
		$(document).ready(function() {
			$("#selectCoupon").click(function(){//选择优惠方式
				window.location.href = "${ctx}/jpo/coupon?openid=${openid}&orderId=${order.orderNO}&price="+$('#payAmount').html();
			});
			
			if($("#couponId").val()){
				
				$(".yhj_name").html("<i></i>");
				$(".yhj_dic").html($("#couponFaceValue").val()+"元优惠券");
				$("#selectCoupon .yhj_state").html("已选择");
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
</script>
</head>
<body>
  <!-- 公共页头  -->
  <header class="header">
      <h1>选择支付方式</h1>
      <div class="left-head">
          <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
              <span class="inset_shadow">
                  <span class="head-return"></span>
              </span>
          </a>
      </div>
  </header>

 <c:if test="${not empty order}">
    <!-- 页面内容块 -->
    <div id="order-box" class="padding_order_box">
        <h2>订单信息</h2>
        <h5>订单编号：${order.orderNO }</h5>
        <input type="hidden" id="SPRING_TOKEN" name="SPRING_TOKEN" value="${SPRING_TOKEN}"/>
        <div class="o-item o-detail" style="margin:0 auto;padding: 0;width:100%;">
	            <ul class="flight-listsim mt0" data-flg="">
	                 <li class="flight-fltinfo1">
	                    <div class="fr">${order.flightBaseInfo.flightNO }
	                    <br>${order.flightBaseInfo.airCompanyName }</div><i class="icon11"></i> <strong>
	                    <fmt:formatDate value="${order.flightBaseInfo.startTime}" pattern="yyyy-MM-dd"/>
	                   </strong>
	                </li> 
	                <li class="flight-fltinfo2">
	                    <div class="pos">
	                        <p>
	                            <strong style="font-size:14px;">
	                            <fmt:formatDate value="${order.flightBaseInfo.startTime}" pattern="HH:mm"/>
	                            </strong>
	                            ${order.flightBaseInfo.departAirport}
	                           <c:choose>
	                           	<c:when test="${order.flightBaseInfo.departTerm=='--'}">
	                           		T1
	                           	</c:when>
	                           	<c:otherwise>
	                           		${order.flightBaseInfo.departTerm}
	                           	</c:otherwise>
	                           </c:choose>
	                           
	                       		 </p>
	                        <p>
	                            <strong style="font-size:14px;">
	                            <fmt:formatDate value="${order.flightBaseInfo.endTime}" pattern="HH:mm"/>
	                            </strong>
	                          		 ${order.flightBaseInfo.arrivalAirport}
	                          	 <c:choose>
	                           	<c:when test="${order.flightBaseInfo.arrivalTerm=='--'}">
	                           		T1
	                           	</c:when>
	                           	<c:otherwise>
	                           		${order.flightBaseInfo.arrivalTerm}
	                           	</c:otherwise>
	                           </c:choose>	 
	                        </p>
	                    </div>
	                    <div class="canwei" style="margin:14px 0 0 40px;">
	                        <p>经济舱</p>
	                    </div>
	                </li>
	            </ul>
	            <h3>登机者信息</h3>
	            <div id="fPeople">
	                <ul class="i-item per-list">
	                	<c:forEach var="passengers" items="${order.passengers }">
	                		<li class="fn-clear">
		                        <p>登机者:<span style="padding-left:5px;">${passengers.passengerName }</span> </p>
		                        <p>身份证号:<span>${passengers.idNo }</span></p>		<!-- 返回数据缺少 -->
		                    </li>
	                	</c:forEach>
	                </ul>
	            </div>
            
	            <div class="i-item per-list">
	                <p class="fn-clear" style="line-height:30px;text-align:left;">
	                   	共计:
	                    <span class="price"><em>￥</em><label id="payAmount"><fmt:parseNumber value="${order.flightPriceInfo.payAmount }" pattern="0"/></label></span>
	                </p>
	            </div>
            <ul class="flight-listsim mt0" data-flg="">
                 <li class="flight-fltinfo1">
                    <div class="fr">${order.flightBaseInfo.flightNO }
                    <br>${order.flightBaseInfo.airCompanyName }</div><i class="icon11"></i> <strong>
                    <fmt:formatDate value="${order.flightBaseInfo.startTime}" pattern="yyyy-MM-dd"/>
                   </strong>
                </li> 
                <li class="flight-fltinfo2">
                    <div class="pos">
                        <p>
                            <strong style="font-size:14px;">
                            <fmt:formatDate value="${order.flightBaseInfo.startTime}" pattern="HH:mm"/>
                            </strong>
                            ${order.flightBaseInfo.departAirport}
                           <c:choose>
                           	<c:when test="${order.flightBaseInfo.departTerm=='--'}">
                           		T1
                           	</c:when>
                           	<c:otherwise>
                           		${order.flightBaseInfo.departTerm}
                           	</c:otherwise>
                           </c:choose>
                           
                       		 </p>
                        <p>
                            <strong style="font-size:14px;">
                            <fmt:formatDate value="${order.flightBaseInfo.endTime}" pattern="HH:mm"/>
                            </strong>
                          		 ${order.flightBaseInfo.arrivalAirport}
                          	 <c:choose>
                           	<c:when test="${order.flightBaseInfo.arrivalTerm=='--'}">
                           		T1
                           	</c:when>
                           	<c:otherwise>
                           		${order.flightBaseInfo.arrivalTerm}
                           	</c:otherwise>
                           </c:choose>	 
                        </p>
                    </div>
                    <div class="canwei" style="margin:14px 0 0 40px;">
                        <p>${order.flightBaseInfo.cabinName}</p>
                    </div>
                </li>
            </ul>
              
         

        	</div>
        	<div class="bankbox">
	            <p class="paytitle">请选择支付方式</p>
	            <div class="banklist">
	                <ul>
	                    <li class="w-item order-info" id="user_money">
	                        <a class="link" href="javascript:;" >
	                    	<em></em>
	                        		使用余额(账户余额:<b id="balance">0.00</b>元)
	                        <label class="h-user-moeny">0.00</label></a>
	                    </li>
	                    <li class="w-item order-info"> 
	                        <a class="link" href="javascript:;" data-type="0" cardtype="0">支付宝账户支付</a>
	                    </li>
	                </ul>
	            </div>
        	</div>
            <!-- 选择优惠卡券 -->
            <div class="youhuijuan">
	            <input  type="hidden"  id="couponId"  value="${couponId}">
	            <input  type="hidden"  id="dealerOrderId"  value="${order.orderNO}">
	            <input  type="hidden"  id="couponName"  value="${coupon.baseInfo.couponActivity.baseInfo.name}">
	            <input  type="hidden"  id="couponFaceValue"  value="<fmt:parseNumber type="number" value="${youhui}"/>">
	            <p class="youhuijuantitle">选择优惠方式</p>
	            <div class="youhuijuanlist">
	                <ul>
	                   <li id="selectCoupon">
	                       <div class="yhj_name"><i></i>优惠劵</div>
	                       <div class="yhj_dic" >${fn:length(couponList)}张可用</div> 
	                       <div class="yhj_state"><a href="#"  >未选择</a></div>
	                  </li> 
	                </ul>
	            </div>
            </div>
     </div>
     <div id="gopay">
          <c:choose>
           <c:when test="${order.flightPriceInfo.payAmount - youhui < 0}">
     		<div class="order_price">实付总额：<strong id='relpayAmount'>￥<label>0.00</label></strong></div>
     	   </c:when>
     	   <c:otherwise><div class="order_price">实付总额：<strong id='relpayAmount'> ￥<label><fmt:parseNumber type="number" value="${order.flightPriceInfo.payAmount - youhui}"/></label></strong></div></c:otherwise>
   		</c:choose>
    	<a class="config" href="javascript:alipay();">确认支付</a>
    </div>

</c:if> 
  <!-- 消息提示 -->
 <c:if test="${not empty message}">
		<input id="message" value="${message}" type="hidden">
</c:if>
<c:if test="${empty message}">
	<input id="message" value="1" type="hidden">
</c:if>    
      
<script>
	$(function(){
		var total=parseFloat($("#relpayAmount label").html()).toFixed(2);
		$("#user_money").click(function(){
			var that=$(this);
			if(that.data("checkboxClick")){
				that.data("checkboxClick","");
				that.find("em").removeClass("on");
			}else{
				that.data("checkboxClick","on");
				that.find("em").addClass("on");
			}
			payMoneycount();
		});
		function payMoneycount(){
			
//			var youhui=parseFloat($("#youhui").html()).toFixed(2);
		var youhui=0;
				var balance=parseFloat($("#balance").html()).toFixed(2);
				if(!$("#user_money").data("checkboxClick")){
					balance="0.00";
				}
				if(total-youhui>0){
					//可以使用优惠券
					if((total-youhui)>balance){
						$(".h-user-moeny").html(balance);
					}else{
						$(".h-user-moeny").html(parseFloat((total-youhui)).toFixed(2));
					}
				}else{
					//无需使用优惠券
					$(".h-user-moeny").html("0.00");
				}
				/* $("#qianbao").html(parseFloat($(".h-user-moeny").html()).toFixed(2));
				$("#accountBalance").val(parseFloat($("..h-user-moeny").html()).toFixed(2)); */
				//计算总支付金额
				var tickettotal=total;
				if((tickettotal-youhui-parseFloat($(".h-user-moeny").html()).toFixed(2))>0){
					$("#relpayAmount label").html(parseFloat(tickettotal-youhui-parseFloat($(".h-user-moeny").html()).toFixed(2)).toFixed(2)).attr("use","true");
				}else{
					$("#relpayAmount label").html("0.00").attr("use","false");
					
				}
				
			}
	});
	
	
	</script>
	<style>
	#user_money{
	background:#fff;}
	#user_money em{background: url(../img/member/input_r.png) no-repeat 0 -14px;
width: 14px;
height: 14px;
background-size: 14px;
display: inline-block;}
#user_money em.on{background-position:0px 0px;}
	</style>
</body>
    </html>
