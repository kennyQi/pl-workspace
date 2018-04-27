var imageAdd = {};
imageAdd.imageUpload = function(me) {
	$("#productIDInput").val($("#productID").val());
	$("#productIDInput1").val($("#productID").val());
	$("#myform").submit();
}

/*//左侧菜单
function leftClick(el){
	alert(el.html());
}*/

//分页
function jumpPage(pageNum){
	$("#pageNum").attr("value",pageNum);
	$("#listform").submit();
}

//排序
function sortAndJumpPage(){
	var sort=$("#sort").val();
	if(sort=="h2l"){
		$("#sort").attr("value","l2h");
	}else {
		$("#sort").attr("value","h2l");
	}
	$("#listform").submit();
}

//提交
function submitForm(){
	$("#submitform").submit();
}

//提交
function batchSubmit(){
	$("#productIDInput").val($("#productID").val());
	$("#productIDInput1").val($("#productID").val());
	$("#batchform").submit();
}

function schange(){
	$("#productIDInput").val($("#productID").val());
	$("#productIDInput1").val($("#productID").val());
}

$(function(){
	
	
	var a=$(".table-content-title").find(".time");
	var b=$(".table-content-title").find(".amount");
	a.click(function(){
		$(this).addClass("active");
		b.removeClass("active");
		
		$(this).find("img").hide();
		b.find("img").show();
	});
	
	b.click(function(){
		$(this).addClass("active");
		a.removeClass("active");
		
		$(this).find("img").hide();
		a.find("img").show();
	});
	
	
	regEvent("click",".list-content-one .add",function(){
		var l=$(".list-content").find(".list-content-one").length;
		var index=+$(".list-content").find(".list-content-one").eq(l-1).attr("data-index");
		var str='';
		str+='<div data-index="'+(index+1)+'" class="list-content-one">';
		str+='<span class="list-content-item"><span>充值账号'+(l+1)+'</span><input type="text" name="csairCard"></span>';
		str+='<span class="list-content-item"><span>用户姓名</span><input type="text" name="csairName"></span>';
		str+='<span class="list-content-item"><span>数量</span><input type="text" name="num"></span>';
		str+='<img class="add" src="'+path+'/resources/img/order/add.png">';
		str+='<img  class="delete" src="'+path+'/resources/img/order/del.png">';
		str+='</div>';
		$(".list-content").append(str);
	});
	regEvent("click",".list-content-one .delete",function(){
		
		var i=$(this).parent().attr("data-index");
		$(".list-content").find(".list-content-one").each(function(){
			if($(this).attr("data-index") == i){
				$(this).remove();
				$("#span"+1).remove();
			}
		});
		//$(".list-content").find(".list-content-one[data-index='"+i+"']").remove();
	});
	//绑定上传事件
	regEvent("click",".export",function(){
		$("#imgFile").click();
	});
	//绑定排序事件
	regEvent("click",".time-a",function(){
		sortAndJumpPage();
	});
	//绑定上传事件
	regEvent("click",".load-in",function(){
		window.location.href=path+"/excel/templet.xls";
	});
});
