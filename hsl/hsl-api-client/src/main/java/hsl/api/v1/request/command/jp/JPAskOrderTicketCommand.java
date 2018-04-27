package hsl.api.v1.request.command.jp;

import hsl.api.base.ApiPayload;

/**
 * 出票
 * 
 * @author yuxx
 * 
 */
public class JPAskOrderTicketCommand extends ApiPayload {

	/**
	 * 出票的平台订单号
	 */
	private String orderId;

	/**
	 * 支付方式
	 */
	private Integer payWay;

	/**
	 * 第三方支付单号
	 */
	private String payOrderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

}
