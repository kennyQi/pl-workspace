package hg.dzpw.pojo.command.platform.dealer;


import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

/**
 * @类功能说明：运营后台创建经销商
 * @类修改者：
 * @修改日期：2015-3-24下午3:19:03
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-24下午3:19:03
 */
@SuppressWarnings("serial")
public class PlatformCreateDealerCommand extends DZPWPlatformBaseCommand {

	// 1.基本信息
	/**
	 * 经销商名称
	 */
	private String name;
	/**
	 * 登录账户
	 */
	private String adminLoginName;
	/**
	 * 登录密码
	 */
	private String adminPassword;
	/**
	 * 经销商代码
	 */
	private String key;
	/**
	 * 密钥
	 */
	private String secretKey;
	/**
	 * 经销商简介
	 */
	private String intro;
	
	/**
	 * LOGO(图片地址)
	 */
	private String dealerLogo;
	
	// 2.资质信息
	/**
	 * 营业执照(图片地址)
	 */
	private String businessLicense;
	/**
	 * 税务登记证(图片地址)
	 */
	private String taxRegistrationCertificate;
	/**
	 * 组织代码证(图片地址)
	 */
	private String organizationCodeCertificate;
	/**
	 * 法人身份证(图片地址)
	 */
	private String corporateIDCard;
	// 3.联系信息
	/**
	 * 联系人
	 */
	private String linkMan;
	/**
	 * 手机号
	 */
	private String telephone;
	/**
	 * 联系邮箱
	 */
	private String email;
	/**
	 * 联系人地址（省、市、区、详细地址）
	 */
	private String provinceId, cityId, areaId, address;
	// 4.经销商网址
	/**
	 * 经销商网址
	 */
	private String dealerWebsite;

	/**
	 * 消息推送地址
	 */
	private String publishUrl;

	/**
	 * 是否使用消息推送
	 */
	private Boolean publishAble = true;
	
	//经销商结算信息
	/**
	 * 账户类型  1-汇金宝支付平台账户   2-北农商电子账户
	 */
	private Integer accountType;
	
	/**
	 * 结算账户
	 */
	private String accountNumber;
	
	/**
	 * 预付款
	 */
	private Double advancePayment;
	
	/**
	 * 警戒阀值
	 */
	private Double warnBalance;
	
	/**
	 * 企业用户在汇金宝平台的操作员编号
	 */
	private String operatorNo;
	/**
	 * 汇金宝平台的用户编号
	 */
	private String cstNo;
	
	/**
	 * 商户ID
	 */
	private String businessId;
	
	/**
	 * 应用Key
	 */
	private String businessKey;
	
	/**
	 * 手续费率 （千分之）
	 */
	private Double settlementFee;
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getTaxRegistrationCertificate() {
		return taxRegistrationCertificate;
	}

	public void setTaxRegistrationCertificate(String taxRegistrationCertificate) {
		this.taxRegistrationCertificate = taxRegistrationCertificate;
	}

	public String getOrganizationCodeCertificate() {
		return organizationCodeCertificate;
	}

	public void setOrganizationCodeCertificate(
			String organizationCodeCertificate) {
		this.organizationCodeCertificate = organizationCodeCertificate;
	}

	public String getCorporateIDCard() {
		return corporateIDCard;
	}

	public void setCorporateIDCard(String corporateIDCard) {
		this.corporateIDCard = corporateIDCard;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDealerWebsite() {
		return dealerWebsite;
	}

	public void setDealerWebsite(String dealerWebsite) {
		this.dealerWebsite = dealerWebsite;
	}

	public String getPublishUrl() {
		return publishUrl;
	}

	public void setPublishUrl(String publishUrl) {
		this.publishUrl = publishUrl;
	}

	public Boolean getPublishAble() {
		return publishAble;
	}

	public void setPublishAble(Boolean publishAble) {
		this.publishAble = publishAble;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getAdvancePayment() {
		return advancePayment;
	}

	public void setAdvancePayment(Double advancePayment) {
		this.advancePayment = advancePayment;
	}

	public Double getWarnBalance() {
		return warnBalance;
	}

	public void setWarnBalance(Double warnBalance) {
		this.warnBalance = warnBalance;
	}

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public Double getSettlementFee() {
		return settlementFee;
	}

	public void setSettlementFee(Double settlementFee) {
		this.settlementFee = settlementFee;
	}

	public String getDealerLogo() {
		return dealerLogo;
	}

	public void setDealerLogo(String dealerLogo) {
		this.dealerLogo = dealerLogo;
	}

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public String getAdminLoginName() {
		return adminLoginName;
	}

	public void setAdminLoginName(String adminLoginName) {
		this.adminLoginName = adminLoginName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	

}
