package hsl.h5.base.listener;

import hg.common.component.ConfigurerListener;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

import java.net.URL;

/**
 * @类功能说明：系统配置监听器
 * @类修改者：
 * @修改日期：2015年2月10日上午10:13:01
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年2月10日上午10:13:01
 */
public class ZZPLWapConfigurerListener implements ConfigurerListener {
	@Override
	public void configLoadOver() {
		// 重新加载LOG4J配置
		URL url = Loader.getResource("log4j.properties");
		if (url != null) {
			PropertyConfigurator.configure(url);
		}
	}
	
}