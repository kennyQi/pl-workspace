package plfx.api.client.api.v1.gj.event;

import plfx.api.client.common.publish.PublishEventMessage;

/**
 * @类功能说明：国际机票订单取消事件消息
 * @类修改者：
 * @修改日期：2015-7-8下午3:04:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8下午3:04:53
 */
public class GJCancelOrderEventMessage extends PublishEventMessage {

	/**
	 * 分销平台国际机票订单号
	 */
	private String platformOrderId;
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 取消状态
	 * 
	 * 1：成功取消；2:拒绝取消
	 */
	private Integer cancleStatus;

	/**
	 * 拒绝取消原因
	 */
	private String cancleMemo;

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

	public Integer getCancleStatus() {
		return cancleStatus;
	}

	public void setCancleStatus(Integer cancleStatus) {
		this.cancleStatus = cancleStatus;
	}

	public String getCancleMemo() {
		return cancleMemo;
	}

	public void setCancleMemo(String cancleMemo) {
		this.cancleMemo = cancleMemo;
	}

}
