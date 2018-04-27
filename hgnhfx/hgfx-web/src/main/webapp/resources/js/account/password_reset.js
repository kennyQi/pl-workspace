
$(function(){
	
	$(".login-btn").click(function(){
		reset_pass();
	});
	
	function reset_pass(){
		var newPass = $("#password").val();
		var againPass = $("#login").val();
		if(!validate(newPass,againPass)){
			return;
		}
		
		$.ajax({
    		type:'POST',
    		url:webRoot+"/accountManager/resetPass",
    		data:{"againPass":againPass,"newPass":newPass,"token":token},
    		dataType:'json',
    		success: function(data){
    			if(data.code == 200){
    				location.href = webRoot + "/accountManager/resetPassSucc";
    			} else if(data.code == 301) {
    				warningRemove1();
    				warningRemove2();
    				warning2(data.msg);
    			} else {
    				warningRemove1();
    				warningRemove2();
    				warning1(data.msg);
    			}
    		}
    	});
		
	}
	
	function validate(newPass,againPass){
		if(newPass == null || newPass == "" || newPass == undefined){
			warning1("请输入密码");
			return false;
		} else {
			warningRemove1();
		}
		
		if(againPass == null || againPass == "" || againPass == undefined){
			warning2("请输入密码");
			return false;
		} else {
			warningRemove2();
		}
		
		if(newPass.length < 8 || newPass.length > 20){
			warning1("请输入8~20位由字符/数字/符号(除空格)组成的新密码");
			return false;
		} else {
			warningRemove1();
		}
		
		if(againPass.length < 8 || againPass.length > 20){
			warning2("请输入8~20位由字符/数字/符号(除空格)组成的新密码");
			return false;
		} else {
			warningRemove2();
		}
		
		if(newPass != againPass){
			warning2("两次输入不一致，请重新输入");
			return false;
		} else {
			warningRemove2();
		}
		
		if(passwordLevel(newPass) != 2){
			warning1("密码必须包含字母数字,可选标点符号,空格除外");
			return false;
		} else {
			warningRemove1();
		}
		
		if(passwordLevel(againPass) != 2){
			warning2("密码必须包含字母数字,可选标点符号,空格除外");
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
});