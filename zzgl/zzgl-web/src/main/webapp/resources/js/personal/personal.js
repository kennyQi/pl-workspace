//=============================================================================
// 文件名:		common.js
// 版权:		Copyright (C) 2015 ply
// 创建人:		liwei
// 创建日期:	2015-4-30
// 描述:		此文件修改请通知作者
// *************常用代码示例: ***********

$(document).ready(function() {
    //用户上传头像
    ply.control.uploadImg({
        dir:$("body"),
        imgDir:$("img.user"),
        wid:86,
        hei:86,
        uploadUrl:""
    });
    //显示照相机
    $("#fileName").hover(function(){
        $(".hoverIcon").css({"display":"block"});
    },function(){
        $(".hoverIcon").css({"display":"none"});
    });
    //输入框改变样式
    $(document).on("focus",".input_p",function(){       //获得焦点时
	    var thisWidth = $(this).width();
        $(this).css({"border":"2px solid #80d7d6","height":"26px","width":thisWidth-2,"line-height":"26px"});
    });
     $(document).on("blur",".input_p",function(){    //失去焦点时
		var thisWidth = $(this).width();
         $(this).css({"border":"1px solid #dfe8ef","height":"28px","width":thisWidth+2,"line-height":"26px"});
     });

	//弹出框关闭按钮鼠标划入划出事件
	$(document).on("mouseenter",".floating_p a",function(){
		$(this).css({"background":"url(images/ic_script.png) 0 -91px"});
	});
	$(document).on("mouseleave",".floating_p a",function(){
		$(this).css({"background":"url(images/ic_script.png) 0 -800px"});
	});
	//弹出层点击关闭按钮
	$(document).on("click",".floating_p a",function(){
		$(".checkMobileBox").remove();
		$(".successBox").remove();
	});
    //弹出层
    $(".order_p").mouseenter(function(){
        $(this).addClass("oncs");
    }).mouseleave(function(){
        $(this).removeClass("oncs");
    });

    //下拉框通用
    $(".companySelect").click(function(){
		$('.selectNav').hide();
        $(this).next(".selectNav").show();
    });
    $(".selectNav li").click(function(){//下拉列表选值
        var optionsValue=$(this).html();
        var forDom=$(this).parent().attr("for");
        $("#"+forDom).html(optionsValue);
        $(this).parents(".selectNav").css({"display":"none"});

    });
	$("body").bind("click",function(evt){     //点击空白处隐藏
if($(evt.target).parents("ul > li").length==0) { 
   $('.selectNav').hide(); 
   $(".select").css({"background":"url(images/ic_tr.png) 0 -105px"});
  } 
});
});
	