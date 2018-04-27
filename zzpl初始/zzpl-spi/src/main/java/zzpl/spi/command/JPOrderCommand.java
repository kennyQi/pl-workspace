package zzpl.spi.command;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import slfx.jp.pojo.dto.flight.FlightPassengerDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightDTO;
import slfx.jp.pojo.dto.order.Passenger;
import zzpl.pojo.dto.jp.JPOrderStatusConstant;

@SuppressWarnings("serial")
public class JPOrderCommand implements Serializable {
	
	
	/**
	 * 订单ID
	 */
	private String id;
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 易购单号
	 */
	private String ygOrderNo;					

	/**
	 * 供应商订单号
	 */
	private String supplierOrderNo;
	/**
	 * abe订单
	 */
	private String abeOrderId;
	
	private String pnr;	
	
	/**
	 * 票号
	 */
	private String[] tktNo;		
	
	/**
	 * 订单来源
	 */
	private String orderSource;	
	
	/**
	 * 乘机人列表：商城端用
	 */
	private List<FlightPassengerDTO> passangers=new ArrayList<FlightPassengerDTO>();	
	
	/**
	 * 乘机人列表：admin端用
	 */
	private Set<Passenger> passangerList;	

	/**
	 * 仓位
	 */
	private String classNo;				
	
	/**
	 * 航程	
	 */
	private String flightay;	
	
	/**
	 * 出发城市代码:admin端用
	 */
	private String startCityCode;
	/**
	 * 目的城市代码：admin端用
	 */
	private String endCityCode;
	/**
	 * 支付金额
	 */
	private Double payAmount;	
	
	/**
	 * 票面价金额
	 */
	private Double tktAmount;
	/**
	 *所有乘客的  机场总建设费 + 燃油费 + 其他
	 */
	private Double tktTax;
	/** 平台赚取的返点：用户支付总价的百分比 */
	private Double commRate;
	
	/** 平台所得佣金 ：用户支付总价的百分比计算后的具体所得 */
	private Double commAmount;
	
	
	/**
	 * 商家信息
	 */
	private String agencyName;
	
	/**
	 * 商家联系方式
	 */
	private String[] agencyTel;	
	
	
	private SlfxFlightDTO flightDTO;
	
	/**
	 * 订单创建时间 
	 */
	private Date createDate;

	/**
	 * 经销商订单号 
	 */
	private String dealerOrderCode;
	
	/**
	 * 供应商支付方式 
	 */
	private String supplierPayType;
	
	/**
	 * 航班信息快照
	 */
	private String flightSnapshotJSON;
	
	/**
	 * 出票平台
	 */
	private String platCode;
		
	/**
	 * 订单状态
	 */
	private Integer status;				
	
	/**
	 * 订单状态:admin端用
	 */
	private JPOrderStatusConstant orderStatus;
	
	/**
	 * /异常订单为1，普通订单为0，默认为0；
	 */
	private String type;  
	
	private Double adjust;
	
	private Double platPolicyPirce;
	
	/**
	 * 退废金额
	 */
	private Double backPrice;
	/** 支付状态 */
	private Integer payStatus;
	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Double getBackPrice() {
		return backPrice;
	}

	public void setBackPrice(Double backPrice) {
		this.backPrice = backPrice;
	}

	
	/** 异常订单调整 金额 admin端用*/
	private Double adjustAmount;
	
	/**
	 * 异常订单调整凭证 admin端用
	 */
	private String voucherPicture;
	
	/**
	 * 异常订单调整原因 admin端用
	 */
	private String adjustReason;
	
	/**
	 * 商城回掉地址
	 */
	private String notifyUrl;

	private String flag;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getYgOrderNo() {
		return ygOrderNo;
	}

	public void setYgOrderNo(String ygOrderNo) {
		this.ygOrderNo = ygOrderNo;
	}

	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}

	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}

	public String getAbeOrderId() {
		return abeOrderId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAbeOrderId(String abeOrderId) {
		this.abeOrderId = abeOrderId;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String[] getTktNo() {
		return tktNo;
	}

	public void setTktNo(String[] tktNo) {
		this.tktNo = tktNo;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public List<FlightPassengerDTO> getPassangers() {
		return passangers;
	}

	public void setPassangers(List<FlightPassengerDTO> passangers) {
		this.passangers = passangers;
	}

	public Set<Passenger> getPassangerList() {
		return passangerList;
	}

	public void setPassangerList(Set<Passenger> passangerList) {
		this.passangerList = passangerList;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getFlightay() {
		return flightay;
	}

	public void setFlightay(String flightay) {
		this.flightay = flightay;
	}

	public String getStartCityCode() {
		return startCityCode;
	}

	public void setStartCityCode(String startCityCode) {
		this.startCityCode = startCityCode;
	}

	public String getEndCityCode() {
		return endCityCode;
	}

	public void setEndCityCode(String endCityCode) {
		this.endCityCode = endCityCode;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Double getTktAmount() {
		return tktAmount;
	}

	public void setTktAmount(Double tktAmount) {
		this.tktAmount = tktAmount;
	}

	public Double getTktTax() {
		return tktTax;
	}

	public void setTktTax(Double tktTax) {
		this.tktTax = tktTax;
	}

	public Double getCommRate() {
		return commRate;
	}

	public void setCommRate(Double commRate) {
		this.commRate = commRate;
	}

	public Double getCommAmount() {
		return commAmount;
	}

	public void setCommAmount(Double commAmount) {
		this.commAmount = commAmount;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String[] getAgencyTel() {
		return agencyTel;
	}

	public void setAgencyTel(String[] agencyTel) {
		this.agencyTel = agencyTel;
	}

	public SlfxFlightDTO getFlightDTO() {
		return flightDTO;
	}

	public void setFlightDTO(SlfxFlightDTO flightDTO) {
		this.flightDTO = flightDTO;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public String getSupplierPayType() {
		return supplierPayType;
	}

	public void setSupplierPayType(String supplierPayType) {
		this.supplierPayType = supplierPayType;
	}

	public String getFlightSnapshotJSON() {
		return flightSnapshotJSON;
	}

	public void setFlightSnapshotJSON(String flightSnapshotJSON) {
		this.flightSnapshotJSON = flightSnapshotJSON;
	}

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public JPOrderStatusConstant getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(JPOrderStatusConstant orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAdjust() {
		return adjust;
	}

	public void setAdjust(Double adjust) {
		this.adjust = adjust;
	}

	public Double getPlatPolicyPirce() {
		return platPolicyPirce;
	}

	public void setPlatPolicyPirce(Double platPolicyPirce) {
		this.platPolicyPirce = platPolicyPirce;
	}

	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getVoucherPicture() {
		return voucherPicture;
	}

	public void setVoucherPicture(String voucherPicture) {
		this.voucherPicture = voucherPicture;
	}

	public String getAdjustReason() {
		return adjustReason;
	}

	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
