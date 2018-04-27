package jxc.app.dao.system;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import jxc.domain.model.system.Zone;
@Repository
public class ZoneDao extends BaseDao<Zone, BaseQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		if (qo != null) {
			//id条件
			if (StringUtils.isNotBlank(qo.getId())) {
					criteria.add(Restrictions.eq("id", qo.getId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Zone> getEntityClass() {
		return Zone.class;
	}

}
