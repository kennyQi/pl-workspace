package hg.dzpw.pojo.qo;

import java.util.Date;

import hg.dzpw.pojo.common.BasePojoQo;

public class FinanceDetailQo extends BasePojoQo {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 景区名称
	 */
	private String scenicSpotName;
	
	/**
	 * 景区状态
	 */
	private Integer scenicSpotStatus;
	
	/**
	 * 结算流水号
	 */
	private String settleNo;
	
	/**
	 * 经销商名称
	 */
	private String dealerName;
	
	/**
	 * 产品编号
	 */
	private String policyNo;
	
	/**
	 * 经销商代码
	 */
	private String dealerKey;
	
	/**
	 * 票务名称
	 */
	private String policyName;
	
	/**
	 * 订单状态
	 */
	private Integer status;
	
	/**
	 * 经销商id
	 */
	private String dealerId;
	
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 查询开始时间
	 */
	private Date dateBegin;
	
	/**
	 * 查询结束时间
	 */
	private Date dateEnd;

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getSettleNo() {
		return settleNo;
	}

	public void setSettleNo(String settleNo) {
		this.settleNo = settleNo;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getDealerKey() {
		return dealerKey;
	}

	public void setDealerKey(String dealerKey) {
		this.dealerKey = dealerKey;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getScenicSpotName() {
		return scenicSpotName;
	}

	public void setScenicSpotName(String scenicSpotName) {
		this.scenicSpotName = scenicSpotName;
	}

	public Integer getScenicSpotStatus() {
		return scenicSpotStatus;
	}

	public void setScenicSpotStatus(Integer scenicSpotStatus) {
		this.scenicSpotStatus = scenicSpotStatus;
	}
	
}
