package plfx.yeexing.qo.admin;

import hg.common.component.BaseQo;

import java.util.Date;

/****
 * 
 * @类功能说明：平台订单查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月1日下午2:34:28
 * @版本：V1.0
 *
 */
public class PlatformOrderQO extends BaseQo {

	private static final long serialVersionUID = 5952689219411916553L;

	/****
	 * 排序条件
	 */
	private Boolean createDateAsc;
	/**
	 * 易行天下订单号（易行天下系统中唯一）一次只能传一个易行订单
	 */
	private String yeeXingOrderId;
	/***
	 * 平台订单号
	 */
	private String orderNo;
	
	/****
	 * 下单开始时间  (用于时间段查询订单)
	 */
	private Date createDateFrom; 
	/***
	 * 下单结束时间  (用于时间段查询订单)
	 */
	private Date createDateTo; 
	
	/***
	 * 订单状态
	 */
	private Integer[] status; 
	
    /***
     * admin使用订单状态
     */
	private Integer tempStatus; 
	/***
	 * 订单支付状态
	 */
	private Integer payStatus;  
	/***
	 * 下单登录名
	 */
	private String loginName;
	/****
	 * 乘机人QO
	 */
	private PassengerQO  passengerQO;  
	
	/***
	 * 乘机人
	 */
    private String passengerName; 
    
	/***
	 * 航班号
	 */
	private String flightNo;
	
	/***
	 *经销商订单编号
	 */
	private String dealerOrderCode; 
	
	private boolean isFilterCancel = false;

    /***
     * 只显示支付成功的订单
     */
	private boolean justDisPaySucc = false; 
	
	/**
	 * 是否支付
	 */
	private Boolean isPay;
	
	private String pnr;
	
	/**
	 * 经销商代码
	 */
	private String dealerCode;
	
	/**
	 * 供应商名称
	 */
	private String suppShortName;
	
	/***
	 * 航空公司
	 */
	private String airCompName;
	
	
	public String getAirCompName() {
		return airCompName;
	}

	public void setAirCompName(String airCompName) {
		this.airCompName = airCompName;
	}

	public String getSuppShortName() {
		return suppShortName;
	}

	public void setSuppShortName(String suppShortName) {
		this.suppShortName = suppShortName;
	}

	private String type = "0"; // 异常订单为1，普通订单为0，默认为0；
	
	private String supplierOrderNo;
	
	/**
	 * 退废种类
	 * T:退废票订单
	 * C:取消的订单
	 * A:排除退废和取消的订单
	 */
	private String refundType;
	

	public String getPassengerName() {
		return passengerName;
	}

	public String getPnr() {
		return pnr;
	}

	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}

	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public PlatformOrderQO() {
		super();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getYeeXingOrderId() {
		return yeeXingOrderId;
	}

	public void setYeeXingOrderId(String yeeXingOrderId) {
		this.yeeXingOrderId = yeeXingOrderId;
	}

	public Boolean getIsPay() {
		return isPay;
	}

	public void setIsPay(Boolean isPay) {
		this.isPay = isPay;
	}

	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Date getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
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

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public PassengerQO getPassengerQO() {
		return passengerQO;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPassengerQO(PassengerQO passengerQO) {
		this.passengerQO = passengerQO;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}


	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public boolean isFilterCancel() {
		return isFilterCancel;
	}

	public void setFilterCancel(boolean isFilterCancel) {
		this.isFilterCancel = isFilterCancel;
	}

	public boolean isJustDisPaySucc() {
		return justDisPaySucc;
	}

	public void setJustDisPaySucc(boolean justDisPaySucc) {
		this.justDisPaySucc = justDisPaySucc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
