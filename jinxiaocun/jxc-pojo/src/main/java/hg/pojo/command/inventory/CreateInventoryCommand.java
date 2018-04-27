package hg.pojo.command.inventory;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：库存
 * @备注：
 * @类修改者：
 * @修改日期：2015-03-18 11:07:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-03-18 11:07:24
 */
public class CreateInventoryCommand extends BaseCommand {
	private static final long serialVersionUID = 1L;
	/**
	 * 良品可用数
	 */
	private Integer qualifiedProductAvailable;
	/**
	 * 良品锁定数
	 */
	private Integer qualifiedProductInstock;
	/**
	 * 仓库
	 */
	private String warehouseId;
	/**
	 * 次品可用数
	 */
	private Integer defectiveProductAvailable;
	/**
	 * sku商品信息
	 */
	private String skuProductId;
	/**
	 * 次品锁定数
	 */
	private Integer defectiveProductLocked;
	/**
	 * 供应商
	 */
	private String supplierId;
	/**
	 * 良品锁定数
	 */
	private Integer qualifiedProductLocked;
	/**
	 * 次品在库数
	 */
	private Integer defectiveProductInstock;

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

	public Integer getQualifiedProductLocked() {
		return qualifiedProductLocked;
	}

	public void setQualifiedProductLocked(Integer qualifiedProductLocked) {
		this.qualifiedProductLocked = qualifiedProductLocked;
	}

	public Integer getQualifiedProductInstock() {
		return qualifiedProductInstock;
	}

	public void setQualifiedProductInstock(Integer qualifiedProductInstock) {
		this.qualifiedProductInstock = qualifiedProductInstock;
	}

	public Integer getDefectiveProductInstock() {
		return defectiveProductInstock;
	}

	public void setDefectiveProductInstock(Integer defectiveProductInstock) {
		this.defectiveProductInstock = defectiveProductInstock;
	}

	public Integer getDefectiveProductLocked() {
		return defectiveProductLocked;
	}

	public void setDefectiveProductLocked(Integer defectiveProductLocked) {
		this.defectiveProductLocked = defectiveProductLocked;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getSkuProductId() {
		return skuProductId;
	}

	public void setSkuProductId(String skuProductId) {
		this.skuProductId = skuProductId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

}
