$(function(){
    $(".m_tab li").each(function(i){
        $(this).click(function(){
            $(".m_tab li").removeClass("on").eq(i).addClass("on");
            $(".m_log").find(".ct").hide().eq(i).show();
        });
    });

    $("[name='login']").click(function(){
        var parent=$(this).closest(".ct");
        if(parent.find("[name='user']").val()==""){
            alertMsg("请填写用户名",parent.find("[name='user']").offset().top-10,parent.find("[name='user']").offset().left-40);
            return;
        }

        if(parent.find("[name='pwd']").val()==""){
            alertMsg("请填写密码",parent.find("[name='pwd']").offset().top-10,parent.find("[name='pwd']").offset().left-40);
            return;
        }

        if(parent.find("[name='yzm']").val()==""){
            alertMsg("请填写验证码",parent.find("[name='yzm']").offset().top-10,parent.find("[name='yzm']").offset().left-10);
            return;
        }
    });
});

function alertMsg(msg,ofTop,ofLeft){
    if(!($(".m_errTips").length>0)){
        var html='<div class="m_errTips" style="display:none">'+
            '<div class="l fl">'+
            ' <span class="fl">'+msg+'</span>'+
            '</div>'+
            '<span class="r fl"></span>'+
            '</div>';
        $("body").append(html);

        ofLeft=ofLeft-$(".m_errTips").width();
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
        setTimeout(function(){
            $(".m_errTips").remove();
        },2500);
    }
}