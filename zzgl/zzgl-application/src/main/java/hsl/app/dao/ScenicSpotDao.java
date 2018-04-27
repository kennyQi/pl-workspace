package hsl.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.mp.scenic.MPScenicSpot;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
@Repository
public class ScenicSpotDao extends BaseDao<MPScenicSpot, HslMPScenicSpotQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslMPScenicSpotQO qo) {
		
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.ilike("scenicSpotBaseInfo.name", qo.getName(),MatchMode.ANYWHERE));
			}
		}
		return criteria;
	}

	@Override
	protected Class<MPScenicSpot> getEntityClass() {
		return MPScenicSpot.class;
	}

}
