package plfx.jp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import plfx.jp.domain.model.AirlineCompany;
import plfx.jp.qo.AirlineCompanyQo;

@Repository
public class AirlineCompanyDao extends BaseDao<AirlineCompany, AirlineCompanyQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, AirlineCompanyQo qo) {
		return criteria;
	}

	@Override
	protected Class<AirlineCompany> getEntityClass() {
		return AirlineCompany.class;
	}

}
