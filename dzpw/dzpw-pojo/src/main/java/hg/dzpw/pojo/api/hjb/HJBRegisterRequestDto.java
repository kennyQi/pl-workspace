package hg.dzpw.pojo.api.hjb;

import java.io.Serializable;

public class HJBRegisterRequestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5814858258860018653L;

	/**
	 * 接口版本号
	 */
	private String version;
	/**
	 * 票务平台唯一标识
	 */
	private String merchantId;
	/**
	 * 调用端IP，可空
	 */
	private String callerIp;
	/**
	 * 需提供的供应商名称
	 */
	private String cstName;
	/**
	 * 调用方客户版本号
	 */
	private String cstVersion;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 联系人移动电话
	 */
	private String mobile;
	/**
	 * 联系人电话
	 */
	private String phone;
	/**
	 * 联系人邮箱
	 */
	private String email;
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	/**
	 * 操作员邮箱
	 */
	private String operatorEmail;
	/**
	 * 操作员手机
	 */
	private String operatorMobile;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 分账账户
	 */
	private String splitAccount;
	/**
	 * 证件类型
	 */
	private String ctfType;
	/**
	 * 证件号码
	 */
	private String ctfNo;
	
	public HJBRegisterRequestDto() {
		this("1.0","zy01835");
	}
	public HJBRegisterRequestDto(String version,String merchantId){
		this.merchantId=merchantId;
		this.version=version;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getCallerIp() {
		return callerIp;
	}
	public void setCallerIp(String callerIp) {
		this.callerIp = callerIp;
	}
	public String getCstName() {
		return cstName;
	}
	public void setCstName(String cstName) {
		this.cstName = cstName;
	}
	public String getCstVersion() {
		return cstVersion;
	}
	public void setCstVersion(String cstVersion) {
		this.cstVersion = cstVersion;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorEmail() {
		return operatorEmail;
	}
	public void setOperatorEmail(String operatorEmail) {
		this.operatorEmail = operatorEmail;
	}
	public String getOperatorMobile() {
		return operatorMobile;
	}
	public void setOperatorMobile(String operatorMobile) {
		this.operatorMobile = operatorMobile;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getSplitAccount() {
		return splitAccount;
	}
	public void setSplitAccount(String splitAccount) {
		this.splitAccount = splitAccount;
	}
	public String getCtfType() {
		return ctfType;
	}
	public void setCtfType(String ctfType) {
		this.ctfType = ctfType;
	}
	public String getCtfNo() {
		return ctfNo;
	}
	public void setCtfNo(String ctfNo) {
		this.ctfNo = ctfNo;
	}
	
}
