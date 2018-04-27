package lxs.pojo.exception.mp;

import lxs.pojo.BaseException;

@SuppressWarnings("serial")
public class CreateTicketOrderException extends BaseException{

	public CreateTicketOrderException (String message) {
		super(-1,message);
	}
	
	public final static String PARAMETER_ILLEGAL="参数错误";
	
	public final static String GET_PRICE_FAIL="出行日期无产品";
	
	public final static String CREAT_LOCAL_ORDER_FAIL="创建本地订单失败";
}
