package hg.demo.member.common.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * @author zhurz
 */
@SuppressWarnings("serial")
public class MemberSQO extends BaseSPIQO {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 部门ID
	 */
	private String departmentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}
