package hg.pojo.qo;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class SupplierPriorityPolicyQO extends BaseQo{
	
	/**
	 * 项目名称
	 */
	private String projectName;
	
	/**
	 * 供应商名称
	 */
	private String supplierName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
}
