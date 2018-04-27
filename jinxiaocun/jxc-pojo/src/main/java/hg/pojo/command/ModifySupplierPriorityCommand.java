package hg.pojo.command;

import java.util.List;

@SuppressWarnings("serial")
public class ModifySupplierPriorityCommand extends JxcCommand {

	/**
	 * 供应商优先级政策id
	 */
	private String SupplierPriorityPolicyId;
	
	/**
	 * 项目id列表
	 */
	private List<String> projectIdList;
	
	/**
	 * 供应商id列表
	 */
	private List<String> supplierIdList;
	
	/**
	 * 优先级列表
	 */
	private List<Integer> priorityList;

	public String getSupplierPriorityPolicyId() {
		return SupplierPriorityPolicyId;
	}

	public void setSupplierPriorityPolicyId(String supplierPriorityPolicyId) {
		SupplierPriorityPolicyId = supplierPriorityPolicyId;
	}

	public List<String> getProjectIdList() {
		return projectIdList;
	}

	public void setProjectIdList(List<String> projectIdList) {
		this.projectIdList = projectIdList;
	}

	public List<String> getSupplierIdList() {
		return supplierIdList;
	}

	public void setSupplierIdList(List<String> supplierIdList) {
		this.supplierIdList = supplierIdList;
	}

	public List<Integer> getPriorityList() {
		return priorityList;
	}

	public void setPriorityList(List<Integer> priorityList) {
		this.priorityList = priorityList;
	}

}
