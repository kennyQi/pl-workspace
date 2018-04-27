package plfx.api.client.api.v1.gj.request;

import plfx.api.client.common.BaseClientCommand;
import plfx.api.client.common.api.PlfxApiAction;

/**
 * @类功能说明：为国际机票订单付款
 * @类修改者：
 * @修改日期：2015-7-6下午5:26:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-6下午5:26:57
 */
@SuppressWarnings("serial")
@PlfxApiAction(PlfxApiAction.GJ_PayToJPOrder)
public class PayToJPOrderGJCommand extends BaseClientCommand {

	/**
	 * 分销平台订单号
	 */
	private String platformOrderId;

	/**
	 * 确认支付总价
	 */
	private Double totalPrice;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
