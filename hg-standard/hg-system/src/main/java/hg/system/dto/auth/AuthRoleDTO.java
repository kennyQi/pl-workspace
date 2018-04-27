package hg.system.dto.auth;

import hg.system.dto.EmbeddDTO;

/**
 * @类功能说明：角色DTO
 * @类修改者：zzb
 * @修改日期：2014年11月3日下午3:35:56
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月3日下午3:35:56
 */
@SuppressWarnings("serial")
public class AuthRoleDTO extends EmbeddDTO {

	/**
	 * 角色id
	 */
	private String id;

	/**
	 * 角色名
	 */
	private String roleName;

	/**
	 * 显示名
	 */
	private String displayName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}