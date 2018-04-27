package plfx.xl.domain.model.event;

import hg.common.component.BaseEvent;
import hg.common.util.SysProperties;

/**
 * 
 * @类功能说明：线路同步事件
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandegn
 * @创建时间：2015年2月3日上午10:11:05
 * 
 */
@SuppressWarnings("serial")
public class SynchronizationLineEvent extends BaseEvent {

	public SynchronizationLineEvent(String name, String params, String describe) {
		super(name, params, describe, 
				SysProperties.getInstance().get("projectId"), 
				SysProperties.getInstance().get("envId"));
	}
	
	/*private String lineId;
	
	private String lineDealerId;

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineDealerId() {
		return lineDealerId;
	}

	public void setLineDealerId(String lineDealerId) {
		this.lineDealerId = lineDealerId;
	}*/
	
}
