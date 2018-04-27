package zzpl.app.dao.user;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.FrequentFlyer;
import zzpl.pojo.qo.user.FrequentFlyerQO;

@Repository
public class FrequentFlyerDAO extends BaseDao<FrequentFlyer, FrequentFlyerQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, FrequentFlyerQO qo) {
		if(qo!=null){
			if(qo.getUserID()!=null&&StringUtils.isNotBlank(qo.getUserID())){
				criteria.add(Restrictions.eq("userID", qo.getUserID()));
			}
			if(qo.getIdNO()!=null&&StringUtils.isNotBlank(qo.getIdNO())){
				criteria.add(Restrictions.eq("idNO", qo.getIdNO()));
			}
		}
		criteria.addOrder(Order.desc("createTime"));
		return criteria;
	}

	@Override
	protected Class<FrequentFlyer> getEntityClass() {
		return FrequentFlyer.class;
	}

}
