<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页</title>
<link href="<%=request.getContextPath()%>/resources/css/gb.css" type="text/css" rel="stylesheet" />
<style type="text/css">
.container {
	width: 100%;
	background: #fff;
	margin-bottom: 0
}

.c404 {
	width: 1000px;
	margin: 0 auto;
	background: #fff;
}

.c404 p {
	position: relative;
	background:url("<%=request.getContextPath()%>/resources/img/404.png") top center no-repeat; 
	height:643px
	
}

.c404 p a {
	position: absolute;
	float: left;
	width: 170px;
	height: 30px;
	bottom: 110px;
	right: 220px;
	text-indent: -9999px;
	z-index:9999;
}

#footer {
	margin-top: inherit
}
</style>

<script type="text/javascript" src="js/jquery.min.js"></script>
</head>
<body>
	<div class="wrapper">
		<div class="container" id="container">
			<div class="c404">
				
				<p>
					<a href="<%=request.getContextPath()%>/home">返回首页</a>
				</p>
			</div>
		</div>
		<div class="T_top">
			<a href="#">Top</a>
		</div>
	</div>
</body>
</html>
