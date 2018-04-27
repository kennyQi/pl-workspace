package hsl.pojo.command.coupon;
/**
 * @类功能说明：消费订单Command
 * @类修改者：
 * @修改日期：2015年4月24日下午2:53:40
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年4月24日下午2:53:40
 */
public class ConsumeCouponCommand {
	/**
	 * 卡券id
	 */
	private String couponId;
	/**
	 * 订单ID(订单快照)
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
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
}
