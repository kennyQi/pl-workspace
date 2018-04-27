package hsl.pojo.command;


/**订单退款卡券处理command
 * @author Administrator
 *
 */
public class OrderRefundCommand {
	/**
	 * 订单ID
	 */
    private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
