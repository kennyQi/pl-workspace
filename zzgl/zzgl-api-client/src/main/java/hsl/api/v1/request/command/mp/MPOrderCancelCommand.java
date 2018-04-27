package hsl.api.v1.request.command.mp;

import hsl.api.base.ApiPayload;

/**
 * 门票取消订单
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
public class MPOrderCancelCommand extends ApiPayload {

	/**
	 * 商城订单号
	 */
	private String orderId;

	private Integer reason;

	private String remark;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getReason() {
		return reason;
	}

	public void setReason(Integer reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
