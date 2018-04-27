package hsl.pojo.command.jp;

import hg.common.component.BaseCommand;

/**
 * 退废票时更新机票订单状态和支付状态，并保存退款金额
 *
 */
@SuppressWarnings("serial")
public class UpdateJPOrderStatusCommand extends BaseCommand{
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 经销商订单号 
	 */
	private String dealerOrderCode;
	/**
	 * 订单状态
	 */
	private Integer status;	
	/**
	 * 退废金额
	 */
	private Double backPrice;
	/** 
	 * 支付状态
	 */
	private Integer payStatus;
	/**
	 * 支付帐号
	 */
	private String buyerEmail;
	/**
	 * 支付现金,没有卡券支付
	 */
	private Double payCash;
	/**
	 * 支付订单号
	 */
	private String payTradeNo;
	/**
	 * (单个乘客)支付余额,没有卡券支付-->用户真实支付余额除以乘客数量，最后去整数。
	 */
	private Double payBalance;
	public String getDealerOrderCode() {
		return dealerOrderCode;
	}
	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getBackPrice() {
		return backPrice;
	}
	public void setBackPrice(Double backPrice) {
		this.backPrice = backPrice;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
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
	public Double getPayCash() {
		return payCash;
	}
	public void setPayCash(Double payCash) {
		this.payCash = payCash;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getPayBalance() {
		return payBalance;
	}
	public void setPayBalance(Double payBalance) {
		this.payBalance = payBalance;
	}
	
}
