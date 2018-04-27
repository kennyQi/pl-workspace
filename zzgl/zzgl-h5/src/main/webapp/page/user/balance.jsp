<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>账户余额</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/jsbox.js"></script>
<script type="text/javascript" src="${ctx}/js/pop.ups/init.js"></script>
<script type="text/javascript" src="${ctx}/js/hsl.js"></script>
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/company.css"/>
</head>
<body class="personal">
<!-- 页头  -->
<header class="header">
	  <h1>账户余额</h1>	  
	  <div class="left-head"> 
	    <a id="goBack" href="${ctx}/user/center?openid=" class="tc_back head-btn">
	        <span class="inset_shadow">
	            <span class="head-return"></span>
	        </span>
	    </a> 
	  </div>
	</header>
<!-- 页头 -->
	<section class="hd_myprofile">
	 	<c:if test="${!empty headimgurl}">
	 		<img src="${headimgurl }" alt="头像" id="headImage" class="hd_data_p radius3"/>
	 	</c:if>
	 	<c:if test="${empty headimgurl}">
	 		<img src="${ctx}/img/defaultHeadImage.png" alt="头像" id="headImage" class="hd_data_p radius3"/>
	 	</c:if>
	 	<h2 style="color:#3f3f3f;position: absolute;color: #3f3f3f;left: 95px;font-size: 15px;">账号:<i id="userName" >${userName}</i>  余额:<i id="balanceMoney"></i>元</h2>
	</section>
	<!--start 页面内容 -->
	<section class="plist pmyfile">
		<a href="javascript:;" class="i06"><div style="margin-left:20px;">充值:<p style="display:inline-block"><input type="text" class="inBlance" id="code"/></p>
		<span class="btnBlance">确定</span>
		</div>
		</a>
 		
 	</section>
	<!--end 页面内容 -->
<style>
.inBlance{border:1px solid #d4d4d4;width:150px;}
.btnBlance{width:60px;height:25px;text-align:center;line-height:25px;color:#fff;background:#febd49;display:inline-block;text-indent:0px;
position:absolute;right:0px;top:10px;}
i{font-style:normal}
</style>
<script type="text/javascript">
$(function(){
	 getBalance();
	$(".btnBlance").click(function(){
		var code=$("#code").val();
		if(code!=""){
			$.ajax({
				url : '${ctx}/company/recharge?code='+code,
				type : 'post',
				dataType :'json',
				async:false,
				success : function(data) {
					$.pop.tips(data.resule);
					if(data.resule=="充值成功"){
						 getBalance();
					}
					
				}
			})
		}else{
			$.pop.tips("充值码不能为空");
		}
	})
})

function getBalance(){
	$.ajax({
		url : '${ctx}/company/toRecharge',
		type : 'post',
		dataType :'json',
		async:false,
		success : function(data) {
			if(data!=null){
				$("#balanceMoney").html(data.balance.toFixed(2));
			}else{
				$("#balanceMoney").html(0);
			}
		}
	})
}
</script>
</body>
</html>