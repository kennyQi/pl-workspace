package jxc.domain.model.purchaseorder;


import hg.common.util.UUIDGenerator;
import hg.pojo.dto.purchaseorder.PurchaseOrderDetailDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.model.product.SkuProduct;

/**
 * 结算方式
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_PURCHASE_ORDER + "PURCHASE_ORDER_DETAIL")
public class PurchaseOrderDetail extends JxcBaseModel {
	/**
	 * 采购单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PURCHASE_ORDER_ID")
	private PurchaseOrder purchaseOrder;
	/**
	 * sku商品信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SKU_PRODUCT_ID")
	private SkuProduct skuProduct;

	/**
	 * 含税总价
	 */
	@Column(name = "TOTAL_PRICE_INCL_TAX", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPriceInclTax;

	/**
	 * 不含税总价
	 */
	@Column(name = "TOTAL_PRICE_EXCL_TAX", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPriceExclTax;

	/**
	 * 含税单价
	 */
	@Column(name = "UNIT_PRICE_INCL_TAX", columnDefinition = M.DOUBLE_COLUM)
	private Double unitPriceInclTax;

	/**
	 * 不含税单价
	 */
	@Column(name = "UNIT_PRICE_EXCL_TAX", columnDefinition = M.DOUBLE_COLUM)
	private Double unitPriceExclTax;

	/**
	 * 物流费
	 */
	@Column(name = "LOGISTIC_COST", columnDefinition = M.DOUBLE_COLUM)
	private Double logisticCost;

	/**
	 * 数量
	 */
	@Column(name = "QUANTITY", columnDefinition = M.NUM_COLUM)
	private Integer quantity;

	/**
	 * 税率
	 */
	@Column(name = "TAX_RATE", columnDefinition = M.DOUBLE_COLUM)
	private Double taxRate;

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public SkuProduct getSkuProduct() {
		return skuProduct;
	}

	public void setSkuProduct(SkuProduct skuProduct) {
		this.skuProduct = skuProduct;
	}

	public Double getTotalPriceInclTax() {
		return totalPriceInclTax;
	}

	public void setTotalPriceInclTax(Double totalPriceInclTax) {
		this.totalPriceInclTax = totalPriceInclTax;
	}

	public Double getTotalPriceExclTax() {
		return totalPriceExclTax;
	}

	public void setTotalPriceExclTax(Double totalPriceExclTax) {
		this.totalPriceExclTax = totalPriceExclTax;
	}

	public Double getUnitPriceInclTax() {
		return unitPriceInclTax;
	}

	public void setUnitPriceInclTax(Double unitPriceInclTax) {
		this.unitPriceInclTax = unitPriceInclTax;
	}

	public Double getUnitPriceExclTax() {
		return unitPriceExclTax;
	}

	public void setUnitPriceExclTax(Double unitPriceExclTax) {
		this.unitPriceExclTax = unitPriceExclTax;
	}

	public Double getLogisticCost() {
		return logisticCost;
	}

	public void setLogisticCost(Double logisticCost) {
		this.logisticCost = logisticCost;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public void create(PurchaseOrderDetailDTO dto, PurchaseOrder purchaseOrder) {
		setId(UUIDGenerator.getUUID());
		setLogisticCost(dto.getLogisticCost());
		skuProduct = new SkuProduct();
		skuProduct.setId(dto.getSkuCode());
		
		setQuantity(dto.getQuantity());
		setUnitPriceInclTax(dto.getUnitPriceInclTax());
		setTotalPriceInclTax(dto.getTotalPriceInclTax());
		
		setPurchaseOrder(purchaseOrder);
		setStatusRemoved(false);
		
	}

}
