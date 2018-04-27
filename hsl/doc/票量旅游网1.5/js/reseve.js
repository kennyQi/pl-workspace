$(function(){
	
	var subNavTop=$("#float").offset().top; 
	var cssTop=parseInt($("#float").css("top"));
	var g_histLeft=$("#float").offset().left;
	var g_histAb_Right=parseInt($("#float").css("right"));
	var footTop=$(".gd_foot").offset().top;
	$(window).scroll(function(){
		console.log($(window).scrollTop()+","+footTop);
		   //获取float层距离顶端的高度
		if($(window).scrollTop()>(footTop)){
			$("#float").css({"top":cssTop,left:"","right":g_histAb_Right,"position":"absolute"});
		}else if($(window).scrollTop()>subNavTop){
			$("#float").css({"top":0,left:g_histLeft,"right":"","position":"fixed"});
			//$("#float").css({"position":"fixed","top":"0px"});
		}else {
			$("#float").css({"top":cssTop,left:"","right":g_histAb_Right,"position":"absolute"});
		};
	});

$("#addTourist").click(function(){      //添加游客
	var rel=parseInt($(this).attr("rel"));
	var html='<ul class="tourist ov">'+
        '<li class="touristName yahei"><span>游客'+rel+'</span><a href="javascript:;" class="delTourist">删除</a></li>'+
        '<li class="inp touristInp"><span class="travelTitle">姓名：</span><input type="text" name="" id="" style="width:240px;padding:0 8px 0 8px;"><span style="color:red; margin-left:9px;"  class="mo ts">请填写真实姓名</span></li>'+
       ' <li class="inp touristInp"><span class="travelTitle">手机：</span><input type="text" name="" class="mobile" style="width:240px;padding:0 8px 0 8px;"><span style="color:red; margin-left:9px;" class="mo ts">请填写正确的手机号码</span></li>'+
       ' <li class="inp touristInp"><span class="travelTitle">身份证号码：</span><input type="text" name="" class="ID" style="width:240px;padding:0 8px 0 8px;"><span style="color:red; margin-left:9px;" class="mo ts">请填写正确的身份证号码</span></li>'+
        '<li><span class="line line1"></span></li>'+
    		'</ul>';
	$(this).attr("rel",rel+1);
	$("#tourists").append(html);
}); 

$("#addChildren").click(function(){    //添加儿童
	
var rel=parseInt($(this).attr("rel"));
	var html=' <span class="line line1"></span><ul class="children ov">'+
        '<li class="touristName yahei"><span>儿童'+rel+'</span><a href="javascript:;" class="delTourist">删除</a></li>'+
        '<li class="inp touristInp"><span class="travelTitle">姓名：</span><input type="text" name="" id="" style="width:240px;padding:0 8px 0 8px;"><span style="color:red; margin-left:9px;" class="mo ts">请填写真实姓名</span></li>'+
        '<li class="inp touristInp"><span class="travelTitle">手机：</span><input type="text" name="" class="mobile" style="width:240px;padding:0 8px 0 8px;"><span style="color:red; margin-left:9px;" class="mo ts">请填写正确的手机号码</span></li>'+
    '</ul>';
	$(this).attr("rel",rel+1);
	$("#childrens").append(html);
});


$(".radio").click(function(){
	$(this).closest(".discount").find(".radio").removeClass("radio_on");
	
	$(this).closest(".invoice").find(".radio").removeClass("radio_on");
	$(this).addClass("radio_on");

});

	$("#use").click(function(){     //优惠劵选择
		$("#boxDiscount").show();
	});
	$("#notUse").click(function(){     //优惠劵选择
		$("#boxDiscount").hide();
	});
$(".choice").click(function(){
	if($(this).attr("class").indexOf("choice_on")!=-1){
		$(this).removeClass("choice_on");
	}else{
		$(this).addClass("choice_on");
	}
});

$("#select").click(function(){       //下拉列表选择值
	var displayValue=$("#options").css("display");
	if(displayValue == "none"){
		$("#options").css({"display":"block"});
		}else{
			$("#options").css({"display":"none"});
			}
	});

$("#options li").click(function(){     
	var optionsValue=$(this).html();
	$("#select span:eq(0)").html(optionsValue);
	$("#options").css({"display":"none"});
});

for(var i=0,l=parseInt($("#adult").val());i<(l-1);i++){
	$("#addTourist").trigger("click");
}

for(var i=0,l=parseInt($("#children").val());i<(l-1);i++){
	$("#addChildren").trigger("click");
}


//验证邮箱

	var reg = /^([a-zA-Z0-9])+([a-zA-Z0-9\_])+@([a-zA-Z0-9\_-])+((\.[a-zA-Z0-9\_-]{2,3}){1,2})$/;
	$(".email").blur(function(){
		$(this).val($(this).val().replace(/ /g,""));
		if(!(reg.test($(this).val()))){
			$(this).siblings("span.mo").show();
		}
	});
	$(".email").focus(function(){
			$(this).siblings("span.mo").hide();
	});
	
	

});




///删除人数


	
$(document).on("click","#childrens .delTourist",function(){
	if($(this).attr("rel")==undefined){
		$(this).closest(".children").remove();
		$(".line1:last").remove();
	}else{
		if($(".children").length>1){
			$(this).closest("#childrens").find(".children:last").remove();
			$(".line1:last").remove();
		}else{
			return ;
		}
	}
	
	var rel=parseInt($(this).closest("#childrens").find("#addChildren").attr("rel"));
	$(this).closest("#childrens").find("#addChildren").attr("rel",(rel-1))
});


$(document).on("click","#tourists .delTourist",function(){
	if($(this).attr("rel")==undefined){
		
		$(this).closest(".tourist").remove();
	}else{
		if($(".tourist").length>1){
			$(this).closest("#tourists").find(".tourist:last").remove();
		}else{
			return ;
		}
	}
	
	var rel=parseInt($(this).closest("#tourists").find("#addTourist").attr("rel"));
	$(this).closest("#tourists").find("#addTourist").attr("rel",(rel-1))
});

//验证手机

$(document).on("blur",".mobile",function(){
	var patrn=/^1[3|4|5|8][0-9]\d{8}$/; 
	$(this).val($(this).val().replace(/ /g,""));
	if(!(patrn.test($(this).val()))){
		$(this).siblings("span.mo").show();
	}
});

$(document).on("focus",".mobile",function(){
		$(this).siblings("span.mo").hide();
});
	