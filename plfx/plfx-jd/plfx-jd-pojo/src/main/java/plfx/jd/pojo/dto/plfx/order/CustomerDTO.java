package plfx.jd.pojo.dto.plfx.order;

import plfx.jd.pojo.dto.ylclient.order.BaseJDDTO;


public class CustomerDTO extends BaseJDDTO{
	private static final long serialVersionUID = -6562534908710207566L;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * Email
	 */
	private String email;

	/**
	 * 手机
	 */
	private String mobile;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 性别
	 */
	private Integer gender;

	/**
	 * 证件类型
	 */
	private Integer idType;

	/**
	 * 证件号码
	 */
	private String idNo;

	/**
	 * 国籍
	 */
	private String nationality;

	/**
	 * 酒店确认号
	 */
	private String confirmationNumber;

	private Integer type;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
