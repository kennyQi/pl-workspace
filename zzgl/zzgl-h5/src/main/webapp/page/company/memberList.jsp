<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>常用联系人</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx }/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx }/css/company.css"/>
<style>
.plist a.i10 div i {
background-position: 0 -210px;
}
</style>
<!-- javascript -->
<script type="text/javascript" src="${ctx }/js/jquery-1.10.2.js"></script>
</head>
<body class="personal">
<!-- 页头  -->
<header class="header">
	  <h1>联系人</h1>	  
	  <div class="left-head"> 
	    <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	  <div class="right-head"> 
	    <a  href="javaScript:void(0)" class="tc_back head-btn" id="add">
	       		添加
	    </a> 
	  </div>
	</header>
<!-- 页头 -->

<section class="zuzhi_search">
<form action="${ctx}/company/queryMembers" method="post" id="zuzhi_form1">
      <div class="user_name">
         <input type="text" value="" class="txt_search"  id="J_txt_s" autocomplete="off" name="name" placeholder="搜索">
         <em id="z_sea_icon"></em>
      </div>
  </form>
	</section>
  	
	<div id="memberList">
	<c:forEach items="${memberListResult.commonContactList }" var="member">
		<!--start 页面内容 -->
		<section class="plist pzuzhi nbottom">
			<a href="${ctx }/company/queryMemberDetail?id=${member.id}&departmentId=${memberListResult.departId}" class="i10"><div><i></i>${member.name }<em></em></div></a>
	 	</section>
	</c:forEach> 
	</div>
	
	<section class="jiazai">
	<!-- <a href="javascript:addList();" id="addjz">点击加载...</a> -->
	<input type="hidden" value="${memberListResult.departId}" id="hiddenDepartId"/>
	</section>
	<!--end 页面内容 -->
  
<script type="text/javascript">
	$("#J_txt_s").focus(function(){
			$(this).val("")
	 });
	 $("#J_txt_s").blur(function(){
		 if($("#J_txt_s").val().length>0){			  
			}
			else{
				$(this).val("")
				}
			
	 })
	
	$("#z_sea_icon").click(function(){
		var searchName = $("#J_txt_s").val();
		if(check(searchName)){
			alert("搜索内容包含敏感词汇");
		}else{
			$("#zuzhi_form1").submit();
		}
	})
	
	function check(str){
		 if(str.indexOf("<") != -1 || str.indexOf(">") != -1 || str.indexOf("/") != -1 
				 || str.indexOf("\\") != -1 || str.indexOf("%") != -1) {
				return true;
		 }else{
			 return false; 
		 }
		 
	 }
	 
	 var pageNo = 1;
	 var pageSize = 5;
	 function addList(){
		 pageNo++;
		
			 $.ajax({
			   type: "POST",
			   url: "${ctx }/company/queryMembersMore",
			   dataType: "json",
			   cache: false,
			   data: "pageNo="+pageNo+"&pageSize="+pageSize,
			   success: function(msg){
					if(msg.result == "1"){
						if(msg.memberCount < 1){
							pageNo--;
							alert("没有更多成员");
						}else{
							var memberList = msg.memberList;
							for(var i=0; i<msg.memberCount; i++){
								var member = memberList[i];
								var htmlStr = "";
								htmlStr = htmlStr + "<section class='plist pzuzhi nbottom'>";
								htmlStr = htmlStr + "<a href='${ctx }/company/queryMemberDetail?id="+member.id+"'"; 
								htmlStr = htmlStr + "class='i10'><div><i></i>"+member.name+"<em><u>"+member.job+"</u></em></div></a>";
								$("#memberList").append(htmlStr);
							}
						}
					}else{
						page--;
						alert("加载失败");
					}
			  },
			  error: function(msg){
				  pageNo--;
				  alert("加载失败");
			  }
			});
			
	 }
	 $("#add").click(function(){
		 window.location.href="${ctx }/company/memberAdd";
	 })
</script>
</body>
</html>