package slfx.jp.pojo.dto.order;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：平台订单DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:56:19
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class PlatformOrderDTO extends BaseJpDTO{
	
	
	/**
	 * 预订单号
	 */
	private String subsOrderNo;
	
	private String pnr;
	
	/**
	 * PNR文本信息
	 */
	private String pnrText;
	
	/**
	 * Pat文本
	 */
	private String pataText;
	
	
	
	/**  易购返回信息  **/
	
	
	/**
	 * 出票平台（BT）
	 */
	private String platCode;
	
	/**
	 * 平台订单号
	 */
	private String platOrderNo;
	
	/**
	 * 易购订单号
	 */
	private String orderNo;
		
	/**
	 * 订单金额
	 */
	private double balanceMoney;
	
	/**
	 * 票面价（单人）
	 */
	private double fare;
	
	/**
	 * 税款合计（单人）
	 */
	private double taxAmount;
	
	/**
	 * 佣金合计
	 */
	private double commAmount;
	
	/**
	 * 代理费率
	 */
	private double commRate;
	
	/**
	 * 附加代理费
	 */
	private double commMoney;
	
	/**
	 * 供应商Office号
	 */
	private String ticketOffice;

	public String getSubsOrderNo() {
		return subsOrderNo;
	}

	public void setSubsOrderNo(String subsOrderNo) {
		this.subsOrderNo = subsOrderNo;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getPnrText() {
		return pnrText;
	}

	public void setPnrText(String pnrText) {
		this.pnrText = pnrText;
	}

	public String getPataText() {
		return pataText;
	}

	public void setPataText(String pataText) {
		this.pataText = pataText;
	}

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	public String getPlatOrderNo() {
		return platOrderNo;
	}

	public void setPlatOrderNo(String platOrderNo) {
		this.platOrderNo = platOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public double getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(double commAmount) {
		this.commAmount = commAmount;
	}

	public double getCommRate() {
		return commRate;
	}

	public void setCommRate(double commRate) {
		this.commRate = commRate;
	}

	public double getCommMoney() {
		return commMoney;
	}

	public void setCommMoney(double commMoney) {
		this.commMoney = commMoney;
	}

	public String getTicketOffice() {
		return ticketOffice;
	}

	public void setTicketOffice(String ticketOffice) {
		this.ticketOffice = ticketOffice;
	}
	
	
	
	
	
	
}
