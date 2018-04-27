package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.model.config.KVConfig;
import hg.system.qo.KVConfigQo;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository("kvConfigDao")
public class KVConfigDao extends BaseDao<KVConfig, KVConfigQo> {

	@Override
	protected Class<KVConfig> getEntityClass() {
		return KVConfig.class;
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, KVConfigQo qo) {
		// TODO Auto-generated method stub
		return criteria;
	}

}
