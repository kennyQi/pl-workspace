package hsl.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import hg.common.component.BaseDao;
import hsl.domain.model.mp.ad.HotScenicSpot;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.pojo.qo.mp.HslPCHotScenicSpotQO;

@Repository
public class PCHotScenicDao extends BaseDao<HotScenicSpot, HslPCHotScenicSpotQO>{

	@Autowired
	private ScenicSpotDao scenicSpotDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, HslPCHotScenicSpotQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getAdId())){
				criteria.add(Restrictions.eq("adId", qo.getAdId()));
			}
			if(StringUtils.isNotBlank(qo.getScenicSpotId())){
				HslMPScenicSpotQO scenicSpotQO=new HslMPScenicSpotQO();
				scenicSpotQO.setId(qo.getScenicSpotId());
				Criteria criteria2 = criteria.createCriteria("scenicSpot", JoinType.LEFT_OUTER_JOIN);
				scenicSpotDao.buildCriteriaOut(criteria2, scenicSpotQO);
			}
		}
		return criteria;
	}

	@Override
	protected Class<HotScenicSpot> getEntityClass() {
		return HotScenicSpot.class;
	}

}
