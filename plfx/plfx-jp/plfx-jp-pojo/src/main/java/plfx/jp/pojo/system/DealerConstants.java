package plfx.jp.pojo.system;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @类功能说明：机票经销商常量类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年6月26日下午1:48:57
 * @版本：V1.0
 *
 */
public class DealerConstants {
	public final static String PRE_USE = "0";
	public final static String USE = "1";
	
	public final static String PRE_USE_STRING = "未启用";
	public final static String PUBLIC_USE = "启用";
	
	
	public final static Map<String,String> STATUS_MAP = new HashMap<String,String>();
	static {
		STATUS_MAP.put( PRE_USE, PRE_USE_STRING);
		STATUS_MAP.put(USE, PUBLIC_USE);
	}
}
