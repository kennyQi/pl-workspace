package plfx.jd.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.jd.domain.model.hotel.YLHotel;
import plfx.jd.domain.model.hotel.YLHotelImage;
import plfx.jd.pojo.qo.YLHotelQO;
@Repository
public class HotelDAO extends BaseDao<YLHotel, YLHotelQO>  {

	@Override
	protected Criteria buildCriteria(Criteria criteria, YLHotelQO qo) {
		// TODO Auto-generated method stub
		if(qo != null){
			//酒店ID
			if(qo.getHotelId() != null){
				criteria.add(Restrictions.eq("hotelId", qo.getHotelId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<YLHotel> getEntityClass() {
		// TODO Auto-generated method stub
		return YLHotel.class;
	}
	
	public YLHotel queryUnique(YLHotelQO qo) {
		YLHotel ylHotel = super.queryUnique(qo);
		if (ylHotel != null) {
			if(null != ylHotel.getImageList()){
				for(YLHotelImage image:ylHotel.getImageList()){
					Hibernate.initialize(image.getLocationsList());
				}
			}
			if(null != ylHotel.getRoomList()){
				Hibernate.initialize(ylHotel.getRoomList());
			}
			if(null != ylHotel.getSupplierList()){
				Hibernate.initialize(ylHotel.getSupplierList());
			}
			
		 	
		}
		return ylHotel;
	}

	

}
