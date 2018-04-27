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
    <link rel="stylesheet" href="${ctx}/css/orderView.1.0.css" />
    <link rel="stylesheet" href="${ctx}/css/airplane_list.css" />
    <%@ include file="/page/common/jscommon.jsp"%>
	<script type="text/javascript" src="${ctx}/js/rili.js"></script>
	<script type="text/javascript" src="${ctx}/js/fly.js"></script>
	<script type="text/javascript">
		function alipay() {
			var couponId = $("#couponId").val();//卡券的id
			var dealerOrderId =$("#dealerOrderId").val();//订单号
			var payPrice = $("#payAmount").val();//最低限额
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
				    			$.pop.tips("卡券已经使用");
					    		return false;
				    		}else{
					    		//form提交订单
				    			var subject = encodeURI("${subject}").replace(/%/gi, "$");
				    			location.href = "${ctx}/alipay/pay?out_trade_no=${order.dealerOrderCode}&payAmount=${order.payAmount}&id=${couponId}&total_fee=${order.payAmount - youhui}&subject=" + subject;
				    		}
				    	},
				    	error:function(msg){
				    			alert(JSON.stringify(msg));
				    	}
				    });
				}else{
					var subject = encodeURI("${subject}").replace(/%/gi, "$");
	    			location.href = "${ctx}/alipay/pay?out_trade_no=${order.dealerOrderCode}&payAmount=${order.payAmount}&id=${couponId}&total_fee=${order.payAmount - youhui}&subject=" + subject;
				}
		}
		$(document).ready(function() {
			$("#selectCoupon").click(function(){//选择优惠方式
				window.location.href = "${ctx}/jpo/coupon?openid=${openid}&orderId=${order.dealerOrderCode}";
			});
			
			if($("#couponId").val()){
				
				$(".yhj_name").html("<i></i>");
				$(".yhj_dic").html($("#couponFaceValue").val()+"元优惠券");
				$("#selectCoupon").html("已选择");
			}
		});
	</script>
</head>
<body>
    <!-- 公共页头  -->
    <header class="header">
        <h1>选择支付方式</h1>
        <%-- <div class="left-head">
            <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
                <span class="inset_shadow">
                    <span class="head-return"></span>
                </span>
            </a>
        </div> --%>
    </header>

    <!-- 页面内容块 -->
    <div id="order-box" class="padding_order_box">
        <h2>订单信息</h2>
        <h5>订单编号：${order.dealerOrderCode }</h5>
        <div class="o-item o-detail" style="margin:0 auto;padding: 0;width:100%;">
            <ul class="flight-listsim mt0" data-flg="">
                <li class="flight-fltinfo1">
                    <div class="fr">${order.flightDTO.flightNo }
                    <br>${order.flightDTO.aircraftName }</div><i class="icon11"></i> <strong>${order.flightDTO.startDate }</strong>
                    &nbsp;${order.flightDTO.airCompName }
                </li>
                <li class="flight-fltinfo2">
                    <div class="pos">
                        <p>
                            <strong>${order.flightDTO.startTime }</strong>
                            ${order.flightDTO.startPortName }
                        </p>
                        <p>
                            <strong>${order.flightDTO.endTime }</strong>
                            ${order.flightDTO.endPortName }
                        </p>
                    </div>
                    <div class="canwei" style="margin:14px 0 0 40px;">
                        <p><script>document.write(JP.getClassType('${order.classCode }'));</script></p>
                    </div>
                </li>
            </ul>
            <h3>登机者信息</h3>
            <div id="fPeople">
                <ul class="i-item per-list">
                	<c:forEach var="passanger" items="${order.passangers }">
                		<li class="fn-clear">
	                        <p>登机者:<span style="padding-left:5px;">${passanger.name }</span> </p>
	                        <p>身份证号:<span>${passanger.cardNo }</span></p>		<!-- 返回数据缺少 -->
	                    </li>
                	</c:forEach>
                </ul>
            </div>
            <%-- <div class="i-item per-list">
                <dl class="fn-clear">
                    <dt>联系方式:</dt>
                    <dd>${orderInfo.linker.contactInfo.mobile }</dd>
                </dl>
            </div> --%>
            <div class="i-item per-list">
                <p class="fn-clear" style="line-height:30px;text-align:left;">
                   	共计:
                    <span class="price"><em>￥</em><fmt:parseNumber value="${order.payAmount }" pattern="0"/></span>
                </p>
            </div>
         

        </div>
        <div class="bankbox">
            <p class="paytitle">请选择支付方式</p>
            <div class="banklist">
                <ul>
                    <li class="w-item order-info"> 
                        <a class="link" href="javascript:;" data-type="0" cardtype="0">支付宝账户支付</a>
                    </li>
                    </ul>
                </div>
            </div>
            <!-- 选择优惠卡券 -->
            <div class="youhuijuan">
            <input  type="hidden"  id="couponId"  value="${couponId}">
             <input  type="hidden"  id="dealerOrderId"  value="${order.dealerOrderCode}">
              <input  type="hidden"  id="payAmount"  value="${order.payAmount}">
            <input  type="hidden"  id="couponName"  value="${coupon.baseInfo.couponActivity.baseInfo.name}">
            <input  type="hidden"  id="couponFaceValue"  value="<fmt:parseNumber type="number" value="${youhui}"/>">
            <p class="youhuijuantitle">选择优惠方式</p>
            <div class="youhuijuanlist">
                <ul>
                   <li>
                       <div class="yhj_name"><i></i>优惠劵</div>
                       <div class="yhj_dic" >${fn:length(couponList)}张可用</div> 
                       <div class="yhj_state"><a href="#"  id="selectCoupon">未选择</a></div>
                  </li> 
                </ul>
                </div>
            </div>
        </div>
        <div id="gopay">
              <c:choose>
	              <c:when test="${order.payAmount - youhui < 0}">
			      		<div class="order_price">实付总额：<strong> ¥0</strong></div>    
			      </c:when>
			      <c:otherwise><div class="order_price">实付总额：<strong> ¥<fmt:parseNumber type="number" value="${order.payAmount - youhui}"/></strong></div></c:otherwise>  
		      </c:choose>
		      <a class="config" href="javascript:alipay();">确认支付</a>
        </div>

        <!-- <div id="loading" style="display: none;">
            <div class="ui-loader">
                <div class="load_container">
                    <span class="loading"></span>
                    <span class="loadFont">
                        正在进入安全支付环境,
                        <br>您的支付信息将加密后传输</span>
                </div>
            </div>
            <div class="h-bg"></div>
        </div> -->

</body>
    </html>
