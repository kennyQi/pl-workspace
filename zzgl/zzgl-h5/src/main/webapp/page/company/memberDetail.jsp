<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>个人版个人资料</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx }/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx }/css/company.css"/>
  <script type="text/javascript" src="${ctx}/js/jquery.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jquery.form.js"></script> 	 <script type="text/javascript" src="${ctx}/js/json2.js"></script> 	 <script type="text/javascript" src="${ctx}/js/jsbox.js"></script> 	 <script type="text/javascript" src="${ctx}/js/pop.ups/style.js"></script><script type="text/javascript" src="${ctx}/js/pop.ups/pop.ups.js"></script>	 <script type="text/javascript" src="${ctx}/js/hsl.js"></script>
<script type="text/javascript" src="${ctx }/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/member.js"></script>
</head>
<body class="personal">
<!-- 页头  -->
<header class="header">
	  <h1>联系人详情</h1>	  
	  <div class="left-head"> 
	    <a id="goBack" href="javascript:history.go(-1);" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	</header>
<!-- 页头 -->

	<!--start 页面内容 -->
	<section class="plist pmyfile">
	<form action="${ctx}/company/queryMembers" method="post" id="saveForm">
    	<div class="input"><label>姓名:</label><p><input type="text" value='${memberResult.commonContactDTO.name }' name="name" id="name"></p></div>
    	<div class="input"><label>手机号:</label><p><input type="text" value='${memberResult.commonContactDTO.mobile }' name="mobile" id="mobile"></p></div>
    	<div class="input"><label>身份证:</label><p><input type="text" value='${memberResult.commonContactDTO.cardNo }' name="cardNo"id="cardNo"></p></div>
    	<div class="submit">
    	<input type="hidden" value="2" name="sign"/>
    	<input type="hidden"  name="id" id="commonId" />
    </form>
            <a  id="btn_click" class="" title="提交">提交</a>
        </div>
        
  	</section>
	<!--end 页面内容 -->
<style>
.submit{padding:20px;}
.submit a{background: #febd49;height:30px;text-align:center;line-height:35px;color:#fff;border-radius:4px;}
.input{background:#fff;padding:10px 20px;display:box;display:-webkit-box;}
.input p{border:1px solid #dedede;background:#fff;height:30px;line-height:30px;display:block;
-webkit-box-flex:1;box-flex:1;}
.input p input{border:none;padding:0px 5px;outline:none;}
.input label{display:block;width:80px;text-align:right;line-height:35px;}
</style>
<script type="text/javascript">
	$(function(){
		$("#commonId").val(ply.GetQueryString("id"));
	})
</script>
</body>

</html>