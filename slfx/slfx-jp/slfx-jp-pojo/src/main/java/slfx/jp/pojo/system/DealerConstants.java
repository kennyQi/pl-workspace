package slfx.jp.pojo.system;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年10月16日上午8:40:22
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
