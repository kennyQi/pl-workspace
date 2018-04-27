package hg.pojo.command;


/**
 * 修改供应商优先级策略command
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class ModifySupplierPriorityPolicyCommand extends JxcCommand {

	/**
	 * 供应商优先级策略id
	 */
	private String policyId;
	
	/**
	 * 项目名称
	 */
	private String projectName;
	
	/**
	 * 供应商名称
	 */
	private String supplierName;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

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
