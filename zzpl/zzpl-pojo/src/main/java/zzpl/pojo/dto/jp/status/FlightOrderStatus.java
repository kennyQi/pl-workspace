package zzpl.pojo.dto.jp.status;

public class FlightOrderStatus {
	
	/**
	 * 等待出票
	 */
	public final static Integer CONFIRM_ORDER_SUCCESS = 100;
	
	public final static String CONFIRM_ORDER_SUCCESS_MSG = "等待出票";
	
	/**
	 * 购票审批未通过
	 */
	public final static Integer CONFIRM_ORDER_FAILED = 101;
	
	public final static String CONFIRM_ORDER_FAILED_MSG = "购票审批未通过";
	
	/**
	 * 等待出票
	 */
	public final static Integer APPROVAL_SUCCESS = 102;
	
	public final static String APPROVAL_SUCCESS_MSG = "等待出票";
	
	/**
	 * 拒绝出票 
	 */
	public final static Integer REFUSE_TICKET_SUCCESS = 103;
	
	public final static String REFUSE_TICKET_SUCCESS_MSG= "拒绝出票";
	
	/**
	 * 出票成功
	 */
	public final static Integer PRINT_TICKET_SUCCESS = 104;
	
	public final static String PRINT_TICKET_SUCCESS_MSG = "出票成功";
	
	/**
	 * 出票失败
	 */
	public final static Integer PRINT_TICKET_FAILED = 105;
	
	public final static String PRINT_TICKET_FAILED_MSG = "出票失败";
	
	/**
	 * 等待退票
	 */
	public final static Integer CONFIRM_CANCEL_ORDER_SUCCESS = 106;
	
	public final static String CONFIRM_CANCEL_ORDER_SUCCESS_MSG = "等待退票";
	
	/**
	 * 退票审批未通过
	 */
	public final static Integer CONFIRM_CANCEL_ORDER_FAILED = 107;
	
	public final static String CONFIRM_CANCEL_ORDER_FAILED_MSG = "退票审批未通过";
	
	/**
	 * 等待退票
	 */
	public final static Integer CANCEL_APPROVAL_SUCCESS = 108;
	
	public final static String CANCEL_APPROVAL_SUCCESS_MSG = "等待退票";
	
	/**
	 * 退票成功
	 */
	public final static Integer CANCEL_TICKET_SUCCESS = 110;
	
	public final static String CANCEL_TICKET_SUCCESS_MSG = "退票成功";
	
	/**
	 * 退票失败
	 */
	public final static Integer CANCEL_TICKET_FAILED = 111;
	
	public final static String CANCEL_TICKET_FAILED_MSG = "退票失败";
	
	/**
	 * 订单作废
	 */
	public final static Integer ORDER_PROCESS_VOID = 112;
	
	public final static String ORDER_PROCESS_VOID_MSG = "订单作废";
	
	/**
	 * 获取政策成功
	 */
	public final static Integer GET_POLICY_SUCCESS = 114;
	
	public final static String GET_POLICY_SUCCESS_MEG = "获取政策成功";
	
	/**
	 * 获取政策成功
	 */
	public final static Integer GET_POLICY_FAIL = 115;
	
	public final static String GET_POLICY_FAIL_MEG = "获取政策失败";
}
