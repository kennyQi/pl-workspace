/**
 * @author zzb
 */
(function($) {
	
	$.fn.extend({
		
		ajaxTodo:function() {
			return this.each(function() {
				var $this = $(this);
				$this.unbind('click').bind('click', function(event) {

					var url = unescape($this.attr("href"));
					var title = $this.attr("title");
					if (title) {
						HG.confirm('确认', title, function(r) {
							ajaxTodo(url, $this.attr("callback"));
						});
					} else {
						ajaxTodo(url, $this.attr("callback"));
					}
					event.preventDefault();
				});
			});
		},
		
		selectedTodo: function() {
			
			function _getIds(selectedIds) {
				var ids = "";
				var $box = $.hgtab.getCurrent();
				$box.find("input:checked").filter("[name='"+selectedIds+"']").each(function(i){
					var val = $(this).val();
					ids += i==0 ? val : ","+val;
				});
				return ids;
			}
			return this.each(function() {
				var $this = $(this);
				var selectedIds = $this.attr("rel") || "ids";
				var postType = $this.attr("postType") || "map";

				$this.unbind('click').bind('click', function(){
					var targetType = $this.attr("targetType");
					var ids = _getIds(selectedIds, targetType);
					if (!ids) {
						HG.messager("请选择信息！", "info");
						return false;
					}
					
					var _callback = $this.attr("callback") || (hgTabAjaxDone);
					if (! $.isFunction(_callback)) _callback = eval('(' + _callback + ')');
					
					function _doPost(){
						$.ajax({
							type:'POST', url:$this.attr('href'), dataType:'json', cache: false,
							data: function(){
								if (postType == 'map'){
									return $.map(ids.split(','), function(val, i) {
										return {name: selectedIds, value: val};
									})
								} else {
									var _data = {};
									_data[selectedIds] = ids;
									return _data;
								}
							}(),
							success: _callback,
							error: HG.ajaxError
						});
					}
					var title = $this.attr("title");
					if (title) {
						HG.confirm('确认', title, _doPost);
					} else {
						_doPost();
					}
					return false;
				});
				
			});
		}
	});
})(jQuery);