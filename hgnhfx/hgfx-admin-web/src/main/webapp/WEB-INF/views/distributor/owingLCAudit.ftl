<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="pageHeader" style="height:100%;">
<form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);
 "
          action="${contextPath}/distributorManagement/owingLCAudit" method="post">
        <input type="hidden" name="pageNum" value="${pagination.pageNo?c}" />
        <input type="hidden" name="numPerPage" value="${pagination.pageSize?c}" />
        </form>
        </div>
        
<div class="pageContent">
<#include "/public/pagination/pagination.html">
				    <div class="panelBar">
				        <ul class="toolBar">
				            <li><span style="padding: 0">可欠费积分审核列表</span>
				            </li>
				        </ul>
				    </div>
				    <table class="table" width="100%" layoutH="138">
				        <thead>
				        <tr>
				            <th width="40">商户ID</th>
				            <th width="40">商户公司</th>
				            <th width="30">原可欠费积分</th>
				            <th width="20">设定积分为</th>
				            <th width="30">理由</th>
				            <th width="40">申请提交人</th>
				            <th width="30">申请时间</th>
				            <th width="30">状态</th>
				            <th width="10">操作</th>
				        </tr>
				        </thead>
				        <tbody>
				     
				        <#list pagination.list as item>
				        <tr>
				            <td>
				         	${item.distributor.code }
				            </td>
				            <td>
				               ${item.distributor.name}
				            </td>
				            <td>
				              ${item.preArrears }
				            </td>
				            <td>
				               ${item.applyArrears }
				            </td>
				            <td title="${item.reason}">
				               <#if item.reason?length gt 10>${item.reason?substring(0,10)}..<#else>${item.reason}</#if>
				            </td>
				            <td>
				                ${item.operator.loginName }
				            </td>
				             <td>
				             	 ${(item.applyDate?string("yyyy-MM-dd HH:mm"))!'' }
				            </td>
				             <td>
				                ${item.getCheckStatusName() }
				            </td>
				             <td>
				             	<#if item.checkStatus == 1>
				                <a title="确定通过？"  target="ajaxTodo" href="${contextPath}/distributorManagement/owingLCAuditPass?arrearsRecordID=${item.id}"  style="color: #0066CC;">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
				                <a title="确定拒绝？"  target="ajaxTodo" href="${contextPath}/distributorManagement/owingLCAuditRefuse?arrearsRecordID=${item.id}"  style="color: #0066CC;">拒绝</a>&nbsp;&nbsp;&nbsp;&nbsp;
				            	<#else>
				            		-
				            	</#if>
				            </td>
				        </tr>
				       </#list>
				        </tbody>
				    </table>
				
				</div>
				
