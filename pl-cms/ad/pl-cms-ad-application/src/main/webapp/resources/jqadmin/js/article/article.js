
var articleGrid;

$(document).ready(function() {
	
	// 1. 绑定grid
	articleGrid = $('#articleGrid').grid({
		url: memberGrid_url,		// 后台路径
		method: "POST",
		setData: function(record) {
			if(record && record.title){
				if(record.title && record.title.length>10){
					record.title=record.title.substring(0,10)+"...";
				}
				if(record.status){
					var currentStatus = record.status.currentStatus;
					if(currentStatus == 1){
						record.currentStatus='显示';
					}else if(currentStatus == 2){
						record.currentStatus='隐藏';
					}
				}
				if(record.createDate){
					record.createDate = new Date(record.createDate).format('yyyy-MM-dd hh:mm');
				}
			}
		}
	}).data('grid');
	
	// 2. 绑定时间控件
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

	// 3. 绑定搜索按钮
	$('#btn2').click(function() {
		
		articleGrid.load({
			contentChannelId: $('select[name=contentChannelId]', '#articleSearchForm').val(),
			title: $('input[name=title]', '#articleSearchForm').val(),
			currentStatus: $('select[name=currentStatus]', '#articleSearchForm').val(),
			beginTimeStr: $('#beginTimeStr').val(),
			endTimeStr: $('#endTimeStr').val()
		});
	});
});

	function delArticle(id){
		var msg = "你确认删除吗？";
		if(confirm(msg)==true){
			delUrl = delUrl+id;
			 $.ajax({
					url : delUrl,
					cache : false,
					type : 'post',
					dataType : 'json',
					success : function(resp){					
						alert(resp.message);
						window.location.reload();
					}
				 });
		}
		
	}

