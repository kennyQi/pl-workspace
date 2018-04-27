package plfx.jd.pojo.system;

import java.util.Map;
import java.util.TreeMap;


/**
 * 
 * @类功能说明：酒店订单常量
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2015年1月27日下午4:07:49
 * @版本：V1.0
 *
 */
public class HotelOrderConstants {
	/**
	 * 订单状态
	 */
	public static final Map<String,String>	ORDER_STATUS_MAP = new TreeMap<String,String>();
	public final static String ORDER_CANCEL = "1";
	public final static String ORDER_SUCCESS = "0";
	
	static{
		ORDER_STATUS_MAP.put(ORDER_CANCEL, "订单已取消");
		ORDER_STATUS_MAP.put(ORDER_SUCCESS, "订单预定成功");
	}
	
	/**
	 * 性别
	 */
	public static final Map<Integer,String> GENDER_MAP = new TreeMap<Integer, String>();
	
	private final static Integer FEMALE = 0;
	private final static Integer MAILE = 1;
	private final static Integer UNKNOW = 2;
	static {
		GENDER_MAP.put(FEMALE,"男");
		GENDER_MAP.put(MAILE,"女");
		GENDER_MAP.put(UNKNOW,"未知");
	}
}

