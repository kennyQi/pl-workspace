package slfx.jp.domain.model.policy;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import slfx.jp.domain.model.J;

/**
 * 
 * @类功能说明：比价过后的平台政策加价后的快照（供应商政策+平台政策）
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年8月5日上午9:36:36
 * @版本：V1.0
 * 
 */

@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "POLICY_SNAPSHOT")
public class JPPlatformPolicySnapshot extends BaseModel {

	/** 经销商 */
	@Column(name="DEALER_CODE", length=64)
	private String dealerCode;

	/** 政策编号 */
	@Column(name="POLICY_ID", length=64)
	private String policyId;

	/** 出票平台 */
	@Column(name="PLAT_CODE", length=64)
	private String platCode;
	
	/** 出票平台 */
	@Column(name="PLATform_NAME", length=64)
	private String platformName;

	/** 出票OFFICE */
	@Column(name="TKT_OFFICE", length=64)
	private String tktOffice;

	/** 是否需要换编码出票 */
	@Column(name="SWITCH_PNR", length=64)
	private String switchPnr;

	/** 航班起飞日期 */
	@Column(name="FLT_START", columnDefinition = J.DATE_COLUM)
	private Date fltStart;

	/** 航班到达日期 */
	@Column(name="FLT_END", columnDefinition = J.DATE_COLUM)
	private Date fltEnd;

	/** 票面价 */
	@Column(name="TICKET_PRICE", columnDefinition = J.MONEY_COLUM)
	private Double ticketPrice;

	/** 折扣 */
	@Column(name="REBATE", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer rebate;

	/** 税款合计 */
	@Column(name="TAX_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double taxAmount;

	/** 佣金合计 */
	@Column(name="COMM_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double commAmount;

	/** 交易手续费 */
	@Column(name="PAYMENT_FEE", columnDefinition = J.MONEY_COLUM)
	private Double paymentFee;

	/** 代理费率 */
	@Column(name="COMM_RATE", columnDefinition = J.MONEY_COLUM)
	private Double commRate;

	/** 票证类别 */
	@Column(name="TKT_TYPE", length=64)
	private String tktType;

	/** 支持的支付方式 */
	@Column(name="PAYMENT_TYPE", length=64)
	private String paymentType;

	/** 备注 */
	@Column(name="REMARK",length=255)
	private String remark;

	/** 平台工作效率(秒) */
	@Column(name="EFFICIENCY", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer efficiency;

	/** 出票工作时间 */
	@Column(name="TKT_WORK_TIME", length=64)
	private String tktWorktime;

	/** 退废票工作时间 */
	@Column(name="RFD_WORK_TIME", length=64)
	private String rfdWorktime;
	
	/**
	 * 燃油附加费
	 */
	@Column(name="FUEL_SUR_TAX", columnDefinition = J.MONEY_COLUM)
	private Double fuelSurTax;

	/**
	 * 机建费
	 */
	@Column(name="AIRPORT_TAX", columnDefinition = J.MONEY_COLUM)
	private Double airportTax;
	
	/** 
	 * 舱位代码 
	 */
	@Column(name="CLASS_CODE", columnDefinition = J.CHAR_COLUM)
	private String classCode;
	
	/** 
	 * 航班号
	 */
	@Column(name="FLIGHT_NO",length=8)
	private String flightNo;
	
	/** 废票截止时间：23:59 */
	@Column(name="WAST_WORK_TIME", length=64)
	private String wastWorkTime;
	
	/** 
	 * isVip [10]
	 */
	@Column(name="IS_VIP", length=12)
	private String isVip;
	
	/** 
	 * 平台类型
	 */
	@Column(name="PLATFORM_TYPE", length=12)
	private String platformType;
	
	/** 
	 * 创建时间
	 */
	@Column(name="CREATE_DATE", columnDefinition = J.DATE_COLUM)
	private Date createDate;

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

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

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
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

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
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

	public String getTktType() {
		return tktType;
	}

	public void setTktType(String tktType) {
		this.tktType = tktType;
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

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 提前出票天数 
	@Column(name="PRE_TKT", columnDefinition = J.TYPE_NUM_COLUM)
	private Integer preTkt;
	 * */

	/** 出票有效开始日期 
	@Column(name="TKT_START", columnDefinition = J.DATE_COLUM)
	private Date tktStart;
	 * */

	/** 出票有效截止日期 
	@Column(name="TKT_END", columnDefinition = J.DATE_COLUM)
	private Date tktEnd;
	 * */

	/** 票款合计
	@Column(name="FARE", columnDefinition = J.MONEY_COLUM)
	private Double fare;
	 *  */

	/** 运价基础
	@Column(name="FARE_BASE", length=64)
	private String fareBase;
	 *  */
	/** 是否自动出票 
	@Column(name="AUTO_TICKET", length=64)
	private String autoTicket;
	 * */

	/** 是否提供行程单 
	@Column(name="RECEIPT", length=64)
	private String receipt;
	 * */
	
	/** 是否使用平台政策 
	@Column(name="IS_USE_PLAT_POLICY", columnDefinition = J.CHAR_COLUM)
	private Boolean isUsePlatformPolicy;
	 * */
	
	/** 平台加价政策 
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLATFORM_POLICY")
	private JPPlatformPolicy platformPolicy;
	 * */
	
	/** 平台加价政策调整 
	@Column(name="PRICE_ADJUST", columnDefinition = J.MONEY_COLUM)
	private Double priceAdjust;
	 * */

}
