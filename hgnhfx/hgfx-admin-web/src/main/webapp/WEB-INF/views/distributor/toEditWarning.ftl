<#assign contextPath=springMacroRequestContext.getContextPath() />

<div class="pageContent">

    <form method="post" action="${contextPath}/distributorManagement/modifyWarningValue" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
        <input type="hidden" name="reserveInfoId" value="${distributor.reserveInfo.id!?html}"/>
        <div class="pageFormContent" layoutH="58">
            <div class="unit">
                <label>账号：</label>
                 <label>${loginName!?html}</label>
            </div>
            <div class="unit">
                <label>预警积分：</label>
                 <label>${distributor.reserveInfo.warnValue!?html}积分</label>
            </div>
            <div class="unit">
                <label>修改预警积分为：</label>
                <input type="text" name="warnValue" size="64" class="required number" min="1" max="10000000"/>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">确认修改</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>
</div>

