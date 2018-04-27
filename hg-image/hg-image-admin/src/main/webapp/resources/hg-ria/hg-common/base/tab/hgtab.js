/**
 * @author zzb
 * @date 2014-09-22
 */

(function($){
	
	$.hgtab = {
		_current:null,
		_contentTag: '#_content',
		_tabs: [],
		getContent: function() {
			var _content = $(this._contentTag);
			return _content;
		},
		getHomeInfo: function() {
			var getHomeInfo = {
				url: HG._set.proUrl + HG._set.homeUrl,
				tabid: '_home',
				title: HG.word("mainPage"),
				root: true	
			};
			return getHomeInfo;
		},
		/**
		 * push tabs
		 */
		_push: function(url, tabid, title, root) {
			var self = this;
			if(root) {
				// 重置_tabs数组
				var _curTabs = [];
				_curTabs.push(self.getHomeInfo());
				if (tabid != self.getHomeInfo().tabid) {
					_curTabs.push({
						url: url,
						tabid: tabid,
						title: title,
						root: root
					});
				}
				
				this._tabs = _curTabs;
			} else {
				// 查到该节点
				var _curTabs = [];
				for(var i in self._tabs) {
					var item = self._tabs[i];
					if(item.tabid != tabid) {
						_curTabs.push(item);
					} else {
						break;
					}
				}
				_curTabs.push({
					url: url,
					tabid: tabid,
					title: title,
					root: root
				});
				this._tabs = _curTabs;
			}
		},
		getCurrent: function() {
			return this._current;
		},
		getTab: function(tabid) {
			var _tab = $("body").data(tabid);
			return _tab;
		},
		getTabInfo: function(tabid) {
			for(var i in self._tabs) {
				var item = self._tabs[i];
				if(item.tabid != tabid) {
					return item;
				}
			}
		},
		/**
		 * 返回
		 */
		back: function() {
			var self = this;
			
			var backIndex = 0;
			if(self._tabs.length > 1) {
				backIndex = self._tabs.length - 2;
			}
			var backInfo = self._tabs[backIndex];
			
			self.open(backInfo.url, backInfo.tabid, backInfo.title, backInfo.root);
		},
		/**
		 * 打开主页
		 */
		openHomePage: function() {
			$.hgtab.open(this.getHomeInfo().url, this.getHomeInfo().tabid, this.getHomeInfo().title, this.getHomeInfo().root);
		},
		
		/**
		 * 打开一个tab
		 * @param url	链接
		 * @param tabid	tab id 
		 * @param title 标题
		 */
		open: function(url, tabid, title, root) {
			var self = this;
			title = title.trim();
			
			if (url) {
				HG.block();
				// 1. 销毁控件
				self.destroy();

				var $content = this.getContent();

				// 2. 重新设置页面
				$.ajax({
					type: 'GET',
					url: url,
					cache: false,
					dataType: 'html',
					success: function(response) {
						
						setTimeout(function() {
							
							var json = HG.jsonEval(response);
							
							if (json.statusCode == HG.statusCode.error) {
								if (json.message) HG.messager(json.message, "info");
							} else if (json.statusCode == HG.statusCode.timeout) {
								// 登陆超时
								HG.confirm(HG.word("confirm"), HG.msg("sessionTimout"), function() {
									window.location = json.forwardUrl;
								});
							} else {
								var $response = $(response);
								
								$content.html($response);
								
								// 创建 导航目录
								$.hgtab._push(url, tabid, title, root);
								$.hgtab._createPageBar()
								
								// body中写入该tab的信息
								$("body").data(tabid, $response);

								// 设置tab当前tabid
								$.hgtab._current = tabid;
								
								// initUI
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
			
			var self = this;
			
			for (var i in self._tabs) {
				var item = self._tabs[i];
				if (item.tabid == tabid) {
					self.open(item.url, item.tabid, item.title, item.root);
					return;
				}
			}
		},
		
		/**
		 * 销毁
		 */
		destroy: function() {
			
			var $content = $('#content');
			var $p = $($content || document);
			
		},
		
		/**
		 * 创建导航条
		 */
		_createPageBar: function() {
			
			var self = this;
			var $pageBar = $('<div class="page-bar"></div>');
			
			var $barUl = $('<ul class="page-breadcrumb"></ul>');
			for(var i in self._tabs) {
				var item = self._tabs[i];
				
				var $barItem = $('<li>' +
								(i == 0 ? '<i class="fa fa-home"></i>' : '<i class="fa fa-angle-right"></i>') + 
								'<a href="' + item.url + '" target="hgTab" rel="' 
								 	+ item.tabid + '" root="' + item.root + '">' + item.title + '</a>' +
							'</li>');
				$barUl.append($barItem);
			}
			$pageBar.append($barUl);
			
			self.getContent().prepend($pageBar);
			
		},
		
		/**
		 * 刷新tab
		 */
		reloadTab: function(tabid) {
			if(tabid == $.hgtab.getCurrent()) {
				initUI($.hgtab.getContent());
			} else {
				$.hgtab.openByTabid(tabid);
			}
		}
		
	}
})(jQuery);