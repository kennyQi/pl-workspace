package zzpl.app.dao.user;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.CostCenterOrder;
import zzpl.pojo.qo.user.CostCenterOrderQO;

@Repository
public class CostCenterOrderDAO extends BaseDao<CostCenterOrder,CostCenterOrderQO > {

	@Override
	protected Criteria buildCriteria(Criteria criteria, CostCenterOrderQO qo) {
		if (qo.getOrderID()!=null&&StringUtils.isNotBlank(qo.getOrderID())) {
			criteria.add(Restrictions.eq("orderID", qo.getOrderID()));
		}
		if (qo.getOrderID()!=null&&StringUtils.isNotBlank(qo.getOrderType())) {
			criteria.add(Restrictions.eq("orderType", qo.getOrderType()));
		}
		return criteria;
	}

	@Override
	protected Class<CostCenterOrder> getEntityClass() {
		return CostCenterOrder.class;
	}

	

}
