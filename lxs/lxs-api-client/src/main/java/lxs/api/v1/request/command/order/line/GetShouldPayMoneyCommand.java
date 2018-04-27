package lxs.api.v1.request.command.order.line;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class GetShouldPayMoneyCommand extends ApiPayload {
	private String orderNO;

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

}
