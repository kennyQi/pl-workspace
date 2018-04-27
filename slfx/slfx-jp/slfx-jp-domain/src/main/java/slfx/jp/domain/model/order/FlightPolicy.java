package slfx.jp.domain.model.order;

import hg.common.component.BaseModel;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import slfx.jp.domain.model.J;

/**
 * 
 * @类功能说明：比价后选中的航班政策 model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:26:59
 * @版本：V1.0
 *
 */
@Entity
@Table(name = J.TABLE_PREFIX + "FLIGHT_POLICY")
public class FlightPolicy extends BaseModel{
	
	private static final long serialVersionUID = 1L;

	/** 政策编号 */
   @Column(name = "POLICY_ID", length = 64)
   private String policyId;
   
   /** 出票平台 */
   @Column(name = "PLAT_CODE", length = 64)
   private String platCode;
   
   /** 出票OFFICE */
   @Column(name = "TKT_OFFICE", length = 16)
   private String tktOffice;
   
   /** 是否需要换编码出票 */
   @Column(name = "SWITCH_PNR", length = 8)
   private String switchPnr;
   
   /** 航班有效开始日期 */
   @Column(name = "FLT_START", columnDefinition = J.DATE_COLUM)
   private Date fltStart;
   
   /** 航班有效截止日期 */
   @Column(name = "FLT_END", columnDefinition = J.DATE_COLUM)
   private Date fltEnd;
   
   /** 提前出票天数 */
   @Column(name = "PRE_TKT", columnDefinition = J.TYPE_NUM_COLUM)
   private Integer preTkt;
   
   /** 出票有效开始日期 */
   @Column(name = "TKT_START", columnDefinition = J.DATE_COLUM)
   private Date tktStart;
   
   /** 出票有效截止日期 */
   @Column(name = "TKT_END", columnDefinition = J.DATE_COLUM)
   private Date tktEnd;
   
   /** 票款合计 */
   @Column(name = "FARE", columnDefinition = J.MONEY_COLUM)
   private Double fare;
   
   /** 折扣 */
   @Column(name = "REBATE", columnDefinition = J.TYPE_NUM_COLUM)
   private Integer rebate;
   
   /** 税款合计 */
   @Column(name = "TAX_AMOUNT", columnDefinition = J.MONEY_COLUM)
   private Double taxAmount;
   
   /** 佣金合计 */
   @Column(name = "COMM_AMOUNT", columnDefinition = J.MONEY_COLUM)
   private Double commAmount;
   
   /** 交易手续费 */
   @Column(name = "PAYMENT_FEE", columnDefinition = J.MONEY_COLUM)
   private Double paymentFee;
   
   /** 代理费率 */
   @Column(name = "COMM_RATE", columnDefinition = J.MONEY_COLUM)
   private Double commRate;
   
   /** 运价基础 */
   @Column(name = "FARE_BASE", length = 16)
   private String fareBase;
   
   /** 票证类别 */
   @Column(name = "TKT_TYPE", length = 16)
   private String tktType;
   
   /** 是否自动出票 */
   @Column(name = "AUTO_TICKET", length = 8)
   private String autoTicket;
   
   /** 是否提供行程单 */
   @Column(name = "RECEIPT", length = 8)
   private String receipt;
   
   /** 支持的支付方式 */
   @Column(name = "PAYMENT_TYPE", length = 16)
   private String paymentType;
   
   /** 备注 */
   @Column(name = "REMARK", length = 500)
   private String remark;
   
   /** 平台工作效率(分钟) */
   @Column(name = "EFFICIENCY", columnDefinition = J.NUM_COLUM)
   private Integer efficiency;
   
   /** 出票工作时间 */
   @Column(name = "TKT_WORK_TIME", length = 16)
   private String tktWorktime;
   
   /** 退废票工作时间 */
   @Column(name = "RFD_WORK_TIME", length = 16)
   private String rfdWorktime;

	public String getPolicyId() {
		return policyId;
	}
	
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	
	public String getPlatCode() {
		return platCode;
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

}