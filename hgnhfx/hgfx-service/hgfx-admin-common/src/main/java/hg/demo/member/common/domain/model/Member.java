package hg.demo.member.common.domain.model;

import hg.demo.member.common.domain.model.def.M;
import hg.framework.common.base.BaseStringIdModel;

import javax.persistence.*;

/**
 * 成员
 *
 * @author zhurz
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX + "OPERATOR")
public class Member extends BaseStringIdModel {

	/**
	 * 姓名
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 手机号
	 */
	@Column(name = "MOBILE", length = 64)
	private String mobile;

	/**
	 * 职位
	 */
	@Column(name = "JOB", length = 32)
	private String job;

	/**
	 * 所属部门
	 */
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;

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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
