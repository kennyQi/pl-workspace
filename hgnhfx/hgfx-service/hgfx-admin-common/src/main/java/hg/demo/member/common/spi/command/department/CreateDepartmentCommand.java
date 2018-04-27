package hg.demo.member.common.spi.command.department;

import hg.framework.common.base.BaseSPICommand;

/**
 * @author zhurz
 */
@SuppressWarnings("serial")
public class CreateDepartmentCommand extends BaseSPICommand {

	/**
	 * 部门名称
	 */
	private String name;

	/**
	 * 部门主管ID
	 */
	private String managerMemberId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManagerMemberId() {
		return managerMemberId;
	}

	public void setManagerMemberId(String managerMemberId) {
		this.managerMemberId = managerMemberId;
	}
}
