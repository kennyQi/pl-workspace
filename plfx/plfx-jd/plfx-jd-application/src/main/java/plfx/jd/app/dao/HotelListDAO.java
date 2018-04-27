package plfx.jd.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.jd.domain.model.hotel.YLHotelList;
import plfx.jd.pojo.qo.YLHotelListQO;
@Repository
public class HotelListDAO extends BaseDao<YLHotelList, YLHotelListQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, YLHotelListQO qo) {

		if(qo != null){
			
			if(StringUtils.isNotBlank(qo.getHotelId())){
				criteria.add(Restrictions.eq("hotelId",qo.getHotelId()));
			}
			if(qo.getStatus() != null){
				criteria.add(Restrictions.eq("status",qo.getStatus()));
			}
			if(qo.getUpdateStatus() != null){
				criteria.add(Restrictions.eq("updateStatus",qo.getStatus()));
			}
			if(qo.getUpdatedTime() != null){
				criteria.add(Restrictions.ge("updatedTime",qo.getUpdatedTime()));
				
			}
		}
		return criteria;
	}

	@Override
	protected Class<YLHotelList> getEntityClass() {
		// TODO Auto-generated method stub
		return YLHotelList.class;
	}

}
