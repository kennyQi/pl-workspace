<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="pageHeader">

    <form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"
          action="${contextPath}/distributorManagement/distributorList" method="get">

        <input type="hidden" name="pageNum" value="${pagination.pageNo?c}" />
        <input type="hidden" name="numPerPage" value="${pagination.pageSize?c}" />

        <div class="searchBar">
            <table class="searchContent" style="width: 100%;">
                <tr>
                    <td align="right" width="80px">
                        商户ID：
                    </td>
                    <td>
                        <input type="text" name="code" value="${sqo.code}" />
                    </td>
                    <td align="right">
                      账号：
                    </td>
                    <td>
                         <input type="text" name="account" value="${sqo.account}" />
                    </td>
         <td colspan="4">
         	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         </td>
        
                </tr>
                <tr>
                <td align="right">
                       商户姓名：
                    </td>
                    <td>
                         <input type="text" name="name" value="${sqo.name}" />
                    </td>  
                    <td  align="right">
                        账号状态：
                    </td>
                    <td>
                        <select name="status">
                            <option value="">全部</option>
                            <option value="1" <#if sqo.status==1>selected</#if>>启用</option>
                            <option value="0" <#if sqo.status==0>selected</#if>>禁用</option>
                        </select>
                    </td>
                   <td colspan="3">
         				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        			 </td>
                     <td >
                        <button type="submit">查询</button>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div class="pageContent">
<#include "/public/pagination/pagination.html">
    <div class="panelBar">
        <ul class="toolBar">
           <li><a class="add" href="${contextPath}/distributorManagement/toAddDistributor"
                   lookupgroup="orgLookup" mask="true"><span>添加</span>
            </a>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
            <th width="7%">商户ID</th>
            <th width="6%">账号</th>
            <th width="7%">手机号</th>
            <th width="6%">商户姓名</th>
            <th width="9%">公司名称</th>
            <th width="9%">主站</th>
            <th width="6%">本月消费积分</th>
            <th width="6%">可用积分余额</th>
            <th width="5%">预警积分</th>
            <th width="6%">可欠费积分</th>
            <th width="6%">使用中的商品</th>
            <th width="4%">签名KEY</th>
            <th width="4%">账号状态</th>
            <th width="4%">折扣类型</th>
            <th width="19%">操作</th>
        </tr>
        </thead>
        <tbody>
     <#list pagination.list as user>
        <tr>
            <td>
         ${user.distributor.code}
            </td>
            <td>
               ${user.loginName}
            </td>
            <td>${user.distributor.phone}</td>
            <td>
         ${user.name}
            </td>
            <td>
          ${user.distributor.name}
            </td>
            <td>
          ${user.distributor.webSite}
            </td>
            <td>
          ${sumList[user_index]}
            </td>
            <td>
               ${user.distributor.reserveInfo.usableBalance}   
            </td>
            <td>
               ${user.distributor.reserveInfo.warnValue}
            </td>
            <td>
              ${user.distributor.reserveInfo.arrearsAmount}
            </td>
            <td>
                <a title="使用中的商品" lookupgroup="orgLookup" mask="true" href="${contextPath}/distributorManagement/toProduct?distributorId=${user.distributor.id}&loginName=${user.loginName}"  style="color: #0066CC;">${user.distributor.prodNum!0}</a>
            </td>
             <td>
                <a title="查看签名key" lookupgroup="orgLookup" mask="false" href="${contextPath}/distributorManagement/signView?id=${user.id}"  style="color: #0066CC;">查看</a>
            </td>
             <td>
                 <#if user.distributor.status==1>
                 	启用
                 <#else>
                	 禁用
                 </#if>
            </td>
            <td>
            	<#if user.distributor.discountType==1>
            		定价
            	</#if>
            	<#if user.distributor.discountType==2>
            		返利
            	</#if>
            </td>
             <td>
                <#if user.distributor.status==1>
                <a title="禁用" target="ajaxTodo" href="${contextPath}/distributorManagement/modifyEnable?id=${user.distributor.id}&status=0"  style="color: #0066CC;">禁用</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <#else >
                <a title="启用" target="ajaxTodo" href="${contextPath}/distributorManagement/modifyEnable?id=${user.distributor.id}&status=1"  style="color: #0066CC;">启用</a>&nbsp;&nbsp;&nbsp;&nbsp;
                </#if>
                <a title="添加积分" lookupgroup="orgLookup" mask="true" href="${contextPath}/distributorManagement/toAddLC?distributorID=${user.distributor.id}&loginName=${user.loginName}"  style="color: #0066CC;" height="470">添加积分</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a title="可欠费积分" lookupgroup="orgLookup" mask="true" href="${contextPath}/distributorManagement/toOwingLC?id=${user.id}"  style="color: #0066CC;">可欠费积分</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a title="修改预警" lookupgroup="orgLookup" mask="true" href="${contextPath}/distributorManagement/toEditWarning?id=${user.distributor.id}&loginName=${user.loginName}"  style="color: #0066CC;">修改预警</a>

            </td>
        </tr>
       </#list>
        </tbody>
    </table>

</div>
