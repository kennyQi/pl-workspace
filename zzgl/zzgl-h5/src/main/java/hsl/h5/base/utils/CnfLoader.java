package hsl.h5.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 配置文件加载器
 * @author 胡永伟
 */
public class CnfLoader {
	
	private CnfLoader() {}
	
	private static String cnf = "main";
	
	static {
		Configure.bind(cnf, "classpath:cnf.properties");
		load(cnf);
	}
	
	private static void reloadCnfSelf() {
		if (hasModified(cnf)) {
			load(cnf);
		}
	}
	
	public static void reload(String key) {
		if (hasLoadOnce(key)) {
			if (isCnfReload()) {
				reloadCnfSelf();
				if (isCnfReload() && hasModified(key)) {
					load(key);
				}
			}
		} else {
			load(key);
		}
	}
	
	private static boolean isCnfReload() {
		return Boolean.parseBoolean(
				CnfCenter.getProperties(cnf).getProperty("reload"));
	}
	
	private static boolean stdoutOpens() {
		return Boolean.parseBoolean(
				CnfCenter.getProperties(cnf).getProperty("stdout"));
	}
	
	private static void load(String key) {
		File config = getCnfFile(key);
		CnfCenter.setModifyTime(key, config.lastModified());
		InputStream in = null;
		try {
			in = new FileInputStream(config);
			Properties properties = new Properties();
			properties.load(in);
			CnfCenter.setProperties(key, properties);
			stdout(key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeStream(in);
		}
	}
	
	private static void stdout(String key) {
		if (stdoutOpens()) {
			System.out.println(new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()) + 
							" > cnf > log > load > " + key);
		}
	}

	private static Boolean hasLoadOnce(String key) {
		return CnfCenter.getModifyTime(key) != null;
	}
	
	private static Boolean hasModified(String key) {
		Long modifyTime = getCnfFile(key).lastModified();
		Long lastModifyTime = CnfCenter.getModifyTime(key);
		return (!modifyTime.equals(lastModifyTime));
	}
	
	private static File getCnfFile(String key) {
		String file = CnfCenter.getFileUrl(key);
		if (file == null) {
			throw new RuntimeException(
					"Found no file binding to the key named " + key + ".");
		}
		File config = new File(file);
		if (!config.exists()) {
			throw new RuntimeException("File[" + config + "] does not exist.");
		}
		return config;
	}
	
	private static void closeStream(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				;
			}
			in = null;
		}
	}

}
