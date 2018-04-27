package plfx.gjjp.domain.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @类功能说明：票量分销国际机票常量
 * @类修改者：
 * @修改日期：2015-7-2下午3:11:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午3:11:54
 */
public class GJJPConstants {

	/**
	 * 供应商订单状态
	 */
	public final static Map<Integer, String> SUPPLIER_ORDER_STATUS_MAP = new LinkedHashMap<Integer, String>();

	/**
	 * 平台订单状态
	 */
	public final static Map<Integer, String> ORDER_STATUS_MAP = new LinkedHashMap<Integer, String>();

	/**
	 * 供应商机票状态
	 */
	public final static Map<Integer, String> SUPPLIER_TICKET_STATUS_MAP = new LinkedHashMap<Integer, String>();

	/**
	 * 平台机票状态
	 */
	public final static Map<Integer, String> TICKET_STATUS_MAP = new LinkedHashMap<Integer, String>();

	/**
	 * 平台订单支付状态
	 */
	public final static Map<Integer, String> ORDER_PAY_STATUS_MAP = new LinkedHashMap<Integer, String>();

	/**
	 * 乘客类型
	 */
	public final static Map<Integer, String> PASSENGER_TYPE_MAP = new LinkedHashMap<Integer, String>();

	/**
	 * 证件类型
	 */
	public final static Map<Integer, String> IDTYPE_MAP = new LinkedHashMap<Integer, String>();
	/**
	 * 证件类型
	 */
	public final static Map<Integer, String> LOG_WORKER_TYPE_MAP = new LinkedHashMap<Integer, String>();
	
	// 操作人类型
	
	/**
	 * 操作人类型，分销平台管理员
	 */
	public final static Integer LOG_WORKER_TYPE_PLATFORM = 1;
	/**
	 * 操作人类型，经销商
	 */
	public final static Integer LOG_WORKER_TYPE_DEALER = 2;
	/**
	 * 操作人类型，供应商
	 */
	public final static Integer LOG_WORKER_TYPE_SUPPLIER = 3;
	/**
	 * 操作人类型，系统调度
	 */
	public final static Integer LOG_WORKER_TYPE_SCHEDULER = 4;
	
	// 平台订单状态
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
	
	// 平台订单支付状态

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
	
	// 平台机票状态
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

		// 订单日志操作人类型
		LOG_WORKER_TYPE_MAP.put(LOG_WORKER_TYPE_PLATFORM, "分销平台管理员");
		LOG_WORKER_TYPE_MAP.put(LOG_WORKER_TYPE_DEALER, "经销商");
		LOG_WORKER_TYPE_MAP.put(LOG_WORKER_TYPE_SUPPLIER, "供应商");
		LOG_WORKER_TYPE_MAP.put(LOG_WORKER_TYPE_SCHEDULER, "系统调度");

		// 平台订单状态
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

		// 平台订单支付状态
		ORDER_PAY_STATUS_MAP.put(ORDER_PAY_CLOSED, "已关闭");
		ORDER_PAY_STATUS_MAP.put(ORDER_PAY_STATUS_WAIT, "待支付");
		ORDER_PAY_STATUS_MAP.put(ORDER_PAY_STATUS_ALREADY, "已支付");
		ORDER_PAY_STATUS_MAP.put(ORDER_PAY_STATUS_REFUND_WAIT, "待退款");
		ORDER_PAY_STATUS_MAP.put(ORDER_PAY_STATUS_REFUND_ALREADY, "部分退款");
		ORDER_PAY_STATUS_MAP.put(ORDER_PAY_STATUS_REFUND_SOME, "全部退款");

		// 供应商订单状态
		SUPPLIER_ORDER_STATUS_MAP.put(1, "等待支付");
		SUPPLIER_ORDER_STATUS_MAP.put(2, "支付成功");
		SUPPLIER_ORDER_STATUS_MAP.put(3, "处理完成");
		SUPPLIER_ORDER_STATUS_MAP.put(4, "客户删除");
		SUPPLIER_ORDER_STATUS_MAP.put(5, "退票完成");
		SUPPLIER_ORDER_STATUS_MAP.put(6, "自动出票失败");
		SUPPLIER_ORDER_STATUS_MAP.put(7, "申请取消");
		SUPPLIER_ORDER_STATUS_MAP.put(8, "供应商拒出票");
		SUPPLIER_ORDER_STATUS_MAP.put(9, "拒绝出票");
		SUPPLIER_ORDER_STATUS_MAP.put(10, "废票完成");
		SUPPLIER_ORDER_STATUS_MAP.put(11, "申请撤单");
		SUPPLIER_ORDER_STATUS_MAP.put(12, "平台审核");
		SUPPLIER_ORDER_STATUS_MAP.put(13, "平台审核退回");
		SUPPLIER_ORDER_STATUS_MAP.put(14, "供应审核");
		SUPPLIER_ORDER_STATUS_MAP.put(15, "供应审核退回");

		// 供应商机票状态
		SUPPLIER_TICKET_STATUS_MAP.put(1, "已订票");
		SUPPLIER_TICKET_STATUS_MAP.put(2, "已出票");
		SUPPLIER_TICKET_STATUS_MAP.put(3, "申请退票");
		SUPPLIER_TICKET_STATUS_MAP.put(4, "退票完成");
		SUPPLIER_TICKET_STATUS_MAP.put(5, "客户删除");
		SUPPLIER_TICKET_STATUS_MAP.put(6, "自动出票失败");
		SUPPLIER_TICKET_STATUS_MAP.put(7, "申请废票");
		SUPPLIER_TICKET_STATUS_MAP.put(8, "申请取消");
		SUPPLIER_TICKET_STATUS_MAP.put(9, "退废票被拒绝");
		SUPPLIER_TICKET_STATUS_MAP.put(10, "退废票被锁定");
		SUPPLIER_TICKET_STATUS_MAP.put(11, "再次申请退废票");
		SUPPLIER_TICKET_STATUS_MAP.put(12, "申请退废票解锁");
		SUPPLIER_TICKET_STATUS_MAP.put(13, "供应商拒绝退废票");
		SUPPLIER_TICKET_STATUS_MAP.put(14, "必须退废票");
		SUPPLIER_TICKET_STATUS_MAP.put(15, "退票时供应商退分润成功");
		SUPPLIER_TICKET_STATUS_MAP.put(16, "取消时供应商退分润成功");
		SUPPLIER_TICKET_STATUS_MAP.put(17, "客服申请退票");
		SUPPLIER_TICKET_STATUS_MAP.put(18, "客服申请退票成功");
		SUPPLIER_TICKET_STATUS_MAP.put(19, "供应商拒绝出票");
		SUPPLIER_TICKET_STATUS_MAP.put(20, "拒绝出票");
		SUPPLIER_TICKET_STATUS_MAP.put(21, "必须出票");
		SUPPLIER_TICKET_STATUS_MAP.put(22, "拒绝出票时供应商退分润成功");
		SUPPLIER_TICKET_STATUS_MAP.put(24, "废票完成");
		SUPPLIER_TICKET_STATUS_MAP.put(25, "暂缓退款");
		SUPPLIER_TICKET_STATUS_MAP.put(26, "退票理由审核拒绝");
		SUPPLIER_TICKET_STATUS_MAP.put(27, "申请撤单");

		// 平台机票状态
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