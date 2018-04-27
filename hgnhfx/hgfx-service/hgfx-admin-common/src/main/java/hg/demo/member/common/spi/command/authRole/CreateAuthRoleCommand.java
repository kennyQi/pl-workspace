package hg.demo.member.common.spi.command.authRole;

import hg.framework.common.base.BaseSPICommand;

/**
 * 创建角色参数
 */
@SuppressWarnings("serial")
public class CreateAuthRoleCommand extends BaseSPICommand {
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 角色显示名
	 */
	private String displayName;
	/**
	 * 权限资源ID
	 */
	private String[] permIds;

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

	public String[] getPermIds() {
		return permIds;
	}

	public void setPermIds(String[] permIds) {
		this.permIds = permIds;
	}
	
}
