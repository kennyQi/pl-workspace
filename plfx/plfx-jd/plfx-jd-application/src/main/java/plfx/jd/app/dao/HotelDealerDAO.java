package plfx.jd.app.dao;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import plfx.jd.domain.model.crm.HotelDealer;
@Repository
public class HotelDealerDAO extends BaseDao<HotelDealer, BaseQo>  {

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return criteria;
	}

	@Override
	protected Class<HotelDealer> getEntityClass() {
		return HotelDealer.class;
	}

}
