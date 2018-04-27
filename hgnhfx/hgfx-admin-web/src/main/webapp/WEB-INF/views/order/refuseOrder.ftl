<#assign contextPath=springMacroRequestContext.getContextPath() />

<div class="pageContent">

    <form method="post" action="${contextPath}/orderManagement/checkRefuse" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
        <input type="hidden" name="id" value="${id}">
        <div class="pageFormContent" layoutH="58">
            <div class="unit" align="center">
                <label>拒绝理由：</label>
                <textarea name="refuseReason" cols="65" rows="8" class="required" maxlength="20"></textarea>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">确认拒绝</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>
</div>

