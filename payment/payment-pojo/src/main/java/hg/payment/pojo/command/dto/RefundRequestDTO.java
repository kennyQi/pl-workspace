package hg.payment.pojo.command.dto;

import hg.payment.pojo.dto.BaseDTO;

/**
 * 
 * 
 *@类功能说明：退款请求通用参数
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月27日上午8:44:06
 *
 */
public class RefundRequestDTO extends BaseDTO{
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * 客户端ID
	 */
	private String paymentClientId;
	
	/**
	 * 商城订单号
	 */
	private String tradeNo;

	/**
	 * 退款金额
	 */
	private Double amount;
	
	/**
	 * 退款理由
	 */
	private String refundMessage;
	

	

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRefundMessage() {
		return refundMessage;
	}

	public void setRefundMessage(String refundMessage) {
		this.refundMessage = refundMessage;
	}

	public String getPaymentClientId() {
		return paymentClientId;
	}

	public void setPaymentClientId(String paymentClientId) {
		this.paymentClientId = paymentClientId;
	}




	
	
	
	
}
