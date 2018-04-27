$(function(){
	
	//验证
	
	var mobileError=$("#mobileError").val();
	var mobileCode=$("#mobileCode").val();
	var phone=$("[mark='phone']"),
		yzm=$("[mark='yzm']"),
		email=$("[mark='email']"),
		yzm1=$("[mark='yzm1']");
	var type=$("#index").val();
	if(type=="2"){
		$("#email").addClass("check_on");
		$("#mobile").removeClass("check_on");
		$("#yx_form").show();
		$("#dp_form").hide();
		if(mobileError!=""){
			alertMsg("用户不存在",email.offset().top-10,email.offset().left+email.closest("span").width()+10);
		}
		if(mobileCode!=""){
			 alertMsg("验证码有误,请重新输入",yzm1.offset().top-10,yzm1.offset().left+yzm1.closest("span").width()+10);
		}	
	}else{
		if(mobileError!=""){
			alertMsg("用户不存在",phone.offset().top-10,phone.offset().left+phone.closest("span").width()+10);
		}
		if(mobileCode!=""){
			 alertMsg("验证码有误,请重新输入",yzm.offset().top-10,yzm.offset().left+yzm.closest("span").width()+10);
		}	
	}

	//单选框
        $(".check").each(function(i){
        	$(this).click(function(){
	           $(".check").removeClass("check_on");
	           $(this).addClass("check_on");
	        	   $("form").hide().eq(i).show();
        	});
        });
    //发送手机验证码
    $("#sendYzm").click(function(){
    	if($(this).attr("can")=="false") return;
    	var reg = /^0?(13|15|18)[0-9]{9}/;
    	var phone=$("[mark='phone']");
    	var contextPath=$("#contextPath").val();
    	 if(phone.val()==""){
             alertMsg("请填写手机号",phone.offset().top-10,phone.offset().left+phone.closest("span").width()+10);
             return;
         }else if(!reg.test(phone.val()) || phone.val().length != 11){
         	alertMsg("手机号有误",phone.offset().top-10,phone.offset().left+phone.closest("span").width()+10);
             return;
         }else{
        	 $(this).attr("can","false");
        	 $.ajax({
     			type : "POST",
     			url : contextPath+"/comRegister/sendCode?mobile="+ phone.val() + "&scene=2",
     			contentType : "application/x-www-form-urlencoded; charset=utf-8",
     			dataType : "json",
     			success : function(result) {
     				if (result.status == "success") {
     					$("#validTokenMo").val(result.validToken);
     					alertMsg("验证码发送到您的手机",phone.offset().top-10,phone.offset().left+phone.closest("span").width()+160);
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
     		                             $(this).attr("can","true");
     		                         }
     		                     },1000);
     		                 }));
     		        	 }
     			        return;
     				} else {
     					alertMsg(result.error,phone.offset().top-10,phone.offset().left+phone.closest("span").width()+160);

     		        	 $(this).attr("can","true");
     			         return;
     				}
     			},
     		    error:function(result) {
     		    	  alertMsg("发送失败",phone.offset().top-10,phone.offset().left+phone.closest("span").width()+160);

     	        	 $(this).attr("can","true");
     		    	  return;
     		    }
        	});
        	
        }
    });
  //发送邮箱验证码
    $("#sendEm").click(function(){
    	if($(this).attr("can")=="false") return;
    	var reg =/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    	var email=$("[mark='email']");
    	var contextPath=$("#contextPath").val();
    	 if(email.val()==""){
             alertMsg("请填写邮箱号码",email.offset().top-10,email.offset().left+email.closest("span").width()+160);
             return;
         }else if(!reg.test(email.val())){
         	alertMsg("邮箱号码有误",email.offset().top-10,email.offset().left+email.closest("span").width()+160);
             return;
         }else{
        	 $(this).attr("can","false");
        	 $.ajax({
     			type : "POST",
     			url : contextPath+"/user/sendMailCode?mail="+ email.val() + "&scene=1",
     			contentType : "application/x-www-form-urlencoded; charset=utf-8",
     			dataType : "json",
     			success : function(result) {
     				if (result.status == "success") {
     					$("#validTokenEm").val(result.validToken);
     					alertMsg("验证码发送到您的邮箱",email.offset().top-10,email.offset().left+email.closest("span").width()+160);
     					 if(true){
     		                 var html="重新发送（60）",i=60;
     		                 (function(i,c){
     		                    c(i,c);
     		                 }(i,function(i,c){
     		                     setTimeout(function(){
     		                         i--;
     		                         html="重新发送（"+i+"）";
     		                         $("#sendEm").html(html);
     		                         if(i>=0) {
     		                             c(i, c);
     		                         }else{
     		                        	 $(this).attr("can","true");
     		                             $("#sendEm").html("点击发送验证码");
     		                         }
     		                     },1000);
     		                 }));
     		        	 }
     			        return;
     				} else {
     					 $(this).attr("can","true");
     					alertMsg(result.error,email.offset().top-10,email.offset().left+email.closest("span").width()+10);
     			         return;
     				}
     			},
     		    error:function(result) {
     		    	 	$(this).attr("can","true");
     		    	  alertMsg("发送失败",email.offset().top-10,email.offset().left+email.closest("span").width()+10);
     		    	  return;
     		    }
        	});
        	
        }
    });


});
function submits(id){
	checking(id);
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

        //ofLeft=ofLeft-$(".m_errTips").width();
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
        $("input").focus(function(){
        	$(".m_errTips").remove();
        });
    }
}
function checking(id){//id==1手机 id==2邮箱
	 var phone=$("[mark='phone']"),
     yzm=$("[mark='yzm']"),
     yzm1=$("[mark='yzm1']"),
     pwd1=$("[mark='pwd']"),
     pwd2=$("[mark='pwd1']")
     pwd3=$("[mark='pwd3']"),
     pwd4=$("[mark='pwd4']");
	 var reg = /^0?(13|15|18)[0-9]{9}/;//手机正则表达式
	 var regmail=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;//邮箱正则
		var curpreg = /^(\w){6,20}$/;
	 var msg=true;
	 if(id=="1"){
		 if(phone.val()==""){
		     alertMsg("请填写手机号",phone.offset().top-10,phone.offset().left+phone.closest("span").width()+160);
		     msg=false;
		     return;
		 }else if(!reg.test(phone.val()) || phone.val().length != 11){
		 	alertMsg("手机号有误",phone.offset().top-10,phone.offset().left+phone.closest("span").width()+160);
		 	msg=false;
		     return;
		 }
		 if(yzm.val()==""){
		     alertMsg("请填写验证码",yzm.offset().top-10,yzm.offset().left+yzm.closest("span").width()+10);
		     msg=false;
		     return;
		 }
		 if(pwd1.val()==""||pwd1.val().length<6||!curpreg.test(pwd1.val())){
		     alertMsg("6-20位的字母、数字或下划线_",pwd1.offset().top-10,pwd1.offset().left+pwd1.closest("span").width()+10);
		     msg=false;
		     return;
		 }
		 if(pwd2.val()==""){
		
		     alertMsg("请确认密码",pwd2.offset().top-10,pwd2.offset().left+pwd2.closest("span").width()+10);
		     msg=false;
		     return;
		 }else if(pwd2.val()!=pwd1.val()){
		 	alertMsg("密码不一致",pwd2.offset().top-10,pwd2.offset().left+pwd2.closest("span").width()+10);
		 	msg=false;
		     return;
		 }
		 if(msg==true){
			 $("#dp_form").submit();
		 }
     }
	 if(id=="2"){
		var reg =/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    	var email=$("[mark='email']");
    	var contextPath=$("#contextPath").val();
    	 if(email.val()==""){
             alertMsg("请填写邮箱号码",email.offset().top-10,email.offset().left+email.closest("span").width()+160);
             msg=false;
             return;
         }else if(!reg.test(email.val())){
         	alertMsg("邮箱号码有误",email.offset().top-10,email.offset().left+email.closest("span").width()+160);
         	 msg=false;
             return;
	     }
    	 if(yzm1.val()==""){
    	     alertMsg("请填写验证码",yzm1.offset().top-10,yzm1.offset().left+yzm1.closest("span").width()+10);
    	     msg=false;
    	     return;
    	 }
    	 if(pwd3.val()==""||pwd3.val().length<6||!curpreg.test(pwd1.val())){
    	     alertMsg("6-20位的字母、数字或下划线_",pwd3.offset().top-10,pwd3.offset().left+pwd3.closest("span").width()+10);
    	     msg=false;
    	     return;
    	 }
    	 if(pwd4.val()==""){
    	     alertMsg("请确认密码",pwd4.offset().top-10,pwd4.offset().left+pwd4.closest("span").width()+10);
    	     msg=false;
    	     return;
    	 }else if(pwd3.val()!=pwd4.val()){
    	 	alertMsg("密码不一致",pwd4.offset().top-10,pwd4.offset().left+pwd4.closest("span").width()+10);
    	 	msg=false;
    	     return;
    	 }
		 if(msg==true){
			 $("#yx_form").submit();
		 }
	 }
}
