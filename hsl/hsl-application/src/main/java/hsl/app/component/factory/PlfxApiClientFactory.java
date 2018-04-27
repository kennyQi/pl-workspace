package hsl.app.component.factory;

import hsl.app.component.config.SysProperties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import plfx.api.client.common.util.PlfxApiClient;

/**
 * 票量分销客户端工厂类
 *
 * @author zhurz
 * @since 1.7
 */
@Component
public class PlfxApiClientFactory implements FactoryBean<PlfxApiClient> {

	@Override
	public PlfxApiClient getObject() throws Exception {
		String url = SysProperties.getInstance().get("plfx_api_url", "http://127.0.0.1:8084/slfx-api/api");
		String dealerKey = SysProperties.getInstance().get("plfx_api_key", "F1001");
		String secretKey = SysProperties.getInstance().get("plfx_api_pwd", "123456");
//		return new PlfxApiClient(url, dealerKey, secretKey);
		return new PlfxApiDevClient(url, dealerKey, secretKey);
	}

	@Override
	public Class<?> getObjectType() {
		return PlfxApiClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
