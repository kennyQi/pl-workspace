package plfx.yxgjclient.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import plfx.yxgjclient.pojo.param.AvailJourney;
import plfx.yxgjclient.pojo.param.BaseParam;

public class YIGJConstant {
	/**
	 * 类NullConverter中用来指定父类静态变量
	 * 方便将继承类的属性也转成xml
	 * @author guotx
	 * @date 2015-07-23 16:30
	 *
	 */
	public final static List<Class<?>> classList = new ArrayList<Class<?>>();
	static {
		classList.add(BaseParam.class);
		classList.add(AvailJourney.class);
	}
	/**
	 * 取消未支付订单接口名称applyCancelOrderNoPay
	 */
	public static String APPLY_CANCEL_NOPAY="applyCancelOrderNoPay";
	/**
	 * 取消已支付订单接口名称applyCancelOrder
	 */
	public static String APPLY_CANCEL_ORDER="applyCancelOrder";
	/**
	 * 申请退费票接口名称applyRefundTicket
	 */
	public static String APPLY_REFUND_TICKET="applyRefundTicket";
	/**
	 * 生成订单接口名称createOrder
	 */
	public static String CREATE_ORDER="createOrder";
	/**
	 * 航段政策获取接口名称matchPoliciesBySegInfo
	 */
	public static String MATCH_POLICIES_BY_SEGINFO="matchPoliciesBySegInfo";
	/**
	 * 自动扣款接口名称payAuto
	 */
	public static String PAY_AUTO="payAuto";
	/**
	 * 出票规则接口名称queryAirRules
	 */
	public static String QUERY_AIR_RULES="queryAirRules";
	/**
	 * 航班查询接口名称queryFlight
	 */
	public static String QUERY_FLIGHT="queryFlight";
	/**
	 * 更多仓位接口名称queryMoreCabins
	 */
	public static String QUERY_MORE_CABINS="queryMoreCabins";
	/**
	 * 订单信息接口名称queryOrderDetail
	 */
	public static String QUERY_ORDER_DETAIL="queryOrderDetail";
	/**
	 * 订单状态接口名称queryOrderState
	 */
	public static String QUERY_ORDER_STATE="queryOrderState";
	/**
	 * 政策获取接口名称queryPolicies
	 */
	public static String QUERY_POLICIES="queryPolicies";
	/**
	 * 查询政策信息接口名称queryPolicyState
	 */
	public static String QUERY_POLICY_STATE="queryPolicyState";
	/**
	 * 查询订单票号接口名称queryTicketNo
	 */
	public static String QUERY_TICKET_NO="queryTicketNo";
	/**
	 * 查询退费票信息queryRefundState
	 */
	public static String QUERY_REFUND_STATE="queryRefundState";
	
	/**
	 * 国际机票对易行取消订单原因
	 * 2015-07-28 16:23:43
	 */
	public final static HashMap<String,String> GJ_CANCEL_ORDER_TYPE = new HashMap<String,String>();
	static {
		GJ_CANCEL_ORDER_TYPE.put("1", "扣率过低");
		GJ_CANCEL_ORDER_TYPE.put("2", "舱位过高");
		GJ_CANCEL_ORDER_TYPE.put("3", "价格不正确");
		GJ_CANCEL_ORDER_TYPE.put("4", "客人证件不齐");
		GJ_CANCEL_ORDER_TYPE.put("5", "行程未确定");
		GJ_CANCEL_ORDER_TYPE.put("6", "其他原因");
	}
	
	/**
	 * 国际机票退票原因
	 * 2015-07-28 16:20:43
	 */
	public final static HashMap<String,String> GJ_REFUND_TYPE_MAP = new HashMap<String,String>();
	static {
		GJ_REFUND_TYPE_MAP.put("1", "当日作废");
		GJ_REFUND_TYPE_MAP.put("2", "自愿退票");
		GJ_REFUND_TYPE_MAP.put("3", "非自愿退票");
		GJ_REFUND_TYPE_MAP.put("4", "其他");
	}
	/**
	 * 记录日志操作名称
	 */
	public static String GJ_REFUND_LOG_NAME="国际机票退废票";
	
	/**
	 * 记录日志操作名称
	 */
	public static String GJ_CANCEL_LOG_NAME="国际机票取消订单";
	
}
