/**
 * @author zzb
 * @date 2014-09-22
 */

(function($){
	
	$.hgtab = {
		_current:null,
		getCurrent:function(){
			return this._current;
		},
		/**
		 * 打开一个tab
		 * @param url	链接
		 * @param tabid	tab id 
		 * @param title 标题
		 */
		open: function(url, tabid, title, parTabid) {
			var self = this;
			
			var $content = $('#content');
			
			if (url) {
				HG.block();
				// 1. 销毁控件
				self.destroy();
				
				// 2. 重新设置页面
				$.ajax({
					type: 'GET',
					url: url,
					cache: false,
					dataType: 'html',
					success: function(response) {
						
						setTimeout(function() { 
							
							var json = HG.jsonEval(response);
							
							if (json.statusCode==HG.statusCode.error) {
								if (json.message) HG.messager(json.message, "info");
							} else if (json.statusCode == HG.statusCode.timeout) {
								// 登陆超时
								HG.confirm('确认', "登陆超时！请重新登陆", function() {
									window.location = HG.getRootPath() + "/jq/login";
								});
							} else {
								var $response = $(response);
								
								$content.html($response);
								$response.data('_tabid', tabid);
								$response.data('_url', url);
								$response.data('_title', title);
								if(parTabid)
									$response.data('_parTabid', parTabid);
								
								$content.prepend('<h2>' + title + '</h2>');
								
								$("body").data(tabid, $response);
								$.hgtab._current = $response;
								setTimeout(function() {
									initUI($content);
								}, 80);
								
							}
							
							HG.unblock();
						}, 70);
						
					},
					error: HG.ajaxError
				});
			}
			
		},
		
		/**
		 * 根据tab id打开tab(只限左侧)
		 */
		openByTabid: function(tabid) {
			
			var $menu = $('#menu');
			$('a', '#menu').each(function() {
				
				var $a = $(this);
				if($a.attr('ref') == tabid) {
					$a.click();
					// HG.triggerMinClickEvent($a[0]);
				}
			});
		},
		
		/**
		 * 销毁
		 */
		destroy: function() {
			
			var $content = $('#content');
			var $p = $($content || document);
			
			// 销毁editor
			var thisId = $p.data('editorid');
			
			if(thisId)
				UE.delEditor(thisId);
			
		},
		
	}
})(jQuery);