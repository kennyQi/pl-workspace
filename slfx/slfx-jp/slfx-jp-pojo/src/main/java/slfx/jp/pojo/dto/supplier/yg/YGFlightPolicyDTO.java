package slfx.jp.pojo.dto.supplier.yg;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：易购航班政策DTO(同步返回结果)
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月16日下午4:17:29
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGFlightPolicyDTO extends BaseJpDTO{
	
	/** 是否成功 Y:成功  */
	private String flag;
	
	/** 政策编号  */
	private String policyId;
	
	/** 出票平台编号   */
	private String platCode;

	/** 出票平台代码  */
	private String platCodeForShort;

	/** 出票平台代码  */
	private String platformMappingCode;

	/** 出票平台名称  */
	private String platformName;

	/** 是否vip如：false  */
	private String isVip;

	/**  出票平台类型 */
	private String platformType;

	/** 出票 OFFICE  */
	private String tktOffice;

	/** 是否需要换编码出票  */
	private String switchPnr;

	/** 航班有效开始日期 YYYY/MM/DD */
	private String fltStart ;

	/**  航班有效截止日期 YYYY/MM/DD */
	private String fltEnd;

	/**  提前出票天数  */
	private String preTkt;

	/** 出票有效开始日期 yyyy-MM-dd */
	private String tktStart ;

	/** 出票有效截止日期 yyyy-MM-dd */
	private String tktEnd ;

	/** 票款合计  */
	private String fare ;

	/**  折扣 */
	private String rebate ;

	/** 税款合计  */
	private String taxAmount;

	/** 佣金合计   */
	private String commAmount ;

	/** 交易手续费  */
	private String paymentFee ;

	/** 代理费率   */
	private String commRate;

	/**  附加代理费 */
	private String commMoney;

	/**  运价基础  */
	private String fareBase ;

	/**  票证类别 BSP/B2B */
	private String tktType;

	/**  是否自动出票  */
	private String autoTicket;

	/** 是否提供行程单   */
	private String receipt;

	/** 支持的支付方式   */
	private String paymentType;

	/**  备注  */
	private String remark;

	/** 退票规则（暂不支持）   */
	private String refundRule;

	/** 平台工作效率(分钟)  */
	private String efficiency;

	/** 出票工作时间 08:00-23:59   */
	private String tktWorktime;

	/**  退废票工作时间  */
	private String rfdWorktime;

	/** 票面价  */
	private String price;

	/**  支付金额  */
	private String settlePrice;
	
	private String IsSpecial;

	/** 废票时间   */
	private String wastWorkTime;
	
	/**  成人税金 *//*
	private String aduTaxFare;

	*//** 成人奖励票面  *//*
	private String aduRewardTicketFare;

	*//** 成人奖励返点  *//*
	private String aduRewardDiscount;

	*//** 成人开票费  *//*
	private String aduEtdzFare;

	*//** 成人奖励返款  *//*
	private String aduRewardMoney;

	*//** 儿童基础返点  *//*
	private String chdBaseDiscount;

	*//** 儿童奖励返点 *//*
	private String chdRewardDiscount;

	*//** 儿童税金  *//*
	private String chdTaxFare;

	*//** 儿童奖励票面  *//*
	private String chdRewardTicketFare;

	*//** 儿童开票费 *//*
	private String chdEtdzFare;

	*//** 儿童奖励返款  *//*
	private String chdRewardMoney;*/

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

	public String getPlatCodeForShort() {
		return platCodeForShort;
	}

	public void setPlatCodeForShort(String platCodeForShort) {
		this.platCodeForShort = platCodeForShort;
	}

	public String getPlatformMappingCode() {
		return platformMappingCode;
	}

	public void setPlatformMappingCode(String platformMappingCode) {
		this.platformMappingCode = platformMappingCode;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
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

	public String getFltStart() {
		return fltStart;
	}

	public void setFltStart(String fltStart) {
		this.fltStart = fltStart;
	}

	public String getFltEnd() {
		return fltEnd;
	}

	public void setFltEnd(String fltEnd) {
		this.fltEnd = fltEnd;
	}

	public String getPreTkt() {
		return preTkt;
	}

	public void setPreTkt(String preTkt) {
		this.preTkt = preTkt;
	}

	public String getTktStart() {
		return tktStart;
	}

	public void setTktStart(String tktStart) {
		this.tktStart = tktStart;
	}

	public String getTktEnd() {
		return tktEnd;
	}

	public void setTktEnd(String tktEnd) {
		this.tktEnd = tktEnd;
	}

	public String getFare() {
		return fare;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}

	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

	public String getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(String commAmount) {
		this.commAmount = commAmount;
	}

	public String getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(String paymentFee) {
		this.paymentFee = paymentFee;
	}

	public String getCommRate() {
		return commRate;
	}

	public void setCommRate(String commRate) {
		this.commRate = commRate;
	}

	public String getCommMoney() {
		return commMoney;
	}

	public void setCommMoney(String commMoney) {
		this.commMoney = commMoney;
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

	public String getRefundRule() {
		return refundRule;
	}

	public void setRefundRule(String refundRule) {
		this.refundRule = refundRule;
	}

	public String getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(String efficiency) {
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSettlePrice() {
		return settlePrice;
	}

	public void setSettlePrice(String settlePrice) {
		this.settlePrice = settlePrice;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIsSpecial() {
		return IsSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		IsSpecial = isSpecial;
	}

	public String getWastWorkTime() {
		return wastWorkTime;
	}

	public void setWastWorkTime(String wastWorkTime) {
		this.wastWorkTime = wastWorkTime;
	}

	/*public String getAduTaxFare() {
		return aduTaxFare;
	}

	public void setAduTaxFare(String aduTaxFare) {
		this.aduTaxFare = aduTaxFare;
	}

	public String getAduRewardTicketFare() {
		return aduRewardTicketFare;
	}

	public void setAduRewardTicketFare(String aduRewardTicketFare) {
		this.aduRewardTicketFare = aduRewardTicketFare;
	}

	public String getAduRewardDiscount() {
		return aduRewardDiscount;
	}

	public void setAduRewardDiscount(String aduRewardDiscount) {
		this.aduRewardDiscount = aduRewardDiscount;
	}

	public String getAduEtdzFare() {
		return aduEtdzFare;
	}

	public void setAduEtdzFare(String aduEtdzFare) {
		this.aduEtdzFare = aduEtdzFare;
	}

	public String getAduRewardMoney() {
		return aduRewardMoney;
	}

	public void setAduRewardMoney(String aduRewardMoney) {
		this.aduRewardMoney = aduRewardMoney;
	}

	public String getChdBaseDiscount() {
		return chdBaseDiscount;
	}

	public void setChdBaseDiscount(String chdBaseDiscount) {
		this.chdBaseDiscount = chdBaseDiscount;
	}

	public String getChdRewardDiscount() {
		return chdRewardDiscount;
	}

	public void setChdRewardDiscount(String chdRewardDiscount) {
		this.chdRewardDiscount = chdRewardDiscount;
	}

	public String getChdTaxFare() {
		return chdTaxFare;
	}

	public void setChdTaxFare(String chdTaxFare) {
		this.chdTaxFare = chdTaxFare;
	}

	public String getChdRewardTicketFare() {
		return chdRewardTicketFare;
	}

	public void setChdRewardTicketFare(String chdRewardTicketFare) {
		this.chdRewardTicketFare = chdRewardTicketFare;
	}

	public String getChdEtdzFare() {
		return chdEtdzFare;
	}

	public void setChdEtdzFare(String chdEtdzFare) {
		this.chdEtdzFare = chdEtdzFare;
	}

	public String getChdRewardMoney() {
		return chdRewardMoney;
	}

	public void setChdRewardMoney(String chdRewardMoney) {
		this.chdRewardMoney = chdRewardMoney;
	}*/
   
}