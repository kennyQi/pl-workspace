package hsl.pojo.dto.company;

import hsl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class CompanySearchDTO extends BaseDTO{
	/**
	 * 组织名称
	 */
	private String companyName;
	/**
	 * 组织id
	 */
	private String companyId;
	/**
	 * 部门名称
	 */
	private String departName;
	/**
	 * 部门id
	 */
	private String departId;
	/**
	 * 成员数量
	 */
	private Integer memberCount;
	/**
	 * 成员名称
	 */
	private String memberName;
	/**
	 * 成员id
	 */
	private String memberId;
	/**
	 * 成员职务
	 */
	private String job;
	/**
	 * 成员电话
	 */
	private String mobile;
	/**
	 * 成员身份证
	 */
	private String certificateID;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public Integer getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCertificateID() {
		return certificateID;
	}
	public void setCertificateID(String certificateID) {
		this.certificateID = certificateID;
	}
	
}
