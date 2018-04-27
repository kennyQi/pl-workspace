package hsl.pojo.command.jp;
import hg.common.component.BaseCommand;
import hsl.pojo.dto.jp.FlightPriceInfoDTO;
import hsl.pojo.dto.jp.PassengerGNDTO;
import java.util.Date;
import java.util.List;
@SuppressWarnings("serial")
public class BookGNFlightCommand extends BaseCommand {
	/**
	 * ---------------------------------提交审批------------------------------------
	 * 订单编号
	 */
	private String orderNO;

	/**
	 * 登录用户ID
	 */
	private String userID;

	/**
	 * 登录人姓名
	 */
	private String loginName;

	/**
	 * 登录人手机
	 */
	private String userMobilePhone;
	/**
	 * 票号
	 */
	private String airId;
	
	/**
	 * 联系人姓名
	 */
	private String linkName;

	/**
	 * 联系人电话
	 */
	private String linkTelephone;

	/**
	 * 联系人邮箱
	 */
	private String linkEmail;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 乘客集合
	 */
	private List<PassengerGNDTO> passengers;

	/**
	 * 离港机场
	 */
	private String departAirport;

	/**
	 * 到港机场
	 */
	private String arrivalAirport;

	/**
	 * 始发地
	 */
	private String orgCity;

	/**
	 * 目的地
	 */
	private String dstCity;

	/**
	 * 始发地航站楼
	 */
	private String departTerm;

	/**
	 * 目的地航站楼
	 */
	private String arrivalTerm;
	/**
	 * 航空公司名称  深圳航空
	 */
	private String airCompanyName;

	/**
	 * 航空公司
	 */
	private String airComp;

	/**
	 * 航班
	 */
	private String flightNO;

	/**
	 * 机型
	 */
	private String planeType;

	/**
	 * 出发时间
	 */
	private Date startTime;

	/**
	 * 到达时间
	 */
	private Date endTime;

	/**
	 * 舱位折扣
	 */
	private String cabinDiscount;

	/**
	 * 舱位名称
	 */
	private String cabinName;
	
	/****
	 * 舱位代码
	 */
	private String cabinCode;
	/****
	 * 中转次数
	 */
	private Integer stopNumber;
	

	/**
	 * 舱位加密字符串
	 */
	private String encryptString;
	
	private Integer tickType;
	 /**
     * 支持的支付方式     
     * 1-支付宝 2-汇付 6-IPS  7-德付通如都支持，则为1267
     */
    private Integer  payPlatform;
	
	/**
	 * ---------------------------------订单基本信息----------------------------------
	 * 订单创建时间
	 */
	private Date createTime;

	/**
	 * 订单状态
	 */
	private Integer status;

	/** 支付状态 */
	private Integer payStatus;
	/**
	 * 报销凭证
	 */
	private Integer reimbursementStatus;
	/**
	 * -----------分销返回的信息---------
	 * 
	 */
	private Double totalPrice;
	
	/**
	 * 单张票价
	 */
	private Double tickPrice;

	/**
	 * 经销商返回的备注
	 */
	private String memo;

	/**
	 * 经销商返回的备注
	 */
	private String refuseMemo;
	
	/**
	 * 政策ID
	 */
	private int plcid;
	private FlightPriceInfoDTO flightPriceInfoDTO;

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setUserName(String loginName) {
		this.loginName = loginName;
	}

	public String getAirId() {
		return airId;
	}

	public void setAirId(String airId) {
		this.airId = airId;
	}

	public Double getTickPrice() {
		return tickPrice;
	}

	public void setTickPrice(Double tickPrice) {
		this.tickPrice = tickPrice;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkTelephone() {
		return linkTelephone;
	}

	public void setLinkTelephone(String linkTelephone) {
		this.linkTelephone = linkTelephone;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<PassengerGNDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerGNDTO> passengers) {
		this.passengers = passengers;
	}

	public String getDepartAirport() {
		return departAirport;
	}

	public void setDepartAirport(String departAirport) {
		this.departAirport = departAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public String getOrgCity() {
		return orgCity;
	}

	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}

	public String getDepartTerm() {
		return departTerm;
	}

	public void setDepartTerm(String departTerm) {
		this.departTerm = departTerm;
	}

	public String getArrivalTerm() {
		return arrivalTerm;
	}

	public void setArrivalTerm(String arrivalTerm) {
		this.arrivalTerm = arrivalTerm;
	}

	public String getAirComp() {
		return airComp;
	}

	public void setAirComp(String airComp) {
		this.airComp = airComp;
	}

	public String getFlightNO() {
		return flightNO;
	}

	public void setFlightNO(String flightNO) {
		this.flightNO = flightNO;
	}

	public String getPlaneType() {
		return planeType;
	}

	public void setPlaneType(String planeType) {
		this.planeType = planeType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCabinDiscount() {
		return cabinDiscount;
	}

	public void setCabinDiscount(String cabinDiscount) {
		this.cabinDiscount = cabinDiscount;
	}

	public String getCabinName() {
		return cabinName;
	}

	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}

	public Integer getTickType() {
		return tickType;
	}

	public void setTickType(Integer tickType) {
		this.tickType = tickType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getReimbursementStatus() {
		return reimbursementStatus;
	}

	public void setReimbursementStatus(Integer reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}

	public FlightPriceInfoDTO getFlightPriceInfoDTO() {
		return flightPriceInfoDTO;
	}

	public void setFlightPriceInfoDTO(FlightPriceInfoDTO flightPriceInfoDTO) {
		this.flightPriceInfoDTO = flightPriceInfoDTO;
	}

	public String getUserMobilePhone() {
		return userMobilePhone;
	}

	public void setUserMobilePhone(String userMobilePhone) {
		this.userMobilePhone = userMobilePhone;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRefuseMemo() {
		return refuseMemo;
	}

	public void setRefuseMemo(String refuseMemo) {
		this.refuseMemo = refuseMemo;
	}


	public int getPlcid() {
		return plcid;
	}

	public void setPlcid(int plcid) {
		this.plcid = plcid;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}

	public String getCabinCode() {
		return cabinCode;
	}

	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}

	public Integer getStopNumber() {
		return stopNumber;
	}

	public void setStopNumber(Integer stopNumber) {
		this.stopNumber = stopNumber;
	}

	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}
	
}
