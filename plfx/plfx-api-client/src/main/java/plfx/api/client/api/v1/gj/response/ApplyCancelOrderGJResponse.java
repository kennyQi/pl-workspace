package plfx.api.client.api.v1.gj.response;

import plfx.api.client.common.ApiResponse;

/**
 * @类功能说明：申请取消已付款未出票的订单结果
 * @类修改者：
 * @修改日期：2015-7-7下午3:51:01
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-7下午3:51:01
 */
@SuppressWarnings("serial")
public class ApplyCancelOrderGJResponse extends ApiResponse {
	/**
	 * 结果代码：订单不存在
	 */
	public final static String RESULT_NOT_EXIST = "-1";
	/**
	 * 结果代码：非待出票的订单不能取消
	 */
	public final static String RESULT_NOT_OUT_TICKET_WAIT = "-2";

	/**
	 * 分销平台订单号
	 */
	private String platformOrderId;
	
	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

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

}
