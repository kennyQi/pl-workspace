package plfx.gjjp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import plfx.gjjp.app.pojo.qo.GJJPOrderLogQo;
import plfx.gjjp.domain.model.GJJPOrderLog;

@Repository()
public class GJJPOrderLogDao extends BaseDao<GJJPOrderLog, GJJPOrderLogQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, GJJPOrderLogQo qo) {
		if (qo.getLogDateSort() == null) {
			// 默认升序
			criteria.addOrder(Order.asc("logDate"));
		}
		return criteria;
	}

	@Override
	protected Class<GJJPOrderLog> getEntityClass() {
		return GJJPOrderLog.class;
	}

}
