package hg.demo.member.common.spi.command.department;

import hg.framework.common.base.BaseSPICommand;

/**
 * @author zhurz
 */
@SuppressWarnings("serial")
public class ModifyDepartmentCommand extends BaseSPICommand {

	/**
	 * 部门ID
	 */
	private String id;

	/**
	 * 部门名称
	 */
	private String name;

	/**
	 * 部门主管ID
	 */
	private String managerOperatorId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManagerOperatorId() {
		return managerOperatorId;
	}

	public void setManagerOperatorId(String managerOperatorId) {
		this.managerOperatorId = managerOperatorId;
	}
}
