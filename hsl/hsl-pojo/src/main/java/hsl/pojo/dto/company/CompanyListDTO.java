package hsl.pojo.dto.company;

import java.util.List;

import hsl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class CompanyListDTO extends BaseDTO{
	/**
	 * 公司id
	 */
	private String companyId;
	
	/**
	 * 公司名字
	 */
	private String companyName;
	
	/**
	 * 部门列表
	 */
	private List<DepartmentDTO> departmentList;
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public List<DepartmentDTO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentDTO> departmentList) {
		this.departmentList = departmentList;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
}
