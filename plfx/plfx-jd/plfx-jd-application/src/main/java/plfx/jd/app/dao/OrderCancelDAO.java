package plfx.jd.app.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import plfx.jd.domain.model.order.OrderCancel;
import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
@Repository
public class OrderCancelDAO extends BaseDao<OrderCancel, BaseQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return criteria;
	}

	@Override
	protected Class<OrderCancel> getEntityClass() {
		return OrderCancel.class;
	}

}
