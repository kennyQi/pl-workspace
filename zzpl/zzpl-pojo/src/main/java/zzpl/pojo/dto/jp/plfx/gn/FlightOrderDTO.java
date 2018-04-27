package zzpl.pojo.dto.jp.plfx.gn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import zzpl.pojo.dto.BaseDTO;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;

@SuppressWarnings("serial")
public class FlightOrderDTO extends BaseDTO {
	
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
	 * 公司ID
	 */
	private String companyID;
	
	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 部门ID
	 */
	private String departmentID;

	/**
	 * 登录人姓名
	 */
	private String userName;

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
	 * 始发地名称
	 */
	private String orgCityName;

	/**
	 * 目的地
	 */
	private String dstCity;

	/**
	 * 目的地名称
	 */
	private String dstCityName;

	/**
	 * 始发地航站楼
	 */
	private String departTerm;

	/**
	 * 目的地航站楼
	 */
	private String arrivalTerm;

	/**
	 * 航空公司
	 */
	private String airComp;

	/**
	 * 航空公司名称
	 */
	private String airCompanyName;

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
	private String startDate;

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

	/**
	 * 舱位
	 */
	private String cabinCode;

	/**
	 * 舱位加密字符串
	 */
	private String encryptString;

	/**
	 * 参考机场建设费
	 */
	private Double buildFee;

	/**
	 * 参考燃油费
	 */
	private Double oilFee;

	/**
	 * 票面价
	 */
	private String ibePrice;

	/**
	 * 提交审批时价格
	 */
	private Double commitPrice;

	/**
	 * 支付类型 1:代扣 2:客户支付
	 * (前期意义为这两种 后期有可能 1：代扣 2：支付宝 3：微信 ..........)
	 */
	private String payType;

	/**
	 * 第三方支付流水号
	 */
	public String trade_no;
	
	/**
	 * 付款账号
	 */
	public String buyer_email;
	
	/**
	 * 支持的支付方式 1-支付宝 2-汇付 6-IPS 7-德付通如都支持，则为1267
	 */
	private Integer payPlatform;

	/**
	 * 出票方式 1：自动出票，2：手动出票
	 */
	private Integer printTicketType;


	/**
	 * ---------------------------------真正购票成功后经销商返回----------------------------
	 * 机票成本价
	 */
	private Double totalPrice;
	
	/**
	 * 分销价加
	 */
	private Double platTotalPrice;
	
	/**
	 * 经销商返回的备注
	 */
	private String memo;

	/**
	 * 经销商返回的备注
	 */
	private String refuseMemo;
	
	/**
	 * 发生退款时退款金额
	 */
	private Double refundPrice;

	/**
	 * 政策ID
	 */
	private String plcid;

	/**
	 * 票号类型
	 */
	private Integer tickType;

	/**
	 * ---------------------------------订单基本信息----------------------------------
	 * 订单创建时间
	 */
	private Date createTime;
	
	private String dateTime;

	/**
	 * 订单状态
	 */
	private Integer status;
	
	/**
	 * 订单状态描述
	 */
	private String statusMSG;

	/**
	 * 支付状态
	 */
	private Integer payStatus;

	/**
	 * 报销凭证
	 */
	private Integer reimbursementStatus;
	
	private List<PassengerGNDTO> passengers;
	
	private String passengerName;
	
	private String airID;
	
	/**
	 * 成本中心ID
	 */
	private String costCenterID;
	
	private String orderType = FlightOrderDTO.GN_FLIGHT_ORDER;
	
	private Double cosaofPrice;
	
	public final static String GN_FLIGHT_ORDER = "1";

	public final static String GJ_FLIGHT_ORDER = "2";
	
	/**
	 * 申请分销价格失败
	 */
	private String failed;

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

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getOrgCityName() {
		return orgCityName;
	}

	public void setOrgCityName(String orgCityName) {
		this.orgCityName = orgCityName;
	}

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}

	public String getDstCityName() {
		return dstCityName;
	}

	public void setDstCityName(String dstCityName) {
		this.dstCityName = dstCityName;
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

	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.startDate = sdf.format(startTime);
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

	public String getCabinCode() {
		return cabinCode;
	}

	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}

	public Double getBuildFee() {
		return buildFee;
	}

	public void setBuildFee(Double buildFee) {
		this.buildFee = buildFee;
	}

	public Double getOilFee() {
		return oilFee;
	}

	public void setOilFee(Double oilFee) {
		this.oilFee = oilFee;
	}

	public String getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(String ibePrice) {
		this.ibePrice = ibePrice;
	}

	public Double getCommitPrice() {
		return commitPrice;
	}

	public void setCommitPrice(Double commitPrice) {
		this.commitPrice = commitPrice;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public Integer getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}

	public Integer getPrintTicketType() {
		return printTicketType;
	}

	public void setPrintTicketType(Integer printTicketType) {
		this.printTicketType = printTicketType;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
		if (totalPrice == -1) {
			this.totalPrice = null;
		}
	}

	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
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

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}

	public String getPlcid() {
		return plcid;
	}

	public void setPlcid(String plcid) {
		this.plcid = plcid;
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.dateTime = sdf.format(createTime);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		if (status == FlightOrderStatus.APPROVAL_SUCCESS) {
			this.statusMSG = FlightOrderStatus.APPROVAL_SUCCESS_MSG;
		} else if (status == FlightOrderStatus.PRINT_TICKET_SUCCESS){
			this.statusMSG = FlightOrderStatus.PRINT_TICKET_SUCCESS_MSG;
		}else if (status == FlightOrderStatus.PRINT_TICKET_FAILED){
			this.statusMSG = FlightOrderStatus.PRINT_TICKET_FAILED_MSG;
		}else if (status == FlightOrderStatus.CANCEL_APPROVAL_SUCCESS){
			this.statusMSG = FlightOrderStatus.CANCEL_APPROVAL_SUCCESS_MSG;
		}else if (status == FlightOrderStatus.CANCEL_TICKET_SUCCESS){
			this.statusMSG = FlightOrderStatus.CANCEL_TICKET_SUCCESS_MSG;
		}else if (status == FlightOrderStatus.CANCEL_TICKET_FAILED){
			this.statusMSG = FlightOrderStatus.CANCEL_TICKET_FAILED_MSG;
		}else if(status==FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS){
			this.statusMSG = FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS_MSG;
		}else if(status==FlightOrderStatus.CONFIRM_ORDER_SUCCESS){
			this.statusMSG = FlightOrderStatus.CONFIRM_ORDER_SUCCESS_MSG;
		}else if(status==FlightOrderStatus.CONFIRM_ORDER_FAILED){
			this.statusMSG = FlightOrderStatus.CONFIRM_ORDER_FAILED_MSG;
		}else if(status==FlightOrderStatus.ORDER_PROCESS_VOID){
			this.statusMSG = FlightOrderStatus.ORDER_PROCESS_VOID_MSG;
		}else if(status==FlightOrderStatus.CONFIRM_CANCEL_ORDER_FAILED){
			this.statusMSG = FlightOrderStatus.CONFIRM_CANCEL_ORDER_FAILED_MSG;
		}else if(status==FlightOrderStatus.REFUSE_TICKET_SUCCESS){
			this.statusMSG = FlightOrderStatus.REFUSE_TICKET_SUCCESS_MSG;
		}else {
			this.statusMSG = "订单异常";
		}
		
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

	public String getCostCenterID() {
		return costCenterID;
	}

	public void setCostCenterID(String costCenterID) {
		this.costCenterID = costCenterID;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getStatusMSG() {
		return statusMSG;
	}

	public void setStatusMSG(String statusMSG) {
		this.statusMSG = statusMSG;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Double getCosaofPrice() {
		return cosaofPrice;
	}

	public void setCosaofPrice(Double cosaofPrice) {
		this.cosaofPrice = cosaofPrice;
	}

	public List<PassengerGNDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerGNDTO> passengers) {
		this.passengers = passengers;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getAirID() {
		return airID;
	}

	public void setAirID(String airID) {
		this.airID = airID;
	}

	public String getFailed() {
		return failed;
	}

	public void setFailed(String failed) {
		this.failed = failed;
	}
	
}
