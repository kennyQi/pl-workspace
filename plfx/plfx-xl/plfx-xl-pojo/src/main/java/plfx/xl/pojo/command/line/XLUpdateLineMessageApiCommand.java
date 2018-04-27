package plfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;


/**
 * 
 * @类功能说明：api接口线路修改消息通知command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年2月2日下午2:07:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLUpdateLineMessageApiCommand extends BaseCommand {
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
