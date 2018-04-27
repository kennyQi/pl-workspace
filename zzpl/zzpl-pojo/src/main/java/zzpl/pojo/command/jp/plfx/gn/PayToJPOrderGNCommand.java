package zzpl.pojo.command.jp.plfx.gn;

import hg.common.component.BaseCommand;

@SuppressWarnings("serial")
public class PayToJPOrderGNCommand extends BaseCommand{
	/**
	 * 分销平台订单号
	 */
	private String dealerOrderId;

	/**
	 * 确认支付总价
	 */
	private Double totalPrice;
	 /**
     * 支持的支付方式     
     * 1-支付宝 2-汇付 6-IPS  7-德付通如都支持，则为1267
     */
    private Integer  payPlatform;

	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}