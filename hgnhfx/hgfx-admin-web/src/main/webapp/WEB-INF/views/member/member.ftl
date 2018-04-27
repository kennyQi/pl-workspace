<#-- @ftlvariable name="useCache" type="String" -->
<#-- @ftlvariable name="time" type="long" -->
<#-- @ftlvariable name="sqo" type="hg.member.common.spi.qo.DepartmentSQO" -->
<#-- @ftlvariable name="pagination" type="hg.common.model.Pagination" -->
<#assign contextPath = springMacroRequestContext.getContextPath() />
<!doctype html>
<html lang="zhCn">
<head>
	<meta charset="UTF-8">
	<title>测试页面</title>
</head>

<body>
<h2>测试页面</h2>
<form style="display: inline;">
	部门名称：<input type="text" name="name" value="${sqo.name!?html}">
	对应平台：<input type="text" name="fromPlatform" value="${sqo.fromPlatform!?html}">
	<label><input type="checkbox" name="useCache" value="y"
	<#if useCache!="">checked</#if>>使用缓存</label>
	<input type="submit" value="查询">
</form>
|
<form action="${contextPath}/member/create" method="post" style="display: inline;">
	对应平台：<input type="text" name="fromPlatform" value="${sqo.fromPlatform!?html}">
	部门名称：<input type="text" name="name">
	<input type="submit" value="保存">
</form>
<hr>
<table border="0" width="80%">
	<tr>
		<td width="30%">ID</td>
		<td width="70%">名称</td>
	</tr>
<#list pagination.list as dep>
<#-- @ftlvariable name="dep" type="hg.member.common.domain.model.Department" -->
	<tr>
		<td>${dep.id!?html}</td>
		<td>${dep.name!?html}</td>
	</tr>
</#list>

</table>
查询耗时：${time?c}毫秒
</body>
</html>