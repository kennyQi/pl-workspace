// 使用页面：company_allordertourism.html
// 日期：2015、4/22
// 作者：lw
$(document).ready(function() {
		//查看游客信息
	$(".seeTourist").click(function(){
		/*var list="["+$(this).next("input").val().slice(2);
		eval("list="+list);*/
		/*var index=$(".seeTourist").index($(this));
		$(".inf_float").css({"display":"none"});
		$(".qx_fl").css({"display":"none"});
		var top= 460+index*180+'px';
		var html='<div class="inf_float" style="margin-top:'+top
		+'"><span class="float_title floating_p yahei h3">游客信息'
		+'<a href="javascript:;"></a></span><ul><li class="inf_top">'
		+'<span class="inf_name">姓名</span>'
		+'<span class="inf_ID">身份证号</span><span class="inf_price">单价</span><span class="inf_state">订单状态</span><span class="inf_Pstate">支付状态</span></li>'+
		
		'<li><span class="inf_name">王浩</span><span class="inf_ID">330523198011011831</span><span class="inf_price">200.00</span><span class="inf_state">预订成功待确认</span><span class="inf_Pstate wait">待支付</span></li><li><span class="inf_name">王浩</span><span class="inf_ID">330523198011011831</span><span class="inf_price">200.00</span><span class="inf_state">预订成功待确认</span><span class="inf_Pstate">已支付</span></li><li><span class="inf_name">王浩</span><span class="inf_ID">330523198011011831</span><span class="inf_price">200.00</span><span class="inf_state">预订成功待确认</span><span class="inf_Pstate">已支付</span></li></ul></div>';
		$(".inf_box").append(html);*/

		var top=$(this).offset().top;
		var left=$(this).offset().left;
		if($(this).closest("li").find(".inf_box").css("display")=="none"){
			$(this).closest("li").find(".inf_box").show().find(".inf_float").css({"left":left-100,top:top+30});
		}else{
			$(this).closest("li").find(".inf_box").hide();
		}
		
	});
	$(document).on("click",".float_title a",function(){    //关闭按钮
		 $(this).parents(".inf_box").css({"display":"none"});
	});
	
	$(".qxOrder").click(function(){var left=$(this).offset().left;
		var index=$(".qxOrder").index($(this));
		$(this).parent().find(".qx_fl").remove();
		$(this).parent().find(".inf_box").css({"display":"none"});
		var top= 460+index*180+'px';
		var html='<div class="qx_fl" style="margin-top:'+top+';margin-left:'+left+'"><a href="javascript:;"></a><span>请致电400-660-6660，进行取消订单</span></div>';
		$(".qx_box").append(html);
		});
	$(document).on("click",".qx_fl a",function(){    //关闭按钮
		 $(this).parents(".qx_fl").css({"display":"none"});
	});
	
	//关闭按钮鼠标划入划出事件
/*$(document).on("mouseenter",".qx_fl a",function(){      //取消订单
	$(this).css({"background":"url(../resources/images/ic_script.png) 0 -263px"});
	});
$(document).on("mouseleave",".qx_fl a",function(){
	$(this).css({"background":""});
	});*/

	});