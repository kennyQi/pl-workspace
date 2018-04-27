<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="pageHeader">

    <form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"
          action="${contextPath}/distributorRegister/list" method="get">

        <input type="hidden" name="pageNum" value="${pagination.pageNo?c}" />
        <input type="hidden" name="numPerPage" value="${pagination.pageSize?c}" />

        <div class="searchBar">
            <table class="searchContent" style="width: 100%;">
                <tr>
                    <td align="right" width="80px">
                       公司名称：
                    </td>
                    <td>
                        <input type="text" name="companyName" value="${sqo.companyName}" />
                    </td>
                    <td align="right">
                      账号：
                    </td>
                    <td>
                         <input type="text" name="loginName" value="${sqo.loginName}" />
                    </td>
         <td colspan="4">
         	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         </td>
        
                </tr>
                <tr>
                    <td  align="right">
                        账号状态：
                    </td>
                    <td>
                        <select name="status">
                            <option value="">全部</option>
                            <option value="0" <#if sqo.status==0>selected</#if>>待审核</option>
                            <option value="1" <#if sqo.status==1>selected</#if>>已通过</option>
                            <option value="2" <#if sqo.status==2>selected</#if>>已拒绝</option>
                        </select>
                    </td>
                    <td align="right">
                       申请时间：
                    </td>
                    <td>
                        <input type="text" name="strCreateDate" value="${sqo.strCreateDate! }" class="date textInput readonly" mindate="2000-01-15" maxdate="2216-12-15" readonly="true" placeholder="开始时间">
                        -
                        <input type="text" name="endCreateDate" value="${sqo.endCreateDate! }" class="date textInput readonly" mindate="2000-01-15" maxdate="2216-12-15" readonly="true" placeholder="结束时间">
                    </td>  
                   <td colspan="2">
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
           <li><a class="add" title="确定要批量通过吗?" href="${contextPath}/distributorRegister/aduitPass" rel="ids" target="selectedTodo"><span>批量通过</span></a></li>
           <li class="line">line</li>
            <li><a class="delete" title="确定要批量拒绝吗?" href="${contextPath}/distributorRegister/aduitRefuse" rel="ids" target="selectedTodo"><span>批量拒绝</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
       		 <th width="4%"><input type="checkbox" group="ids"
                                  class="checkboxCtrl" title="全选">全选
            </th>
            <th width="10%">账号</th>
            <th width="11%">手机号</th>
            <th width="11%">商户姓名</th>
            <th width="8%">公司名称</th>
            <th width="11%">产品主站</th>
            <th width="14%">申请时间</th>
            <th width="6%">状态</th>
            <th width="15%">操作</th>
        </tr>
        </thead>
        <tbody>
     <#list pagination.list as user>
        <tr>
           <td>
           <#if user.status == 0>
                <input name="ids" value="${user.id}" type="checkbox" >
               </#if>
            </td>
            <td>
               ${user.loginName}
            </td>
            <td>${user.phone}</td>
            <td>
         ${user.linkMan}
            </td>
            <td>
          ${user.companyName}
            </td>
            <td>
          ${user.webSite}
            </td>
             <td>
               ${user.createDate}
            </td>
             <td>
               ${user.getStatusName() }
            </td>
             <td>
             	<#if user.status == 0>
                <a title="通过" title="确定要通过吗?" target="ajaxTodo" href="${contextPath}/distributorRegister/aduitPass?id=${user.id}"  style="color: #0066CC;" height="470">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a title="拒绝" title="确定要拒绝吗?" target="ajaxTodo" href="${contextPath}/distributorRegister/aduitRefuse?id=${user.id}"  style="color: #0066CC;">拒绝</a>&nbsp;&nbsp;&nbsp;&nbsp;
           		<#else>
           		--
           		</#if>
            </td>
        </tr>
       </#list>
        </tbody>
    </table>

</div>
