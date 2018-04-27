 $(function(){
	showTip =  function(data,Info) {
		var html = '<div class="showtopmask" style="width:100%;height:100%;background:rgba(0,0,0,0.3);z-index:99"><div class="showtip_div" style="border-radius:3px;position:fixed;top:0;left:0;background:#fff;padding:0 25px 0 25px;min-width:100px;opacity:1;min-height:30px;text-align:center;color:#333;display:block;z-index:999"><div class="showTipTitle" style="padding:18px 0;font-size:15px;color:#333">'+data+'</div><div class="showTipInfo" style="padding:18px 0;border-top:#bab5b1 1px solid;color:#007cf0;font-size:15px">'+Info+'</div></div></div>';
	$('body').append($(html).css({'position':'absolute','display':'block','width':'242px','min-height':'30px'}));
	var $t = $('.showtip_div');
	var $t1 = $('.showtopmask');
	var $t2 =$('.showtopmask');
	var winW = $(window).width(),
	winH = $(window).height()/2+$(window).scrollTop();
	altW = $t.width();
	altH = $t.height();
	var winH1=$(window).height();		 	
	$t.css({'top':winH-altH/2,'left':25,'width':winW-100}).fadeIn(0).delay(1500).animate({top: ($(window).height()+$(window).scrollTop()-$t.height()*11)+'px' ,opacity: "hide"}, 0, function() {
            $t.remove();               
        });
    $t1.css({'top':0,'z-index':9999,'width':winW+'px'}).fadeIn(0).delay(1500).animate({top: ($(window).height()+$(window).scrollTop()-$t1.height()*11)+'px' ,opacity: "hide"}, 0, function() {
            $t1.remove();               
        }); 
    $t2.css({'height':winH1});   
	};

	showTipS =  function(data) {
		var html = '<div class="showtopmask" style="width:100%;height:100%;background:rgba(0,0,0,0.3);z-index:99"><div class="showtip_div" style="border-radius:3px;position:fixed;top:0;left:0;background:#fff;padding:0 25px 0 25px;min-width:100px;opacity:1;min-height:30px;text-align:center;color:#333;display:block;z-index:999"><div class="showTipTitle" style="padding:18px 0;font-size:15px;color:#333">'+data+'</div></div></div>';
	$('body').append($(html).css({'position':'absolute','display':'block','width':'242px','min-height':'30px'}));
	var $t = $('.showtip_div');
	var $t1 = $('.showtopmask');
	var winW = $(window).width();
	winH = $(window).height()/2+$(window).scrollTop();
	altW = $t.width();
	altH = $t.height();
	var winH1=$(window).height();		 	
	$t.css({'top':winH-altH/2,'left':25,'width':winW-100}).fadeIn(0).delay(1500).animate({top: ($(window).height()+$(window).scrollTop()-$t.height()*11)+'px' ,opacity: "hide"}, 0, function() {
            $t.remove();               
        });
    $t1.css({'top':0,'z-index':9999,'width':winW+'px'}).fadeIn(0).delay(1500).animate({top: ($(window).height()+$(window).scrollTop()-$t1.height()*11)+'px' ,opacity: "hide"}, 0, function() {
            $t1.remove();               
        }); 
    $t2.css({'height':winH1});
	};
});


