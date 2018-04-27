package hsl.pojo.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 汇商旅常量
 *
 * @author zhurz
 * @since 1.5
 */
public interface HSLConstants {

	/**
	 * 常量MAP
	 *
	 * @param <K> 键
	 * @param <V> 值
	 * @author zhurz
	 * @since 1.5.1
	 */
	@SuppressWarnings("serial")
	class ConstantsMap<K, V> extends LinkedHashMap<K, V> {
		@Override
		public V get(Object key) {
			V v = super.get(key);
			if (v == null && key instanceof String && ((String) key).matches("^[\\d]+$")) {
				return super.get(Integer.valueOf(key.toString()));
			}
			return v;
		}
	}

	/**
	 * 游客常量
	 *
	 * @author zhurz
	 * @since 1.5
	 */
	interface Traveler {
		/**
		 * 游客类型：成人
		 */
		Integer TYPE_ADULT = 1;
		/**
		 * 游客类型：儿童
		 */
		Integer TYPE_CHILD = 2;

		/**
		 * 证件类型：身份证
		 */
		Integer ID_TYPE_SFZ = 1;
	}
	
	/**
	 * 线路订单支付类型
	 *
	 * @author zhurz
	 * @since 1.5
	 */
	interface LineOrderPayType {
		/**
		 * 支付类型：微信支付
		 */
		Integer PAY_TYPE_WX = 1;
		
		/**
		 * 支付类型：支付宝
		 */
		Integer PAY_TYPE_ALIPAY = 2;

		/**
		 * 支付类型：免费
		 */
		Integer PAY_TYPE_FREE = 99;
	}

	/**
	 * 配置KEY
	 *
	 * @author zhurz
	 * @since 1.6.1
	 */
	interface HSLConfigKey {
		/**
		 * 微信自动回复
		 */
		String KEY_WX_REPLY = "WX_REPLY";

		/**
		 * 微信菜单
		 */
		String KEY_WX_MENU = "WX_MENU";
	}

	/**
	 * 来源类型
	 *
	 * @author zhurz
	 * @since 1.7
	 */
	interface FromType {
		/**
		 * 移动端
		 */
		Integer FROM_TYPE_MOBILE = 0;
		/**
		 * PC WEB端
		 */
		Integer FROM_TYPE_PC = 1;
	}

	/**
	 * 支付平台类型
	 *
	 * @author zhurz
	 * @since 1.7
	 */
	interface PaymentPlatformType {
		/**
		 * 支付平台类型：支付宝
		 */
		Integer PAYMENT_PLATFORM_TYPE_ALIPAY = 1;
	}

	/**
	 * 易行机票订单退废票申请常量
	 *
	 * @author zhurz
	 * @since 1.7
	 */
	interface YXJPOrderRefundApply {
		/**
		 * 申请种类：1.当日作废
		 */
		Integer REFUND_TYPE_SAMEDAY = 1;
		/**
		 * 申请种类：2.自愿退票
		 */
		Integer REFUND_TYPE_VOLUNTARY = 2;
		/**
		 * 申请种类：3.非自愿退票
		 */
		Integer REFUND_TYPE_INVOLUNTARY = 3;
		/**
		 * 申请种类：4.差错退款
		 */
		Integer REFUND_TYPE_FAIL = 4;
		/**
		 * 申请种类：5.其他
		 */
		Integer REFUND_TYPE_OTHER = 5;

		@SuppressWarnings("serial")
		Map<Integer, String> REFUND_TYPE_MAP = new ConstantsMap<Integer, String>() {{
			put(REFUND_TYPE_SAMEDAY, "当日作废");
			put(REFUND_TYPE_VOLUNTARY, "自愿退票");
			put(REFUND_TYPE_INVOLUNTARY, "非自愿退票");
			put(REFUND_TYPE_FAIL, "差错退款");
			put(REFUND_TYPE_OTHER, "其他");
		}};

	}

	/**
	 * 易行机票订单乘客机票常量
	 *
	 * @author zhurz
	 * @since 1.7
	 */
	interface YXJPOrderPassengerTicket {
		/**
		 * 机票状态：待支付
		 */
		Integer STATUS_PAY_WAIT = 1;
		/**
		 * 机票状态：已取消未付款
		 */
		Integer STATUS_TICKET_CANCEL_NOPAY = 2;
		/**
		 * 机票状态：出票处理中
		 */
		Integer STATUS_TICKET_DEALING = 3;
		/**
		 * 机票状态：已出票
		 */
		Integer STATUS_TICKET_SUCC = 4;
		/**
		 * 机票状态：出票失败待退款
		 */
		Integer STATUS_TICKET_FAIL_REBACK_WAIT = 5;
		/**
		 * 机票状态：出票失败已退款
		 */
		Integer STATUS_TICKET_FAIL_REBACK_SUCC = 6;
		/**
		 * 机票状态：退废处理中
		 */
		Integer STATUS_TICKET_REFUND_DEALING = 7;
		/**
		 * 机票状态：退废失败
		 */
		Integer STATUS_TICKET_REFUND_FAIL = 8;
		/**
		 * 机票状态：退废成功待退款
		 */
		Integer STATUS_TICKET_REFUND_SUCC_REBACK_WAIT = 9;
		/**
		 * 机票状态：退废成功已退款
		 */
		Integer STATUS_TICKET_REFUND_SUCC_REBACK_SUCC = 10;

		@SuppressWarnings("serial")
		Map<Integer, String> yxOrderStatusMap = new ConstantsMap<Integer, String>() {{
			put(STATUS_PAY_WAIT, "待支付");
			put(STATUS_TICKET_CANCEL_NOPAY, "已取消未付款");
			put(STATUS_TICKET_DEALING, "出票处理中");
			put(STATUS_TICKET_SUCC, "已出票");
			put(STATUS_TICKET_FAIL_REBACK_WAIT, "出票失败待退款");
			put(STATUS_TICKET_FAIL_REBACK_SUCC, "出票失败已退款");
			put(STATUS_TICKET_REFUND_DEALING, "退废处理中");
			put(STATUS_TICKET_REFUND_FAIL, "退废失败");
			put(STATUS_TICKET_REFUND_SUCC_REBACK_SUCC, "退废成功已退款");
			put(STATUS_TICKET_REFUND_SUCC_REBACK_WAIT, "退废成功待退款");
		}};

	}

	/**
	 * 电子票务门票政策类型
	 *
	 * @author zhurz
	 * @since 1.8
	 */
	interface DZPWTicketPolicyType {

		/**
		 * 门票政策类型：独立单票
		 */
		Integer TICKET_POLICY_TYPE_SINGLE = 1;
		/**
		 * 门票政策类型：套票（联票）
		 */
		Integer TICKET_POLICY_TYPE_GROUP = 2;

		/**
		 * 门票政策类型：套票（联票）下的单票，这类门票政策不做展示
		 */
		Integer TICKET_POLICY_TYPE_SINGLE_IN_GROUP = 3;
		/**
		 * 门票政策类型：独立单票下的单票，这类门票政策不做展示
		 */
		Integer TICKET_POLICY_TYPE_SINGLE_IN_SINGLE = 4;

	}

	/**
	 * 电子票务政策退票规则
	 *
	 * @author zhurz
	 * @since 1.8
	 */
	interface DZPWTicketPolicySellReturnRule {
		
		/**
		 * 退票规则：不能退
		 */
		Integer RETURN_RULE_DISABLE = 1;
		/**
		 * 退票规则：无条件退
		 */
		Integer RETURN_RULE_UNCONDITIONAL = 2;
		/**
		 * 退票规则：退票收取百分比手续费
		 */
		Integer RETURN_RULE_COST_PERCENT = 3;
		/**
		 * 退票规则：退票收取X元手续费
		 */
		Integer RETURN_RULE_COST_RMB_YUAN = 4;
	}

	/**
	 * 电子票务政策使用的验证类型
	 *
	 * @author zhurz
	 * @since 1.8
	 */
	interface DZPTicketPolicyValidUseDateType {

		/**
		 * 门票使用条件：固定有效期
		 */
		Integer VALID_USE_DATE_TYPE_FIXED = 1;

		/**
		 * 门票使用条件：有效天数
		 */
		Integer VALID_USE_DATE_TYPE_DAYS = 2;

	}

	/**
	 * 门票类型
	 *
	 * @author zhurz
	 * @since 1.8
	 */
	interface DZPGroupTicketType {

		/**
		 * 门票类型：独立单票
		 */
		Integer TICKET_TYPE_SINGLE = 1;

		/**
		 * 门票类型：联票
		 */
		Integer TICKET_TYPE_GROUP = 2;

	}

	/**
	 * 联票状态
	 *
	 * @author zhurz
	 * @since 1.8
	 */
	interface DZPGroupTicketStatus {

		/**
		 * 等待用户支付
		 */
		Integer GROUP_TICKET_CURRENT_USER_PAY_WAIT = 1;
		/**
		 * 用户支付成功，等待经销商（本平台）支付
		 */
		Integer GROUP_TICKET_CURRENT_DEALER_PAY_WAIT = 2;
		/**
		 * 经销商（本平台）支付成功
		 */
		Integer GROUP_TICKET_CURRENT_DEALER_PAY_SUCC = 3;
		/**
		 * 出票成功
		 */
		Integer GROUP_TICKET_CURRENT_OUT_SUCC = 4;
		/**
		 * 交易成功
		 */
		Integer GROUP_TICKET_CURRENT_TRADE_SUCC = 5;
		/**
		 * 交易关闭
		 * 1.由于票务在规定之间内未支付或者有经销商自主发起的取消订单操作。取消订单为同一订单号所有票务统一取消
		 * 2.当退款状态为“退款成功”，标记退款状态为交易关闭
		 */
		Integer GROUP_TICKET_CURRENT_TRADE_CLOSE = 6;
	}

	/**
	 * 联票退款状态
	 *
	 * @author zhurz
	 * @since 1.8
	 */
	interface DZPGroupTicketRefundStatus {
		/**
		 * 无退款
		 */
		Integer GROUP_TICKET_REFUND_CURRENT_ALL_NONE = 0;
		/**
		 * 退款成功（当票务中所有景区的状态为已退款给用户）
		 */
		Integer GROUP_TICKET_REFUND_CURRENT_ALL_SUCC = 1;
		/**
		 * 部分退款成功（当票务中只有部分景区的状态为已退款给用户）
		 */
		Integer GROUP_TICKET_REFUND_CURRENT_SOME_SUCC = 2;
		/**
		 * 退款待处理（当景区状态中存在待退款状态时显示，待退款给经销商或用户都算）
		 */
		Integer GROUP_TICKET_REFUND_CURRENT_WAIT_HANDLE = 3;

		Map<String,String> dzpTicketOrderRefundStatusMap = new LinkedHashMap<String, String>(){{
			put("0","无退款");
			put("1","退款成功");
			put("2","部分退款成功");
			put("3","退款待处理");
		}};
	}

	/**
	 * 电子票订单状态
	 *
	 * @author zhurz
	 * @since 1.8
	 */
	interface DZPTicketOrderStatus {

		/**
		 * 等待用户支付
		 */
		Integer ORDER_STATUS_USER_PAY_WAIT = 1;
		/**
		 * 用户支付成功，等待经销商（本平台）支付
		 */
		Integer ORDER_STATUS_DEALER_PAY_WAIT = 2;
		/**
		 * 经销商（本平台）支付成功
		 */
		Integer ORDER_STATUS_DEALER_PAY_SUCC = 3;
		/**
		 * 出票成功
		 */
		Integer ORDER_STATUS_OUT_SUCC = 4;
		/**
		 * 交易成功
		 */
		Integer ORDER_STATUS_TRADE_SUCC = 5;
		/**
		 * 交易关闭
		 */
		Integer ORDER_STATUS_TRADE_CLOSE = 6;

		Map<String,String> dzpTicketOrderStatusMap = new LinkedHashMap<String, String>(){{
			put("1","待支付");
			put("2","用户支付成功");
			put("3","经销商支付成功");
			put("4","出票成功");
			put("5","交易成功");
			put("6","交易关闭");
		}};


	}

	/**
	 * 电子票游客证件类型
	 *
	 * @author zhurz
	 * @since 1.8
	 */
	interface DZPTouristIdType {
		/**
		 * 身份证
		 */
		Integer ID_TYPE_ID = 1;
		/**
		 * 军官证
		 */
		Integer ID_TYPE_OFFICER = 2;
		/**
		 * 驾驶证
		 */
		Integer ID_TYPE_DRIVE = 3;
		/**
		 * 护照
		 */
		Integer ID_TYPE_PASSPORT = 4;
	}
	
	/**
	 * 单票状态
	 *
	 * @author zhurz
	 * @since 1.8
	 */
	interface DZPSingleTicketStatus {

		/**
		 * 待游玩
		 */
		Integer SINGLE_TICKET_CURRENT_UNUSE = 1;
		/**
		 * 已游玩
		 */
		Integer SINGLE_TICKET_CURRENT_USED = 2;
		/**
		 * 已失效（未支付订单的失效）
		 */
		Integer SINGLE_TICKET_USE_CURRENT_INVALID = 3;
		/**
		 * 无须退款（不支持退款的票）
		 */
		Integer SINGLE_TICKET_CURRENT_NO_REFUND = 4;
		/**
		 * 电子票务待退款给经销商（本平台）
		 */
		Integer SINGLE_TICKET_CURRENT_DEALER_WAIT_REFUND = 5;
		/**
		 * 电子票务已退款给经销商（本平台），待退款给用户
		 */
		Integer SINGLE_TICKET_CURRENT_USER_WAIT_REFUND = 6;
		/**
		 * 已退款给用户
		 */
		Integer SINGLE_TICKET_CURRENT_USER_REFUNDED = 7;

		Map<String,String> dzpSingleTicketStatusMap = new LinkedHashMap<String, String>(){{
			put("1","待游玩");
			put("2","已游玩");
			put("3","已失效");
			put("4","无须退款");
			put("5","待退款给经销商");
			put("6","待退款");
			put("7","已退款");
		}};
	}
}


