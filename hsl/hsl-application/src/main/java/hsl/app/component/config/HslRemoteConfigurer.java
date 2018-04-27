package hsl.app.component.config;

import hg.common.component.RemoteConfigurer;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class HslRemoteConfigurer extends RemoteConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		SysProperties.getInstance().resetConfig(props.entrySet());
	}

}
