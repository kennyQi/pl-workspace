$(function(){
	var message = $("#message").val();
	if(message != "1"){
		showMsg(message);
	}
	var contextPath = $("#contextPath").val();
	//页面加载完成,拉取数据
	loadOrderList(0,contextPath);
	
	//点击加载更多
	$("#tips").click(function(){
		var pageNum = 0;
		
		$(".order_list").each(function(i,j){
			pageNum++;
		})
		if(pageNum == 0){
			return false;
		}
		
		var canPull = $("#canPull").val();
		if(canPull == 1){
			return false;
		}
		
		$("#canPull").val(1);
		if($("#tips").data("ajaxIng")!="false"){
			$("#tips").data("ajaxIng","false");
			loadOrderList(pageNum,contextPath);
		}
		
	});
	
});

//下拉加载更多 ajax 请求
function loadOrderList(num,contextPath){
	$.ajax({
		url:contextPath+"/lineSalesPlan/loadMoreOrderList",
		data:{"queryNum":num},
		type:"post",
		success:function(data){
			var dats = eval('('+data+')');
			$("#pageNum").val(0);
			if(parseInt(dats.length)!= 0){
				var oList = $("#order_list");
				for(var o in dats){
 					if(dats[o] != null){
 						var str = dats[o].split(",");
 						var htm = "<div class='order_list'><a href="+contextPath+"/lineSalesPlan/orderDetail?dealerOrderNo="+str[0]+"><div class=''><div class='order_detail'><span class='name'><i class='iconfont'>&#xe63b;</i>"+str[1]+"</span><span class='date'><i class='iconfont'>&#xe676;</i>"+str[2]+"</span></div><div class='price'><label>"+str[3]+"</label></div><i class='iconfont right_bar'>&#xe6f1;</i></div></a></div>";
 						oList.append(htm);
 					}
 				}
				if(dats.length < 20){
					$("#pageNum").val(0);
					$("#canPull").val(1);
					$("#tips").html("<i></i>祝亲旅行愉快...");
					$("#tips").data("ajaxIng","false");
				}else if(dats.length == 20){
					$("#pageNum").val(dats.length);
					$("#canPull").val(0);
					$("#tips").html("<i></i>点击加载更多线路活动订单...");
					$("#tips").data("ajaxIng","true");
				}
			}else{
				if(num == 0){
					$(".noOrder").show();
					$("#tips").hide();
				}else{
					$("#pageNum").val(0);
					$("#tips").html("<i></i>祝亲旅行愉快...");
					$("#tips").data("ajaxIng","false");
				}
			}
			$("#canPull").val(0);
		}
	});
}
