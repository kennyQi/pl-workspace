package hg.payment.pojo.exception;

@SuppressWarnings("serial")
public class PaymentException extends BaseException{

	public PaymentException(Integer code, String message) {
		super(code, message);
	}
	/**
	 * 没有提供支付客户端主键
	 */
	public final static Integer RESULT_PAYMENTCLIENT_WITHOUTID = 1;
	
	
	/**
	 * 支付客户端查询无结果
	 */
	public final static Integer RESULT_PAYMENTCLIENT_NOTFOUND = 2;
	
	/**
	 * 商城请求创建支付订单缺少参数
	 */
	public final static Integer CREATE_PAYOEDER_WITHOUTPARAM = 3;
	
	/**
	 * 支付客户端密钥不正确（商户密钥不正确）
	 */
	public final static Integer RESULT_PAYMENTCLIENT_NOTMATCHKEY = 4;
	
	/**
	 * 支付客户端不可用
	 */
	public final static Integer RESULT_PAYMENTCLIENT_DONOTVALID = 5;
	
	/**
	 * 支付渠道在该客户端不可用
	 */
	public final static Integer RESULT_PAYCHANNEL_NOTVALID = 6;
	
	/**
	 * 生成支付宝请求URL出现异常
	 */
	public final static Integer RESULT_ALIPAYURL_ERROR = 7;
	
	/**
	 * 卖家支付宝帐号信息错误
	 */
	public final static Integer CREATE_PAYORDER_SELLER_ALIACCOUNT_ERROR = 8;
	
	/**
	 * 支付平台订单不存在
	 */
	public final static Integer RESULT_PAYORDER_NOTFOUND = 9;
	
	/**
	 * 订单状态不合理
	 */
	public final static Integer ORDER_STATUS_NOTVALID = 10;
	
	/**
	 * 没有提供订平台单编号
	 */
	public final static Integer RESULT_PAYORDER_WITHOUT_ID = 11;
	
	/**
	 * 商城请求订单时，提供的支付渠道和订单类型不符
	 */
	public final static Integer CREATE_PAYORDER_INVALID_PAY_CHANNEL = 12;
	
	/**
	 * 待办事项执行次数超过规定执行次数
	 */
	public final static Integer BACKLOG_EXECUTE_COUNT_NOTVALID = 13;
	
	/**
	 * 重复通知
	 */
	public final static Integer RESULT_PAYORDER_DUPLICATE_NOTIFY = 14;
	
	/**
	 * 查询订单缺少必须参数
	 */
	public final static Integer QUERY_PAYOEDER_WITHOUTPARAM = 15;
	
	/**
	 * 待办事项不存在
	 */
	public final static Integer RESULT_BACKLOG_NOTFOUND = 16;
	
	/**
	 * 没有可用的支付类型
	 */
	public final static Integer RESULT_PAYTYPE_WITHOUTID = 17;
	
	/**
	 * 请求参数不合法
	 */
	public final static Integer RESULT_PAYMENTCONTEXT_WITHOUTID = 18;
	
	/**
	 * 重复通知
	 */
	public final static Integer RESULT_PAYORDER_DUPLICATE_PAY = 19;
	
	/**
	 * 退款请求缺少必要参数
	 */
	public final static Integer REFUND_PAYOEDER_WITHOUTPARAM = 20;
	
	/**
	 * 退款时订单状态不合理
	 */
	public final static Integer REFUND_PAYOEDER_STATUS_NOT_VALID = 21;
	
	/**
	 * 退款时请求参数不合理
	 */
	public final static Integer REFUND_PAYOEDER_PARAM_NOT_VALID = 22;
	
	/**
	 * 退款记录不存在
	 */
	public final static Integer RESULT_REFUND_NOT_FOUND = 23;
	
	/**
	 * 请求退款时支付宝返回的错误信息
	 */
	public final static Integer REFUND_PAYOEDER_ERROR_OF_ALIPAY = 24;
}
