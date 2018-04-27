package hsl.h5.base.result.company;

import hsl.h5.base.result.api.ApiResult;
import hsl.pojo.dto.company.CompanyDTO;

public class DepartmentResult extends ApiResult{
	private String id;
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
	/**
	 * 成员数量
	 */
	private Integer memberCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Integer getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}
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
	public CompanyDTO getCompany() {
		return company;
	}
	public void setCompany(CompanyDTO company) {
		this.company = company;
	}
	
	
}
