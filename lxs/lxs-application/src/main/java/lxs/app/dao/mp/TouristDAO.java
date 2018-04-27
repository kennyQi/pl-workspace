package lxs.app.dao.mp;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import lxs.domain.model.mp.Tourist;
import lxs.pojo.qo.mp.TouristQO;
import hg.common.component.BaseDao;

@Repository
public class TouristDAO extends BaseDao<Tourist, TouristQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, TouristQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getOrderID())){
				criteria.add(Restrictions.eq("orderID", qo.getOrderID()));
			}
			if(StringUtils.isNotBlank(qo.getTicketNo())){
				criteria.add(Restrictions.eq("ticketNo", qo.getTicketNo()));
			}
			if(qo.getLocalStatus()!=null){
				criteria.add(Restrictions.eq("localStatus", qo.getLocalStatus()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Tourist> getEntityClass() {
		return Tourist.class;
	}

}
