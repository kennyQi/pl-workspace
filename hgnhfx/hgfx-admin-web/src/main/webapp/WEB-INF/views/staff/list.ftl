<#assign contextPath=springMacroRequestContext.getContextPath() />
<script type="text/javascript">
    function resetOperForm(){
        $("[name='roleId']")[0].selectedIndex=0;
        $("[name='enable']")[0].selectedIndex=0;
        $("[name='realName']").val('');
        $("[name='loginName']").val('');
        $("[name='mobile']").val('');
        $("[name='email']").val('');

    }
</script>
<div class="pageHeader">

    <form id="pagerForm" rel="pagerForm" onsubmit="return navTabSearch(this);"
          action="${contextPath}/staff/list" method="get">

        <input type="hidden" name="pageNum" value="${pagination.pageNo?c}" />
        <input type="hidden" name="numPerPage" value="${pagination.pageSize?c}" />

        <div class="searchBar">
            <table class="searchContent" style="width: 100%;">
                <tr>
                    <td>
                        操作员姓名：
                    </td>
                    <td>
                        <input type="text" name="realName" value="${staffSqo.realName!?html}" />
                    </td>
                    <td>
                        登录名：
                    </td>
                    <td>
                        <input type="text" name="loginName" value="${staffSqo.loginName!?html}" />
                    </td>
                    <td>
                        操作员角色：
                    </td>
                    <td>
                        <select name="roleId">
                            <option value="">所有角色</option>
                        <#list userRoleList as userRole>
                            <option value="${userRole.id!?html}" <#if userRole.id == staffSqo.roleId>selected="selected"</#if>>${userRole.displayName!?html}</option>
                        </#list>
                        </select>
                    </td>
                    <td>
                        <button type="submit">查询</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        状态：
                    </td>
                    <td>
                        <select name="enable">
                            <option value="">全部</option>
                        <#list userEnableList as userEnable>
                            <option value="${userEnable.key?c}" <#if userEnable.key == staffSqo.enable>selected="selected"</#if>>${userEnable.value!?html}</option>
                        </#list>
                        </select>
                    </td>
                    <td>
                        联系电话：
                    </td>
                    <td>
                        <input type="text" name="mobile" value="${staffSqo.mobile!?html}" />
                    </td>
                    <td>
                        联系邮箱：
                    </td>
                    <td>
                        <input type="text" name="email" value="${staffSqo.email!?html}" />
                    </td>
                    <td>
                        <button type="button" onclick="resetOperForm()">重置</button>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="${contextPath}/staff/addoredit"
                   lookupgroup="orgLookup" mask="true"><span>添加</span>
            </a>
            </li>
            <!-- <li><a title="确定要重置这些操作员的密码吗?" target="selectedTodo" rel="ids"
                   href="${contextPath}/staff/resetpwd" class="delete"><span>重置密码</span>
            </a>
            </li> -->
            <li class="line">line</li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
        <tr>
            <th width="10"><input type="checkbox" group="ids"
                                  class="checkboxCtrl" title="全选">
            </th>
            <th width="50">操作员姓名</th>
            <th width="50">操作员角色</th>
            <th width="10">添加时间</th>
            <th width="50">登录名</th>
            <th width="20">联系电话</th>
            <th width="30">联系邮箱</th>
            <th width="10">状态</th>
            <th width="60">操作</th>
        </tr>
        </thead>
        <tbody>
        <#list pagination.list as staff>
        <tr>
            <td>
                <input name="ids" value="${staff.staff.id}" type="checkbox">
            </td>
            <td>
            ${staff.staff.staffBaseInfo.realName!?html}
            </td>
            <td>
                <#list staff.staff.authUser.authRoleSet as role>
                ${role.displayName!?html}<#if role_index != 0>、</#if>
                </#list>
            </td>
            <td>${(staff.staff.authUser.createDate)!}</td>
            <td>
            ${staff.staff.authUser.loginName!?html}
            </td>
            <td>
            ${staff.staff.staffBaseInfo.mobile!?html}
            </td>
            <td>
            ${staff.staff.staffBaseInfo.email!?html}
            </td>
            <td>
                <#list userEnableList as userEnable>
						<#if userEnable.key == staff.staff.authUser.enable>${userEnable.value!?html}<#break></#if>
					</#list>
            </td>
            <td  class="a_button2" >
                <#if (staff.roleset??)&&(staff.roleset?size>0)&&!staff.roleset?seq_contains("admin")&&staff.staff.authUser.loginName != userinfo.loginName>
                    <a title="编辑" lookupgroup="orgLookup" mask="true" href="${contextPath}/staff/addoredit?id=${staff.staff.id}"  style="color: #0066CC;">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;

                    <a title="确定要删除此操作员吗" href="${contextPath}/staff/del?id=${staff.staff.id}"  target="ajaxTodo">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <#if staff.staff.authUser.enable==0>
                        <a title="确定要启用此操作员吗?" href="${contextPath}/staff/modifyenable?enable=1&id=${staff.staff.id}"  target="ajaxTodo">启用</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <#else>
                        <a title="确定要禁用此操作员吗?" href="${contextPath}/staff/modifyenable?enable=0&id=${staff.staff.id}"  target="ajaxTodo">禁用</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    </#if>
                    <a title="修改密码" lookupgroup="orgLookup" mask="true" href="${contextPath}/staff/modifypwd?id=${staff.staff.id}" >修改密码</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a title="确定要重置密码？" href="${contextPath}/staff/resetpwd?ids=${staff.staff.id}"  target="ajaxTodo">重置密码</a>
                </#if>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>

<#include "/public/pagination/pagination.html">
</div>
