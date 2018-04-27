package plfx.xl.app.component.init;

import hg.system.cache.KVConfigCacheManager;
import hg.system.model.config.KVConfig;
import hg.system.qo.KVConfigQo;
import hg.system.service.KVConfigService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("kVConfigInit_xl")
public class KVConfigInit implements InitializingBean {

	private final static Logger logger = LoggerFactory.getLogger(KVConfigInit.class);

	@Autowired
	private KVConfigCacheManager cacheManager;

	@Autowired
	private KVConfigService kvConfigService;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("数据字典加载...");
		KVConfigQo qo = new KVConfigQo();
		List<KVConfig> list = kvConfigService.queryList(qo);
		cacheManager.reflushKVConfig(list);
		logger.info("数据字典加载完毕");
	}

}
