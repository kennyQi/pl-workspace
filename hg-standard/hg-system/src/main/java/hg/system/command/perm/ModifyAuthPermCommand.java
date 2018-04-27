package hg.system.command.perm;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：资源编辑command
 * @类修改者：zzb
 * @修改日期：2014年11月3日上午10:27:59
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月3日上午10:27:59
 */
@SuppressWarnings("serial")
public class ModifyAuthPermCommand extends BaseCommand {

	/**
	 * 资源id
	 */
	private String permId;
	
	/**
	 * 资源名称
	 */
	private String displayName;

	/**
	 * 资源链接
	 */
	private String url;

	/**
	 * 资源类型
	 */
	private Short permType;

	/**
	 * 角色
	 */
	private String permRole;

	/**
	 * 上级资源
	 */
	private String parentId;

	
	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Short getPermType() {
		return permType;
	}

	public void setPermType(Short permType) {
		this.permType = permType;
	}

	public String getPermRole() {
		return permRole;
	}

	public void setPermRole(String permRole) {
		this.permRole = permRole;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
