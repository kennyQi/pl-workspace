package hsl.app.component.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class SysProperties {

	private Properties properties = null;
	private final String PROP_PATH = "/system.properties";
	private static SysProperties sysProperties;
	private Map<String, String> remoteConfigMap = new HashMap<String, String>();
	
	public synchronized void resetConfig(Set<Entry<Object, Object>> configEntrySet) {
		remoteConfigMap.clear();
		for (Entry<Object, Object> entry : configEntrySet) {
			if (entry.getKey() == null || entry.getValue() == null)
				continue;
			remoteConfigMap.put(entry.getKey().toString(), entry.getValue().toString());
		}
	}

	public static SysProperties getInstance() {
		if (sysProperties == null) {
			synchronized (SysProperties.class) {
				if (sysProperties == null){
					sysProperties = new SysProperties();					
				}
			}
		}
		return sysProperties;
	}

	private SysProperties() {
		init();
	}

	private void init() {
		try {
			InputStream inputStream = getInputStream();
			properties = new Properties();
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Properties getProp(String path) {
		Properties props = new Properties();
		InputStream in = null;
		try {
			// 讲文件信息读取到输出流
			in = SysProperties.class.getResourceAsStream(path);
			props.load(in);
			return props;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null){
					in.close();					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props;
	}

	private InputStream getInputStream() {
		return SysProperties.class.getResourceAsStream(PROP_PATH);
	}

	public String get(String key) {
		if (key == null) return null;
		String value = properties.getProperty(key);
		if (value == null) value = remoteConfigMap.get(key);
		return value;
	}

	/**
	 * 获取配置，如果配置为null时使用默认值
	 *
	 * @param key
	 * @param defaultValue			默认值
	 * @return
	 */
	public String get(String key, String defaultValue) {
		String value = get(key);
		return value == null ? defaultValue : value;
	}
}
