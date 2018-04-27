package slfx.jp.command.admin;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：管理员取消订单command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:03:10
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class AdminCancelOrderCommand extends BaseCommand {
	
	
	/**
	 * 平台订单id(非订单号)
	 */
	private String id;

	/**
	 * 订单状态
	 */
	private boolean cancel;

	/**
	 * 取消原因
	 */
	private String cancelRemark;
	
	/**
	 * 要取消的平台订单号
	 */
	private String orderId;

	/**
	 * 要取消的票号
	 */
	private String ticketNumbers;
	
	/**
	 * 要取消的状态
	 */
	private String cancelStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isCancel() {
		return cancel;
	}

	public String getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTicketNumbers() {
		return ticketNumbers;
	}

	public void setTicketNumbers(String ticketNumbers) {
		this.ticketNumbers = ticketNumbers;
	}
	
}
