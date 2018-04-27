package lxs.api.v1.request.command.order.line;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class PayBargainMoneyCommand extends ApiPayload{
	/**
	 * 订单号
	 */
	private String dealerOrderNo;

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}
}
