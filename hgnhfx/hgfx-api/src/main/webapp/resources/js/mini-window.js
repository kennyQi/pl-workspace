function showWin(){
	$("#winP").show();
	$("#win").show();
}

function hideWin(){
	$("#winP").hide();
	$("#win").hide();
}

$(function(){
	if($("body").find("#win").length == 1){
		$("#closeWin").click(function(){
			hideWin();
		});
	}
});