package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class WarehouseQO extends JxcBaseQo{
	/**
	 * 仓库类型id
	 */
	private String warehouseTypeId;
	/**
	 * 仓库名称
	 */
	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWarehouseTypeId() {
		return warehouseTypeId;
	}

	public void setWarehouseTypeId(String warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}
	
}
