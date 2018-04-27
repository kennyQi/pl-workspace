package hsl.pojo.qo.lineSalesPlan.order;

import hg.common.component.BaseQo;

/**
 * @类功能说明：线路销售方案订单QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 9:36
 */
public class LSPOrderQO extends BaseQo {
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 活动订单类型
	 * 1为团购
	 * 2为秒杀
	 */
	private Integer orderType;
	/**
	 * 活动ID
	 */
	private String planId;
	/**
	 * 经销商订单号
	 */
	private String dealerOrderNo;
	/**
	 * 活动名称
	 */
	private String lspName;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 订单状态
	 */
	private Integer[] orderStatusArray;
	/**
	 * 支付状态
	 */
	private Integer payStatus;
	/**
	 * 支付交易号
	 */
	private String payTradeNo;
	/**
	 * 游客的证件号
	 */
	private String travelerIdNo;
	/**
	 * 是否关联游客信息
	 */
	private boolean fetchTraveler;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public String getLspName() {
		return lspName;
	}

	public void setLspName(String lspName) {
		this.lspName = lspName;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public boolean isFetchTraveler() {
		return fetchTraveler;
	}

	public void setFetchTraveler(boolean fetchTraveler) {
		this.fetchTraveler = fetchTraveler;
	}

	public String getTravelerIdNo() {
		return travelerIdNo;
	}

	public void setTravelerIdNo(String travelerIdNo) {
		this.travelerIdNo = travelerIdNo;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public Integer[] getOrderStatusArray() {
		return orderStatusArray;
	}

	public void setOrderStatusArray(Integer[] orderStatusArray) {
		this.orderStatusArray = orderStatusArray;
	}
}
