// 使用页面：company_list.html
// 日期：2015、4/21
// 作者：lw

$(document).ready(function() {

	
	
	var liNum = $('.tableUl>li').length-2//检测li个数，判断是否显示分页导航
	if(liNum >=12 ){
		$(".pagebox").show();
		}else{
			$(".pagebox").hide();
			$(".tableUl").css({"margin-bottom":"40px"});
			};
$("body").bind("click",function(evt){     //点击空白处隐藏
if($(evt.target).parents(" .ri > .selectBox > .select > li").length==0) { 
   $('.selectNav').hide(); 
   $(".sl").css({"background":"url(images/ic_tr.png) 0 0"});
  } 
});
	});