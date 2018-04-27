<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="accordion" fillSpace="sidebar">
	<div class="accordionHeader">
		<h2>
			<span>Folder</span>日志管理
		</h2>
	</div>
	<div class="accordionContent">
		<ul class="tree treeFolder">
			<#list logPerms as logPerm>
				<li><a href="${contextPath}${logPerm.url}" target="navTab" rel="${logPerm.rel}">${logPerm.displayName}</a></li>
			</#list>
		</ul>
	</div>
</div>



