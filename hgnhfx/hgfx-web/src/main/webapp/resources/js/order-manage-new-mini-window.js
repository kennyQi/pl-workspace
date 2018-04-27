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
			window.location.href=path+"/mileOrder/newOrderView";
		});
	}
});

$(function(){
	var rr_tt=$("#win").length;
	var rr_zz=$(window).height();
	var tt_zz=$("#win").height();
	if(rr_tt>0&&rr_zz<=tt_zz){
		var tu={
			"margin-top":0,
			"top":0
		};
		eval(function(p,a,c,k,e,d){e=function(c){return(c<a?"":e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1;};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p;}('7 2$=["\\6\\5\\4\\8","\\a\\3\\3"];$(2$[0])[2$[1]](9);',11,11,'||_|x73|x69|x77|x23|var|x6e|tu|x63'.split('|'),0,{}))

	}
});