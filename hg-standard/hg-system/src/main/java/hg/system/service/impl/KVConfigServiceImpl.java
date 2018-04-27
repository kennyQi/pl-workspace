package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.cache.KVConfigCacheManager;
import hg.system.dao.KVConfigDao;
import hg.system.model.config.KVConfig;
import hg.system.qo.KVConfigQo;
import hg.system.service.KVConfigService;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("kvConfigService")
public class KVConfigServiceImpl extends BaseServiceImpl<KVConfig, KVConfigQo, KVConfigDao> implements KVConfigService {
	@Resource 
	private KVConfigCacheManager kvConfigCacheManager;
	
	@Autowired
	private KVConfigDao kvConfigDao;
	
	@Override
	protected KVConfigDao getDao() {
		return kvConfigDao;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public KVConfig get(Serializable id) {
		return super.get(id);
	}
	
	@Override
	public void deleteById(Serializable id) {
		KVConfig entity =super.get(id);
		this.simpleDao.delete(entity);
		kvConfigCacheManager.removeKV(entity.getDataKey());
	}

	@Override
	public KVConfig save(KVConfig entity) {
		entity  = super.save(entity);
		kvConfigCacheManager.putKV(entity);
		return entity;
	}
	
	@Override
	public KVConfig update(KVConfig entity) {
		entity = super.update(entity);
		kvConfigCacheManager.putKV(entity);
		return entity;
	}
	
}
