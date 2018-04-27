package slfx.jp.pojo.dto.flight;

import java.util.Date;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：航班政策DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:53:47
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class SlfxFlightPolicyDTO extends BaseJpDTO{
	
   /** 政策编号 */
   private String policyId;
   
   /** 出票平台code */
   private String platCode;
   
   /** 出票平台名字 */
   private String platformName;
   
   /** 经销商code */
   private String dealerCode;
   
   /** 出票OFFICE */
   private String tktOffice;
   
   /** 是否需要换编码出票 */
   private String switchPnr;
   
   /** 航班有效开始日期 */
   private Date fltStart;
   
   /** 航班有效截止日期 */
   private Date fltEnd;
   
   /** 提前出票天数 */
   private Integer preTkt;
   
   /** 出票有效开始日期 */
   private Date tktStart;
   
   /** 出票有效截止日期 */
   private Date tktEnd;
   
   /** 票款合计 */
   private Double fare;
   
   /** 票面价 */
   private Double ticketPrice;
      
   /** 折扣 */
   private Integer rebate;
   
   /** 税款合计 */
   private Double taxAmount;
   
   /** 佣金合计 */
   private Double commAmount;
   
   /** 交易手续费 */
   private Double paymentFee;
   
   /** 代理费率 */
   private Double commRate;
   
   /** 运价基础 */
   private String fareBase;
   
   /** 票证类别 */
   private String tktType;
   
   /** 是否自动出票 */
   private String autoTicket;
   
   /** 是否提供行程单 */
   private String receipt;
   
   /** 支持的支付方式 */
   private String paymentType;
   
   /** 备注 */
   private String remark;
   
   /** 平台工作效率(秒) */
   private Integer efficiency;
   
   /** 出票工作时间 */
   private String tktWorktime;
   
   /** 退废票工作时间 */
   private String rfdWorktime;
   
   /**
    * 燃油附加费
    */
   private Double fuelSurTax;
   
   /**
    * 机建费
    */
   private Double airportTax;
   
   private String IsSpecial;
   
   /** 是否使用平台政策 */
   private Boolean isUsePlatformPolicy;
	
   /** 平台加价政策调整 */
   private Double priceAdjust;
   
   /** 平台废票时间 */
   private String wastWorkTime;
   
   /** 
    * 航班号
    */
   private String flightNo;
   
   /** 
	 * 舱位代码 
	 */
	private String classCode;
	
	/** 
	 * isVip [10]
	 */
	private String isVip;
	
	/** 
	 * 平台类型
	 */
	private String platformType;
   
	public String getPolicyId() {
		return policyId;
	}
	
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	
	public String getPlatCode() {
		return platCode;
	}
	
	public String getDealerCode() {
		return dealerCode;
	}
	
	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}
	
	public String getTktOffice() {
		return tktOffice;
	}
	
	public void setTktOffice(String tktOffice) {
		this.tktOffice = tktOffice;
	}
	
	public String getSwitchPnr() {
		return switchPnr;
	}
	
	public void setSwitchPnr(String switchPnr) {
		this.switchPnr = switchPnr;
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
	
	public Integer getPreTkt() {
		return preTkt;
	}
	
	public void setPreTkt(Integer preTkt) {
		this.preTkt = preTkt;
	}
	
	public Date getTktStart() {
		return tktStart;
	}
	
	public void setTktStart(Date tktStart) {
		this.tktStart = tktStart;
	}
	
	public Date getTktEnd() {
		return tktEnd;
	}
	
	public void setTktEnd(Date tktEnd) {
		this.tktEnd = tktEnd;
	}
	
	public Double getFare() {
		return fare;
	}
	
	public void setFare(Double fare) {
		this.fare = fare;
	}
	
	public Integer getRebate() {
		return rebate;
	}
	
	public void setRebate(Integer rebate) {
		this.rebate = rebate;
	}
	
	public Double getTaxAmount() {
		return taxAmount;
	}
	
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	
	public Double getCommAmount() {
		return commAmount;
	}
	
	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}
	
	public Double getPaymentFee() {
		return paymentFee;
	}
	
	public void setPaymentFee(Double paymentFee) {
		this.paymentFee = paymentFee;
	}
	
	public Double getCommRate() {
		return commRate;
	}
	
	public void setCommRate(Double commRate) {
		this.commRate = commRate;
	}
	
	public String getFareBase() {
		return fareBase;
	}
	
	public void setFareBase(String fareBase) {
		this.fareBase = fareBase;
	}
	
	public String getTktType() {
		return tktType;
	}
	
	public void setTktType(String tktType) {
		this.tktType = tktType;
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
	
	public Integer getEfficiency() {
		return efficiency;
	}
	
	public void setEfficiency(Integer efficiency) {
		this.efficiency = efficiency;
	}
	
	public String getTktWorktime() {
		return tktWorktime;
	}
	
	public void setTktWorktime(String tktWorktime) {
		this.tktWorktime = tktWorktime;
	}
	
	public String getRfdWorktime() {
		return rfdWorktime;
	}
	
	public void setRfdWorktime(String rfdWorktime) {
		this.rfdWorktime = rfdWorktime;
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

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	
	public String getIsSpecial() {
		return IsSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		IsSpecial = isSpecial;
	}
	
	public Boolean getIsUsePlatformPolicy() {
		return isUsePlatformPolicy;
	}

	public void setIsUsePlatformPolicy(Boolean isUsePlatformPolicy) {
		this.isUsePlatformPolicy = isUsePlatformPolicy;
	}

	public Double getPriceAdjust() {
		return priceAdjust;
	}

	public void setPriceAdjust(Double priceAdjust) {
		this.priceAdjust = priceAdjust;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getWastWorkTime() {
		return wastWorkTime;
	}

	public void setWastWorkTime(String wastWorkTime) {
		this.wastWorkTime = wastWorkTime;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	@Override
	public String toString() {
		return "FlightPolicyDTO [policyId=" + policyId + ", platCode="
				+ platCode + ", dealerCode=" + dealerCode + ", tktOffice="
				+ tktOffice + ", switchPnr=" + switchPnr + ", fltStart="
				+ fltStart + ", fltEnd=" + fltEnd + ", preTkt=" + preTkt
				+ ", tktStart=" + tktStart + ", tktEnd=" + tktEnd + ", fare="
				+ fare + ", ticketPrice=" + ticketPrice + ", rebate=" + rebate
				+ ", taxAmount=" + taxAmount + ", commAmount=" + commAmount
				+ ", paymentFee=" + paymentFee + ", commRate=" + commRate
				+ ", fareBase=" + fareBase + ", tktType=" + tktType
				+ ", autoTicket=" + autoTicket + ", receipt=" + receipt
				+ ", paymentType=" + paymentType + ", remark=" + remark
				+ ", efficiency=" + efficiency + ", tktWorktime=" + tktWorktime
				+ ", rfdWorktime=" + rfdWorktime + ", fuelSurTax=" + fuelSurTax
				+ ", airportTax=" + airportTax + ", IsSpecial=" + IsSpecial
				+ ", isUsePlatformPolicy=" + isUsePlatformPolicy
				+ ", priceAdjust=" + priceAdjust + "]";
	}

}