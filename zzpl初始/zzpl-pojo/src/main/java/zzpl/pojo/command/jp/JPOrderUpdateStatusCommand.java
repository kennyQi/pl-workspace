package zzpl.pojo.command.jp;

import hg.common.component.BaseCommand;

/**
 * 退废票时更新机票订单状态和支付状态，并保存退款金额
 *
 */
@SuppressWarnings("serial")
public class JPOrderUpdateStatusCommand extends BaseCommand{
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
	/** 支付状态 */
	private Integer payStatus;
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
	
}
