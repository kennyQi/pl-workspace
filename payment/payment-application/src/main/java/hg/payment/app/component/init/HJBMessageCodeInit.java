package hg.payment.app.component.init;

import hg.payment.app.cache.HJBMessageCodeCacheManager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * 
 *@类功能说明：
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年11月19日上午8:59:19
 *
 */
@Component
public class HJBMessageCodeInit implements InitializingBean{

	@Autowired
	private HJBMessageCodeCacheManager hjbMessageCodeCacheManager;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		hjbMessageCodeCacheManager.initMessageCode();
	}

}
