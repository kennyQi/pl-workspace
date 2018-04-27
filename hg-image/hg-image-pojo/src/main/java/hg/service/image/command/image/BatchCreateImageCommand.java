package hg.service.image.command.image;

import java.util.List;
import hg.service.image.base.BaseCommand;

/**
 * @类功能说明：创建图片(同时创建默认 ImageSpec)，根据规格集合剪裁图片。
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-10-30上午11:17:56
 */
public class BatchCreateImageCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片command
	 */
	private List<CreateImageCommand> commands;

	public BatchCreateImageCommand(){}
	public BatchCreateImageCommand(List<CreateImageCommand> commands){
		super();
		this.setCommands(commands);
	}
	
	public List<CreateImageCommand> getCommands() {
		return commands;
	}
	public void setCommands(List<CreateImageCommand> commands) {
		this.commands = (commands == null || commands.size() < 1)?null:commands;
	}
}