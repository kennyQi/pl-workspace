var count = 0;
$(function(){
	
	/**
	param1:验证码id
	param2:点击获取处理
	param3:省略
	param4:省略
	*/
	var code_id = document.getElementById("code");
	$ls4d6.init(code_id,function(){
		var t=code_id;
		t.style.cssText+="background-color:#cccccc;border-top: 1px solid #cccccc;"
		+"  border-bottom: 1px solid #cccccc;  border-right: 1px solid #cccccc;";
		setTimeout(function(){
			t.style.cssText+="background-color:#4799ef;border-top: 1px solid #4799ef;"
		+"  border-bottom: 1px solid #4799ef;  border-right: 1px solid #4799ef;";
		},60000);
		
		var len = $("#code").html().length;
		console.log(len);
		if(len == 5){
			send_sms();
		} else {
			return;
		}
	},'获取验证码','重新获取',60);
	
	/*$("#code").click(function(){
		send_sms();
	});*/
	
	$(".login-btn").click(function(){
		check_sms_code();
	});
	
	function send_sms(){
		var mobile = $("#login").val();
		if(!validate_send_sms(mobile,null,false)){
			return;
		} 
		
		$.ajax({
    		type:'POST',
    		url:webRoot+"/accountManager/sendSms",
    		data:{"mobile":mobile},
    		dataType:'json',
    		success: function(data){
    			if(data.code == 200){
    				warningRemove1();
    				alert(data.msg)
    			} else {
    				warningRemove1();
    				warningRemove2();
    				warning1(data.msg);
    			}
    		}
    	});
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
	
	function check_sms_code(){
		var mobile = $("#login").val();
		var smsCode = $("#password").val();
		if(!validate_send_sms(mobile,smsCode,true)){
			return;
		}
		
		$.ajax({
    		type:'POST',
    		url:webRoot+"/accountManager/checkCode",
    		data:{"mobile":mobile,"code":smsCode},
    		dataType:'json',
    		success: function(data){
    			if(data.code == 200){
    				location.href = webRoot + "/accountManager/resetPass?num="+data.msg;
    			} else {
    				warningRemove1();
    				warningRemove2();
    				warning2(data.msg);
    			}
    		}
    	});
	}
	 
})