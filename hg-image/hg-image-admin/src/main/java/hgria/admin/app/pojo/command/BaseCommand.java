package hgria.admin.app.pojo.command;

import hgria.admin.app.pojo.BaseParam;

import com.alibaba.fastjson.JSON;

/**
 * 命令基类
 *
 * @author yuxx
 */
@SuppressWarnings("serial")
public abstract class BaseCommand extends BaseParam {

    /**
     * 命令id
     */
    private String commandId;

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public String getCommandId() {
        return commandId;
    }

    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }



}
