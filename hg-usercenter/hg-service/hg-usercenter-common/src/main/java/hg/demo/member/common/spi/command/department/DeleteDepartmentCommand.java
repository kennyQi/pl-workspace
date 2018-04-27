package hg.demo.member.common.spi.command.department;

import hg.framework.common.base.BaseSPICommand;

/**
 * @author zhurz
 */
@SuppressWarnings("serial")
public class DeleteDepartmentCommand extends BaseSPICommand {

	/**
	 * 部门ID
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
