package slfx.jp.pojo.dto.supplier.yg;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：易购订单DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2014年7月31日下午5:00:00
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGFlightOrderDTO extends BaseJpDTO{
	
	/**
	 * 订座记录编码(PNR)
	 */
	private String pnr;
	
	/**
	 * 舱位代码
	 */
	private String classCode;
	
	/**
	 * 订座记录文本信息
	 */
	private String pnrText;
	
	/**
	 * Pat文本
	private String pataText;
	 */
	
	/**
	 * 航空公司大编码
	 */
	private String bigPnr;
	
	/**
	 * 政策编号
	 */
	private String policyId;
	
	/**
	 * 出票平台三字编码（001）
	 */
	private String platCode;
	
	/**
	 * 自有平台:P
	 * 快捷采购:Y、A、S
	 */
	private String platformType;
	
	/**
	 * VIP
	 */
	private String isVip;

	/**
	 * 账号级别
	 */
	private String accountLevel;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 工作时间00：00-23：59
	 */
	private String workTime;
	
	/**
	 * 退票时间09:00-18:00
	 */
	private String refundWorkTime;
	
	/**
	 * 废票时间:23:59止
	 */
	private String wastWorkTime;
	
	/**
	 * 供应商Office号
	 */
	private String ticketOffice;
	
	/**
	 * 供应商订单号
	 */
	private String supplierOrderNo;
	
	/**
	 * 供应商名称
	 */
	private String supplierName;
	
	/**
	 * 易购订单号
	 */
	private String ygOrderNo;
	
	/**
	 * 乘机人数
	 */
	private Integer psgCount;
	
	/**
	 * 订单金额(订单需要支付给供应商的金额)
	 */
	private Double balanceMoney;
	
	/**
	 * 票面（单人）
	 */
	private Double fare;
	
	/**
	 * 订单税款（多人税款合计）
	 */
	private Double taxAmount;
	
	/**
	 * 佣金合计（fare * psgCount + TaxAmount - balanceMoney）
	 * 自己计算
	 */
	private Double commAmount;
	
	/**
	 * 代理费率：9.4
	 */
	private Double commRate;
	
	/**
	 * 附加代理费(暂时没用)
	 */
	private Double commMoney;

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getPnrText() {
		return pnrText;
	}

	public void setPnrText(String pnrText) {
		this.pnrText = pnrText;
	}

	public String getBigPnr() {
		return bigPnr;
	}

	public void setBigPnr(String bigPnr) {
		this.bigPnr = bigPnr;
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

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getRefundWorkTime() {
		return refundWorkTime;
	}

	public void setRefundWorkTime(String refundWorkTime) {
		this.refundWorkTime = refundWorkTime;
	}

	public String getWastWorkTime() {
		return wastWorkTime;
	}

	public void setWastWorkTime(String wastWorkTime) {
		this.wastWorkTime = wastWorkTime;
	}

	public String getTicketOffice() {
		return ticketOffice;
	}

	public void setTicketOffice(String ticketOffice) {
		this.ticketOffice = ticketOffice;
	}

	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}

	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getYgOrderNo() {
		return ygOrderNo;
	}

	public void setYgOrderNo(String ygOrderNo) {
		this.ygOrderNo = ygOrderNo;
	}

	public Integer getPsgCount() {
		return psgCount;
	}

	public void setPsgCount(Integer psgCount) {
		this.psgCount = psgCount;
	}

	public Double getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
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

	public Double getCommRate() {
		return commRate;
	}

	public void setCommRate(Double commRate) {
		this.commRate = commRate;
	}

	public Double getCommMoney() {
		return commMoney;
	}

	public void setCommMoney(Double commMoney) {
		this.commMoney = commMoney;
	}

}
