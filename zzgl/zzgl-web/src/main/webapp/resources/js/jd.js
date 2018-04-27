$(function(){
	

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
	var searchForm=$("#searchform");
	//点击后筛选条件列显示小框
	

    //更多省份
    $(".left_choose .more").click(function(){
        if($(this).find("a").html()=="更多"){
            $(this).find("a").html("收起").closest(".left_all_box1").height("68");
        }else{
            $(this).find("a").html("更多").closest(".left_all_box1").height("34");
        }

    });
	
    if($('#city').val()!="0"){
    	addBox($('#city').val(),"search_price");
    }
	
	//搜索条件选择
	
	$(document).on("click",".left_all dl dd:not(.more) a",function(){
		//if($(this).parent().hasClass("on"))return;
		var that=$(this);
		that.closest("dl").find("dd").removeClass("on");
		$(this).closest("dd").addClass("on");
		var val=$(this).text()
			,id=that.closest("dl").attr("id")
			,rel=that.closest("dl").attr("rel")
			;
		if(id=="search_hotalstar"){
			$("#mprice").val($(this).closest("dd").attr("mrel"));
			$("#lprice").val($(this).closest("dd").attr("lrel"));
		}else if(id=="search_province" || id=="search_price"){
			$("#"+rel).val($(this).closest("dd").attr("rel"));
			rel=rel.replace("Id","");
			$("#"+rel).val($(this).closest("dd").find("a").html());
			if(id=="search_province"){
				$("#cityId").val(0);
				$("#city").val(0);
			}
		}else{
			$("#"+rel).val($(this).closest("dd").attr("rel"));
		}
            addBox(val,id);
            //表单提交

        	searchForm.submit();
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

	
	$("#search_brand dd a").click(function(){
		$(".m_moreBrand .che").removeClass("che_on");
	}); 
	
	
	//排序
	
	$("#desc .cmd").click(function(){
		if($(this).index()==0){
			$("#orderBy").val(0);
		}else{
			if($(this).attr("class").indexOf("cmdon")!=-1){
				$("#orderBy").val(2);
			}else{
				$("#orderBy").val(1);
			}
		}
		$("#searchform").submit();
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


//页面加载
$(function(){
	//上一页和下一页以及表单的提交方法
		//设置表单提交的按钮监听
		$("#sendForm").on("click",function(){
			$("#pageNum").val(1);
			$("#searchform").submit();
		});
		$("#pre").on("click",function(){
			var currentPageNo = parseInt($("#pageNum").val());
			if(currentPageNo>1){
				$("#pageNum").val(currentPageNo-1);
				$("#searchform").submit();
			}else{
				alert("已经是第一页了");
			}
		});
		
		$("#next").on("click",function(){
			var currentPageNo = parseInt($("#pageNum").val());
			var total = parseInt($("#totalPage").val());
		
			if(currentPageNo>=total){
				alert("已经是最后一页了");
			}else{
				$("#pageNum").val(currentPageNo+1);
				$("#searchform").submit();
			}
		});
		$("#searchform").on("submit",function(){
			//表单提交前的处理
			var comb=/^[a-zA-Z0-9\u4e00-\u9fa5]{0,100}$/;
			if(!comb.test($("#scontent").val())){
				alert("景点名字不正确");
				return false;
			}
			return true;
		});
		
		
		
		
	
	if($("#provinceId").val()!=0){
		searchCity($("#provinceId").val());
	}
	
	//筛选条件框
	var searchBoxProvince=$("#provinceId").val()
		,searchBoxCity=$("#cityId").val()	
		,searchBoxGrade=$("#grade").val()	
		,searchBoxPrice=$("#mprice").val()	
		;

	if(searchBoxGrade!="0"){
		addBox($("#search_brand").find("dd[rel='"+searchBoxGrade+"'] a").html(),"search_brand");
	}

	if(searchBoxPrice!="0"&&searchBoxPrice!=""){
		addBox($("#search_hotalstar").find("dd[mrel='"+searchBoxPrice+"'] a").html(),"search_hotalstar");
	}

	if(searchBoxProvince!="0"){
		
		addBox($("#search_province").find("dd[rel='"+searchBoxProvince+"'] a").html(),"search_province");
	}
	
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
	
});

//根据省份id去查询相应的城市
function searchCity(pId){
	$.ajax({
	       type: "POST",
	       url: "../comRegister/searchCity?province="+pId,
	       contentType: "application/x-www-form-urlencoded; charset=utf-8",
	       dataType:"json",
	       success: function(data) {
	    	   if (data.length > 0) {  
		            var layer = "";  

		            layer+='<dt class="h3 fl">市<i>订单</i>级:</dt><dd class="nor on" rel="0"><a href="javascript:">全部</a></dd>';
		            $.each(data, function (idx, item) {  
		            	layer +="<dd rel='"+item.code +"'><a href='javascript:' >"+ item.name + "</a></dd>"
		            });
		            
					$("#search_price").html(layer).parent().show();
		        }
	    	   //查询出城市之后 初始化选中的城市
	    	   var cId=$("#cityId").val();
	    	    if(cId>0){
	    	    	$("#search_price dd").removeClass("on");
	    	    	/*if(!($("[rel='"+cId+"']").length>0)){
	    	    		$("#cityId").val(0);
	    	    		cId=0;
	    	    	}*/
	    	    	$("#search_price [rel='"+cId+"']").addClass("on");
    	    		addBox($("#search_price").find("dd[rel='"+cId+"'] a").html(),"search_price");
	    	    }else{
	    	    	$("#search_price dd").eq(0).addClass("on");
	    	    }

	       }
	});
}

function addBox(val,id){

	var searchForm=$("#searchform");
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




