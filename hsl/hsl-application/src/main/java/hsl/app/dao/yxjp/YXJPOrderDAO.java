package hsl.app.dao.yxjp;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.common.util.MyBeanUtils;
import hsl.domain.model.yxjp.YXJPOrder;
import hsl.domain.model.yxjp.YXJPOrderPassenger;
import hsl.pojo.qo.yxjp.YXJPOrderQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("yxjpOrderDAO")
public class YXJPOrderDAO extends BaseDao<YXJPOrder, YXJPOrderQO> {

	@Autowired
	private YXJPOrderPassengerDAO passengerDAO;

	@Override
	protected Criteria buildCriteria(Criteria criteria, YXJPOrderQO qo) {

		// 乘客信息（子查询）
		if (qo.getPassengerQO() != null) {
			DetachedCriteria dc = DetachedCriteria.forClass(YXJPOrderPassenger.class, "p");
			dc.setProjection(Projections.property("p.id"));
			dc.add(Property.forName("p.fromOrder.id").eqProperty((StringUtils.isBlank(qo.getAlias()) ? Criteria.ROOT_ALIAS : qo.getAlias()) + ".id"));
			Criteria passengerCriteria = (Criteria) MyBeanUtils.getFieldValue(dc, "criteria");
			passengerDAO.buildCriteriaOut(passengerCriteria, qo.getPassengerQO());
			criteria.add(Subqueries.exists(dc));
		}

		return criteria;
	}

	private void checkFetch(List<YXJPOrder> orders, YXJPOrderQO qo) {
		if (qo != null && orders != null && orders.size() > 0) {
			for (YXJPOrder order : orders)
				if (qo.isFetchPassengers())
					Hibernate.initialize(order.getPassengers());
		}
	}

	@Override
	public List<YXJPOrder> queryList(YXJPOrderQO qo, Integer offset, Integer maxSize) {
		List<YXJPOrder> orders = super.queryList(qo, offset, maxSize);
		checkFetch(orders, qo);
		return orders;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination resultPagination = super.queryPagination(pagination);
		checkFetch((List<YXJPOrder>) resultPagination.getList(), (YXJPOrderQO) pagination.getCondition());
		return resultPagination;
	}

	@Override
	protected Class<YXJPOrder> getEntityClass() {
		return YXJPOrder.class;
	}
}
