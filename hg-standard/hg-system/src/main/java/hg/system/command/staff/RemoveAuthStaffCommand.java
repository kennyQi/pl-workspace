package hg.system.command.staff;

import hg.common.component.BaseCommand;
import hg.common.util.StringUtil;

import java.util.List;

/**
 * @类功能说明：删除员工_command
 * @类修改者：zzb
 * @修改日期：2014年11月5日上午10:30:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日上午10:30:37
 */
@SuppressWarnings("serial")
public class RemoveAuthStaffCommand extends BaseCommand {

	/**
	 * 员工ids
	 */
	private String staffIds;

	
	public String getStaffIds() {
		return staffIds;
	}

	public List<String> getStaffIdList() {
		return StringUtil.splitToList(getStaffIds(), ",");
	}

	public void setStaffIds(String staffIds) {
		this.staffIds = staffIds;
	}
	


}
