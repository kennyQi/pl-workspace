$(function(){
	var flag=true;
	$(window).scroll(function(){
		var totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop())+50;
		if ($(document).height() <= totalheight)
		{
					//ajax函数
					//list_ajax();
					pullUpList();
		}
	});
	Date.prototype.Format = function(fmt) 
	{ //author: meizz 
	  var o = { 
	    "M+" : this.getMonth()+1,                 //月份 
	    "d+" : this.getDate(),                    //日 
	    "h+" : this.getHours(),                   //小时 
	    "m+" : this.getMinutes(),                 //分 
	    "s+" : this.getSeconds(),                 //秒 
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度 
	    "S"  : this.getMilliseconds()             //毫秒 
	  }; 
	  if(/(y+)/.test(fmt)) 
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	  for(var k in o) 
	    if(new RegExp("("+ k +")").test(fmt)) 
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
	  return fmt; 
	};
	function pullUpList(){
		var contextPath=$("#contextPath").val();
		if(flag){
			$(".m_load").show();
			flag=false;
			pageNo++;
			console.log(1);
			$.ajax({
			   type: "POST",
			   url: contextPath+"/contribution/pullUpList",
			   dataType: "text",
			   cache: false,
			   data: {
				   "pageNo" : pageNo, "pageSize" : pageSize
			   },
			   success: function(msg){
				    msg=eval("("+msg+")");
				    var lists = msg.pagination.list;
				    pageNo = msg.pagination.pageNo;
				    //var listCount = msg.pagination.pageNo * msg.pagination.pageSize;
					for(var i = 0; i < lists.length; i++){
						var createDate=new Date(lists[i].createDate).Format("yyyy-MM-dd hh:mm:ss");
						var htmlStr = "";
						htmlStr = htmlStr + "<li><a href='"+contextPath+"/contribution/details/"+lists[i].id+"'>";
						htmlStr = htmlStr + "<article class='tt fl'>";
						htmlStr = htmlStr + "<h2 class='paT paB'>"+lists[i].baseInfo.title+"</h2>";
						htmlStr = htmlStr + "<span class='t_b'>"+createDate+"</span>";
						htmlStr = htmlStr + " </article></a></li>";
						var line=$(htmlStr);
						$(".new_list").append(line);
					}
					if (!msg.haveMore) {
						$(".m_load").hide();
						console.error(2);
						flag = false;
					} else {
						$(".m_load").hide();
						flag = true;
					}
				},
			   error: function(){
				   console.error("error");
				   pageNo--;
				   flag=true;
			   }
			});
		}
	}
});