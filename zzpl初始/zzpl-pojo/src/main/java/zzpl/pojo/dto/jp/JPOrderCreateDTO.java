package zzpl.pojo.dto.jp;

public class JPOrderCreateDTO {
	/**
	 * 下单成功后返回商城订单号
	 */
	private String dealerOrderCode;

	/**
	 * 订单需要支付的票价
	 */
	private Double ticketPrice;

	/**
	 * 订单需要支付的机建费
	 */
	private Double airportTax;

	/**
	 * 订单需要支付的燃油费
	 */
	private Double fuelSurTax;
	
	/**
	 * 订单总金额
	 */
	private Double payAmount;
	
	/**
	 * 舱位代码
	 */
	private String classCode;


	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Double getAirportTax() {
		return airportTax;
	}

	public void setAirportTax(Double airportTax) {
		this.airportTax = airportTax;
	}

	public Double getFuelSurTax() {
		return fuelSurTax;
	}

	public void setFuelSurTax(Double fuelSurTax) {
		this.fuelSurTax = fuelSurTax;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
}
