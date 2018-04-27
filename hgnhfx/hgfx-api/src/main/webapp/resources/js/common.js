$(function(){
	
	$(".table-content-pure").find("tr").hover(function(){
		if($(this).hasClass("thd")){
			return;
		}
	
		$(this).parent().find("tr").removeClass("hv");
		$(this).parent().find("tr").each(function(){
			$(this).find("td").eq(0).removeClass("hv2");
		});
		$(this).addClass("hv");
		$(this).find("td").eq(0).addClass("hv2");
	},function(){
		
	});
});