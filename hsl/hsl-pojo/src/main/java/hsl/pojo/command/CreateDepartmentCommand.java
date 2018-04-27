package hsl.pojo.command;

/**
 * 创建部门COMD
 * @author zhuxy
 *
 */
public class CreateDepartmentCommand {
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 父部门ID（）
	 */
	private String  parDeptId;
	/**
	 * 公司ID
	 */
	private String companyId;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getParDeptId() {
		return parDeptId;
	}
	public void setParDeptId(String parDeptId) {
		this.parDeptId = parDeptId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}
