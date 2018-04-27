

$(function(){
	//在ie7,8下trim()无法使用,重新定义trim
	String.prototype.trim = function () {
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
	}
	var carouselUp=$("#carousel .up")
	,carouselDown=$("#carousel .down")
	,li=$("#carousel .list li")
	,showImg=$("#show_img")
	,stopCarou=true//停止轮播
	,carouselLen=li.length
	;

	//轮播初始化
	carousel(li,0,showImg);

	carouselUp.hover(function(){
		$(this).addClass("clickOn");
	},function(){
		$(this).removeClass("clickOn");
	});
	carouselDown.hover(function(){
		$(this).addClass("clickOn");
	},function(){
		$(this).removeClass("clickOn");
	});

	//轮播图点击事件
	carouselUp.click(function(){
		var i=$("#carousel .current").index();
		i=i==0?carouselLen-1:i-1;
		carousel(li,i,showImg);
	});
	carouselDown.click(function(){
		var i=$("#carousel .current").index();
		i=i==carouselLen-1?0:i+1;
		carousel(li,i,showImg);
	});
	//点击图片
	li.click(function(){
		var i=$(this).index();
		carousel(li,i,showImg);
	});
	showImg.hover(function(){
		stopCarou=false;
	},function(){
		stopCarou=true;
	});

	//定时轮播
	setInterval(function(){
		if(stopCarou==true)
		{var i=$("#carousel .current").index();
		i=i==carouselLen-1?0:i+1;
		carousel(li,i,showImg);
		}
	},5000);

	//出发日期选择
	$("#starSelect").click(function(){
		var $span=$(this).find(".select_option span");
		if($span.length>6){
			$(this).find(".select_option").css({"overflow-y":"scroll","height":6*$span.height()});
		}
		$(this).find(".select_option").toggle();
	});
	$("#starSelect span").hover(function(){
		$(this).addClass("on");
	},function(){
		$(this).removeClass("on");
	});

	$("#starSelect span").click(function(){
		var money=parseFloat($(this).attr("money").replace(",",""));
		var children=parseFloat($(this).attr("children").replace(",",""));
		var adultNum=parseFloat($("#adult").html());
		var chidrenNum=parseFloat($("#chidren").html());
		var name=$(this).attr("date").replace(/-/g,"");
		$(this).closest("#starSelect").find("label").html($(this).html());
		$(this).siblings().attr("rel","");
		$(this).attr("rel","on");
		var moneys=money*adultNum+chidrenNum*children;
		var moneys=moneys.toFixed(2);
		$("#trips_money").html('<i class="h3">￥</i>'+(moneys));
		var obj=$("td[name='"+name+"']");
		obj.closest("tbody").find("td").removeClass("xdsoft_current");
		obj.addClass("xdsoft_current");
	});

	//人员的加减
	$(".chooce_num .add").click(function(){
		var $num=$(this).closest(".chooce_num").find(".num")
		,minVal=parseFloat($num.attr("min"))
		,totalNum=0
		;
		if(!($(this).attr("class").indexOf("on")!=-1)){
			if(minVal==parseFloat($num.html())){
				$(this).closest(".chooce_num").find(".minus").removeClass("on");
			}
			$num.html(parseFloat($num.html())+1);
			if($("#starSelect span[rel='on']").length>0){
				var money=parseFloat($("#starSelect span[rel='on']").attr("money").replace(",",""));
				var children=parseFloat($("#starSelect span[rel='on']").attr("children").replace(",",""));
				var adultNum=parseFloat($("#adult").html());
				var chidrenNum=parseFloat($("#chidren").html());
				var moneys=money*adultNum+chidrenNum*children;
				var moneys=moneys.toFixed(2);
				$("#trips_money").html('<i class="h3">￥</i>'+(moneys));
				totalNum=$("#starSelect span[rel='on']").attr("num");
				if(totalNum==(adultNum+chidrenNum)){
					$(".chooce_num .add").addClass("on");
				}
			}
		}
	});

	$(".chooce_num .minus").click(function(){
		var $num=$(this).closest(".chooce_num").find(".num")
		,minVal=parseFloat($num.attr("min"))
		,totalNum=0
		;

		if(!($(this).attr("class").indexOf("on")!=-1)){
			$num.html(parseFloat($num.html())-1);
			if(parseFloat($num.html())==minVal){
				$(this).addClass("on");
			}
		}
		if($("#starSelect span[rel='on']").length>0){
			var money=parseFloat($("#starSelect span[rel='on']").attr("money").replace(",",""));
			var children=parseFloat($("#starSelect span[rel='on']").attr("children").replace(",",""));
			var adultNum=parseFloat($("#adult").html());
			var chidrenNum=parseFloat($("#chidren").html());
			$("#trips_money").html('<i class="h3">￥</i>'+(money*adultNum+chidrenNum*children).toFixed(2));
			totalNum=$("#starSelect span[rel='on']").attr("num");
			if(totalNum>(adultNum+chidrenNum)){
				$(".chooce_num .add").removeClass("on");
			}
		}
	});

	//人数直接输入


	//订单轮播
	var order_new_moveHeight=$(".order_new_move li").eq(0).height()
	,order_new_moveList=$(".order_new_move li").length
	,$order_carous=$("#order_carous")
	,order_carousHtml=$order_carous.find(".order_new_move").clone()
	,order_boxHeight=$(".order_new_move").height()
	,F_oldHeight=0
	,S_oldHeight=0
	;
	//初始状态设置
	$order_carous.append(order_carousHtml).height(order_new_moveHeight);
	$order_carous.find(".order_new_move").eq(1).css("top",order_boxHeight);
	setInterval(function(){
		F_oldHeight=parseInt($(".order_new_move").eq(0).css("top"));
		S_oldHeight=parseInt($(".order_new_move").eq(1).css("top"));
		$(".order_new_move").eq(0).animate({top:F_oldHeight-order_new_moveHeight},300);
		$(".order_new_move").eq(1).animate({top:S_oldHeight-order_new_moveHeight},300,function(){

			order_new_moveHeight=$(".order_new_move").eq(0).find("li").eq(parseInt($order_carous.attr("rel"))+1).height()|| $(".order_new_move li").eq(0).height();
			if(parseInt($order_carous.attr("rel"))==(order_new_moveList-1)){
				order_new_moveHeight=$(".order_new_move li").eq(0).height();
				$order_carous.append($(".order_new_move").eq(0).css("top",order_boxHeight).detach());
				$order_carous.attr("rel",0).height(order_new_moveHeight);
			}else{
				$order_carous.attr("rel",parseInt($order_carous.attr("rel"))+1).height(order_new_moveHeight);
			}

		});
	},5000);



	//中部导航
	var subNavhei=$("#subNav").height();
	var $subNavA=$("#subNav .nav a");
	var oldsubNavAOn=0;
	$subNavA.each(function(i){
		$(this).click(function(){
			var id=$(this).attr("rel")
			,hei=$("#"+id).offset().top
			;
			$(window).scrollTop(hei-subNavhei);

			$subNavA.removeClass("on").eq(i).addClass("on");
			$("#subNav_bg").stop().animate({left:i*124},300);
			oldsubNavAOn=i;

			console.log(hei);
		});

		$(this).hover(function(){
			$subNavA.removeClass("on").eq(i).addClass("on");
			$("#subNav_bg").stop().animate({left:i*124},300);
		},function(){
			$subNavA.removeClass("on").eq(oldsubNavAOn).addClass("on");
			$("#subNav_bg").stop().animate({left:oldsubNavAOn*124},300);

		});
	});


	//中部导航浏览记录跟随
	var subNavTop=$("#subNav").offset().top;

	//详细行程导航浏览记录跟随
	var dayAnchorTop=$("#day_anchor").offset().top
	,dayAnchorOldTop=parseFloat($("#day_anchor").css("top"))
	,dayAnchorHeight=parseFloat($("#day_anchor").height());

	var dayAnchorLeft=$("#day_anchor").offset().left;
	var dayAnchor_Left=parseFloat($("#day_anchor").css("left"));
	$(window).scroll(function(){
		//回顶部按钮
		if($(window).scrollTop()>subNavTop){
			$("#subNav").css({"top":0,"position":"fixed"});
			//回顶部按钮
			$(".g_totop").show();
		}else{
			$("#subNav").css({"top":979,"position":"absolute"});
			//回顶部按钮
			$(".g_totop").hide();
		}
		for(var i=0,l=$subNavA.length;i<l;i++){
			var id=$subNavA.eq(i).attr("rel")
			,hei=$("#"+id).offset().top
			;
			if($(window).scrollTop()>=(hei-subNavhei-5)){
				$subNavA.removeClass("on").eq(i).addClass("on");
				$("#subNav_bg").stop().animate({left:i*124},300);
				oldsubNavAOn=i;
			}else{
				break;
			}
		}
		if($(window).scrollTop()>(dayAnchorTop-(2*subNavhei))&&$(window).scrollTop()<(dayAnchorTop-(2*subNavhei)+$("#anchor_detail").height()-dayAnchorHeight)){
			$("#day_anchor").css({"position":"fixed","left":dayAnchorLeft,"top":(dayAnchorHeight)});
			//}else if($(window).scrollTop()>(dayAnchorTop-(2*subNavhei)+$("#anchor_detail").height()-dayAnchorHeight)){
//			$("#day_anchor").css({"position":"absolute","top",dayAnchorOldTop});
		}else{
			$("#day_anchor").css({"position":"absolute","top":dayAnchorOldTop,"left":dayAnchor_Left});
		}

		for(var i=0,l=$("#day_anchor a").length;i<l;i++){
			var id=$("#day_anchor a").eq(i).attr("rel")
			,hei=$("#"+id).offset().top
			;
			if($(window).scrollTop()>=(hei-subNavhei-40)){
				$("#day_anchor a").eq(i).addClass("on");
			}else{
				$("#day_anchor a").eq(i).removeClass("on");
			}
			$("#day_anchor a").eq(0).addClass("on");
		}
	});


//	$(window).scroll(function(){
//	if($(window).scrollTop()>(dayAnchorTop-(2*subNavhei))&&$(window).scrollTop()<(dayAnchorTop-(2*subNavhei)+$("#anchor_detail").height()-dayAnchorHeight)){
//	$("#day_anchor").css({"position":"fixed","left":dayAnchorLeft,"top":(dayAnchorHeight)});
//	//}else if($(window).scrollTop()>(dayAnchorTop-(2*subNavhei)+$("#anchor_detail").height()-dayAnchorHeight)){
////	$("#day_anchor").css({"position":"absolute","top",dayAnchorOldTop});
//	}else{
//	$("#day_anchor").css({"position":"absolute","top":dayAnchorOldTop,"left":dayAnchor_Left});
//	}

//	for(var i=0,l=$("#day_anchor a").length;i<l;i++){
//	var id=$("#day_anchor a").eq(i).attr("rel")
//	,hei=$("#"+id).offset().top
//	;
//	if($(window).scrollTop()>=(hei-subNavhei-40)){
//	$("#day_anchor a").eq(i).addClass("on");
//	}else{
//	$("#day_anchor a").eq(i).removeClass("on");
//	}
//	$("#day_anchor a").eq(0).addClass("on");
//	}
//	});
	//详细行程
	$("#day_anchor a").each(function(i){
		$(this).click(function(){
			var id=$(this).attr("rel")
			,hei=$("#"+id).offset().top
			;
			if(i==0){
				$(window).scrollTop(hei-subNavhei-40-10);
			}else{
				$(window).scrollTop(hei-subNavhei-10);
			}
			$(this).addClass("on");
			//for(var q=(i+1),l=$("#day_anchor a").length;q<l;q++){
//			$("#day_anchor a").eq(q).removeClass("on");
//			}
		});
	});
//	$(window).scroll(function(){
//	for(var i=0,l=$subNavA.length;i<l;i++){
//	var id=$subNavA.eq(i).attr("rel")
//	,hei=$("#"+id).offset().top
//	;
//	if($(window).scrollTop()>=(hei-subNavhei)){
//	$subNavA.removeClass("on").eq(i).addClass("on");
//	$("#subNav_bg").stop().animate({left:i*124},300);
//	}else{
//	break;
//	}
//	}
//	});


	//详情内部图片轮播
	//初始状态设置

	var $m_detail_carousl=$(".m_detail_carousl")
	,$m_detail_carousl_list=3
	;

	$m_detail_carousl.each(function(i){
		var that=$(this)
		,imglist=that.find(".img_list img")
		,len=imglist.length
		,wid=imglist.width()
		,padR=parseFloat(imglist.css("margin-right"))
		;
		that.find(".img_list").width(len*(wid+padR));
		if((len*(wid+padR))<=($m_detail_carousl_list*(wid+padR))){
			that.find(".left,.right").hide();
		}
		//轮播往右
		that.find(".right").click(function(){
			var oldLeft=parseFloat(that.find(".img_list").css("left"));
			if(!that.find(".img_list").is(":animated")){
				that.find(".img_list").stop().animate({left:oldLeft-($m_detail_carousl_list*(wid+padR))},500,function(){
					if(((len*(wid+padR))+parseFloat(that.find(".img_list").css("left")))<=($m_detail_carousl_list*(wid+padR))){
						that.find(".right").hide();
					}
					that.find(".left").show();
				});
			}
		});
		//轮播往左
		that.find(".left").click(function(){
			var oldLeft=parseFloat(that.find(".img_list").css("left"));
			if(!that.find(".img_list").is(":animated")){
				that.find(".img_list").stop().animate({left:oldLeft+($m_detail_carousl_list*(wid+padR))},500,function(){
					if(parseFloat(that.find(".img_list").css("left"))==0){
						that.find(".left").hide();
					}
					that.find(".right").show();
				});
			}
		});

		//图片浮动
		imglist.hover(function(){
			var html='<div class="air_img_box pa"></div>';
			$("body").append(html);
			var $airImgBox=$(".air_img_box")
			$airImgBox.html($(this).clone());
			var wid=$airImgBox.find("img").width()
			,docWid=$(document).width()
			;
			$(document).mousemove(function (e) {  
				var x 
				,y
				;
				x=e.pageX;  
				y=e.pageY; 
				$airImgBox.css("top",y+5);
				if(docWid<(x+wid+30)){
					$airImgBox.css("left",(x-5-wid));
				}else{
					$airImgBox.css("left",x+5);
				}

			})  
		},function(){
			$(".air_img_box").remove();
		});
	});


	//起价说明
	$("#price_explain").hover(function(){
		$(this).find(".price_explain").show();
	},function(){
		$("#price_explain").find(".price_explain").hide();

	});


});

////////////////////////日历
$(function(){

	$('#datetimepicker3').datetimepicker({
		inline:true
		,lang:"ch"

	});

	var $starSelect=$("#starSelect");
	var datetimepicker_date=($starSelect.find("span").eq(0).attr("date")||"0").replace(/-/g,"");
	var dateTime=datetimepicker_date.slice(0,4)+"-"+datetimepicker_date.slice(4,6)+"-"+datetimepicker_date.slice(6,8);
	$('#datetimepicker3').datetimepicker({value:datetimepicker_date.slice(0,4)+"/"+datetimepicker_date.slice(4,6)+"/"+datetimepicker_date.slice(6,8)+' 12:00'});
	$starSelect.find("span[date='"+dateTime+"']").trigger("click");
	$starSelect.find(".select_option").hide();


});

//$(document).on("click","#m_data td",function(){
//var $starSelect=$("#starSelect");
//if($(this).html().indexOf("span")!=-1){
//var d=$(this).attr("name");
//$starSelect.find("span [date='"+d+"']").trigger("click");
//$starSelect.find(".select_option").hide();
//}
//});


function carousel(obj,i,dir){
	if(i>4)
	{
		if(!obj.parent().is(":animated"))
			obj.parent().animate({top:-(obj.height()+2)*(i-4)+18});
	}else{
		if(!obj.parent().is(":animated"))
			obj.parent().animate({top:18});
	}
	obj.removeClass("current").eq(i).addClass("current");
	var src=obj.eq(i).find("img").attr("src");
	dir.find("img").attr("src",src);
}

//为日期添加价格人数
function addDate(){
	var $starSelect=$("#starSelect");
	var datetimepicker_date=($starSelect.find("span").eq(0).attr("date")||"0").replace(/-/g,"");

	$starSelect.find("span").each(function(i,e){
		var time=$(this).attr("date").replace("-","").replace(/-/g,"")
		,num=$(this).attr("num")
		,money=$(this).attr("money")
		;


		var html='<span class="data_all"><label class="data_per"><'+num+'人</label>￥'+money+'</span>';
		$("[name='"+time+"']").find("div").append(html);
	});
}
//点击事件

function dateClick(obj){
	var $starSelect=$("#starSelect");
	var date=obj.attr("name");
	var dateTime=date.slice(0,4)+"-"+date.slice(4,6)+"-"+date.slice(6,8);
	//人员重置
	$("#adult").html(1);
	$("#chidren").html(0);
	$(".chooce_num .minus").addClass("on");
	$(".chooce_num .add").removeClass("on");
	$starSelect.find("span[date='"+dateTime+"']").trigger("click");
	$starSelect.find(".select_option").hide();
}

