package slfx.jp.domain.model.order;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import slfx.jp.domain.model.J;

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
@Entity
@Table(name = J.TABLE_PREFIX+"FLIGHT_TICKET")
public class FlightTicket extends BaseModel{

	private static final long serialVersionUID = 6664610944173233937L;

	/**
	 * 所出票号
	 */
	@Column(name = "TICKET_NO", length = 32)
	private String ticketNo;
	
	/**
	 * 单价
	 */
	@Column(name = "PRICE", columnDefinition = J.MONEY_COLUM)
	private Double price;
	
	/**
	 * 燃油费   必填
	 */
	@Column(name = "FUEL_SUR_TAX", columnDefinition = J.MONEY_COLUM)
	private Double fuelSurTax;
	
	/**
	 * 机场建设费   必填
	 */
	@Column(name = "AIR_PORT_TAX", columnDefinition = J.MONEY_COLUM)
	private Double airportTax;
	/**
	 * 是否废票   必填
	 */
	@Column(name = "REFUND", length = 32)
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
