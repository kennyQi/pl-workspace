package hsl.pojo.command.lineSalesPlan.order;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/4 9:17
 */
public class UpdateLSPOrderStatusCommand extends BaseCommand {
	/**
	 * 订单的主键ID
	 */
	private String orderId;
	/**
	 * 订单状态
	 */
	private Integer status;
	/**
	 * 支付状态
	 */
	private Integer payStatus;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
}
