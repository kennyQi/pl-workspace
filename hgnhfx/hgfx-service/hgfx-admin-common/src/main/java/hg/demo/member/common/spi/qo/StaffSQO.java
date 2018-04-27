package hg.demo.member.common.spi.qo;

import hg.framework.common.base.BaseSPIQO;

import java.util.Date;

/**
 * Created by admin on 2016/5/20.
 */
@SuppressWarnings("serial")
public class StaffSQO extends BaseSPIQO {
    /**
     * id
     */
    private String id;
    /**
     * 员工登录名
     */
    private String loginName;
    /**
     * 是否查询角色
     */
    private boolean queryAuthRole;
    /**
     * 员工真实姓名
     */
    private String realName;
    /**
     * 员工手机号
     */
    private String mobile;
    /**
     * 注册时期区间（开始）
     */
    private Date createDateBegin;
    /**
     * 注册日期区间（结束）
     */
    private Date createDateEnd;

    /**
     * 状态
     */
    private Short enable;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String tel;

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

    public boolean isQueryAuthRole() {
        return queryAuthRole;
    }

    public void setQueryAuthRole(boolean queryAuthRole) {
        this.queryAuthRole = queryAuthRole;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreateDateBegin() {
        return createDateBegin;
    }

    public void setCreateDateBegin(Date createDateBegin) {
        this.createDateBegin = createDateBegin;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
