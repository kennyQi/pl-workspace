<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="pageHeader">

    <form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"
          action="${contextPath}/orderManagement/orderList" method="get">

        <input type="hidden" name="pageNum" value="${pagination.pageNo?c}" />
        <input type="hidden" name="numPerPage" value="${pagination.pageSize?c}" />

        <div class="searchBar">
            <table class="searchContent" style="width: 100%;">
                <tr>
                    <td align="right">
                        订单编号：
                    </td>
                    <td>
                        <input type="text" name="flowNo" value="${mileOrderSqo.flowNo! }" />
                    </td>
                    <td align="right">
                       商户：
                    </td>
                    <td>
                        <select name="distributorId">
                        	<option value="">全部</option>
                            <#list distriList as item>
                            <option <#if item.id == mileOrderSqo.distributorId>selected="selected"</#if> value="${item.id }">${item.name }</option>
                       		</#list>
                       
                        </select>
                    </td>
                    <td align="right">
                        渠道商：
                    </td>
                    <td>
                        <select  class="combox" name="channelId" ref="sel_pro" refUrl="${contextPath}/orderManagement/proJsonList?value={value}">
                        	<option value="">全部</option>
                        	<#list channelList as item>
                            <option <#if item.id == mileOrderSqo.channelId>selected="selected"</#if> value="${item.id }">${item.channelName }</option>
                       		</#list>
                        </select>
                    </td>
         <td>
         	&nbsp;
         </td>
         <td>
                        <button type="button" onclick="exportExcelActivityStat()">导出</button>
                    </td>      
        
                </tr>
                <tr>
                <td align="right">
                       商品名称：
                    </td>
                    <td>
                        <select  class="combox" name="productId" id="sel_pro">
                        	<option value="">全部</option>
                           <#list proList as item>
                            <option <#if item.id == mileOrderSqo.productId>selected="selected"</#if> value="${item.id }">${item.prodName }</option>
                       		</#list>
                    
                        </select>
                    </td>  
                    <td  align="right">
                        订单状态：
                    </td>
                    <td>
                        <select name="status">
                            <option value="">全部</option>
                            <option <#if mileOrderSqo.status == 0>selected="selected"</#if> value="0">已付款</option>
                            <option <#if mileOrderSqo.status == 2>selected="selected"</#if> value="2">确认中</option>
                            <option <#if mileOrderSqo.status == 6>selected="selected"</#if> value="6">处理中</option>
                       		<option <#if mileOrderSqo.status == 3>selected="selected"</#if> value="3">已拒绝</option>
                       		<option <#if mileOrderSqo.status == -1>selected="selected"</#if> value="-1">订单取消</option>
                       		<option <#if mileOrderSqo.status == 4>selected="selected"</#if> value="4">订单成功</option>
                       		<option <#if mileOrderSqo.status == 5>selected="selected"</#if> value="5">订单失败</option>
                        </select>
                    </td>
                    <td align="right">
                      订单生成时间：
                    </td>
                    <td colspan="2">
                        <input type="text" name="strImportDate" value="${mileOrderSqo.strImportDate! }" class="date textInput readonly" mindate="2000-01-15" maxdate="2216-12-15" readonly="true" placeholder="开始时间">
                        -
                        <input type="text" name="endImportDate" value="${mileOrderSqo.endImportDate! }" class="date textInput readonly" mindate="2000-01-15" maxdate="2216-12-15" readonly="true" placeholder="结束时间">
                    </td>
                   
                     <td>
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
            <li><span style="padding: 0">查询结果</span>
            </li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
            <th width="10%">订单编号</th>
            <th width="5%">商品名称</th>
            <th width="5%">渠道商</th>
            <th width="7%">商户公司</th>
            <th width="8%">订单生成时间</th>
            <th width="7%">会员账号</th>
            <th width="4%">会员姓名</th>
            <th width="4%">数量</th>
            <th width="4%">单价(积分)</th>
            <th width="4%">消耗积分</th>
            <th width="4%">审核人</th>
            <th width="7%">审核时间</th>
            <th width="8%">通过理由</th>
            <th width="4%">确认人</th>
            <th width="7%">确认时间</th>
            <th width="7%">拒绝理由</th>
            <th width="5%">订单状态</th>
        </tr>
        </thead>
        <tbody>
     	<#list pagination.list as item>
        <tr>
            <td>
         		${item.flowCode!}
            </td>
            <td>
               ${item.productName!''}
            </td>
            <td>
         		 ${item.channelName!''}
            </td>
            <td>
            	${item.merName!''}
            </td>
            <td>
         		${(item.importDate?string("yyyy-MM-dd HH:mm"))!''}
            </td>
            <td>
          		${item.csairCard}
            </td>
            <td>
          		${item.csairName}
            </td>
            <td>
          		${item.num}
            </td>
            <td>
              ${item.unitPrice}
            </td>
            <td>
            	${item.count}
            </td>
            <td>
              <#if item.status == -1>
              --
            	<#else>
              ${item.aduitPerson!'系统'}
              </#if>
            </td>
            <td>
            <#if item.status == -1>
            	--
            <#else>
              ${(item.checkDate?string("yyyy-MM-dd HH:mm"))!''}
            </#if>
            </td>
            <td title="${item.reason}">
               <#if item.reason?length gt 10>${item.reason?substring(0,10)}..<#else>${item.reason}</#if>
            </td>
            <td>
              ${item.confirmPerson!''}
            </td>
            <td>
              ${(item.confirmDate?string("yyyy-MM-dd HH:mm"))!''}
            </td>
            <td title="${item.refuseReason}">
               <#if item.refuseReason?length gt 10>${item.refuseReason?substring(0,10)}..<#else>${item.refuseReason}</#if>
            </td>
            <td>
              ${item.statusName}
            </td>
        </tr>
       </#list>
        </tbody>
    </table>

</div>
<script type="text/javascript">
<!--
/**
 * 导出
 */
function exportExcelActivityStat(){
	var parmStr = $("#pagerForm",navTab.getCurrentPanel()).serialize();
	var url = "${contextPath}/orderManagement/export?"+parmStr;
	window.location.href=url;
}
//-->
</script>
