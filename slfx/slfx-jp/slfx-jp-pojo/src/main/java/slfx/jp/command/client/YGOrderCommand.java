package slfx.jp.command.client;

import java.util.List;

import slfx.jp.pojo.dto.flight.FlightPassengerDTO;

/**
 * 
 * @类功能说明：易购下单command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:07:48
 * @版本：V1.0
 *
 */
public class YGOrderCommand {
	/**
	 * PNR编码:必填
	 */
	private String pnr;
	
	/**
	 * 仓位代码
	 */
	private String classCode;
	
	/**
	 * PNR文本信息：必填
	 */
	private String pnrText;
	
	/**
	 * Pat文本：必填
	 */
	private String pataText;
	
	/**
	 * 出票类型:[Yeego] [B2B] [BSP]
	 * 空值默认为Yeego：可选
	 */
	private String ticketType;
	
	/**
	 * 航空公司大编码：必填
	 */
	private String bigPNR;
	
	/**
	 * 政策编号：必填
	 */
	private String policyId;
	
	/**
	 * 返点 （数字：例如6.2）：必填
	 */
	private Double policyCommsion;
	
	/**
	 * 出票平台（三字码：001）：必填
	 */
	private String platCode;
	
	/**
	 * P:自有平台：必填
	 * Y、A、S: 快捷采购
	 */
	private String platformType;
	
	/**
	 * 是否是平台VIP帐号：和帐号级别两者必须填写一个
	 */
	private String isVip;
	
	/**
	 * 账号级别
	 */
	private String accountLevel;
	
	/**
	 * 结算价格：必填
	 */
	private double balanceMoney;
	
	/**
	 * 备注：可选
	 */
	private String remark;
	
	/**
	 * 工作时间（：可选）00：00-23：59
	 */
	private String workTime;
	
	/**
	 * 退票时间：可选     09:00-18:00
	 */
	private String refundWorkTime;
	
	/**
	 * 废票时间    ：可选    18:00止
	 */
	private String wastWorkTime;
	
	/**
	 * 存在相同PNR未支付订单是否强制删除：可选
	 * [1]:强制删除(默认)
	 * [2]:不删除返回报错
	 */
	private String forceDelete;
	
	/**
	 * 预定OFFICE：可选 （PNR是由哪个OFFICE号预订的）
	 */
	private String ticketOffice;
	
	/**
	 * 是否需要换编码出票Y/N
	private String switchPnr;
	 */
	
	/**
	 * 航程列表(用于验证PNR信息，如果是多航段就写多个Flights)
	 */
	private YGFlightCommand flight;
	
	/**
	 * 旅客信息列表（用于验证PNR信息，如果是多人就写多个Passengers） 
	 */
	private List<FlightPassengerDTO> passengers;

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

	public String getPataText() {
		return pataText;
	}

	public void setPataText(String pataText) {
		this.pataText = pataText;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getBigPNR() {
		return bigPNR;
	}

	public void setBigPNR(String bigPNR) {
		this.bigPNR = bigPNR;
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

	public String isVip() {
		return isVip;
	}

	public void setVip(String isVip) {
		this.isVip = isVip;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public Double getBalanceMoney() {
		return balanceMoney;
	}

	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
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

	public Double getPolicyCommsion() {
		return policyCommsion;
	}

	public void setPolicyCommsion(Double policyCommsion) {
		this.policyCommsion = policyCommsion;
	}

	public void setBalanceMoney(double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

	public String getForceDelete() {
		return forceDelete;
	}

	public void setForceDelete(String forceDelete) {
		this.forceDelete = forceDelete;
	}

	public String getTicketOffice() {
		return ticketOffice;
	}

	public void setTicketOffice(String ticketOffice) {
		this.ticketOffice = ticketOffice;
	}

	public List<FlightPassengerDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<FlightPassengerDTO> passengers) {
		this.passengers = passengers;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public YGFlightCommand getFlight() {
		return flight;
	}

	public void setFlight(YGFlightCommand flight) {
		this.flight = flight;
	}

	/*public String getSwitchPnr() {
		return switchPnr;
	}

	public void setSwitchPnr(String switchPnr) {
		this.switchPnr = switchPnr;
	}*/
	
}
