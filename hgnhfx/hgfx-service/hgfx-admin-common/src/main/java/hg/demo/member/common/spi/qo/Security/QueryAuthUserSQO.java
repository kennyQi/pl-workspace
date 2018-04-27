package hg.demo.member.common.spi.qo.Security;

import hg.framework.common.base.BaseSPIQO;

/**
 * Created by admin on 2016/5/30.
 */
public class QueryAuthUserSQO extends BaseSPIQO {
    /**
     * 登录名
     */
    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
