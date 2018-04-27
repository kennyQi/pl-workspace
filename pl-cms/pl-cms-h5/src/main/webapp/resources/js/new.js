$(function(){

	$(window).scroll(function(){
		var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop())+50;
		if ($(document).height() <= totalheight)
		{
					//ajax函数
//					list_ajax();
		}
	});
	var path=$(".zan").attr("path");
	var conV=$(".zan").attr("conV");
	//评论
	inputEdit($("#msg"),"old","rel_input_on");
	$("#submit").click(function(){
		var msg=$.trim($("#msg").val());//评论内容；
		var reg=/[\'*~`@#$%^&+={}\[\]\<\>\(\)]/;
		if(msg.length>500){
			showBox("评论内容过长！");
//			var hei=document.documentElement.clientHeight;
//			var tm_box=$(".tm_ct").height();
//			$(".tm_ct").html("评论内容过长！");
//			$(".tm_bg").show().css("height",hei);
//			$(".tm").show().css({"height":hei,"top":(hei-tm_box)/2,});
			return false;
		}
		if(msg==""){
			showBox("评论内容不能为空！");
//			var hei=document.documentElement.clientHeight;
//			var tm_box=$(".tm_ct").height();
//			$(".tm_ct").html("评论内容不能为空！");
//			$(".tm_bg").show().css("height",hei);
//			$(".tm").show().css({"height":hei,"top":(hei-tm_box)/2,});
			return false;
		}
		if(reg.test(msg)){
			showBox("评论内容包含有特殊字符！");
//			var hei=document.documentElement.clientHeight;
//			var tm_box=$(".tm_ct").height();
//			$(".tm_ct").html("评论内容包含有特殊字符！");
//			$(".tm_bg").show().css("height",hei);
//			$(".tm").show().css({"height":hei,"top":(hei-tm_box)/2,});
			return false;
		}else{
			$.ajax({
			       type: "POST",
			       url: path+"/comment/create",
			       data: "articleId="+conV+"&content="+msg,
			       contentType: "application/x-www-form-urlencoded; charset=utf-8",
			       dataType:"text",
			       success: function(data) {
			    	   //评论成功
//			    	    var hei=document.documentElement.clientHeight;
//						var tm_box=$(".tm_ct").height();
//						$(".tm_ct").html("提交成功，等待审核");
//						$(".tm_bg").show().css("height",hei);
//						$(".tm").show().css({"height":hei,"top":(hei-tm_box)/2,});
						showBox("提交成功，等待审核");
						setTimeout(function(){
							$(".tm_bg").add(".tm").hide();
						}, 2000);
						$("#msg").val("");
			       }
			});
			//msg_ajax(); 页面先添加上去，
//			var count=parseInt($("#comment_count").val())+1;
//			var myDate=new Date();
//			var  d='<li class="box">';
//			d=d+'<div class="box_cont">';
//			d=d+'<span class="flo fl">'+count+'楼</span>';
//			d=d+'<div class="main fl paT paB"><span class="tip fl"><!-- <label class="come fl">来自iphone</label> -->';
//			d=d+' <i class="time fr">'+myDate.toLocaleString();
//			d=d+'</i></span><span class="msg fl">'+msg;
//			d=d+' </span></div></div></li>';
//			$(".new_list").prepend(d);
		}
		//ajax 函数
	});
	$(".tm_bg").add(".tm").click(function(){
		$(".tm_bg").add(".tm").hide();
	});
	
	//稿件点赞
	$("#zan").click(function(){
		if($(this).attr("class").indexOf("on")!=-1){
			//取消
			$(this).removeClass("zan_on");
			var count=parseInt($(this).html());
			$(this).html(count-1);
			//点赞 请求后台
			$.ajax({
			       type: "POST",
			       url: path+"/contribution/cancelSupport/"+conV,
			       contentType: "application/x-www-form-urlencoded; charset=utf-8",
			       dataType:"text",
			       success: function(data) {
//			    	   	alert(data);
			       }
			});
		}else{
			$(this).addClass("zan_on");
			var count=parseInt($(this).html());
			$(this).html(count+1);
			//点赞 请求后台
			$.ajax({
			       type: "POST",
			       url: path+"/contribution/support/"+conV,
			       contentType: "application/x-www-form-urlencoded; charset=utf-8",
			       dataType:"text",
			       success: function(data) {
//			    	   alert(data);
			       }
			});
		}
	});
	//文章点赞
	$("#article_zan").click(function(){
		
		if($(this).attr("class").indexOf("on")!=-1){
			//取消
			$(this).removeClass("zan_on");
			var count=parseInt($(this).html());
			$(this).html(count-1);
			//点赞 请求后台
			$.ajax({
			       type: "POST",
			       url: path+"/article/cancelSupport/"+conV,
			       contentType: "application/x-www-form-urlencoded; charset=utf-8",
			       dataType:"text",
			       success: function(data) {
//			    	   	alert(data);
			       }
			});
		}else{
			$(this).addClass("zan_on");
			var count=parseInt($(this).html());
			$(this).html(count+1);
			//点赞 请求后台
			$.ajax({
			       type: "POST",
			       url: path+"/article/support/"+conV,
			       contentType: "application/x-www-form-urlencoded; charset=utf-8",
			       dataType:"text",
			       success: function(data) {
//			    	   alert(data);
			       }
			});
		}
	});
});
function showBox(title){
	setTimeout(function(){
		var hei=document.documentElement.clientHeight;
		var tm_box=$(".tm_ct").height();
		$(".tm_ct").html(title);
		$(".tm_bg").show().css("height",hei);
		$(".tm").show().css({"height":hei,"top":(hei-tm_box)/2,});
	}, 200);
}
