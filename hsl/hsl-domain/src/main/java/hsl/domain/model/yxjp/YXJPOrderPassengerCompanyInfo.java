package hsl.domain.model.yxjp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 员工在公司的信息
 *
 * @author zhurz
 * @since 1.7
 */
@Embeddable
@SuppressWarnings("serial")
public class YXJPOrderPassengerCompanyInfo implements Serializable {

	/**
	 * 组织ID
	 */
	@Column(name = "COMPANY_ID", length = 64)
	private String companyId;

	/**
	 * 组织名称
	 */
	@Column(name = "COMPANY_NAME", length = 64)
	private String companyName;

	/**
	 * 部门ID
	 */
	@Column(name = "DEPARTMENT_ID", length = 64)
	private String departmentId;

	/**
	 * 部门名称
	 */
	@Column(name = "DEPARTMENT_NAME", length = 64)
	private String departmentName;

	/**
	 * 成员ID
	 */
	@Column(name = "MEMEBER_ID", length = 64)
	private String memeberId;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMemeberId() {
		return memeberId;
	}

	public void setMemeberId(String memeberId) {
		this.memeberId = memeberId;
	}
}
