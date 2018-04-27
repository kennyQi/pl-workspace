package hg.framework.common.util;

import java.util.UUID;

/**
 * UUID随机数生成器
 *
 * @author zhurz
 */
public class UUIDGenerator {

	/**
	 * 获得一个UUID
	 *
	 * @return String UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}