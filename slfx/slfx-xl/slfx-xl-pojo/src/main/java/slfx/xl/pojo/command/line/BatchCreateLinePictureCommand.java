package slfx.xl.pojo.command.line;

import java.util.List;
import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建线路图片
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenyanshou
 * @创建时间：2014年12月18日上午9:59:23
 * @版本：V1.0
 */
public class BatchCreateLinePictureCommand extends BaseCommand{ 
	private static final long serialVersionUID = 1L;

	/**
	 * 创建线路图片指令
	 */
	private List<CreateLinePictureCommand> commands;
	
	public BatchCreateLinePictureCommand(){
		super();
	}
	public BatchCreateLinePictureCommand(List<CreateLinePictureCommand> commands) {
		super();
		setCommands(commands);
	}
	
	public List<CreateLinePictureCommand> getCommands() {
		return commands;
	}
	public void setCommands(List<CreateLinePictureCommand> commands) {
		this.commands = (commands == null || commands.size() < 1)?null:commands;
	}
}