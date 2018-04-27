package hsl.pojo.command;


/**
 * 订单退款卡券处理command
 *
 * @author Administrator
 */
public class OrderRefundCommand {
	/**
	 * 订单号（不是model的ID）
	 */
	private String orderId;

	public OrderRefundCommand() {
	}

	public OrderRefundCommand(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
