<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no"
	name="viewport" />
<title></title>


</head>
<body>
	<style>
* {
	padding: 0px;
	margin: 0px;
}

.cont {
	padding: 12px;
}

.cont img {
	width: 100%;
	border-radius: 4px;
	margin: 10px 0px;
}

.cont * {
	font-size: 14px;
	color: #343536;
}

.cont p {
	text-indent: 26px;
	line-height: 26px;
}

.title {
	color: #454648;
	font-size: 20px;
	line-height: 20px;
	padding-bottom: 10px;
}

.time {
	color: #6c6c6c;
	font-size: 12px;
	border-bottom: 1px solid #b0b0b0;
	padding-bottom: 10px;
}
</style>
	<div class="cont">
		<p>版本号：${bean.versionNumber}</p>

		<p>版本说明：${bean.versionContent}</p>

	</div>
</body>
</html>
