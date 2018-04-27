$(function(){
	$("#addhist .inp").focus(function(){
		var rel=$(this).attr("old");
		$(this).addClass("inp_on");
		if(rel==$(this).val()||rel==$(this).html()){
				$(this).val("").html("");
		}
	});
	$("#addhist .inp").blur(function(){
		var rel=$(this).attr("old");
			if($(this).val()==""){
				$(this).val(rel).removeClass("inp_on");
			}
			
	});
	$(".tm_bg").add(".tm").click(function(){
		$(".tm_bg").add(".tm").hide();
	});
});