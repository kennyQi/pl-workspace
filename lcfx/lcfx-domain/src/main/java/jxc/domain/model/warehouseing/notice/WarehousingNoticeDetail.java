package jxc.domain.model.warehouseing.notice;

import hg.common.util.UUIDGenerator;
import hg.pojo.dto.purchaseorder.PurchaseOrderDetailDTO;
import hg.pojo.dto.warehousing.WarehousingNoticeDetailDTO;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.purchaseorder.PurchaseOrder;

@Entity
@Table(name = M.TABLE_PREFIX_WAREHOUSING + "WAREHOUSING_NOTICE_DETAIL")
public class WarehousingNoticeDetail extends JxcBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 采购单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WAREHOUSING_NOTICE_ID")
	private WarehousingNotice warehousingNotice;

	/**
	 * sku商品信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SKU_PRODUCT_ID")
	private SkuProduct skuProduct;

	/**
	 * 不含税总价
	 */
	@Column(name = "TOTAL_PRICE_EXCL_TAX", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPriceExclTax;

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

	/**
	*
	*/
	@Column(name = "TOTAL_PRICE_INCL_TAX", columnDefinition = M.DOUBLE_COLUM)
	private Double totalPriceInclTax;

	public Double getTotalPriceInclTax() {
		return totalPriceInclTax;
	}

	public void setTotalPriceInclTax(Double totalPriceInclTax) {
		this.totalPriceInclTax = totalPriceInclTax;
	}

	public Double getUnitPriceInclTax() {
		return unitPriceInclTax;
	}

	public void setUnitPriceInclTax(Double unitPriceInclTax) {
		this.unitPriceInclTax = unitPriceInclTax;
	}

	/**
	*
	*/
	@Column(name = "UNIT_PRICE_INCL_TAX", columnDefinition = M.DOUBLE_COLUM)
	private Double unitPriceInclTax;

	public SkuProduct getSkuProduct() {
		return skuProduct;
	}

	public void setSkuProduct(SkuProduct skuProduct) {
		this.skuProduct = skuProduct;
	}

	public Double getTotalPriceExclTax() {
		return totalPriceExclTax;
	}

	public void setTotalPriceExclTax(Double totalPriceExclTax) {
		this.totalPriceExclTax = totalPriceExclTax;
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

	public WarehousingNotice getWarehousingNotice() {
		return warehousingNotice;
	}

	public void setWarehousingNotice(WarehousingNotice warehousingNotice) {
		this.warehousingNotice = warehousingNotice;
	}

	public void create(WarehousingNoticeDetailDTO dto, WarehousingNotice warehousingNotice) {
		setId(UUIDGenerator.getUUID());
		setLogisticCost(dto.getLogisticCost());
		skuProduct = new SkuProduct();
		skuProduct.setId(dto.getSkuCode());

		setQuantity(dto.getQuantity());
		setUnitPriceInclTax(dto.getUnitPriceInclTax());
		setTotalPriceInclTax(dto.getTotalPriceInclTax());

		setWarehousingNotice(warehousingNotice);
		setStatusRemoved(false);

	}

}
