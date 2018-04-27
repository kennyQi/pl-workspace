<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta name="version" content="票量旅游 v1.0 20140910">
<meta http-equiv="Cache-Control" content="must-revalidate,no-cache">
<meta http-equiv="x-dns-prefetch-control" content="on" />
<title>中智票量</title>
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/ticket.css">
 <script type="text/javascript" src="${ctx}/js/jquery.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script> 	 <script type="text/javascript" src="${ctx}/js/json2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jsbox.js"></script> 	 <script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script><script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>	 <script type="text/javascript" src="${ctx}/js/hsl.js"></script>
<!--  <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 基础页面中有  -->

</head>
<style>
body{ background:#f8f8f8;}

	.pullUp {
        height:40px;
        line-height:40px;
        margin-bottom: 5px;
        font-weight:bold;
        font-size:14px;
        color:#888;
    }
    .pullUp .pullUpIcon  {
        display:block;margin-left:36%;
        width:40px; height:40px;
        background:url(${ctx}/img/pull-icon.png) 0 0 no-repeat;
        -webkit-background-size:40px 80px; background-size:40px 80px;
        -webkit-transition-property:-webkit-transform;
        -webkit-transition-duration:250ms;
    }
    .pullUp span{float:left;}
    
    .pullUp .pullUpIcon  {
        -webkit-transform:rotate(-180deg) translateZ(0);
    }

    
    .pullUp.flip .pullUpIcon {
        -webkit-transform:rotate(0deg) translateZ(0);
    }
    
    .pullUp.loading .pullUpIcon {
        background-position:0 100%;
        -webkit-transform:rotate(0deg) translateZ(0); 
        -webkit-transition-duration:0ms; 

        -webkit-animation-name:loading;
        -webkit-animation-duration:2s;
        -webkit-animation-iteration-count:infinite;
        -webkit-animation-timing-function:linear;
    }

    @-webkit-keyframes loading {
        from { -webkit-transform:rotate(0deg) translateZ(0); }
        to { -webkit-transform:rotate(360deg) translateZ(0); }
    }
</style>
<body>
  <!-- 公共页头  -->
<header class="header">
  <h1>机票订单</h1>
  <div class="left-head"> <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn"><span class="inset_shadow"><span class="head-return"></span></span></a> </div>
</header>
<script type="text/javascript">
	//屏幕滚动回调函数列表
	window.callbacks_J = $.Callbacks();
	window.JFlag = true;
	window.MFlag = true;
	
	/**
	*  滚动事件
	*/
	$(window).scroll(function(){
		callbacks_J.fire();
	});

	
	/**************************** 机票部分开始 *********************************/
	var statusMap = '${statusMap}';
	statusMap = eval("("+statusMap+")");
	
	var payflag = '${PAYWAIT}'+'${NOPAY}';

	var pageNo = 1;
	$(document).ready(function() {
		$.pop.lock(true);
	    $.pop.load(true, '努力加载中...');
	    ajaxLoad(true, true);
	});
	
	function ajaxLoad(isFir, isAsync) {
		scrollBottomOff();
		/* $.ajax({
	    	url:'${ctx}/jpo/ajaxList?pageNo=' + pageNo + '&pageSize=15&openid=${openid}',
	    	type:'post',
	    	async:isAsync,
	    	timeout:60000,
	    	dataType:'json',
	    	
	    	success:function(data) {
	    		alert(data)
			    $.pop.load(false, function() {
			    	$.pop.lock(false);
			    	if (data.result == '1') {
			    		if (isFir) {
							$("#J_pullUp").show();
							$("#J_noDataTip").hide();
						}
			    		load(data.jpOrders);
			    		if (data.jpOrders&&data.jpOrders.length <15) {
							$("#J_pullUp").hide();
							JFlag = false;
						} else {
							scrollBottomOn();
						}
						pageNo++;
				    } else if (data.result == '-1') {
				    	if (isFir) {
							$("#J_pullUp").hide();
							$("#J_noDataTip").show();
						} 
				    } else {
				    	scrollBottomOff();
				    	setTimeout(function() {
				    		$.pop.tips('系统繁忙,请稍候1');
				    	}, 666);
				    }
			    });
	    	},error:function(data) {
	    		console.info(data)
			    $.pop.load(false, function() {
	    			$.pop.lock(false);
	    			if (!isFir) { 
	    			 scrollBottomOn(); 
	    			}
			    	setTimeout(function() {
			    		$.pop.tips('系统繁忙,请稍候');
			    	}, 666);
			    });
	    	}
	    }); */
		$.ajax({
	    	url:'${ctx}/jpo/ajaxList?pageNo=' + pageNo + '&pageSize=15&openid=${openid}',
	    	type:'post',
	    	async:isAsync,
	    	timeout:60000
		}).done(function(data) {
			data=eval("("+data+")");
			    $.pop.load(false, function() {
			    	$.pop.lock(false);
			    	if (data.result == '1') {
			    		if (isFir) {
							$("#J_pullUp").show();
							$("#J_noDataTip").hide();
						}
			    		load(data.flightOrderDTO);
			    		if (data.flightOrderDTO&&data.flightOrderDTO.length <15) {
							$("#J_pullUp").hide();
							JFlag = false;
						} else {
							scrollBottomOn();
						}
						pageNo++;
				    } else if (data.result == '-1') {
				    	if (isFir) {
							$("#J_pullUp").hide();
							$("#J_noDataTip").show();
						} 
				    } else {
				    	scrollBottomOff();
				    	setTimeout(function() {
				    		$.pop.tips('系统繁忙,请稍候1');
				    	}, 666);
				    }
			    });
	    	}).fail(function(data) {
			    $.pop.load(false, function() {
	    			$.pop.lock(false);
	    			if (!isFir) { 
	    			 scrollBottomOn(); 
	    			}
			    	setTimeout(function() {
			    		$.pop.tips('系统繁忙,请稍候');
			    	}, 666);
			    });
	    	
	    }); 
	}
	
	function load(orders) {
		var temp = $("#J_temp").html();
		if(!orders){
			return;
		}
		for (var i = 0; i < orders.length; i++) {
			var order = orders[i];
			//alert(order.status+order.payStatus);
			var status = "";
			if(!order.payStatus||!order.status){
				status = "无结果";
			}else{
				status = ''+order.status+order.payStatus;
				if(statusMap[status]){
					status = statusMap[status];
				}else{
					status = '状态异常';
				}
			}
			var child = temp;
			//判断订单是否已取消
			var isShow='';
			if(status!='待支付'){
				isShow='none';
			}
			child = child.replace(/#orderNo#/gi, order.id)
				.replace(/#orderSt#/gi, status)
				.replace(/#startDate#/gi, getTime(new Date(order.flightBaseInfo.startTime),1))
				.replace(/#airCompName#/gi, order.flightBaseInfo.airCompanyName)
				.replace(/#startTerminal#/gi, order.flightBaseInfo.departAirport+order.flightBaseInfo.departTerm)
				.replace(/#endTerminal#/gi, order.flightBaseInfo.arrivalAirport+order.flightBaseInfo.arrivalTerm)
				.replace(/#flightNo#/gi, order.flightBaseInfo.flightNO)
				.replace(/#cabinName#/gi, order.flightBaseInfo.cabinName)
				.replace(/#startTime#/gi, getTime(new Date(order.flightBaseInfo.startTime),2))
			/* 	.replace(/#startPortName#/gi, order.flightBaseInfo.startPortName) */
				.replace(/#endTime#/gi, getTime(new Date(order.flightBaseInfo.endTime),2))
			/* 	.replace(/#endPortName#/gi, order.flightBaseInfo.endPortName) */
				.replace(/#payAmount#/gi, order.flightPriceInfo.payAmount)
				.replace(/#orderFID#/gi, order.id)
				.replace(/#display#/gi,isShow);
			
				$(".jp_info ul.main").append(child);
			
		}
	}
	/**
	*	查看机票订单详情
	*/
	function detail(orderId) {
		location.href = "${ctx}/jpo/detail?openid=${openid}&orderId=" + orderId;
	}
	
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
	
	/**
	*  机票列表拖动函数
	*/
	function J_wait() {
		var scrollTop = $(window).scrollTop();
		var scrollHeight = $(document).height();
		var windowHeight = window.screen.height;
		if (scrollTop + windowHeight >= scrollHeight-15) {
			$(".J_pullUpLabel").html("Loading...");
			$("#J_pullUp").addClass("loading");
			ajaxLoad(false, false);
		}
	}
	
	/**
	*  添加机票列表拖动函数
	*/
	function scrollBottomOn() {
		callbacks_J.add(J_wait);
	}
	/**
	*  移除机票列表拖动函数
	*/
	function scrollBottomOff() {
		callbacks_J.remove(J_wait);
	}
	
	/**************************** 机票部分结束 *********************************/
	
	
	
	/**************************** 门票部分开始 *********************************/
	
/* 	var M_pageNo = 1;
	$(document).ready(function() {
		$.pop.lock(true);
	    $.pop.load(true, '努力加载中...');
	    ajaxLoad_M(true, true);
	});
	
	function ajaxLoad_M(isFir, isAsync) {
		callbacks_J.remove(M_wait);
		$.ajax({
	    	url:'${ctx}/mpo/ajaxList?pageNo=' + M_pageNo + '&pageSize=15&openid=${openid}',
	    	type:'post',
	    	async:isAsync,
	    	timeout:60000,
	    	dataType:'json',
	    	error:function(e) {
			    $.pop.load(false, function() {
	    			$.pop.lock(false);
	    			if (!isFir) { callbacks_J.add(M_wait); }
			    	setTimeout(function() {
			    		$.pop.tips('门票系统繁忙,请稍候');
			    	}, 666);
			    });
	    	},
	    	success:function(data) {
			    $.pop.load(false, function() {
			    	$.pop.lock(false);
			    	if (data.result == '1') {
			    		if (isFir) {
							$("#M_pullUp").show();
							$("#M_noDataTip").hide();
						}
			    		load_M(data.orders);
			    		if (data.orders&&data.orders.length <15) {
							$("#M_pullUp").hide();
							callbacks_J.remove(M_wait);
							MFlag = false;
						} else {
							callbacks_J.add(M_wait);
						}
			    		M_pageNo++;
				    } else if (data.result == '-1') {
				    	if (isFir) {
							$("#M_pullUp").hide();
							$("#M_noDataTip").show();
						} 
				    } else {
				    	callbacks_J.remove(M_wait);
				    	setTimeout(function() {
				    		$.pop.tips('系统繁忙,请稍候');
				    	}, 666);
				    }
			    });
	    	}
	    });
	}
	
	/**
	* 加载门票
	*/
	/* function load_M(orders) {
		if(!orders){
			return;
		}
		var temp = $("#M_temp").html();
		
		for (var i = 0; i < orders.length; i++) {
			var order = orders[i];
			var child = temp;
			var status = "";
			if (order.status.cancel) { status = "已取消"; }
			if (order.status.outOfDate) { status = "已过期"; }
			if (order.status.prepared) { status = "待游玩"; }
			if (order.status.used) { status = "已游玩"; }
			var orderId = order.dealerOrderCode, orderCode = orderId;
			if (orderCode.length > 18) { orderCode = orderCode.substring(0, 18) + "..."; }
			child = child.replace(/#orderCode#/gi, orderCode).replace(/#orderId#/gi, orderId)
				.replace(/#status#/gi, status).replace(/#number#/gi, order.number)
				.replace(/#image#/gi, order.scenicSpot.scenicSpotBaseInfo.image)
				.replace(/#name#/gi, order.scenicSpot.scenicSpotBaseInfo.name)
				.replace(/#address#/gi, order.scenicSpot.scenicSpotGeographyInfo.address)
				.replace(/#intro#/gi, order.scenicSpot.scenicSpotBaseInfo.intro);
			$(".mp_info ul.main").append($(child));
		}
	} */
	
	/**
	* 门票滚动事件
	*/
/* 	function M_wait() {
		var scrollTop = $(window).scrollTop();
		var scrollHeight = $(document).height();
		var windowHeight = window.screen.height;
		if (scrollTop + windowHeight >= scrollHeight-15) {
		
			$(".M_pullUpLabel").html("Loading...");
			$("#M_pullUp").addClass("loading");
			ajaxLoad_M(false, false);
		}
	} */
	
	/**
	* 查看门票详情
	*/
	/* function mpdetail(orderId){
		location.href = "${ctx}/mpo/detail?openid=${openid}&orderId="+orderId;
	} */
	
	/****************************日期格式化 *********************************/
	function getTime(date,type) {//type==1转换日期  type==2转换时间
	if(type==1){
		var now = "";
		now = date.getFullYear() + "-"; //读英文就行了
		var month = (date.getMonth() + 1).toString();
		if (month.length == 1) {
			month = "0" + month;
		}
		now = now + month + "-";//取月的时候取的是当前月-1如果想取当前月+1就可以了
		var d = date.getDate().toString();
		if (d.length == 1) {
			d = "0" + d;
		}
		now = now + d + " ";
		return now;
	}else if(type==2){
		var now = "";
		
		var hour = date.getHours().toString();
		if (hour.length == 1) {
			hour = "0" + hour;
		}
		now = now + hour + ":";
		var minute = date.getMinutes().toString();
		if (minute.length == 1) {
			minute = "0" + minute;
		}
		now = now + minute;
		return now;
	}
	
	
	
}
</script>
<%--<section class="kajuan_nav tabs">
<a href="#" class="sel k_icon">
  <div><i></i>机票订单</div>
  </a>
  <a href="#" class="k_icon">
  <div><i></i>门票订单</div>
  </a>
</section>--%>
<section class="order_info tabs_info ">
  <div class="jp_info">
  <ul class="main">   
  <!-- 模板开始 -->  
	<div id="J_temp" style="display: none;">
	    <li>
	      <div class="order_title">
	          <div class="order_icon"></div>
	          <span>#orderSt#</span>
	      </div>
	       <div class="order_info1 order_line" onclick="detail('#orderNo#')">             
             <div class="order_info_title"><span>#startDate#</span><h3>#airCompName# #flightNo#</h3><em>#cabinName#</em></div>
             <ul class="order_i_list">
              <li><i>#startTime#</i><!-- #startPortName# -->#startTerminal#</li>
              <li><i>#endTime#</i><!-- #endPortName# -->#endTerminal#</li>                
             </ul>             
           	</div>
           	<div class="order_oper"> 
            	<div class="order_n_r"><h4><i>￥</i>#payAmount#</h4></div>
            	<a  onclick="detail('#orderNo#')" style="display:#display_s#;" class="m_bth m_org">查看详情</a>
           		<a  onclick="payNow('#orderNo#')" style="display:#display#;" class="m_bth m_org" >立即支付</a>
           		<!-- <a  onclick="cancelJ('#orderFID#')" style="display:#display#;" class="m_bth m_blue">取消订单</a> -->
         	</div> 
	    </li> 
	</div>
	<!-- 模板结束 -->  
   </ul>
   <!-- 加载状态组件开始 -->
  	<div id="J_noDataTip" class="noDataTip" style="text-align: center; font-size: 15px; color: #666;
		padding-top: 50px; font-weight: bold; display: none;">未搜索到相关机票订单信息...</div>
    <div id="J_pullUp" class="loading pullUp" style="display:none;">
        <span class="pullUpIcon J_pullUpIcon"></span>
        <span class="pullUpIcon J_pullUpLabel">Loading...</span>
    </div>
  <!-- 加载状态组件结束 -->
  </div>  
  
  <div style="display:none" class="mp_info">
  <ul class="main">
  	<!-- 模板开始 -->  
  	<div id="M_temp" style="display: none;">
    <li>
      <div class="order_title ">
          <div class="order_icon"></div>
          <span>#status#</span>
      </div>
       <div class="order_info1" onclick="mpdetail('#orderId#')">             
            <div class="order_name">
              <div class="order_nc order_line">
                <div class="order_n_l">
                  <h3>#name#<i>#address#</i></h3>                  
                  <p>#intro#</p>
                </div>                
              </div>
            </div>
            <div class="order_img"><img src="#image#"></div>
           </div>
           <div class="order_oper"> 
            <div class="order_n_r"><h4>数量：#number#张</h4></div>
           <a href="${ctx}/mpo/detail?openid=${openid}&orderId=#orderId#" class="m_bth m_green01">查看详情</a>
         </div> 
    </li>
    </div>
    <!-- 模板结束 -->  
  </ul>
  <!-- 加载状态组件开始 -->
  	<div id="M_noDataTip" class="noDataTip" style="text-align: center; font-size: 15px; color: #666;
		padding-top: 50px; font-weight: bold; display: none;">未搜索到相关门票订单信息...</div>
    <div id="M_pullUp" class="loading pullUp" style="display:none;">
        <span class="pullUpIcon M_pullUpIcon"></span>
        <span class="pullUpLabel M_pullUpLabel">Loading...</span>
    </div>
  <!-- 加载状态组件结束 -->
  </div>
</section>
  <script type="text/javascript">
  $(".tabs a").bind("click",function(){
    var index = $(this).index();
    //index = 0是机票，1是门票，准备添加一个切换滚动事件的回调函数
    if(index==0){
    	callbacks_J.remove(M_wait);//移除门票的回调函数
    	if(JFlag){
    		callbacks_J.add(J_wait);
    	}
    }else if(index==1){
    	callbacks_J.remove(J_wait);
    	if(MFlag){
    		callbacks_J.add(M_wait);//添加门票的回调函数
    	}
    }
    var divs = $(".tabs_info > div");
    $(this).parent().children("a").attr("class", "no");//将所有选项置为未选中
            $(this).attr("class", "sel");     //设置当前选中项为选中样式
            divs.hide(); 
            divs.eq(index).show();  
   })  
 </script> 
</body>
</html>


