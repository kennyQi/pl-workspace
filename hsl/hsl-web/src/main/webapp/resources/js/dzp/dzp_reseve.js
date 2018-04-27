// liwei
//2015-05-19
$(document).ready(function() {
$("#num").val("1");
	$(".min").on("click",function(){  //减少
		if($(this).next("#num").val()>1){
			$(this).next("#num").val(parseInt($(this).next("#num").val()) - 1);
			$(this).css({"color":"#777"});
		}else{
			$(this).css({"color":"#ddd"});
		}
	});
	$(".add").on("click",function(){  //增加
	   /* *//* if($(this).prev("#num").val() <= 4){*/
		    $(this).prev("#num").val(parseInt($(this).prev("#num").val()) + 1);
		    $(".min").css({"color":"#777"});
		/* }*/
	});
$(document).on("mouseout",".mouse",function(){
	$(this).find('.money').css({"color":"#6fbf4d"});
	});
$(document).on("mouseover",".mouse",function(){
	$(this).find('.money').css({"color":"#ff6600"});
	});
$(".mobile").blur(function() {    //验证手机号码
  if(this.value){
    pattern = /^1[3|4|5|8][0-9]\d{8}$/;
    if (!pattern.test(this.value)) {
      $(this).siblings(".tick_ts").html('请输入正确的手机号码');
	  $(this).siblings(".tick_ts").css({"color":"red"});
    }else{
	  $(this).siblings(".tick_ts").html('接收订单确认短信，请务必填写填写正确');
	  $(this).siblings(".tick_ts").css({"color":"#888"});
		}
  };
});

});