<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="accordion" fillSpace="sidebar">
	<div class="accordionHeader">
		<h2>
			<span>Folder</span>权限管理
		</h2>
	</div>
	<div class="accordionContent">
		<ul class="tree treeFolder">
			<#list authPerms as authPerm>
				<li><a  href="${contextPath}${(authPerm.url)!''}" target="navTab" rel="${(authPerm.rel)!''}">${(authPerm.displayName)!''}</a></li>
			</#list>
		</ul>
	</div>
</div>



