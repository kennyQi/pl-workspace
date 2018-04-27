package zzpl.domain.model.order;

import hg.common.component.BaseModel;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import zzpl.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_ORDER + "FLIGHT_ORDER")
public class FlightOrder extends BaseModel {

	/**
	 * ---------------------------------提交审批------------------------------------
	 * 订单编号
	 */
	@Column(name = "ORDER_NO", length = 512)
	private String orderNO;

	/**
	 * 合并付款订单号 用于多人购票支付时给支付宝用的订单号 规则为 'UN_'+一个新的订单号（并不是每一张订单中的orderNO）
	 */
	@Column(name = "UNION_ORDER_NO", length = 512)
	private String unionOrderNO;

	/**
	 * 登录用户ID
	 */
	@Column(name = "USER_ID", length = 512)
	private String userID;

	/**
	 * 公司ID
	 */
	@Column(name = "COMPANY_ID", length = 512)
	private String companyID;

	/**
	 * 部门ID
	 */
	@Column(name = "DEPARTMENT_ID", length = 512)
	private String departmentID;

	/**
	 * 登录人姓名
	 */
	@Column(name = "USER_NAME", length = 512)
	private String userName;

	/**
	 * 联系人姓名
	 */
	@Column(name = "LINK_NAME", length = 512)
	private String linkName;

	/**
	 * 联系人电话
	 */
	@Column(name = "LINK_TELEPHONE", length = 512)
	private String linkTelephone;

	/**
	 * 联系人邮箱
	 */
	@Column(name = "LINK_EMAIL", length = 512)
	private String linkEmail;

	/**
	 * 备注
	 */
	@Column(name = "NOTE", columnDefinition = M.TEXT_COLUM)
	private String note;

	/**
	 * 离港机场
	 */
	@Column(name = "DEPART_AIRPORT", length = 512)
	private String departAirport;

	/**
	 * 到港机场
	 */
	@Column(name = "ARRIVAL_AIRPORT", length = 512)
	private String arrivalAirport;

	/**
	 * 始发地
	 */
	@Column(name = "ORY_CITY", length = 512)
	private String orgCity;

	/**
	 * 始发地名称
	 */
	@Column(name = "ORY_CITY_NAME", length = 512)
	private String orgCityName;

	/**
	 * 目的地
	 */
	@Column(name = "DST_CITY", length = 512)
	private String dstCity;

	/**
	 * 目的地名称
	 */
	@Column(name = "DST_CITY_NAME", length = 512)
	private String dstCityName;

	/**
	 * 始发地航站楼
	 */
	@Column(name = "DEPART_TERM", length = 512)
	private String departTerm;

	/**
	 * 目的地航站楼
	 */
	@Column(name = "ARRIVAL_TERM", length = 512)
	private String arrivalTerm;

	/**
	 * 航空公司
	 */
	@Column(name = "AIR_COMP", length = 512)
	private String airComp;

	/**
	 * 航空公司名称
	 */
	@Column(name = "AIR_COMP_NAME", length = 512)
	private String airCompanyName;

	/**
	 * 航班
	 */
	@Column(name = "FIGHT_NO", length = 512)
	private String flightNO;

	/**
	 * 机型
	 */
	@Column(name = "PLANE_TYPE", length = 512)
	private String planeType;

	/**
	 * 出发时间
	 */
	@Column(name = "START_TIME", columnDefinition = M.DATE_COLUM)
	private Date startTime;

	/**
	 * 到达时间
	 */
	@Column(name = "END_TIME", columnDefinition = M.DATE_COLUM)
	private Date endTime;

	/**
	 * 舱位折扣
	 */
	@Column(name = "CABIN_DISCOUNT", length = 512)
	private String cabinDiscount;

	/**
	 * 舱位名称
	 */
	@Column(name = "CABIN_NAME", length = 512)
	private String cabinName;

	/**
	 * 舱位
	 */
	@Column(name = "CABIN_CODE", length = 512)
	private String cabinCode;

	/**
	 * 舱位加密字符串
	 */
	@Column(name = "ENCRYPT_STRING", length = 512)
	private String encryptString;

	/**
	 * 参考机场建设费
	 */
	@Column(name = "BUILD_FEE", columnDefinition = M.DOUBLE_COLUM)
	private Double buildFee;

	/**
	 * 参考燃油费
	 */
	@Column(name = "OIL_FEE", columnDefinition = M.DOUBLE_COLUM)
	private Double oilFee;

	/**
	 * 票面价
	 */
	@Column(name = "IBE_PRICE", length = 512)
	private String ibePrice;

	/**
	 * 提交审批时价格
	 */
	@Column(name = "COMMIT_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double commitPrice;

	/**
	 * 支付类型 1:代扣 2:客户支付 (前期意义为这两种 后期有可能 1：代扣 2：支付宝 3：微信 ..........)
	 */
	@Column(name = "PAY_TYPE", length = 512)
	private String payType;

	public static final Integer PAY_BY_TRAVEL = 1;
	public static final Integer PAY_BY_SELF = 2;

	/**
	 * 第三方支付流水号
	 */
	@Column(name = "TRADE_NO", length = 512)
	public String trade_no;

	/**
	 * 付款账号
	 */
	@Column(name = "BUYER_EMAIL", length = 512)
	public String buyer_email;

	/**
	 * 支持的支付方式 1-支付宝 2-汇付 6-IPS 7-德付通如都支持，则为1267
	 */
	@Column(name = "PAY_PLAT_FORM", columnDefinition = M.NUM_COLUM)
	private Integer payPlatform;

	/**
	 * 出票方式 1：自动出票，2：手动出票
	 */
	@Column(name = "PRINT_TICKET_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer printTicketType;

	/**
	 * 自动出票
	 */
	public static final Integer AUTO_VOTE_PRIMT_TICKET = 1;
	/**
	 * 手动出票
	 */
	public static final Integer MANAUL_VOTE_PRIMT_TICKET = 2;

	/**
	 * 出票取到 1：易行 2:去哪 3：携程 99：其他
	 */
	@Column(name = "TICKET_CHANNEL", columnDefinition = M.NUM_COLUM)
	private Integer ticketChannel;

	/**
	 * 当是99的时候填写信息
	 */
	@Column(name = "TICKET_CHANNEL_NAME", length = 512)
	private String ticketChannelName;

	/**
	 * ---------------------------------真正购票成功后经销商返回----------------------------
	 * 机票成本价
	 */
	@Column(name = "TOTAL_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPrice;

	/**
	 * 分销价加
	 */
	@Column(name = "PLAT_TOTAL_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double platTotalPrice;

	/**
	 * 用户支付价
	 */
	@Column(name = "USER_PAY_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double userPayPrice;

	/**
	 * 经销商返回的备注
	 */
	@Column(name = "MEMO", columnDefinition = M.TEXT_COLUM)
	private String memo;

	/**
	 * 经销商返回的备注
	 */
	@Column(name = "REFUSE_MEMO", columnDefinition = M.TEXT_COLUM)
	private String refuseMemo;

	/**
	 * 发生退款时退款金额
	 */
	@Column(name = "REFUND_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double refundPrice;

	/**
	 * 政策ID
	 */
	@Column(name = "PLCID", length = 512)
	private String plcid;

	/**
	 * 票号类型
	 */
	@Column(name = "TICK_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer tickType;

	/**
	 * ---------------------------------订单基本信息----------------------------------
	 * 订单创建时间
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;

	/**
	 * 订单状态
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer status;

	/**
	 * 支付状态
	 */
	@Column(name = "PAY_STATUS", columnDefinition = M.NUM_COLUM)
	private Integer payStatus;

	/**
	 * 报销凭证
	 */
	@Column(name = "REIMBURSEMENT_STATUS", columnDefinition = M.NUM_COLUM)
	private Integer reimbursementStatus;

	/**
	 * 成本中心ID
	 */
	@Column(name = "COST_CENTER_ID", length = 512)
	private String costCenterID;
	
	/**
	 * 是否是代购
	 */
	@Column(name = "REPLACE_BUY", length = 512)
	private String replaceBuy;
	
	/**
	 * 代购
	 */
	public static final String REPLACE_BUY_OK = "1";
	/**
	 * 非代购
	 */
	public static final String REPLACE_BUY_NOT = "2";
	
	/**
	 * 代购人
	 */
	@Column(name = "REPLACE_PERSON", length = 512)
	private String replacePerson;

	/**
	 * 关联乘客
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "flightOrder", cascade = CascadeType.ALL)
	private List<Passenger> passengers;

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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
	}

	public Double getUserPayPrice() {
		return userPayPrice;
	}

	public void setUserPayPrice(Double userPayPrice) {
		this.userPayPrice = userPayPrice;
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

	public String getCostCenterID() {
		return costCenterID;
	}

	public void setCostCenterID(String costCenterID) {
		this.costCenterID = costCenterID;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
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
