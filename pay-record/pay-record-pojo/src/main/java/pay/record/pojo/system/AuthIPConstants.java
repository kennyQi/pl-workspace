package pay.record.pojo.system;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @类功能说明：授权IP常量类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月27日下午4:44:37
 * @版本：V1.0
 *
 */
public class AuthIPConstants {
	public final static String AUTH_FAIL = "0";
	public final static String AUTH_SUCC = "1";
	
	public final static String AUTH_FAIL_STRING = "未授权";
	public final static String AUTH_SUCC_STRING = "已授权";
	
	
	public final static Map<String,String> STATUS_MAP = new HashMap<String,String>();
	static {
		STATUS_MAP.put( AUTH_FAIL, AUTH_FAIL_STRING);
		STATUS_MAP.put( AUTH_SUCC, AUTH_SUCC_STRING);
	}
}
