package plfx.api.client.api.v1.gj.event;

import plfx.api.client.common.publish.PublishEventMessage;

/**
 * @类功能说明：国际机票订单支付成功通知消息
 * @类修改者：
 * @修改日期：2015-7-8下午3:03:09
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8下午3:03:09
 */
public class GJPaySuccessEventMessage extends PublishEventMessage {

	/**
	 * 分销平台国际机票订单号
	 */
	private String platformOrderId;

	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 总支付金额
	 */
	private Double totalPrice;

	public String getPlatformOrderId() {
		return platformOrderId;
	}

	public void setPlatformOrderId(String platformOrderId) {
		this.platformOrderId = platformOrderId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
