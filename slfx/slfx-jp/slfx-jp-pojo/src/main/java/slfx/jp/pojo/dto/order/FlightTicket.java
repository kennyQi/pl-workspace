package slfx.jp.pojo.dto.order;

import java.io.Serializable;


/**
 * 
 * @类功能说明：机票
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年7月31日下午4:27:18
 * @版本：V1.0
 *
 */
public class FlightTicket implements Serializable{

	private static final long serialVersionUID = 6664610944173233937L;

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

	public String getRefund() {
		return refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
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

}
