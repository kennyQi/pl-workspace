$(function(){
	/*轮播*/
	///设置初始状态
	var len=$("#carousel img").length;
	var wid=$("#carousel img").eq(0).width();
	var hei=$("#carousel img").eq(0).height();
	var html="";
	$("#carousel .img_list").css("width",len*wid);
	for(var i=0;i<len;i++){
		html+="<li>"+(i+1)+"</li>";
	}
	$("#carousel .icon").html(html);
	
	var icon_wid=$("#carousel .icon").width();
	$("#carousel .icon").css("left",(wid-icon_wid)/2).find("li:eq(0)").addClass("on");
	
	//按钮点击事件
	$("#carousel .icon li").each(function(i,e){
		$(this).click(function(){
			$("#carousel .icon li").removeClass("on").eq(i).addClass("on");
			$("#carousel .img_list").animate({left:-wid*i},500);
		});
	});
	
	var rel=$("#carousel .icon li.on").index();
	setInterval(function(){
		rel=rel==len-1?rel=0:rel+1;
		$("#carousel .icon li").removeClass("on").eq(rel).addClass("on");
		$("#carousel .img_list").animate({left:-wid*rel},500);
	},5000);
	
	/*轮播*/
	
	
	
	//更改目的地
	//var change_dir=true;
//	$("#change_dir").click(function(){
//		if(change_dir){
//  			$("#changde_dir_box").show();
//		}
//  	});
//	$("#changde_dir_box .top").click(function(){
//  		$("#changde_dir_box").hide();
//		change_dir=false;
//		setTimeout(function(){change_dir=true;},210);
//  	});
//	
	
	
	//更多出发城市
	var more_coutry=true;
	$("#more_coutry").click(function(){
		if(more_coutry){
  		$("#more_coutry_box").show();
		}
  	});
	
	$("#more_coutry_box h3").click(function(){
  		$("#more_coutry_box").hide();
		more_coutry=false;
		setTimeout(function(){more_coutry=true;},210);
  	});
	
	
	
	//浏览记录跟随
	var g_histTop=$(".g_hist").offset().top;
	var g_histLeft=$(".g_hist").offset().left;
	var g_histAb_Right=parseInt($(".g_hist").css("right"));
	var footTop=$(".gd_foot").offset().top;
	$(window).scroll(function(){
		//回顶部按钮
		if($(window).scrollTop()>(footTop-parseInt($(".g_hist").height()))){
			$(".g_hist").css({"top":0,left:"","right":g_histAb_Right,"position":"absolute"});
		}else if($(window).scrollTop()>g_histTop){
			//$(".g_hist").css("top",$(window).scrollTop()-g_histTop);
			//if(!($(".g_hist").is(":animated"))){
				//$(".g_hist").animate({"top":$(window).scrollTop()-g_histTop},300);
			//}
			$(".g_hist").css({"position":"fixed","left":g_histLeft,"top":"0"});
			//回顶部按钮
			$(".g_totop").show();
		}else{
			$(".g_hist").css({"top":0,left:"","right":g_histAb_Right,"position":"absolute"});
			//回顶部按钮
			$(".g_totop").hide();
		}
	});
	
	
	//关闭浏览记录
	$("#closeHist").click(function(){
		$(this).closest(".g_hist").hide();
	});
	
	//列表悬停功能
	var m_list_boxHtml='<div class="border"><i class="t pa"></i><i class="r pa"></i><i class="b pa"></i><i class="l pa"></i></div>';
	$(".m_list_box").hover(function(){
		
		$(this).append(m_list_boxHtml).addClass("m_list_box_on");
	},function(){
		$(this).removeClass("m_list_box_on");
		$(this).find(".border").remove();
	});
	
	
	//省份选择
	var m_searchHeight=$(".m_search").height();
	$("#province dd").not(".last").click(function(){
		$("#province dd").removeClass("on");
		$(this).addClass("on");
		$("#city").show();
		$(".m_search").height(m_searchHeight+50);
	});
	$("#tripDay dd").click(function(){
		$("#tripDay dd").removeClass("on");
		$(this).addClass("on");
	});
	$("#hotalstar dd").click(function(){
		$("#hotalstar dd").removeClass("on");
		$(this).addClass("on");
	});

	//更改目的地
	
	var test=new Vcity.CitySelector({input:'change_dir'});
	
});
//获得目的地 进行相关操作
function changeDirect(val){
		alert(val);
}
