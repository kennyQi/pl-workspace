package zzpl.domain.model.user;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import zzpl.domain.model.M;

/**
 * 
 * @类功能说明：结算中心（Center of Settling Accounts of Fund）
 * @类修改者：
 * @修改日期：2015年8月6日下午1:50:42
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年8月6日下午1:50:42
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_USER + "COSAOF")
public class COSAOF extends BaseModel {

	/***
	 * 乘客姓名
	 */
	@Column(name = "PASSENGER_NAME", length = 512)
	private String passengerName;

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

	/***
	 * PNR
	 */
	@Column(name = "PNR", length = 512)
	private String pnr;

	/***
	 * 航程信息
	 */
	@Column(name = "VOYAGE", length = 512)
	private String voyage;

	/**
	 * 发生退款时退款金额
	 */
	@Column(name = "REFUND_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double refundPrice;

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
	 * 申请人价
	 */
	@Column(name = "USER_PAY_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double userPayPrice;

	/**
	 * 申请人退款价
	 */
	@Column(name = "USER_REFUND_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double userRefundPrice;

	/**
	 * 结算价格
	 */
	@Column(name = "CASAOF_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double casaofPrice;

	/**
	 * 支付类型 1:代扣 2:客户支付 (前期意义为这两种 后期有可能 1：代扣 2：支付宝 3：微信 ..........)
	 */
	@Column(name = "PAY_TYPE", length = 512)
	private String payType;

	/**
	 * ---------------------------------订单基本信息----------------------------------
	 * 订单创建时间
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;

	/**
	 * 票号
	 */
	@Column(name = "AIR_ID", length = 512)
	private String airID;

	/**
	 * 订单状态
	 */
	@Column(name = "ORDER_STATUS", columnDefinition = M.NUM_COLUM)
	private Integer orderStatus;

	/**
	 * 结算订单状态
	 */
	@Column(name = "COSAOF_STATUS", columnDefinition = M.NUM_COLUM)
	private Integer cosaofStatus;

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
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

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
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

	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
	}

	public Double getCasaofPrice() {
		return casaofPrice;
	}

	public void setCasaofPrice(Double casaofPrice) {
		this.casaofPrice = casaofPrice;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAirID() {
		return airID;
	}

	public void setAirID(String airID) {
		this.airID = airID;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getCosaofStatus() {
		return cosaofStatus;
	}

	public void setCosaofStatus(Integer cosaofStatus) {
		this.cosaofStatus = cosaofStatus;
	}

	public Double getUserPayPrice() {
		return userPayPrice;
	}

	public void setUserPayPrice(Double userPayPrice) {
		this.userPayPrice = userPayPrice;
	}

	public Double getUserRefundPrice() {
		return userRefundPrice;
	}

	public void setUserRefundPrice(Double userRefundPrice) {
		this.userRefundPrice = userRefundPrice;
	}

}
