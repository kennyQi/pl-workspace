package zzpl.domain.model.jp;

import hg.common.component.BaseModel;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
//@Entity
//@Table(name = "T_JP_PASSANGER")
public class JPPassanger extends BaseModel {
	/**
	 * 旅客姓名,如: 中文(张三) 英文(Jesse/W)
	 */
	@Column(name = "NAME", length = 64)
	private String name;
	
	/**
	 * 乘客手机号
	 */
	@Column(name = "MOBILE", length = 11)
	private String mobile;
	
	/** 乘客序号 */
	@Column(name = "PSG_NO")
	private int psgNo;

	/** 乘客类型 */
	@Column(name = "PASSANGER_TYPE")
	private String passangerType;

	/** 航信接口证件类型 */
	@Column(name = "CARD_TYPE")
	private String cardType;
	
	/**
	 * 乘客证件号
	 */
	@Column(name = "ID_CARD_NO")
	private String cardNo;

	/** 出生日期 */
	@Column(name = "BIRTHDAY", length = 32)
	private String birthday;

	/** 跟随成人序号 */
	@Column(name = "CARRIER_PSG_NO")
	private int carrierPsgNo;

	/** 保险份数 */
	@Column(name = "INSUE_SUM")
	private int insueSum;

	/** 保险单价 */
	@Column(name = "INSUE_FEE")
	private Double insueFee;

	/**
	 * 该乘机人所支付的政策
	 */
	@Embedded
	private JPFlightPolicy flightPolicy;

	/**
	 * 该乘机人所出的机票
	 */
	@Embedded
	private JPTicket ticket;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private JPOrder order;
	
	/**
	 * 订单退废记录
	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "passanger")
	private JPOrderRefundLog orderRefundLog;
	
	/**
	 * 组织ID
	 */
	@Column(name = "COMPANY_ID")
	private String companyId;
	
	/**
	 * 组织名称
	 */
	@Column(name = "COMPANY_NAME")
	private String companyName;
	
	/**
	 * 部门ID
	 */
	@Column(name = "DEPARTMENT_ID")
	private String departmentId;
	
	/**
	 * 部门名称
	 */
	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;
	
	/**
	 * 成员ID
	 */
	@Column(name = "MEMBER_ID")
	private String memeberId;
	
	
	
	//////
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getPsgNo() {
		return psgNo;
	}

	public void setPsgNo(int psgNo) {
		this.psgNo = psgNo;
	}

	public String getPassangerType() {
		return passangerType;
	}

	public void setPassangerType(String passangerType) {
		this.passangerType = passangerType;
	}

	public String getCardType() {
		return cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getCarrierPsgNo() {
		return carrierPsgNo;
	}

	public void setCarrierPsgNo(int carrierPsgNo) {
		this.carrierPsgNo = carrierPsgNo;
	}

	public int getInsueSum() {
		return insueSum;
	}

	public void setInsueSum(int insueSum) {
		this.insueSum = insueSum;
	}

	public Double getInsueFee() {
		return insueFee;
	}

	public void setInsueFee(Double insueFee) {
		this.insueFee = insueFee;
	}

	public JPFlightPolicy getFlightPolicy() {
		return flightPolicy;
	}

	public void setFlightPolicy(JPFlightPolicy flightPolicy) {
		this.flightPolicy = flightPolicy;
	}

	public JPTicket getTicket() {
		return ticket;
	}

	public void setTicket(JPTicket ticket) {
		this.ticket = ticket;
	}

	public JPOrder getOrder() {
		return order;
	}

	public void setOrder(JPOrder order) {
		this.order = order;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public JPOrderRefundLog getOrderRefundLog() {
		return orderRefundLog;
	}

	public void setOrderRefundLog(JPOrderRefundLog orderRefundLog) {
		this.orderRefundLog = orderRefundLog;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMemeberId() {
		return memeberId;
	}

	public void setMemeberId(String memeberId) {
		this.memeberId = memeberId;
	}
	
}
