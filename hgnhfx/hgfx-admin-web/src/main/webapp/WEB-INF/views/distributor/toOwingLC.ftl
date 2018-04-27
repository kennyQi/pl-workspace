<#assign contextPath=springMacroRequestContext.getContextPath() />

<div class="pageContent">

    <form method="post" action="${contextPath}/distributorManagement/createArrearsRecord" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
        <input type="hidden" value="${distributorUser.distributor.id}" name="distributorID"/> 
        <div class="pageFormContent" layoutH="58">
            <div class="unit">
                <label>账号：</label>
                 <label>${distributorUser.loginName}</label>
            </div>
            <div class="unit">
                <label>原可欠费积分：</label>
                <label>${reserveInfo.arrearsAmount}积分</label>
                 <input type="hidden" value="${reserveInfo.arrearsAmount?c}" name="preArrears"/>
            </div>
            <div class="unit">
                <label>修改可欠费积分：</label>
                <input type="text" name="applyArrears" size="16" class="required zhengzhengshu"/>
               <span class="info"> &nbsp;&nbsp;&nbsp;1-10000000的正整数</span>
            </div>
            <div class="unit">
                <label>理由：</label>
                <textarea name="reason" cols="34" rows="4" class="required reasonArea"></textarea>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">确认申请</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>
</div>



