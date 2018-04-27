<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="${contextPath}/distributorManagement/addLCAudit?curNav=addAudit"  target="ajax" rel="addAudit"><span>添加积分</span></a></li>
					<li><a href="${contextPath}/distributorManagement/owingLCAudit?curNav=owingAudit" target="ajax" rel="owingAudit"><span>可欠费积分</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:100%;">
			<div id="addAudit" class="page unitBox">
				<div class="pageContent">
				    <div class="panelBar">
				        <ul class="toolBar">
				            <li><span style="padding: 0">添加积分审核列表</span>
				            </li>
				        </ul>
				    </div>
				    <table class="table" width="100%" layoutH="138">
				        <thead>
				        <tr>
				            <th width="40">商户ID</th>
				            <th width="40">商户公司</th>
				            <th width="30">增加积分</th>
				            <th width="20">积分余额</th>
				            <th width="30">证明</th>
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
				         	${item.distributor.id }
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
				             	 ${item.applyDate }
				            </td>
				             <td>
				                ${item.getCheckStatusName() }
				            </td>
				             <td>
				             	<#if item.checkStatus == 1>
				                <a title="通过" lookupgroup="orgLookup" mask="true" href="${contextPath}/staff/addoredit?id=${staff.id}"  style="color: #0066CC;">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
				                <a title="拒绝" lookupgroup="orgLookup" mask="true" href="${contextPath}/staff/addoredit?id=${staff.id}"  style="color: #0066CC;">拒绝</a>&nbsp;&nbsp;&nbsp;&nbsp;
				            	<#else>
				            		-
				            	</#if>
				            </td>
				        </tr>
				       </#list>
				        </tbody>
				    </table>
				
				<#include "/public/pagination/pagination.html">
				</div>
			</div>
			<div id="id="owingAudit"></div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	
	<p>&nbsp;</p>
	
<!-- 	<div class="tabs" currentIndex="0" eventType="click"> -->
<!-- 		<div class="tabsHeader"> -->
<!-- 			<div class="tabsHeaderContent"> -->
<!-- 				<ul> -->
<!-- 					<li><a href="javascript:;"><span>标题1</span></a></li> -->
<!-- 					<li><a href="javascript:;"><span>标题2</span></a></li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="tabsContent" style="height:250px;"> -->
<!-- 			<div>内容1 -->
<!-- 				<p> -->
<!-- 					<label>客户名称：</label> -->
<!-- 					<input name="name" class="required" type="hover" size="30" value="" alt="请输入客户名称"/> -->
<!-- 				</p> -->
<!-- 			</div> -->
<!-- 			<div> -->
				
<!-- 				<div class="tabs" currentIndex="0" eventType="click" style="width:300px"> -->
<!-- 					<div class="tabsHeader"> -->
<!-- 						<div class="tabsHeaderContent"> -->
<!-- 							<ul> -->
<!-- 								<li><a href="javascript:;"><span>标题1</span></a></li> -->
<!-- 								<li><a href="javascript:;"><span>标题2</span></a></li> -->
<!-- 							</ul> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="tabsContent" style="height:150px;"> -->
<!-- 						<div>内容1</div> -->
<!-- 						<div>内容2</div> -->
<!-- 					</div> -->
<!-- 					<div class="tabsFooter"> -->
<!-- 						<div class="tabsFooterContent"></div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="tabsFooter"> -->
<!-- 			<div class="tabsFooterContent"></div> -->
<!-- 		</div> -->
<!-- 	</div> -->
</div>
