<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="accordion" fillSpace="sidebar">
	<div class="accordionHeader">
		<h2>
			<span>Folder</span>系统设置
		</h2>
	</div>
	<div class="accordionContent">
		<ul class="tree treeFolder">
			<#list sysPerms as sysPerm>
				<li><a  href="${contextPath}${(sysPerm.url)!''}" target="navTab" rel="${(sysPerm.rel)!''}">${(sysPerm.displayName)!''}</a></li>
			</#list>
		</ul>
	</div>
</div>
