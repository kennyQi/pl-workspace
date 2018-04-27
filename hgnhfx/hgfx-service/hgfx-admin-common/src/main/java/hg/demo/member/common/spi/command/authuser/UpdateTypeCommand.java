package hg.demo.member.common.spi.command.authuser;

import hg.framework.common.base.BaseSPICommand;

/**
 * Created by admin on 2016/5/30.
 */
public class UpdateTypeCommand extends BaseSPICommand {
    /**
     * id
     */
    private String id;
    /**
     * 状态
     */
    private Short enable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Short getEnable() {
        return enable;
    }

    public void setEnable(Short enable) {
        this.enable = enable;
    }
}
