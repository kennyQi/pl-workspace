package hg.demo.member.common.domain.model;

import hg.framework.common.base.BaseStringIdModel;

import javax.persistence.*;

/**
 * 用户信息
 * Created by admin on 2016/5/19.
 */
@Entity
@Table(name = "SYS_STAFF")
public class Staff extends BaseStringIdModel{
    private static final long serialVersionUID = 1L;
    /**
     * 员工权限帐号
     */

    @OneToOne(fetch = FetchType.EAGER, optional = false,cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AuthUser authUser;

    /**
     * 基础信息
     */
    private StaffBaseInfo staffBaseInfo;

    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public StaffBaseInfo getStaffBaseInfo() {
        return staffBaseInfo;
    }

    public void setStaffBaseInfo(StaffBaseInfo staffBaseInfo) {
        this.staffBaseInfo = staffBaseInfo;
    }
}
