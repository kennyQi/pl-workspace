package hg.dzpw.pojo.command.platform.order;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

import java.util.List;

/**
 * @类功能说明: 创建联票订单
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-21 下午1:42:40 
 * @版本：V1.0
 */
public class PlatformCancelTicketOrderCommand extends DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;

	/**
	 * 联票订单ID
	 */
	private List<String> ticketOrderId;

	public List<String> getTicketOrderId() {
		return ticketOrderId;
	}
	public void setTicketOrderId(List<String> ticketOrderId) {
		this.ticketOrderId = (null == ticketOrderId || ticketOrderId.size() < 1)?null:ticketOrderId;
	}
}