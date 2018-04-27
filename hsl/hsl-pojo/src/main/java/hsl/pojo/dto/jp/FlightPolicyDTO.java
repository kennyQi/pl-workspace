package hsl.pojo.dto.jp;

import java.util.Date;

/**
 * 航班政策 model
 * 
 * @author yuxx
 * 
 */
public class FlightPolicyDTO {

	/** 政策编号 */
	private String policyId;

	/** 折扣 */
	private Integer rebate;

	/** 折扣价格 */
	private Double ticketPrice;

	/** 燃油费 */
	private Double fuelSurTax;

	/** 机建费 */
	private Double airportTax;

	/** 是否自动出票 */
	private String autoTicket;

	/** 是否提供行程单 */
	private String receipt;

	/** 支持的支付方式 */
	private String paymentType;

	/** 备注 */
	private String remark;

	/** 票款合计 */
	private Double fare;
	
	/** 出票工作时间 */
	private String tktWorktime;
	
   /** 航班有效开始日期 */
   private Date fltStart;
   
   /** 航班有效截止日期 */
   private Date fltEnd;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public Integer getRebate() {
		return rebate;
	}

	public void setRebate(Integer rebate) {
		this.rebate = rebate;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
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

	public String getAutoTicket() {
		return autoTicket;
	}

	public void setAutoTicket(String autoTicket) {
		this.autoTicket = autoTicket;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTktWorktime() {
		return tktWorktime;
	}

	public void setTktWorktime(String tktWorktime) {
		this.tktWorktime = tktWorktime;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public Date getFltStart() {
		return fltStart;
	}

	public void setFltStart(Date fltStart) {
		this.fltStart = fltStart;
	}

	public Date getFltEnd() {
		return fltEnd;
	}

	public void setFltEnd(Date fltEnd) {
		this.fltEnd = fltEnd;
	}

}