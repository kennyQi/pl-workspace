package pl.app.dao;

import hg.common.component.BaseDao;
import hg.service.ad.domain.model.position.AdPosition;
import hg.service.ad.pojo.qo.ad.AdPositionQO;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;


@Repository
public class AdPositionDao extends BaseDao<AdPosition, AdPositionQO> {
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, AdPositionQO qo) {
		
		if (qo != null) {
		}
		return criteria;
	}

	@Override
	protected Class<AdPosition> getEntityClass() {
		return AdPosition.class;
	}

}
