$(function(){
	var $all_1 = $('.all_1');
	var $all_2 = $('.all_2');
	var $all_3 = $('.all_3');
	var $img_11 = $('#img_11'); 
	var $img_12 = $('#img_12'); 
	var $img_13 = $('#img_13'); 
	var $img_14 = $('#img_14'); 
	var $img_21 = $('#img_21'); 
	var $img_22 = $('#img_22'); 
	var $img_23 = $('#img_23'); 
	var $img_31 = $('#img_31'); 
	var $img_32 = $('#img_32');
	var $img_33 = $('#img_33');
	$img_11.mouseenter(function(){
        $(this).stop(true,false).animate({top:'76px'});
	})
	$img_11.mouseout(function(){
        $(this).stop(true,false).animate({top:'96px'});
	})
	$img_12.mouseenter(function(){
		$(this).stop(true,false).animate({top:'76px'});
	})
	$img_12.mouseout(function(){
        $(this).stop(true,false).animate({top:'96px'});
	})
	$img_13.mouseenter(function(){
		$(this).stop(true,false).animate({top:'386px'});
	})
	$img_13.mouseout(function(){
        $(this).stop(true,false).animate({top:'406px'});
	})
	$img_14.mouseenter(function(){
		$(this).stop(true,false).animate({top:'404px'});
	})
	$img_14.mouseout(function(){
        $(this).stop(true,false).animate({top:'424px'});
	})
	$img_21.mouseenter(function(){
		$(this).stop(true,false).animate({top:'43px'});
	})
	$img_21.mouseout(function(){
        $(this).stop(true,false).animate({top:'63px'});
	})
	$img_22.mouseenter(function(){
		$(this).stop(true,false).animate({top:'76px'});
	})
	$img_22.mouseout(function(){
        $(this).stop(true,false).animate({top:'96px'});
	})
	$img_23.mouseenter(function(){
		$(this).stop(true,false).animate({top:'45px'});
	})
	$img_23.mouseout(function(){
        $(this).stop(true,false).animate({top:'65px'});
	})
	$img_31.mouseenter(function(){
		$(this).stop(true,false).animate({top:'68px'});
	})
	$img_31.mouseout(function(){
        $(this).stop(true,false).animate({top:'88px'});
	})
	$img_32.mouseenter(function(){
		$(this).stop(true,false).animate({top:'239px'});
	})
	$img_32.mouseout(function(){
        $(this).stop(true,false).animate({top:'259px'});
	})
	$img_33.mouseenter(function(){
		$(this).stop(true,false).animate({top:'48px'});
	})
	$img_33.mouseout(function(){
        $(this).stop(true,false).animate({top:'68px'});
	})
	$all_1.mouseenter(function(){
        $img_11.stop(true,false).animate({top:'66px',left:'30px'});
        $img_12.stop(true,false).animate({top:'76px',left:'651px'});
        $img_13.stop(true,false).animate({top:'408px',left:'750px'});
        $img_14.stop(true,false).animate({top:'404px',left:'50px'});
	})
	$all_1.mouseout(function(){
		$img_11.stop(true,false).animate({top:'96px',left:'-20px'});
		$img_12.stop(true,false).animate({top:'96px',left:'671px'});
		$img_13.stop(true,false).animate({top:'406px',left:'800px'});
		$img_14.stop(true,false).animate({top:'424px',left:'8px'});
	})
	$all_2.mouseenter(function(){
        $img_21.stop(true,false).animate({top:'23px'});
        $img_22.stop(true,false).animate({top:'136px'});
        $img_23.stop(true,false).animate({top:'35px'});
	})
	$all_2.mouseout(function(){
        $img_21.stop(true,false).animate({top:'63px'});
        $img_22.stop(true,false).animate({top:'96px'});
        $img_23.stop(true,false).animate({top:'65px'});
	})
	$all_3.mouseenter(function(){
        $img_31.stop(true,false).animate({top:'58px',left:'190px'});
        $img_32.stop(true,false).animate({top:'239px',left:'456px'});
        $img_33.stop(true,false).animate({top:'60px',left:'888px'});
	})
	$all_3.mouseout(function(){
        $img_31.stop(true,false).animate({top:'88px',left:'150px'});
        $img_32.stop(true,false).animate({top:'259px',left:'456px'});
        $img_33.stop(true,false).animate({top:'60px',left:'942px'});
	})
})

// 快速定位
function sliderTo(position){
   $('body,html').animate({scrollTop:position+'px'},1000);
   return false;
}