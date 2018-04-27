$(function(){
	//单票的显示个数控制
	var $itemDpLi = $('.item.dp li');
	var len = $itemDpLi.length;
	if(len>3){
		$itemDpLi.hide();
		$itemDpLi.eq(0).show();
		$itemDpLi.eq(1).show();
	}
	$('.btn-open').on('click',function(){
		$(this).hide();
		$itemDpLi.show();
	});

    //景区介绍
    $('.part .tit').click(function(){
    	if($(this).find('.btn-switch').hasClass('close')){
    		$(this).find('.btn-switch').removeClass('close').addClass('open').parent().next('.detail').removeClass('ov');
    	}else if($(this).find('.btn-switch').hasClass('open')){
    		$(this).find('.btn-switch').removeClass('open').addClass('close').parent().next('.detail').addClass('ov');
    	}
    });

})