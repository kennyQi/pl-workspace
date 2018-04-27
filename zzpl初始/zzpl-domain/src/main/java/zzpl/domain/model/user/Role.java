package zzpl.domain.model.user;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import zzpl.domain.model.M;
import hg.common.component.BaseModel;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_USER + "ROLE")
public class Role extends BaseModel {

	/**
	 * 角色名称
	 */
	@Column(name = "ROLE_NAME", length = 512)
	private String roleName;

	/**
	 * 角色描述
	 */
	@Column(name = "DESCRIPTION", length = 512)
	private String description;

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

	/**
	 * 关联用户信息
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = { CascadeType.ALL })
	private List<UserRole> userRoles;

	/**
	 * 关联菜单信息
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = { CascadeType.ALL })
	private List<RoleMenu> roleMenus;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<RoleMenu> getRoleMenus() {
		return roleMenus;
	}

	public void setRoleMenus(List<RoleMenu> roleMenus) {
		this.roleMenus = roleMenus;
	}

}
