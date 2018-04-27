<#assign contextPath=springMacroRequestContext.getContextPath() />
<script type="text/javascript">
    function validateSubmit(e,cb){
        if($("#p1").val() == '' || $("#p2").val()==''){
            alertMsg.error("密码不能为空");
            return false;
        }
        if($("#p1").val() != $("#p2").val()){
            alertMsg.error("两次输入的密码不一致");
            return false;
        }
        return validateCallback(e, cb);
    }
</script>
<div class="pageContent">
    <form method="post" id="myForm" action="${contextPath}/staff/changepwdpost" class="pageForm required-validate nowrap" onsubmit="return validateSubmit(this, navTabAjaxDone)" >

        <div class="pageFormContent bill" layoutH="56">
            <dl>
                <dt>原密码：</dt>
                <dd>
                    <input type="password" class="required pwd alphanumeric" name="oldPwd" />
                </dd>
            </dl>
            <dl>
                <dt>新密码：</dt>
                <dd>
                    <input id="w_validation_pwd" type="password" class="required pwd alphanumeric"  name="pwd1112" />
                </dd>
            </dl>
            <dl>
                <dt>确定密码：</dt>
                <dd>
                    <input type="password" class="required pwd alphanumeric" name="pwd" equalto="#w_validation_pwd"  />
                </dd>
            </dl>

            <h6></h6>



        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive">
                    <div class="buttonContent">
                        <button type="submit" >修改密码</button>
                    </div>
                </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div></li>
            </ul>
        </div>
    </form>
</div>
<script type="text/javascript">
</script>
