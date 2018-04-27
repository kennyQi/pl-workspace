$(function(){
	/*轮播*/
	///设置初始状态
	var len=$("#carousel .turn").length;
	var wid=parseInt($("#carousel").eq(0).width());
	var hei=$("#carousel .turn").eq(0).height();
	var html="";
	$("#carousel .turn").width(wid);
	$("#carousel .turn").hide().eq(0).show();
	//$("#carousel .img_list").css("width",len*wid);
	for(var i=0;i<len;i++){
		html+="<li>"+(i+1)+"</li>";
		$("#carousel .turn").eq(i).css("z-index",0);
	}
	$("#carousel .icon").html(html);
	
	var icon_wid=$("#carousel .icon").width();
	//$("#carousel .icon").css("left",(wid-icon_wid)/2).find("li:eq(0)").addClass("on");
	$("#carousel .icon").find("li:eq(0)").addClass("on");
	$("#carousel .turn").eq(0).css("z-index",3);
	
	//按钮点击事件
	$("#carousel .icon li").each(function(i,e){
		$(this).click(function(){
			if($(this).attr("class")!="on"){
				$("#carousel .icon li").removeClass("on").eq(i).addClass("on");
				$("#carousel .turn").fadeOut(800).eq(i).show(0);
				setTimeout(function(){
					$("#carousel .turn").css("z-index",0).eq(i).css("z-index",len);
				},500);
			}
		});
	});
	if(len>1){
	setInterval(function(){
		var rel=$("#carousel .icon li.on").index();
		if(rel==(len-1)){
			rel=0;
		}else{
			rel++;
		}
		$("#carousel .icon li").removeClass("on").eq(rel).addClass("on");
		$("#carousel .turn").fadeOut(800).eq(rel).show(0);
		setTimeout(function(){
			$("#carousel .turn").css("z-index",0).eq(rel).css("z-index",len);
		},500);
	},3000);
	}
	/*轮播*/
	
	//机票时间选择

	 $("#startTime").click(function(){
		 $(".cityBox").addClass("hide");
		 laydate();
	 });
	 $("#hotel_in,#hotel_out").click(function(){
		 $(".cityBox").addClass("hide");
		 laydate();
	 });
	
	/*搜索框切换*/
	$("#select_tab span").each(function(i){
		$(this).click(function(){
			$("#select_tab span").removeClass("on");
			$(this).addClass("on");
			$(this).closest(".select_items").find(".tab_box").hide().eq(i).show();
			if(i==1){
				$(".select_btn").hide();
			}else{
				$(".select_btn").show();
			}
		});
	});
	
var test=new VcitySmall.CitySelector({input:'citySelect_from'});
var test2=new VcitySmall.CitySelector({input:'citySelect_to'});
var test3=new Vcity.CitySelector({input:'citySelect_line_from'});
var test4=new Vcity.CitySelector({input:'citySelect_line_to'});
/*var test5=new Vcity.CitySelector({input:'hotel_city'});*/


	//公告轮播
	var text_carouselHeight=$("#text_carousl").height()
		,text_carouselList=$("#text_carousl .group .news").length
		,$text_carousl=$("#text_carousl")
		,text_carouslHtml=$text_carousl.find(".group").clone()
		;
	//初始状态设置
	/*$text_carousl.append(text_carouslHtml);
	$text_carousl.find(".group").eq(1).css("top",text_carouselHeight*text_carouselList);
	setInterval(function(){
		var F_oldHeight=parseInt($(".group").eq(0).css("top"));
		var S_oldHeight=parseInt($(".group").eq(1).css("top"));
		$(".group").eq(0).animate({top:F_oldHeight-text_carouselHeight},300);
		$(".group").eq(1).animate({top:S_oldHeight-text_carouselHeight},300,function(){
			if(parseInt($text_carousl.attr("rel"))==text_carouselList){
				$text_carousl.append($(".group").eq(0).css("top",text_carouselHeight*(text_carouselList-1)).detach());
				$text_carousl.attr("rel",0);
			}
			$text_carousl.attr("rel",parseInt($text_carousl.attr("rel"))+1);
		});
	},5000);*/
	
	
	//特惠专区效果
	
	$(".prefe_box").hover(function(){
		$(this).addClass("prefe_box_on");
	},function(){
		$(this).removeClass("prefe_box_on");
	});
	
	//跟团游
	var m_follow={
		that:$("#m_follow"),
		action:function(){
			//初始化
			var $tips=this.that.find(".tips")
				,boxWid=this.that.width()
				,wid=this.that.find(".tips").innerWidth()
				,$box=this.that.find(".m_follow_box")
				,len=$box.length
				;
			//hover事件
			$tips.each(function(i,e){
				$(this).addClass("c"+(i%3+1));
				$(this).mouseover(function(e){
					for(var q=0;q<len;q++){
						if(q<=i){
							//if(!($box.is(":animated"))){
								$box.eq(q).stop().animate({"left":q*wid},300);
							//}
						}else{
							
							//if(!($box.is(":animated"))){
							$box.eq(q).stop().animate({"left":(boxWid-(len-q)*wid)},300);
							//}
						}
					}
				});
			});
			$tips.eq(0).trigger("mouseover");
			
		}
	}
	m_follow.action();
		
	//景点轮播	
	(function(){
		var obj=$("#m_carousel")
			,$box=obj.find(".list_box")
			,$list=obj.find(".list")
			,len=$box.length
			,leftHandle=obj.find(".left")
			,rightHandle=obj.find(".right")
			,cloneHtml=$list.clone()
			,wid=$box.width()
			,zIndex=parseInt(obj.parent().find(".m_carou_bg").css("z-index"))
			;
		//初始化
		$list.before(cloneHtml)
		//更新list对象
		$list=obj.find(".list");
		$list.width(len*wid).eq(0).css({"left":-len*wid});
		obj.attr("rel",0);
		//$list.eq(1).find(".list_box:eq(0)").css({"z-index":zIndex+1});
		
		$(".m_carou_bg").css({"width":((obj.parent().width()-1200)/2),"left":0});
		$(".m_carou_bg2").css({left:"",right:0});
		
		//向左事件
		rightHandle.click(function(){
			var rel=parseInt(obj.attr("rel"));
			rel++;
			if(!(obj.find(".list").is(":animated"))){
				if(rel==1){
					$list=obj.find(".list");
					$list.eq(1).animate({left:-rel*wid},500);
					//$list.eq(1).find(".list_box").css({"z-index":zIndex-1});
					//$list.eq(1).find(".list_box").eq(rel).animate({"z-index":zIndex+1},300);
					$list.eq(0).animate({left:(-len-rel)*wid},500,function(){
						var det=$list.eq(0).detach();
						obj.find(".list").after(det);
						obj.find(".list").eq(1).css("left",(len-rel)*wid);
					});
				}else{
					$list=obj.find(".list");
					$list.eq(0).animate({left:-rel*wid},500);
					$list.eq(1).animate({left:(len-rel)*wid},500);
					//$list.eq(0).find(".list_box").css({"z-index":zIndex-1});
					if(rel==len){
						rel=0;
					//	$list.eq(1).find(".list_box").eq(rel).animate({"z-index":zIndex+1},300);
					}else{
					//	$list.eq(0).find(".list_box").eq(rel).animate({"z-index":zIndex+1},300);
					}
				}

				$(".link_group a").removeClass("on").eq(rel).addClass("on");
				obj.attr("rel",rel);
			}
			
		});
		leftHandle.click(function(){
			var rel=parseInt(obj.attr("rel"));
			rel--;
			if(!(obj.find(".list").is(":animated"))){
				if(rel==-1){
					$list=obj.find(".list");
					$list.eq(0).animate({left:-(rel+len)*wid},500);
					$list.eq(1).animate({left:-(rel)*wid},500);
					//$list.find(".list_box").css({"z-index":zIndex-1});
					//$list.eq(0).find(".list_box").eq(len+rel).animate({"z-index":zIndex+1},300);
					rel=len-1;
				}else if(rel==0){
					var det=$list.eq(1).detach();
					obj.find(".list").before(det);
					obj.find(".list").eq(0).css("left",(-len-1)*wid);
					$list=obj.find(".list");
					$list.eq(0).animate({left:-len*wid},500);
					$list.eq(1).animate({left:-rel*wid},500);
					//$list.find(".list_box").css({"z-index":zIndex-1});
					//$list.eq(1).find(".list_box").eq(rel).animate({"z-index":zIndex+1},300);
				}else{
					$list=obj.find(".list");
					$list.eq(0).animate({left:-rel*wid},500);
					$list.eq(1).animate({left:(len-rel)*wid},500);
					//$list.find(".list_box").css({"z-index":zIndex-1});
					//$list.eq(0).find(".list_box").eq(rel).animate({"z-index":zIndex+1},300);
				}

				$(".link_group a").removeClass("on").eq(rel).addClass("on");
				obj.attr("rel",rel);
			}
		});
		
		//点击事件
		$("#jd_carsoul a").each(function(i){
			$(this).click(function(){
				$("#jd_carsoul a").removeClass("on").eq(i).addClass("on");
				var rel=i;
				if(!(obj.find(".list").is(":animated"))){
					if(rel==1){
						$list=obj.find(".list");
						$list.eq(1).animate({left:-rel*wid},500);
						$list.eq(0).animate({left:(-len-rel)*wid},500,function(){
							var det=$list.eq(0).detach();
							obj.find(".list").after(det);
							obj.find(".list").eq(1).css("left",(len-rel)*wid);
						});
					}else if(rel==0){
						$list=obj.find(".list");
						$list.eq(0).animate({left:-len*wid},500);
						$list.eq(1).animate({left:-rel*wid},500);
					}else{
					
						$list=obj.find(".list");
						$list.eq(0).animate({left:-rel*wid},500);
						$list.eq(1).animate({left:(len-rel)*wid},500);
						rel=rel==len?0:rel;
					}
				
					obj.attr("rel",rel);
				}
			});
		});
		$("#jd_carsoul a").eq(0).trigger("click");
	})();
	
	
	//右侧导航
	(function(){
		var $m_airNav=$("#m_airNav")
			,boxTop=$m_airNav.offset().top
			,boxcssTop=parseInt($m_airNav.css("top"))
			,boxWid=$m_airNav.width()
			,wid=$(window).width()
			,$m_a=$m_airNav.find("a")
			,title=$(".m_title").innerHeight();
			;
		//初始化
		if((wid-1200)/2>boxWid){
			$m_airNav.css("right",((wid-1200)/2-boxWid)/2);
		}else{
			$m_airNav.hide();
		}
		
		$m_a.click(function(){
			$m_a.removeClass("on");
			$(this).addClass("on");
			var id=$(this).attr("rel")
				,hei=$("#"+id).offset().top
			$(window).scrollTop(hei);	
		});
			
		$(window).scroll(function(){
			if($(window).scrollTop()>=(boxTop)){
					$m_airNav.css({position:"fixed",top:title});
			}else{
				$m_airNav.css({position:"absolute",top:boxcssTop});
			}
			
			for(var i=0,l=$m_a.length;i<l;i++){
				var id=$m_a.eq(i).attr("rel")
					,hei=$("#"+id).offset().top
					;
				if($(window).scrollTop()>=hei){
					$m_a.removeClass("on").eq(i).addClass("on");
				}else{
					break;
				}
			}
		});
		
		$m_airNav.find(".up").click(function(){
			var index=$m_airNav.find(".on").index();
			index=index==0?($m_a.length):index;
			$m_a.eq(index-1).trigger("click");
		});
		
		$m_airNav.find(".down").click(function(){
			var index=$m_airNav.find(".on").index();
			index=index==($m_a.length-1)?-1:index;
			$m_a.eq(index+1).trigger("click");
		});
	})();
});