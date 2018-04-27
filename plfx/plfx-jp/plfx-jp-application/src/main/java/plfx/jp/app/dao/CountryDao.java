package plfx.jp.app.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import plfx.jp.domain.model.Country;
import plfx.jp.qo.CountryQo;

import hg.common.component.BaseDao;

@Repository
public class CountryDao extends BaseDao<Country, CountryQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, CountryQo qo) {
		return criteria;
	}

	@Override
	protected Class<Country> getEntityClass() {
		return Country.class;
	}

}
