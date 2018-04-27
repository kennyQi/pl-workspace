package hsl.app.component.factory;

import hg.common.util.SpringContextUtil;
import hg.dzpw.dealer.client.common.util.DealerApiClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 电子票务对经销商接口类
 *
 * @author zhurz
 * @since 1.8
 */
@Component
public class DealerApiClientFactory implements FactoryBean<DealerApiClient> {

	@Override
	public DealerApiClient getObject() throws Exception {
		try {
			return (DealerApiClient) SpringContextUtil.getBean("dzpwDealerApiClient");
		} catch (Exception e) {
			return new DealerApiClient(
					"http://192.168.2.56:8080/api",
					"JX0019",
					"e86faf87d740469aab499bac642a3fc5"
			);
		}
	}

	@Override
	public Class<?> getObjectType() {
		return DealerApiClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}