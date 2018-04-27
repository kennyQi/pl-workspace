package zzpl.pojo.dto.user;

import java.util.Date;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class DepartmentDTO extends BaseDTO {

	/**
	 * 部门名称
	 */
	private String departmentName;

	/**
	 * 部门描述
	 */
	private String description;
	
	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 添加时间
	 */
	private Date createTime;

	/**
	 * 所属公司
	 */
	private CompanyDTO companyDTO;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
