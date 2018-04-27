/**
 * @修改说明: 自定义样式的分页控件;
 * @修改人: zzb;
 * @修改时间: 2014-10-16;
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
			
			var $paginLine = $('<div class="row"></div>');
			
			//  页面尺寸选择
			var $pageListCho = $('<div class="col-md-2"></div>');
			var $select = $('<select class="form-control select2me" data-placeholder=""></select>');
			for (var i in self.pageList) {
				var num = self.pageList[i];
				var $option = $('<option value="' + num + '"' + (num == self.params.pageSize ? 'selected' : '') + '>' + num + '</option>');
				$select.append($option);
			}
			
			$select.change(function() {
				var num = $(this).children('option:selected').val();
				console.info(num);
				self.params.pageSize = num;
				self.params.pageNo = 1;
				
				self.load();
			});
			$pageListCho.append($select);
			$paginLine.append($pageListCho);
			
			$select.selectpicker();
			
			// 页码数据
			var $pageInfo = $('<div class="col-md-2 dataTables_info">共 ' + pt + ' 条记录</div>');
			$paginLine.append($pageInfo);

			var $pageInfoShow = $('<div class="col-md-2 dataTables_info">第 '
					+ ((pn - 1) * ps + 1) + ' 到 ' + ((pn * ps) > pt ? pt : (pn * ps)) +  ' 条</div>');
			$paginLine.append($pageInfoShow);
			
			// 分页条
			var pagination = $('<div class="col-md-6 text-right dataTables_paginate paging_simple_numbers"></div>');
			var ul = $('<ul class="pagination"></ul>');
			pagination.append(ul);
			
			// 首页
			var pres = $('<li class="paginate_button previous ' + (pn <= 1 ? 'disabled' : '') + '"></li>');
			var btn = $('<a href="#"><i class="fa fa-angle-double-left"></a>').bind('click', function() {
				if (!$(this).parent().hasClass('disabled')) {
					self.params.pageNo = 1;
					self.load();
				}
			})
			pres.append(btn);
			ul.append(pres);
			
			// 上一页
			var pre = $('<li class="paginate_button previous ' + (pn <= 1? 'disabled' : '') + '"></li>');
			btn = $('<a href="#"><i class="fa fa-angle-left"></a>').bind('click', function() {
				if (!$(this).parent().hasClass('disabled')) {
					self.params.pageNo--;
					self.load();
				}
			})
			pre.append(btn);
			ul.append(pre);
			
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
				li = $('<li class="paginate_button ' + (pn === i ? 'disabled' : '') + '"><a href="javascript:;">' + i + '</a></li>');
				ul.append(li);
				
				li.data('pn', i);
				li.bind('click', function() {
					if (!$(this).hasClass('disabled')) {
						self.params.pageNo = $(this).data('pn');
						self.load();
					}
				});
				ul.append(li);
			}
			
			// 下一页
			var next = $('<li class="paginate_button ' + (pn >= pb ? 'disabled' : '') + '"></li>');
			btn = $('<a href="#"><i class="fa fa-angle-right"></a>').bind('click', function() {
				if (!$(this).parent().hasClass('disabled')) {
					self.params.pageNo++;
					self.load();
				}
			});
			next.append(btn);
			ul.append(next);
			
			// 尾页
			var nexts = $('<li class="paginate_button ' + (pn >= pb ? 'disabled' : '') + '"></li>');
			btn = $('<a href="#"><i class="fa fa-angle-double-right"></a>').bind('click', function() {
				if (!$(this).parent().hasClass('disabled')) {
					self.params.pageNo = pb;
					self.load();
				}
			})
			nexts.append(btn);
			ul.append(nexts);
			
			$paginLine.append(pagination);
			
			this.pagination = $paginLine;
			
			return $paginLine;
		},
		
		clean: function() {
			this.grid.children().remove();
			if(this.pagination) this.pagination.remove();
			this.rows = [];
		},

		render: function(data) {
			if(!data || !data.list || data.list.length == 0) {
				$(this.grid).append(this.noRecord);
				return;
			}
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

			HG.block(null, self.grid);
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
					
					HG.unblock(self.grid);
				},
				error: function(e) {
					if (self.loadFailure) self.loadFailure();
					HG.unblock(self.grid);
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
		pageList: [10, 20, 30, 40, 50],
		clickable: true,
		noRecord: '<div>暂无数据！</div>'
		
	};
	
	$.fn.grid.Constructor = Grid;
})(jQuery);