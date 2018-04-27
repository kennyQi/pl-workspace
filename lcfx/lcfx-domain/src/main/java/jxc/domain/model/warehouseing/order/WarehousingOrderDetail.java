package jxc.domain.model.warehouseing.order;

import hg.common.util.UUIDGenerator;
import hg.pojo.dto.purchaseorder.PurchaseOrderDetailDTO;
import hg.pojo.dto.warehousing.WarehousingOrderDetailDTO;

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
@Table(name = M.TABLE_PREFIX_WAREHOUSING + "WAREHOUSING_ORDER_DETAIL")
public class WarehousingOrderDetail extends JxcBaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 入库单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WAREHOUSING_ORDER_ID")
	private WarehousingOrder warehousingOrder;


	public WarehousingOrder getWarehousingOrder() {
		return warehousingOrder;
	}

	public void setWarehousingOrder(WarehousingOrder warehousingOrder) {
		this.warehousingOrder = warehousingOrder;
	}

	/**
	 * sku商品信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SKU_PRODUCT_ID")
	private SkuProduct skuProduct;

	/**
	 * 预计的总数
	 */
	@Column(name = "EXPECTED_TOTAL_QUANTITY", columnDefinition = M.NUM_COLUM)
	private Integer expectedTotalQuantity;

	/**
	 * 次品数
	 */
	@Column(name = "DEFECTIVE_PRODUCT_QUANTITY", columnDefinition = M.NUM_COLUM)
	private Integer defectiveProductQuantity;

	/**
	 * 良品数
	 */
	@Column(name = "QUALIFIED_PRODUCT_QUANTITY", columnDefinition = M.NUM_COLUM)
	private Integer qualifiedProductQuantity;

	/**
	 * 剩余数（待入库）
	 */
	@Column(name = "REMAINING_QUANTITY", columnDefinition = M.NUM_COLUM)
	private Integer remainingQuantity;

	public SkuProduct getSkuProduct() {
		return skuProduct;
	}

	public void setSkuProduct(SkuProduct skuProduct) {
		this.skuProduct = skuProduct;
	}

	public Integer getExpectedTotalQuantity() {
		return expectedTotalQuantity;
	}

	public void setExpectedTotalQuantity(Integer expectedTotalQuantity) {
		this.expectedTotalQuantity = expectedTotalQuantity;
	}

	public Integer getDefectiveProductQuantity() {
		return defectiveProductQuantity;
	}

	public void setDefectiveProductQuantity(Integer defectiveProductQuantity) {
		this.defectiveProductQuantity = defectiveProductQuantity;
	}

	public Integer getQualifiedProductQuantity() {
		return qualifiedProductQuantity;
	}

	public void setQualifiedProductQuantity(Integer qualifiedProductQuantity) {
		this.qualifiedProductQuantity = qualifiedProductQuantity;
	}

	public Integer getRemainingQuantity() {
		return remainingQuantity;
	}

	public void setRemainingQuantity(Integer remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	
	public void create(WarehousingOrderDetailDTO dto, WarehousingOrder warehousingOrder) {
		setId(UUIDGenerator.getUUID());
//		setLogisticCost(dto.getLogisticCost());
		skuProduct = new SkuProduct();
		skuProduct.setId(dto.getSkuCode());
		
//		setQuantity(dto.getQuantity());
//		setUnitPriceInclTax(dto.getUnitPriceInclTax());
//		setTotalPriceInclTax(dto.getTotalPriceInclTax());
		
//		setPurchaseOrder(purchaseOrder);
		setStatusRemoved(false);
		
	}


}
