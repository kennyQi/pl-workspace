package zzpl.domain.model.user;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_USER + "COST_CENTER_ORDER")
public class CostCenterOrder extends BaseModel{
	public final static String GN_FLIGHT_ORDER = "1";

	public final static String GJ_FLIGHT_ORDER = "2";

	/**
	 * 流程ID
	 */
	@ManyToOne
	@JoinColumn(name = "COST_CENTER_ID")
	private CostCenter costCenter;

	/**
	 * 订单类型 1：国内机票 2：国际机票
	 */
	@Column(name = "ORDER_TYPE", length = 512)
	private String orderType;

	/**
	 * 订单ID
	 */
	@Column(name = "ORDER_ID", length = 512)
	private String orderID;

	/**
	 * 订单编号
	 */
	@Column(name = "ORDER_NO", length = 512)
	private String orderNO;
	
	/**
	 * 真实姓名
	 */
	@Column(name = "USER_ID", length = 512)
	private String userID;

	/**
	 * 真实姓名
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 身份证号
	 */
	@Column(name = "IDCARD_NO", length = 18)
	private String idCardNO;

	/**
	 * 公司ID
	 */
	@Column(name = "COMPANY_ID", length = 512)
	private String companyID;

	/**
	 * 公司名称
	 */
	@Column(name = "COMPANY_NAME", length = 512)
	private String companyName;

	/**
	 * 部门ID
	 */
	@Column(name = "DEPARTMENT_ID", length = 512)
	private String departmentID;

	/**
	 * 部门名称
	 */
	@Column(name = "DEPARTMENT_NAME", length = 512)
	private String departmentName;

	/**
	 * 发生退款时退款金额
	 */
	@Column(name = "REFUND_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double refundPrice;

	/**
	 * ---------------------------------真正购票成功后经销商返回----------------------------
	 * 总支付金额
	 */
	@Column(name = "TOTAL_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPrice;

	/**
	 * 计入成本的价格
	 */
	@Column(name = "COST_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double costfPrice;

	/**
	 * ---------------------------------订单基本信息----------------------------------
	 * 订单创建时间
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;

	public CostCenter getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(CostCenter costCenter) {
		this.costCenter = costCenter;
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

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNO() {
		return idCardNO;
	}

	public void setIdCardNO(String idCardNO) {
		this.idCardNO = idCardNO;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getCostfPrice() {
		return costfPrice;
	}

	public void setCostfPrice(Double costfPrice) {
		this.costfPrice = costfPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
