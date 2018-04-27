package lxs.domain.model.line.event;

import hg.common.component.BaseEvent;

public class LineOrderPayEvent extends BaseEvent {
	private static final long serialVersionUID = 1L;
	/**
	 * 线路订单号
	 */
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}