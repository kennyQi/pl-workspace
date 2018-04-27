<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="pageHeader">

    <form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"
          action="${contextPath}/orderManagement/pendingAuditList" method="get">

        <input type="hidden" name="pageNum" value="${pagination.pageNo?c}" />
        <input type="hidden" name="numPerPage" value="${pagination.pageSize?c}" />

        <div class="searchBar">
            <table class="searchContent" style="width: 100%;">
                <tr>
                    <td align="right">
                       会员账号：
                    </td>
                    <td>
                        <input type="text" name="csairCard" value="${mileOrderSqo.csairCard! }" />
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
                        <select  class="combox" name="channelId" ref="sel_pro_p" refUrl="${contextPath}/orderManagement/proJsonList?value={value}">
                        	<option value="">全部</option>
                        	<#list channelList as item>
                            <option <#if item.id == mileOrderSqo.channelId>selected="selected"</#if> value="${item.id }">${item.channelName }</option>
                       		</#list>
                        </select>
                    </td>
         <td colspan="2">
         	&nbsp;
         </td>     
        
                </tr>
                <tr>
                <td align="right">
                       商品名称：
                    </td>
                    <td>
                         <select  class="combox" name="productId" id="sel_pro_p">
                        	<option value="">全部</option>
                           <#list proList as item>
                            <option <#if item.id == mileOrderSqo.productId>selected="selected"</#if> value="${item.id }">${item.prodName }</option>
                       		</#list>
                    
                        </select>
                    </td>  
                    <td align="right">
                      订单生成时间：
                    </td>
                    <td >
                        <input type="text" name="strImportDate" value="${mileOrderSqo.strImportDate! }" class="date textInput readonly" mindate="2000-01-15" maxdate="2216-12-15" readonly="true" placeholder="开始时间">
                        -
                        <input type="text" name="endImportDate" value="${mileOrderSqo.endImportDate! }" class="date textInput readonly" mindate="2000-01-15" maxdate="2216-12-15" readonly="true" placeholder="结束时间">
                    </td>
                    <td >&nbsp;</td>
                      <td>
                        <button type="submit">查询</button>
                    </td>
                   <td colspan="2">&nbsp;</td>
                   
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
             <li class="line">line</li>
             <li><a rel="ids"
                   href="javascript:void(0);" onclick="checksubmit()" class="add"><span>批量通过</span>
            </li>
            <li><a title="确定要批量拒绝吗?" target="selectedTodo" rel="ids"
                   href="${contextPath}/orderManagement/refuseMany" class="delete"><span>批量拒绝</span>
            </a>
            </li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
        <th width="4%"><input type="checkbox" group="ids"
                                  class="checkboxCtrl" title="全选">全选
            </th>
            <th width="13%">订单编号</th>
            <th width="10%">商品名称</th>
            <th width="10%">渠道商</th>
            <th width="10%">商户</th>
            <th width="9%">订单生成时间</th>
            <th width="10%">会员账号</th>
            <th width="5%">数量</th>
            <th width="4%">单价</th>
            <th width="18%">异常原因</th>
            <th width="6%">操作</th>
        </tr>
        </thead>
        <tbody>
     	<#list pagination.list as item>
        <tr>
        	<td>
                <input name="ids" value="${item.id}" type="checkbox">
            </td>
            <td>
         		${item.flowCode!}
            </td>
            <td>
               ${item.product.prodName!''}
            </td>
            <td>
              <#if (item.product.channel)??>
         		 ${item.product.channel.channelName!''}
         		</#if> 
            </td>
            <td>
          		${item.distributor.name!''}
            </td>
            <td>
          		${(item.importDate?string("yyyy-MM-dd HH:mm"))!''}
            </td>
            <td>
              	${item.csairCard}
            </td>
            <td>
              	${item.num}
            </td>
            <td>
              ${item.unitPrice}
            </td>
            <td title=" ${item.errorReason}">
              ${item.errorReason}
            </td>
            <td  class="a_button2" >
                <a title="通过" lookupgroup="orgLookup" mask="true" href="${contextPath}/orderManagement/toCheck?id=${item.id}"  style="color: #0066CC;">通过</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a title="确定拒绝？" target="ajaxTodo" href="${contextPath}/orderManagement/refuseOne?id=${item.id}"  style="color: #0066CC;">拒绝</a>&nbsp;&nbsp;&nbsp;&nbsp;
                
            </td>
        </tr>
       </#list>
        </tbody>
    </table>

<#include "/public/pagination/pagination.html">
</div>
<script>
function checksubmit(){
	var size = $("input[name='ids']:checked").length
	var para =""
	if(size==0){
		alertMsg.error("请选择信息");
		return;
	}
	for(var i=0;i<size;i++){
		para=para+"ids="+$("input[name='ids']:checked").eq(i).val()+"&"
	}
	$.pdialog.open('${contextPath}/orderManagement/toCheck?'+para, 'editDialog', '通过理由',{width:700,height:300,mask:true})
}
</script>