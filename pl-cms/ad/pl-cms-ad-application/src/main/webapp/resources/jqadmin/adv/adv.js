var advGrid;

$(document).ready(function() {

	init = function() {
		
		// 1. 绑定grid
		advGrid = $('#advGrid').grid({
			url: advGrid_url,		// 后台路径
			method: "POST",
			params: {
				pageSize: 10
			},
			setData: function(record) {
				
				if(!record)
					return;
				
				var result = this.result;
				
				// 图片位 转化
				if (result && result.condition) {
					
					var condition = result.condition.advPos;
					for (var i in condition) {
						if (condition[i].en == record.position)
							record.positionCn = condition[i].cn;
					}
				}
				
				// 创建日期格式化
				if(record && record.createDate)
					record.createDate = new Date(record.createDate).format('yyyy-MM-dd hh:mm');
				
				// 状态 转化
				if(record.status && record.status == '1') {
					record.statusCn = '已显示';
					record.showOrHideCn = '隐藏';
					record.showOrHideUrl = hide_url;
				} else {
					record.statusCn = '已隐藏';
					record.showOrHideCn = '显示';
					record.showOrHideUrl = show_url;
				}
				
			},
			afterLoad: function(result) {
				var result = this.result;
				
				if (result && result.condition) {
					var status = result.condition.status;
					var position = result.condition.position;
					
					if(status != 1 || position == "") {
						$('td[name="sortTd"]').hide();
						$('#operTd').attr('colspan', 6);
						$('#advTitleTh').attr('width', '25%');
						$('#sortTh').css("display", "none");
					}else{
						$('#operTd').attr('colspan', 7);
						$('#advTitleTh').attr('width', '10%');
						$('#sortTh').css("display", "");
					}
				}
			}
		}).data('grid');
		
		var start = {
		    elem: '#beginTimeStr',
		    format: 'YYYY-MM-DD',
		    max: '2299-06-16 23:59:59', //最大日期
		    choose: function(datas){
		         end.min = datas; //开始日选好后，重置结束日的最小日期
		         end.start = datas //将结束日的初始值设定为开始日
		    }
		};
		
		var end = {
		    elem: '#endTimeStr',
		    format: 'YYYY-MM-DD',
		    max: '2299-06-16 23:59:59',
		    choose: function(datas){
		        start.max = datas; //结束日选好后，充值开始日的最大日期
		    }
		};
		laydate(start);
		laydate(end);

		// 2. 绑定搜索按钮
		$('#btn2').click(function() {
			
			advGrid.load({
				title: $('input[name=title]', '#advSearchForm').val(),
				position: $('select[name=position]', '#advSearchForm').val(),
				status: $('select[name=status]', '#advSearchForm').val(),
				beginTimeStr: $('#beginTimeStr').val(),
				endTimeStr: $('#endTimeStr').val()
			});
		});

	}
	
	init();
});