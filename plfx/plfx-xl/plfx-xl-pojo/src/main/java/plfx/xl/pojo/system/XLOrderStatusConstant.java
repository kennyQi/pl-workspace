package plfx.xl.pojo.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.KeyValue;

/**
 * 
 * @类功能说明：线路订单状态常量
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月16日上午10:40:58
 * @版本：V1.0
 *
 */
public class XLOrderStatusConstant{

/*------------用户线路订单状态-begin----------------------------------------------*/
	//订单状态
	public static final String USER_CREATE_SUCCESS_WAIT_PAY = "1001";
	public static final String USER_RESERVE_SUCCESS_PAY_BALANCE_MONEY = "1002";
	public static final String USER_RESERVE_SUCCESS_PAY_SUCCESS = "1003";
	public static final String USER_RESERVE_SUCCESS_WAIT_TRAVEL = "1004";
	public static final String USER_RESERVE_SUCCESS_TRAVEL = "1005";
	
	public static final String USER_RESERVE_SUCCESS_TRAVEL_OVER = "1006";
	
	public static final String USER_CREATE_SUCCESS_WAIT_PAY_VAL = "下单成功待支付";
	public static final String USER_RESERVE_SUCCESS_PAY_BALANCE_MONEY_VAL = "预订成功待付尾款";
	public static final String USER_RESERVE_SUCCESS_PAY_SUCCESS_VAL = "预订成功已付款";
	public static final String USER_RESERVE_SUCCESS_WAIT_TRAVEL_VAL = "预订成功等待出游";
	public static final String USER_RESERVE_SUCCESS_TRAVEL_VAL = "出游中";
	
	public static final String USER_RESERVE_SUCCESS_TRAVEL_OVER_VAL = "出游完毕";
	
	//用户订单状态list
	public final static List<KeyValue> USER_XLORDER_STATUS_LIST = new ArrayList<KeyValue>();
	static {
		USER_XLORDER_STATUS_LIST.add(new Attr(USER_CREATE_SUCCESS_WAIT_PAY,USER_CREATE_SUCCESS_WAIT_PAY_VAL));
		USER_XLORDER_STATUS_LIST.add(new Attr(USER_RESERVE_SUCCESS_PAY_BALANCE_MONEY,USER_RESERVE_SUCCESS_PAY_BALANCE_MONEY_VAL));
		USER_XLORDER_STATUS_LIST.add(new Attr(USER_RESERVE_SUCCESS_PAY_SUCCESS,USER_RESERVE_SUCCESS_PAY_SUCCESS_VAL));
		USER_XLORDER_STATUS_LIST.add(new Attr(USER_RESERVE_SUCCESS_WAIT_TRAVEL,USER_RESERVE_SUCCESS_WAIT_TRAVEL_VAL));
		USER_XLORDER_STATUS_LIST.add(new Attr(USER_RESERVE_SUCCESS_TRAVEL,USER_RESERVE_SUCCESS_TRAVEL_VAL));
		
		USER_XLORDER_STATUS_LIST.add(new Attr(USER_RESERVE_SUCCESS_TRAVEL_OVER,USER_RESERVE_SUCCESS_TRAVEL_OVER_VAL));
	}
	
	// 用户订单状态map
	public final static HashMap<String, String> USER_XLORDER_STATUS_MAP = new HashMap<String, String>();
	static {
		USER_XLORDER_STATUS_MAP.put(USER_CREATE_SUCCESS_WAIT_PAY,USER_CREATE_SUCCESS_WAIT_PAY_VAL);
		USER_XLORDER_STATUS_MAP.put(USER_RESERVE_SUCCESS_PAY_BALANCE_MONEY,USER_RESERVE_SUCCESS_PAY_BALANCE_MONEY_VAL);
		USER_XLORDER_STATUS_MAP.put(USER_RESERVE_SUCCESS_PAY_SUCCESS,USER_RESERVE_SUCCESS_PAY_SUCCESS_VAL);
		USER_XLORDER_STATUS_MAP.put(USER_RESERVE_SUCCESS_WAIT_TRAVEL,USER_RESERVE_SUCCESS_WAIT_TRAVEL_VAL);
		USER_XLORDER_STATUS_MAP.put(USER_RESERVE_SUCCESS_TRAVEL,USER_RESERVE_SUCCESS_TRAVEL_VAL);
		
		USER_XLORDER_STATUS_MAP.put(USER_RESERVE_SUCCESS_TRAVEL_OVER,USER_RESERVE_SUCCESS_TRAVEL_OVER_VAL);
	}
/*------------用户线路订单状态-end------------------------------------------------*/
	
/*------------商城线路订单状态-begin----------------------------------------------*/
	//订单状态
	public static final String SHOP_CREATE_SUCCESS_NOT_RESERVE = "2001";
	public static final String SHOP_CREATE_SUCCESS_LOCK_SEAT = "2002";
	public static final String SHOP_RESERVE_SUCCESS = "2003";
	public static final String SHOP_ORDER_CANCEL = "2004";
	public static final String SHOP_ORDER_SUCCESS = "2005";
	
	public static final String SHOP_CREATE_SUCCESS_NOT_RESERVE_VAL = "下单成功未订位";
	public static final String SHOP_CREATE_SUCCESS_LOCK_SEAT_VAL = "下单成功已锁定位置";
	public static final String SHOP_RESERVE_SUCCESS_VAL = "预订成功";
	public static final String SHOP_ORDER_CANCEL_VAL = "订单取消";
	public static final String SHOP_ORDER_SUCCESS_VAL="下单成功";
	
	//订单支付状态
	public static final String SHOP_WAIT_PAY_BARGAIN_MONEY = "2101";
	public static final String SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX = "2102";
	public static final String SHOP_WAIT_PAY_BALANCE_MONEY = "2103";
	public static final String SHOP_RECEIVE_BALANCE_MONEY_WAIT_PAY_FX = "2104";
	public static final String SHOP_PAY_SUCCESS = "2105";
	public static final String SHOP_WAIT_REFUND = "2106";
	
	public static final String SHOP_WAIT_PAY_BARGAIN_MONEY_VAL = "待支付订金";
	public static final String SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX_VAL = "已收到订金";
	public static final String SHOP_WAIT_PAY_BALANCE_MONEY_VAL = "等待支付尾款";
	public static final String SHOP_RECEIVE_BALANCE_MONEY_WAIT_PAY_FX_VAL = "已收到全款代付分销";
	public static final String SHOP_PAY_SUCCESS_VAL = "全款支付成功";
	public static final String SHOP_WAIT_REFUND_VAL = "等待退款";
	
	//商城订单状态list
	public final static List<KeyValue> SHOP_XLORDER_STATUS_LIST = new ArrayList<KeyValue>();
	static {
		SHOP_XLORDER_STATUS_LIST.add(new Attr(SHOP_CREATE_SUCCESS_NOT_RESERVE,SHOP_CREATE_SUCCESS_NOT_RESERVE_VAL));
		SHOP_XLORDER_STATUS_LIST.add(new Attr(SHOP_CREATE_SUCCESS_LOCK_SEAT,SHOP_CREATE_SUCCESS_LOCK_SEAT_VAL));
		SHOP_XLORDER_STATUS_LIST.add(new Attr(SHOP_RESERVE_SUCCESS,SHOP_RESERVE_SUCCESS_VAL));
		SHOP_XLORDER_STATUS_LIST.add(new Attr(SHOP_ORDER_CANCEL,SHOP_ORDER_CANCEL_VAL));
		SHOP_XLORDER_STATUS_LIST.add(new Attr(SHOP_ORDER_SUCCESS,SHOP_ORDER_SUCCESS_VAL));
	}
	
	// 商城支付状态list
	public final static List<KeyValue> SHOP_XLORDER_PAY_STATUS_LIST = new ArrayList<KeyValue>();
	static {
		SHOP_XLORDER_PAY_STATUS_LIST.add(new Attr(SHOP_WAIT_PAY_BARGAIN_MONEY,SHOP_WAIT_PAY_BARGAIN_MONEY_VAL));
		SHOP_XLORDER_PAY_STATUS_LIST.add(new Attr(SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX,SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX_VAL));
		SHOP_XLORDER_PAY_STATUS_LIST.add(new Attr(SHOP_WAIT_PAY_BALANCE_MONEY,SHOP_WAIT_PAY_BALANCE_MONEY_VAL));
		SHOP_XLORDER_PAY_STATUS_LIST.add(new Attr(SHOP_RECEIVE_BALANCE_MONEY_WAIT_PAY_FX,SHOP_RECEIVE_BALANCE_MONEY_WAIT_PAY_FX_VAL));
		SHOP_XLORDER_PAY_STATUS_LIST.add(new Attr(SHOP_PAY_SUCCESS,SHOP_PAY_SUCCESS_VAL));
		SHOP_XLORDER_PAY_STATUS_LIST.add(new Attr(SHOP_WAIT_REFUND,SHOP_WAIT_REFUND_VAL));
	}
	
	// 商城订单状态map
	public final static HashMap<String, String> SHOP_XLORDER_STATUS_MAP = new HashMap<String, String>();
	static {
		SHOP_XLORDER_STATUS_MAP.put(SHOP_CREATE_SUCCESS_NOT_RESERVE,SHOP_CREATE_SUCCESS_NOT_RESERVE_VAL);
		SHOP_XLORDER_STATUS_MAP.put(SHOP_CREATE_SUCCESS_LOCK_SEAT,SHOP_CREATE_SUCCESS_LOCK_SEAT_VAL);
		SHOP_XLORDER_STATUS_MAP.put(SHOP_RESERVE_SUCCESS,SHOP_RESERVE_SUCCESS_VAL);
		SHOP_XLORDER_STATUS_MAP.put(SHOP_ORDER_CANCEL,SHOP_ORDER_CANCEL_VAL);
		SHOP_XLORDER_STATUS_MAP.put(SHOP_ORDER_SUCCESS,SHOP_ORDER_SUCCESS_VAL);
	}
	
	// 商城支付状态map
	public final static HashMap<String, String> SHOP_XLORDER_PAY_STATUS_MAP = new HashMap<String, String>();
	static {
		SHOP_XLORDER_PAY_STATUS_MAP.put(SHOP_WAIT_PAY_BARGAIN_MONEY,SHOP_WAIT_PAY_BARGAIN_MONEY_VAL);
		SHOP_XLORDER_PAY_STATUS_MAP.put(SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX,SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX_VAL);
		SHOP_XLORDER_PAY_STATUS_MAP.put(SHOP_WAIT_PAY_BALANCE_MONEY,SHOP_WAIT_PAY_BALANCE_MONEY_VAL);
		SHOP_XLORDER_PAY_STATUS_MAP.put(SHOP_RECEIVE_BALANCE_MONEY_WAIT_PAY_FX,SHOP_RECEIVE_BALANCE_MONEY_WAIT_PAY_FX_VAL);
		SHOP_XLORDER_PAY_STATUS_MAP.put(SHOP_PAY_SUCCESS,SHOP_PAY_SUCCESS_VAL);
		SHOP_XLORDER_PAY_STATUS_MAP.put(SHOP_WAIT_REFUND,SHOP_WAIT_REFUND_VAL);
	}
/*------------商城线路订单状态-end------------------------------------------------*/
	
/*------------分销线路订单状态-begin----------------------------------------------*/
	//订单状态
	public static final String SLFX_CREATE_SUCCESS_NOT_RESERVE = "3001";
	public static final String SLFX_CREATE_SUCCESS_LOCK_SEAT = "3002";
	public static final String SLFX_RESERVE_SUCCESS = "3003";
	public static final String SLFX_ORDER_CANCEL = "3004";
	public static final String SLFX_ORDER_SUCCESS = "3005"; //
	
	public static final String SLFX_CREATE_SUCCESS_NOT_RESERVE_VAL = "下单成功未订位";
	public static final String SLFX_CREATE_SUCCESS_LOCK_SEAT_VAL = "下单成功已锁定位置";
	public static final String SLFX_RESERVE_SUCCESS_VAL = "预订成功";
	public static final String SLFX_ORDER_CANCEL_VAL = "订单取消";
	public static final String SLFX_ORDER_SUCCESS_VAL = "下单成功"; //
	
	//订单支付状态
	public static final String SLFX_WAIT_PAY_BARGAIN_MONEY = "3101";
	// 为了和直销状态方便转化作+1000 操作
	public static final String SLFX_PAY_BARGAIN_MONEY = "3102";
	public static final String SLFX_WAIT_PAY_BALANCE_MONEY = "3103";
	public static final String SLFX_PAY_SUCCESS = "3105";
	public static final String SLFX_WAIT_REFUND = "3106";

	public static final String SLFX_WAIT_PAY_DIFFERENCE_MONEY = "3107"; //
	public static final String SLFX_REFUND_SUCCESS = "3108";
	
	
	public static final String SLFX_WAIT_PAY_BARGAIN_MONEY_VAL = "待支付订金";
	public static final String SLFX_PAY_BARGAIN_MONEY_VAL = "已支付订金";
	public static final String SLFX_WAIT_PAY_BALANCE_MONEY_VAL = "等待支付尾款";
	public static final String SLFX_PAY_SUCCESS_VAL = "已支付全款";
	public static final String SLFX_WAIT_REFUND_VAL = "等待退款";
	
	public static final String SLFX_WAIT_PAY_DIFFERENCE_MONEY_VAL = "等待支付差价";//
	public static final String SLFX_REFUND_SUCCESS_VAL = "退款成功";
	
	
	//分销订单状态list
	public final static List<KeyValue> SLFX_XLORDER_STATUS_LIST = new ArrayList<KeyValue>();
	static {
		SLFX_XLORDER_STATUS_LIST.add(new Attr(SLFX_CREATE_SUCCESS_NOT_RESERVE, SLFX_CREATE_SUCCESS_NOT_RESERVE_VAL));
		SLFX_XLORDER_STATUS_LIST.add(new Attr(SLFX_CREATE_SUCCESS_LOCK_SEAT, SLFX_CREATE_SUCCESS_LOCK_SEAT_VAL));
		SLFX_XLORDER_STATUS_LIST.add(new Attr(SLFX_RESERVE_SUCCESS, SLFX_RESERVE_SUCCESS_VAL));
		SLFX_XLORDER_STATUS_LIST.add(new Attr(SLFX_ORDER_CANCEL, SLFX_ORDER_CANCEL_VAL));
		
		SLFX_XLORDER_STATUS_LIST.add(new Attr(SLFX_ORDER_SUCCESS, SLFX_ORDER_SUCCESS_VAL));
	}
	
	// 分销支付状态list
	public final static List<KeyValue> SLFX_XLORDER_PAY_STATUS_LIST = new ArrayList<KeyValue>();
	static {
		SLFX_XLORDER_PAY_STATUS_LIST.add(new Attr(SLFX_WAIT_PAY_BARGAIN_MONEY,SLFX_WAIT_PAY_BARGAIN_MONEY_VAL));
		SLFX_XLORDER_PAY_STATUS_LIST.add(new Attr(SLFX_PAY_BARGAIN_MONEY,SLFX_PAY_BARGAIN_MONEY_VAL));
		SLFX_XLORDER_PAY_STATUS_LIST.add(new Attr(SLFX_WAIT_PAY_BALANCE_MONEY,SLFX_WAIT_PAY_BALANCE_MONEY_VAL));
		SLFX_XLORDER_PAY_STATUS_LIST.add(new Attr(SLFX_PAY_SUCCESS,SLFX_PAY_SUCCESS_VAL));
		SLFX_XLORDER_PAY_STATUS_LIST.add(new Attr(SLFX_WAIT_REFUND,SLFX_WAIT_REFUND_VAL));
		
		SLFX_XLORDER_PAY_STATUS_LIST.add(new Attr(SLFX_REFUND_SUCCESS,SLFX_REFUND_SUCCESS_VAL));
		
		SLFX_XLORDER_PAY_STATUS_LIST.add(new Attr(SLFX_WAIT_PAY_DIFFERENCE_MONEY,SLFX_WAIT_PAY_DIFFERENCE_MONEY_VAL));
	}
	
	// 分销订单状态map
	public final static HashMap<String, String> SLFX_XLORDER_STATUS_MAP = new HashMap<String, String>();
	static {
		SLFX_XLORDER_STATUS_MAP.put(SLFX_CREATE_SUCCESS_NOT_RESERVE, SLFX_CREATE_SUCCESS_NOT_RESERVE_VAL);
		SLFX_XLORDER_STATUS_MAP.put(SLFX_CREATE_SUCCESS_LOCK_SEAT, SLFX_CREATE_SUCCESS_LOCK_SEAT_VAL);
		SLFX_XLORDER_STATUS_MAP.put(SLFX_RESERVE_SUCCESS, SLFX_RESERVE_SUCCESS_VAL);
		SLFX_XLORDER_STATUS_MAP.put(SLFX_ORDER_CANCEL, SLFX_ORDER_CANCEL_VAL);
	}
	
	// 分销支付状态map
	public final static HashMap<String, String> SLFX_XLORDER_PAY_STATUS_MAP = new HashMap<String, String>();
	static {
		SLFX_XLORDER_PAY_STATUS_MAP.put(SLFX_WAIT_PAY_BARGAIN_MONEY,SLFX_WAIT_PAY_BARGAIN_MONEY_VAL);
		SLFX_XLORDER_PAY_STATUS_MAP.put(SLFX_PAY_BARGAIN_MONEY,SLFX_PAY_BARGAIN_MONEY_VAL);
		SLFX_XLORDER_PAY_STATUS_MAP.put(SLFX_WAIT_PAY_BALANCE_MONEY,SLFX_WAIT_PAY_BALANCE_MONEY_VAL);
		SLFX_XLORDER_PAY_STATUS_MAP.put(SLFX_PAY_SUCCESS,SLFX_PAY_SUCCESS_VAL);
		SLFX_XLORDER_PAY_STATUS_MAP.put(SLFX_WAIT_REFUND,SLFX_WAIT_REFUND_VAL);
		
		SLFX_XLORDER_PAY_STATUS_MAP.put(SLFX_REFUND_SUCCESS,SLFX_REFUND_SUCCESS_VAL);
	}
/*------------分销线路订单状态-end------------------------------------------------*/

}