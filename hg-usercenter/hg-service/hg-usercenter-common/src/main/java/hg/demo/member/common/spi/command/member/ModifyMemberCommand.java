package hg.demo.member.common.spi.command.member;

import hg.framework.common.base.BaseSPICommand;

/**
 * @author zhurz
 */
@SuppressWarnings("serial")
public class ModifyMemberCommand extends BaseSPICommand {

	/**
	 * 成员ID
	 */
	private String id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 职位
	 */
	private String job;

	/**
	 * 所属部门
	 */
	private String departmentId;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}
