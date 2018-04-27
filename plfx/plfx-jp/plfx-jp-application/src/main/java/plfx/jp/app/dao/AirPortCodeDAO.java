package plfx.jp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.jp.domain.model.AirPortCode;
import plfx.jp.qo.api.AirPortCodeQO;

@Repository
public class AirPortCodeDAO extends BaseDao<AirPortCode, AirPortCodeQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, AirPortCodeQO qo) {
		if(qo != null){
			if(qo.getAirCode() != null){
				criteria.add(Restrictions.eq("airCode", qo.getAirCode()));
			}
			if(qo.getAirName() != null){
				criteria.add(Restrictions.eq("airName", qo.getAirName()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<AirPortCode> getEntityClass() {
		return AirPortCode.class;
	}

}
