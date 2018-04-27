package hsl.app.common.util;

import hsl.app.component.config.SysProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * 应用信息工具
 *
 * @author zhurz
 * @since 1.7.1
 */
public class AppInfoUtils {

	private Properties appProperties;
	private Properties mvnProperties;
	public static final String appVersion = "app.version";

	private static AppInfoUtils instance;

	private AppInfoUtils() {
	}

	private static synchronized void init() {
		if (instance == null) {
			instance = new AppInfoUtils();
			try {
				instance.appProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("app-info.properties"));
				instance.mvnProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("META-INF/maven/hsl/hsl-application/pom.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (instance.appProperties == null) instance.appProperties = new Properties();
				if (instance.mvnProperties == null) instance.mvnProperties = new Properties();
			}
		}
	}

	/**
	 * 获取应用版本
	 *
	 * @return
	 */
	public static String getVersion() {
		init();
		String appVersion = instance.appProperties.getProperty(AppInfoUtils.appVersion);
		if ("${app.version}".equals(appVersion) || StringUtils.isBlank(appVersion))
			appVersion = instance.mvnProperties.getProperty("version");
		return StringUtils.isBlank(appVersion) ? "app" : appVersion;
	}

	/**
	 * 得到支付宝备注前缀，在开发环境中返回【开发】，测试返回【测试】，生产返回空字符串。
	 *
	 * @return
	 */
	public static String getAlipayRemarkPrefix() {
		String envId = SysProperties.getInstance().get("envId", "");
		// 区分支付记录
		if (envId.contains("DEV"))
			return "【开发】";
		else if (envId.contains("TEST"))
			return "【测试】";
		return "";
	}

}
