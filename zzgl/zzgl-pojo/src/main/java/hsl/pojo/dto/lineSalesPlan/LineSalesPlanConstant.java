package hsl.pojo.dto.lineSalesPlan;

import java.util.HashMap;

/**
 * @类功能说明：线路销售方案常量类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 14:12
 */
public class LineSalesPlanConstant {
	/*****************************线路销售方案状态************************/
	/**
	 * 未审核
	 */
	public static final Integer LSP_STATUS_NOCHECK=1;
	/**
	 * 未开始
	 */
	public static final Integer LSP_STATUS_NOBEGIN=2;
	/**
	 * 进行中（活动进行中不能修改销售方案的信息）
	 */
	public static final Integer LSP_STATUS_SALESING=3;
	/**
	 * 活动结束
	 */
	public static final Integer LSP_STATUS_END=4;
	/**
	 * 活动非正常结束（活动结束拼团失败）
	 */
	public static final Integer LSP_STATUS_END_GROUP_FAIL=5;

	/**
	 * 活动结束拼团成功（活动结束拼团成功）
	 */
	public static final Integer LSP_STATUS_END_GROUP_SUCC=6;

	public static final String LSP_STATUS_NOCHECK_VAL="未审核";

	public static final String LSP_STATUS_NOBEGIN_VAL="未开始";

	public static final String LSP_STATUS_SALESING_VAL="进行中";

	public static final String LSP_STATUS_END_VAL="活动结束";

	public static final String LSP_STATUS_END_GROUP_FAIL_VAL="活动结束拼团失败";

	public static final String LSP_STATUS_END_GROUP_SUCC_VAL="活动结束拼团成功";

	//销售方案订单状态
	public final static HashMap<Integer,String> LSP_STATUS= new HashMap<Integer,String>();
	static {
		LSP_STATUS.put(LSP_STATUS_NOCHECK, LSP_STATUS_NOCHECK_VAL);
		LSP_STATUS.put(LSP_STATUS_NOBEGIN,LSP_STATUS_NOBEGIN_VAL);
		LSP_STATUS.put(LSP_STATUS_SALESING, LSP_STATUS_SALESING_VAL);
		LSP_STATUS.put(LSP_STATUS_END, LSP_STATUS_END_VAL);
		LSP_STATUS.put(LSP_STATUS_END_GROUP_FAIL, LSP_STATUS_END_GROUP_FAIL_VAL);
		LSP_STATUS.put(LSP_STATUS_END_GROUP_SUCC, LSP_STATUS_END_GROUP_SUCC_VAL);
	}
	/*****************************线路销售方案显示状态***********************/

	public static final Integer LSP_SHOW_STATUS_HIDE=1;

	public static final Integer LSP_SHOW_STATUS_SHOW=2;

	/**************************销售方案订单状态**************************/
	public static final Integer LSP_ORDER_STATUS_SUCCESS_NOT_RESERVE=2001;

	public static final Integer LSP_ORDER_STATUS_RESERVE_SUCCESS=2003;

	public static final Integer LSP_ORDER_STATUS_CANCEL=2004;

	public static final Integer LSP_ORDER_STATUS_SUCCESS_GROUP_ING=2005;

	public static final Integer LSP_ORDER_STATUS_SUCCESS_GROUP_SUC=2006;

	public static final Integer LSP_ORDER_STATUS_SUCCESS_GROUP_ERR=2007;

	public static final Integer LSP_ORDER_STATUS_REFUND_DEALING=2008;

	public static final Integer LSP_ORDER_STATUS_REFUND_SUCCESS=2009;
	//订单状态
	public static final String LSP_ORDER_STATUS_SUCCESS_NOT_RESERVE_VAL="下单成功未占位";

	public static final String LSP_ORDER_STATUS_RESERVE_SUCCESS_VAL="预定成功";

	public static final String LSP_ORDER_STATUS_CANCEL_VAL="订单取消";

	public static final String LSP_ORDER_STATUS_SUCCESS_GROUP_ING_VAL="下单成功组团中";

	public static final String LSP_ORDER_STATUS_SUCCESS_GROUP_SUC_VAL="下单成功组团成功";

	public static final String LSP_ORDER_STATUS_SUCCESS_GROUP_ERR_VAL="下单成功组团失败";

	public static final String LSP_ORDER_STATUS_REFUND_DEALING_VAL="订单退款处理中";

	public static final String LSP_ORDER_STATUS_REFUND_SUCCESS_VAL="订单退款成功";
	//销售方案订单状态
	public final static HashMap<Integer,String> LSP_ORDER_STATUS= new HashMap<Integer,String>();
	static {
		LSP_ORDER_STATUS.put(LSP_ORDER_STATUS_SUCCESS_NOT_RESERVE, LSP_ORDER_STATUS_SUCCESS_NOT_RESERVE_VAL);
		LSP_ORDER_STATUS.put(LSP_ORDER_STATUS_RESERVE_SUCCESS,LSP_ORDER_STATUS_RESERVE_SUCCESS_VAL);
		LSP_ORDER_STATUS.put(LSP_ORDER_STATUS_CANCEL, LSP_ORDER_STATUS_CANCEL_VAL);

		LSP_ORDER_STATUS.put(LSP_ORDER_STATUS_SUCCESS_GROUP_ING, LSP_ORDER_STATUS_SUCCESS_GROUP_ING_VAL);
		LSP_ORDER_STATUS.put(LSP_ORDER_STATUS_SUCCESS_GROUP_SUC, LSP_ORDER_STATUS_SUCCESS_GROUP_SUC_VAL);
		LSP_ORDER_STATUS.put(LSP_ORDER_STATUS_SUCCESS_GROUP_ERR, LSP_ORDER_STATUS_SUCCESS_GROUP_ERR_VAL);

		LSP_ORDER_STATUS.put(LSP_ORDER_STATUS_REFUND_DEALING, LSP_ORDER_STATUS_REFUND_DEALING_VAL);
		LSP_ORDER_STATUS.put(LSP_ORDER_STATUS_REFUND_SUCCESS,LSP_ORDER_STATUS_REFUND_SUCCESS_VAL);
	}


	/**************************销售方案订单支付状态**************************/
	/****为了保证状态统一,支付状态值和分销保持统一******/
	public static final Integer LSP_PAY_STATUS_NOPAY=2101;

	public static final Integer LSP_PAY_STATUS_PAY_SUCCESS=2105;

	public static final Integer LSP_PAY_STATUS_REFUND_WAIT=2106;

	public static final Integer LSP_PAY_STATUS_REFUND_SUCCESS=2107;
	//支付状态
	public static final String LSP_PAY_STATUS_NOPAY_VAL="未支付";

	public static final String LSP_PAY_STATUS_PAY_SUCCESS_VAL="支付成功";

	public static final String LSP_PAY_STATUS_REFUND_WAIT_VAL="等待退款";

	public static final String LSP_PAY_STATUS_REFUND_SUCCESS_VAL="已退款";
	//销售方案订单支付状态
	public final static HashMap<Integer,String> LSP_ORDER_PAY_STATUS= new HashMap<Integer,String>();
	static {
		LSP_ORDER_PAY_STATUS.put(LSP_PAY_STATUS_NOPAY,LSP_PAY_STATUS_NOPAY_VAL);
		LSP_ORDER_PAY_STATUS.put(LSP_PAY_STATUS_PAY_SUCCESS, LSP_PAY_STATUS_PAY_SUCCESS_VAL);
		LSP_ORDER_PAY_STATUS.put(LSP_PAY_STATUS_REFUND_WAIT, LSP_PAY_STATUS_REFUND_WAIT_VAL);
		LSP_ORDER_PAY_STATUS.put(LSP_PAY_STATUS_REFUND_SUCCESS,LSP_PAY_STATUS_REFUND_SUCCESS_VAL);
	}

}
