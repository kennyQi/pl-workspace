//手机号格式校验
function checkMobile(mobile){
	var result={code:1,text:'合法手机号'};
	if (mobile == null || mobile == undefined || mobile == "") { 
		result.code=-1;
		result.text="手机号码不能为空！";
	}else if (!mobile.match(/^(((13[0-9])|(15[0-3,5-9])|(18[0-9])|(17[01678])|147)\d{8})$/)) { 
		result.code=-1;
		result.text="手机号码格式不正确！"; 
	} 
	return result; 
}
//判断参数是否为空
function isEmpty(v){
	if(v == null || v == undefined || v == ''){
		return true;
	}else{
		return false;
	}
}