package slfx.api.v1.request.command.jp;

import slfx.api.base.ApiPayload;

/**
 * 
 * @类功能说明：出票COMMAND
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月1日下午5:06:51
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPAskOrderTicketCommand extends ApiPayload {

	/**
	 * 出票的商城订单号
	 */
	private String orderId;

	/**
	 * 支付方式
	 */
	private String payWay;

	/**
	 * 第三方支付单号
	 */
	private String payOrderId;
	
	/**
	 * 出票完成通知地址
	 */
	private String notifyUrl;
	
	/**
	 * 付款支付宝账号 
	 */
	private String buyerEmail;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

}
