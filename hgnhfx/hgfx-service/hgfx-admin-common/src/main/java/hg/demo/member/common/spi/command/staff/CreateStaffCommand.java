package hg.demo.member.common.spi.command.staff;

import hg.demo.member.common.domain.model.AuthUser;
import hg.framework.common.base.BaseSPICommand;

import java.util.Date;

/**
 * Created by admin on 2016/5/20.
 */
public class CreateStaffCommand extends BaseSPICommand {
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String loginPwd;
    /**
     * 角色IDS
     */
    private String roleIds;
    /**
     * 用户电子邮箱地址
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 状态
     */
    private Short enable;

    /**
     * 电话号码
     */
    private String tel;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Short getEnable() {
        return enable;
    }

    public void setEnable(Short enable) {
        this.enable = enable;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
