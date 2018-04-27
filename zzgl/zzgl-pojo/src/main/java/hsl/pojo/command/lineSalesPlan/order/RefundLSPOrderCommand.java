package hsl.pojo.command.lineSalesPlan.order;

import hg.common.component.BaseCommand;

/**
 * @类功能说明：退款线路销售方案订单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/10 11:24
 */
public class RefundLSPOrderCommand extends BaseCommand{
	/**
	 * lsp订单主键ID
	 */
	private String orderId;
	/**
	 * 退款金额
	 */
	private Double refundPrice;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Double getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(Double refundPrice) {
		this.refundPrice = refundPrice;
	}
}
