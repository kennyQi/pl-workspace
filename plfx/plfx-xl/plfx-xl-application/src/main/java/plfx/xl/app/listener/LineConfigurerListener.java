package plfx.xl.app.listener;

import hg.common.component.ConfigurerListener;
import java.net.URL;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

public class LineConfigurerListener implements ConfigurerListener {

	@Override
	public void configLoadOver() {
		// 重新加载LOG4J配置
		URL url = Loader.getResource("log4j.properties");
		if (url != null) {
			PropertyConfigurator.configure(url);
		}
	}

}
