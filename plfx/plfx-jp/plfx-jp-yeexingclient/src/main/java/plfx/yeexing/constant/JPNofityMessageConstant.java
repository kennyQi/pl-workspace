package plfx.yeexing.constant;

/**
 * 
 * @类功能说明：国内机票通知类型常量类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年8月3日下午2:18:15
 * @版本：V1.0
 *
 */
public class JPNofityMessageConstant {

	/**
	 * 出票成功
	 */
	public static final String TICKET_SUCCESS = "1";
	
	/**
	 * 支付成功
	 */
	public static final String TICKET_PAY_SUCCESS = "2";
	
	/**
	 * 取消成功
	 */
	public static final String TICKET_CANCEL_SUCCESS = "3";
	
	/**
	 * 退废票成功
	 */
	public static final String TICKET_REFUSE_SUCCESS = "4";
	
	/**
	 * 分销申请退废票接口调用成功通知经销商
	 */
	public static final String TICKET_REFUSE_DEAL = "5";
	
	/**
	 * 拒绝出票
	 */
	public static final String TICKET_REFUSE_FAIL = "6";
}
