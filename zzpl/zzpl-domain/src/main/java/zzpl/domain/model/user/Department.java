package zzpl.domain.model.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import zzpl.domain.model.M;
import hg.common.component.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_USER + "DEPARTMENT")
public class Department extends BaseModel {

	/**
	 * 部门名称
	 */
	@Column(name = "DEPARTMENT_NAME", length = 512)
	private String departmentName;

	/**
	 * 部门描述
	 */
	@Column(name = "DESCRIPTION", length = 512)
	private String description;
	
	/**
	 * 状态
	 * 0:正常
	 * 1:删除
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer status;

	/**
	 * 排序
	 */
	@Column(name = "SORT", columnDefinition = M.NUM_COLUM)
	private Integer sort;

	/**
	 * 添加时间
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date createTime;

	/**
	 * 所属公司
	 */
	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
