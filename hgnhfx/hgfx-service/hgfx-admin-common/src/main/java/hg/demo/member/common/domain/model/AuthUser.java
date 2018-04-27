package hg.demo.member.common.domain.model;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 用户简单表
 * Created by admin on 2016/5/19.
 */
@Entity
@Table(name = "AUTH_USER")
@DynamicUpdate
public class AuthUser extends BaseStringIdModel{
    private static final long serialVersionUID = 1L;
    /**
     * 登录名
     */
    @Column(name = "LOGIN_NAME", length = 64)
    private String loginName;

    /**
     * 密码
     */
    @Column(name = "PASSWD", length = 128)
    private String passwd;

    /**
     * 显示名称
     */
    @Column(name = "DISPLAY_NAME", length = 96)
    private String displayName;

    /**
     * 帐号状态 1启用 0禁用
     */
    @Column(name = "ENABLE")
    private Short enable;

    /**
     * 帐号角色集合
     */
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE,})
    @JoinTable(name = "AUTH_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<AuthRole> authRoleSet = new LinkedHashSet<AuthRole>();

    /**
     * 创建时间
     */
//    @Column(name = "CREATE_DATE", columnDefinition = "datetime")
    @Column(name = "CREATE_DATE", columnDefinition = O.DATE_COLUM)
    private Date createDate;

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

    public Set<AuthRole> getAuthRoleSet() {
        return authRoleSet;
    }

    public void setAuthRoleSet(Set<AuthRole> authRoleSet) {
        this.authRoleSet = authRoleSet;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
