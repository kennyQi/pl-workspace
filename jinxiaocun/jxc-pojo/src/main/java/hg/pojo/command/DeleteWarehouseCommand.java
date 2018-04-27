package hg.pojo.command;


/**
 * 删除仓库
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeleteWarehouseCommand extends JxcCommand {
	
	/**
	 * 仓库id
	 */
	private String warehouseId;

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	
}
