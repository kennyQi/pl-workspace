package lxs.app.dao.mp;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import lxs.domain.model.mp.TicketOrder;
import lxs.pojo.qo.mp.TicketOrderQO;
import hg.common.component.BaseDao;

@Repository
public class TicketOrderDAO extends BaseDao<TicketOrder, TicketOrderQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, TicketOrderQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getUserID())){
				criteria.add(Restrictions.eq("userID", qo.getUserID()));
			}
			if(StringUtils.isNotBlank(qo.getOrderNO())){
				criteria.add(Restrictions.eq("orderNO", qo.getOrderNO()));
			}
			if(StringUtils.isNotBlank(qo.getSerialNumber())){
				criteria.add(Restrictions.eq("serialNumber", qo.getSerialNumber()));
			}
			if(StringUtils.isNotBlank(qo.getBookMan())){
				criteria.add(Restrictions.like("bookMan", qo.getBookMan(), MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE));
			}
		}
		criteria.addOrder(Order.desc("creatDate"));
		return criteria;
	}

	@Override
	protected Class<TicketOrder> getEntityClass() {
		return TicketOrder.class;
	}

}
