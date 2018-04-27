package hg.pojo.command;

import hg.pojo.dto.supplier.SupplierLinkManDTO;

import java.util.List;

@SuppressWarnings("serial")
public class ModifySupplierLinkManCommand extends JxcCommand {

	/**
	 * 所属供应商
	 */
	private String supplierId;
	
	private List<SupplierLinkManDTO> supplierLinkManList;

	public List<SupplierLinkManDTO> getSupplierLinkManList() {
		return supplierLinkManList;
	}

	public void setSupplierLinkManList(List<SupplierLinkManDTO> supplierLinkManList) {
		this.supplierLinkManList = supplierLinkManList;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	
}
