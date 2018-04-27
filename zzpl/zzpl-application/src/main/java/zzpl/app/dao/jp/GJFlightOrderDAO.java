package zzpl.app.dao.jp;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.order.GJFlightOrder;
import zzpl.pojo.qo.jp.GJFlightOrderQO;
import hg.common.component.BaseDao;

@Repository
public class GJFlightOrderDAO extends BaseDao<GJFlightOrder, GJFlightOrderQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, GJFlightOrderQO qo) {
		if(qo!=null){
			if(qo.getUserID()!=null&&StringUtils.isNotBlank(qo.getUserID())){
				criteria.add(Restrictions.eq("userID", qo.getUserID()));
			}
			if(qo.getOrderNO()!=null&&StringUtils.isNotBlank(qo.getOrderNO())){
				criteria.add(Restrictions.eq("orderNO", qo.getOrderNO()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<GJFlightOrder> getEntityClass() {
		return GJFlightOrder.class;
	}

}
