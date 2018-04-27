package hsl.pojo.dto.company;

import hsl.pojo.dto.BaseDTO;

/**
 * 企业中心部门DTO
 * @author zhuxy
 *
 */
@SuppressWarnings("serial")
public class DepartmentDTO extends BaseDTO{
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 父部门ID（）
	 */
	private String  parDeptId;
	/**
	 * 部门下的成员数量
	 */
	private Integer totalCount;
	/**
	 * 组织
	 */
	private CompanyDTO company;
	
	
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
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public CompanyDTO getCompanyDTO() {
		return company;
	}
	public void setCompany(CompanyDTO companyDTO) {
		this.company = companyDTO;
	}

}
