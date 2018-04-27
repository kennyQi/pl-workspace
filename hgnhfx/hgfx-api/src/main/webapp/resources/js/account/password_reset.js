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
    		data:{"againPass":againPass,"newPass":newPass},
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
			warning1("请输入8~20位由字符/数字/标点符合组成的新密码");
			return false;
		} else {
			warningRemove1();
		}
		
		if(againPass.length < 8 || againPass.length > 20){
			warning2("请输入8~20位由字符/数字/标点符合组成的新密码");
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
		
		return true;
	}
	
});