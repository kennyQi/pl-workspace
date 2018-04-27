$(function(){
	var message = $("#message").val();
	if(message != "1"){
		showMsg(message);
	}
	var contextPath=$("#contextPath").val();
	//查询帐号余额
	$.ajax({
		type : "post",
		async : false,
		url : contextPath+"/accountController/queryAccount",
		success : function(data) {
			$("#balance").html(parseFloat(data).toFixed(2));
		}
	});

	//选择优惠方式
	$("#payLineOrderForm").find("#sels").click(function(){

		var orderId = $("#payLineOrderForm input[name='lineOrderId']").val();
		if(orderId == "" || orderId == null){
			showMsg("该订单已支付");
			$("#successForm").submit();
			return false;
		}
		$("#selectCouponForm #hiddenValue").html("<input name='orderId' value='"+orderId+"'>");

		$("#selectCouponForm").submit();
	});

	
});

var submitOrdersFormType=true;//页面只允许点击一次
function submitOrdersForm(){
	if(submitOrdersFormType) {
		submitOrdersFormType = false;
		var orderId = $("#payLineOrderForm input[name='lineOrderId']").val();
		console.info(orderId);
		if (orderId == "" || orderId == null) {
			showMsg("该订单已支付");
			$("#successForm").submit();
			return false;
		}
		$("#selectCouponForm #hiddenValue").html("<input name='orderId' value='" + orderId + "'>");
		$("#payLineOrderForm").submit();
	}else{
		console.info("第二次点击！");
	}
}

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

$(function(){
	var total=parseFloat($(".total li:last").find("label:last").html().slice(1)).toFixed(2);
	$(".balance").find("[type='checkbox']").change(function(){

		payMoneycount();
	});
	function payMoneycount(){

//		var youhui=parseFloat($("#youhui").html()).toFixed(2);
		var youhui=0;
		var balance=parseFloat($("#balance").html()).toFixed(2);
		if(!$(".balance [type='checkbox']").prop("checked")){
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
		$("#balanceMoney").val($(".h-user-moeny").html());
		/* $("#qianbao").html(parseFloat($(".h-user-moeny").html()).toFixed(2));
		 $("#accountBalance").val(parseFloat($("..h-user-moeny").html()).toFixed(2)); */
		//计算总支付金额
		var tickettotal=total;
		if((tickettotal-youhui-parseFloat($(".h-user-moeny").html()).toFixed(2))>0){
			$(".total li:last").find("label:last").html("￥"+parseFloat(tickettotal-youhui-parseFloat($(".h-user-moeny").html()).toFixed(2)).toFixed(2)).attr("use","true");
		}else{
			$(".total li:last").find("label:last").html("￥"+"0.00").attr("use","false");

		}

		if($(".total li:last").find("label:last").html().slice(1)=="0.00"){
			$("#alipay").removeClass("alipay").addClass("submit").html("确认支付");
			$("[name='payType']").val(99)
		}else{
			$("#alipay").addClass("alipay").removeClass("submit").html("");
			$("[name='payType']").val(2)
		}

	}
});
