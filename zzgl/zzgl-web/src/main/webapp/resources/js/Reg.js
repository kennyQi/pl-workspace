/* liwei */
/* 2015-05-26 */

var patternMobile=/^0?(13[0-9]|15[012356789]|18[0236789]|17[0236789]|14[57])[0-9]{8}$/;
var patternEmail=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
var regCan=true;
$(document).ready(function() {
	//点击图片切换验证码
	$("[kaptchaImagea='kaptchaImagea']").click(function(){
		$("[kaptchaImagea='kaptchaImagea']").each(function(){
			$("[p_kaptchaImage='p_kaptchaImage']").hide().attr('src','../comRegister/captcha-image?'+ Math.floor(Math.random() * 100)).fadeIn();
		})

	})
	//点击图片切换验证码
	$("[p_kaptchaImage='p_kaptchaImage']").click(function(){
		$("[p_kaptchaImage='p_kaptchaImage']").each(function(){
			$("[p_kaptchaImage='p_kaptchaImage']").hide().attr('src','../comRegister/captcha-image?'+ Math.floor(Math.random() * 100)).fadeIn();
		})

	})
	var index=$("#index").val();//得到类型判断 1是个人 2是企业
	//页面加载判断是否有错误信息
	var pcodeError=$("#pcodeError").val();
	var valicodeInvalid=$("#valicodeInvalid").val();
	var emailBind=$("#emailBind").val();
	var userNameRepeat=$("#userNameRepeat").val();
	var mobileRepeat=$("#mobileRepeat").val();
	var mobileWrong=$("#mobileWrong").val();
	if(index=="2"){
		$(".reg_top a").click();
		if(pcodeError!=""){
			alertMsg("验证码错误",$(".verifications").offset().top-10,$(".verifications").closest("li").offset().left+$(".verifications").closest("li").width()+15);
		}
		if(valicodeInvalid!=""){
			alertMsg("手机验证码错误",$(".check_mobiles").offset().top-10,$(".check_mobiles").closest("li").offset().left+$(".check_mobiles").closest("li").width()+15);
		}
		if(emailBind!=""){
			alertMsg(emailBind,$(".emails").offset().top-10,$(".emails").closest("li").offset().left+$(".emails").closest("li").width()+15);
		}
		if(userNameRepeat!=""){
			alertMsg(userNameRepeat,$(".userNames").offset().top-10,$(".userNames").closest("li").offset().left+$(".userNames").closest("li").width()+15);
		}
		if(mobileRepeat!=""){
			alertMsg(mobileRepeat,$(".mobiles").offset().top-10,$(".mobiles").closest("li").offset().left+$(".mobiles").closest("li").width()+15);
		}
	}else{
		if(pcodeError!=""){
			alertMsg("验证码错误",$(".verification").offset().top-10,$(".verification").closest("li").offset().left+$(".verification").closest("li").width()+15);
		}
		if(valicodeInvalid!=""){
			alertMsg("手机验证码错误",$(".check_mobile").offset().top-10,$(".check_mobile").closest("li").offset().left+$(".check_mobile").closest("li").width()+15);
		}
		if(emailBind!=""){
			alertMsg(emailBind,$(".email").offset().top-10,$(".email").closest("li").offset().left+$(".email").closest("li").width()+15);
		}
		if(userNameRepeat!=""){
			alertMsg(userNameRepeat,$(".userName").offset().top-10,$(".userName").closest("li").offset().left+$(".userName").closest("li").width()+15);
		}
		if(mobileRepeat!=""){
			alertMsg(mobileRepeat,$(".mobile").offset().top+300,$(".mobile").closest("li").offset().left+$(".mobile").closest("li").width()+15);
		}
	}
	$(".agr i").click(function(){
		$(this).toggleClass('click');

	});

	/**********************************************表单验证***************************************************/
	var pattern = null;
	$("[name='loginName']").blur(function() {    //验证用户名称
		if(this.value){
			
			$(this).removeClass("false");
			$(".m_errTips").remove();
		}else{
			alertMsg("用户名不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
			$(this).addClass("false");
		}
	});
	
	$(".PWD1").blur(function() {    //验证密码
		if(this.value){
			var curpreg = /^(\w){6,20}$/;
			if (!curpreg.test(this.value)) {
				alertMsg("请输入6-20位的字母、数字或者下划线_",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
				$(this).addClass("false");
			}else{
				$(this).removeClass("false");
				$(".m_errTips").remove();
			}
		}else{
			alertMsg("密码不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
			$(this).addClass("false");
		}
	});
	$(".PWD2,.PWD4").blur(function() {    //验证再次输入的密码
		if($("#personal").hasClass("transparent_white")){
			if(this.value != $(".PWD1").val()){
				alertMsg("密码不一致",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
				$(this).addClass("false");
			}else{
				$(this).removeClass("false");
				$(".m_errTips").remove();
			}
		}else{
			if(this.value != $("#PWD3").val()){
				alertMsg("密码不一致",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
				$(this).addClass("false");
			}else{
				$(this).removeClass("false");
				$(".m_errTips").remove();
			}
		}

	});
	$("[name='mobile']").blur(function() {    //验证手机号码
		if(this.value){
			pattern = /^0?(13[0-9]|15[012356789]|18[0236789]|17[0236789]|14[57])[0-9]{8}$/;
			if (!pattern.test(this.value)) {
				alertMsg("请填写正确的手机号",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
				$(this).addClass("false");
			}else{
				$(this).removeClass("false");
				$(".m_errTips").remove();
			}
		}else{
			alertMsg("手机号码不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
			$(this).addClass("false");
		}

	});
	$("[name='email']").blur(function() {    //验证邮箱
		if(this.value){
			pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
			if (!pattern.test(this.value)) {

				alertMsg("请填写正确的邮箱",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
				$(this).addClass("false");
			}else{
				$(this).removeClass("false");
				$(".m_errTips").remove();
			}
		}else{
			alertMsg("邮箱不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
			$(this).addClass("false");
		}
	});
	$("[name='authcode']").blur(function() {    //验证码
		if(this.value==""){
			alertMsg("验证码不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
			$(this).addClass("false");
		}else{
			$(this).removeClass("false");
			$(".m_errTips").remove();
		}
	});
	$("[name='name']").blur(function() {    //验证码
		if(this.value==""){
			alertMsg("企业名不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
			$(this).addClass("false");
		}else{
			$(this).removeClass("false");
		}
	});
	$("[name='validCode']").blur(function() {    //验证码
		if(this.value==""){
			alertMsg("手机验证码不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
			$(this).addClass("false");
		}else{
			$(this).removeClass("false");
			$(".m_errTips").remove();
		}
	});


	/**********************************************表单验证***************************************************/
	$("[name='submit']").click(function(){
		var msg=true;
		var parent=$(this).closest(".per_box");
		parent.find("input").each(function(){
			if($(this).attr("warm")!=undefined){
				if($(this).val()==""){
					alertMsg($(this).attr("warm"),$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
					msg=false;
					return;
				}
				if($(this).hasClass("false")){
					alertMsg("输入有误,请核对后输入",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
					msg=false;
					return;
				}
			}
		});
		if(msg==true){
			if($("#personal").hasClass("transparent_white")){
				if($("#deal").hasClass("click")){
					//alert("提交个人注册")
					if($("#PWD1").val()== $("#PWD2").val()){
						$("#formpersonal").submit();
					}else{
						$(".PWD2").blur();
					}

				}else{
					alert("请勾选协议");
				}
			}else if($("#enterprise").hasClass("transparent_white")){
				if($("#deal_two").hasClass("click")){
					// alert("提交企业注册")
					if($("#PWD3").val()== $("#PWD4").val()){
						$("#reg_enterprise").submit();
					}else{
						$(".PWD2").blur();
					}

				}else{
					alert("请勾选协议");
				}
			}
		}
	});
});


function alertMsg(msg,ofTop,ofLeft){
	//if(!($(".m_errTips").length>0)){
	$(".m_errTips").remove();
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
	//}
} 
/*
function send(id){
	if(regCan==false) return;
	regCan=false;
	var contextPath=$("#contextPath").val();
	var mobile=$("#mobile_person").val();
	if(mobile==""){
		regCan=true;
		return false;
	}
	$.ajax({
		type: "POST",
		url: contextPath+"/comRegister/sendCode?mobile="+mobile+"&scene=1",
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType:"json",
		success: function(result) {
			if(result.status=="success"){
				
				$("#validToken_person").val(result.validToken);	
				
				var wait = 60;
				function time(){
					if (wait == 0) {
						$('.mobileyzm').html("<a href='javascript:;' class='mobileyzms yzm fr yahei color9 h2' onclick='send(2)'>免费获取验证码</a>");
						wait = 60;
					} else {
						$('.mobileyzm').html("<i class=' yzm fr yahei color9 h2' style='color:#a3a3a3;'>重新发送("+wait+")</i>");
						wait--;
						setTimeout(function() {
							time();
						},1000);
					}
				}
				time();
			}else{
				//$(this).removeAttribute("disabled");			
				$(this).val("免费获取验证码");
				wait_person = 0;
				alertMsg(result.error,$("#mobile_person").offset().top-10,$("#mobile_person").closest("li").offset().left+$("#mobile_person").closest("li").width()+15);

			}
			regCan=true;
		},
		error:function(result) {
			regCan=true;
			alertMsg("验证码获取失败",$("#mobile_person").offset().top-10,$("#mobile_person").closest("li").offset().left+$("#mobile_person").closest("li").width()+15);
		}
	});
}
*/


function send(id){
	var mobile=$("#mobile_person").val();
	if(id=="2"){
		mobile=$("#mobile_persons").val();
	}
	if(!mobile){
		if(id=="2"){
			alertMsg("手机号码不能为空",$("#mobile_persons").offset().top-10,$("#mobile_persons").closest("li").offset().left+$("#mobile_persons").closest("li").width()+15);
		}else{
			alertMsg("手机号码不能为空",$("#mobile_person").offset().top-10,$("#mobile_person").closest("li").offset().left+$("#mobile_person").closest("li").width()+15);
		}
		return;
	}
	if(!patternMobile.test(mobile)){
		if(id=="2"){
			alertMsg("手机号码格式不正确",$("#mobile_persons").offset().top-10,$("#mobile_persons").closest("li").offset().left+$("#mobile_persons").closest("li").width()+15);
		}else{
			alertMsg("手机号码格式不正确",$("#mobile_person").offset().top-10,$("#mobile_person").closest("li").offset().left+$("#mobile_person").closest("li").width()+15);
		}
		return;
	}

	alertCode(mobile,id)
}

//弹出验证码
function alertCode(mobile,id){
	var contextPath=$("#contextPath").val();
	var html='<div class="alertCodeBoxBg"></div>' +
		'<div class="alertCode">' +
		'<div class="alertCodeBox">' +
		'<a href="javascript:;"></a>'+
		'<input type="text" id="alertCodeValue" />' +
		'<img src="'+contextPath+'/comRegister/sendImageCodeByReg?time='+new Date().getTime()+'">' +
		'<button onclick="sendMsg('+mobile+','+id+')">提交</button></div></div>';
	$("body").append(html);

	$(".m_errTips").remove();
	$(".alertCodeBox a").click(function(){
		$(".alertCodeBox,.alertCodeBoxBg").remove();
	});
}

//请求验证码
function sendMsg(mobile,id){
	if(regCan==false) return;
	regCan=false;
	var contextPath=$("#contextPath").val();
	var code=$("#alertCodeValue").val();//验证码
	if(code==""){
		alert("验证码不能为空！");
		regCan=true;
		return;
	}
	$.ajax({
		type: "POST",
		url: contextPath+"/comRegister/sendCode?mobile="+mobile+"&scene=1&imagecode="+code,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType:"json",
		success: function(result) {
			if(result.status=="success"){
				if(id=="2"){
					$("#validToken_personq").val(result.validToken);
				}else{
					$("#validToken_person").val(result.validToken);
				}
				var wait = 60;
				function time(){
					if (wait == 0) {
						if(id=="1"){
							$('.mobileyzm').html("<a href='javascript:;' class='mobileyzms yzm fr yahei color9 h2' onclick='send(1)'>免费获取验证码</a>");
						}else{
							$('.mobileyzms').html("<a href='javascript:;' class='mobileyzms yzm fr yahei color9 h2' onclick='send(2)'>免费获取验证码</a>");
						}
						regCan=true;
						wait = 60;
					} else {
						if(id=="1"){
							$('.mobileyzm').html("<i class=' yzm fr yahei color9 h2' style='color:#a3a3a3;'>重新发送("+wait+")</i>");
						}else{
							$('.mobileyzms').html("<i class=' yzm fr yahei color9 h2' style='color:#a3a3a3;'>重新发送("+wait+")</i>");
						}
						wait--;
						setTimeout(function() {
								time();
							},
							1000)
					}
				}
				time();
			}else{
				//$(this).removeAttribute("disabled");
				$(this).val("免费获取验证码");
				wait_person = 0;
				if(id=="1"){
					alertMsg(result.error,$("#mobile_person").offset().top-10,$("#mobile_person").closest("li").offset().left+$("#mobile_person").closest("li").width()+15);
				}else{
					alertMsg(result.error,$("#mobile_persons").offset().top-10,$("#mobile_persons").closest("li").offset().left+$("#mobile_persons").closest("li").width()+15);
				}
				regCan=true;

			}
			$(".alertCodeBox,.alertCodeBoxBg").remove();
		},
		error:function(result) {
			regCan=true;
			if(id=="1"){
				alertMsg("验证码获取失败",$("#mobile_person").offset().top-10,$("#mobile_person").closest("li").offset().left+$("#mobile_person").closest("li").width()+15);
			}else{
				alertMsg("验证码获取失败",$("#mobile_persons").offset().top-10,$("#mobile_persons").closest("li").offset().left+$("#mobile_persons").closest("li").width()+15);
			}
			$(".alertCodeBox,.alertCodeBoxBg").remove();
		}
	});
}