package plfx.jd.app.dao;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import plfx.jd.domain.model.order.Customer;
@Repository
public class CustomerDAO extends BaseDao<Customer, BaseQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return criteria;
	}

	@Override
	protected Class<Customer> getEntityClass() {
		return Customer.class;
	}

}
