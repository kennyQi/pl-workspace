<#assign contextPath=springMacroRequestContext.getContextPath() />

<div class="pageContent">

    <form method="post" action="${contextPath}/orderManagement/passOne" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
        <input type="hidden" name="id" value="${id}">
        <div class="pageFormContent" layoutH="58">
            <div class="unit" align="center">
                <label>通过理由：</label>
                <textarea name="reason" cols="48" rows="8" class="required"></textarea>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">确认通过</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>
</div>

