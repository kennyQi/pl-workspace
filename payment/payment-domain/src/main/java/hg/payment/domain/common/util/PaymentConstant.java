package hg.payment.domain.common.util;

/**
 * 
 * 
 *@类功能说明：支付平台静态变量
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月2日上午9:10:23
 *
 */
public class PaymentConstant {

	/************** 待办事项类型 **************/
	/** 支付通知**/
	public static String BACKLOG_TYPE_PAYMENT_PAY = "003";
	/** 退款 通知**/
	public static String BACKLOG_TYPE_PAYMENT_REFUND = "004";
	/** 支付宝退款 通知**/
	public static String BACKLOG_TYPE_PAYMENT_REFUND_ALIPAY = "005";
	
	
	/************** 通知商城类型 **************/
	/** 支付结果通知**/
	public static String NOTIFY_CLIENT_TYPE_PAY = "PAY";
	/** 支付宝退款结果通知**/
	public static String NOTIFY_CLIENT_TYPE_ALIPAY_REFUND = "ALIPAY_REFUND";
	
	
}
