package hg.dzpw.dealer.client.api.v1.response;

import hg.dzpw.dealer.client.common.ApiResponse;
import hg.dzpw.dealer.client.dto.order.TicketOrderDTO;

/**
 * @类功能说明：下单结果
 * @类修改者：
 * @修改日期：2014-11-26上午11:17:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-26上午11:17:17
 */
@SuppressWarnings("serial")
public class CreateTicketOrderResponse extends ApiResponse {
	
	/** 参数错误 */
	public final static String RESULT_PARAM_ERROR = "-1";
	/** 经销商订单号重复 */
	public final static String RESULT_DEALER_ORDER_CODE_REPEAT = "-2";
	/** 可售门票数不足 */
	public final static String RESULT_REMAINING_QTY_LOW = "-3";
	/** 订单价格错误 */
	public final static String RESULT_ORDER_PRICE_ERROR = "-4";
	/** 门票政策版本错误 */
	public final static String RESULT_TICKET_POLICY_VERSION_ERROR = "-5";
	/** 门票政策不存在或被删除 */
	public final static String RESULT_TICKET_POLICY_NOT_EXISTS = "-6";
	/** 出游日期错误 */
	public final static String RESULT_TRAVEL_DATE_ERROR = "-7";

	
	/**
	 * 订单详情
	 */
	private TicketOrderDTO ticketOrder;


	public TicketOrderDTO getTicketOrder() {
		return ticketOrder;
	}

	public void setTicketOrder(TicketOrderDTO ticketOrder) {
		this.ticketOrder = ticketOrder;
	}

}
