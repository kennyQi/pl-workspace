package pay.record.pojo.system;

import hg.system.model.dto.Attr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.KeyValue;

/**
 * 
 * @类功能说明：基础支付记录常量类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月8日下午3:42:35
 * @版本：V1.0
 *
 */
public class BasePayRecordConstants {
	public final static String PAY_PLATFORM_ZFB = "0";
	
	public final static String PAY_PLATFORM_ZFB_STRING = "支付宝";
	
	/**
	 * 支付平台map
	 */
	public final static Map<String,String> PAY_PLATFORM_MAP = new HashMap<String,String>();
	static {
		PAY_PLATFORM_MAP.put( PAY_PLATFORM_ZFB, PAY_PLATFORM_ZFB_STRING);
	}
	
	public final static String FROM_PROJECT_PLZX = "0";
	public final static String FROM_PROJECT_SLFX = "1";
	public final static String FROM_PROJECT_ZZPL = "2";
	public final static String FROM_PROJECT_PLFX = "3";
	public final static String FROM_PROJECT_ZX = "4";
	public final static String FROM_PROJECT_LXS = "5";
	
	public final static String FROM_PROJECT_PLZX_STRING = "票量直销";
	public final static String FROM_PROJECT_SLFX_STRING = "商旅分销";
	public final static String FROM_PROJECT_ZZPL_STRING = "中智票量";
	public final static String FROM_PROJECT_PLFX_STRING = "票量分销";
	public final static String FROM_PROJECT_ZX_STRING = "智行";
	public final static String FROM_PROJECT_LXS_STRING = "旅行社";
	
	/**
	 * 来源项目标识map
	 */
	public final static Map<String,String> FROM_PROJECT_CODE_MAP = new HashMap<String,String>();
	static {
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_PLZX, FROM_PROJECT_PLZX_STRING);
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_SLFX, FROM_PROJECT_SLFX_STRING);
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_ZZPL, FROM_PROJECT_ZZPL_STRING);
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_PLFX, FROM_PROJECT_PLFX_STRING);
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_ZX, FROM_PROJECT_ZX_STRING);
		FROM_PROJECT_CODE_MAP.put( FROM_PROJECT_LXS, FROM_PROJECT_LXS_STRING);
	}
	
	public final static String RECORD_TYEP_UTJ = "1";
	public final static String RECORD_TYEP_FTG = "2";
	public final static String RECORD_TYEP_GTF = "3";
	public final static String RECORD_TYEP_JTU = "4";
	public final static String RECORD_TYEP_JTF = "5";
	public final static String RECORD_TYEP_FTJ = "6";
	
	public final static String RECORD_TYEP_UTJ_STRING = "用户->直销";
	public final static String RECORD_TYEP_FTG_STRING = "分销->供应商";
	public final static String RECORD_TYEP_GTF_STRING = "供应商->分销";
	public final static String RECORD_TYEP_JTU_STRING = "直销->用户";
	public final static String RECORD_TYEP_JTF_STRING = "直销->分销";
	public final static String RECORD_TYEP_FTJ_STRING= "分销->直销";
	
	/**
	 * 记录类型map
	 */
	public final static Map<String,String> RECORD_TYEP_MAP = new HashMap<String,String>();
	static {
		RECORD_TYEP_MAP.put( RECORD_TYEP_UTJ, RECORD_TYEP_UTJ_STRING);
		RECORD_TYEP_MAP.put( RECORD_TYEP_FTG, RECORD_TYEP_FTG_STRING);
		RECORD_TYEP_MAP.put( RECORD_TYEP_GTF, RECORD_TYEP_GTF_STRING);
		RECORD_TYEP_MAP.put( RECORD_TYEP_JTU, RECORD_TYEP_JTU_STRING);
		RECORD_TYEP_MAP.put( RECORD_TYEP_JTF, RECORD_TYEP_JTF_STRING);
		RECORD_TYEP_MAP.put( RECORD_TYEP_FTJ, RECORD_TYEP_FTJ_STRING);
	}
	
	public final static String FROM_CLIENT_TYPE_MOBILE = "0";
	public final static String FROM_CLIENT_TYPE_PC = "1";
	
	public final static String FROM_CLIENT_TYPE_MOBILE_STRING = "移动端";
	public final static String FROM_CLIENT_TYPE_PC_STRING = "PC端";
	
	/**
	 * 来源客户端类型map
	 */
	public final static Map<String,String> FROM_CLIENT_TYPE = new HashMap<String,String>();
	static {
		FROM_CLIENT_TYPE.put( FROM_CLIENT_TYPE_MOBILE, FROM_CLIENT_TYPE_MOBILE_STRING);
		FROM_CLIENT_TYPE.put( FROM_CLIENT_TYPE_PC, FROM_CLIENT_TYPE_PC_STRING);
	}
	
	/****
	 * 业务类型
	 */
	public final static String ON_LINE_PAY = "1";
	public final static String TRADE_REFUND = "2";
	public final static String TRANSFER_MONEY = "3";
	
	public final static String ON_LINE_PAY_STRING = "在线支付";
	public final static String TRADE_REFUND_STRING = "交易退款";
	public final static String TRANSFER_MONEY_STRING = "转账";
	
	public final static Map<String,String> BUSSINSE_TYPE = new HashMap<String,String>();
	static {
		BUSSINSE_TYPE.put( ON_LINE_PAY, ON_LINE_PAY_STRING);
		BUSSINSE_TYPE.put( TRADE_REFUND, TRADE_REFUND_STRING);
		BUSSINSE_TYPE.put( TRANSFER_MONEY, TRANSFER_MONEY_STRING);
	}
    //线路订单状态
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
    
		//-------------机票订单状态---------------------------------
		
		public static final String PLFX_PAY_WAIT = "28";
		public static final String PLFX_TICKET_CANCEL = "29";
		public static final String PLFX_TICKET_DEALING = "30";
//		public static final String PLFX_TICKET_TRY_AGAIN = "31";
		public static final String PLFX_TICKET_FAIL = "32";
		public static final String PLFX_TICKET_SUCC = "33";
		public static final String PLFX_TICKET_REFUND_DEALING = "34";
		public static final String PLFX_TICKET_REFUND_FAIL = "35";
		public static final String PLFX_TICKET_REFUND_SUCC = "36";
		
		
		
		public static final String PLFX_TICKET_NO_PAY = "37";
		public static final String PLFX_TICKET_PAY_SUCC = "38";
		public static final String PLFX_TICKET_RECEIVE_PAYMENT_SUCC = "39";
		public static final String PLFX_TICKET_TO_BE_BACK_WAIT = "40";
		public static final String PLFX_TICKET_TO_BE_BACK_SUCC = "41";
		public static final String PLFX_TICKET_REBACK_WAIT = "42";
		public static final String PLFX_TICKET_REBACK_SUCC = "43";
		
		
		public static final String PLFX_PAY_WAIT_VAL = "待确认";
		public static final String PLFX_TICKET_CANCEL_VAL = "已取消";
		public static final String PLFX_TICKET_DEALING_VAL = "出票处理中";
//		public static final String PLFX_TICKET_TRY_AGAIN_VAL = "出票待重试";
		public static final String PLFX_TICKET_FAIL_VAL = "出票失败";
		public static final String PLFX_TICKET_SUCC_VAL = "已出票";
		public static final String PLFX_TICKET_REFUND_DEALING_VAL = "退/废处理中";
		public static final String PLFX_TICKET_REFUND_FAIL_VAL = "退/废失败";
		public static final String PLFX_TICKET_REFUND_SUCC_VAL = "退/废成功";
		
		public static final String PLFX_TICKET_NO_PAY_VAL = "待支付 ";
		public static final String PLFX_TICKET_PAY_SUCC_VAL = "已支付";
		public static final String PLFX_TICKET_RECEIVE_PAYMENT_SUCC_VAL = "已收款";
		public static final String PLFX_TICKET_TO_BE_BACK_WAIT_VAL = "待回款";
		public static final String PLFX_TICKET_TO_BE_BACK_SUCC_VAL = "已回款";
		public static final String PLFX_TICKET_REBACK_WAIT_VAL = "待退款";
		public static final String PLFX_TICKET_REBACK_SUCC_VAL = "已退款";
		
		public final static List<KeyValue> JPORDER_STATUS_LIST = new ArrayList<KeyValue>();
		static {
		JPORDER_STATUS_LIST.add(new Attr(PLFX_PAY_WAIT, PLFX_PAY_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_CANCEL, PLFX_TICKET_CANCEL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_DEALING, PLFX_TICKET_DEALING_VAL));
//		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_TRY_AGAIN, PLFX_TICKET_TRY_AGAIN_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_FAIL, PLFX_TICKET_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_SUCC, PLFX_TICKET_SUCC_VAL));
		
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_DEALING, PLFX_TICKET_REFUND_DEALING_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_FAIL, PLFX_TICKET_REFUND_FAIL_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_SUCC, PLFX_TICKET_REFUND_SUCC_VAL));
		
		
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_NO_PAY, PLFX_TICKET_NO_PAY_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_PAY_SUCC, PLFX_TICKET_PAY_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_RECEIVE_PAYMENT_SUCC, PLFX_TICKET_RECEIVE_PAYMENT_SUCC_VAL));
		
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_TO_BE_BACK_WAIT, PLFX_TICKET_TO_BE_BACK_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_TO_BE_BACK_SUCC, PLFX_TICKET_TO_BE_BACK_SUCC_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REBACK_WAIT, PLFX_TICKET_REBACK_WAIT_VAL));
		JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REBACK_SUCC, PLFX_TICKET_REBACK_SUCC_VAL));
		
		}
		//分销订单状态
		public final static List<KeyValue> PLFX_JPORDER_STATUS_LIST = new ArrayList<KeyValue>();
		static {
			PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_PAY_WAIT, PLFX_PAY_WAIT_VAL));
			PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_CANCEL, PLFX_TICKET_CANCEL_VAL));
			PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_DEALING, PLFX_TICKET_DEALING_VAL));
//			JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_TRY_AGAIN, PLFX_TICKET_TRY_AGAIN_VAL));
			PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_FAIL, PLFX_TICKET_FAIL_VAL));
			PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_SUCC, PLFX_TICKET_SUCC_VAL));
			
			PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_DEALING, PLFX_TICKET_REFUND_DEALING_VAL));
			PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_FAIL, PLFX_TICKET_REFUND_FAIL_VAL));
			PLFX_JPORDER_STATUS_LIST.add(new Attr(PLFX_TICKET_REFUND_SUCC, PLFX_TICKET_REFUND_SUCC_VAL));
			
		}
		//分销支付状态
		public final static List<KeyValue> PLFX_JPORDER_PAY_STATUS_LIST = new ArrayList<KeyValue>();
		static {
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_NO_PAY, PLFX_TICKET_NO_PAY_VAL));
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_PAY_SUCC, PLFX_TICKET_PAY_SUCC_VAL));
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_RECEIVE_PAYMENT_SUCC, PLFX_TICKET_RECEIVE_PAYMENT_SUCC_VAL));
			
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_TO_BE_BACK_WAIT, PLFX_TICKET_TO_BE_BACK_WAIT_VAL));
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_TO_BE_BACK_SUCC, PLFX_TICKET_TO_BE_BACK_SUCC_VAL));
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_REBACK_WAIT, PLFX_TICKET_REBACK_WAIT_VAL));
			PLFX_JPORDER_PAY_STATUS_LIST.add(new Attr(PLFX_TICKET_REBACK_SUCC, PLFX_TICKET_REBACK_SUCC_VAL));
		}
		
}
