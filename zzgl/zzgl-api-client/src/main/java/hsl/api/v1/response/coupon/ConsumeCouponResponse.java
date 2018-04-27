package hsl.api.v1.response.coupon;

import hsl.api.base.ApiResponse;

public class ConsumeCouponResponse extends ApiResponse{
	/**
	 * 景点查询无结果
	 */
	public final static String RESULT_COUPON_NOCANCEL = "-1"; 
	
	/**
	 * 卡券不可用
	 */
	public final static String COUPON_UNAVAILABLE = "-2";
	
	/**
	 * 5活动状态异常
	 */
	public final static String ACTIVITY_STATUS_ERROR = "-3";
}
