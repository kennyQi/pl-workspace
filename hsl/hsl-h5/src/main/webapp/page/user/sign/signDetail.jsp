<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/page/common/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
		<title>汇购6年庆签到详情</title>
		<style>
			*{margin:0px;padding:0px;list-style: none;font-size:18px;font-family: "Microsoft YaHei"}
			.info{margin:10px;}
		</style>
	</head>
	<body>
		<img src="${ctx}/img/sign/banner.jpg" style="width:100%;" />
		<div class="info" id="info">
		
			<p>
				<b>【汇购6年庆】</b>应签到${totalCount}人，实际签到${alreadySignCount}人，${noSignCount}人未签到。
			</p>
			<br />
			
			<p>
				<b>【未签到人】</b>${noSignStr}
			</p>
		
		</div>
	</body>
</html>
