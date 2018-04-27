package hsl.pojo.exception;

/**
 * @类功能说明：酒店异常
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2015年7月2日下午5:51:50
 * @版本：V1.1
 *
 */
@SuppressWarnings("serial")
public class HotelException extends BaseException{
	public HotelException(Integer code, String message){
		super(code,message);
	}
	
	/**
	 * 没有数据
	 */
	public static final Integer QO_ERROR = 1; 
	
	/**
	 * 更新指令异常
	 */
	public static final Integer COMMAND_ERROR = 2;
	
	
	/**
	 * 创建订单失败
	 */
	public static final Integer CREATE_ORDER_FAIL = 3;
	
	
	/**
	 * 更新订单状态失败
	 */
	public static final Integer UPDATE_ORDER_STATUS=4;
}
