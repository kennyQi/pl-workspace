package hsl.app.dao;

import hg.common.component.BaseDao;
import hsl.domain.model.jp.Passenger;
import hsl.pojo.qo.jp.HslJPPassangerQO;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository									 
public  class JPPassengerDao extends BaseDao<Passenger, HslJPPassangerQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslJPPassangerQO qo) {
		return null;
	}

	@Override
	protected Class<Passenger> getEntityClass() {

		return Passenger.class;
	}

}
