$(function(){
	/*城市选择*/
	var test=new Vcity.CitySelector({input:'citySelect_from'});
	
	//时间选择关闭地区选择
	$("[name='layTime']").click(function(){
		$("#cityBox").addClass("hide");
	});
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
	})
	
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
	
	//关闭浏览记录
	$("#closeHist").click(function(){
		$(this).closest(".g_hist").hide();
	});
	
	//酒店名称和商圈位置输入
	$("[name='place']").focus(function(){
		var rel=$(this).attr("old");
		if(rel==$(this).val()){
			$(this).val("");
		}
	});
	
	$("[name='place']").blur(function(){
		var rel=$(this).attr("old");
		if($(this).val()==""){
			$(this).val(rel);
		}
	});
	
	//酒店列表显示更多
	$(document).on("click",".m_list_box .tb .more",function(){
		var that=$(this);
		if(!(that.attr("class").indexOf("on")!=-1)){
			that.closest(".m_list_box").find("tr").show();
			that.addClass("more_on").html("收起全部房型"+$(this).attr("rel"));
		}else{
			var $tr=that.closest(".m_list_box").find("tr:not('.last')");
			for(var q=4;q<$tr.length;q++){
					$tr.eq(q).hide();
			}
			that.removeClass("more_on").html("展开全部房型"+$(this).attr("rel"));
		}
	});
	//列表显示
	var m_list_boxNum=3;
	$(".m_list_box .tb").each(function(){
		var $tr=$(this).find("tr:not('.last')");
		if($tr.length>(m_list_boxNum+2)){
			for(var i=(m_list_boxNum+1);i<$tr.length;i++){
				$tr.eq(i).hide();
			}
			$(this).find("tr:last td").html('<span class="more Bg curp" rel="'+(parseInt($tr.length)-1)+'">展开全部房型('+(parseInt($tr.length)-1)+')</span>');
		}
	});
	
	$(".m_list_box .tb tr:not('.last')").hover(function(){
		$(this).addClass("on");
	},function(){
		$(this).removeClass("on");
	});
	

});
//搜索条件事件
function addevent(){
	//显示位置更多
	
	$("#location dd:not('.nor') a").click(function(){
		$("#location dd.nor").removeClass("on");
		if($(this).parent().attr("class")&&$(this).parent().attr("class").indexOf("choose")!=-1){
			$(".location_more").hide();
			$("#location dd").removeClass("choose");
		}else{
			var rel=$(this).parent().attr("rel");//获取目标的name属性
			$("#location dd").removeClass("choose");
			$(this).parent().addClass("choose");
			$(".location_more").hide();
			$(".location_more[name='"+rel+"']").show();
		}
	});
	$("#location dd.nor").click(function(){
			$(".location_more").hide();
			$("#location dd").removeClass("choose");
			$(this).addClass("on");
			addBox("不限","location");
			$(".location_more").find(".list").removeClass("list_on");
	});
	
	
	$("#location dd:not('.nor') a").eq(0).trigger("click");
	//选择具体位置
	$(".location_more").each(function(i){
		var that=$(this);
		that.find(".list").click(function(){
			$(".location_more").find(".list").removeClass("list_on");
			$(this).addClass("list_on");
			var val=$(this).html()
				,id="location"
				;
			addBox(val,id);
			val=null,id=null;
			/**********新加入给隐藏域赋值*************/
			$("#sqrido").val($(this).html());
			send();
		});
		/**********新加入*************/
		var hotelGeo="";//商圈or行政区
		$("#location .location_more span").each(function(){
			if($(this).hasClass("list_on")){
				hotelGeo=$(this).html();
			}
		})
		if(hotelGeo!=""){
			$("#hotelNames").val(hotelGeo);
			//清空酒店名称查询和商圈查询
			$("#hotelName").val("");
			$("#hotelName").attr("placeholder","酒店名称");
			$("#hotelLoaction").val("");
			$("#hotelLoaction").attr("placeholder","商圈位置");
		}
	});
	
	//商圈更多
	$("[name='locationMore']").click(function(){
		$(this).hide().closest(".location_more").css("height","auto");
		$(this).parent().find("[name='locationClose']").show();
	});
	$("[name='locationClose']").click(function(){
		$(this).parent().find("[name='locationMore']").show();
		$(this).hide().closest(".location_more").css("height","32px").hide();
		$("#location dd:not('.nor')").removeClass("choose");
	});
	
	
	
	
	
	//条件选择
	
	$(".left_all dl").not("#location").find("dd a").click(function(){
		var that=$(this);
		that.closest("dl").find("dd").removeClass("on");
		$(this).closest("dd").addClass("on");
		var val=$(this).text()
			,id=that.closest("dl").attr("id")
			;
		addBox(val,id);
			val=null,id=null;
		send();
	});
	
	//自定义价格
	$("#priceSure").click(function(){
		var minV=$("#priceMin").val()==""?0:parseInt($("#priceMin").val())
			,maxV=$("#priceMax").val()==""?minV:parseInt($("#priceMax").val())
			;
		if(minV>maxV||maxV==0){
			$("#priceMax").css("border-color","red");
			setTimeout(function(){
				$("#priceMax").css("border-color","#dedede");
			},1000);
		}else{
			addBox(minV+"元-"+maxV+"元","price");
			$("#price dd").removeClass("on");
		}
		$(this).hide();
		//定义价格时给标识符赋值,标识现在的价格区间是我们自定义的
		send();
	});
	
	$("#priceMin").focus(function(){
			$("#priceSure").show();
		
	});
	
	$("#priceMax").focus(function(){
		$("#priceSure").show();
	});
	
	$("#price dd a").click(function(){
		$("#priceMin").add("#priceMax").val("");
	}); 
	//筛选条件
	$(document).on("mouseover","#condition a",function(){
		$(this).addClass("choose_on");
	});
	$(document).on("mouseout","#condition a",function(){
		$(this).removeClass("choose_on");
	});
	$(document).on("click","#condition a",function(){//删除筛选条件
		var id=$(this).attr("rel");
		if(id=="location"){
			if($(this).attr("rel")=="hotalstar"){//酒店星级
				$("#starRate").val("");
			}else if($(this).attr("rel")=="price"){
				$("#lowRates").val(0);
				$("#highRate").val(9999);
			}else if($(this).attr("rel")=="location"){
				$("#sqrido").val("");
			}
			$(".location_more").find(".list").removeClass("list_on");
		}else{
			$("#"+id).find("dd a").eq(0).trigger("click");
		}
		$(this).remove();
		if($("#condition a").length==0){
			$("#condition").hide();
		}
		send();
	});
	
	//更多品牌酒店
	$("#moreBrand").click(function(){
		$(".m_moreBrand").toggle();
	});
	$(".m_moreBrand .close").click(function(){
		$(this).closest(".m_moreBrand").hide();
	});
	$(".m_moreBrand .che").click(function(){
		$(".m_moreBrand .che").removeClass("che_on");
		$(this).addClass("che_on");
	});
	
	$(".m_moreBrand .sure").click(function(){
		$(this).closest(".m_moreBrand").hide();
		var val=$(".m_moreBrand .che_on").text();
		$("#brand dd").removeClass("on");
		addBox(val,"brand");
	});
	
	
	$("#brand dd a").click(function(){
		$(".m_moreBrand .che").removeClass("che_on");
	}); 
}

//点击后筛选条件列显示小框
function addBox(val,id){
		$("a[rel='"+id+"']").remove();
	if(val!="不限"&&val!=""){
		var html='<a href="javascript:;" class="choose" rel="'+id+'">'+val+'<i class="icon">&nbsp;</i></a>';
		$("#condition").append(html);
	}
	if($("#condition a").length>0){
			$("#condition").show();
	}else{
			$("#condition").hide();
	}
}
