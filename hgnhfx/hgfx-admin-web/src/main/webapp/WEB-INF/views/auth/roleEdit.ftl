<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="pageContent">
	<form method="post" enctype="multipart/form-data" action="${contextPath}/auth/role/editRole" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="id" value="${role.id???string(role.id!0,'')}" />
			<div class="unit">
				<label>角色名：</label>
				<input type="text" name="roleName" size="64" class="required" maxlength="64" value="${role.roleName!?html}" />
			</div>
			<div class="unit">
				<label>显示名：</label>
				<input type="text" name="displayName" size="64" class="required" maxlength="64" value="${role.displayName!?html}" />
			</div>
			<div class="unit">
				<label>权限：</label>
				<div layoutH="50" style="float:left; display:block; overflow:auto; width:353px; border:solid 1px #CCC; line-height:21px; background:#fff">
					${permTreeHtml!}
				</div>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>

