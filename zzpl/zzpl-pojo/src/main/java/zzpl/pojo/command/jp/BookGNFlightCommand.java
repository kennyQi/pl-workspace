package zzpl.pojo.command.jp;

import hg.common.component.BaseCommand;

import java.util.Date;

@SuppressWarnings("serial")
public class BookGNFlightCommand extends BaseCommand {
	/**
	 * ---------------------------------提交审批------------------------------------
	 * 订单编号
	 */
	private String orderNO;

	/**
	 * 合并付款订单号 用于多人购票支付时给支付宝用的订单号 规则为 'UN_'+一个新的订单号（并不是每一张订单中的orderNO）
	 */
	private String unionOrderNO;

	/**
	 * 登录用户ID
	 */
	private String userID;

	/**
	 * 登录人姓名
	 */
	private String userName;

	/**
	 * 公司ID
	 */
	private String companyID;

	/**
	 * 部门ID
	 */
	private String departmentID;

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
	private String passengerListJSON;

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
	 * 始发地
	 */
	private String orgCityName;

	/**
	 * 目的地
	 */
	private String dstCity;

	/**
	 * 目的地
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
	 * 航空公司名称 深圳航空
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

	private Double platTotalPrice;

	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
	}

	/**
	 * 最终总价
	 */
	private Double totalPrice;

	private Integer tickType;

	/**
	 * ---------------------------------订单基本信息----------------------------------
	 * 订单创建时间
	 */
	private Date createTime;

	/**
	 * 订单状态
	 */
	private Integer payStatus;

	/**
	 * 订单状态
	 */
	private Integer status;

	/**
	 * 报销凭证
	 */
	private Integer reimbursementStatus;

	/**
	 * 成本中心
	 */
	private String costCenterID;

	/**
	 * 支付类型 1:代扣 2:客户支付 (前期意义为这两种 后期有可能 1：代扣 2：支付宝 3：微信 ..........)
	 */
	private String payType;

	public static final Integer PAY_BY_TRAVEL = 1;
	public static final Integer PAY_BY_SELF = 2;
	
	/**
	 * 是否是代购
	 */
	private String replaceBuy;
	
	/**
	 * 代购人
	 */
	private String replacePerson;

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getAirId() {
		return airId;
	}

	public void setAirId(String airId) {
		this.airId = airId;
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

	public String getPassengerListJSON() {
		return passengerListJSON;
	}

	public void setPassengerListJSON(String passengerListJSON) {
		this.passengerListJSON = passengerListJSON;
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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
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

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getUnionOrderNO() {
		return unionOrderNO;
	}

	public void setUnionOrderNO(String unionOrderNO) {
		this.unionOrderNO = unionOrderNO;
	}

	public String getReplaceBuy() {
		return replaceBuy;
	}

	public void setReplaceBuy(String replaceBuy) {
		this.replaceBuy = replaceBuy;
	}

	public String getReplacePerson() {
		return replacePerson;
	}

	public void setReplacePerson(String replacePerson) {
		this.replacePerson = replacePerson;
	}

}
