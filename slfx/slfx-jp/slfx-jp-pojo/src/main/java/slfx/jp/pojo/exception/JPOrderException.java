package slfx.jp.pojo.exception;

/**
 * 
 * @类功能说明：机票订单异常类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午3:23:02
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPOrderException extends JPException {

	private final static String CODE = "0101";
	
	public JPOrderException(String code, String message) {
		super(code, message);
	}
	
	
	/**
	 * shopCreateOrder 01
	 * command参数为空
	 */
	public final static  String  ORDER_PARAMS_NULL = CODE + "0101";
	
	/**
	 * 下单没有乘机人
	 */
	public final static String ORDER_PARAMS_NO_PASSANGER = CODE + "0102";
	
	/**
	 * 订单已存在
	 */
	public final static String ORDER_IS_EXSITS = CODE + "0103";
	
	/**
	 * 乘机人身份验证异常
	 */
	public final static String ORDER_PASSANGER_CHECK_FAIL = CODE + "0104";
			
	/**
	 * 查询报价失败
	 */
	public final static String ORDER_QUERY_PAT_ERROR = CODE + "0105";
	
	/**
	 * 调用报价接口失败
	 */
	public final static String ORDER_USE_PAT_INTERFACE_ERROR = CODE + "0106";
	
	/**
	 * 航班舱位无效
	 */
	public final static String ORDER_ABE_FLIGHT_NULL = CODE + "0107";
	
	/**
	 * ABE下单异常
	 */
	public final static String ORDER_USE_ABE_FLIGHT_ERROR = CODE + "0108";
	
	/**
	 * 根据报价结果获取真实pnr失败
	 */
	public final static String ORDER_PNR_ERROR = CODE + "0109";
	
	/**
	 *根据真实pnr获取pnrText失败 
	 */
	public final static String ORDER_PNRTEXT_ERROR = CODE + "0110";
	
	/**
	 * 获取比较政策失败
	 */
	public final static String ORDER_FLIGHT_POLICY_ERROR = CODE + "0111";
	
	/**
	 * 易购下单异常
	 */
	public final static String ORDER_USE_YG_FLIGHT_ERROR = CODE + "0112";
	
	/**
	 * 平台下单异常
	 */
	public final static String ORDER_CREATE_ERROR = CODE + "0113";
	
	
	/**
	 * shopAskOrderTicket 03
	 * command为空
	 */
	public final static String ORDER_ASK_TICKET_PARAMS_NULL = CODE + "0301";
	
	/**
	 * 订单为空
	 */
	public final static String ORDER_QUERY_NULL = CODE + "0302";
	
	/**
	 * 分销平台根据分销平台订单号查询易购订单号失败
	 */
	public final static String ORDER_QUERY_YGORDER_BY_ORDERID_ERROR = CODE + "0303";
	
	/**
	 * shopCancelOrder 04
	 * command为空
	 */
	public final static String ORDER_SHOP_CANCEL_PARAMS_NULL = CODE + "0401";
	
	/**
	 * shopRefundOrder 05
	 * 票号为空
	 */
	public final static String ORDER_TICKET_NUMBER_NULL = CODE + "0501";
	
	/**
	 * 删除Pnr异常
	 */
	public final static String ORDER_DELETE_PNR_ERROR = CODE + "0502";
	
	/**
	 * 易购退废票返回结果为空
	 */
	public final static String ORDER_YG_CANCEL_REFUND_NULL = CODE + "0503";
	
	/**
	 * shopQueryOrderList 06
	 * qo为空
	 */
	public final static String ORDER_SHOP_QUERY_QO_NULL = CODE + "0601";
}
