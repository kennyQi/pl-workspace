package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.model.meta.AddrProjection;
import hg.system.qo.AddrProjectionQo;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class AddrProjectionDao extends BaseDao<AddrProjection, AddrProjectionQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, AddrProjectionQo qo) {
		return criteria;
	}

	@Override
	protected Class<AddrProjection> getEntityClass() {
		return AddrProjection.class;
	}

}
