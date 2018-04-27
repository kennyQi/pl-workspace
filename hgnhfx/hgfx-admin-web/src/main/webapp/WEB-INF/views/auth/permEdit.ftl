<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="pageContent">
	<form method="post" enctype="multipart/form-data" action="${contextPath}/auth/perm/save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="perm.id" value="${perm.id???string(perm.id!'0','')}" />
			<div class="unit">
				<label>资源名称：</label>
				<input type="text" name="perm.displayName" size="64" maxlength="64" value="${perm.displayName!?html}" />
			</div>
			<div class="unit">
				<label>URL：</label>
				<input type="text" name="perm.url" size="64" maxlength="512" class="required" value="${perm.url!?html}" />
			</div>
			<div class="unit">
				<label>菜单REL：</label>
				<input type="text" name="perm.rel" size="64" maxlength="512" value="${perm.rel!?html}" />
			</div>
			<div class="unit">
				<label>资源类型：</label>
				<select class="combox" name="perm.permType" class="required">
					<#list permTypeList as permType>
					<option value="${permType.key!?html}" <#if permType.key == perm.permType>selected="selected"</#if>>${permType.value!?html}</option>
					</#list>
				</select>
			</div>
			<div class="unit">
				<label>角色：</label>
				<input type="text" name="perm.permRole" size="64" maxlength="64" value="${perm.permRole!?html}" />
			</div>
			<div class="unit">
				<label>上级资源：</label>
				<select class="combox" name="perm.parentId">
					<option value="">顶级资源</option>
					<#list parentPermList as parentPerm>
					<option value="${parentPerm.id!0}" <#if parentPerm.id == perm.parentId>selected="selected"</#if>>${parentPerm.displayName!?html}</option>
					</#list>
				</select>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>

