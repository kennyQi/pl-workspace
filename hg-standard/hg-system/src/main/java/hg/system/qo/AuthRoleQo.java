package hg.system.qo;

import hg.common.model.qo.DwzBasePaginQo;

/**
 * @类功能说明：查询角色Qo
 * @类修改者：zzb
 * @修改日期：2014年11月3日下午3:34:34
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月3日下午3:34:34
 */
@SuppressWarnings("serial")
public class AuthRoleQo extends DwzBasePaginQo {

	/**
	 * 角色名
	 */
	private String roleName;
	private boolean roleNameLike;

	/**
	 * 显示名
	 */
	private String displayName;
	private boolean displayNameLike;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isRoleNameLike() {
		return roleNameLike;
	}

	public void setRoleNameLike(boolean roleNameLike) {
		this.roleNameLike = roleNameLike;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isDisplayNameLike() {
		return displayNameLike;
	}

	public void setDisplayNameLike(boolean displayNameLike) {
		this.displayNameLike = displayNameLike;
	}

}
