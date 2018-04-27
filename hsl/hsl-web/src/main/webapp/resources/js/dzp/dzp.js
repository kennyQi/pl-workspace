$(function(){
	
	//浏览记录跟随
	/*var g_histTop=$(".g_hist").offset().top;
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
	
	//关闭浏览记录
	$("#closeHist").click(function(){
		$(this).closest(".g_hist").hide();
	});*/

//列表显示更多
    $(document).on("click",".m_list_box .tb .more",function(){
        var that=$(this);
        if(!(that.attr("class").indexOf("on")!=-1)){
            that.closest(".m_list_box").find("tr").show();
            that.addClass("more_on").html("收起所有门票类型"+$(this).attr("rel"));
        }else{
            var $tr=that.closest(".m_list_box").find("tr:not('.last')");
            for(var q=4;q<$tr.length;q++){
                $tr.eq(q).hide();
            }
            that.removeClass("more_on").html("展开所有门票类型"+$(this).attr("rel"));
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
			$(this).find("tr:last").css({height:"48px","line-height":"48px"}).find("td").html('<span class="more Bg curp" rel="'+(parseInt($tr.length)-1)+'">展开所有门票类型('+(parseInt($tr.length)-1)+')</span>');
		}
	});
	
	$(".m_list_box .tb tr:not('.last')").hover(function(){
		$(this).addClass("on");
	},function(){
		$(this).removeClass("on");
	});
	

});
//搜索条件事件
$(function(){
	
	//点击后筛选条件列显示小框
	function addBox(val,id){
			$("a[rel='"+id+"']").remove();
		if(val!="全部"&&val!=""){
			var html='<a href="javascript:;" class="choose" rel="'+id+'">'+val+'<i class="icon">&nbsp;</i></a>';
			$("#condition .tt").after(html);
		}
		if($("#condition a").length>0){
				$("#condition").show();
		}else{
				$("#condition").hide();
		}
	}

    //更多省份
    $(".left_choose .more").click(function(){
        if($(this).find("a").html()=="更多"){
            $(this).find("a").html("收起").closest(".left_all_box1").height("68");
        }else{
            $(this).find("a").html("更多").closest(".left_all_box1").height("34");
        }

    });
	
	
	//搜索条件选择
	
	$(".left_all dl dd").not(".more").find("a").click(function(){
		var that=$(this);
		that.closest("dl").find("dd").removeClass("on");
		$(this).closest("dd").addClass("on");
		var val=$(this).text()
			,id=that.closest("dl").attr("id")
			;
            addBox(val,id);

			val=null;
            id=null;
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
	$(document).on("click","#condition a",function(){
		var id=$(this).attr("rel");
		if(id=="location"){
			$(".location_more").find(".list").removeClass("list_on");
		}else{
			$("#"+id).find("dd a").eq(0).trigger("click");
		}
		$(this).remove();
		if($("#condition a").length==0){
			$("#condition").hide();
		}
	});

    $("#condition .clear").click(function(){
        $(".left_choose").find("dd a:eq(0)").trigger("click");
    });

	
	$("#brand dd a").click(function(){
		$(".m_moreBrand .che").removeClass("che_on");
	}); 
});

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
    },5000);

    /*轮播*/
});

