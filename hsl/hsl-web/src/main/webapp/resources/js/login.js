$(function(){
    $(".m_tab li").each(function(i){
        $(this).click(function(){
            $(".m_tab li").removeClass("on").eq(i).addClass("on");
            $(".m_log").find(".ct").hide().eq(i).show();
        });
    });

    $("#Q_submit").click(function(){
        var parent=$(this).closest(".ct");
        if(parent.find("[name='loginName']").val()==""){
            alertMsg("请填写用户名",parent.find("[name='loginName']").offset().top-10,parent.find("[name='loginName']").offset().left-40);
            return;
        }

        if(parent.find("[name='password']").val()==""){
            alertMsg("请填写密码",parent.find("[name='password']").offset().top-10,parent.find("[name='password']").offset().left-40);
            return;
        }
        var authcode = parent.find("[name='authcode']").val();
        if( authcode==""){
            alertMsg("请填写验证码",parent.find("[name='authcode']").offset().top-10,parent.find("[name='authcode']").offset().left-10);
            return;
        }else if(authcode.length!=4){
        	 alertMsg("验证码位数不正确",parent.find("[name='authcode']").offset().top-10,parent.find("[name='authcode']").offset().left-10);
             return;
        }
        $("#Q_regForm").submit();
    });
    //个人登录
    $("#G_submit").click(function(){
        var parent=$(this).closest(".ct");
        if(parent.find("[name='loginName']").val()==""){
            alertMsg("请填写用户名",parent.find("[name='loginName']").offset().top-10,parent.find("[name='loginName']").offset().left-40);
            return;
        }

        if(parent.find("[name='password']").val()==""){
            alertMsg("请填写密码",parent.find("[name='password']").offset().top-10,parent.find("[name='password']").offset().left-40);
            return;
        }
        var authcode = parent.find("[name='authcode']").val();
        if(authcode==""){
            alertMsg("请填写验证码",parent.find("[name='authcode']").offset().top-10,parent.find("[name='authcode']").offset().left-10);
            return;
        }else if(authcode.length!=4){
       	 alertMsg("验证码位数不正确！",parent.find("[name='authcode']").offset().top-10,parent.find("[name='authcode']").offset().left-10);
         return;
    }
        $("#G_regForm").submit();
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
        /*setTimeout(function(){
            $(".m_errTips").remove();
        },2500);*/
        $("input").focus(function(){
        	$(".m_errTips").remove();
        });
    }
}