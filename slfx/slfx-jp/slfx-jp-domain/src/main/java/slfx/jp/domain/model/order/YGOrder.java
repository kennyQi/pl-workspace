package slfx.jp.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import slfx.jp.command.client.YGOrderCommand;
import slfx.jp.domain.model.J;
import slfx.jp.pojo.dto.supplier.yg.YGFlightOrderDTO;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @类功能说明：易购订单 model
 * @类修改者：
 * @修改日期：
 * @修改说明：易购下单请求信息和返回信息全部存储
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月30日下午4:44:28
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "YG_ORDER")
public class YGOrder extends BaseModel{

	/**
	 * PNR编码
	 */
	@Column(name = "PNR", length = 6)
	private String pnr;
	
	/**
	 * 舱位代码
	 */
	@Column(name = "CLASS_CODE", length = 6)
	private String classCode;
	
	/**
	 * PNR文本信息
	 */
	@Column(name = "PNR_TEXT", columnDefinition = J.TEXT_COLUM)
	private String pnrText;
	
	/**
	 * Pat文本
	@Column(name = "PATA_TEXT", columnDefinition = J.TEXT_COLUM)
	private String pataText;
	 */
	
	/**
	 * 航空公司大编码
	 */
	@Column(name = "BIG_PNR", length = 6)
	private String bigPNR;
	
	/**
	 * 政策编号
	 */
	@Column(name = "POLICY_ID",length = 64)
	private String policyId;
	
	/**
	 * 出票平台（001）
	 */
	@Column(name = "PLAT_CODE", length = 3)
	private String platCode;
	
	/**
	 * P:自有平台
	 * Y、A、S: 快捷采购
	 */
	@Column(name = "PLAT_FORM_TYPE", length = 1)
	private String platformType;
	
	/**
	 * 是否是平台VIP帐号
	 */
	@Column(name = "IS_VIP", length = 5)
	private String isVip;
	
	/**
	 * 账号级别
	 */
	@Column(name = "ACCOUNT_LEVEL", length = 4)
	private String accountLevel;
	
	/**
	 * 备注
	 */
	@Column(name = "REMARK", columnDefinition = J.TEXT_COLUM)
	private String remark;
	
	/**
	 * 工作时间00：00-23：59
	 */
	@Column(name = "WORK_TIME", length = 16)
	private String workTime;
	
	/**
	 * 退票时间09:00-18:00
	 */
	@Column(name = "REFUND_WORK_TIME", length = 16)
	private String refundWorkTime;
	
	/**
	 * 废票时间18:00止
	 */
	@Column(name = "WAST_WORK_TIME", length = 16)
	private String wastWorkTime;
	
	/**
	 * 供应商Office号
	 */
	@Column(name = "TICKET_OFFICE", length = 8)
	private String ticketOffice;
	
	/**
	 * 供应商订单号
	 */
	@Column(name = "SUPPLIER_ORDER_NO",length = 64)
	private String supplierOrderNo;

	/**
	 * 易购订单号
	 */
	@Column(name = "YG_ORDER_NO", length = 64)
	private String ygOrderNo;

	/**
	 * 出票类型:[Yeego] [B2B] [BSP]
	 * 空值默认为Yeego
	@Column(name = "TICKET_TYPE", length = 6)
	private String ticketType;
	 */
	
	/**
	 * 乘机人数
	 */
	@Column(name = "PSG_COUNT", columnDefinition = J.DOUBLE_COLUM)
	private Integer psgCount;
	
	/**
	 * 订单金额(订单需要支付给供应商的金额)
	 */
	@Column(name = "BALANCE_MONEY", columnDefinition = J.MONEY_COLUM)
	private Double balanceMoney;
	
	/**
	 * 票面（单人）
	 */
	@Column(name = "FARE", columnDefinition = J.MONEY_COLUM)
	private Double fare;
	
	/**
	 * 订单税款（多人税款合计）
	 */
	@Column(name = "TAX_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double taxAmount;
	
	/**
	 * 佣金合计（fare * psgCount + TaxAmount - balanceMoney）
	 * 自己计算
	 */
	@Column(name = "COMM_AMOUNT", columnDefinition = J.MONEY_COLUM)
	private Double commAmount;
	
	/**
	 * 代理费率：9.4
	 */
	@Column(name = "COMM_RATE", columnDefinition = J.MONEY_COLUM)
	private Double commRate;
	
	/**
	 * 航程(用于验证PNR信息，如果是多航段就写多个Flights)
	 */
	@Transient
	private YGFlight flight;
	
	/**
	 * 航班信息JSON串
	 */
	@Column(name = "FLIGHT_JSON", columnDefinition = J.TEXT_COLUM)
	private String flightJson;
	
	/**
	 * 旅客信息列表（用于验证PNR信息，如果是多人就写多个Passengers） 
	 */
	@Transient
	private List<YGPassenger> passengers;

	/**
	 * 旅客信息列表 JSON串
	 */
	@JSONField(serialize = false)
	@Column(name = "PASSENGERS_JSON", columnDefinition = J.TEXT_COLUM)
	private String passengersJson;
	
	/** 创建时间 */
	@Column(name = "CREATE_DATE", columnDefinition = J.DATE_COLUM)
	private Date createDate;
	
	/** 订单状态 */
	@Column(name = "STATUS", length = 64)
	private String status;

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
		if(commAmount == null || commAmount == 0){
			if(balanceMoney != null && taxAmount != null && fare != null && psgCount != null){
				this.commAmount = (fare * psgCount + taxAmount - balanceMoney);
			}
		}else{
			this.commAmount = 0d;			
		}
	}

	public Double getCommRate() {
		return commRate;
	}

	public void setCommRate(Double commRate) {
		this.commRate = commRate;
	}

	public YGFlight getFlight() {
		return flight;
	}

	public void setFlight(YGFlight flight) {
		this.flight = flight;
	}

	public String getFlightJson() {
		return flightJson;
	}

	public void setFlightJson(String flightJson) {
		this.flightJson = flightJson;
	}

	public List<YGPassenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<YGPassenger> passengers) {
		this.passengers = passengers;
	}

	public String getPassengersJson() {
		return passengersJson;
	}

	public void setPassengersJson(String passengersJson) {
		this.passengersJson = passengersJson;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	/**
	 * 附加代理费
	@Column(name = "COMM_MONEY", columnDefinition = J.MONEY_COLUM)
	private double commMoney;
	 */

	/**
	 * 存在相同PNR未支付订单是否强制删除
	 * [1]:强制删除(默认)
	 * [2]:不删除返回报错
	@Column(name = "FORCE_DELETE", length = 8)
	private String forceDelete;
	 */
	
	/**
	 * 预定OFFICE （PNR是由哪个OFFICE号预订的）
	@Column(name = "BOOKING_OFFICE", length = 8)
	private String bookingOffice;
	 */
	public YGOrder(YGOrderCommand ygOrderCommand,YGFlightOrderDTO yGFlightOrderDTO){
		setId(UUIDGenerator.getUUID());
		setStatus(YGOrderStatus.HK);
		setSupplierOrderNo(yGFlightOrderDTO.getSupplierOrderNo());
		setYgOrderNo(yGFlightOrderDTO.getYgOrderNo());
		setPsgCount(yGFlightOrderDTO.getPsgCount());
		setBalanceMoney(yGFlightOrderDTO.getBalanceMoney());
		setFare(yGFlightOrderDTO.getFare());
		setTaxAmount(yGFlightOrderDTO.getTaxAmount());
		//没有传值就自己计算
		setCommAmount(yGFlightOrderDTO.getCommAmount());
		setCommRate(yGFlightOrderDTO.getCommRate());
		setTicketOffice(yGFlightOrderDTO.getTicketOffice());
		setPnr(ygOrderCommand.getPnr());
		setClassCode(ygOrderCommand.getClassCode());
		setPlatCode(ygOrderCommand.getPlatCode());
		setPnrText(ygOrderCommand.getPnrText());
		setBigPNR(ygOrderCommand.getBigPNR());
		setPolicyId(ygOrderCommand.getPolicyId());
		setPlatformType(ygOrderCommand.getPlatformType());
		setIsVip(ygOrderCommand.getIsVip());
		setAccountLevel(ygOrderCommand.getAccountLevel());
		setRemark(ygOrderCommand.getRemark());
		setWorkTime(ygOrderCommand.getWorkTime());
		setRefundWorkTime(ygOrderCommand.getRefundWorkTime());
		setWastWorkTime(ygOrderCommand.getWastWorkTime());
		setFlightJson(JSON.toJSONString(ygOrderCommand.getFlight()));
		setPassengersJson(JSON.toJSONString(ygOrderCommand.getPassengers()));
		setCreateDate(new Date());
		setStatus(YGOrderStatus.HK);
	}
	
	public void updateYg(){
		String tempId = UUIDGenerator.getUUID();
		setId(tempId);
		setCreateDate(new Date());
	}
	
	public YGOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
}