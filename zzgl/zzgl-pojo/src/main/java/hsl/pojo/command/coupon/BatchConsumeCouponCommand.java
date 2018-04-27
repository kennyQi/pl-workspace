package hsl.pojo.command.coupon;



public class BatchConsumeCouponCommand {
	/**
	 * 批量卡券ids
	 */
	private String[] couponIds;
	/**
	 * 订单快照
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 支付价格
	 */
	private Double payPrice;
	/**
	 * 详细
	 */
	private String detail;

	private String userID;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getPayPrice() {
		return payPrice;
	}
	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String[] getCouponIds() {
		return couponIds;
	}
	public void setCouponIds(String[] couponIds) {
		this.couponIds = couponIds;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}
