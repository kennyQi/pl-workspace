package hsl.app.component.factory;

import hsl.app.component.config.SysProperties;
import hsl.payment.alipay.config.HSLAlipayConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 支付宝配置工厂类
 *
 * @author zhurz
 * @since 1.7
 */
@Component
public class HSLAlipayConfigFactory implements FactoryBean<HSLAlipayConfig> {

	@Override
	public HSLAlipayConfig getObject() throws Exception {
		HSLAlipayConfig config = new HSLAlipayConfig();
		config.setKey(SysProperties.getInstance().get("alipay_key"));
		config.setPartner(SysProperties.getInstance().get("alipay_partner"));
		config.setSellerEmail(SysProperties.getInstance().get("alipay_seller_email"));
		return config;
	}

	@Override
	public Class<?> getObjectType() {
		return HSLAlipayConfig.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
