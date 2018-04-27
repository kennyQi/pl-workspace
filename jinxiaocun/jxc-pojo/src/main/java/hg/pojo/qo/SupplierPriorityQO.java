package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class SupplierPriorityQO extends BaseQo{
	/**
	 * 供应商优先级策略id
	 */
	private String supplierPriorityPolicyId;

	/**
	 * 供应商id
	 */
	private String supplierId;
	
	/**
	 * 项目id
	 */
	private String projectId;

	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierPriorityPolicyId() {
		return supplierPriorityPolicyId;
	}

	public void setSupplierPriorityPolicyId(String supplierPriorityPolicyId) {
		this.supplierPriorityPolicyId = supplierPriorityPolicyId;
	}
}
