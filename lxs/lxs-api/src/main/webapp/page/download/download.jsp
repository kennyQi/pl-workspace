<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
	<title>随心游</title>
	<link rel="stylesheet" href="${ctx}/page/download/css/download.css" type="text/css">
	<script src="${ctx}/page/download/js/jquery-1.11.3.min.js" type="text/javascript"></script>
</head>
<body>
	<div class="bg" id="fi" style="display:none;">
		<div class="btn-download-wrap">
			<a href="https://itunes.apple.com/cn/app/sui-xin-you365/id1003658507?mt=8" target="_blank" class="btn-download-iphone">
				<img src="${ctx}/page/download/img/btn_iphone_bg.png" width="100%">
			</a>
			<p class="placeholder"></p>
			<a href="${ctx}/apk/suixinyou.apk" target="_blank" class="btn-download-android">
				<img src="${ctx}/page/download/img/btn_android_bg.png" width="100%">
			</a>
		</div>
	</div>
	<div class="wx" id="se" style="display:none;">
       <img src="${ctx}/page/download/img/wx.png" width="100%" height="100%"/>
    </div>
	<script type="text/javascript">
		$(function(){
			var $bg = $('.bg');
			var $btnDownloadWrap = $('.btn-download-wrap');
			var w = $bg.width();
			var h = $bg.height();
			$btnDownloadWrap.height(h*0.1838768).css('top',h*0.33);
		})
	</script>
    <!-- 微信提示 -->
	<script >
	    var userAgentInfo = navigator.userAgent;
	    if(userAgentInfo.indexOf("MicroMessenger")!=-1){
	        document.getElementById("se").style.display="block";
	    }else{
	        document.getElementById("fi").style.display="block";
	    }
        function download(){
	    	if(userAgentInfo.indexOf("MicroMessenger")==-1){
	    		document.getElementById("download").click();
		    }
        }
    </script>
</body>
</html>
