package hsl.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.hotel.order.District;
import hsl.pojo.qo.hotel.DistrictQO;
@Repository
public class DistrictDao extends BaseDao<District, DistrictQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, DistrictQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getHotelGeoId())){
				criteria.add(Restrictions.eq("hotelGeo.id", qo.getHotelGeoId()));
			}
			if(StringUtils.isNotBlank(qo.getDistrictId())){
				criteria.add(Restrictions.eq("districtId", qo.getDistrictId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<District> getEntityClass() {
		return District.class;
	}

}
