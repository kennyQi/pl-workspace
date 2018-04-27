$(function(){
	
	$("#payLineOrderForm").find("#sels").click(function(){//选择优惠方式
		
		var orderId = $("#payLineOrderForm input[name='lineOrderId']").val();
		if(orderId == "" || orderId == null){
			showMsg("该订单已支付");
			$("#successForm").submit();
			return false;
		}
		$("#selectCouponForm #hiddenValue").html("<input name='orderId' value='"+orderId+"'>");
		
		$("#selectCouponForm").submit();
	});
	
	var message = $("#message").val();
	
	if(message != "1"){
		if(message != "无需支付"){
			showMsg(message);
		}
	}
	if(message == "无需支付"){
		showMsg(message);
		$("#successForm").submit();
		return false;
	}
	
	var token = $("#payLineOrderForm input[name='token']").val();
	sessionStorage.setItem('token', token);
});

function submitOrdersForm(){
	var orderId = $("#payLineOrderForm input[name='lineOrderId']").val();
	console.info(orderId);
	if(orderId == "" || orderId == null){
		showMsg("该订单已支付");
		$("#successForm").submit();
		return false;
	}
	

	$("#selectCouponForm #hiddenValue").html("<input name='orderId' value='"+orderId+"'>");
	
	$("#payLineOrderForm").submit();
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
