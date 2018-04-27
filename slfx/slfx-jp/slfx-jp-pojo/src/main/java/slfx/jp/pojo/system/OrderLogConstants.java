package slfx.jp.pojo.system;

import java.util.HashMap;
import java.util.Map;

public class OrderLogConstants {
	//创建订单
	public static String CRATE_LOG_NAME = "10";
	//取消订单
	public static String CANCEL_LOG_NAME = "20";
	//请求出票
	public static String ASK_ORDER_TICKET_LOG_NAME ="30";
	//出票通知
	public static String MESSAGE_LOG_NAME = "40";
	//退票
	public static String RETURN_LOG_NAME ="50";
	//废票
	public static String DESTROY_LOG_NAME = "60";
	
	
	//直销
	public static String ZX_WORKER = "1";
	//分销
	public static String FX_WORKER = "2";
	//定时
	public static String TIMER_WORKER = "3";
	
	
	//成功
	public static String SUCCESS = "100";
	//失败
	public static String FAIL = "200";
	
	
	//创建订单
	public static String CRATE_LOG_INFO = "111";
	//取消订单-直销
	public static String CANCEL_ZX_LOG_INFO = "121";
	//取消订单-分销
	public static String CANCEL_FX_LOG_INFO = "122";
	//取消订单-定时任务
	public static String CANCEL_TIMER_LOG_INFO = "123";
	//请求出票
	public static String ASK_ORDER_TICKET_LOG_INFO = "131";
	//出票通知-成功
	public static String MESSAGE_SUCCESS_LOG_INFO = "141";
	//出票通知-失败
	public static String MESSAGE_FAIL_LOG_INFO = "241";
	//退票-直销
	public static String RETURN_ZX_LOG_INFO = "151";
	//退票-分销
	public static String RETURN_FX_LOG_INFO = "152";
	//销票-直销
	public static String DESTROY_ZX_LOG_INFO = "161";
	//销票-分销
	public static String DESTROY_FX_LOG_INFO = "162";
	
	public final static Map<String,String> NAME_MAP = new HashMap<String,String>();
	public final static Map<String,String> WORKER_MAP = new HashMap<String,String>();
	public final static Map<String,String> INFO_MAP = new HashMap<String,String>();
	static {
		NAME_MAP.put(CRATE_LOG_NAME, "创建订单");
		NAME_MAP.put(CANCEL_LOG_NAME, "取消订单");
		NAME_MAP.put(ASK_ORDER_TICKET_LOG_NAME, "请求出票");
		NAME_MAP.put(MESSAGE_LOG_NAME, "出票通知");
		NAME_MAP.put(RETURN_LOG_NAME, "退票");
		NAME_MAP.put(DESTROY_LOG_NAME, "废票");
		
		WORKER_MAP.put(ZX_WORKER, "直销");
		WORKER_MAP.put(FX_WORKER, "分销");
		WORKER_MAP.put(TIMER_WORKER, "定时任务");
		
		INFO_MAP.put(CRATE_LOG_INFO, "创建机票订单");
		INFO_MAP.put(CANCEL_ZX_LOG_INFO, "直销取消机票订单");
		INFO_MAP.put(CANCEL_FX_LOG_INFO, "分销取消机票订单");
		INFO_MAP.put(CANCEL_TIMER_LOG_INFO, "定时任务取消机票订单");
		INFO_MAP.put(ASK_ORDER_TICKET_LOG_INFO, "请求出票");
		INFO_MAP.put(MESSAGE_SUCCESS_LOG_INFO, "出票成功");
		INFO_MAP.put(MESSAGE_FAIL_LOG_INFO, "出票失败");
		INFO_MAP.put(RETURN_ZX_LOG_INFO, "直销退票");
		INFO_MAP.put(RETURN_FX_LOG_INFO, "分销退票");
		INFO_MAP.put(DESTROY_ZX_LOG_INFO, "直销废票");
		INFO_MAP.put(DESTROY_FX_LOG_INFO, "分销废票");
	}
	
}
