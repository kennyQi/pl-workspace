package hsl.pojo.exception;

@SuppressWarnings("serial")
public class MPException extends BaseException{

	public MPException(Integer code, String message) {
		super(code, message);
	}
	
	/**
	 * 景点查询无结果
	 */
	public final static Integer RESULT_SCENICSPOT_NOTFOUND = 16; 
	
	/**
	 * 订单查询无结果
	 */
	public final static Integer RESULT_ORDER_NOTFOUND = 17;
	
	/**
	 * 价格日历查询无结果
	 */
	public final static Integer RESULT_DATEPRICE_NOTFOUND = 18;
	
	/**
	 * 热门景点已经存在
	 */
	public static final Integer RESULT_HOT_EXIST = 19;
}
