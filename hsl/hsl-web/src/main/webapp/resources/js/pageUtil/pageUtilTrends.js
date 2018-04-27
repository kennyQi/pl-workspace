$(function(){
	page(1);
	$(".pageN1").css("background","#ff9900");
	$(document).on("click","#page #pageBtns a",function(){
		var that=$(this).attr("class");
		page($(this).html());
	})
	$(document).on("click","#page #up a",function(){//点击上一页
		var pageNo=parseInt($("#pageNo").val());
		if(pageNo==1){
			alert("已经是第一页了")
		}else{
			page(pageNo-1);
		}
	})
	$(document).on("click","#page #next a",function(){//点击下一页
		var pageNo=parseInt($("#pageNo").val());
		var pageCount=parseInt($("#pageCount").val());
		if(pageNo==pageCount){
			alert("已经是最后一页了")
		}else{
			page(pageNo+1);
		}
	})
	function page(pageNo){
		var totalCount,
		pageSize,
		pageCount;
		totalCount=parseInt($("#totalCount").val());//获取总条目
		pageSize=parseInt($("#pageSize").val());//获取每页大小
		pageCount=totalCount%pageSize==0?parseInt(totalCount/pageSize):(parseInt(totalCount/pageSize))+1;
		var showPage=parseInt(pageNo)-2>0?parseInt(pageNo)-2:1;//移动大小
		var shift=parseInt(pageNo)+3<=5?5:parseInt(pageNo)+2;
		var text="<span id='up'><a href='#top'><</a></span>&nbsp;" +
		"<span id='pageBtns'></span>" +
		"<span id='next'><a href='#top'>></a></sapn>";
		var pageNumber="";
		if(shift>pageCount){
			showPage=parseInt(pageCount)-4;
			shift=parseInt(pageCount);
		}
		for(var i=showPage;i<=shift;i++){
			pageNumber+='<a class="pageN'+i+'" href="#top">'+i+'</a>&nbsp;';
		}
		$("#pageNo").val(pageNo);
		$("#pageCount").val(pageCount);
		$("#pageBtns").empty();
		$("#page").empty();
		$("#page").append(text);
		$("#pageBtns").append(pageNumber);
		$(".pageN"+pageNo+"").css("background","#ff9900");//设置背景颜色
		
		//设置一个方法,每次翻页调用这个方法
	}
})
