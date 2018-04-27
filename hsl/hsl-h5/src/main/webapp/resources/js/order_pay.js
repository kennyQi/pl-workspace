$(function(){
	
	var message = $("#message").val();
	if(message != "1"){
		showMsg(message);
	}
	
	//添加游玩人提交表单
	$("#addPersonForm .iconfont").click(function(){
		var userId = $("#createOrderForm input[name='userId']").val();
		var lineId = $("#createOrderForm input[name='lineId']").val();
		var travelDate = $("#createOrderForm input[name='travelDate']").val();
		var adultNo = $("#createOrderForm input[name='adultNo']").val();
		var childNo = $("#createOrderForm input[name='childNo']").val();
		
		var linkName = $("#createOrderForm input[name='linkName']").val();
		var linkMobile = $("#createOrderForm input[name='linkMobile']").val();
		var linkEmail = $("#createOrderForm input[name='linkEmail']").val();
		
		var htm ="";
		$("#createOrderForm input[name='travelerIds']").each(function(i,j){
			htm +="<input type='text' value='"+$(j).val()+"' name='travelerIds'>" ;
		});
		htm += "<input type='text' value='"+userId+"' name='userId'>" +
		       "<input type='text' value='"+lineId+"' name='lineId'>" +
		       "<input type='text' value='"+travelDate+"' name='travelDate'>" +
		       "<input type='text' value='"+adultNo+"' name='adultNo'>" +
		       "<input type='text' value='"+linkName+"' name='linkName'>" +
		       "<input type='text' value='"+linkMobile+"' name='linkMobile'>" +
		       "<input type='text' value='"+linkEmail+"' name='linkEmail'>" +
		       "<input type='text' value='"+childNo+"' name='childNo'>";
		$("#addPersonForm #addpersonHiddenValue").html(htm);
		$("#addPersonForm").submit();
	});
	//提交表单
	$(".next-btn").click(function(){
		
		
		var tokenOld = $("#createOrderForm input[name='token']").val();
		var tokenSuccess = sessionStorage.getItem('token');
		if(tokenOld == tokenSuccess){
			showMsg("请勿重复提交表单");
			return false;
		}
		
		var linkName = $("#createOrderForm").find("input[name='linkName']").val();
		var linkMobile = $("#createOrderForm").find("input[name='linkMobile']").val();
		var linkEmail = $("#createOrderForm").find("input[name='linkEmail']").val();
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
		
		if(linkEmail == ""){
			showMsg("联系人邮箱不能为空！");
			return false;
		}
		
		if(!pattern.test(linkEmail)){
			showMsg("联系人邮箱有误，请重新填写！");
			return false;
		}
		
		//判断游客数量
		var count = 0;
		var audltNum = $("#createOrderForm input[name='adultNo']").val();
		var childNum = $("#createOrderForm input[name='childNo']").val();
		$("#createOrderForm").find("input[name='travelerIds']").each(function(){
			count++;
		});
		if(count == 0){
			showMsg("请添加游玩人！");
			return false;
		}
		if(count != parseInt(audltNum)+parseInt(childNum)){
			showMsg("游玩人数量不正确！");
			return false;
		}
		createOrderForm.submit();
	});
   //滑动
    /*$(".pList li").each(function(i,obj){
        var startX= 0,left= 0,endX= 0,nowX=0;
        //if(obj.target=="")
        obj.addEventListener('touchstart', function(event) {
            event.preventDefault();
            if (event.targetTouches.length == 1&&!(event.target.className.indexOf("delete")!=-1)) {
                var touch = event.targetTouches[0];
                startX=touch.pageX;
                left=parseInt($(obj).attr("left"));
            }
        }, false);
        obj.addEventListener('touchmove', function(event) {
            event.preventDefault();
            if (event.targetTouches.length == 1&&!(event.target.className.indexOf("delete")!=-1)) {
                var touch = event.targetTouches[0];
                endX=touch.pageX;
                if(left==0){
                    if(touch.pageX-startX<-75){
                        $(obj).css({
                            "transform":"translateX(-75px)",
                            "-webkit-transform":"translateX(-75px)"
                        });
                    }else if(touch.pageX-startX<0){
                        $(obj).css({
                            "transform":"translateX("+(touch.pageX-startX)+"px)",
                            "-webkit-transform":"translateX("+(touch.pageX-startX)+"px)"
                        });
                    }
                }
            }
        }, false);
        obj.addEventListener('touchend', function(event) {
            event.preventDefault();
            if(!(event.target.className.indexOf("delete")!=-1)){
                if(endX-startX<0){
                    $(obj).animate({"transform":"translateX(-75px)","-webkit-transform":"translateX(-75px)"},50);
                    $(obj).attr("left",1);
                }else{
                    $(obj).animate({"transform":"translateX(0px)","-webkit-transform":"translateX(0px)"},50);
                    $(obj).attr("left",0);
                }
            }
        }, false);
    });*/

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

