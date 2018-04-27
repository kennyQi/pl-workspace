
package hsl.pojo.dto.hotel.order;

import java.io.Serializable;

public class OrderCancelCommandDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 订单Id
	 */
    protected long orderId;
    /**
     * 取消编号
     */
    protected String cancelCode;
    /**
     * 取消原因
     */
    protected String reason;
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getCancelCode() {
		return cancelCode;
	}
	public void setCancelCode(String cancelCode) {
		this.cancelCode = cancelCode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

    

}
