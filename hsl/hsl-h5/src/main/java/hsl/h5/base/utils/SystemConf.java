package hsl.h5.base.utils;

/**
 * 系统配置管理器
 * @author 胡永伟
 */
public class SystemConf {

	private SystemConf() {}
	
	private static Configure configure;
	
	private static String _status = null;
	
	static {
		Configure.bind("status", "classpath:status.properties");
		configure = Configure.getInstance("status");
	}
	
	public static String get(String name) {
		String status = configure.get("system_status");
		if (_status == null || !_status.equals(status)) {
			Configure.bind("system", "classpath:system/" + status + ".properties");
			_status = status;
		}
		return Configure.getInstance("system").get(name);
	}
	
}
