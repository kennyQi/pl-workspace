package hg.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SysProperties {

	private Properties properties = null;
	private final String PROP_PATH = "/system.properties";
	private static SysProperties sysProperties;

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
			// TODO Auto-generated catch block
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
		return properties.getProperty(key);
	}

}
