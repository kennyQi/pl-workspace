package plfx.jd.app.dao;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import plfx.jd.domain.model.crm.HotelSupplier;
@Repository
public class HotelSupplierDAO extends BaseDao<HotelSupplier, BaseQo>  {
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		
		return criteria;
	}
	@Override
	protected Class<HotelSupplier> getEntityClass() {
		return HotelSupplier.class;
	}

}
