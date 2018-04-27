package slfx.jp.pojo.exception;

/**
 * 
 * @类功能说明：机票异常类
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
public class JPException extends BaseException {

	private static String CODE = "01";
	
	public JPException(String code, String message) {
		super(code, message);
	}
	
	public final static String FLIGHT_QUERY_QO_NULL = CODE + "020101";
	
	public final static String FLIGHT_NOT_IN_CACHE = CODE + "020102";
	
	public final static String FLIGHT_POLICY_QUERY_QO_NULL = CODE + "030101";
	

}
