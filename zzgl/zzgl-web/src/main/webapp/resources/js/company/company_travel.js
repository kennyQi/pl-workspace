// 使用页面：company_list.html
// 日期：2015、4/21
// 作者：lw

$(document).ready(function() {
	var liNum = $('.tableUl>li').length-2//检测li个数，判断是否显示分页导航
	if(liNum >=12 ){
		$(".pagebox").show();
		}else{
			$(".pagebox").hide();
			$(".tableUl").css({"margin-bottom":"40px"});
			};
			
	
	$(document).on("click","#depatLi li",function(){
		var that=$(this);
		$.get("../company/organizeManage/getMembers?deptId="+that.attr("value"),function(data){
			$("#personLi li").remove();
			var members = eval('('+data+')');
			var html="";
			html+='<li class="l" value="0">全部</li>';
			for(var i in members){
				html+='<li class="l" value="'+members[i].id+'">'+members[i].name+'</li>';
			}
			$("#personLi").append(html);
			//初始化人员值
			$("#personLi li[value='"+$("#memberId").val()+"']").click();
			
		});
	});
	$("#companyLi li").click(function(){
		var that=$(this);
		$.get("../company/organizeManage/getDepartments?companyId="+that.attr("value"),function(data){
			$("#depatLi li").remove();
			var departments = eval('('+data+')');
			var html="";
			html+='<li class="l" value="0">全部</li>';
			for(var i in departments){
				html+='<li class="l" value="'+departments[i].id+'">'+departments[i].deptName+'</li>';
			}
			$("#depatLi").append(html);
			//初始化部门的值
			$("#depatLi li[value='"+$("#departmentId").val()+"']").click();
		});
	});
	if($("#companyId").val()!=""){
		$("#companyLi li[value='"+$("#companyId").val()+"']").click();
	}
	
	$("#ticketType li[value='"+$("#projectType").val()+"']").click();
});