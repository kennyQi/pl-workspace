package plfx.jd.app.dao;

import org.hibernate.Criteria;

import plfx.jd.domain.model.order.Invoice;
import hg.common.component.BaseDao;
import hg.common.component.BaseQo;

public class InvoiceDAO extends BaseDao<Invoice, BaseQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return criteria;
	}

	@Override
	protected Class<Invoice> getEntityClass() {
		return Invoice.class;
	}

}
