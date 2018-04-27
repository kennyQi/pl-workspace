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
			$("#login").val(mob_near);
			
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
		var tel=document.getElementById("login").value;
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
	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
	if(mobile == null || mobile == "" || mobile == undefined){
		warning1("请输入手机号码");
		return false;
	} else if(!reg.test(mobile)){
		warning1("输入正确手机号");
		return false;
	} else {
		warningRemove1();
	}
	
	if(needCodeCheck){
		if(smsCode == null || smsCode == "" || smsCode == undefined){
			$(".smsCode").html("请输入验证码");
			warning2("请输入验证码");
			return false;
		} else {
			warningRemove2();
		}
	}
	
	return true;
}
var globeInfo='';
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
	var mobile = $("#login").val();
	//启动倒计时，值变为false;停止，true
	if(!validate_send_sms(mobile,null,false)){
		warningRemove1();
		hd.isStop=true;
	} else {
		if(is_get_code()){
			var isStop = send_sms();
			hd.isStop=isStop;
			if(isStop){
				//修复code.js执行覆盖了提示的bug
				setTimeout(function(){
					if(globeInfo != ""){
						warning1(globeInfo);
					}
				},100);
			}
		}
	}
	
	return;
}
function send_sms(){
	var mobile = $("#login").val();
	if(!validate_send_sms(mobile,null,false)){
		return;
	} 
	
	var isStop = false;
	$.ajax({
		type:'POST',
		url:webRoot+"/accountManager/prepareSendSms",
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
				warningRemove1();
				warningRemove2();
				warning1(data.msg);
				globeInfo=data.msg;
			}
		}
	});
	
	return isStop;
}

function send_sms_really(mobile){
	$.ajax({
		type:'POST',
		url:webRoot+"/accountManager/sendSms",
		data:{"mobile":mobile,"token":token},
		dataType:'json',
		success: function(data){
			if(data.code == 200){
				
			} else {
				warning1(data.msg);
				hd.isStop=true;
			}
		}
	});
}

$(function(){
	
	/**
	param1:验证码id
	param2:点击获取处理
	param3:省略
	param4:省略
	*/
	//beforeClickOperate();
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
		var mobile = $("#login").val();
		if(!validate_send_sms(mobile,null,false)){
			hd.isStop=true;
			return;
		}
	});
	
	$(".login-btn").click(function(){
		check_sms_code();
	});
	
	function check_sms_code(){
		var mobile = $("#login").val();
		var smsCode = $("#password").val();
		if(!validate_send_sms(mobile,smsCode,true)){
			return;
		}
		
		$.ajax({
    		type:'POST',
    		url:webRoot+"/accountManager/checkCode?type=1",
    		data:{"mobile":mobile,"code":smsCode,"token":token},
    		dataType:'json',
    		success: function(data){
    			if(data.code == 200){
    				$("#login").val("");//防止back的时候触发send_SMS方法
    				location.href = webRoot + "/accountManager/resetPass?num="+data.msg;
    			} else {
    				warningRemove1();
    				warningRemove2();
    				warning2(data.msg);
    			}
    		}
    	});
	}
	
	checkCookie();
	storeCookie();
	 
})