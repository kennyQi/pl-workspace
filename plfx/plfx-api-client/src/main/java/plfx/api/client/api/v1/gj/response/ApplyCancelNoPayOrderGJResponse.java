package plfx.api.client.api.v1.gj.response;

import plfx.api.client.common.ApiResponse;

/**
 * @类功能说明：申请取消未付款的订单结果
 * @类修改者：
 * @修改日期：2015-7-7下午3:50:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-7下午3:50:21
 */
@SuppressWarnings("serial")
public class ApplyCancelNoPayOrderGJResponse extends ApiResponse {
	/**
	 * 结果代码：订单不存在
	 */
	public final static String RESULT_NOT_EXIST = "-1";
	/**
	 * 结果代码：订单已被支付过或正在审核
	 */
	public final static String RESULT_PAID_OR_AUDIT = "-2";
	/**
	 * 结果代码：缺少其他原因具体内容
	 */
	public final static String RESULT_NO_CANCEL_OTHER_REASON = "-3";
	/**
	 * 结果代码：订单已经被取消
	 */
	public final static String RESULT_CANCEL_ALREADY = "-4";
	
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
