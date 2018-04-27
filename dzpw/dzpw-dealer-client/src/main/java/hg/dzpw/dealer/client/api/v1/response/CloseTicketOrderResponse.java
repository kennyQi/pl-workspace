package hg.dzpw.dealer.client.api.v1.response;

import hg.dzpw.dealer.client.common.ApiResponse;

/**
 * @类功能说明：关闭订单结果
 * @类修改者：
 * @修改日期：2015-4-22下午5:04:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-22下午5:04:18
 */
@SuppressWarnings("serial")
public class CloseTicketOrderResponse extends ApiResponse {
	
	/** 订单不存在 */
	public final static String RESULT_ORDER_NOT_EXISTS = "-1";
	/** 不能关闭已经关闭的订单 */
	public final static String RESULT_ORDER_CLOSED = "-2";
	/** 不能关闭已经支付的订单 */
	public final static String RESULT_ORDER_PAID = "-3";
	/** 关闭失败 */
	public final static String RESULT_FAIL = "-4";
	
}
