/*新建子账号显示方法*/
function showWin(){
	$("#winP").show();
	$("#winAccount").show();
}
/*新建子账号隐藏方法*/
function hideWin(){
	$("#winP").hide();
	$("#winAccount").hide();
}

/*删除账号显示方法*/
function showWin2(){
	$("#winP").show();
	$("#delAccount").show();
}
/*删除账号隐藏方法*/
function hideWin2(){
	$("#winP").hide();
	$("#delAccount").hide();
}

function hideWin3()
{
$("#winP").hide();
$("#resetAccount").hide();
}

//验证提示
function tipShow(){
	$(".tip-show-a").show();
	$("#user ").css("border","1px solid #ea5657");
}
function tipHide(){
	$(".tip-show-a").hide();
	$("#user ").css("border","1px solid #d5d5d5");
	$("#name ").css("border","1px solid #d5d5d5");
}
$(function(){
	if($("body").find("#winAccount").length == 1){
		//showWin();
		$("#closeWin").click(function(){
			hideWin();
		});
		
		$("#closeWin2").click(function(){
			hideWin2();
		});
		$("#closeWin3").click(function(){
			hideWin3();
		});
		
		tipShow();tipHide();
	}
});