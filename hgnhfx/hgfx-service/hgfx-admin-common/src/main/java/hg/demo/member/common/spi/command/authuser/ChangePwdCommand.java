package hg.demo.member.common.spi.command.authuser;

import hg.framework.common.base.BaseSPICommand;

/**
 * Created by admin on 2016/5/30.
 */
public class ChangePwdCommand extends BaseSPICommand {
    /**
     * id
     */
    private String id;
    /**
     * 密码
     */
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
