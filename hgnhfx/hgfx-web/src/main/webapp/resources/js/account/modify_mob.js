function getCookie(c_name)
{

	if (document.cookie.length > 0)

	{

		var c_start = document.cookie.indexOf(c_name + "=");

		if (c_start != -1)

		{

			c_start = c_start + c_name.length + 1;

			c_end = document.cookie.indexOf(";", c_start);

			if (c_end == -1)
				c_end = document.cookie.length;

			return unescape(document.cookie.substring(c_start, c_end));

		}

	}

	return "";

}

function setCookie(c_name, value, expiredays)
{

	var exdate = new Date();

	exdate.setDate(exdate.getDate() + expiredays);

	document.cookie = c_name + "=" + escape(value) +

	((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());

}
function checkCookie(){
	if(mob_near){
		var code_text=getCookie('tel'+mob_near);
		if(!code_text || code_text == "重新获取" || code_text == "获取验证码"){
			
		}else{
			var codeTime=+code_text.replace(/\D/g,"");
			var code_id = document.getElementById("code");
			//code_id.value=mob_near;
			$("#mobile").val(mob_near);
			
			hd.init(code_id,function(){
				var t=code_id;
				t.style.cssText+="background-color:#cccccc;border-top: 1px solid #cccccc;"
				+"  border-bottom: 1px solid #cccccc;  border-right: 1px solid #cccccc;";
				setTimeout(function(){
					t.style.cssText+="background-color:#4799ef;border-top: 1px solid #4799ef;"
				+"  border-bottom: 1px solid #4799ef;  border-right: 1px solid #4799ef;";
				},60000);

			},'获取验证码','重新获取',60,true);
			$("#code").attr("data-time",codeTime);
			code_id.click();
			
		}
	}
}
function storeCookie(){
	setInterval(function(){
		var tel=document.getElementById("mobile").value;
		if($.trim(tel) != ''){
			var codeText=document.getElementById("code").innerHTML;
			setCookie('tel'+tel,codeText,1);
		}
	},1000);
}
/**
 * @param needCodeCheck是否需要code验证
 */
function validate_send_sms(mobile,smsCode,needCodeCheck){
	warningRemove1();
	var reg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;
	if(mobile == null || mobile == "" || mobile == undefined){
		warning1("请输入手机号码",1);
		return false;
	} else if(!reg.test(mobile)){
		warning1("输入正确手机号",1);
		return false;
	} else {
		warningRemove1();
	}
	
	if(needCodeCheck){
		if(smsCode == null || smsCode == "" || smsCode == undefined){
			$(".smsCode").html("请输入验证码");
			warning1("请输入验证码",2);
			return false;
		} else {
			warningRemove1();
		}
	}
	
	return true;
}
function validate_send_sms_boolean(mobile,smsCode,needCodeCheck){
	var reg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;
	if(mobile == null || mobile == "" || mobile == undefined){
		return false;
	} else if(!reg.test(mobile)){
		return false;
	} 
	
	if(needCodeCheck){
		if(smsCode == null || smsCode == "" || smsCode == undefined){
			return false;
		} 
	}
	
	return true;
}
function is_get_code(){
	//判断是否可以获取验证码
	var code_text = $("#code").text();
	if(code_text == "重新获取" || code_text == "获取验证码"){
		return true;
	}
	
	return false;
}
function beforeClickOperate(){
	//此处是点击之前的操作
	var mobile = $("#mobile").val();
	//启动倒计时，值变为false;停止，true
	hd.isStop=true;
	var isStop = validate_send_sms_boolean(mobile,null,false);
	if(!isStop){
		hd.isStop=true;
	} else {
		if(is_get_code()){
			var isStop = send_sms();
			hd.isStop=isStop;
			if(isStop){
				//修复code.js执行覆盖了提示的bug
				setTimeout(function(){
					if(globeInfo != ""){
						warning1(globeInfo,null);
					}
				},100);
			}
		}
	}
	
	return;
}
function send_sms(){
	var mobile = $("#mobile").val();
	if(!validate_send_sms(mobile,null,false)){
		return;
	} 
	
	var isStop = false;
	$.ajax({
		type:'POST',
		url:webRoot+"/account/prepareSendSms?type=2",
		async:false,
		data:{"mobile":mobile,"token":token},
		dataType:'json',
		success: function(data){
			if(data.code == 200){
				warningRemove1();
				isStop=false;
				send_sms_really(mobile);
			} else if(data.code == 101){
				isStop=true;
				globeInfo="";
			} else {
				isStop=true;
				globeInfo = data.msg;
			}
		}
	});
	
	return isStop;
}

function send_sms_really(mobile){
	$.ajax({
		type:'POST',
		url:webRoot+"/account/sendSms",
		data:{"mobile":mobile,"token":token},
		dataType:'json',
		success: function(data){
			if(data.code == 200){
				
			} else {
				warning1(data.msg);
			}
		}
	});
}
var globeInfo='';
$(function(){
	
	/**
	param1:验证码id
	param2:点击获取处理
	param3:省略
	param4:省略
	*/
	var code_id = document.getElementById("code");
	hd.init(code_id,function(){
		var t=code_id;
		t.style.cssText+="background-color:#cccccc;border-top: 1px solid #cccccc;"
		+"  border-bottom: 1px solid #cccccc;  border-right: 1px solid #cccccc;";
		setTimeout(function(){	
			t.style.cssText+="background-color:#4799ef;border-top: 1px solid #4799ef;"
		+"  border-bottom: 1px solid #4799ef;  border-right: 1px solid #4799ef;";
		},60000);
	},'获取验证码','重新获取',60);
	
	$("#code").click(function(){
		var mobile = $("#mobile").val();
		if(!validate_send_sms(mobile,null,false)){
			return;
		}
	});
	
	$("#addAccount2").click(function(){
		check_sms_code();
	});
	
	$("#pa_cancel").click(function(){
		hideModifyPasswordWin();
	});
	$("#pa_ok").click(function(){
		modify_pass();
	});
	$("#see").click(function(){
		hideModifySucWin();
	});
	
	function check_sms_code(){
		var mobile = $("#mobile").val();
		var smsCode = $("#smsCode").val();
		if(!validate_send_sms(mobile,smsCode,true)){
			return;
		}
		
		$.ajax({
    		type:'POST',
    		url:webRoot+"/account/checkCode?type=2",
    		data:{"mobile":mobile,"code":smsCode,"token":token},
    		dataType:'json',
    		success: function(data){
    			if(data.code == 200){
    				warningRemove1();
    				alert("修改成功");
    				hideModifyMobileWin();
    				location.reload();
    			} else {
    				warningRemove1();
    				warning1(data.msg,null);
    			}
    		} 
    	});
	}
	
	function modify_pass(){
		var originalPass = $("#originalPass").val();
		var newPass = $("#newPass").val();
		var againPass = $("#againPass").val();
		
		if(!validate_pass(originalPass,newPass,againPass)){
			return;
		}
		
		$.ajax({
    		type:'POST',
    		url:webRoot+"/account/modfiyPass",
    		data:{"againPass":againPass,"originalPass":originalPass,"newPass":newPass,"token":token},
    		dataType:'json',
    		success: function(data){
    			if(data.code == 200){
    				hideModifyPasswordWin();
    				showModifySucWin();
    				location.reload();
    			} else {
    				warningRemove2();
    				warning2(data.msg,null);
    			}
    		}
    	});
	}
	
	function validate_pass(originalPass,newPass,againPass){
		if(originalPass == null || originalPass == "" || originalPass == undefined){
			warning2("请输入原始密码",1);
			return false;
		} else {
			warningRemove2();
		}
		
		if(newPass == null || newPass == "" || newPass == undefined){
			warning2("请输入新密码",2);
			return false;
		} else {
			warningRemove2();
		}
		
		if(againPass == null || againPass == "" || againPass == undefined){
			warning2("请输入新密码",3);
			return false;
		} else {
			warningRemove2();
		}
		
		if(newPass.length < 8 || newPass.length > 20){
			warning2("请输入8~20位由字符/数字/符号(除空格)组成的新密码",2);
			return false;
		} else {
			warningRemove2();
		}
		
		if(againPass.length < 8 || againPass.length > 20){
			warning2("请输入8~20位由字符/数字/符号(除空格)组成的新密码",3);
			return false;
		} else {
			warningRemove2();
		}
		
		if(newPass != againPass){
			warning2("两次输入不一致，请重新输入",3);
			return false;
		} else {
			warningRemove2();
		}
		
		if(passwordLevel(newPass) != 2){
			warning2("密码必须包含字母数字,可选标点符号,空格除外",2);
			return false;
		} else {
			warningRemove2();
		}
		
		if(passwordLevel(againPass) != 2){
			warning2("密码必须包含字母数字,可选标点符号,空格除外",3);
			return false;
		} else {
			warningRemove2();
		}
		
		return true;
	}
	
	/**
	 * 密码等级
	 * @param password密码
	 */
	function passwordLevel(password) {
		var Modes = 0;
		for (i = 0; i < password.length; i++) {
			Modes |= CharMode(password.charCodeAt(i));
		}
		return bitTotal(Modes);
		// CharMode函数
		function CharMode(iN) {
			if (iN >= 48 && iN <= 57)// 数字
				return 1;
			/*if (iN >= 65 && iN <= 90) // 大写字母
				return 2;*/
			if ((iN >= 97 && iN <= 122) || (iN >= 65 && iN <= 90))
				// 大小写
				return 4;
			/*else
				return 8;*/ // 特殊字符
		}
		// bitTotal函数
		function bitTotal(num) {
			modes = 0;
			for (i = 0; i < 4; i++) {
				if (num & 1)
					modes++;
				num >>>= 1;
			}
			return modes;
		}
	}
	
	checkCookie();
	storeCookie();
	 
})