package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * 返利配置sqo
 * @author zqq
 * @date 2016-7-25上午9:57:17
 * @since
 */
public class RebateSetSQO extends BaseSPIQO{

	/**  */
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 商品
	 */
	private String productId;
	
	/**
	 * 商户
	 */
	private String distributorId;
	
	
	/**
	 * 审核状态
	 * 0-待审核     1-已通过 2-已拒绝
	 * */
	private Integer checkStatus;
	
	/**
	 * 申请审核时间
	 * */
	private String applyDate;
	
	/**
	 *  审核时间
	 * */
	private String checkDate;
	
	/**
	 *  生效时间
	 * */
	private String implementDate;
	
	private String implementDateStart;
	
	private String implementDateEnd;
	
	/**
	 *  失效时间
	 * */
	private String invalidDate;
	
	private String invalidDateStart;
	
	private String invalidDateEnd;
	
	/**
	 * 是否有效
	 * Y-有效  N-无效
	 * */
	private Boolean isImplement;
	     
	/**
	 * 申请人
	 * */
	private String applyUserName;
	
	/**
	 * 当前返现设置id
	 */
	private String runningSetId;

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

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getImplementDate() {
		return implementDate;
	}

	public void setImplementDate(String implementDate) {
		this.implementDate = implementDate;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
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

	public String getImplementDateStart() {
		return implementDateStart;
	}

	public void setImplementDateStart(String implementDateStart) {
		this.implementDateStart = implementDateStart;
	}

	public String getImplementDateEnd() {
		return implementDateEnd;
	}

	public void setImplementDateEnd(String implementDateEnd) {
		this.implementDateEnd = implementDateEnd;
	}

	public String getInvalidDateStart() {
		return invalidDateStart;
	}

	public void setInvalidDateStart(String invalidDateStart) {
		this.invalidDateStart = invalidDateStart;
	}

	public String getInvalidDateEnd() {
		return invalidDateEnd;
	}

	public void setInvalidDateEnd(String invalidDateEnd) {
		this.invalidDateEnd = invalidDateEnd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
