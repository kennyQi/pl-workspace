package lxs.pojo.command.mp;

/**
 * @类功能说明：退款
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-7-17下午2:24:46
 * @版本：V1.0
 *
 */
public class ApplyRefundCommand{
	
	/**
	 * 退款门票号
	 * 支持统一订单下的多票号一起申请
	 * 必填
	 */
	private String[] ticketNos;
	
	/**
	 * 退款票号所属电子票务订单号
	 * 必填
	 */
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String[] getTicketNos() {
		return ticketNos;
	}

	public void setTicketNos(String[] ticketNos) {
		this.ticketNos = ticketNos;
	}
	
}
