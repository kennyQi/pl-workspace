$(document).ready(function() {

	init = function() {
		if ( $.browser.msie && /6.0/.test(navigator.userAgent) ) {
			try {
				document.execCommand("BackgroundImageCache", false, true);
			}catch(e){}
		}
		//清理浏览器内存,只对IE起效
		if ($.browser.msie) {
			window.setInterval("CollectGarbage();", 10000);
		}
		
		$('a', '#menu').each(function() {
			$(this).click(function(event) {
				
				// 1. 切换中间页面
				var $a = $(this);
				var url = unescape($a.attr("href"));
				var tabid = $a.attr('ref');
				var title = $a.attr('title') || $a.text();

				$.hgtab.open(url, tabid, title);
				
				// 2. 调整样式
				$('li', '#menu').removeClass('sel');
				var $par = $a.parent();
				if($par.is('li')) {
					$par.addClass('sel');
				}

				event.preventDefault();
				return false;
			});
			
			if($(this).attr('default') == 'default') {
				$(this).click();
				// HG.triggerMinClickEvent($(this)[0]);
			}
		});
		

	}
	
	init();
});

