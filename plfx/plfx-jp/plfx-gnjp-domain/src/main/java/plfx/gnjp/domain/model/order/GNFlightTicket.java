package plfx.gnjp.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import plfx.jp.domain.model.J;

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
@Entity
@Table(name = J.TABLE_PREFIX_GN + "FLIGHT_TICKET")
public class GNFlightTicket extends BaseModel{

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

	public GNFlightTicket() {
		
	}
	
	//新增机票信息
	public GNFlightTicket(String ticketNum, double fare) {
		this.setId(UUIDGenerator.getUUID());
		this.setTicketNo(ticketNum);
		this.setPrice(fare);
	}

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
