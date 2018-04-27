package hg.dzpw.dealer.client.api.v1.response;

import hg.dzpw.dealer.client.common.ApiResponse;

/**
 * @类功能说明：确认支付结果
 * @类修改者：
 * @修改日期：2014-11-26上午11:17:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-26上午11:17:17
 */
@SuppressWarnings("serial")
public class PayToTicketOrderResponse extends ApiResponse {
	/** 订单不存在 */
	public final static String RESULT_ORDER_NOT_EXISTS = "-1";
	/** 支付失败 */
	public final static String RESULT_PAY_ERROR = "-2";
	/** 余额不足 */
	public final static String RESULT_MONEY_NOT_ENOUGH = "-3";
	/** 不能为已经关闭的订单付款 */
	public final static String RESULT_ORDER_CLOSED = "-4";
	/** 不能为已经付款的订单付款 */
	public final static String RESULT_ORDER_PAID = "-5";

}
