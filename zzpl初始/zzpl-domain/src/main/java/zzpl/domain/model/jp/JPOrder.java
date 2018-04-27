package zzpl.domain.model.jp;

import hg.common.component.BaseModel;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import slfx.api.v1.request.command.jp.APIJPOrderCreateCommand;
import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightPolicyDTO;
import slfx.jp.pojo.dto.flight.TicketDTO;
import zzpl.domain.model.M;
import zzpl.pojo.dto.jp.FlightDTO;

/**
 * 商城机票订单
 * 
 * @author yuxx
 * 
 */
@SuppressWarnings("serial")
//@Entity
//@Table(name = "T_JP_ORDER")
public class JPOrder extends BaseModel {

	/** 商城订单号 */
	@Column(name = "DEALER_ORDER_CODE", unique = true)
	private String dealerOrderCode;

	/** 平台订单号 */
	@Column(name = "ORDER_CODE", unique = true)
	private String orderCode;
	
	/**第三方支付单号 */
	@Column(name = "PAY_TRADE_NO")
	private String payTradeNo;
	
	/** 付款支付宝账号 */
	@Column(name = "BUYER_EMAIL")
	private String buyerEmail;
	
	/** 订单支付总金额 */
	@Column(name = "PAY_AMOUNT")
	private Double payAmount;

	/**
	 * 单人票面价
	 */
	@Column(name = "TICKET_PRICE")
	private Double ticketPrice;
	
	/**
	 * 单张机票税款（基建+燃油）
	 */
	@Column(name = "SINGLE_TAX_AMOUNT")
	private Double singleTaxAmount;
	
	/** 下单时间 */
	@Column(name = "CREATE_DATE")
	private Date createDate;

	/** PNR */
	@Column(name = "PNR")
	private String pnr;

	/**
	 * 乘机人 FlightPassangerDto[]
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
	private Set<JPPassanger> passangers;

	/**
	 * 订单状态
	 */
	@Column(name = "STATUS")
	private Integer status;
	
	/** 支付状态 */
	@Column(name = "PAYSTATUS")
	private Integer payStatus;


	/** 支付类型 */
	@Column(name = "SUPPLIER_PAY_TYPE")
	private String supplierPayType;
	/**
	 * 退废金额
	 */
	@Column(name = "BACKPRICE")
	private Double backPrice;
	
	/**
	 * 退废实际金额
	 */
	@Column(name = "RETURNEDPRICE")
	private Double returnedPrice;

	/**
	 * 订单对应航班信息 瞬态 FlightDTO
	 */
	@Transient
	private FlightDTO flightDTO;

	/**
	 * 下单用户
	 */
	@Embedded
	private JPOrderUser orderUser;

	/**
	 * 整个机票订单的model总快照，状态设为空
	 */
	@Column(name = "JP_ORDER_SNAPSHOT", columnDefinition = M.TEXT_COLUM)
	private String jpOrderSnapshot;
	
	/**
	 * 废票截至时间
	 */
	@Column(name = "WAST_WORK_TIME")
	private Date wastWorkTime;
	
	/**
	 * 工作时间
	 */
	@Column(name = "WORK_TIME")
	private String workTime;
	
	/**
	 * 退票时间
	 */
	@Column(name = "REFUND_WORK_TIME")
	private String refundWorkTime;
	
	
	/**
	 * 订单操作记录
	 */
	@OneToMany(mappedBy = "order")
	private List<JPOrderOperationLog> orderOperationLogs;
	
	/**
	 * 订单航班舱位
	 */
	@Column(name = "CLASS_CODE", length = 64)
	private String classCode;
	
	/**
	 * 航班起飞时间
	 */
	@Column(name = "FLIGHT_START_TIME")
	private String flightStartTime;
	
	/**  出票平台code  供应商的代号如：（001或BT）、（002或B5） 对应agencyName */
	@Column(name = "PLAT_CODE", length = 64)
	private String platCode;
	
	/**
	 * 商家信息
	 */
	@Column(name = "AGENCY_NAME", length = 16)
	private String agencyName;
	
	/**
	 * 来源标识：0 mobile , 1  pc
	 * 经销商所属用户KEY(规则 = 来源标识_key)
	 */
	@Column(name = "CLIENT_USER_KEY")
	private String clientUserKey;
	
	
	//////////
	
	public Double getReturnedPrice() {
		return returnedPrice;
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

	public void setReturnedPrice(Double returnedPrice) {
		this.returnedPrice = returnedPrice;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public Double getBackPrice() {
		return backPrice;
	}

	public void setBackPrice(Double backPrice) {
		this.backPrice = backPrice;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}
	
	public String getOrderCode() {
		return orderCode;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSupplierPayType() {
		return supplierPayType;
	}

	public void setSupplierPayType(String supplierPayType) {
		this.supplierPayType = supplierPayType;
	}

	public JPOrderUser getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(JPOrderUser orderUser) {
		this.orderUser = orderUser;
	}

	public String getJpOrderSnapshot() {
		return jpOrderSnapshot;
	}

	public void setJpOrderSnapshot(String jpOrderSnapshot) {
		this.jpOrderSnapshot = jpOrderSnapshot;
	}

	public Set<JPPassanger> getPassangers() {
		return passangers;
	}

	public void setPassangers(Set<JPPassanger> passangers) {
		this.passangers = passangers;
	}

	public FlightDTO getFlightDTO() {
		return flightDTO;
	}

	public void setFlightDTO(FlightDTO flightDTO) {
		this.flightDTO = flightDTO;
	}

	public Date getWastWorkTime() {
		return wastWorkTime;
	}

	public void setWastWorkTime(Date wastWorkTime) {
		this.wastWorkTime = wastWorkTime;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Double getSingleTaxAmount() {
		return singleTaxAmount;
	}

	public void setSingleTaxAmount(Double singleTaxAmount) {
		this.singleTaxAmount = singleTaxAmount;
	}

	public List<JPOrderOperationLog> getOrderOperationLogs() {
		return orderOperationLogs;
	}

	public void setOrderOperationLogs(List<JPOrderOperationLog> orderOperationLogs) {
		this.orderOperationLogs = orderOperationLogs;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getFlightStartTime() {
		return flightStartTime;
	}

	public void setFlightStartTime(String flightStartTime) {
		this.flightStartTime = flightStartTime;
	}

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getClientUserKey() {
		return clientUserKey;
	}

	public void setClientUserKey(String clientUserKey) {
		this.clientUserKey = clientUserKey;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public void initFromCommand(APIJPOrderCreateCommand command) {
		this.setId(UUIDGenerator.getUUID());
		this.setSupplierPayType(command.getPayType());
		this.setClassCode(command.getClassCode());
		this.setClientUserKey(command.getClientUserKey());
		this.setOrderUser(BeanMapperUtils.getMapper().map(command.getLinker(), JPOrderUser.class));
		this.getOrderUser().setUserId(command.getLinker().getId());
		
		List<FlightPassengerDTO> passangerDtos = command.getPassangers();
		Set<JPPassanger> passangers = new HashSet<JPPassanger>();
		for (FlightPassengerDTO passangerDto : passangerDtos) {
			JPPassanger passanger =  BeanMapperUtils.getMapper().map(passangerDto, JPPassanger.class);
			
			SlfxFlightPolicyDTO flightPolicyDto = passangerDto.getFlightPolicyDTO();
			JPFlightPolicy flightPolicy = BeanMapperUtils.getMapper().map(flightPolicyDto, JPFlightPolicy.class);
			
			TicketDTO ticketDTO = passangerDto.getTicketDto();
			JPTicket ticket = BeanMapperUtils.getMapper().map(ticketDTO, JPTicket.class);
			
			passanger.setId(UUIDGenerator.getUUID());
			passanger.setBirthday(this.getBirthdayFromIdCardNo(passanger.getCardNo()));
			passanger.setFlightPolicy(flightPolicy);
			passanger.setTicket(ticket);
			passanger.setId(UUIDGenerator.getUUID());
			passanger.setOrder(this);
			passangers.add(passanger);
		}
		
		this.setPassangers(passangers);
	}
	
	public String getBirthdayFromIdCardNo(String idCardNo) {
		return StringUtils.isBlank(idCardNo) || idCardNo.length() < 14 ? "" :
			idCardNo.substring(6, 10) + "-" + idCardNo.substring(10, 12) + "-" + idCardNo.substring(12, 14);
	}

}
