package hg.system.command.perm;

import hg.common.component.BaseCommand;
import hg.common.util.StringUtil;

import java.util.List;

/**
 * 
 * @类功能说明：删除资源_command
 * @类修改者：zzb
 * @修改日期：2014年10月31日上午10:58:30
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月31日上午10:58:30
 *
 */
@SuppressWarnings("serial")
public class RemoveAuthPermCommand extends BaseCommand {
	
	/**
	 * 资源ids
	 */
	private String permIds;

	
	public String getPermIds() {
		return permIds;
	}

	public List<String> getPermIdList() {
		return StringUtil.splitToList(getPermIds(), ",");
	}

	public void setPermIds(String permIds) {
		this.permIds = permIds;
	}
	


}
