package plfx.gjjp.app.dao;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import plfx.gjjp.app.pojo.qo.GJPassengerQo;
import plfx.gjjp.domain.model.GJPassenger;

@Repository
public class GJPassengerDao extends BaseDao<GJPassenger, GJPassengerQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, GJPassengerQo qo) {
		return criteria;
	}

	@Override
	protected Class<GJPassenger> getEntityClass() {
		return GJPassenger.class;
	}

	private void ifInitAll(GJPassengerQo qo, List<GJPassenger> list) {
		if (qo.getInitAll() != null && qo.getInitAll()) {
			for (GJPassenger passenger : list) {
				Hibernate.initialize(passenger.getJpOrder());
				Hibernate.initialize(passenger.getTickets());
			}
		}
	}

	@Override
	public List<GJPassenger> queryList(GJPassengerQo qo, Integer offset,
			Integer maxSize) {
		List<GJPassenger> list = super.queryList(qo, offset, maxSize);
		ifInitAll(qo, list);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Pagination queryPagination(Pagination pagination) {
		Pagination result = super.queryPagination(pagination);
		ifInitAll((GJPassengerQo) result.getCondition(), (List<GJPassenger>) result.getList());
		return result;
	}

}
