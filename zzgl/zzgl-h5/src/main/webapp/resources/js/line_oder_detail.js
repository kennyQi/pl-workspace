 //详情
$(function(){
	var message = $("#message").val();
	if(message != "1"){
		showMsg(message);
	}
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