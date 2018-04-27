package plfx.gjjp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import plfx.gjjp.app.pojo.qo.GJJPTicketQo;
import plfx.gjjp.domain.model.GJJPTicket;

@Repository
public class GJJPTicketDao extends BaseDao<GJJPTicket, GJJPTicketQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, GJJPTicketQo qo) {
		return criteria;
	}

	@Override
	protected Class<GJJPTicket> getEntityClass() {
		return GJJPTicket.class;
	}

}
