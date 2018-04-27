package jxc.app.dao.system;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import jxc.domain.model.system.SystemConfig;
import hg.common.component.BaseDao;
import hg.common.component.BaseQo;

@Repository
public class SystemConfigDao extends BaseDao<SystemConfig, BaseQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return criteria;
	}

	@Override
	protected Class<SystemConfig> getEntityClass() {
		return SystemConfig.class;
	}

}
