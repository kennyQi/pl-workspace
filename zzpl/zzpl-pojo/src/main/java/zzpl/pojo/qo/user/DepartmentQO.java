package zzpl.pojo.qo.user;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class DepartmentQO extends BaseQo{

	/**
	 * 公司ID
	 */
	private String companyID;
	/**
	 * 部门ID
	 */
	private String departmentID;
	/**
	 * 部门名称
	 */
	private String departmentName;
	
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
}
