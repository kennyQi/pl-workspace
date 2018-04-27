package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.cache.MetaTagCacheManager;
import hg.system.command.seo.CreateMetaTagCommand;
import hg.system.command.seo.DeleteMetaTagCommand;
import hg.system.command.seo.ModifyMetaTagCommand;
import hg.system.dao.MetaTagDao;
import hg.system.model.seo.MetaTag;
import hg.system.qo.MetaTagQo;
import hg.system.service.MetaTagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MetaTagServiceImpl extends BaseServiceImpl<MetaTag, MetaTagQo, MetaTagDao> implements MetaTagService {
	
	@Autowired
	private MetaTagDao dao;
	@Autowired
	private MetaTagCacheManager cacheManager;

	@Override
	public void createMetaTag(CreateMetaTagCommand command) {
		MetaTag metaTag = new MetaTag();
		metaTag.create(command);
		getDao().save(metaTag);
	}

	@Override
	public void modifyMetaTag(ModifyMetaTagCommand command) {
		if (command.getId() == null)
			throw new RuntimeException("缺少META标签ID");
		MetaTag metaTag = getDao().get(command.getId());
		if (metaTag == null)
			throw new RuntimeException("META标签不存在");
		cacheManager.delMetaTag(metaTag.getUri());
		metaTag.modify(command);
		getDao().update(metaTag);
	}

	@Override
	public void deleteMetaTag(DeleteMetaTagCommand command) {
		if (command == null || command.getId() == null)
			return;
		MetaTag metaTag = getDao().get(command.getId());
		if (metaTag == null)
			return;
		getDao().delete(metaTag);
		cacheManager.delMetaTag(metaTag.getUri());
	}

	@Override
	protected MetaTagDao getDao() {
		return dao;
	}

}
