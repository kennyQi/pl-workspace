function closeWin(){
	$("#winP").hide();
	$("#win").hide();
}

$(function(){
	var func=function(){
		if($("#win").length==0 && $("#winP").length==0){
			var str='';
			str+='<div id="winP" style=""></div>';
			str+='<div id="win" style="">';
			str+='<div id="closez" onclick="closeWin();" style=""></div>';
			str+='</div>';
			
			$("body").append(str);
		}
		
		$("#winP").show();
		$("#win").show();
	};
	$("#api").click(func);
	
	if($(".change-select").length>3){
		$("#api2").click(func);
	}
});