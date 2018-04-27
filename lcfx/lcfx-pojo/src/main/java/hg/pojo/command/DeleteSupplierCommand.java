package hg.pojo.command;

import java.util.List;

/**
 * 删除供应商
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeleteSupplierCommand extends JxcCommand {

	/**
	 * 供应商id列表
	 */
	private List<String> supplierListId;

	public List<String> getSupplierListId() {
		return supplierListId;
	}

	public void setSupplierListId(List<String> supplierListId) {
		this.supplierListId = supplierListId;
	}
	
}
