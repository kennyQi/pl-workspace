package zzpl.pojo.dto.jp.status;

public class FlightPayStatus {
	
	/**
	 * 等待申请人支付
	 */
	public final static Integer WAIT_USER_TO_PAY = 96;
	
	/**
	 * 等待支付
	 */
	public final static Integer USER_PAY_SUCCESS = 98;
	
	
	/**
	 * 等待支付
	 */
	public final static Integer WAIT_TO_PAY = 100;
	
	/**
	 * 支付成功
	 */
	public final static Integer PAY_SUCCESS = 102;
	
	/**
	 * 支付失败（如果是是申请人支付证明，出现这种状态说明申请人已经支付成功）
	 */
	public final static Integer PAY_FAILED = 103;
	
	/**
	 * 等待退款
	 */
	public final static Integer REFUND = 104;
	
	/**
	 * 退款成功
	 */
	public final static Integer REFUND_SUCCESS = 106;
	
	/**
	 * 退款失败
	 */
	public final static Integer REFUND_FAILED = 107;
	
	/**
	 * 等待退款
	 */
	public final static Integer USER_REFUND = 108;
	
	/**
	 * 退款到申请人成功
	 */
	public final static Integer REFUND_USER_SUCCESS = 110;
	
	/**
	 * 退款到申请人失败
	 */
	public final static Integer REFUND_USER_FAILED = 111;
	
}
