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
<!-- 页面样式表 -->
<link rel="Stylesheet" href="${ctx}/css/fbase.css">
<link rel="stylesheet" href="${ctx}/css/orderView.1.0.css" />
 <script type="text/javascript" src="${ctx}/js/jquery.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script> 	 <script type="text/javascript" src="${ctx}/js/json2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jsbox.js"></script> 	 <script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script><script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>	 <script type="text/javascript" src="${ctx}/js/hsl.js"></script>
<!-- <script type="text/javascript" src="js/jquery-1.10.2.js"></script> -->


</head>
<body style="position:relative;width:100%;" id="bf" >
<div id="contentWrap" style="position:absolute;width:100%; padding-bottom:90px;">
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
         

    <div class="order_title mt20">
        <span class="fn-left border">订单状态</span><span class="fn-right">${status}</span>        
         <input type="hidden" id="SPRING_TOKEN" name="SPRING_TOKEN" value="${SPRING_TOKEN}"/>    
    </div>
        <div class="o-item o-detail" style="padding: 0;">
            <div style="padding: 10px 10px 0 10px;">
                 <dl class="pr-t fn-clear mlr10"  style="text-align: center;">
                    <span style="font-size:20px; line-height: 30px;color:#313131">
                    ${order.flightBaseInfo.orgCity}<i><img src="${ctx}/img/member/jp_jiantou.png" style="margin:0 5px 5px 5px"></i>${order.flightBaseInfo.dstCity }</span><span style="font-size: 13px;color:#999">（单程）</span>
                </dl>
                 <dl class="pr-t fn-clear mb10">
                    <dt>航班信息：</dt>
                    <dd style="color:#999;">${order.flightBaseInfo.airCompanyName}${order.flightBaseInfo.flightNO} ${order.flightBaseInfo.cabinName}</dd>
                </dl>
                    <dl class="fn-clear mb10">
                      <dt>起飞日期：</dt>
                        <dd><fmt:formatDate value="${order.flightBaseInfo.startTime}" pattern="yyyy-MM-dd HH:mm"/></dd>
                    </dl>
                    <dl class="fn-clear mb10">
                        <dt>起降机场：</dt>
                        <dd>${order.flightBaseInfo.departAirport }${order.flightBaseInfo.departTerm}-${order.flightBaseInfo.arrivalAirport}${order.flightBaseInfo.arrivalTerm}</dd>
                    </dl> 
                    <dl class="fn-clear mb10">
                      <dt>降落日期：</dt>
                        <dd><fmt:formatDate value="${order.flightBaseInfo.endTime}" pattern="yyyy-MM-dd HH:mm"/></dd>
                    </dl>
                    <dl class="fn-clear mb10">
                      <dt>飞行时间：</dt>
                      <dd>
                       <%--  <fmt:formatNumber value=" ${(order.flightBaseInfo.endTime.getTime()-order.flightBaseInfo.startTime.getTime())/3600000}" maxFractionDigits="0"/>
                        	   小时
                        	 <c:choose>
                           	<c:when test="${((order.flightBaseInfo.endTime.getTime()-order.flightBaseInfo.startTime.getTime())/60000)%60==0}">
                           		0分
                           	</c:when>
                           	<c:otherwise>
                           		<fmt:formatNumber value="${((order.flightBaseInfo.endTime.getTime()-order.flightBaseInfo.startTime.getTime())/60000)%60}" maxFractionDigits="0"/>
                    			  分
                           	</c:otherwise>
                           </c:choose> --%>
                           ${hour}小时${min}分
                        	
                        </dd>
                    </dl>
                      <dl class="fn-clear mb10">
                      <dt>舱位信息：</dt>
                        <dd>${order.flightBaseInfo.cabinName}</dd>
                    </dl>   
                     <%--  <dl class="fn-clear mb10">
                      <dt>里程信息：</dt>
                        <dd>${order.flightDTO.mileage }公里</dd>
                    </dl> --%>          
            </div>
            <div class="o-info fn-hide" style="display: block;">
                <div class="i-item" style="padding: 8px 10px 0 10px;border-top:#e1e1e1 1px solid">
                <dl class="pr-t fn-clear">
                  <dt>订单总价：</dt>
                        <dd>
                            <span class="f60"><i>¥</i>&nbsp&nbsp<fmt:formatNumber value="${order.flightPriceInfo.payAmount}" pattern="0"/></span> 
                        </dd>                        
                </dl>
                <c:if test="${not empty backprice}">
                <dl class="pr-t fn-clear">
                  <dt>退费金额：</dt>
                        <dd>
                            <span class="f60"><i>¥</i>&nbsp&nbsp<fmt:formatNumber value="${backprice }" pattern="0"/></span> 
                        </dd>                        
                </dl>
                </c:if>
                  <dl class="fn-clear">
                      <dt>订&nbsp&nbsp单&nbsp&nbsp号：</dt>
                        <dd>${order.orderNO }</dd>
                    </dl> 
                    <dl class="fn-clear">
                       <dt>代&nbsp&nbsp理&nbsp&nbsp商：</dt>
                        <dd>票量旅行平台</dd>
                    </dl>          
            </div> 
            <div class="order_title">
              <span class="fn-left border">登机人</span>          
             </div>
             <c:forEach var="passanger" items="${order.passengers }">
            <div class="i-item per-list" style="padding: 0 10px 0 10px;">
                    <dl class="fn-clear">
                        <dt>姓&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp名：</dt>
                        <dd>${passanger.passengerName }(成人)</dd>
                        <dt>身&nbsp&nbsp份&nbsp&nbsp证：</dt>
                        <dd>${passanger.idNo }</dd>
                        <c:if test="${(order.status==17) }">
                       		<dt>机票号：</dt><dd>${passanger.airId }</dd>
                       	</c:if>
                    </dl>
            </div>
            </c:forEach>
            <div class="order_title">
              <span class="fn-left border">联系人</span>          
             </div>
                <div class="i-item per-list" style="padding: 0 10px 0 10px;">
                    <dl class="fn-clear">
                        <dt>联&nbsp&nbsp系&nbsp&nbsp人：</dt>
                        <dd>
                        <dd>${userDTO.baseInfo.nickName}&nbsp;</dd>
                        <dt>手&nbsp&nbsp机&nbsp&nbsp号：</dt><dd>${order.jpLinkInfo.linkTelephone}</dd>
                    </dl>
                </div>  
            </div>                       
        </div> 

    <div id="footnav">
    	<c:if test="${(order.status==PAYWAIT&&order.payStatus==NOPAY) }">
        <div class="b_pay">
            <a onclick="payNow('${order.id}')" class="btn btn-org ">立即支付</a>
           <%--  <a onclick="cancelJ('${order.id}')" class="btn m_green ">取消订单</a> --%>
        </div>
        </c:if>
        <div class="bankbox" id="bankbox">    
            <div class="banklist">
               <p>联系客服</p>
            </div>
        </div>
    </div>    
    </div>
</div>

<!-- 弹窗 -->
<section id="f_tel" style="display:none">
    <div class="f_tel_title">
      <div class="f_tel_hd">
       <a href="tel:010—65912283">010—65912283</a>
       <a id="J_hide">取消</a>
       </div>
     </div>
   </section>

<div id="loading" style="display:none;">
    <div class="ui-loader">
        <div class="load_container">
            <span class="loading"></span><span class="loadFont">正在进入安全支付环境,<br>
                您的支付信息将加密后传输</span>
        </div>
    </div>
    <div class="h-bg">
    </div>
</div>

<script type="text/javascript">
		/**
		*	机票立即支付跳转
		*/
		function payNow(orderId){
			location.href = "${ctx}/jpo/pay?openid=${openid}&orderId=" + orderId;
		}
		
		/**
		*	取消机票
		*/
		function cancelJ(id){
			window.cancel_J_id = id;
			//$.pop.confirm('真的要取消订单吗？',cancelJP,function(){console.info('用户取消')});这个插件的显示位置不对，下拉到下面的时候位置错误
			//location.href = "${ctx}/jpo/cancel?openid=${openid}&id=" + id;//订单数据id而非id
			if(confirm("真的要取消吗")){
				cancelJP();
			}
		}
		
		/**
		* 取消机票的回调函数
		*/
		function cancelJP(){
			$.pop.lock(true);
		    $.pop.load(true, '正在取消');
			$.ajax({
		    	url:"${ctx}/jpo/cancel?openid=${openid}&id=" + window.cancel_J_id,
		    	type:'post',
		    	async:true,
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
		    	success:function(data){
		    		console.info(data);
		    		 $.pop.load(false, function() {
		    			 $.pop.lock(false);
		    			 if(data.result==-1){
		    				 $.pop.tips('取消失败');
		    			 }else if(data.result==1){
			    			$.pop.tips('取消成功');
				 	    	setTimeout(function() {
				 				location.reload();
				 	    	}, 666);	 
		    			 }else{
		    				 $.pop.tips('服务器异常');
		    			 }

		    		 });
		    	}
		    });
		}
		
        $(window).load(function(){     
        $("#J_hide").on("click",function(){
          $("#f_tel").hide();
          $("body").removeClass("f_tel"); 
            $("#f_tel").removecss();     

        });
        $("#bankbox").click(function(){       
        var dh = $("#bf").scrollTop();
        var h =$(window).height();
        if(dh>0){
             $("#f_tel").show();
             $("body").addClass("f_tel");
             $("#f_tel").css({"top":dh-48,"position":"absolute","height":h+48}); 
        }
        else{
            $("#f_tel").show();
             $("body").addClass("f_tel");
             $("#f_tel").css({"top":dh,"position":"absolute"});
        }                 
        }); 
        });        
</script>


</body>
</html>