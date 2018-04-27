package plfx.yeexing.pojo.dto.order;

import plfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：机票
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月26日下午3:34:45
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPFlightTicketDTO extends BaseJpDTO {
	/**
	 * 所出票号
	 */
	private String ticketNo;
	
	/**
	 * 单价
	 */

	private Double price;
	
	/**
	 * 燃油费   必填
	 */
	
	private Double fuelSurTax;
	
	/**
	 * 机场建设费   必填
	 */

	private Double airportTax;
	/**
	 * 是否废票   必填
	 */

	private String refund;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public String getRefund() {
		return refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}

}
