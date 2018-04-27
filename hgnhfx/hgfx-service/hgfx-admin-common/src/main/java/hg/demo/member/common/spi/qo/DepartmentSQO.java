package hg.demo.member.common.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * @author zhurz
 */
@SuppressWarnings("serial")
public class DepartmentSQO extends BaseSPIQO {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 经理成员ID
	 */
	private String managerId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
}
