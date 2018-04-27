package lxs.pojo.exception.mp;

import lxs.pojo.BaseException;

@SuppressWarnings("serial")
public class DZPWException extends BaseException{

	public DZPWException(String message){
		super(-1,message);
	}
	
	public final static String QUERY_DZPW_REGION_FAIL="查询电子票务行政区域失败";
	
	public final static String QUERY_DZPW_SCENICSPOT_FAIL="查询电子票景区失败";
	
	public final static String QUERY_DZPW_POLICY_FAIL="查询电子票务政策失败";
	
	public final static String QUERY_DZPW_USE_RECORD_FAIL="查询电子使用记录失败";
	
	public final static String CREATE_DZPW_ORDER_FAIL="创建电子票务订单失败";
	
	public final static String QUERY_DZPW_ORDER_FAIL="查询电子票务订单失败";
	
	public final static String QUERY_DZPW_TICKET_IN_ORDER_FAIL="查询电子票务订单中门票失败";
	
	public final static String PAY_DZPW_ORDER_FAIL="支付电子票务订单失败";
	
	public final static String REFUND_DZPW_ORDER_FAIL="退票电子票务订单失败";
	
	public final static String CLOSE_DZPW_ORDER_FAIL="关闭电子票务订单失败";
}
