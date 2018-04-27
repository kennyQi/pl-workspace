package jxc.domain.model.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;
import jxc.domain.model.product.SkuProduct;
import jxc.domain.model.supplier.Supplier;
import jxc.domain.model.warehouse.Warehouse;
import jxc.domain.model.warehouseing.notice.WarehousingNoticeDetail;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreatePaymentMethodCommand;
import hg.pojo.command.CreateUnitCommand;
import hg.pojo.command.ModifyPaymentMethodCommand;
import hg.pojo.command.ModifyUnitCommand;
import hg.pojo.command.RemovePaymentMethodCommand;
import hg.pojo.command.RemoveUnitCommand;

@Entity
@Table(name = M.TABLE_PREFIX_WAREHOUSING + "INVENTORY")
public class Inventory extends JxcBaseModel {

	/**
	 * sku商品信息
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SKU_PRODUCT_ID")
	private SkuProduct skuProduct;

	/**
	 * 仓库
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "WAREHOUSE_ID")
	private Warehouse warehouse;

	/**
	 * 供应商
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SUPPLIER_ID")
	private Supplier supplier;

	/**
	 * 次品可用数
	 */
	@Column(name = "DEFECTIVE_PRODUCT_AVAILABLE", columnDefinition = M.NUM_COLUM)
	private Integer defectiveProductAvailable;

	/**
	 * 良品可用数
	 */
	@Column(name = "QUALIFIED_PRODUCT_AVAILABLE", columnDefinition = M.NUM_COLUM)
	private Integer qualifiedProductAvailable;

	/**
	 * 次品在库
	 */
	@Column(name = "DEFECTIVE_PRODUCT_INSTOCK", columnDefinition = M.NUM_COLUM)
	private Integer defectiveProductInstock;

	/**
	 * 良品在库
	 */
	@Column(name = "QUALIFIED_PRODUCT_INSTOCK", columnDefinition = M.NUM_COLUM)
	private Integer qualifiedProductInstock;

	/**
	 * 次品锁定数
	 */
	@Column(name = "DEFECTIVE_PRODUCT_LOCKED", columnDefinition = M.NUM_COLUM)
	private Integer defectiveProductLocked;

	/**
	 * 良品锁定数
	 */
	@Column(name = "QUALIFIED_PRODUCT_LOCKED", columnDefinition = M.NUM_COLUM)
	private Integer qualifiedProductLocked;


	public SkuProduct getSkuProduct() {
		return skuProduct;
	}

	public void setSkuProduct(SkuProduct skuProduct) {
		this.skuProduct = skuProduct;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Integer getDefectiveProductAvailable() {
		return defectiveProductAvailable;
	}

	public void setDefectiveProductAvailable(Integer defectiveProductAvailable) {
		this.defectiveProductAvailable = defectiveProductAvailable;
	}

	public Integer getQualifiedProductAvailable() {
		return qualifiedProductAvailable;
	}

	public void setQualifiedProductAvailable(Integer qualifiedProductAvailable) {
		this.qualifiedProductAvailable = qualifiedProductAvailable;
	}

	public Integer getDefectiveProductInstock() {
		return defectiveProductInstock;
	}

	public void setDefectiveProductInstock(Integer defectiveProductInstock) {
		this.defectiveProductInstock = defectiveProductInstock;
	}

	public Integer getQualifiedProductInstock() {
		return qualifiedProductInstock;
	}

	public void setQualifiedProductInstock(Integer qualifiedProductInstock) {
		this.qualifiedProductInstock = qualifiedProductInstock;
	}

	public Integer getDefectiveProductLocked() {
		return defectiveProductLocked;
	}

	public void setDefectiveProductLocked(Integer defectiveProductLocked) {
		this.defectiveProductLocked = defectiveProductLocked;
	}

	public Integer getQualifiedProductLocked() {
		return qualifiedProductLocked;
	}

	public void setQualifiedProductLocked(Integer qualifiedProductLocked) {
		this.qualifiedProductLocked = qualifiedProductLocked;
	}

}
