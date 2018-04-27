package plfx.api.client.api.v1.gj.response;

import plfx.api.client.common.ApiResponse;

/**
 * @类功能说明：为国际机票订单付款响应
 * @类修改者：
 * @修改日期：2015-7-8上午11:11:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8上午11:11:58
 */
@SuppressWarnings("serial")
public class PayToJPOrderGJResponse extends ApiResponse {
	/**
	 * 结果代码：支付成功
	 */
	public final static String RESULT_PAY_SUCCESS = "1";
	/**
	 * 结果代码：价格错误
	 */
	public final static String RESULT_PRICE_ERROR = "-1";
	/**
	 * 结果代码：订单不存在
	 */
	public final static String RESULT_NOT_EXIST = "-2";
	/**
	 * 结果代码：当前订单不能支付
	 */
	public final static String RESULT_CANNOT_PAY = "-3";

	/**
	 * 分销平台订单号
	 */
	private String platformOrderId;

	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 订单总支付价格
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
