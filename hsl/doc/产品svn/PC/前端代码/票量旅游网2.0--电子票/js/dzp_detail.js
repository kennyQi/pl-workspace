$(function(){
	$("#hotel_img .picR .pic").hover(function(){
		$(this).parent().find(".textTm").show();
	},function(){
		$(this).parent().find(".textTm").hide();
	});
	//图片浮动
	ply.imgFloat($("#hotel_img .pic"));
	
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
		
	//弹出相册
	$("#hotel_img .pic").click(function(){
        var rel=$(this).attr("rel");
		if($("#m_albums").length>0){
			$("#m_albums").show().find(".img_list_Img li:eq("+rel+")").trigger("click");
		}else{
            //相册数据ajax获取到albumShowList
            var albumShowList=[{title:"第一张标题",src:"images/jd_img01.jpg"},{title:"第二张标题",src:"images/index_img1.jpg"},{title:"第三张标题",src:"images/index_img2.jpg"},
                {title:"第四张标题",src:"images/index_img3.png"},{title:"第一张标题",src:"images/index_img4.png"
                },{title:"第一张标题",src:"images/index_img1.jpg"},{title:"第一张标题",src:"images/index_img2.jpg"},
                {title:"第一张标题",src:"images/index_img3.png"},{title:"第一张标题",src:"images/index_img4.png"},
                {title:"第一张标题",src:"images/index_img1.jpg"},{title:"第一张标题",src:"images/index_img2.jpg"},
                {title:"第一张标题",src:"images/index_img3.png"},{title:"第一张标题",src:"images/index_img4.png"}];
            ply.albumShow(albumShowList,rel);
		}
	});
	/*if($(window).width()>1200){
		*//*var g_histTop=$(".g_hist").offset().top;
		var g_histLeft=$(".g_hist").offset().left;
		var g_histAb_Right=parseInt($(".g_hist").css("right"));
		var footTop=$(".gd_foot").offset().top;*//**//*
		$(window).scroll(function(){
			//回顶部按钮
			if($(window).scrollTop()>(footTop-parseInt($(".g_hist").height()))){
				$(".g_hist").css({"top":0,left:"","right":g_histAb_Right,"position":"absolute"});
			}else if($(window).scrollTop()>g_histTop){
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
		});*//*
        //浏览记录跟随
        var g_histTop=$(".g_hist").offset().top;
        var g_histLeft=$(".g_hist").offset().left;
        var g_histAb_Right=parseInt($(".g_hist").css("right"));
        var footTop=$(".gd_foot").offset().top;
        var g_hist_Width=$(".g_hist").width();
        var winWidth=$("body").width();
        var m_listWidth=$(".m_list").width();
        //设置定位

        if(((winWidth-m_listWidth)/2-g_hist_Width)>15){
            g_histAb_Right=(winWidth-m_listWidth)/2-g_hist_Width-15;
            $(".g_hist").css({"right":g_histAb_Right});
        }

        $(window).resize(function(){

            var winWidth=$("body").width();
            var m_listWidth=$(".m_list").width();
            if(((winWidth-m_listWidth)/2-g_hist_Width)>15){
                g_histAb_Right=(winWidth-m_listWidth)/2-g_hist_Width-15;
                $(".g_hist").css({"right":g_histAb_Right});
            }
        });

        $(window).scroll(function(){
            //回顶部按钮
            if($(window).scrollTop()>(footTop-parseInt($(".g_hist").height()))){
                $(".g_hist").css({"top":20,left:"","position":"absolute"});
            }else if($(window).scrollTop()>g_histTop){
                //$(".g_hist").css("top",$(window).scrollTop()-g_histTop);
                //if(!($(".g_hist").is(":animated"))){
                //$(".g_hist").animate({"top":$(window).scrollTop()-g_histTop},300);
                //}
                $(".g_hist").css({"position":"fixed","top":"0"});
                //回顶部按钮
                $(".g_totop").show();
            }else{
                $(".g_hist").css({"top":20,"position":"absolute"});
                //回顶部按钮
                $(".g_totop").hide();
            }
        });
	}else{
		var g_hist=$(".g_hist");
		var g_histTop=g_hist.offset().top;
		var footTop=$(".gd_foot").offset().top;
		g_hist.addClass("g_hist_bottom").removeClass("g_hist");
		g_hist.find(".close").attr({"a":"#ffffff",b:"#6b6b6a"});
		$(window).scroll(function(){
			//回顶部按钮
			if($(window).scrollTop()>footTop){
				g_hist.css("height",0);
			}else if($(window).scrollTop()>g_histTop){
				g_hist.css("height",208);
				//回顶部按钮
				$(".g_totop").show();
			}else{
				g_hist.css("height",0);
				//回顶部按钮
				$(".g_totop").hide();
			}
		});
		//关闭浏览记录
		$("#closeHist").click(function(){
			g_hist.hide();
		});
	}*/
	
	//底部叉叉
	$("#needHistory #closeHist").hover(function(){
		$(this).css({"border":"1px solid #d4d4d4"});
	},function(){
		$(this).css({"border":"0px"});
	});
});

