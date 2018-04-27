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
	//图片设定高度
	$(".new_list img").each(function(){
		var w=$(this).width();
		$(this).height(w*0.75);
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
		var channelId=$("#channelId").val();
		if(flag){
			$(".m_load").show();
			flag=false;
			pageNo++;
			console.log(1);
			$.ajax({
			   type: "POST",
			   url: contextPath+"/article/pullUpList",
			   dataType: "text",
			   cache: false,
			   data: {
				   "pageNo" : pageNo, "pageSize" : pageSize,"showChannelId":channelId
			   },
			   success: function(msg){
				    msg=eval("("+msg+")");
				    var lists = msg.pagination.list;
				    pageNo = msg.pagination.pageNo;
				    //var listCount = msg.pagination.pageNo * msg.pagination.pageSize;
					for(var i = 0; i < lists.length; i++){
						var htmlStr = "";
						var title=lists[i].baseInfo.title;
						if(title.length>14){
							title=title.substring(0,14)+"...";
						}
						var createDate=new Date(lists[i].createDate).Format("yyyy-MM-dd hh:mm:ss");
						htmlStr = htmlStr + "<li><a href='"+contextPath+"/article/details/"+lists[i].id+"?showChannelId="+channelId+"'>";
						htmlStr = htmlStr + "<span class='picBox fl'><img src='"+fileUploadPath+lists[i].baseInfo.titleImage.url+"' class='fl' /></span>";
						htmlStr = htmlStr + "<article class='tt fr'>";
						htmlStr = htmlStr + "<h2>"+title+"</h2>";
						htmlStr = htmlStr + "<span class='paT'>"+createDate+"</span>";
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
				   console.info("error");
				   pageNo--;
				   flag=true;
			   }
			});
		}
	}
});