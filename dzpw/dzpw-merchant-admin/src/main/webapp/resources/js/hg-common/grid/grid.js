/**
 * @修改说明: 自定义样式的分页控件;
 * @修改人: zzb;
 * @修改时间: 2014-09-22;
 * @修改版本: v1.0;
 */
(function($) {
	var Grid = function(element, options) {
		this.grid = $(element);
		this.rows = [];
		this.url = options.url;
		var paramsSour = $.extend({}, $.fn.grid.defaults.params, options.params);
		this.options = $.extend({}, $.fn.grid.defaults, options);
		$.extend(this, this.options);
		this.params = paramsSour;

		this.initContainer();
	};

	Grid.prototype = {
		constructor: Grid,

		initContainer: function() {
			var options = this.options;
			this.rowTemplate = this.grid.html();
			this.grid.data('events', {});

			this.clean();
			if (this.autoload) {
				this.load();
			}
		},

		createRows: function(records) {
			for (var i = 0, len = records.length; i < len; i++) {
				records[i].rowIndex = i + 1;
				this.rows.push(this.createRow(records[i]));
			}
			return this.rows;
		},
		
		createRow: function(record) {
			this.setData(record);
			if (this.beforeInsert) {
				this.beforeInsert(record);
			}
			var template = this.createRowTemplate ? this.createRowTemplate(record) : this.rowTemplate;
			var row = $(HG.fullProperty(template, record));
			row.css('cursor', 'pointer');
			if(this.clickable)
				row.bind('click', {record: record, grid: this}, this._onRowClick);
			row.bind('mouseover', {record: record, grid: this}, this._onMouseOver);
			row.bind('mouseout', {record: record, grid: this}, this._onMouseOut);
			if(this.clickable)
				row.bind('dblclick', {record: record, grid: this}, this._onRowDoubleClick);
		
			return row;
		},
		
		createPagination: function(pager) {				// 拼接分页条
			
			var pn = pager.pageNo, pt = pager.totalCount, ps = pager.pageSize;
			var pb = pager.totalPage || (pt + ps - 1) / ps;
			if (pt === 0) return;
			
			var self = this;
			self.params.pageNo = self.params.pageNo || 1;
			this.pagination = $('<div class="page"></div>');
			
			var $pageChoice = $('<ul></ul>');
			// 上页
			if (pn > 1) {
				var $sy = $('<li><a href="javascript:;">首页</a></li>');
				$sy.bind('click', function() {
					self.params.pageNo = 1;
					self.load();
				});
				$pageChoice.append($sy);
				
				var $syy = $('<li><a href="javascript:;">上一页</a></li>');
				$syy.bind('click', function() {
					self.params.pageNo = pn - 1;
					self.load();
				});
				$pageChoice.append($syy);
			}
			
			// 中间页码
			var startNum = pn - 4;
			var endNum = pn + 5;
			if (startNum < 0) {
				endNum -= (startNum - 1);
				startNum = 1;
			}
			if (endNum > pb) {
				startNum -= endNum - pb;
				endNum = pb;
				startNum = startNum < 1 ? 1 : startNum;
			}
			
			for (var i = startNum ; i <= endNum; i++) {
				var $ym;
				if(i == pn) {
					$ym = $('<li><a href="javascript:;" style="color:red;font-weight: bold;">' + i + '</a></li>');
				} else {
					$ym = $('<li><a href="javascript:;">' + i + '</a></li>');
				}
				$ym.data('pn', i);
				$ym.bind('click', function() {
					self.params.pageNo = $(this).data('pn');
					self.load();
				});
				$pageChoice.append($ym);
			}

			// 下页
			if (pn < pb) {
				var $xyy = $('<li><a href="javascript:;">下一页</a></li>');
				$xyy.bind('click', function() {
					self.params.pageNo = pn + 1;
					self.load();
				});
				$pageChoice.append($xyy);
				
				var $wy = $('<li><a href="javascript:;">尾页</a></li>');
				$wy.bind('click', function() {
					self.params.pageNo = pb;
					self.load();
				});
				$pageChoice.append($wy);
			}
			
			// 共几页
			$pageChoice.append('<li class="yeshu">共 ' + pb + '页  </li>');
			
			// 到第几页
			var $sub = $('<input type="submit" class="yetijiao" value="确定" />');
			$sub.bind('click', function() {
				
				var $prev = $(this).prev();
				var num = $prev.val();
				
				num = isNaN(num) ? pn : num;
				num = num < 1 ? 1 : num;
				num = num > pb ? pb : num;
				
				$prev.val(num);
				self.params.pageNo = num;
				self.load();
			});
			var $liSub = $('<li class="daod" style="margin-left: 15px;margin-top:3px;">' + 
					'到第<input type="text" class="yetxt" ' + 
					'style="height: 17px;line-height: 17px;vertical-align: top;" value="' + pn + 
					'"> 页</li>');
			$liSub.append($sub)
			$pageChoice.append($liSub);
			
			this.pagination.append($pageChoice);
			return this.pagination;
		},
		
		clean: function() {
			this.grid.children().remove();
			if(this.pagination) this.pagination.remove();
			this.rows = [];
		},

		render: function(data) {
			var rows = this.createRows(data.list);
			
			for(var i in rows) {
				$(this.grid).append(rows[i]);
			}
			if (rows.length > 0) {
				var pagination = this.createPagination(data);
				this.grid.parent().parent().append(pagination);
			}
		},
		
		load: function(params, url) {
			var self = this;
			var options = self.options;
			var method = options.method;
			this.url = url || this.url;
			$.extend(this.params, params);
			
			if (self.beforeLoad) self.beforeLoad();

			$.ajax({
				type: method,
				url: this.url,
				dataType: 'json',
				timeout: this.options.timeout,
				data: this.params,
				success: function(msg) {
					
					self.result = msg;
					self.clean();
					self.render(msg);
					
					initUI(self.grid);
					if (self.afterLoad) self.afterLoad();
					
				},
				error: function(e) {
					if (self.loadFailure) self.loadFailure();

		        }
			});
		},
		
		getSelected: function() {
			return this.selected;
		},
		
		setData: function(record) {
			return record;
		},		
			
		_onRowClick: function(e) {
			var grid = e.data.grid;
			grid.selected = e.data.record;
			
			if($(e.target).hasClass('disable-selected')) return;
			
			if (grid.isBind('selection'))
				grid.grid.trigger('selection', [e.data.record, grid, this]);
			
			if (grid.selectRow) {
				grid.selectRow.removeClass('row-selection');
			}
			var el = $(this);
			el.addClass('row-selection');
			grid.selectRow = el;
			
			if (grid.onRowClick)
				grid.onRowClick(e.data.record, e.data.row, grid);
		},
		
		_onRowDoubleClick: function(e) {
			var el = $(this), grid = e.data.grid;
			
			if (grid.isBind('doubleclick'))
				grid.grid.trigger('doubleclick', [e.data.record, grid, this]);
		},
		
		_onMouseOver: function(e) {
			var el = $(this), grid = e.data.grid;
			el.toggleClass('row-hover');
			
			if (grid.isBind('mouseover'))
				grid.grid.trigger('mouseover', [grid, this]);
		},
		
		_onMouseOut: function(e) {
			var el = $(this), grid = e.data.grid;
			el.toggleClass('row-hover');
			
			if (grid.isBind('mouseout'))
				grid.grid.trigger('mouseout', [grid, this]);
		},
		
		on: function(eventName, handler) {
			this.grid.bind(eventName, handler)
			this.grid.data('events')[eventName] = true;
		},
		
		isBind: function(eventName) {
			return this.grid.data('events')[eventName];
		}
		
	}
	
	$.fn.grid = function(option) {
		var methodReturn;
		
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('grid');
			var options = typeof option === 'object' && option;
			options.params = options.params || {};
			if (!data) $this.data('grid', (data = new Grid(this, options)));
			if (typeof option === 'string') methodReturn = data[option]();
		});
		return (methodReturn === undefined) ? $set : methodReturn;
	};
	
	$.fn.grid.defaults = {
		autoload: true,
		params: {
			pageNo: 1,
			pageSize: 10
		},
		clickable: true
	};
	
	$.fn.grid.Constructor = Grid;
})(jQuery);