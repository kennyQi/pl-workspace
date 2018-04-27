<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>中智票量</title>
<!-- meta信息，可维护 -->
<meta charset="UTF-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<!-- 样式 -->
<link rel="stylesheet" href="${ctx}/css/base.2.0.css" />
<link rel="stylesheet" href="${ctx}/css/company.css"/>
</head>
<body class="personal">
	<!-- 页头  -->
	<header class="header">
		<h1>个人中心</h1>
	 	<div class="left-head">
                <a id="goBack" href="${ctx}/jp/init?openid=${openid}" class="tc_back head-btn">
                    <span class="inset_shadow">
                        <span class="head-return"></span>
                    </span>
                </a>
            </div>

            <div class="right-head">
                <a href="${ctx}/jp/init?openid=${openid}" class="head-btn fn-hide">
                    <span class="inset_shadow">
                        <span class="head-home"></span>
                    </span>
                </a>
            </div>
	</header>
	<!-- 页头  -->
	<section class="p_header"> 
		<c:if test="${!empty headimgurl}">
	 		<img style="width:90px;" src="${headimgurl }" alt="头像" id="headImage"/>
	 	</c:if>
	 	<c:if test="${empty headimgurl}">
	 		<img style="width:90px;" src="${ctx}/img/defaultHeadImage.png" alt="头像" id="headImage"/>
	 	</c:if>
	  <div class="p_name">
	    <h3>${nickname }</h3>
	  </div>
	</section>
	<!--start 页面内容 -->
	<section class="people plist plistmt"> 
		<a href="${ctx}/user/userDetailInfo" class="i01">
		<div><i></i>我的资料<em>></em></div>
		</a>     
		<a href="${ctx }/page/user/balance.jsp" class="i05">
		<div><i></i>账户余额<em></em></div>
		<a href="${ctx }/page/user/repwd.jsp" class="i02">
		<div><i></i>修改密码<em>></em></div>
		</a> 
		<a href="${ctx}/user/jpos?openid=${openid}" class="i03">
		<div><i></i>我的订单<em>></em></div>
		</a> 
		<%if(((String)request.getAttribute("userType")).equals("2")){ %>
		<a href="${ctx}/company/list" class="i04">
		<div><i></i>部门员工通讯录<em>></em></div>
		</a> 
		<%} %>
		<a href="${ctx }/company/couponQuery?isAvailable=true" class="i05">
		<div><i></i>我的卡券<em>></em></div>
		</a>
	</section>
	<!--end 页面内容 -->
<script src="${ctx}/js/jquery-1.10.2.js"></script>
<script src="${ctx}/js/ajaxupload.js"></script>
<script type="text/javascript">
var maxSize = 2*1024*1024;//不设置表示不限制大小，此为2M
new AjaxUpload($("#headImage"), {
	action : "${ctx}/file/imgUpload",
	name : 'file',
	responseType : 'json',
	onSubmit : function(file, ext, fileSize) {
		if(maxSize && maxSize < fileSize) {
			alert("图片大小为:"+fileSize);
			alert("图片大小超过了2M！");
			return false;
		}
		if (!(ext && /^(png|jpg|gif|jpeg|PNG|JPG|GIF|JPEG)$/.test(ext))) {
			alert("请上传图片格式文件！");
			return false;
		}
	},
	onComplete : function(file, response, fileSize) {
		console.info(response);
		if ("success" == response.status) {
  		
	  		//$('#headImage').attr("src", response.imageUrl);
	  		//$('#' + idRef).val(response.imageId);
	  		updateHeadImage(response.imageUrl);
	  	} else {
	  		alert(response.msg);
	  	}
	}
});

function updateHeadImage(imageUrl){
	$.ajax({
		type:"post",
		url:"${ctx}/user/updateHeadImage",
		dataType:"json",
		data: {
			imagePath: imageUrl
		},
		success:function(msg){
			$('#headImage').attr("src", imageUrl);
		}
	});
}
</script>
</body>
</html>