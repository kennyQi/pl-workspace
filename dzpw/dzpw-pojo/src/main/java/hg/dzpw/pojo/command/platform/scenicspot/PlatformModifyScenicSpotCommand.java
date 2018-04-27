package hg.dzpw.pojo.command.platform.scenicspot;

import java.util.List;
import java.util.UUID;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;
import hg.dzpw.pojo.common.util.StringFilterUtil;

/**
 * @类功能说明：修改景区
 * @类修改者：
 * @修改日期：2014-11-12上午10:45:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-12上午10:45:18
 */
public class PlatformModifyScenicSpotCommand extends DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	// --------------------- 景区基本信息 ---------------------
	/**
	 * 登录账户
	 */
	private String adminLoginName;
	/**
	 * 登录密码
	 */
	private String adminPassword;
	/**
	 * 景区密钥(如果为空，则重新生成UUID)
	 */
	private String secretKey;
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
	 * 交通指南
	 */
	private String traffic;
	/**
	 * 百度经度
	 */
	private String longitude;
	/**
	 * 百度纬度
	 */
	private String latitude;
	/**
	 * 景区代码
	 */
	private String code;
	/**
	 * 所在地 省市区ID和街道
	 */
	private String provinceId, cityId, areaId, street;
	/**
	 * 景区级别
	 */
	private String level;
	/**
	 * 景区网址
	 */
	private String webSite;
	/**
	 * 景区介绍
	 */
	private String intro;
	// --------------------- 景区联系信息 ---------------------
	/**
	 * 联系人
	 */
	private String linkMan;
	/**
	 * 联系电话
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
	 * 联系QQ
	 */
	private String qq;
	/**
	 * 公司传真
	 */
	private String fax;
	/**
	 * 联系地址
	 */
	private String address;
	/**
	 * 邮政编码
	 */
	private String postcode;
	// --------------------- 资质信息 ---------------------
	/**
	 * 营业执照
	 */
	private String businessLicense;
	/**
	 * 税务登记证
	 */
	private String taxRegistrationCertificate;
	/**
	 * 组织代码证
	 */
	private String organizationCodeCertificate;
	/**
	 * 法人身份证
	 */
	private String corporateIDCard;
	/**
	 * 景区LOGO(图片地址)
	 */
	private String scenicSpotLogo;
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
	 * 汇金宝平台返回的企业用户编号
	 */
	private String cstNo;
	
	/**
	 * 汇金宝平台返回的操作员编号
	 */
	private String operatorNo;
	/**
	 * 手续费
	 */
	private Double settlementFee;
	
	/**
	 * 账户类型  1-汇金宝支付平台账户   2-支付宝账户
	 */
	private Integer accountType;
	// --------------------- 设备编号 ---------------------
	/**
	 * 新增的设备ID(不包括修改前新增的设备ID)
	 */
	private List<String> deviceIds;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId == null ? null : scenicSpotId.trim();
	}

	public String getAdminLoginName() {
		return adminLoginName;
	}

	public void setAdminLoginName(String adminLoginName) {
		this.adminLoginName = adminLoginName == null ? null : StringFilterUtil
				.reverseString(adminLoginName.trim());
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword == null ? null : StringFilterUtil
				.reverseString(adminPassword.trim());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : StringFilterUtil.reverseString(name
				.trim());
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName == null ? null : StringFilterUtil
				.reverseString(shortName.trim());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : StringFilterUtil.reverseString(code
				.trim());
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId == null ? null : provinceId.trim();
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId == null ? null : cityId.trim();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite == null ? null : StringFilterUtil
				.reverseString(webSite.trim());
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro == null ? null : intro.trim();
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan == null ? null : StringFilterUtil
				.reverseString(linkMan.trim());
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq == null ? null : qq.trim();
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax == null ? null : fax.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : StringFilterUtil
				.reverseString(address.trim());
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode == null ? null : postcode.trim();
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense == null ? null : businessLicense
				.trim();
	}

	public String getTaxRegistrationCertificate() {
		return taxRegistrationCertificate;
	}

	public void setTaxRegistrationCertificate(String taxRegistrationCertificate) {
		this.taxRegistrationCertificate = taxRegistrationCertificate == null ? null
				: taxRegistrationCertificate.trim();
	}

	public String getOrganizationCodeCertificate() {
		return organizationCodeCertificate;
	}

	public void setOrganizationCodeCertificate(
			String organizationCodeCertificate) {
		this.organizationCodeCertificate = organizationCodeCertificate == null ? null
				: organizationCodeCertificate.trim();
	}

	public String getCorporateIDCard() {
		return corporateIDCard;
	}

	public void setCorporateIDCard(String corporateIDCard) {
		this.corporateIDCard = corporateIDCard == null ? null : corporateIDCard
				.trim();
	}

	public String getScenicSpotLogo() {
		return scenicSpotLogo;
	}

	public void setScenicSpotLogo(String scenicSpotLogo) {
		this.scenicSpotLogo = scenicSpotLogo;
	}

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public String getOperatorNo() {
		return operatorNo;
	}

	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public List<String> getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(List<String> deviceIds) {
		this.deviceIds = (null == deviceIds || deviceIds.size() < 1) ? null
				: deviceIds;
	}

	public String getSecretKey() {
		if (secretKey == null || secretKey.trim().length() == 0)
			secretKey = UUID.randomUUID().toString().replace("-", "");
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
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

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
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