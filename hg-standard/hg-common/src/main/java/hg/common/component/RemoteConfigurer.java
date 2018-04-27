package hg.common.component;

import hg.common.model.HttpResponse;
import hg.common.util.HttpUtil;
import hg.common.util.PathUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.alibaba.fastjson.JSON;

/**
 * 远程配置
 * 
 * @author zhurz
 */
public class RemoteConfigurer extends PropertyPlaceholderConfigurer {

	public static final Logger log = LoggerFactory.getLogger(RemoteConfigurer.class);

	public static final String CACHE_PATH_DEFAULT = "remote-config.json";
	public static final String CACHE_PROPERTIES_DEFAULT = "remote-config.properties";
	public static final String CACHE_TEXT_ENCODING = "UTF-8";
	
	/**
	 * 远程配置URL
	 */
	private String remoteConfigUrl;
	/**
	 * 工程ID
	 */
	private String projectId;
	/**
	 * 环境名称
	 */
	private String environmentName;
	
	/**
	 * 缓存配置的文件路径
	 * 默认使用classpath:remote-config.json
	 */
	private String cachePath;
	
	/**
	 * 缓存属性的文件路径
	 * 默认使用classpath:remote-config.properties
	 */
	private String cachePropertiesPath;
	
	/**
	 * 远程配置读取超时事件(毫秒)
	 */
	private int loadConfigTimeOut = 30 * 1000;
	
	/**
	 * 需要替换的属性文件
	 */
	private String[] properties;
	
	private ConfigurerListener listener;

	
	/**
	 * 替换属性文件
	 * 
	 * @param config
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Deprecated
	protected void replaceProperties(Map<String, String> config) {
		if (properties != null && properties.length > 0) {
			for (String property : properties) {
				try {
					URL url = Loader.getResource(property);
					if (url == null) {
						log.info("{}不存在！", property);
						continue;
					}
					File file = new File(url.getPath());
					if (!file.exists()) {
						log.info("{}不存在！", file.getPath());
						continue;
					}
					// 备份原始属性文件
					File fileBak = new File(file.getPath() + ".bak");
					if (!fileBak.exists()) {
						FileUtils.copyFile(file, fileBak);
					}
					String str = IOUtils.toString(new FileInputStream(fileBak));
					String newStr = str;
					for (Entry<String, String> entry : config.entrySet()) {
						newStr = newStr.replace(String.format("${%s}", entry.getKey()), entry.getValue());
					}
					IOUtils.write(newStr, new FileOutputStream(file), "ISO-8859-1");
					log.info("{}替换成功！", property);
					// 更新LOG4J配置
					if (property.endsWith("log4j.properties")) {
						log.info("覆盖并重新加载log4j配置");
						PropertyConfigurator.configure(url);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("{}替换失败！", property);
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		RemoteConfigurer configurer = new RemoteConfigurer();
		configurer.setRemoteConfigUrl("http://192.168.2.85:9999/cc-admin/property-config/map");
		configurer.setProjectId("FLB");
		configurer.setEnvironmentName("FLB-TEST");
		configurer.setCachePath("C:\\Users\\Administrator\\Desktop\\新建文件夹 (2)\\a\\b\\c\\d\\cache.json");
	}

	/**
	 * 获得缓存文件
	 * 
	 * @return
	 */
	public File getCacheFile() {
		File file = null;
		if (StringUtils.isNotBlank(cachePath))
			file = getFile(cachePath, false);
		else
			file = getFile(CACHE_PATH_DEFAULT, false);
		cachePath = file.getPath();
		return file;
	}
	
	/**
	 * 获得属性文件
	 * 
	 * @return
	 */
	public File getPropertiesFile() {
		File file = null;
		if (StringUtils.isNotBlank(cachePropertiesPath))
			file = getFile(cachePropertiesPath, false);
		else
			file = getFile(CACHE_PROPERTIES_DEFAULT, false);
		cachePropertiesPath = file.getPath();
		return file;
	}
	
	/**
	 * 获得缓存文件
	 * 
	 * @param path			路径
	 * @param abs			是否绝对路径
	 * @return
	 */
	public File getFile(String path, boolean abs) {
		String filePath = null;
		if (abs)
			filePath = path;
		else
			filePath = PathUtil.getClassPath() + path;
		File file = new File(filePath);
		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		return file;
	}
	
	/**
	 * 将文本覆盖写入到cache文件中
	 * 
	 * @param text
	 * @return
	 * @throws IOException 
	 */
	public File writeCacheFile(String text) throws IOException {
		File file = getCacheFile();
		FileOutputStream fos = new FileOutputStream(file);
		try {
			IOUtils.write(text == null ? "" : text, fos, CACHE_TEXT_ENCODING);
			log.info("{}已缓存到->\"{}\"。", text, cachePath);
		} finally {
			fos.close();
		}
		return file;
	}

	/**
	 * 将文本覆盖写入到cache文件中
	 * 
	 * @param text
	 * @return
	 * @throws IOException 
	 */
	public File writeCacheProperties(Map<String, String> configMap)
			throws IOException {
		File file = getPropertiesFile();
		Properties properties = new Properties();
		FileOutputStream fos = new FileOutputStream(file);
		try {
			for (Entry<String, String> entry : configMap.entrySet())
				properties.setProperty(entry.getKey(), entry.getValue());
			properties.store(fos, "缓存属性");
			log.info("属性文件已缓存到->\"{}\"。", cachePropertiesPath);
		} finally {
			fos.close();
		}
		return file;
	}
	
	
	/**
	 * 获取缓存配置的文本
	 * 
	 * @return
	 */
	public String getCacheFileText() {
		log.info("从\"{}\"读取缓存配置。", cachePath);
		String text = null;
		try {
			FileInputStream fis = new FileInputStream(getCacheFile());
			try {
				text = IOUtils.toString(fis, CACHE_TEXT_ENCODING);
			} finally {
				fis.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return text;
	}
	
	/**
	 * 获取远程配置JSON
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected String getRemoteConfigJson() throws UnsupportedEncodingException {
		StringBuilder paramSb = new StringBuilder();
		paramSb.append("projectId=");
		paramSb.append(URLEncoder.encode(projectId, "UTF-8"));
		paramSb.append("&envName=");
		paramSb.append(URLEncoder.encode(environmentName, "UTF-8"));
		log.info("开始从{}加载远程配置，参数：{}。", remoteConfigUrl, paramSb);
		HttpResponse response = HttpUtil.reqForPost(remoteConfigUrl, paramSb.toString(), loadConfigTimeOut);
		return response.getResult();
	}
	
	/**
	 * 加载缓存配置
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> loadCacheConfig() {
		String json = getCacheFileText();
		log.info("读取本地缓存JSON：{}。", json);
		return JSON.parseObject(json, Map.class);
	}
	
	/**
	 * 加载远程配置
	 * 
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> loadRemoteConfig() throws IOException {
		String json = getRemoteConfigJson();
		log.info("返回JSON：{}。", json);
		Map<String, Object> configMap = JSON.parseObject(json, Map.class);
		log.info("将返回的JSON写入缓存。");
		try {
			writeCacheFile(json);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("配置缓存写入异常，消息{}。", e.getMessage());
		}
		return configMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) 
			throws BeansException {
		Map<String, Object> map = null;
		try {
			map = loadRemoteConfig();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("远程配置加载失败，消息：{}。", e.getMessage());
			log.info("开始尝试从本地读取缓存配置。");
			try {
				map = loadCacheConfig();
			} catch (Exception e2) {
				log.error("缓存配置加载失败，消息：{}。", e2.getMessage());
			}
		}
		if (map != null) {
			// 属性
			Map<String, String> configMap = (Map<String, String>) map.get("properties");
			for (Entry<String, String> entry : configMap.entrySet()) {
				log.debug("set {} = {}", entry.getKey(), entry.getValue());
				props.setProperty(entry.getKey(), entry.getValue());
			}
			// 缓存属性文件
			try {
				writeCacheProperties(configMap);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("缓存属性文件失败");
			}
			// 属性文件
			List<Map<String, String>> configList = (List<Map<String, String>>) map.get("propertyFile");
			for (Map<String, String> cmap : configList) {
				String fileName = PathUtil.getClassPath() + cmap.get("name");
				String encoding = cmap.get("encoding");
				String content = cmap.get("content");
				File file = new File(fileName);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				try {
					IOUtils.write(content, new FileOutputStream(file), encoding);
					log.debug("写入配置文件成功->{}", file.getPath());
				} catch (Exception e) {
					e.printStackTrace();
					log.error("写入配置文件遇到错误");
				}
			}
			// 常用属性
			try {
				props.setProperty("local_ip", InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				props.setProperty("local_ip", "0.0.0.0");
				e.printStackTrace();
				log.error("获取本地IP失败");
			}
			// 配置加载完毕
			if (listener != null) {
				listener.configLoadOver();
			}
		} else {
			log.warn("未读取到任何配置！");
		}
		super.processProperties(beanFactoryToProcess, props);
	}

	public String getRemoteConfigUrl() {
		return remoteConfigUrl;
	}

	public void setRemoteConfigUrl(String remoteConfigUrl) {
		this.remoteConfigUrl = remoteConfigUrl;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCachePath() {
		return cachePath;
	}

	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}

	public int getLoadConfigTimeOut() {
		return loadConfigTimeOut;
	}

	public void setLoadConfigTimeOut(int loadConfigTimeOut) {
		this.loadConfigTimeOut = loadConfigTimeOut;
	}

	public String getEnvironmentName() {
		return environmentName;
	}

	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}

	public String[] getProperties() {
		return properties;
	}

	public void setProperties(String[] properties) {
		this.properties = properties;
	}

	public ConfigurerListener getListener() {
		return listener;
	}

	public void setListener(ConfigurerListener listener) {
		this.listener = listener;
	}

	public String getCachePropertiesPath() {
		return cachePropertiesPath;
	}

	public void setCachePropertiesPath(String cachePropertiesPath) {
		this.cachePropertiesPath = cachePropertiesPath;
	}

}
