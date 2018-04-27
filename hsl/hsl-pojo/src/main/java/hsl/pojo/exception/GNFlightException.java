package hsl.pojo.exception;
@SuppressWarnings("serial")
public class GNFlightException extends BaseException {

	public GNFlightException(Integer code, String message) {
		super(code, message);
	}

	public final static Integer PLFX_ERROR_CODE = 100;
	
	public final static String PLFX_ERROR_MESSAGE = "分销获取为空错误";
	
	public final static Integer QUERY_GN_CABIN_FAILED_CODE = 101;
	
	public final static String QUERY_GN_CABIN_FAILED_MESSAGE = "查询国内舱位失败";

	public final static Integer QUERY_FLIGHT_ORDER_LIST_FAILED_CODE = 102;
	
	public final static String QUERY_FLIGHT_ORDER_LIST_FAILED_MESSAGE = "查询订单列表失败";
	
	public final static Integer QUERY_FLIGHT_ORDER_INFO_FAILED_CODE = 103;

	public final static String QUERY_FLIGHT_ORDER_INFO_FAILED_MESSAGE = "国内订单详情失败";

}
