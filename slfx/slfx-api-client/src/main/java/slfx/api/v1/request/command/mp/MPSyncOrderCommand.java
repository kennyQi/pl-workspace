package slfx.api.v1.request.command.mp;

import slfx.api.base.ApiPayload;

/**
 * 同步单个订单状态
 * 
 * @author zhurz
 */
public class MPSyncOrderCommand extends ApiPayload {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分销平台订单号
	 */
	private String orderId;

	public MPSyncOrderCommand() {
	}

	public MPSyncOrderCommand(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
