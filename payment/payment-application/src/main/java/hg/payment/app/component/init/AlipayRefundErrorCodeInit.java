package hg.payment.app.component.init;

import hg.payment.app.cache.AlipayRefundErrorCodeCacheManager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlipayRefundErrorCodeInit implements InitializingBean{

	@Autowired
	private AlipayRefundErrorCodeCacheManager alipayRefundErrorCodeCacheManager;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		alipayRefundErrorCodeCacheManager.initMessageCode();
	}

}
