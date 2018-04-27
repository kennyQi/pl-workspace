package plfx.api.client.common.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @类功能说明：接口动作
 * @类修改者：
 * @修改日期：2015-7-2下午3:27:31
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午3:27:31
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PlfxApiAction {
	
	// --------------------- 国际接口 ---------------------

	/**
	 * 查询国际航班
	 */
	public final static String GJ_QueryFlight = "GJ_QueryFlight";
	
	/**
	 * 查询同一国际航班组合更多舱位
	 */
	public final static String GJ_QueryFlightMoreCabins = "GJ_QueryFlightMoreCabins";
	
	/**
	 * 查询国际航班舱位组合政策
	 */
	public final static String GJ_QueryFlightPolicy = "GJ_QueryFlightPolicy";
	
	/**
	 * 创建国际机票订单
	 */
	public final static String GJ_CreateJPOrder = "GJ_CreateJPOrder";
	
	/**
	 * 为国际机票订单付款
	 */
	public final static String GJ_PayToJPOrder = "GJ_PayToJPOrder";
	
	/**
	 * 查询国际机票订单
	 */
	public final static String GJ_QueryJPOrder = "GJ_QueryJPOrder";
	
	/**
	 * 申请取消未付款的订单
	 */
	public final static String GJ_ApplyCancelNoPayOrder = "GJ_ApplyCancelNoPayOrder";
	
	/**
	 * 申请取消已付款未出票的订单
	 */
	public final static String GJ_ApplyCancelOrder = "GJ_ApplyCancelOrder";
	
	/**
	 * 申请退废票(成功出票时可调用)
	 */
	public final static String GJ_ApplyRefundTicket = "GJ_ApplyRefundTicket";
	
	// --------------------- 国内接口 ---------------------
	
	/***
	 * 查询国内航班
	 */
	public final static String GN_QueryFlightList = "GN_QueryFlightList";
	
	/**
	 * 查询国内政策
	 */
	public final static String GN_QueryAirPolicy = "GN_QueryAirPolicy";
	
	/***
	 * 创建国内机票订单
	 */
	public final static String GN_BookJPOrder = "GN_BookJPOrder";
	
	/**
	 * 
	 */
	public final static String GN_PayJPOrder = "GN_PayJPOrder";
	
	/***
	 * 国内机票订单申请取消
	 */
	public final static String GN_CancelTicket = "GN_CancelTicket";
	
	/***
	 * 申请退废票
	 */
	public final static String GN_RefundTicket = "GN_RefundTicket";
	
	/***
	 * 生成订单并自动扣款
	 */
	public final static String GN_AutoOrder = "GN_AutoOrder";
	
	/**
	 * 保存支付信息接口
	 */
	public final static String SAVE_PAYMENT="SavePaymentInfo";
	
	/**
	 * @方法功能说明：接口名称
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-2下午3:20:03
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String value();
	
}
