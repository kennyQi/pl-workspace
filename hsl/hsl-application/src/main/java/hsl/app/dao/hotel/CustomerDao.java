package hsl.app.dao.hotel;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import hg.common.component.BaseDao;
import hsl.domain.model.hotel.order.Customer;
import hsl.pojo.qo.hotel.CustomerQO;

@Repository
public class CustomerDao extends BaseDao<Customer, CustomerQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, CustomerQO qo) {
		return criteria;
	}

	@Override
	protected Class<Customer> getEntityClass() {
		return Customer.class;
	}
	
}
