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
@Table(name = M.TABLE_PREFIX_ZZPL_ORDER + "FLIGHT_ORDER_GJ")
public class GJFlightOrder extends BaseModel {

	/**
	 * ---------------------------------提交审批------------------------------------
	 * 订单编号
	 */
	@Column(name = "ORDER_NO", length = 512)
	private String orderNO;

	/**
	 * 分销平台订单号
	 */
	@Column(name = "PLATFORM_ORDER_ID", length = 512)
	private String platformOrderId;
	
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
	 * 联系地址
	 */
	@Column(name = "LINK_ADDRESS", length = 512)
	private String linkAddress;
	/**
	 * 备注
	 */
	@Column(name = "NOTE", columnDefinition = M.TEXT_COLUM)
	private String note;

	/**
	 * 舱位加密字符串
	 */
	@Column(name = "ENCRYPT_STRING", length = 512)
	private String encryptString;

	/**
	 * 税金
	 */
	@Column(name = "TOTAL_TAX", length = 512)
	private Double totalTax;
	
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
	 * ---------------------------------真正购票成功后经销商返回----------------------------
	 * 总支付金额
	 */
	@Column(name = "TOTAL_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPrice;

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
	 * 支付状态
	 * 
	 * @see GJ#ORDER_PAY_STATUS_MAP
	 */
	@Column(name = "PAY_STATUS", columnDefinition = M.NUM_COLUM)
	private Integer payStatus;
	
	/** 分销订单状态
	 * 
	 * @see GJ#ORDER_STATUS_MAP
	 */
	@Column(name = "FX_ORDER_STATUS", columnDefinition = M.NUM_COLUM)
	private Integer currentValue;
	
	/**
	 * 是否为异常订单
	 */
	@Column(name = "EXCEPTION_ORDER" , columnDefinition = M.CHAR_COLUM)
	private Boolean exceptionOrder = false;

	/**
	 * 异常订单调整 金额
	 */
	@Column(name = "ADJUST_AMOUNT", columnDefinition = M.DOUBLE_COLUM)
	private Double adjustAmount;

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
	 * 报销凭证
	 */
	@Column(name = "REIMBURSEMENT_STATUS", columnDefinition = M.NUM_COLUM)
	private Integer reimbursementStatus;

	/**
	 * 关联航班
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gjFlightOrder", cascade = CascadeType.ALL)
	private List<GJFlightCabin> gjFlightCabins;

	/**
	 * 关联乘客
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gjFlightOrder", cascade = CascadeType.ALL)
	private List<Passenger> passengers;

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
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

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}

	public Double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(Double totalTax) {
		this.totalTax = totalTax;
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

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}

	public Boolean getExceptionOrder() {
		return exceptionOrder;
	}

	public void setExceptionOrder(Boolean exceptionOrder) {
		this.exceptionOrder = exceptionOrder;
	}

	public Double getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(Double adjustAmount) {
		this.adjustAmount = adjustAmount;
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

	public Integer getReimbursementStatus() {
		return reimbursementStatus;
	}

	public void setReimbursementStatus(Integer reimbursementStatus) {
		this.reimbursementStatus = reimbursementStatus;
	}

	public List<GJFlightCabin> getGjFlightCabins() {
		return gjFlightCabins;
	}

	public void setGjFlightCabins(List<GJFlightCabin> gjFlightCabins) {
		this.gjFlightCabins = gjFlightCabins;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	
}

