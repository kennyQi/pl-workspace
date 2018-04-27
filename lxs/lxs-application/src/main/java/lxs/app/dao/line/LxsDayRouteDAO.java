package lxs.app.dao.line;

import hg.common.component.BaseDao;
import lxs.domain.model.line.DayRoute;
import lxs.pojo.qo.line.DayRouteQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LxsDayRouteDAO extends BaseDao<DayRoute, DayRouteQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, DayRouteQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getLineID())){
				criteria.add(Restrictions.eq("line.id", qo.getLineID()));
			}
		}
		criteria.addOrder(Order.asc("days"));
		return criteria;
	}

	@Override
	protected Class<DayRoute> getEntityClass() {
		return DayRoute.class;
	}

}
