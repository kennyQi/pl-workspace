// JavaScript Document

//导航我的订单
	$(".my_oder").hover(function(){
		$(".my_oder_m").css("display","block")
		},function(){
		$(".my_oder_m").css("display","none")
	});


//返回顶部
$(document).ready(function(){	
	 var height = $(window).height()					   
$(window).scroll(function(){
if($(this).scrollTop()> height/2){	
	$(".T_top").css("display","block")
	}
else{
	$(".T_top").css("display","none")}	
});
});

//切换
$(".ticket span,.t_city_icon").click(function(){
 		  var c = $("#citySelect").val();
			var c1 = $("#citySelect1").val();
 			if(c == c1){
				alert("不能选择同一个城市");
				}
			else{
		  $("#citySelect").val(c1);
      $("#citySelect1").val(c);
			}		  
 		})