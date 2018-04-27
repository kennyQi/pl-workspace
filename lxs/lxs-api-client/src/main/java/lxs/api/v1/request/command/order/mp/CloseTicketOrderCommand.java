package lxs.api.v1.request.command.order.mp;

import lxs.api.base.ApiPayload;

/**
 * @类功能说明：关闭订单
 * @类修改者：
 * @修改日期：2015-4-22下午4:58:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-22下午4:58:21
 */
@SuppressWarnings("serial")
public class CloseTicketOrderCommand extends ApiPayload {

	private String orderID;

	private String orderNO;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderNO() {
		return orderNO;
	}

	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}

}
