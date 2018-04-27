package hsl.pojo.exception;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class CouponActivityException extends BaseException{
	public CouponActivityException(Integer code, String message) {
		super(code, message);
	}
	/**
	 * 1不能在上线活修改活动
	 */
	public static final int CANNOT_CHANGE_AFTER_ONLINE=1;
	/**
	 * 2需要审核通过
	 */
	public static final int CHECK_OK_NEEDED=2;
	/**
	 * 3活动尚未开始
	 */
	public static final int ACTIVITY_HAVE_NOT_ACTIVE_YET=3;
	/**
	 * 4活动已经结束
	 */
	public static final int ACTIVITY_ALREADY_OVER=4;
	/**
	 * 5活动状态异常
	 */
	public static final int ACTIVITY_STATUS_ERROR=5;
	/**
	 * 99活动id为空
	 */
	public static final int ACTIVITY_ID_NULL=99;
	
}
