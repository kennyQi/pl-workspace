package hg.dzpw.merchant.component.listener;

import hg.common.component.ConfigurerListener;

import java.net.URL;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

/**
 * @类功能说明: 系统配置监听器
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @版本：V1.0
 */
public class DZPWAdminConfigurerListener implements ConfigurerListener {
	
	@Override
	public void configLoadOver() {
		// 重新加载LOG4J配置
		URL url = Loader.getResource("log4j.properties");
		if (url != null) {
			PropertyConfigurator.configure(url);
		}
	}
}