package hg.fx.command.distributor;

import hg.framework.common.base.BaseSPICommand;

/**
 * 创建商户命令
 * @date 2016-6-16上午10:28:58
 * @since
 */
@SuppressWarnings("serial")
public class CreateDistributorCommand extends BaseSPICommand{
	
	/** 折扣--定价模式 */
	public static final int DISCOUNT_TYPE_FIXED_PRICE = 1;
	
	/** 折扣--返利模式 */
	public static final int DISCOUNT_TYPE_REBATE = 2;
	
	
	/**
	 * 商户编号
	 */
	private String code;
	/**
	 * 联系人
	 */
	private String linkMan;
	/**
	 * 联系手机
	 */
	private String phone;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 公司网址
	 */
	private String webSite;
	/**
	 * 商户审核状态
	 * -1--已拒绝  
	 *  0--待审核  
	 *  1--已通过
	 */
	private Integer checkStatus;
	/**
	 * 商户使用状态
	 * 0--禁用  
	 * 1--启用
	 */
	private Integer status;
	/**
	 * 商户首字母
	 * 商户名字第一个字符如是数字或其他符合的 统一用保存@
	 */
	private String firstLetter;
	/**
	 * API签名KEY
	 */
	private String signKey;
	
	/** 折扣类型 */
	private int discountType;

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public int getDiscountType() {
		return discountType;
	}

	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}
	
}
