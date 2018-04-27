package plfx.gjjp.app.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import plfx.gjjp.app.pojo.qo.GJFlightCabinQo;
import plfx.gjjp.domain.model.GJFlightCabin;

@Repository
public class GJFlightCabinDao extends BaseDao<GJFlightCabin, GJFlightCabinQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, GJFlightCabinQo qo) {
		return criteria;
	}

	@Override
	protected Class<GJFlightCabin> getEntityClass() {
		return GJFlightCabin.class;
	}

}
