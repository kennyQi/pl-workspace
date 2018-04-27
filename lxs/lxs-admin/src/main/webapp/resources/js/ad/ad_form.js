/**
 * 描述：广告表单页面js
 * 作者：xiaoyuzhzh
 */

/**
 * 广告form切换
 */
$(function(){
	$(".AD_POSI").on("change",function(){
		var value = $(this).val();
		$(".formWarpper").hide();
		$(".formWarpper."+value).show();
		$('option[value='+value+']').prop("selected",true);
	});
	
});
