package hg.pojo.command;


/**
 * 删除供应商联系人
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class DeleteSupplierLinkManCommand extends JxcCommand {

	/**
	 * 供应商联系人id
	 */
	private String supplierLinkManId;

	public String getSupplierLinkManId() {
		return supplierLinkManId;
	}

	public void setSupplierLinkManId(String supplierLinkManId) {
		this.supplierLinkManId = supplierLinkManId;
	}
	
}
