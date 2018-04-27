package hg.dzpw.pojo.common;

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

	/**
	 * 来源命令
	 * （来源命令粒度大时派生成粒度小的命令去执行）
	 */
	private Object fromCommand;

	public String toJson() {
		return JSON.toJSONString(this);
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public Object getFromCommand() {
		return fromCommand;
	}

	public void setFromCommand(Object fromCommand) {
		this.fromCommand = fromCommand;
	}

}