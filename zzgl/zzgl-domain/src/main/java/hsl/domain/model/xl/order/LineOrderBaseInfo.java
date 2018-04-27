package hsl.domain.model.xl.order;
import hsl.domain.model.M;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @类功能说明：线路订单基本信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午2:57:29
 * 
 */
@Embeddable
public class LineOrderBaseInfo {

	/**
	 * 经销商订单编号
	 */
	@Column(name = "DEALER_ORDER_NO", length = 64 )
	private String dealerOrderNo;
	
	/**
	 * 成人人数
	 */
	@Column(name = "ADULT_NO", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer adultNo;
	
	/**
	 * 儿童人数
	 */
	@Column(name = "CHILD_NO", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer childNo;
	
	/**
	 * 成人销售单价
	 */
	@Column(name = "ADULT_UNIT_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double adultUnitPrice;
	
	/**
	 * 儿童销售单价
	 */
	@Column(name = "CHILD_UNIT_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double childUnitPrice;
	
	/**
	 * 销售总价
	 */
	@Column(name = "SALE_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double salePrice;
	
	/**
	 * 销售定金
	 */
	@Column(name = "BARGAIN_MONEY", columnDefinition = M.DOUBLE_COLUM)
	private Double bargainMoney;
	
	/**
	 * 供应商成人单价
	 */
	@Column(name = "SUPPLIER_ADULT_UNIT_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double supplierAdultUnitPrice;
	
	/**
	 * 供应商儿童单价
	 */	
	@Column(name = "SUPPLIER_UNIT_CHILD_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double supplierUnitChildPrice;
	
	/**
	 * 供应商总价
	 */
	@Column(name = "SUPPLIER_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double supplierPrice;
	
	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 发团日期
	 */
	@Column(name = "TRAVEL_DATE", columnDefinition = M.DATE_COLUM)
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