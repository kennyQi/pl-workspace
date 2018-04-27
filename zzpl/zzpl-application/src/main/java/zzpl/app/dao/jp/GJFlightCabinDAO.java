package zzpl.app.dao.jp;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.order.GJFlightCabin;
import zzpl.pojo.qo.jp.GJFlightCabinQO;

@Repository
public class GJFlightCabinDAO extends BaseDao<GJFlightCabin, GJFlightCabinQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, GJFlightCabinQO qo) {
		return criteria;
	}

	@Override
	protected Class<GJFlightCabin> getEntityClass() {
		return GJFlightCabin.class;
	}
}
