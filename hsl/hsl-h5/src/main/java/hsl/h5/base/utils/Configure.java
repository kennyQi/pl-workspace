package hsl.h5.base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * 配置管理器
 * @author 胡永伟
 */
public class Configure {
	
	private Configure() {}
	
	private String key;
	
	public static void bind(String key, String file) {
		if (file != null && file.startsWith("classpath:")) {
			file = file.substring("classpath:".length());
			file = Configure.class.getClassLoader().getResource(file).getFile();
			try {
				file = URLDecoder.decode(file, "utf-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		CnfCenter.setFileUrl(key, file);
	}
	
	public static Configure getInstance(String key) {
		Configure configure = new Configure();
		configure.key = key;
		return configure;
	}
	
	public String get(String name) {
		CnfLoader.reload(key);
		Properties props = CnfCenter.getProperties(this.key);
		return (props != null) ? props.getProperty(name) : null;
	}
	
}
