/* 2015-05-26 */

var patternMobile=/^0?(13[0-9]|15[012356789]|18[0236789]|17[0236789]|14[57])[0-9]{8}$/;
var patternEmail=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
var regCan=true;
$(document).ready(function() {
	$(".reg_top a").click(function(){
		$(".m_errTips").remove();
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
	//$('input').val(null);   //清空所有表单内容

	$("[name='loginName']").blur(function() {    //验证用户名称
		if(this.value){
			/*pattern = /^[a-zA-Z0-9_]{4,16}$/;
        if (!pattern.test(this.value)) {
            alertMsg("请填写正确的用户名",$(this).offset().top-10,$(this).offset().left+$(this).closest("li").width()+170);
        }*/
			$(this).removeClass("false");
		}else{
			alertMsg("用户名不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
			$(this).addClass("false");
		}
	});
	$("[name='password']").blur(function() {    //验证密码
		if(this.value){
			//pattern =/^\s*\S{6,}\s*$/;        //6到12位
			if (this.value.length<6||this.value.length>20) {
				alertMsg("请输入6-20位密码",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
				$(this).addClass("false");
			}else{
				$(this).removeClass("false");
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
			}
		}else{
			if(this.value != $("#PWD3").val()){
				alertMsg("密码不一致",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
				$(this).addClass("false");
			}else{
				$(this).removeClass("false");
			}
		}

	});
	$("[name='mobile']").blur(function() {    //验证手机号码
		if(this.value){

			if (!patternMobile.test(this.value)) {
				alertMsg("请填写正确的手机号",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
				$(this).addClass("false");
			}else{
				$(this).removeClass("false");
			}
		}else{
			alertMsg("手机号码不能为空",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
			$(this).addClass("false");
		}

	});
	$("[name='email']").blur(function() {    //验证邮箱
		if(this.value){
			if (!patternEmail.test(this.value)) {

				alertMsg("请填写正确的邮箱",$(this).offset().top-10,$(this).closest("li").offset().left+$(this).closest("li").width()+15);
				$(this).addClass("false");
			}else{
				$(this).removeClass("false");
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
					var personMobile = $("#hiddenMobile").val();
					var mobile = $("#formpersonal #mobile_person");
					var checkMobile = mobile.val();
					if(personMobile !="" && checkMobile !=""){
						if(personMobile != checkMobile){
							alertMsg("输入手机号码与发送短信验证的手机号码不一致",$(mobile).offset().top-10,$(mobile).closest("li").offset().left+$(mobile).closest("li").width()+15);
							return;
						}
					}
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
					var companyMobile = $("#hiddenCompanyMobile").val();
					var mobile = $("#reg_enterprise #mobile_persons");
					var checkMobile = mobile.val();
					if(companyMobile !="" && checkMobile !=""){
						if(companyMobile != checkMobile){
							alertMsg("输入手机号码与发送短信验证的手机号码不一致",$(mobile).offset().top-10,$(mobile).closest("li").offset().left+$(mobile).closest("li").width()+15);
							return;
						}
					}
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
		$("input").focus(function(){
        	$(".m_errTips").remove();
        });
	}
} 
function send(id){
	var mobile=$("#mobile_person").val();
	if(id=="2"){
		mobile=$("#mobile_persons").val();
	}
	if(id=="1"){
		$("#hiddenMobile").val(mobile);
	}
	if(id=="2"){
		$("#hiddenCompanyMobile").val(mobile);
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
		url: contextPath+"/comRegister/sendCode2?mobile="+mobile+"&scene=1&imagecode="+code,
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
