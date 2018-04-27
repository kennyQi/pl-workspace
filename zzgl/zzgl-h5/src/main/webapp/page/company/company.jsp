<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门员工通讯录</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/company.css" />

<!-- javascript -->
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
</head>
<body class="personal">
	<!-- 页头  -->
	<header class="header">
		<h1>联系人</h1>
		<div class="left-head">
			<a id="goBack" href="javascript:history.go(-1);"
				class="tc_back head-btn"> <span class="inset_shadow"> <span
					class="head-return"></span>
			</span>
			</a>
		</div>
	</header>
	<!-- 页头 -->
	<form action="${ctx}/company/searchView" method="post" id="zuzhi_form1">
	<section class="zuzhi_search">
			<div class="user_name">
				<input type="text" value="" class="txt_search" id="J_txt_s" name="searchName" placeholder="搜索">
				<em id="z_sea_icon"></em>
			</div>
	</section>
	</form>
	<c:forEach items="${companyListResult.companyList}" var="company">
	<section class="zuzhi_style">
			<h2>${company.companyName}</h2>
	</section>
	<!--start 页面内容 -->
	<c:forEach items="${company.departmentList}" var="department">
	<section class="plist pzuzhi nbottom">
		<a href="${ctx}/company/queryMembers?departmentId=${department.id}&pageNo=1&pageSize=5" class="i10"><div>
				<i></i>${department.deptName}<em><u>${department.totalCount}</u>></em>
			</div></a>
	</section>
	</c:forEach>
	</c:forEach>
	<!--end 页面内容 -->
	<script type="text/javascript">
	$("#J_txt_s").focus(function(){
			$(this).val("");
	 });
	 $("#J_txt_s").blur(function(){
		 if($("#J_txt_s").val().length>0){			  
			}
			else{
				$(this).val("");
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
</script>
</body>
</html>