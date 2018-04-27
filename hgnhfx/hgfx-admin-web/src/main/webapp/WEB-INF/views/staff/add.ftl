<#assign contextPath=springMacroRequestContext.getContextPath() />

<div class="pageContent">

    <form method="post" action="${contextPath}/staff/save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
        <div class="pageFormContent" layoutH="58">
            <div class="unit">
                <label>操作员姓名：</label>
                <input type="text" name="realName" size="64" maxlength="64" class="required"/>
            </div>
            <div class="unit">
                <label>登录账户：</label>
                <input type="text" name="loginName" size="64" class="required" minlength="5" maxlength="64"/>
            </div>
            <div class="unit">
                <label>登录密码：</label>
                <input type="password" name="loginPwd" class="required pwd alphanumeric" />
            </div>
            <div class="unit">
                <label>操作员角色：</label>
                <select class="combox" name="roleIds">
                <#list authRoleList as userRole>
                    <option value="${userRole.id!?html}">${userRole.displayName!?html}</option>
                </#list>
                </select>
            </div>
            <div class="unit">
                <label>联系邮箱：</label>
                <input type="text" name="email" class="required email"  placeholder="请输入您的电子邮箱" />
            </div>
            <div class="unit">
                <label>联系电话：</label>
                <input type="text" name="mobile" class="mobile" maxlength="11" placeholder="请输入您联系电话" />
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

