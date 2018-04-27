<#assign contextPath = springMacroRequestContext.getContextPath() />

<form id="pagerForm" method="post" action="${contextPath}/getLogList">
	<input type="hidden" name="pageNum" value="${pagination.pageNo?c}" />
	<input type="hidden" name="numPerPage" value="${pagination.pageSize?c}" />
	<input type="hidden" name="userName" value="${sqo.userName}" />
	<input type="hidden" name="update" value="${sqo.update}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${contextPath}/getLogList" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					操作人：<input type="text" name="userName" value="${sqo.userName}"/>
				</td>
				<td><span>操作类型：</span>
					<select  name="update">
					  <option value="">全部</option>
					  <#list updateList as logUpdate>
						<option value="${logUpdate.value}" <#if logUpdate.value== sqo.update>selected="selected"</#if>>${logUpdate.value}</option>
						</#list>
					</select>
				</td>
				<!-- <td>
					创建日期：<input type="text" class="date" readonly="true" />
				</td> -->
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<!-- <li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li> -->
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		    <li class="line">line</li>
		<!-- 	<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li> -->
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80">序列</th>
				<th width="120">操作人</th>
				<th width="120">操作类型</th>
				<th width="200">操作明细</th>
				<th width="80">建档日期</th>
			</tr>
		</thead>
		<tbody>
		  <#list pagination.list as log>
			<tr target="sid_user" rel="1">
				<td>${log_index+1}</td>
				<td>${log.userName}</td>
				<td>${log.update}</td>
				<td>${log.detail}</td>
				<td>${log.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			</tr>
		 </#list>
			
		
		</tbody>
	</table>
	<#include "/public/pagination/pagination.html">
</div>
