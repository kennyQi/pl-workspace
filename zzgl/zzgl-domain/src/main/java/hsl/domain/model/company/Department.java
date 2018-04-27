package hsl.domain.model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

/**
 * 
 * @类功能说明：部门
 * @类修改者：
 * @修改日期：2014年9月16日下午4:40:40
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年9月16日下午4:40:40
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_HSL_COMPANY + "DEPARTMENT")
public class Department extends BaseModel {
	/*
	 * 部门名称
	 */
	@Column(name = "DEPTNAME", length = 10)
	private String deptName;
	/*
	 * 描述信息
	 */
	@Column(name = "DESCINFO", length = 200)
	private String descInfo;
	/*
	 * 父部门ID（）
	 */
	@Column(name = "PARDEPTID", length = 200)
	private String parDeptId;
	/*
	 * 企业的外键
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDescInfo() {
		return descInfo;
	}

	public void setDescInfo(String descInfo) {
		this.descInfo = descInfo;
	}

	public String getParDeptId() {
		return parDeptId;
	}

	public void setParDeptId(String parDeptId) {
		this.parDeptId = parDeptId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
