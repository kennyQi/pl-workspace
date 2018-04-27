package hsl.app.dao;

import hg.common.component.BaseDao;
import hsl.domain.model.hotel.order.CommericalLocation;
import hsl.pojo.qo.hotel.CommericalLocationQO;


import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
@Repository
public class CommericalLocationDao extends BaseDao<CommericalLocation, CommericalLocationQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, CommericalLocationQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getHotelGeoId())){
				criteria.add(Restrictions.eq("hotelGeo.id", qo.getHotelGeoId()));
			}
			if(StringUtils.isNotBlank(qo.getBusinessZoneId())){
				criteria.add(Restrictions.eq("commericalLocationId", qo.getBusinessZoneId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<CommericalLocation> getEntityClass() {
		return CommericalLocation.class;
	}

}
