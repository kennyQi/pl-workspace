package pl.h5.base.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * 配置缓存
 * @author 胡永伟
 */
public class CnfCenter {
	
	private CnfCenter() {}

	private static Map<String, Long> times = new HashMap<String, Long>();
	
	private static Map<String, String> files = new HashMap<String, String>();
	
	private static Map<String, Properties> props = new HashMap<String, Properties>();

	public static void setModifyTime(String key, Long time) {
		CnfCenter.times.put(key, time);
	}
	
	public static Long getModifyTime(String key) {
		return CnfCenter.times.get(key);
	}
	
	public static void setFileUrl(String key, String url) {
		CnfCenter.files.put(key, url);
	}
	
	public static String getFileUrl(String key) {
		return CnfCenter.files.get(key);
	}
	
	public static void setProperties(String key, Properties props) {
		CnfCenter.props.put(key, props);
	}
	
	public static Properties getProperties(String key) {
		return CnfCenter.props.get(key);
	}
	
	public static List<String> getAllKeys() {
		return new ArrayList<String>(files.keySet());
	}

}
