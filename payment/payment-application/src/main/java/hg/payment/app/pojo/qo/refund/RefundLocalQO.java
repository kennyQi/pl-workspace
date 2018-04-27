package hg.payment.app.pojo.qo.refund;

import hg.common.component.BaseQo;

/**
 * 
 * 
 *@类功能说明：退款QO
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月27日上午9:37:46
 *
 */
public class RefundLocalQO extends BaseQo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 商城订单号
	 */
	private String clientTradeNo;
	
	/**
	 * 客户端编号
	 */
	private String paymentClientID;
	
	/**
	 * 第三方交易号
	 */
	private String thirdPartyTradeNo;
	
	/**
	 * 支付平台订单号 
	 */
	private String tradeNo;
	
	
	private boolean payOrderLazy = false;


	public boolean isPayOrderLazy() {
		return payOrderLazy;
	}

	public void setPayOrderLazy(boolean payOrderLazy) {
		this.payOrderLazy = payOrderLazy;
	}

	public String getClientTradeNo() {
		return clientTradeNo;
	}

	public void setClientTradeNo(String clientTradeNo) {
		this.clientTradeNo = clientTradeNo;
	}

	public String getPaymentClientID() {
		return paymentClientID;
	}

	public void setPaymentClientID(String paymentClientID) {
		this.paymentClientID = paymentClientID;
	}

	public String getThirdPartyTradeNo() {
		return thirdPartyTradeNo;
	}

	public void setThirdPartyTradeNo(String thirdPartyTradeNo) {
		this.thirdPartyTradeNo = thirdPartyTradeNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	
	
	
	

}
