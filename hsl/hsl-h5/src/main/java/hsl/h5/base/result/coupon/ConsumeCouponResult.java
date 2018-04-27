package hsl.h5.base.result.coupon;
import hsl.h5.base.result.api.ApiResult;
public class ConsumeCouponResult extends ApiResult{
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
