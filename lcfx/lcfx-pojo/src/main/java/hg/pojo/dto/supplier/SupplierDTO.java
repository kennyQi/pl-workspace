package hg.pojo.dto.supplier;

import java.util.Date;

public class SupplierDTO {

	/**
	 * 供应商名称
	 */
	private String name;

	
	/**
	 * 供应商类型
	 */
	private String typeName;
	
	/**
	 * 开户银行
	 */
	private String bank;
	
	/**
	 * 银行帐号
	 */
	private String account;
	

	/**
	 * 公司网站
	 */
	private String URL;
	
	/**
	 * 法人名字
	 */
	private  String legalPerson;
	
	/**
	 * 税务号
	 */
	private String tax;

	/**
	 * 注册资本
	 */
	private Double registeredCapital;
	
	
	/**
	 * 成立时间
	 */
	private Date establishDate;
	
	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * 联系地址
	 */
	private String address;
	
	/**
	 * 邮编
	 */
	private String postCode;
	
	/**
	 * 联系邮箱
	 */
	private String email;
	
	/**
	 * 传真号
	 */
	private String fax;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private String supplierCode;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public Double getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(Double registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public Date getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(Date establishDate) {
		this.establishDate = establishDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

}
