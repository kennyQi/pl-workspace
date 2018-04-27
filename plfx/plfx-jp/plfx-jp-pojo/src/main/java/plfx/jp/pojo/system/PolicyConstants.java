package plfx.jp.pojo.system;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @类功能说明：
 * 价格政策的常量类
 * 
 * 未发布、           已发布 、                      已开始、           已下架、                 已取消
	0		   1			2		 3		    4	
prePublish   public      effect   lose_effect   cancel
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午3:13:43
 * @版本：V1.0
 *
 */
public class PolicyConstants {
	
	public final static String PRE_PUBLISH = "0";
	public final static String PUBLIC = "1";
	public final static String EFFECT = "2";
	public final static String LOSE_EFFECT = "3";
	public final static String CANCEL = "4";
	
	public final static String PRE_PUBLISH_STRING = "未发布";
	public final static String PUBLIC_STRING = "已发布";
	public final static String EFFECT_STRING = "已开始";
	public final static String LOSE_EFFECT_STRING = "已下架";
	public final static String CANCEL_STRING = "已取消";
	
	public final static Map<String,String> STATUS_MAP = new HashMap<String,String>();
	static {
		STATUS_MAP.put(PRE_PUBLISH, PRE_PUBLISH_STRING);
		STATUS_MAP.put(PUBLIC, PUBLIC_STRING);
		STATUS_MAP.put(EFFECT, EFFECT_STRING);
		STATUS_MAP.put(LOSE_EFFECT, LOSE_EFFECT_STRING);
		STATUS_MAP.put(CANCEL, CANCEL_STRING);
	}
	
	
}
