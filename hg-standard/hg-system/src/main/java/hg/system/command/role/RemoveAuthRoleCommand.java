package hg.system.command.role;

import hg.common.component.BaseCommand;
import hg.common.util.StringUtil;

import java.util.List;

/**
 * @类功能说明：删除角色command
 * @类修改者：zzb
 * @修改日期：2014年11月5日上午9:20:59
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日上午9:20:59
 */
@SuppressWarnings("serial")
public class RemoveAuthRoleCommand extends BaseCommand {

	/**
	 * 角色ids
	 */
	private String roleIds;

	
	public String getRoleIds() {
		return roleIds;
	}

	public List<String> getRoleIdList() {
		return StringUtil.splitToList(getRoleIds(), ",");
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	


}
