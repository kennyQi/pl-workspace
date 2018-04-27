package hg.common.redis;

import hg.common.util.DateUtil;
import hg.common.util.SysProperties;

import java.util.Date;

/**
 * 定义存储在redis缓存里key的名称
 * 
 * @author zhurz
 */
public class JedisKeyConstants {

	/**
	 * 所有缓存KEY的前缀,标识出来自哪个业务系统
	 */
	public final static String KEY_PREFIX = SysProperties.getInstance().get(
			"key_prefix");

	/** 系统配置（数据字典） */
	public final static String SYS_CONFIG_CACHE_KEY = "_SYS_CONFIG_";

	/**
	 * 缓存事件的MAP的KEY的前缀
	 */
	public final static String KEY_EVENT_STORE_PREFIX = KEY_PREFIX
			+ "_EVENT_STORE";

	/**
	 * 返回YYYY-MM-DD的年月日字符串
	 * @param date
	 * @return
	 */
	public static String getPrefixByDate(Date date) {
		return DateUtil.formatDateTime(date, "yyyy-MM-dd");
	}
}
