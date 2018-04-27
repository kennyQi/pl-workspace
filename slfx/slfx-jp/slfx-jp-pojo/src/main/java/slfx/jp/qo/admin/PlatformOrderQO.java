package slfx.jp.qo.admin;

import hg.common.component.BaseQo;

import java.util.Date;


/**
 * 
 * @类功能说明：平台订单查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年7月31日下午5:13:28
 * @版本：V1.0
 * 
 */
public class PlatformOrderQO extends BaseQo {

	private static final long serialVersionUID = 5952689219411916553L;

	// ------------------延迟加载条件------------------
	private Boolean passengerLazy = true;
	private Boolean comparePriceLazy = true;

	// ------------------排序条件------------------
	private Boolean createDateAsc;

	private String dealerOrderCode;  //经销商订单号
//	private String id;	//订单ID
	private String orderNo;  //订单号
	private String ygOrderNo; //易购单号
	private String payTradeNo; //第三方支付单号	private String supplierOrderNo;  //供应商订单号（商家单号）
	private String suppShortName;  // 供应商代码


	private String suppId;
	private String userId; // 下单人
	private String loginName;// 下单登录名
	private String linkerName;// 下单登录名
	private Date createDateFrom; // 开始时间
	private Date createDateTo; // 结束时间

	private String fromCityCode; // 出发地
	private String toCityCode; // 到达地

	private String passenger; // 乘机人
	private String pnr;
	private PassengerQO  passengerQO;  //乘机人QO
	private String carrier; //航空公司代码
	private String airCompName; //航空公司

	private Integer[] status; // 订单状态
	private Integer tempStatus; // admin使用订单状态
	private Integer payStatus;   //	订单支付状态


	private String flightNo;
	private String startDepartureTime;
	private String endDepartureTime;

	private String type; // 异常订单为1，普通订单为0，默认为0；

	private String dealerCode; // 经销商代码

	private Boolean isPay;

	private boolean isFilterCancel = false;


	private boolean justDisPaySucc = false;  //只显示支付成功的订单
	
	private PlatformOrderBackQO platformOrderBackQO; //退废票查询QO
	
	private String supplierOrderNo;

	public PlatformOrderQO() {
		super();
	}

	public PlatformOrderBackQO getPlatformOrderBackQO() {
		return platformOrderBackQO;
	}

	public void setPlatformOrderBackQO(PlatformOrderBackQO platformOrderBackQO) {
		this.platformOrderBackQO = platformOrderBackQO;
	}
	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public String getAirCompName() {
		return airCompName;
	}

	public void setAirCompName(String airCompName) {
		this.airCompName = airCompName;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getStartDepartureTime() {
		return startDepartureTime;
	}

	public void setStartDepartureTime(String startDepartureTime) {
		this.startDepartureTime = startDepartureTime;
	}

	public String getEndDepartureTime() {
		return endDepartureTime;
	}

	public void setEndDepartureTime(String endDepartureTime) {
		this.endDepartureTime = endDepartureTime;
	}

	public String getType() {
		return type;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLinkerName() {
		return linkerName;
	}

	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateDateFrom() {
		return createDateFrom;
	}

	public Date getCreateDateTo() {
		return createDateTo;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public String getFromCityCode() {
		return fromCityCode;
	}

	// public String getId() {
	// return id;
	// }
	public String getOrderNo() {
		return orderNo;
	}

	public String getPassenger() {
		return passenger;
	}

	public PassengerQO getPassengerQO() {
		return passengerQO;
	}

	public String getPnr() {
		return pnr;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}

	public String getToCityCode() {
		return toCityCode;
	}

	public String getSuppId() {
		return suppId;
	}

	public void setSuppId(String suppId) {
		this.suppId = suppId;
	}

	public String getUserId() {
		return userId;
	}

	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public void setFromCityCode(String fromCityCode) {
		this.fromCityCode = fromCityCode;
	}

	//
	// public void setId(String id) {
	// this.id = id;
	// }
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setPassenger(String passenger) {
		this.passenger = passenger;
	}

	public void setPassengerQO(PassengerQO passengerQO) {
		this.passengerQO = passengerQO;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public void setToCityCode(String toCityCode) {
		this.toCityCode = toCityCode;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getPassengerLazy() {
		return passengerLazy;
	}

	public void setPassengerLazy(Boolean passengerLazy) {
		this.passengerLazy = passengerLazy;
	}

	public String getSuppShortName() {
		return suppShortName;
	}

	public void setSuppShortName(String suppShortName) {
		this.suppShortName = suppShortName;
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

	public Boolean getComparePriceLazy() {
		return comparePriceLazy;
	}

	public void setComparePriceLazy(Boolean comparePriceLazy) {
		this.comparePriceLazy = comparePriceLazy;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Boolean getIsPay() {
		return isPay;
	}

	public void setIsPay(Boolean isPay) {
		this.isPay = isPay;
	}

	public boolean getIsFilterCancel() {
		return isFilterCancel;
	}

	public void setIsFilterCancel(boolean isFilterCancel) {
		this.isFilterCancel = isFilterCancel;
	}

	public boolean isJustDisPaySucc() {
		return justDisPaySucc;
	}

	public void setJustDisPaySucc(boolean justDisPaySucc) {
		this.justDisPaySucc = justDisPaySucc;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo;
	}

	public void setFilterCancel(boolean isFilterCancel) {
		this.isFilterCancel = isFilterCancel;
	}


	public Integer[] getStatus() {
		return status;
	}

	public void setStatus(Integer... status) {
		this.status = status;
	}

	public Integer getTempStatus() {
		return tempStatus;
	}

	public void setTempStatus(Integer tempStatus) {
		this.tempStatus = tempStatus;
	}


}
