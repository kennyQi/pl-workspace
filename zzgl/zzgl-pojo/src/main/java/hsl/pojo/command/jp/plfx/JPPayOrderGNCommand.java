package hsl.pojo.command.jp.plfx;

import hg.common.component.BaseCommand;
/**
 * @类功能说明：支付订单接口
 * @类修改者：
 * @修改日期：2015年7月23日上午8:53:50
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年7月23日上午8:53:50
 */
public class JPPayOrderGNCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;
	
	/**
	 * 经销商总支付价格
	 */
	private Double totalPrice;
	
	/**
	 * 所有参数经MD5加密算法后得出的结果
	 */
	private String sign;
	/**
	 * 支付帐号
	 */
	private String buyerEmail;
	/**
	 * 支付订单号
	 */
	private String payTradeNo;
	 /**
     * 支持的支付方式     
     * 1-支付宝 2-汇付 6-IPS  7-德付通如都支持，则为1267
     */
    private Integer  payPlatform;
    /**
	 * 订单总支付价格
	 */
	private Double payPrice;
	/**
	 * 使用账户余额金额
	 */
	private Double accountBalance;

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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}

	public Double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	
}
