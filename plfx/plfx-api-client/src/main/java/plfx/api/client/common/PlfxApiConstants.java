package plfx.api.client.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @类功能说明：票量分销常量
 * @类修改者：
 * @修改日期：2015-7-2下午3:11:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午3:11:54
 */
public class PlfxApiConstants {

	/**
	 * @类功能说明：国际机票常量
	 * @类修改者：
	 * @修改日期：2015-7-13下午6:08:28
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2015-7-13下午6:08:28
	 */
	public final static class GJ {

		/**
		 * 订单状态
		 */
		public final static Map<Integer, String> ORDER_STATUS_MAP = new HashMap<Integer, String>();

		/**
		 * 机票状态
		 */
		public final static Map<Integer, String> TICKET_STATUS_MAP = new HashMap<Integer, String>();

		/**
		 * 订单支付状态
		 */
		public final static Map<Integer, String> ORDER_PAY_STATUS_MAP = new HashMap<Integer, String>();

		/**
		 * 乘客类型
		 */
		public final static Map<Integer, String> PASSENGER_TYPE_MAP = new HashMap<Integer, String>();

		/**
		 * 证件类型
		 */
		public final static Map<Integer, String> IDTYPE_MAP = new HashMap<Integer, String>();

		// 订单状态
		/** 待向供应商下单 */
		public final static Integer ORDER_STATUS_ORDER_TO_SUPPLIER = 0;
		/** 待审核 */
		public final static Integer ORDER_STATUS_AUDIT_WAIT = 1;
		/** 待支付 */
		public final static Integer ORDER_STATUS_PAY_WAIT = 2;
		/** 审核未通过 */
		public final static Integer ORDER_STATUS_AUDIT_FAIL = 3;
		/** 待出票 */
		public final static Integer ORDER_STATUS_OUT_TICKET_WAIT = 4;
		/** 已出票 */
		public final static Integer ORDER_STATUS_OUT_TICKET_ALREADY = 5;
		/** 出票失败 */
		public final static Integer ORDER_STATUS_OUT_TICKET_FAIL = 6;
		/** 待取消 */
		public final static Integer ORDER_STATUS_CANCEL_WAIT = 7;
		/** 已取消 */
		public final static Integer ORDER_STATUS_CANCEL_ALREADY = 8;
		/** 待退废票 */
		public final static Integer ORDER_STATUS_REFUND_WAIT = 9;
		/** 全部退废票 */
		public final static Integer ORDER_STATUS_REFUND_ALL = 10;
		/** 部分退废票 */
		public final static Integer ORDER_STATUS_REFUND_SOME = 11;

		// 订单支付状态

		/** 已关闭 */
		public final static Integer ORDER_PAY_CLOSED = 0;
		/** 待支付 */
		public final static Integer ORDER_PAY_STATUS_WAIT = 1;
		/** 已支付 */
		public final static Integer ORDER_PAY_STATUS_ALREADY = 2;
		/** 待退款 */
		public final static Integer ORDER_PAY_STATUS_REFUND_WAIT = 3;
		/** 部分退款 */
		public final static Integer ORDER_PAY_STATUS_REFUND_ALREADY = 4;
		/** 全部退款 */
		public final static Integer ORDER_PAY_STATUS_REFUND_SOME = 5;

		// 机票状态
		/** 待审核 */
		public final static Integer TICKET_STATUS_AUDIT_WAIT = 1;
		/** 审核未通过 */
		public final static Integer TICKET_STATUS_AUDIT_FAIL = 2;
		/** 待支付 */
		public final static Integer TICKET_STATUS_PAY_WAIT = 3;
		/** 待出票 */
		public final static Integer TICKET_STATUS_OUT_TICKET_WAIT = 4;
		/** 已出票 */
		public final static Integer TICKET_STATUS_OUT_TICKET_ALREADY = 5;
		/** 出票失败 */
		public final static Integer TICKET_STATUS_OUT_TICKET_FAIL = 6;
		/** 待取消 */
		public final static Integer TICKET_STATUS_CANCEL_WAIT = 7;
		/** 已取消 */
		public final static Integer TICKET_STATUS_CANCEL_ALREADY = 8;
		/** 待退票 */
		public final static Integer TICKET_STATUS_REFUND_WAIT = 9;
		/** 已退票 */
		public final static Integer TICKET_STATUS_REFUND_ALREADY = 10;
		/** 待废票 */
		public final static Integer TICKET_STATUS_INVALID_WAIT = 11;
		/** 已废票 */
		public final static Integer TICKET_STATUS_INVALID_ALREADY = 12;

		static {

			// 订单状态
			ORDER_STATUS_MAP.put(ORDER_STATUS_ORDER_TO_SUPPLIER, "待向供应商下单");
			ORDER_STATUS_MAP.put(ORDER_STATUS_AUDIT_WAIT, "待审核");
			ORDER_STATUS_MAP.put(ORDER_STATUS_PAY_WAIT, "待支付");
			ORDER_STATUS_MAP.put(ORDER_STATUS_AUDIT_FAIL, "审核未通过");
			ORDER_STATUS_MAP.put(ORDER_STATUS_OUT_TICKET_WAIT, "待出票");
			ORDER_STATUS_MAP.put(ORDER_STATUS_OUT_TICKET_ALREADY, "已出票");
			ORDER_STATUS_MAP.put(ORDER_STATUS_OUT_TICKET_FAIL, "出票失败");
			ORDER_STATUS_MAP.put(ORDER_STATUS_CANCEL_WAIT, "待取消");
			ORDER_STATUS_MAP.put(ORDER_STATUS_CANCEL_ALREADY, "已取消");
			ORDER_STATUS_MAP.put(ORDER_STATUS_REFUND_WAIT, "待退废票");
			ORDER_STATUS_MAP.put(ORDER_STATUS_REFUND_ALL, "全部退废票");
			ORDER_STATUS_MAP.put(ORDER_STATUS_REFUND_SOME, "部分退废票");

			// 订单支付状态
			ORDER_PAY_STATUS_MAP.put(ORDER_PAY_CLOSED, "已关闭");
			ORDER_PAY_STATUS_MAP.put(ORDER_PAY_STATUS_WAIT, "待支付");
			ORDER_PAY_STATUS_MAP.put(ORDER_PAY_STATUS_ALREADY, "已支付");
			ORDER_PAY_STATUS_MAP.put(ORDER_PAY_STATUS_REFUND_WAIT, "待退款");
			ORDER_PAY_STATUS_MAP.put(ORDER_PAY_STATUS_REFUND_ALREADY, "部分退款");
			ORDER_PAY_STATUS_MAP.put(ORDER_PAY_STATUS_REFUND_SOME, "全部退款");

			// 机票状态
			TICKET_STATUS_MAP.put(TICKET_STATUS_AUDIT_WAIT, "待审核");
			TICKET_STATUS_MAP.put(TICKET_STATUS_AUDIT_FAIL, "审核未通过");
			TICKET_STATUS_MAP.put(TICKET_STATUS_PAY_WAIT, "待支付");
			TICKET_STATUS_MAP.put(TICKET_STATUS_OUT_TICKET_WAIT, "待出票");
			TICKET_STATUS_MAP.put(TICKET_STATUS_OUT_TICKET_ALREADY, "已出票");
			TICKET_STATUS_MAP.put(TICKET_STATUS_OUT_TICKET_FAIL, "出票失败");
			TICKET_STATUS_MAP.put(TICKET_STATUS_CANCEL_WAIT, "待取消");
			TICKET_STATUS_MAP.put(TICKET_STATUS_CANCEL_ALREADY, "已取消");
			TICKET_STATUS_MAP.put(TICKET_STATUS_REFUND_WAIT, "待退票");
			TICKET_STATUS_MAP.put(TICKET_STATUS_REFUND_ALREADY, "已退票");
			TICKET_STATUS_MAP.put(TICKET_STATUS_INVALID_WAIT, "待废票");
			TICKET_STATUS_MAP.put(TICKET_STATUS_INVALID_ALREADY, "已废票");

			// 乘客类型
			PASSENGER_TYPE_MAP.put(1, "一般成人");
			PASSENGER_TYPE_MAP.put(2, "学生");
			PASSENGER_TYPE_MAP.put(3, "青年");
			PASSENGER_TYPE_MAP.put(4, "移民");
			PASSENGER_TYPE_MAP.put(5, "劳务");
			PASSENGER_TYPE_MAP.put(6, "海员");
			PASSENGER_TYPE_MAP.put(7, "特殊身份");
			PASSENGER_TYPE_MAP.put(8, "一般儿童");
			PASSENGER_TYPE_MAP.put(9, "移民儿童");
			PASSENGER_TYPE_MAP.put(10, "婴儿");

			// 证件类型
			IDTYPE_MAP.put(1, "护照");
			IDTYPE_MAP.put(2, "港澳通行证");
			IDTYPE_MAP.put(3, "台胞证");
			IDTYPE_MAP.put(4, "台胞通行证");
			IDTYPE_MAP.put(5, "回乡证");
			IDTYPE_MAP.put(6, "国际海员证");
		}
	}

}