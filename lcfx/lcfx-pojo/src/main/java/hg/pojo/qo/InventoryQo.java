package hg.pojo.qo;

import hg.common.component.BaseQo;

/**
 * @类功能说明：库存
 * @备注：
 * @类修改者：
 * @修改日期：2015-03-18 10:58:44
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-03-18 10:58:44
 */
public class InventoryQo extends BaseQo {
	private static final long serialVersionUID = 1L;
	/**
	 * 仓库
	 */
	private String warehouseId;
	/**
	 * sku商品信息
	 */
	private String skuProductId;
	/**
	 * 供应商
	 */
	private String supplierId;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
