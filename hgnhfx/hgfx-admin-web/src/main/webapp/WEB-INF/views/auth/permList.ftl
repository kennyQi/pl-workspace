<#assign contextPath=springMacroRequestContext.getContextPath() />

<div class="pageHeader">
	<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"
		action="${contextPath}/auth/perm/list" method="post">
	
		<input type="hidden" name="pageNum" value="${pagination.pageNo?c}" />
		<input type="hidden" name="numPerPage" value="${pagination.pageSize?c}" />
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>名称：</label>
					<input type="text" name="displayName" value="${sqo.displayName!?html}" style="width:200px;" />
				</li>
				<li>
					<label>URL：</label>
					<input type="text" name="url" value="${sqo.url!?html}" style="width:200px;" />
				</li>
				<li>
					<select class="combox" name="permType">
						<option value="">所有类型</option>
						<#list permTypeList as pt>
						<option value="${pt.key!}" <#if pt.key == sqo.permType>selected="selected"</#if>>${pt.value!?html}</option>
						</#list>
					</select>
				</li>
			</ul>
			<div class="subBar">
				<ul><li><div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">查询</button>
					</div>
				</div></li></ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${contextPath}/auth/perm/add?permId=${sqo.permId!0}"
				   lookupgroup="orgLookup" mask="true" width="660" height="280"><span>添加</span>
			</a>
			</li>
			<#if sqo.permId?? && sqo.permId gt 0 >
			<li>
			<a title="修改左边选中" lookupgroup="orgLookup" mask="true" href="${contextPath}/auth/perm/edit?id=${sqo.permId}"
					width="548" height="240" class="edit"><span>修改左边选中</span></a>
			</li>
			</#if>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"
				href="${contextPath}/auth/perm/del" class="delete"><span>批量删除</span>
			</a>
			</li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="140">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="ids"
					class="checkboxCtrl">
				</th>
				<th width="50">名称</th>
				<th width="50">资源路径</th>
				<th width="50">类型</th>
				<th width="50">需要检查的角色名</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody>
			<#list pagination.list as perm>
			<tr>
				<td><input name="ids" value="${perm.id}" type="checkbox">
				</td>
				<td>${perm.displayName!'-'?html}</td>
				<td>${perm.url!?html}</td>
				<td><#list permTypeList as pt><#if pt.key == perm.permType>${pt.value!?html}</#if></#list></td>
				<td>${perm.permRole!'-'?html}</td>
				<td><a title="删除" target="ajaxTodo"
					href="${contextPath}/auth/perm/del?id=${perm.id}" class="btnDel">删除</a> <a
					title="编辑" lookupgroup="orgLookup" mask="true" href="${contextPath}/auth/perm/edit?id=${perm.id}"
					class="btnEdit" width="660" height="300">编辑</a></td>
			</tr>
			</#list>
		</tbody>
	</table>
	
	<#include "/public/pagination/pagination.html">
</div>
