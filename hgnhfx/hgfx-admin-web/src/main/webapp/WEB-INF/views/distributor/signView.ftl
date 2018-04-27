<#assign contextPath=springMacroRequestContext.getContextPath() />

<div class="pageContent">

        <div class="pageFormContent" layoutH="58">
            <div class="unit">
                <label>账号：</label>
                 <label>${user.loginName}</label>
            </div>
            <div class="unit">
                <label>公司名称：</label>
                 <label>${user.distributor.name}</label>
            </div>
            <div class="unit">
                <label>签名key：</label>
                <label>${user.distributor.signKey}</label>
            </div>
        </div>
</div>

