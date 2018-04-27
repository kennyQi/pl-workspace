package lxs.pojo.exception.line;

import lxs.pojo.BaseException;

@SuppressWarnings("serial")
public class LineOrderException extends BaseException{
	public LineOrderException(Integer code, String message){
		super(code,message);
	}
	/**
	 * 旅客不存在
	 */
	public static final Integer RESULT_NO_TRAVELER = 37;
	/**
	 * 用户不存在
	 */
	public static final Integer RESULT_NO_USER = 57;
	/**
	 *旅客数量为空 
	 */
	public static final Integer RESULT_NO_TRAVELER_NUMBER = 38;
	/**
	 * 成人价格为空
	 */
	public static final Integer RESULT_NO_ADULT_PRICE = 39;
	/**
	 * 儿童价格为空
	 */
	public static final Integer RESULT_NO_CHILD_PRICE = 40;
	/**
	 * 定金价格为空
	 */
	public static final Integer RESULT_NO_BARGAIN_MONEY = 41;
	/**
	 * 全款价格为空
	 */
	public static final Integer RESULT_NO_ALL_MONEY = 42;
	/**
	 * 出发日期为空
	 */
	public static final Integer RESULT_NO_TRAVEL_DATE = 43;
	/**
	 * 联系人姓名为空
	 */
	public static final Integer RESULT_NO_LINK_NAME = 44;
	/**
	 * 联系人手机为空
	 */
	public static final Integer RESULT_NO_LINK_MOBILE = 45;
	/**
	 * 联系人邮箱为空
	 */
	public static final Integer RESULT_NO_LINK_EMAIL = 46;
	/**
	 * 线路为空
	 */
	public static final Integer RESULT_NO_LINE = 47;
	/**
	 * 创建订单失败
	 */
	public static final Integer RESULT_CREATE_ORDER_FAILED = 48;
	/**
	 * 订单不存在
	 */
	public static final Integer RESULT_ORDER_NOT_FOUND = 49;
	/**
	 * 超过最后支付时间
	 */
	public static final Integer RESULT_PAY_DEADLINE_PASSED = 50;
	/**
	 * 订单号为空
	 */
	public static final Integer RESULT_NO_ORDERNO = 51;
	/**
	 * 订单ID为空
	 */
	public static final Integer RESULT_NO_ORDERID = 52;
	/**
	 * 更新订单状态失败
	 */
	public static final Integer RESULT_UPDATE_ORDER_STATUS_FAILED = 53;
	/**
	 * 支付状态错误
	 */
	public static final Integer RESULT_PAY_TYPE_WRONG = 54;
	/**
	 * 无支付状态
	 */
	public static final Integer RESULT_NO_PAY_TYPE = 55;
	/**
	 * 经销商未确认，不能支付尾款
	 */
	public static final Integer RESULT_NOT_RESERVE = 56;
}
