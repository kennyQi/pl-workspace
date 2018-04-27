package hg.fx.command.rebate;

import hg.demo.member.common.domain.model.AuthUser;
import hg.framework.common.base.BaseSPICommand;
import hg.fx.domain.Distributor;
import hg.fx.domain.Product;

import java.util.Date;

public class CreateRebateSetCommand extends BaseSPICommand{
	
	/**  */
	private static final long serialVersionUID = 1L;
	
	private String productId;
	/**
	 * 返利关联的商品
	 */
	private Product product;
	/**
	 * 商品名称
	 */
	private String productName;
	
	private String distributorId;
	/**
	 * 返利关联的商户
	 */
	private Distributor distributor;
	
	private String loginName;
	
	private String distributorName;
	
	private String distributorUserId;
	
	/**
	 * 区间建值串
	 * */
	private String intervalStr;
	
	/**
	 * 审核状态
	 * 0-待审核     1-已通过 2-已拒绝
	 * */
	private Integer checkStatus;
	
	/**
	 * 申请审核时间
	 * */
	private Date applyDate;
	
	/**
	 *  生效时间
	 * */
	private Date implementDate;
	
	/**
	 * 是否有效
	 * Y-有效  N-无效
	 * */
	private Boolean isImplement;
	     
	/**
	 * 申请人
	 * */
	private AuthUser applyUser;

	private String applyUserName;
	/**
	 * 当前返现设置id
	 */
	private String runningSetId;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getIntervalStr() {
		return intervalStr;
	}
	public void setIntervalStr(String intervalStr) {
		this.intervalStr = intervalStr;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Date getImplementDate() {
		return implementDate;
	}
	public void setImplementDate(Date implementDate) {
		this.implementDate = implementDate;
	}
	public Boolean getIsImplement() {
		return isImplement;
	}
	public void setIsImplement(Boolean isImplement) {
		this.isImplement = isImplement;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getRunningSetId() {
		return runningSetId;
	}
	public void setRunningSetId(String runningSetId) {
		this.runningSetId = runningSetId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Distributor getDistributor() {
		return distributor;
	}
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}
	public AuthUser getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(AuthUser applyUser) {
		this.applyUser = applyUser;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getDistributorUserId() {
		return distributorUserId;
	}
	public void setDistributorUserId(String distributorUserId) {
		this.distributorUserId = distributorUserId;
	}
	
	
	
}
