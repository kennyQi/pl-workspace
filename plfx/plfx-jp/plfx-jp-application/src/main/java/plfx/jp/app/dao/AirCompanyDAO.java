package plfx.jp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.jp.domain.model.AirCompany;
import plfx.jp.qo.api.AirCompanyQO;

@Repository
public class AirCompanyDAO extends BaseDao<AirCompany, AirCompanyQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, AirCompanyQO qo) {
		if(qo != null){
			if(qo.getAirCompanyCode() != null){
				criteria.add(Restrictions.eq("airCompanyCode", qo.getAirCompanyCode()));
			}
			
			if(qo.getAirCompanyName() != null){
				criteria.add(Restrictions.eq("airCompanyName", qo.getAirCompanyName()));
			}
			
			if(qo.getAirCompanyShotName() != null){
				criteria.add(Restrictions.eq("airCompanyShotName", qo.getAirCompanyShotName()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<AirCompany> getEntityClass() {
		return AirCompany.class;
	}

}
