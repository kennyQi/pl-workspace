package plfx.gjjp.app.dao;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import plfx.gjjp.app.pojo.qo.GJJPOrderQo;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.gjjp.domain.model.GJPassenger;

@Repository("gjjpOrderDao")
public class GJJPOrderDao extends BaseDao<GJJPOrder, GJJPOrderQo> {
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, GJJPOrderQo qo) {
		
		if (qo.getCreateDateSort() == null) {
			// 默认创建时间降序
			criteria.addOrder(Order.desc("baseInfo.createDate"));
		}

		// 乘机人信息查询
		if (StringUtils.isNotBlank(qo.getPassengerName()) || qo.getTicketStatus() != null) {

			String thisAlias = qo.getAlias() == null ? Criteria.ROOT_ALIAS : qo.getAlias();
			DetachedCriteria passengerCriteria = DetachedCriteria.forClass(GJPassenger.class, "p");
			passengerCriteria.add(Property.forName(thisAlias + ".id").eqProperty("jpOrder.id"));
			passengerCriteria.setProjection(Projections.property("p.id"));

			// 乘客姓名
			if (StringUtils.isNotBlank(qo.getPassengerName())) {
				passengerCriteria.add(Restrictions.eq("baseInfo.passengerName", qo.getPassengerName()));
			}

			// 机票状态
			if (qo.getTicketStatus() != null) {
				passengerCriteria.add(Restrictions.eq("status.currentValue", qo.getTicketStatus()));
			}

			criteria.add(Subqueries.exists(passengerCriteria));
		}

		return criteria;
	}

	@Override
	protected Class<GJJPOrder> getEntityClass() {
		return GJJPOrder.class;
	}

	private void ifInitAll(GJJPOrderQo qo, List<GJJPOrder> list) {
		if (qo.getInitAll() != null && qo.getInitAll()) {
			for (GJJPOrder jpOrder : list) {
				List<GJPassenger> passengers = jpOrder.getPassengers();
				Hibernate.initialize(passengers);
				if (passengers != null) {
					for (GJPassenger passenger : passengers)
						Hibernate.initialize(passenger.getTickets());
				}
				Hibernate.initialize(jpOrder.getSegmentInfo().getTakeoffFlights());
				Hibernate.initialize(jpOrder.getSegmentInfo().getBackFlights());
			}
		}
	}

	@Override
	public List<GJJPOrder> queryList(GJJPOrderQo qo, Integer offset, Integer maxSize) {
		List<GJJPOrder> list = super.queryList(qo, offset, maxSize);
		ifInitAll(qo, list);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pagination queryPagination(Pagination pagination) {
		Pagination result = super.queryPagination(pagination);
		ifInitAll((GJJPOrderQo) result.getCondition(), (List<GJJPOrder>) result.getList());
		return result;
	}

}
