package plfx.api.client.api.v1.mp.request.command;

import plfx.api.client.base.slfx.ApiPayload;

/**
 * 门票取消订单
 * 
 * @author yuxx
 */
@SuppressWarnings("serial")
public class MPOrderCancelCommand extends ApiPayload {

	/**
	 * 分销平台订单号
	 */
	private String orderId;
	
	/**
	 * 取消原由
	 */
	private Integer cancelReason;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(Integer cancelReason) {
		this.cancelReason = cancelReason;
	}

}
