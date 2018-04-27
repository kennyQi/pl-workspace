package zzpl.pojo.dto.workflow;

import java.util.Date;
import java.util.List;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class WorkflowInstanceOrderDTO extends BaseDTO {
	/**
	 * 流程ID
	 */
	private String workflowInstanceID;

	/**
	 * 订单类型 1：机票
	 */
	private String orderType;

	/**
	 * 订单ID
	 */
	private String orderID;
	/**
	 * 订单编号
	 */
	private String orderNO;
	
	/**
	 * 联系人姓名
	 */
	private String linkName;

	/**
	 * 联系人电话
	 */
	private String linkTelephone;
	
	/**
	 * 票号
	 */
	private String airID;
	
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
	 * 目的地
	 */
	private String dstCity;
	
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
	 * 出发时间
	 */
	private Date startTime;
	private String startDate;

	/**
	 * 到达时间
	 */
	private Date endTime;
	
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
	 * 实际支付价
	 */
	private Double platTotalPrice;
	
	/**
	 * 提交审批时价格
	 */
	private Double commitPrice;
	/**
	 * 经销商返回的备注
	 */
	private String memo;
	
	/**
	 * 经销商返回的备注
	 */
	private String refuseMemo;
	
	/**
	 * 总支付金额
	 */
	private Double totalPrice;
	
	/**
	 * 登录用户ID
	 */
	private String userID;
	
	/**
	 * 登录人姓名
	 */
	private String userName;
	
	/**
	 * 用户编号
	 */
	private String userNO;
	
	/**
	 * 联系电话
	 */
	private String userMobile;
	
	/**
	 * 部门名称
	 */
	private String departmentName;
	
	/**
	 * 成本中心ID
	 */
	private String costCenterID;
	/**
	 * 成本中心ID
	 */
	private String costCenterName;
	/**
	 * 订单创建时间
	 */
	private Date createTime;
	private String createDate;
	/**
	 * 机型
	 */
	private String planeType;
	
	/**
	 * 舱位名称
	 */
	private String cabinName;
	/**
	 * 舱位代码
	 */
	private String cabinCode;
	/**
	 * 出票方式 1：自动出票，2：手动出票
	 */
	private Integer printTicketType;
	
	/**
	 * 出票取到 1：易行 2:去哪 3：携程 99：其他
	 */
	private Integer ticketChannel;
	
	public static final Integer YING_XING = 1;
	
	public static final Integer QU_NA = 2;
	
	public static final Integer XIE_CHENG = 3;
	
	public static final Integer QI_TA = 99;
	
	/**
	 * 当是99的时候填写信息
	 */
	private String ticketChannelName;
	
	/**
	 * 支付状态
	 */
	private Integer payStatus;
	/**
	 * 支付状态说明
	 */
	private String payStatusMSG;
	
	/**
	 * 支付方式 1：自动扣款，2：申请人付款
	 */
	private String payType;
	
	/**
	 * 支付方式说明
	 */
	private String payTypeMSG;
	
	/**
	 * 乘客集合
	 */
	private List<PassengerDTO> passengers;
	
	/**
	 * 当前待办操作
	 */
	private String action;
	
	/**
	 * 流程名称
	 */
	private String workflowName;
	
	/***
	 *乘客姓名
	 */
	private String passengerName;
	
	public String getWorkflowInstanceID() {
		return workflowInstanceID;
	}

	public void setWorkflowInstanceID(String workflowInstanceID) {
		this.workflowInstanceID = workflowInstanceID;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
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

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNO() {
		return userNO;
	}

	public void setUserNO(String userNO) {
		this.userNO = userNO;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCostCenterID() {
		return costCenterID;
	}

	public void setCostCenterID(String costCenterID) {
		this.costCenterID = costCenterID;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<PassengerDTO> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerDTO> passengers) {
		this.passengers = passengers;
	}

	public String getPlaneType() {
		return planeType;
	}

	public void setPlaneType(String planeType) {
		this.planeType = planeType;
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

	public Integer getPrintTicketType() {
		return printTicketType;
	}

	public void setPrintTicketType(Integer printTicketType) {
		this.printTicketType = printTicketType;
	}

	public String getAirID() {
		return airID;
	}

	public void setAirID(String airID) {
		this.airID = airID;
	}

	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}

	public Integer getTicketChannel() {
		return ticketChannel;
	}

	public void setTicketChannel(Integer ticketChannel) {
		this.ticketChannel = ticketChannel;
	}

	public String getTicketChannelName() {
		return ticketChannelName;
	}

	public void setTicketChannelName(String ticketChannelName) {
		this.ticketChannelName = ticketChannelName;
	}

	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayStatusMSG() {
		return payStatusMSG;
	}

	public void setPayStatusMSG(String payStatusMSG) {
		this.payStatusMSG = payStatusMSG;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayTypeMSG() {
		return payTypeMSG;
	}

	public void setPayTypeMSG(String payTypeMSG) {
		this.payTypeMSG = payTypeMSG;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
