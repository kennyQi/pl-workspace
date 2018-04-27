/* liwei */
/* 2015-05-26 */
$(document).ready(function() {
    $(".reg_top a").click(function(){
		 name = $(this).attr("name");
		 if (name == 'per'){
			 $(".reg_personal").show();
			 $(".reg_company").hide();
			 $(this).removeClass().addClass('transparent_white');
			 $(this).siblings('a').removeClass().addClass('transparent_black');
			 }else if(name == 'com'){
				 $(".reg_personal").hide();
			     $(".reg_company").show();
				 $(this).removeClass().addClass('transparent_white');
			     $(this).siblings('a').removeClass().addClass('transparent_black');
				 }
		});
	$(".agr i").click(function(){
		$(this).toggleClass('click');
		});

/**********************************************表单验证***************************************************/
    $('input').val(null);   //清空所有表单内容
    var pattern = null;
    $(".userName").blur(function() {    //验证用户名称
      if(this.value){
        /*pattern = /^[a-zA-Z0-9_]{4,16}$/;
        if (!pattern.test(this.value)) {
            alertMsg("请填写正确的用户名",$(this).offset().top-10,$(this).offset().left+$(this).closest("li").width()+170);
        }*/
      }else{
          alertMsg("用户名不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
      }
    });
    $(".PWD1").blur(function() {    //验证密码
      if(this.value){
        /*pattern = /^{6,12}$/;        //6到12位
        if (!pattern.test(this.value)) {
            alertMsg("密码不能少于6为",$(this).offset().top-10,$(this).offset().left+$(this).closest("li").width()+170);
        }*/
      }else{
          alertMsg("密码不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
      }
    });
    $(".PWD2").blur(function() {    //验证再次输入的密码
      if(this.value != $(".PWD1").val()){
        /*  alert ('密码2验证失败');
        }else{
          alert ('密码2验证成功');*/
          alertMsg("密码不一致",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
      }
    });
    $(".mobile").blur(function() {    //验证手机号码
      if(this.value){
        pattern = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
        if (!pattern.test(this.value)) {
            alertMsg("请填写正确的手机号",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
        }
      }else{
          alertMsg("手机号码不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
      }

    });
    $(".email").blur(function() {    //验证邮箱
      if(this.value){
        pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if (!pattern.test(this.value)) {

            alertMsg("请填写正确的邮箱",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
        }
      }else{

          alertMsg("邮箱不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
      }
    });



    /**********************************************表单验证***************************************************/
    $(document).on("click",".mobileyzms",function(){
        var wait = 60;
        function time(){
            if (wait == 0) {
                $('.mobileyzm').html("<a href='javascript:;' class='mobileyzms yzm fr yahei color9 h2'>免费获取验证码</a>");
                wait = 60;
            } else {
                $('.mobileyzm').html("<i class=' yzm fr yahei color9 h2' style='color:#a3a3a3;'>重新发送("+wait+")</i>");
                wait--;
                setTimeout(function() {
                    time();
                },
                1000)
            }
            }
            time();
    });
    $(".submit").click(function(){
        var parent=$(this).closest(".per_box");
        parent.find("input").each(function(){
            if($(this).attr("warm")!=undefined){
                if($(this).val()==""){
                    alertMsg($(this).attr("warm"),$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
                    return;
                }
            }
        });
        //空判断提交

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