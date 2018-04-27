package plfx.api.client.api.v1.xl.request.command;

import plfx.api.client.base.slfx.ApiPayload;

public class XLModifyLineOrderApiCommand extends ApiPayload{
	
	/**
	 * 经销商订单编号
	 */
	private String dealerOrderNo;
	
	/**
	 * 取消订单的游客ID，用","隔开
	 */
	private String lineOrderTravelers;
	
	/**
	 * 订单状态
	 */
	private Integer status;

	/**
	 * 支付状态
	 */
	private Integer payStatus;


	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public String getLineOrderTravelers() {
		return lineOrderTravelers;
	}

	public void setLineOrderTravelers(String lineOrderTravelers) {
		this.lineOrderTravelers = lineOrderTravelers;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	
	
	

	
}
