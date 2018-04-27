$(".kajuan_info ul li").on("click",function(){
	 $(this).addClass("hover").siblings().removeClass("hover");
	 $("input[name=ka_radio]").attr("checked",false); 
	 $($(this).find('.kj')[0]).attr("checked",true);
	 $("#submitOrderCouponForm input[name='couponId']").val($(this).attr("rel"));
})

 //选择完卡券之后，点击完成回到订单支付页面
 $("#kajuan_secc").on("click",function(){
	 var couponId = $("#couponId").val();
	 console.info(couponId);
	 if(couponId == ""){
		 showMsg("请选择卡券");
		 return false;
	 }
	 
	 $("#submitOrderCouponForm").submit();
 })
 
 //消息弹出框
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
		$(".g_msg").hide(150,function(){
			$(".g_msg").remove();
		});
	},2000);
}