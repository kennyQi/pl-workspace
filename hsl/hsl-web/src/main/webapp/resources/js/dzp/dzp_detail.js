$(function(){

	//中部导航
	var subNavhei=$("#m_subNav").height();
	var $subNavA=$("#m_subNav .nav a");
	var oldsubNavAOn=0;
	$subNavA.each(function(i){
		$(this).click(function(){
			var id=$(this).attr("rel")
			,hei=$("#"+id).offset().top
			;
			$(window).scrollTop(hei-subNavhei);
			
			$subNavA.removeClass("on").eq(i).addClass("on");
			$("#subNav_bg").stop().animate({left:i*113},300);
			oldsubNavAOn=i;
		});
		
		$(this).hover(function(){
				$subNavA.removeClass("on");
				$("#subNav_bg").stop().animate({left:i*113},200);
				$subNavA.eq(i).addClass("on");
		},function(){
				$subNavA.removeClass("on");
				$("#subNav_bg").stop().animate({left:oldsubNavAOn*113},200);
					$subNavA.eq(oldsubNavAOn).addClass("on");
		});
	});
	
	
	//中部导航浏览记录跟随
	var subNavTop=$("#m_subNav").offset().top;
	$(window).scroll(function(){
		//回顶部按钮
		if($(window).scrollTop()>subNavTop){
			//$("#m_subNav").css({"top":0,"position":"fixed","background":"#f4f4f4"});
            $("#m_subNav").css({"top":0,"position":"fixed"});
			//回顶部按钮
			$(".g_totop").show();
		}else{
			$("#m_subNav").css({"top":0,"position":"absolute","background":"#fff"});
			//回顶部按钮
			$(".g_totop").hide();
		}
		for(var i=0,l=$subNavA.length;i<l;i++){
				var id=$subNavA.eq(i).attr("rel")
					,hei=$("#"+id).offset().top
					;
				if($(window).scrollTop()>=(hei-subNavhei)){
					$subNavA.removeClass("on");
					$("#subNav_bg").stop().animate({left:i*113},200);
					$subNavA.eq(i).addClass("on");
					oldsubNavAOn=i;
				}else{
					break;
				}
			}
	});
	
	
	//查看房型具体信息
	$(".tr .de").click(function(){
		if($(this).attr("class").indexOf("de_on")!=-1){
			$(this).removeClass("de_on").closest(".tr").find(".tr_de").hide();
		}else{
			$(this).addClass("de_on").closest(".tr").find(".tr_de").show();
		}
	});
	
	//查看房型鼠标滑动效果
	$(".tr .hs_word").hover(function(){
		$(this).addClass("hs_word_on");
	},function(){
		$(this).removeClass("hs_word_on");});

	//底部叉叉
	$("#needHistory #closeHist").hover(function(){
		$(this).css({"border":"1px solid #d4d4d4"});
	},function(){
		$(this).css({"border":"0px"});
	});
});

