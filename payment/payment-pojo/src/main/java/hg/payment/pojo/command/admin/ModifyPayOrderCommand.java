package hg.payment.pojo.command.admin;

import javax.persistence.Column;

/**
 * 
 * 
 *@类功能说明：支付完成 修改订单信息
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月6日下午1:56:35
 *
 */
public class ModifyPayOrderCommand {
	
	/**
	 * 是否支付成功
	 */
	private Boolean paySuccess;
	
	/**
	 * 付款方帐号
	 */
	private String payerAccount;
	
	/**
	 * 第三方交易帐号
	 */
	private String thirdPartyTradeNo;
	
	/**
	 * 支付平台订单号
	 */
	private String payOrderId;
	
	/**
	 * 订单备注
	 */
	private String orderRemark;

	public Boolean getPaySuccess() {
		return paySuccess;
	}

	public void setPaySuccess(Boolean paySuccess) {
		this.paySuccess = paySuccess;
	}

	public String getThirdPartyTradeNo() {
		return thirdPartyTradeNo;
	}

	public void setThirdPartyTradeNo(String thirdPartyTradeNo) {
		this.thirdPartyTradeNo = thirdPartyTradeNo;
	}

	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}

	public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	
	
	

}
