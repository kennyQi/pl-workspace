<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
</head>
<body>
	<div class="cont">
    <img src="${ctx}/img/404.jpg" />
</div>
<div class="btn ov">
    <span>
        <a href="${ctx}/init"><img src="${ctx}/img/404_back.png" /></a>
    </span>
    <span>
        <a href="javascript:history.go(-1);"><img src="${ctx}/img/404_reload.png" /></a>
    </span>
</div>
<style>
    html,body{background:#a8dde9;}
    .cont img{width:100%;}
    .btn{padding-bottom:20px;}
    .btn span{width:50%;float: left;}
    .btn span img{width:62.5%;margin:auto;display: block;}
</style>
</body>
</html>