package hsl.app.dao.hotel;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.hotel.order.OrderCancel;
import hsl.pojo.qo.hotel.OrderCancelQO;
@Repository
public class OrderCancelDAO extends BaseDao<OrderCancel, OrderCancelQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, OrderCancelQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getOrderId())){
				criteria.add(Restrictions.eq("hotelOrder.id", qo.getOrderId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<OrderCancel> getEntityClass() {
		return OrderCancel.class;
	}

}
