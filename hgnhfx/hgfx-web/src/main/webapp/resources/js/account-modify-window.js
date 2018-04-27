function showModifyMobileWin(){//修改绑定手机弹出框提示
	$("#winP").show();
	$("#winAccount").show();
}

function hideModifyMobileWin(){//修改绑定手机弹出框提示取消
	$("#winP").hide();
	$("#winAccount").hide();
	location.reload();
}

function showModifyPasswordWin(){//修改密码弹出框提示
	$("#winP").show();
	$("#modifyAccount").show();
}

function hideModifyPasswordWin(){//修改密码弹出框提示取消
	$("#winP").hide();
	$("#modifyAccount").hide();
	location.reload();
}

function showModifySucWin(){//修改密码成功弹出框提示
	$("#winP").show();
	$("#modifytip").show();
}

function hideModifySucWin(){//修改密码成功弹出框提示取消
	$("#winP").hide();
	$("#modifytip").hide();
}

$(function(){
	//showModifyMobileWin();
	//hideModifyMobileWin();
	
	//showModifyPasswordWin();
	//hideModifyPasswordWin();
	
	//showModifySucWin();
	//hideModifySucWin();
});