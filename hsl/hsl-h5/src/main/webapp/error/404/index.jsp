<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
<title>404</title>
<!--公共样式部分-->

</head>
<body>
<div class="cont">
    <img src="${ctx}/error/404/images/404.jpg" />
</div>
<div class="btn ov">
    <span>
        <a href="${ctx}"><img src="${ctx}/error/404/images/404_back.png" /></a>
    </span>
    <span>
        <a href="javascript:void(0);" onclick="location.reload();"><img src="${ctx}/error/404/images/404_reload.png" /></a>
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
