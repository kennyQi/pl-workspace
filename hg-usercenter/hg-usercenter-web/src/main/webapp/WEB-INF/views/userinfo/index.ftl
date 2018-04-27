<#assign contextPath = springMacroRequestContext.getContextPath() />
<!doctype html>
<html lang="zhCn">
<head>
	<meta charset="UTF-8">
	<title>用户数据中心列表</title>
</head>
<body>
<h2>用户数据中心列表</h2>

<div class="pageHeader">
<form style="display: inline;">
	<input type="text" name="appId" value="${request.getParameter('appId')}" hidden="hidden"/>
	<input type="text" name="time" value="${request.getParameter('time')}" hidden="hidden"/>
	<input type="text" name="sign" value="${request.getParameter('sign')}" hidden="hidden"/>
<div class="searchBar">
    <table class="searchContent" style="width: 100%;">
        <tr>
        	<td>ID：</td>
            <td> <input type="text" name="id" value="${sqo.id!?html}"></td>
            <td>用户名：</td>
            <td> <input type="text" name="userName" value="${sqo.userName!?html}"></td>
			<td>email：</td>
			<td><input type="text" name="email" value="${sqo.email!?html}"></td>
			<td>电话：</td>
			<td><input type="text" name="phone" value="${sqo.phone!?html}"></td>
			<td>对应平台：</td>
			<td><input type="text" name="fromPlatform" value="${sqo.fromPlatform!?html}"></td>
			<td><input type="submit" value="查询"></td>
	 	</tr>
	</table>
</div>
</form>
|
<form action="${contextPath}/modifyUserInfo" method="post" style="display: inline;">
	<input type="text" name="appId" value="${request.getParameter('appId')}" hidden="hidden"/>
	<input type="text" name="time" value="${request.getParameter('time')}" hidden="hidden"/>
	<input type="text" name="sign" value="${request.getParameter('sign')}" hidden="hidden"/>
<div class="searchBar">
    <table class="searchContent" style="width: 100%;">
        <tr>
        	<td>ID：</td>
            <td> <input type="text" name="id" value="${sqo.id!?html}"></td>
            <td>用户名：</td>
            <td> <input type="text" name="userName" value="${sqo.userName!?html}"></td>
			<td>email：</td>
			<td><input type="text" name="email" value="${sqo.email!?html}"></td>
			<td>电话：</td>
			<td><input type="text" name="phone" value="${sqo.phone!?html}"></td>
			<td>对应平台：</td>
			<td><input type="text" name="fromPlatform" value="${sqo.fromPlatform!?html}"></td>
			<td><input type="submit" value="保存"></td>
	 	</tr>
	</table>
</div>
</form>
</div>
<hr>
<div class="pageContent">
<table class="table" width="100%" layoutH="138" >
        <thead>
	        <tr>
				<th >ID</th>
				<th>用户名</th>
				<th>email</th>
				<th>性别</th>
				<th>年龄</th>
				<th>电话</th>
				<th>状态</th>
			</tr>
	 	</thead>
	 	<tbody>
			<#list pagination.list as dep>
				<tr>
					<td>${dep.id!?html}</td>
					<td>${dep.userName!?html}</td>
					<td>${dep.email!?html}</td>
					<td>${dep.sex!?html}</td>
					<td>${dep.age!?html}</td>
					<td>${dep.phone!?html}</td>
					<td>${dep.status!?html}</td>
				</tr>
			</#list>
		</tbody>
</table>
</div>
查询耗时：${time?c}毫秒
</body>
</html>