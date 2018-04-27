package hg.demo.web.common;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 登录用户信息
 * Created by admin on 2016/5/27.
 */
public class UserInfo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String loginName;
    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 帐号状态 1启用 0禁用
     */
    private Short enable;

    /**
     * 帐号角色集合
     */
    private Set<String> authRoleSet = new LinkedHashSet<String>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public Set<String> getAuthRoleSet() {
        return authRoleSet;
    }

    public void setAuthRoleSet(Set<String> authRoleSet) {
        this.authRoleSet = authRoleSet;
    }
}
