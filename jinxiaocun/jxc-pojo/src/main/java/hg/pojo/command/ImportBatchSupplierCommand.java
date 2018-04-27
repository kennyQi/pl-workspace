package hg.pojo.command;

import hg.pojo.dto.supplier.SupplierDTO;
import hg.pojo.dto.supplier.SupplierLinkManDTO;

import java.util.List;

/**
 * 批量导入供应商command
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ImportBatchSupplierCommand extends JxcCommand {

	/**
	 * 供应商信息列表
	 */
	private List<SupplierDTO> supplierList;
	
	/**
	 * 供应商联系人列表
	 */
	private List<SupplierLinkManDTO> supplierLinkManList;

	public List<SupplierDTO> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<SupplierDTO> supplierList) {
		this.supplierList = supplierList;
	}

	public List<SupplierLinkManDTO> getSupplierLinkManList() {
		return supplierLinkManList;
	}

	public void setSupplierLinkManList(List<SupplierLinkManDTO> supplierLinkManList) {
		this.supplierLinkManList = supplierLinkManList;
	}
	
}
