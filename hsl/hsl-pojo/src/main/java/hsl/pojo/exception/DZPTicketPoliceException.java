package hsl.pojo.exception;

/**
 * 电子票务-门票政策异常处理
 * Created by huanggg on 2016/3/4.
 */
public class DZPTicketPoliceException extends BaseException{

	public DZPTicketPoliceException() {
		super();
	}
	public DZPTicketPoliceException(int code,String message){
		super(code, message);
	}

	/**
	 * 门票政策不存在
	 */
	public static final int TICKETPOLICY_NOT_EXISTS=1001;
	/**
	 * 门票政策对应的景区不存在
	 */
	public static final int TICKETPOLICY_SCENICSPOT_NOT_EXISTS=1002;
	/**
	 * 政策状态错误
	 */
	public static final int TICKETPOLICY_STATUS_ERR=1003;
	/**
	 * 价格日历不存在
	 */
	public static final int SALE_DATE_PRICE_NOT_EXIST=2001;
	/**
	 * 价格日历中的某一天不存在
	 */
	public static final int SALE_DATE_PRICE_ONEDAY_NOT_EXIST=2002;

	/**
	 * 电子票务同步出错
	 */
	public static final int DZPW_SYNC_ERR=3001;
	/**
	 * 电子票务同步返回结果为空
	 */
	public static final int DZPW_SYNC_EMPTY=3002;

	/**
	 * 电子票务联票主图未设定
	 */
	public static final int DZPW_LUNBO_ZHUTU_UNSET=4001;
	/**
	 * 门票政策id为空
	 */
	public static final int DZPW_ORDER_TICKET_POLICY_ID_NULL=5001;
	/**
	 * 订单价格错误
	 */
	public static final int DZPW_ORDER_PRICE_ERROR=5002;
	/**
	 * 游玩日期为空
	 */
	public static final int DZPW_ORDER_DATE_NULL=5003;
	/**
	 * 游玩人为空
	 */
	public static final int DZPW_ORDER_TORISTS_NULL=5004;
	/**
	 * 订单不存在
	 */
	public static final int DZPW_ORDER_NOT_EXIST=5005;
	/**
	 * 订单的门票列表为空
	 */
	public static final int DZPW_ORDER_TICKETS_NOT_EXIST=5006;
	/**
	 * 电子票务远程生成订单失败
	 */
	public static final int DZPW_ORDER_REMOTE_CREATE_FAIL=6001;
	/**
	 * 电子票务本地生成订单失败
	 */
	public static final int DZPW_ORDER_LOCAL_CREATE_FAIL=6002;
	/**
	 * 订单取消失败
	 */
	public static final int DZPW_ORDER_CANCEL_FAIL=6003;
	/**
	 * 订单支付失败
	 */
	public static final int DZPW_ORDER_PAY_FAIL=6004;
	/**
	 * 订单支付状态错误
	 */
	public static final int DZPW_ORDER_PAYSTATUS_ERROR=6005;
	/**
	 * 订单状态错误
	 */
	public static final int DZPW_ORDER_STATUS_ERROR=6006;
}
