package zzpl.api.client.response.order;

import zzpl.api.client.base.ApiResponse;

public class GetGNFlightOrderTotalPriceResponse extends ApiResponse {

	/**
	 * 订单编号
	 */
	private String orderID;

	/**
	 * 订单编号
	 */
	private String orderNO;

	private Double totalPrice;

	private Double commitPrice;

	private Double platTotalPrice;

	private String ibePrice;

	/**
	 * 机场建设
	 */
	private Double buildFee;

	/**
	 * 参考燃油费
	 */
	private Double oilFee;
	
	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getCommitPrice() {
		return commitPrice;
	}

	public void setCommitPrice(Double commitPrice) {
		this.commitPrice = commitPrice;
	}

	public String getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(String ibePrice) {
		this.ibePrice = ibePrice;
	}

	public Double getBuildFee() {
		return buildFee;
	}

	public void setBuildFee(Double buildFee) {
		this.buildFee = buildFee;
	}

	public Double getOilFee() {
		return oilFee;
	}

	public void setOilFee(Double oilFee) {
		this.oilFee = oilFee;
	}

	
}
