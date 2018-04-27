package hsl.pojo.dto.lineSalesPlan.order;

import hsl.pojo.dto.BaseDTO;

import java.util.Date;

/**
 * @类功能说明：线路销售方案订单的基本信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/11/28 13:55
 */
public class LSPOrderBaseInfoDTO extends BaseDTO{

	/**
	 * 经销商订单编号
	 */
	private String dealerOrderNo;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	
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
	 * 销售总价（销售方案价格）
	 */
	private Double salePrice;

	/**
	 * 供应商总价（分销原来价格，提供分销时需要提供该价格）
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

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
}