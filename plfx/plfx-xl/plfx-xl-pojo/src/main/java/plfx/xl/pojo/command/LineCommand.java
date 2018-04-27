package plfx.xl.pojo.command;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月2日下午6:07:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LineCommand extends BaseCommand {

	/**
	 * 线路名称
	 */
	private String lineName;

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

}
