 //详情
$(function(){
	$("#order_detail").click(function(){
	    $(".order_modal").show();
	});

	$(".order_modal #modal_close").click(function(){
	    setTimeout(function(){
	        $(".order_modal").hide();
	    },368);
	});

	//点击提交订单
	$(".next-btn").click(function(){
         $("#orderDetailForm").submit();		
	});
})