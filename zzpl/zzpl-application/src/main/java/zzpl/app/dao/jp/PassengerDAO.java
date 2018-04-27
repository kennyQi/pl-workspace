package zzpl.app.dao.jp;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import zzpl.domain.model.order.Passenger;
import zzpl.pojo.qo.jp.PassengerQO;

@Repository
public class PassengerDAO extends BaseDao<Passenger, PassengerQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, PassengerQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getPassengerName())){
				criteria.add(Restrictions.eq("passengerName", qo.getPassengerName()));
			}
			if(StringUtils.isNotBlank(qo.getAirID())){
				criteria.add(Restrictions.eq("airID", qo.getAirID()));
			}
			if(StringUtils.isNotBlank(qo.getFlightOrderID())){
				criteria.add(Restrictions.eq("flightOrder.id", qo.getFlightOrderID()));			
			}
			if(StringUtils.isNotBlank(qo.getGjFlightOrderID())){
				criteria.add(Restrictions.eq("gjFlightOrder.id", qo.getGjFlightOrderID()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Passenger> getEntityClass() {
		return Passenger.class;
	}

}
