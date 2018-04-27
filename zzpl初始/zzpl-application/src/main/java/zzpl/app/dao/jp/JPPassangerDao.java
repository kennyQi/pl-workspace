package zzpl.app.dao.jp;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.jp.JPPassanger;
import zzpl.pojo.qo.jp.JPPassangerQO;

@Repository
public class JPPassangerDao extends BaseDao<JPPassanger, JPPassangerQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, JPPassangerQO qo) {
		return null;
	}

	@Override
	protected Class<JPPassanger> getEntityClass() {

		return JPPassanger.class;
	}

}
