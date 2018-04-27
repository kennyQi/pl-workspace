$(function(){
        $(".check").click(function(){
            $(".check").removeClass("check_on");
           $(this).addClass("check_on");
        });
    //发送验证码
    $("#sendYzm").click(function(){
        if(true){
            var html="重新发送（60）",i=60;
            (function(i,c){
               c(i,c);
            }(i,function(i,c){
                setTimeout(function(){
                    i--;
                    html="重新发送（"+i+"）";
                    $("#sendYzm").html(html);
                    if(i>=0) {
                        c(i, c);
                    }else{
                        $("#sendYzm").html("点击发送验证码");
                    }
                },1000);
            }));

        }
    });

    $("#submit").click(function(){
        var phone=$("[name='phone']"),
            yzm=$("[name='yzm']"),
            pwd1=$("[name='pwd']"),
            pwd2=$("[name='pwd1']");
        if(phone.val()==""){
            alertMsg("请填写手机号",phone.offset().top-10,phone.offset().left+phone.closest(".inp_li").width()-140);
            return;
        }
        if(yzm.val()==""){

            alertMsg("请填写验证码",yzm.offset().top-10,yzm.offset().left+yzm.closest(".inp_li").width()-140);
            return;
        }
        if(pwd1.val()==""){

            alertMsg("请填写密码",pwd1.offset().top-10,pwd1.offset().left+pwd1.closest(".inp_li").width()-140);
            return;
        }
        if(pwd2.val()==""){

            alertMsg("请确认密码",pwd2.offset().top-10,pwd2.offset().left+pwd2.closest(".inp_li").width()-140);
            return;
        }

    });


});

function alertMsg(msg,ofTop,ofLeft){
    if(!($(".m_errTips").length>0)){
        var html='<div class="m_errTips" style="display:none">'+
            '<span class="r fl"></span>'+
            '<div class="l fl">'+
            ' <span class="fl">'+msg+'</span>'+
            '</div>'+
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