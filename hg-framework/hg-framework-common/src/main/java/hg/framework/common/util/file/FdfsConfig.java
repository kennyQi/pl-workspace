package hg.framework.common.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @类功能说明：FastDFS配置
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：
 * @创建时间：
 */
public class FdfsConfig {
	private Properties properties = null;
	private final String PROP_PATH = "/fastdfs_client.conf";
	private static FdfsConfig fdfsConfig;

	public static FdfsConfig getInstance() {
		if (fdfsConfig == null) {
			synchronized (FdfsConfig.class) {
				if (fdfsConfig == null)
					fdfsConfig = new FdfsConfig();
			}
		}
		return fdfsConfig;
	}

	private FdfsConfig() {
		init();
	}

	public void init() {
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
			in = FdfsConfig.class.getResourceAsStream(path);
			props.load(in);
			return props;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props;
	}

	private InputStream getInputStream() {
		return FdfsConfig.class.getResourceAsStream(PROP_PATH);
	}

	public String get(String key) {
		return properties.getProperty(key);
	}
}