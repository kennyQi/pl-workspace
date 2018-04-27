package hg.payment.pojo.qo.payorder;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class PayOrderQO extends BaseQo{
	
	/**
	 * 商城订单编号
	 */
	private String orderNo;
	
	/**
	 * 客户端编号
	 */
	private String paymentClientID;
	
	/**
	 * api密钥
	 */
	private String paymentClienKeys;
	
	/**
	 * 是否只查询支付成功的订单
	 */
	private Boolean paySuccess;


	public String getPaymentClientID() {
		return paymentClientID;
	}

	public void setPaymentClientID(String paymentClientID) {
		this.paymentClientID = paymentClientID;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPaymentClienKeys() {
		return paymentClienKeys;
	}

	public void setPaymentClienKeys(String paymentClienKeys) {
		this.paymentClienKeys = paymentClienKeys;
	}

	public Boolean getPaySuccess() {
		return paySuccess;
	}

	public void setPaySuccess(Boolean paySuccess) {
		this.paySuccess = paySuccess;
	}

	
	
	
	

}
