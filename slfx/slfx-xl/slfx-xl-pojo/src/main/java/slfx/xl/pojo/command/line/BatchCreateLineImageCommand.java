package slfx.xl.pojo.command.line;

import java.util.List;
import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建图片
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenys
 * @创建时间：2014-12-22上午10:43:37
 */
public class BatchCreateLineImageCommand  extends BaseCommand{ 
	private static final long serialVersionUID = 1L;
	
	/**
	 * 创建图片指令
	 */
	private List<CreateLineImageCommand> commands;

	public BatchCreateLineImageCommand(){
		super();
	}
	public BatchCreateLineImageCommand(List<CreateLineImageCommand> commands) {
		super();
		setCommands(commands);
	}

	public List<CreateLineImageCommand> getCommands() {
		return commands;
	}
	public void setCommands(List<CreateLineImageCommand> commands) {
		this.commands = (commands == null || commands.size() < 1)?null:commands;
	}
}