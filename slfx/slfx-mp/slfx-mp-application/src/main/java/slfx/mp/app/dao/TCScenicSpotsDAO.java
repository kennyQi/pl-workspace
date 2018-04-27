package slfx.mp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import slfx.mp.app.pojo.qo.TCScenicSpotsQO;
import slfx.mp.domain.model.scenicspot.TCScenicSpots;

@Repository
public class TCScenicSpotsDAO extends BaseDao<TCScenicSpots, TCScenicSpotsQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, TCScenicSpotsQO qo) {
		return criteria;
	}

	@Override
	protected Class<TCScenicSpots> getEntityClass() {
		return TCScenicSpots.class;
	}

}
