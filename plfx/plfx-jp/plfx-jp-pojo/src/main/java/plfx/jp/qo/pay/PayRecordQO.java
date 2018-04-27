package plfx.jp.qo.pay;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class PayRecordQO extends BaseQo{
	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
}
