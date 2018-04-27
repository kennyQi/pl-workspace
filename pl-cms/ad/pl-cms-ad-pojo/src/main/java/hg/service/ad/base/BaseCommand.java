package hg.service.ad.base;

/**
 * 命令基类
 * @author yuxx
 */
@SuppressWarnings("serial")
public abstract class BaseCommand extends BaseParam {
    /**
     * 命令id
     */
    private String commandId;

    public String getCommandId() {
        return commandId;
    }
    public void setCommandId(String commandId) {
    	this.commandId = commandId == null ? null : commandId.trim();
    }
}