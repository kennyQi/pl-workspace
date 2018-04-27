package hg.dzpw.dealer.client.api.v1.response;

import java.util.List;

import hg.dzpw.dealer.client.common.ApiResponse;
import hg.dzpw.dealer.client.dto.refund.GroupTicketRefundDTO;

@SuppressWarnings("serial")
public class RefundResponse extends ApiResponse {
	
	/** 订单不存在 */
	public final static String RESULT_ORDER_NOT_EXISTS = "-1";
	/** 非同一订单门票不能申请退款 */
	public final static String RESULT_TICKET_NOT_IN_SAME_ORDER = "-2";
	/** 门票状态不能申请退款 */
	public final static String RESULT_TICKET_CURRENT_NOT_APPLY_REFUND = "-3";
	/** 门票的不支持退款 */
	public final static String RESULT_TICKET_NOT_SUPPORT_REFUND = "-4";
	/** 门票重复申请退款 */
	public final static String RESULT_TICKET_REPEAT_APPLY_REFUND = "-5";
	/** 申请失败 */
	public final static String RESULT_REFUND_ERROR = "-6";
	/** 超过退款可申请期限 */
	public final static String RESULT_REFUND_OUT_OF_DATE = "-7";
	
	/** 流程
	 *  a.校验必填参数
	 *  b.校验订单是否存在    票号是否在同一订单下  校验申请退款订单是否为当前经销商的订单（如不是 返回订单不存在）
	 *  c.校验订单对应门票政策是否支持退款
	 *  d.校验门票的状态是否 可以申请退款   必须为退款待处理  其他状态都不能申请
	 *  e.校验记录表是否已经有记录   订单号和票号存在
	 *  
	 *  注:校验中有一个票号不符合  整个申请就失败    在response里的List中设置异常票号和描述
	 * 
	 * */
	
	
	
	
	
//	/**不能给已退款的订单退款*/
//	public final static String RESULT_REFUND_ORDER_AGAIN = "-3";
//	/**不能给已关闭的订单退款*/
//	public final static String RESULT_REFUND_ORDER_CLOSE = "-4";
//	/**不能给未出票的订单退款*/
//	public final static String RESULT_REFUND_ORDER_UNPAY = "-5";
//	/**付款方(DZPW)余额不足*/
//	public final static String RESULT_REFUND_MONEY_ENOUGH = "-6";
//	/**订单已消费*/
//	public final static String RESULT_REFUND_ORDER_USE = "-7";
//	/**订单不支持退款*/
//	public final static String RESULT_REFUND_REFUSED = "-8";
	
	
	/** 申请退款的异常门票集合 */
	private List<GroupTicketRefundDTO> groupTicketRefunds;

	public List<GroupTicketRefundDTO> getGroupTicketRefunds() {
		return groupTicketRefunds;
	}

	public void setGroupTicketRefunds(List<GroupTicketRefundDTO> groupTicketRefunds) {
		this.groupTicketRefunds = groupTicketRefunds;
	}
	
}
