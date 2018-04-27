//=============================================================================
// 文件名:		common.js
// 版权:		Copyright (C) 2015 ply
// 创建人:		liwei
// 创建日期:	2015-4-30
// 描述:		此文件修改请通知作者
// *************常用代码示例: ***********

$(document).ready(function() {

	sessionStorage.setItem("yxJpPayOrderToken","F");
	sessionStorage.setItem("yxOrderToken","F");
    //用户上传头像
    ply.control.uploadImg({
        dir:$("body"),
        imgDir:$("img.user"),
        wid:86,
        hei:86,
        uploadUrl:"../file/imgUpload"
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
        $(this).css({"border":"2px solid #55bfef","height":"26px","width":thisWidth-2,"line-height":"26px"});
    });
     $(document).on("blur",".input_p",function(){    //失去焦点时
		var thisWidth = $(this).width();
         $(this).css({"border":"1px solid #dfe8ef","height":"28px","width":thisWidth+2,"line-height":"26px"});
     });

	//弹出框关闭按钮鼠标划入划出事件
	$(document).on("mouseenter",".floating_p a",function(){
		$(this).css({"background-position":"0 -91px"});
	});
	$(document).on("mouseleave",".floating_p a",function(){
		$(this).css({"background-position":"0 -800px"});
	});
	//弹出层点击关闭按钮
	$(document).on("click",".floating_p a",function(){
		$(".checkMobileBox").hide();
		$(".successBox").hide();
	});
    //弹出层
    $(".order_p").mouseenter(function(){
        $(this).addClass("oncs");
    }).mouseleave(function(){
        $(this).removeClass("oncs");
    });

    //下拉框通用
    $(".companySelect").click(function(){
    	$(".companySelect").next(".selectNav").hide();
        $(this).next(".selectNav").show();
    });
    $(document).on("click",".selectNav li",function(){//下拉列表选值
        var optionsValue=$(this).html();
        var subValue=$(this).attr("value");
        var forDom=$(this).parent().attr("for");
        var forName=$(this).parent().attr("forName");
        $("#"+forDom).html(optionsValue);
        $("[name='"+forName+"']").val(subValue);
        $(this).parents(".selectNav").css({"display":"none"});

    });
    
    $("body").bind("click",function(evt){     //点击空白处隐藏
    	if($(evt.target).parents("ul > li").length==0) { 
    	   $('.selectNav').hide(); 
    	   
    	  } 
    	});
    $(".changeTime").click(function(){
    	$(".selectNav").hide();
    })
    
});


function pageCreate(total,form){
	$("#page").pageBar({
		pageNum : "pageNum",//页码class
		pageCount : total,//总页数
		formClass : form//分页表单class
	});


}

function alertMsg(msg,ofTop,ofLeft){
	if(!($(".m_errTips").length>0)){
		var html='<div class="m_errTips" style="display:none">'+
		'<span class="r fl"></span>'+
		'<div class="l fl">'+
		' <span class="fl">'+msg+'</span>'+
		'</div>'+
		'</div>';
		$("body").append(html);


		$(".m_errTips").css({left:ofLeft,top:ofTop}).show();
		setTimeout(function(){
			$(".m_errTips").animate({left:ofLeft+5},100,function(){
				$(".m_errTips").animate({left:ofLeft-5},100,function(){
					$(".m_errTips").animate({left:ofLeft+5},100,function(){
						$(".m_errTips").animate({left:ofLeft-5},100,function(){
							$(".m_errTips").animate({left:ofLeft},100);
						});
					});
				});
			});
		},500);
		/*setTimeout(function(){
			$(".m_errTips").remove();
		},2500);*/
	}
} 
	