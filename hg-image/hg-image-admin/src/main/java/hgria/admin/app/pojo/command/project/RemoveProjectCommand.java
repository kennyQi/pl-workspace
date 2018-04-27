package hgria.admin.app.pojo.command.project;

import java.util.List;

import hg.common.util.StringUtil;
import hgria.admin.app.pojo.command.BaseCommand;

/**
 * @类功能说明：删除工程_command
 * @类修改者：zzb
 * @修改日期：2014年11月28日下午4:57:02
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月28日下午4:57:02
 */
@SuppressWarnings("serial")
public class RemoveProjectCommand extends BaseCommand {

	/**
	 * 工程IDS
	 */
	private String projectIds;

	public String getProjectIds() {
		return projectIds;
	}
	
	public List<String> getProjectIdList() {
		return StringUtil.splitToList(getProjectIds(), ",");
	}

	public void setProjectIds(String projectIds) {
		this.projectIds = projectIds;
	}

}
