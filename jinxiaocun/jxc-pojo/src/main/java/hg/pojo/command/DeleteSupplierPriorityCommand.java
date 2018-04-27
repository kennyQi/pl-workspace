package hg.pojo.command;

import java.util.List;


@SuppressWarnings("serial")
public class DeleteSupplierPriorityCommand extends JxcCommand {

	/**
	 * 供应商优先级政策id
	 */
	private String supplierPriorityPolicyId;
	
	/**
	 * 供应商id列表
	 */
	private List<String> supplierIds;
	

	public String getSupplierPriorityPolicyId() {
		return supplierPriorityPolicyId;
	}

	public void setSupplierPriorityPolicyId(String supplierPriorityPolicyId) {
		this.supplierPriorityPolicyId = supplierPriorityPolicyId;
	}

	public List<String> getSupplierIds() {
		return supplierIds;
	}

	public void setSupplierIds(List<String> supplierIds) {
		this.supplierIds = supplierIds;
	}

}
