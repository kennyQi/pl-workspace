package hg.system.command.role;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：创建角色_command
 * @类修改者：zzb
 * @修改日期：2014年11月4日上午11:05:28
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月4日上午11:05:28
 */
@SuppressWarnings("serial")
public class CreateAuthRoleCommand extends BaseCommand {

	/**
	 * 角色名
	 */
	private String roleName;

	/**
	 * 显示名
	 */
	private String displayName;

	/**
	 * 资源ids
	 */
	private String permIds;

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

	public String getPermIds() {
		return permIds;
	}

	public void setPermIds(String permIds) {
		this.permIds = permIds;
	}


}
