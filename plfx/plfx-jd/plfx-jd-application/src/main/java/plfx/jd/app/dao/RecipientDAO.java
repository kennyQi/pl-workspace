package plfx.jd.app.dao;

import org.hibernate.Criteria;

import plfx.jd.domain.model.order.Recipient;
import hg.common.component.BaseDao;
import hg.common.component.BaseQo;

public class RecipientDAO extends BaseDao<Recipient, BaseQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return null;
	}

	@Override
	protected Class<Recipient> getEntityClass() {
		return Recipient.class;
	}

}
