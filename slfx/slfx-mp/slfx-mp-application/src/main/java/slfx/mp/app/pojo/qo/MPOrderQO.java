package slfx.mp.app.pojo.qo;

import java.util.Date;

import hg.common.component.BaseQo;


/**
 * 平台订单查询
 * 
 * @author yuxx
 */
public class MPOrderQO extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 按用户id查询
	 */
	private String userId;

	/**
	 * 显示全部订单明细
	 */
	private Boolean detail;
	
	/**
	 * 经销商渠道
	 */
	private String dealerId;
	/**
	 * 平台订单号
	 */
	private String platformOrderCode;
	/**
	 * 供应商订单号
	 */
	private String supplierOrderCode;
	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	/**
	 * 供应商
	 */
	private String supplierId;
	/**
	 * 下单起始时间
	 */
	private Date createDateFrom;
	/**
	 * 下单终止时间
	 */
	private Date createDateTo;
	/**
	 * 预定人
	 */
	private String bookMan;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
	/**
	 * 景点名称
	 */
	private String scenicSpotsName;
	/**
	 * 游玩人
	 */
	private String travelerName;
	/**
	 * 支付状态
	 */
	private String paymentStatus;

	/**
	 * 支付类型
	 */
	private Integer paymentType;

	/**
	 * 景点名称是否支持模糊查询
	 */
	private Boolean scenicSpotsNameLike = true;
	
	/**
	 * 省
	 */
	private String province;
	
	/**
	 * 市
	 */
	private String city;
	
	/**
	 * 区
	 */
	private String area;
	
	/**
	 * 下单时间排序
	 */
	private Boolean createDateAsc = false;
	
	/**
	 * 供应商订单号是否不为空
	 */
	private Boolean supplierOrderCodeNotNull;
	
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getBookMan() {
		return bookMan;
	}

	public void setBookMan(String bookMan) {
		this.bookMan = bookMan;
	}

	public String getScenicSpotsName() {
		return scenicSpotsName;
	}

	public void setScenicSpotsName(String scenicSpotsName) {
		this.scenicSpotsName = scenicSpotsName;
	}

	public String getTravelerName() {
		return travelerName;
	}

	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}

	public String getPlatformOrderCode() {
		return platformOrderCode;
	}

	public void setPlatformOrderCode(String platformOrderCode) {
		this.platformOrderCode = platformOrderCode;
	}

	public String getSupplierOrderCode() {
		return supplierOrderCode;
	}

	public void setSupplierOrderCode(String supplierOrderCode) {
		this.supplierOrderCode = supplierOrderCode;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Boolean getScenicSpotsNameLike() {
		return scenicSpotsNameLike;
	}

	public void setScenicSpotsNameLike(Boolean scenicSpotsNameLike) {
		this.scenicSpotsNameLike = scenicSpotsNameLike;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getDetail() {
		return detail;
	}

	public void setDetail(Boolean detail) {
		this.detail = detail;
	}

	public Boolean getSupplierOrderCodeNotNull() {
		return supplierOrderCodeNotNull;
	}

	public void setSupplierOrderCodeNotNull(Boolean supplierOrderCodeNotNull) {
		this.supplierOrderCodeNotNull = supplierOrderCodeNotNull;
	}

}
