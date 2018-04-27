package hg.demo.member.common.spi.command.authuser;

import hg.framework.common.base.BaseSPICommand;

/**
 * Created by admin on 2016/5/20.
 */
public class DeleteAuthUserCommand extends BaseSPICommand {
    /**
     * id
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
