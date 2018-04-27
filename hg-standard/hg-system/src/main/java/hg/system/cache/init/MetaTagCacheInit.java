package hg.system.cache.init;

import java.util.List;

import hg.common.util.SysProperties;
import hg.system.cache.MetaTagCacheManager;
import hg.system.model.seo.MetaTag;
import hg.system.qo.MetaTagQo;
import hg.system.service.MetaTagService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetaTagCacheInit implements InitializingBean {

	public final String MetaTagCacheInitAble = "MetaTagCacheInitAble";
	private final Logger logger = LoggerFactory
			.getLogger(MetaTagCacheInit.class);

	@Autowired
	private MetaTagService service;

	@Autowired
	private MetaTagCacheManager cacheManager;

	private boolean initAble() {
		String initAble = SysProperties.getInstance().get(MetaTagCacheInitAble);
		return StringUtils.equalsIgnoreCase(initAble, "true") ? true : false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!initAble())
			return;
		logger.info("MetaTag缓存初始化开始...");
		cacheManager.clearAll();
		List<MetaTag> metaTags = service.queryList(new MetaTagQo());
		for (MetaTag metaTag : metaTags) {
			cacheManager.refreshMetaTag(metaTag);
			logger.info("MetaTag缓存{}={}/{}/{}", metaTag.getUri(),
					metaTag.getTitle(), metaTag.getKeywords(),
					metaTag.getDescription());
		}
		logger.info("MetaTag缓存初始化结束...OK");
	}

}
