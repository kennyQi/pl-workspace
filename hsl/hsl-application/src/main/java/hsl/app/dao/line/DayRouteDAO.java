package hsl.app.dao.line;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import hsl.domain.model.xl.DayRoute;

@Repository
public class DayRouteDAO extends BaseDao<DayRoute, BaseQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return criteria;
	}

	@Override
	protected Class<DayRoute> getEntityClass() {
		return DayRoute.class;
	}

}
