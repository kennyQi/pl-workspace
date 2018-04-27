<#assign contextPath=springMacroRequestContext.getContextPath() />
<div class="pageContent">

    <form method="post" action="${contextPath}/staff/modifypwdpost" class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone)">

        <div class="pageFormContent" layoutH="58">
            <input type="hidden" name="id" value="${staffid}" />
            <div class="unit">
                <label>登录密码：</label>
                <input type="password" name="loginPwd" class="required pwd alphanumeric" value="" />
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>

</div>

