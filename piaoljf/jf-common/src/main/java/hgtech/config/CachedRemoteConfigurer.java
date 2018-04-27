package hgtech.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import hg.common.component.RemoteConfigurer;
import hg.common.util.PathUtil;
import hgtech.util.StringUtil;

public class CachedRemoteConfigurer extends RemoteConfigurer {
	
	//覆盖父类方法，从本地取
	@Override
	protected java.util.Map<String,Object> loadRemoteConfig() throws IOException {
		return loadCacheConfig();
	};
	
	/**
	 * 支持软回车（像sh那样，“\"+"回车")
	 */
	@Override
	public String getCacheFileText() {
		log.info(String.format("从\"{%s}\"读取缓存配置。", getCachePath()));
		String text = null;
		try {
			FileInputStream fis = new FileInputStream(getCacheFile());
			try {
				text = IOUtils.toString(fis, CACHE_TEXT_ENCODING);
				log.info("文件中的原样配置：\n"+text);
				text = StringUtil.removeSoftReturn(text);//移除软回车
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
//	@Override
	public File getCacheFile() {
		File file = null;
//		if (StringUtils.isNotBlank(cachePath))
//			file = getFile(cachePath, false);
//		else
			String path =  System.getProperties().getProperty("user.home")+"/"+CACHE_PATH_DEFAULT;
			System.out.println("read cc from :"+path);
			file = getFile(path, true);
		setCachePath(file.getPath());
		return file;
	}
	
}
