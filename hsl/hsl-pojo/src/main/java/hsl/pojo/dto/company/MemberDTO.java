package hsl.pojo.dto.company;

import hsl.pojo.dto.BaseDTO;


/**
 * 组织成员DTO
 * @author zhuxy
 *
 */
@SuppressWarnings("serial")
public class MemberDTO extends BaseDTO{
	/**
	 * 成员名称
	 */
	private String name;
	/**
	 * 职务
	 */
	private String job;
	/**
	 * 手机号
	 */
	private String mobilePhone;
	/**
	 * 身份证ID
	 */
	private String certificateID;
	/**
	 * 部门
	 */
	private DepartmentDTO Department;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getCertificateID() {
		return certificateID;
	}
	public void setCertificateID(String certificateID) {
		this.certificateID = certificateID;
	}
	public DepartmentDTO getDepartment() {
		return Department;
	}
	public void setDepartment(DepartmentDTO department) {
		Department = department;
	}
	

}
