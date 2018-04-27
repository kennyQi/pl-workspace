package plfx.jp.app.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import plfx.jp.domain.model.Airport;
import plfx.jp.qo.AirportQo;

@Repository
public class AirportDao extends BaseDao<Airport, AirportQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, AirportQo qo) {
		return criteria;
	}

	@Override
	protected Class<Airport> getEntityClass() {
		return Airport.class;
	}

}
