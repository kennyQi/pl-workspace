package plfx.jp.app.common.util;

import hg.common.component.RemoteConfigurer;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @类功能说明：远程配置工具
 * @类修改者：
 * @修改日期：2015-7-16下午3:46:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-16下午3:46:22
 */
public class RemoteConfigUtils {

	private static Properties properties = null;

	/**
	 * @方法功能说明：初始化
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午3:46:38
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public static synchronized void init() {
		if (properties != null)
			return;
		try {
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(RemoteConfigurer.CACHE_PROPERTIES_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @方法功能说明：根据属性名称获取远程配置
	 * @修改者名字：zhurz
	 * @修改时间：2015-8-6上午9:31:47
	 * @修改内容：
	 * @参数：@param propertyName
	 * @参数：@param defaultValue
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String getValue(String propertyName, String defaultValue) {
		init();
		if (properties == null)
			throw new RuntimeException("远程配置读取失败");
		return properties.getProperty(propertyName, defaultValue);
	}
	
	/**
	 * @方法功能说明：根据属性名称获取远程配置
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16下午3:47:33
	 * @修改内容：
	 * @参数：@param propertyName
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String getValue(String propertyName) {
		init();
		if (properties == null)
			throw new RuntimeException("远程配置读取失败");
		return properties.getProperty(propertyName, "");
	}
}
