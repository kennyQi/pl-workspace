<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<title>图片裁切</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script src="../picUtil.js" type="text/javascript"></script>
<script type="text/javascript"
	src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
function loadImg(file,img){
	console.info("load success");
	    var files = !!file.files ? file.files : [];
	    if (!files.length || !window.FileReader) return;
        var reader = new FileReader();
        reader.readAsDataURL(files[0]);
		reader.onloadend = function(){
				img.attr("src",this.result);
//				img.load();
		}
}
function callBackCopp(x,y,w,h){
	console.info(x+','+y+','+w+','+h);
	
}
</script>
<title><?=${title}?></title>
<style>
	body{
		padding:30px 100px;
	}
	input{
		height:25px;
		line-height:25px;
		margin-top:5px;
	}
</style>
</head>
<body>
	<div id="demo0" style="height:400px;width: 600px;border: 1px solid #888;">
		<div><img id="demoImg" width="300px" onload="cutPic(this,callBackCopp)"/></div>
		<input id="demoFile" type="file" name="file" onchange="loadImg(this,$('#demoImg'))"/> 
	</div>
</html>

