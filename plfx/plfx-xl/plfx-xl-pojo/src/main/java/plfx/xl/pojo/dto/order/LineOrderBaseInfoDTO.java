package plfx.xl.pojo.dto.order;

import java.util.Date;

import plfx.xl.pojo.dto.BaseXlDTO;

/**
 * 
 * @类功能说明：线路订单基本信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月17日下午2:57:29
 * 
 */
@SuppressWarnings("serial")
public class LineOrderBaseInfoDTO extends BaseXlDTO{

	/**
	 * 经销商订单编号
	 */
	private String dealerOrderNo;
	
	/** 
	 * 平台订单号
	 */
	private String orderNo;
	
	/**
	 * 成人人数
	 */
	private Integer adultNo;
	
	/**
	 * 儿童人数
	 */
	private Integer childNo;
	
	/**
	 * 成人销售单价
	 */
	private Double adultUnitPrice;
	
	/**
	 * 儿童销售单价
	 */
	private Double childUnitPrice;
	
	/**
	 * 销售总价
	 */
	private Double salePrice;
	
	/**
	 * 销售定金
	 */
	private Double bargainMoney;
	
	/**
	 * 供应商成人单价
	 */
	private Double supplierAdultUnitPrice;
	
	/**
	 * 供应商儿童单价
	 */	
	private Double supplierUnitChildPrice;
	
	/**
	 * 供应商总价
	 */
	private Double supplierPrice;
	
	/**
	 * 创建日期
	 */
	private Date createDate;
	
	/**
	 * 发团日期
	 */
	private Date travelDate;

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public Integer getAdultNo() {
		return adultNo;
	}

	public void setAdultNo(Integer adultNo) {
		this.adultNo = adultNo;
	}

	public Integer getChildNo() {
		return childNo;
	}

	public void setChildNo(Integer childNo) {
		this.childNo = childNo;
	}

	public Double getAdultUnitPrice() {
		return adultUnitPrice;
	}

	public void setAdultUnitPrice(Double adultUnitPrice) {
		this.adultUnitPrice = adultUnitPrice;
	}

	public Double getChildUnitPrice() {
		return childUnitPrice;
	}

	public void setChildUnitPrice(Double childUnitPrice) {
		this.childUnitPrice = childUnitPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getBargainMoney() {
		return bargainMoney;
	}

	public void setBargainMoney(Double bargainMoney) {
		this.bargainMoney = bargainMoney;
	}

	public Double getSupplierAdultUnitPrice() {
		return supplierAdultUnitPrice;
	}

	public void setSupplierAdultUnitPrice(Double supplierAdultUnitPrice) {
		this.supplierAdultUnitPrice = supplierAdultUnitPrice;
	}

	public Double getSupplierUnitChildPrice() {
		return supplierUnitChildPrice;
	}

	public void setSupplierUnitChildPrice(Double supplierUnitChildPrice) {
		this.supplierUnitChildPrice = supplierUnitChildPrice;
	}

	public Double getSupplierPrice() {
		return supplierPrice;
	}

	public void setSupplierPrice(Double supplierPrice) {
		this.supplierPrice = supplierPrice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}