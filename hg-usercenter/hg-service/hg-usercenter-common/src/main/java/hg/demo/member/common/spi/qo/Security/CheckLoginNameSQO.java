package hg.demo.member.common.spi.qo.Security;

import hg.framework.common.base.BaseSPIQO;

/**
 * Created by admin on 2016/5/30.
 */
public class CheckLoginNameSQO extends BaseSPIQO {
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 显示名
     * @return
     */
    private String disName;
    
    
    
    public String getDisName() {
		return disName;
	}

	public void setDisName(String disName) {
		this.disName = disName;
	}

	public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
