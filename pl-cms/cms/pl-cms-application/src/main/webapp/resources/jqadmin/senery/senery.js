var seceryGrid_url = "../jq/senery/senery/list";
var seceryGrid;

$(document).ready(function() {

	init = function() {
		
		// 1. 绑定grid
		seceryGrid = $('#seceryGrid').grid({
			url: seceryGrid_url,		// 后台路径
			method: "POST",
			params: {
				pageSize: 10
			},
			setData: function(record) {
				
				if(record && record.createDate)
					record.createDate = new Date(record.createDate).format('yyyy-MM-dd hh:mm');
			}
		}).data('grid');

		// 2. 绑定时间控件
		var start = {
		    elem: '#start',
		    format: 'YYYY-MM-DD',
		    max: '2299-06-16 23:59:59', //最大日期
		    choose: function(datas){
		         end.min = datas; //开始日选好后，重置结束日的最小日期
		         end.start = datas //将结束日的初始值设定为开始日
		    }
		};
		
		var end = {
		    elem: '#end',
		    format: 'YYYY-MM-DD',
		    max: '2299-06-16 23:59:59',
		    choose: function(datas){
		        start.max = datas; //结束日选好后，充值开始日的最大日期
		    }
		};
		laydate(start);
		laydate(end);

		// 3. 绑定搜索按钮
		$('#btn').click(function() {
			
			console.info($('#start').html());
			seceryGrid.load({
				name: $('input[name=name]', '#senerySearchForm').val(),
				createDateBegin: $('#start').val(),
				createDateEnd: $('#end').val()
			});
		});
		
		

	}
	
	init();
});