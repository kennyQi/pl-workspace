package hsl.app.dao;

import hg.common.component.BaseDao;
import hsl.domain.model.jp.JPPassanger;
import hsl.pojo.qo.jp.HslJPPassangerQO;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class JPPassangerDao extends BaseDao<JPPassanger, HslJPPassangerQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslJPPassangerQO qo) {
		return null;
	}

	@Override
	protected Class<JPPassanger> getEntityClass() {

		return JPPassanger.class;
	}

}
