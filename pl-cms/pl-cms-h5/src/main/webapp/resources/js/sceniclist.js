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
		var fileUploadPath=$("#fileUploadPath").val();
		if(flag){
			$(".m_load").show();
			flag=false;
			pageNo++;
			console.log(1);
			$.ajax({
			   type: "POST",
			   url: contextPath+"/scenic/pullUpList",
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
						var htmlStr = "";
						var title=lists[i].scenicSpotBaseInfo.name;
						if(title.length>14){
							title=title.substring(0,14)+"...";
						}
						htmlStr = htmlStr + "<a href='"+contextPath+"/scenic/details/"+lists[i].id+"'><li class='box paT'>";
						htmlStr = htmlStr + "<img src='"+fileUploadPath+lists[i].scenicSpotBaseInfo.titleImage.url+"' alt='"+lists[i].scenicSpotBaseInfo.name+"' class='pic'>";
						htmlStr = htmlStr + "<p class='tt'>"+title+"</p>";
						htmlStr = htmlStr + " </li></a>";
						var line=$(htmlStr);
						$(".hist_list").append(line);
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
				   console.info("error");
				   pageNo--;
				   flag=true;
			   }
			});
		}
	}
});