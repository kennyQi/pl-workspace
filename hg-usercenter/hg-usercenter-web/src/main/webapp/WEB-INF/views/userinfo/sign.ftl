<#assign contextPath = springMacroRequestContext.getContextPath() />
<!doctype html>
<html lang="zhCn">
<head>
	<meta charset="UTF-8">
	<title>用户数据中心签名生成</title>
</head>
<body>
<h2>用户数据中心签名生成</h2>

<div class="pageHeader">
<form style="display: inline;">
<div class="searchBar">
    <table class="searchContent" style="width: 100%;">
        <tr>
        	<td>appId：</td>
            <td> <input type="text" name="appId" value="${appId!?html}"></td>
            <td>time：</td>
            <td> <input type="text" name="time" value="${time!?html}"></td>
			<td><input type="submit" value="生成"></td>
	 	</tr>
	</table>
</div>
</form>
</div>
<hr>
<div class="pageContent">
<table class="table" width="100%" layoutH="138" >
	 	<tbody>
				<tr>
					<td>sign：</td>
					<td>${sign!?html}</td>
				</tr>
		</tbody>
</table>
</div>
</body>
</html>