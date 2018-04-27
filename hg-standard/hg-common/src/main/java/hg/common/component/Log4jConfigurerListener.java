package hg.common.component;

import java.net.URL;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

/**
 * @类功能说明：LOG4J配置监听器
 * @类修改者：
 * @修改日期：2015-1-12下午5:08:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-1-12下午5:08:24
 */
public class Log4jConfigurerListener implements ConfigurerListener {

	@Override
	public void configLoadOver() {
		// 重新加载LOG4J配置
		URL url = Loader.getResource("log4j.properties");
		if (url != null) {
			PropertyConfigurator.configure(url);
		}
	}

}
