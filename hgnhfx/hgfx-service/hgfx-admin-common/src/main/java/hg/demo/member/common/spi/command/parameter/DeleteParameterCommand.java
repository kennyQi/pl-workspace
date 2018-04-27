package hg.demo.member.common.spi.command.parameter;

import hg.framework.common.base.BaseSPICommand;

/**
 * Created by admin on 2016/5/20.
 */
@SuppressWarnings("serial")
public class DeleteParameterCommand extends BaseSPICommand {
	
	/**
	 * 参数id
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
