$(function(){
	
	var message = $("#message").val();
	if(message != "1"){
		showMsg(message);
	}
	
	//添加游玩人提交表单
	$("#addPersonForm .iconfont").click(function(){
		
		var lspId = $("#createOrderForm input[name='lspId']").val();
		var adultNo = $("#createOrderForm input[name='adultNo']").val();
		var linkName = $("#createOrderForm input[name='linkName']").val();
		var linkMobile = $("#createOrderForm input[name='linkMobile']").val();
		var email = $("#createOrderForm input[name='email']").val();
		var lastNum = $("#createOrderForm input[name='lastNum']").val();
		var htm ="";
		$("#createOrderForm input[name='travelerIds']").each(function(i,j){
			htm +="<input type='text' value='"+$(j).val()+"' name='travelerIds'>" ;
		});
		htm += 
		       "<input type='text' value='"+lspId+"' name='lspId'>" +
		       "<input type='text' value='"+lastNum+"' name='lastNum'>" +
		       "<input type='text' value='"+adultNo+"' name='adultNo'>" +
		       "<input type='text' value='"+linkName+"' name='linkName'>" +
		       "<input type='text' value='"+linkMobile+"' name='linkMobile'>" +
		       "<input type='text' value='"+email+"' name='email'>" ;
		$("#addPersonForm #addpersonHiddenValue").html(htm);
		$("#addPersonForm").submit();
	});
	//提交表单
	$(".next-btn").click(function(){
		var linkName = $("#createOrderForm").find("input[name='linkName']").val();
		var linkMobile = $("#createOrderForm").find("input[name='linkMobile']").val();
		var email = $("#createOrderForm").find("input[name='email']").val();
		var pattern = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		
		if(linkName == ""){
			showMsg("联系人不能为空！！");
			return false;
		}
		
		if(linkMobile == ""){
			showMsg("联系人手机不能为空！！");
			return false;
		}
		
		if(linkMobile.length != 11){
			showMsg("联系人手机号有误，请重新填写！！");
			return false;
		}
		
		if(isNaN(linkMobile)){
			showMsg("联系人手机号有误，请重新填写！！");
			return false;
		}
		
		if(email == ""){
			showMsg("联系人邮箱不能为空！");
			return false;
		}
		
		if(!pattern.test(email)){
			showMsg("联系人邮箱有误，请重新填写！");
			return false;
		}
		
		//判断游客数量
		var count = 0;
		var audltNum = $("#createOrderForm input[name='adultNo']").val();
		$("#createOrderForm").find("input[name='travelerIds']").each(function(){
			count++;
		});
		if(count == 0){
			showMsg("请添加游玩人！");
			return false;
		}
		if(count != parseInt(audltNum)){
			showMsg("游玩人数量不正确！");
			return false;
		}
		createOrderForm.submit();
	});
  

    //删除
    $(".delete").tap(function(){
        var that=$(this);
        setTimeout(function(){
            that.closest("li").remove();
        },50);
    });

    $("#reset").tap(function(){
        document.getElementById("createOrderForm").reset();
        $("#createOrderForm input[name='linkName']").val("");
        $("#createOrderForm input[name='linkMobile']").val("");
        $("#createOrderForm input[name='linkEmail']").val("");
    });

    //是否已阅读
    $(".law").tap(function(){
       if($(this).find(".choose").hasClass("on")){
           $(this).find(".choose").removeClass("on");
       }else{
           $(this).find(".choose").addClass("on");
       }
    });
    
    //详情
    $("#order_detail").click(function(){
        $(".order_modal").show();
    });

    $(".order_modal #modal_close").click(function(){
        setTimeout(function(){
            $(".order_modal").hide();
        },368);
    });
});

//消息弹出框
function showMsg(msg){
	console.info(msg);
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

//弹出确认框
function showConfirm(msg,fn,obj){
    var h=$(window).height()
        ,w=$(window).width()
        ;
    var html='<div class="g_confirm">'+
        '<div class="bg"></div>'+
        '<div class="confirm_box">'+
        '<h3 class="msg">'+msg+'</h3>'+
        '<div class="btn">'+
        '<span class="yes">是</span>'+
        '<span class="no">否</span>'+
        '</div>'+
        '</div>'+
        '</div>';
    $("body").append(html);
    $(".g_confirm .bg").height(h);
    var msg_h=parseInt($(".g_confirm").height());
    $(".g_confirm").css({"margin-top":(h-msg_h)/2});
    $(".g_confirm").show(150);
    $(".g_confirm .no").click(function(){$(".g_confirm").remove();});
    $(".g_confirm .yes").click(function(){
        fn(obj);
        $(".g_confirm").remove();
    });
}

