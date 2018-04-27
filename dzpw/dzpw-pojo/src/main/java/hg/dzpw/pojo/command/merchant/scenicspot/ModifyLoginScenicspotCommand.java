package hg.dzpw.pojo.command.merchant.scenicspot;

import hg.dzpw.pojo.common.DZPWMerchantBaseCommand;

/**
 * @类功能说明：修改当前登录的景区信息（景区用户在景区后台修改）
 * @类修改者：
 * @修改日期：2015-2-9下午5:06:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-9下午5:06:54
 */
@SuppressWarnings("serial")
public class ModifyLoginScenicspotCommand extends DZPWMerchantBaseCommand {

	/**
	 * 景区ID（session中的景区ID）
	 */
	private String scenicspotId;
	/**
	 * 景区分类
	 */
	private Integer classify;
	/**
	 * 景区名称
	 */
	private String name;
	/**
	 * 景区简称
	 */
	private String shortName;
	/**
	 * 景区别名
	 */
	private String aliasName;

	/**
	 * 建筑部级别
	 */
	private String buildLevel;

	/**
	 * 主题
	 */
	private String[] themeValue;
	/**
	 * 景区特色
	 */
	private String feature;
	/**
	 * 开放时间
	 */
	private String openTime;
	/**
	 * 预定须知
	 */
	private String preNotice;
	/**
	 * 百度经度
	 */
	private String longitude;
	/**
	 * 百度纬度
	 */
	private String latitude;
	/**
	 * 景区网址
	 */
	private String webSite;
	/**
	 * 景区地址 省ID，市ID，区ID，街道地址
	 */
	private String provinceId, cityId, areaId, street;
	/**
	 * 景区简介
	 */
	private String intro;
	/**
	 * 景区密钥
	 */
	private String secretKey;
	/**
	 * 交通指南
	 */
	private String traffic;
	/**
	 * 联系人
	 */
	private String linkMan;
	/**
	 * 手机号
	 */
	private String telephone;
	/**
	 * 职位
	 */
	private String job;
	/**
	 * 联系邮箱
	 */
	private String email;
	/**
	 * 开户行
	 */
	private String depositBank;

	/**
	 * 开户单位
	 */
	private String depositOrg;

	/**
	 * 账户号
	 */
	private String depositAccount;
	/**
	 * 账户类型 1-汇金宝支付平台账户 2-支付宝账户
	 */
	private Integer accountType;
	/**
	 * 汇金宝平台返回的操作员编号
	 */
	private String operatorNo;
	/**
	 * 汇金宝平台返回的企业用户编号
	 */
	private String cstNo;
	/**
	 * 手续费费率
	 */
	private Double settlementFee;

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

	/**
	 * 景区LOGO(图片地址)
	 */
	private String scenicSpotLogo;

	public Integer getClassify() {
		return classify;
	}

	public void setClassify(Integer classify) {
		this.classify = classify;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
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

	public String getScenicspotId() {
		return scenicspotId;
	}

	public void setScenicspotId(String scenicspotId) {
		this.scenicspotId = scenicspotId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
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

	public String getScenicSpotLogo() {
		return scenicSpotLogo;
	}

	public void setScenicSpotLogo(String scenicSpotLogo) {
		this.scenicSpotLogo = scenicSpotLogo;
	}

	public Double getSettlementFee() {
		return settlementFee;
	}

	public void setSettlementFee(Double settlementFee) {
		this.settlementFee = settlementFee;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getBuildLevel() {
		return buildLevel;
	}

	public void setBuildLevel(String buildLevel) {
		this.buildLevel = buildLevel;
	}

	public String[] getThemeValue() {
		return themeValue;
	}

	public void setThemeValue(String[] themeValue) {
		this.themeValue = themeValue;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getPreNotice() {
		return preNotice;
	}

	public void setPreNotice(String preNotice) {
		this.preNotice = preNotice;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDepositBank() {
		return depositBank;
	}

	public void setDepositBank(String depositBank) {
		this.depositBank = depositBank;
	}

	public String getDepositOrg() {
		return depositOrg;
	}

	public void setDepositOrg(String depositOrg) {
		this.depositOrg = depositOrg;
	}

	public String getDepositAccount() {
		return depositAccount;
	}

	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}

}
