package hg.fx.command.distributoruser;

import hg.framework.common.base.BaseSPICommand;

/**
 * 添加商户帐号
 * @author Caihuan
 * @date   2016年6月1日
 */
@SuppressWarnings("serial")
public class CreateDistributorUserCommand extends BaseSPICommand{
	
	/**
	 * 商户id 创建子帐号用到
	 */
	private String distributorId;
	/**
	 * 商户帐号
	 */
	private String account;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 商户姓名
	 */
	private String name;
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 公司网址
	 */
	private String webSite;
	
	/**
	 * 1主账号 2子帐号
	 */
	private Integer type;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 审核状态
	 */
	private Integer checkStatus;
	
	/**
	 * 密码
	 */
	private String password;
	
	/** 折扣类型 */
	private int discountType;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public int getDiscountType() {
		return discountType;
	}

	public void setDiscountType(int discountType) {
		this.discountType = discountType;
	}
	
}
