package lxs.pojo.dto.line;

import java.util.Date;

import lxs.pojo.BaseDTO;

@SuppressWarnings("serial")
public class LineOrderBaseInfoDTO extends BaseDTO{
	/**
	 * 经销商订单遍号
	 */
	private String dealerOrderNo;
	
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
	
}
