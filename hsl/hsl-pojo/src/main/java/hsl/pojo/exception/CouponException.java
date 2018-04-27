package hsl.pojo.exception;

@SuppressWarnings("serial")
public class CouponException extends BaseException{

	public CouponException(Integer code, String message) {
		super(code, message);
	}
	
	/**
	 * 景点查询无结果
	 */
	public final static Integer RESULT_COUPON_NOCANCEL = 1; 
	/**
	 * 卡券不可用
	 */
	public final static int COUPON_UNAVAILABLE =2; 
	
	/**
	 * 活动状态异常
	 */
	public static final int ACTIVITY_STATUS_ERROR=3;
	/**
	 * 卡券不能赠送
	 */
	public final static int COUPON_NOSEND = 4;
}
