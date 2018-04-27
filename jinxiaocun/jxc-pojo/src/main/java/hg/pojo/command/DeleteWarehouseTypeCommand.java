package hg.pojo.command;


/**
 * 删除仓库类型
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeleteWarehouseTypeCommand extends JxcCommand{

	/**
	 * 仓库类型id
	 */
	private String warehouseTypeId;

	public String getWarehouseTypeId() {
		return warehouseTypeId;
	}

	public void setWarehouseTypeId(String warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}
	
}
