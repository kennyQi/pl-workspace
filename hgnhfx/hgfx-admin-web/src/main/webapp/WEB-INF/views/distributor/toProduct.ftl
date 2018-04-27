<#assign contextPath=springMacroRequestContext.getContextPath() />

<div class="pageContent">
	<div style="border-bottom:1px solid #eee;height:30px;text-center">账号	${loginName!?html}</div>
	<#if (productInUseList?size > 0)>
		<table class="table" width="100%" layoutH="138">
	        <thead>
	        <tr>
	            <th width="40%">使用中的商品</th>
	            <th width="40%">状态</th>
	            <th width="20%">操作</th>
	        </tr>
	        </thead>
	        
	        <tbody>
	     		
	     		<#list productInUseList as pl>
	     			<#if (pl.status == 0)>
	     				<#assign statusStr = "试用中" />
	     			<#elseif (pl.status == 1)>
	     				<#assign statusStr = "申请中" />
	     			<#elseif (pl.status == 2)>
	     				<#assign statusStr = "使用中 " />
	     			<#elseif (pl.status == 3)>
	     				<#assign statusStr = "已拒绝" />
	     			<#elseif (pl.status == 4)>
	     				<#assign statusStr = "停用中 " />
	     			<#else>
	     				<#assign statusStr = "未知状态 " />
	     			</#if>
	     			<tr>
			            <td width="40%">${pl.product.prodName!?html}</td>
			            <td width="40%">${statusStr}</td>
			            <td width="20%">
			            	<#if (pl.status == 2)>
			     				<a title="停用"  href="javascript:pro_act('${pl.id}',4,'${pl.distributor.id}')"  style="color: #0066CC;">停用</a>
			     			<#elseif (pl.status == 4)>
			     				<a title="启用"   href="javascript:pro_act('${pl.id}',2,'${pl.distributor.id}')"  style="color: #0066CC;">启用</a>
			     			</#if>
			            </td>
			        </tr>
                </#list>
	       		
	        </tbody>
	    </table>
	</#if>
	
	<form method="post" action="${contextPath}/distributorManagement/addProductInUse" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	    <input type="hidden" name="distributorId" value="${distributorId!?html}"/>
	    <div>
	    	<select class="combox" name="prodId" id="prodId">
                <option value="">未添加的商品列表</option>
                <#list productNotUseList as pnl>
                	<option value="${pnl.id!?html}">${pnl.prodName!?html}</option>
                </#list>
            </select>
            <div class="buttonActive"><div class="buttonContent"><button type="button" onclick="addProductInUse()">添加</button></div></div>
	    </div>
    </form>
</div>
<script>
    
    function pro_act(id,status,distributorId){
    	var statusStr = "停用";
    	if(status == 2){
    		statusStr = "启用";
    	}
		alertMsg.confirm(statusStr, {
		
            okCall: function(){

	            $.ajax({
		    		type:'GET',
		    		url:"${contextPath}/distributorManagement/productAct?productInUseId="+id+"&status="+status,
		    		dataType:'json',
		    		success: function(data){
		    			if(data == "200"){
		    				alertMsg.correct('操作成功！')
		    				$.pdialog.reload("${contextPath}/distributorManagement/toProduct?distributorId="+distributorId+"&loginName=${loginName!?html}","",$.pdialog.getCurrent())
		    			} else {
		    				alertMsg.correct('操作失败！')
		    			}
		    		}
		    	});

            }
		
		});
    	
    }
    
    function addProductInUse(){
    	var prodId = $("#prodId").val();
		alertMsg.confirm("确定添加？", {
		
            okCall: function(){

	            $.ajax({
		    		type:'POST',
		    		url:"${contextPath}/distributorManagement/addProductInUse",
		    		data:{"prodId":prodId,"distributorId":"${distributorId!?html}"},
		    		dataType:'json',
		    		success: function(data){
		    			if(data == "200"){
		    				alertMsg.correct('操作成功！')
		    				navTab.reloadFlag("distributorList")
		    				$.pdialog.reload("${contextPath}/distributorManagement/toProduct?distributorId=${distributorId!?html}&loginName=${loginName!?html}","",$.pdialog.getCurrent())
		    			} else if(data == "-1") {
		    				alertMsg.correct('请选择要添加的商品！')
		    			} else {
		    				alertMsg.correct('操作失败！')
		    			}
		    		}
		    	});

            }
		
		});
    }
</script>

