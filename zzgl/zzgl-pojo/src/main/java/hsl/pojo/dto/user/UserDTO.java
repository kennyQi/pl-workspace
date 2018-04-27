package hsl.pojo.dto.user;

import hsl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class UserDTO extends BaseDTO {
	/**
	 * 登录用户的类型，登录用户为企业用户
	 */
	public final static String USER_TYPE_OF_COMPANY = "2";
	
	/**
	 *登录用户的类型，登录用户为个人用户
	 */
	public final static String USER_TYPE_OF_PERSON = "1";
	/**
	 * 用户手机号
	 */
	private String mobile;
	
	private UserAuthInfoDTO authInfo;

	private UserBaseInfoDTO baseInfo;

	private UserContactInfoDTO contactInfo;
	
	private UserStatusDTO status;
	/**
	 * 组织id
	 */
	private String companyId;
	
	/**
	 * 组织名称
	 */
	private String companyName;
	
	/**
	 * 部门id
	 */
	private String departmentId;
	
	/**
	 * 部门名称
	 */
	private String departmentName;
	private String name;
	/**
	 * 账户余额Id
	 */
	private String accountId;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public UserAuthInfoDTO getAuthInfo() {
		return authInfo;
	}

	public void setAuthInfo(UserAuthInfoDTO authInfo) {
		this.authInfo = authInfo;
	}

	public UserBaseInfoDTO getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(UserBaseInfoDTO baseInfo) {
		this.baseInfo = baseInfo;
	}

	public UserContactInfoDTO getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(UserContactInfoDTO contactInfo) {
		this.contactInfo = contactInfo;
	}

	public UserStatusDTO getStatus() {
		return status;
	}

	public void setStatus(UserStatusDTO status) {
		this.status = status;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	
	
}
