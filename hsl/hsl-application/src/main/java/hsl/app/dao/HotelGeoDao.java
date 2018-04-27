package hsl.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.hotel.order.HotelGeo;
import hsl.pojo.qo.hotel.HotelGeoQO;
@Repository
public class HotelGeoDao extends BaseDao<HotelGeo, HotelGeoQO>{
	@Override
	protected Criteria buildCriteria(Criteria criteria, HotelGeoQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getCityName())){
				criteria.add(Restrictions.eq("cityName", qo.getCityName()));
			}
			if(StringUtils.isNotBlank(qo.getCityCode())){
				criteria.add(Restrictions.eq("cityCode", qo.getCityCode()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<HotelGeo> getEntityClass() {
		return HotelGeo.class;
	}


}
