// JavaScript Document

//$(document).ready(function() {
//	var h = $(window).height();		
//	var h1 = $("#footer").height();
//	if(h>700){
// 	$("#footer").css({'position':'absolute','top':h-h1-21})}
//	else{
//		}
// });
 
   $(".p_item .p").hover(function(){
		$(this).addClass("sel")	
		},function(){
		$(this).removeClass("sel")
		});	
		$(".air ul li").hover(function(){
		$(this).addClass("hover")	
		},function(){
		$(this).removeClass("hover")
		})
		$(".ticket span").hover(function(){
		$(this).addClass("hover");		
		},function(){
		$(this).removeClass("hover")
		});
 		
 		//热门预定
		var s = $("#gdq ul li").width()
 		var s1 = $("#gdq ul").width()
		$("#next_L").click(function(){
 		$("#gdq ul").css("maring-left",s*6)
 		})
    //tab切换
   $(".subnav li").bind("click", function () {
           var index = $(this).index(index);
           var divs = $(".submenu > div");
					  $(this).parent().children("li").attr("class", "no");//将所有选项置为未选中
            $(this).attr("class", "sel");     //设置当前选中项为选中样式
            divs.hide(); 
            divs.eq(index).show();
  });
  $("#m_subm li").bind("click", function () {
           var index = $(this).index(index);
           var divs = $(".m_p > div");
					  $(this).parent().children("li").attr("class", "no");//将所有选项置为未选中
            $(this).attr("class", "sel");     //设置当前选中项为选中样式
            divs.hide(); 
            divs.eq(index).show();
  });
  $("#J_air li").bind("click", function () {
           var index = $(this).index(index);
           var divs = $(".m_air > div");
					  $(this).parent().children("li").attr("class", "no");//将所有选项置为未选中
            $(this).attr("class", "sel");     //设置当前选中项为选中样式
            divs.hide(); 
            divs.eq(index).show();
  });