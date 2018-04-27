
var memberGrid;

$(document).ready(function() {
	
	// 1. 绑定grid
	memberGrid = $('#memberGrid').grid({
		url: memberGrid_url,		// 后台路径
		method: "POST",
		setData: function(record) {
			
			if(record && record.baseInfo.createTime){
				
				record.baseInfo.createTime = new Date(record.baseInfo.createTime).format('yyyy-MM-dd');
				record.status.lastLoginTime = new Date(record.status.lastLoginTime).format('yyyy-MM-dd');
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

	queryMember(memberGrid_url);
});


function queryMember(url){
	
	// 3. 绑定搜索按钮
	$('#btn3').click(function() {
		
		
		//console.info($('#source').val());
		memberGrid.load({
			loginName: $('#loginName').val(),
			mobile: $('#mobile').val(),
			source: $('#source').val(),
			beginTimeStr: $('#beginTimeStr').val(),
			endTimeStr: $('#endTimeStr').val()
		});
	});
	
	
	
	
}

	function resetPwd(id){
		 $.ajax({
		    url : reserPwdUrl,
			cache : false,
			type : 'post',
			data: {id:id},
			dataType : 'json',
			success : function(resp){					
				if(resp.status=='success'){
					alert('重置成功');
				}
			}
		 });
		
	}

