package hg.demo.member.common.spi.command.authuser;

import hg.framework.common.base.BaseSPICommand;

import java.util.Set;

/**
 * Created by admin on 2016/5/20.
 */
public class ModifyAuthUserCommand extends BaseSPICommand {
    /**
     * 用户id
     */
    private String id;
    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String passwd;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 帐号状态 1启用 0禁用
     */
    private Short enable;

    /**
     * 角色id
     */
    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Short getEnable() {
        return enable;
    }

    public void setEnable(Short enable) {
        this.enable = enable;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
