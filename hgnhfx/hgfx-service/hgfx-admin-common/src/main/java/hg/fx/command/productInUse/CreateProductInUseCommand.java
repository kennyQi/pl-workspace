package hg.fx.command.productInUse;


import java.util.Date;

import hg.framework.common.base.BaseSPICommand;

@SuppressWarnings("serial")
public class CreateProductInUseCommand extends BaseSPICommand {

	private String id;
	
	/**
	 * 使用状态
	 * 0--试用中  
	 * 1--申请中  
	 * 2--使用中  
	 * 3--已拒绝  
	 * 4--停用中
	 */
	private Integer status;
	
	/**
	 * 商品id
	 */
	private String  prodId;
	
	/**
	 * 商户id
	 */
	private String distributorId;
	
	/**
	 * 联系qq
	 */
	private String qq;
	
	/**
	 * 联系电话  手机、座机都可以
	 */
	private String phone;
	
	/**
	 * 试用时间
	 */
	private Date trialDate;
	
	/**
	 * 启用时间
	 */
	private Date useDate;
	
	/**
	 * 正式申请时盖了公章的电子协议
	 */
	private String agreementPath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getTrialDate() {
		return trialDate;
	}

	public void setTrialDate(Date trialDate) {
		this.trialDate = trialDate;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getAgreementPath() {
		return agreementPath;
	}

	public void setAgreementPath(String agreementPath) {
		this.agreementPath = agreementPath;
	}
	
	
}
