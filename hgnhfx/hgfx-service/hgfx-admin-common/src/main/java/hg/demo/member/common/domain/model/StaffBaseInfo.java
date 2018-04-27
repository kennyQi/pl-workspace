package hg.demo.member.common.domain.model;

import hg.framework.common.base.BaseStringIdModel;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by admin on 2016/5/23.
 */
@Embeddable
public class StaffBaseInfo implements Serializable {
    /**
     * 真实姓名
     */
    @Column(name = "REAL_NAME", length = 64)
    private String realName;

    /**
     * 用户电子邮箱地址
     */
    @Column(name = "EMAIL", length = 128)
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "MOBILE", length = 64)
    private String mobile;

    /**
     * 电话号码
     */
    @Column(name = "TEL", length = 64)
    private String tel;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
