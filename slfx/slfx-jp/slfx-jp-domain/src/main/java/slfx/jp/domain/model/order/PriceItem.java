package slfx.jp.domain.model.order;

/**
 * 
 * @类功能说明：报价项
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:28:59
 * @版本：V1.0
 *
 */
public class PriceItem {
	

	/**
	 * 旅客类型   ADT--成人  CHD--儿童  INF--婴儿  UM--无陪伴儿童
	 */
	private String psgType;
	
	/**
	 * 票面价
	 */
	private Double fare;
	
	/**
	 * 燃油费
	 */
	private Double fuelSurTax;
	
	/**
	 * 机场建设费
	 */
	private Double airportTax;
	
	/**
	 * 税款合计 = 机场建设费+燃油费
	 */
	private Double taxAmount;
	
	/**
	 * 票价总额 = 票面价+税款合计
	 */
	private Double amount;

	public String getPsgType() {
		return psgType;
	}

	public void setPsgType(String psgType) {
		this.psgType = psgType;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public Double getFuelSurTax() {
		return fuelSurTax;
	}

	public void setFuelSurTax(Double fuelSurTax) {
		this.fuelSurTax = fuelSurTax;
	}

	public Double getAirportTax() {
		return airportTax;
	}

	public void setAirportTax(Double airportTax) {
		this.airportTax = airportTax;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
   
}