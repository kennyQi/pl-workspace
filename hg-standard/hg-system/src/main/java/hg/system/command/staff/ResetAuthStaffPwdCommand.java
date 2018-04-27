package hg.system.command.staff;

import hg.common.component.BaseCommand;
import hg.common.util.StringUtil;

import java.util.List;

/**
 * @类功能说明：重置员工密码_command
 * @类修改者：zzb
 * @修改日期：2014年11月5日下午3:48:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日下午3:48:25
 */
@SuppressWarnings("serial")
public class ResetAuthStaffPwdCommand extends BaseCommand {

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
