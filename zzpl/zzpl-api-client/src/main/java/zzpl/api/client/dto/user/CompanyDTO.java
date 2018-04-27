package zzpl.api.client.dto.user;

import java.util.Date;
import java.util.List;

import zzpl.api.client.dto.BaseDTO;


@SuppressWarnings("serial")
public class CompanyDTO extends BaseDTO {

	/**
	 * 公司ID
	 */
	private String companyID;

	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 公司描述
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
	 * 关联部门
	 */
	private List<DepartmentDTO> departmentDTOs;

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public List<DepartmentDTO> getDepartmentDTOs() {
		return departmentDTOs;
	}

	public void setDepartmentDTOs(List<DepartmentDTO> departmentDTOs) {
		this.departmentDTOs = departmentDTOs;
	}

}
