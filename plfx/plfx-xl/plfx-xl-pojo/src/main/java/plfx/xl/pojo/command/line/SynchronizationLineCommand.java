package plfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：分销同步线路第一步，线路创建或修改
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：tandeng
 * @创建时间：2015年2月3日上午9:28:08
 * 
 */
@SuppressWarnings("serial")
public class SynchronizationLineCommand extends BaseCommand {

	/**
	 * 线路Id
	 */
	private String lineId;
	/**
	 * 状态
	 */
	private String status;
	
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}