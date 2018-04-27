package zzpl.pojo.command.jp.plfx.gj;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class PayToJPOrderGJCommand extends BaseCommand{
	/**
	 * 分销平台订单号
	 */
	private String platformOrderId;

	/**
	 * 确认支付总价
	 */
	private Double totalPrice;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}