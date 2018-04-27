package hsl.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.mp.scenic.MPHotScenicSpot;
import hsl.pojo.qo.mp.HslHotScenicSpotQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
@Repository
public class HotScenicSpotDao extends BaseDao<MPHotScenicSpot,HslHotScenicSpotQO>{
	
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, HslHotScenicSpotQO qo) {
		
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getScenicSpotId())){
				HslMPScenicSpotQO scenicSpotQO=new HslMPScenicSpotQO();
				scenicSpotQO.setId(qo.getScenicSpotId());
				Criteria criteria2 = criteria.createCriteria("scenicSpot", JoinType.LEFT_OUTER_JOIN);
				scenicSpotDao.buildCriteriaOut(criteria2, scenicSpotQO);
			}
			else if(StringUtils.isNotBlank(qo.getScenicSpotName())){
				if(qo.getScenicSpotNameIsLike()){
					HslMPScenicSpotQO scenicSpotQO=new HslMPScenicSpotQO();
					scenicSpotQO.setName(qo.getScenicSpotName());
					Criteria criteria2 = criteria.createCriteria("scenicSpot", JoinType.LEFT_OUTER_JOIN);
					scenicSpotDao.buildCriteriaOut(criteria2, scenicSpotQO);
				}
			}
			else{
				criteria.createCriteria("scenicSpot", JoinType.LEFT_OUTER_JOIN);
			}
			
			
			
			if(qo.getIsOpen()!=null&&qo.getIsOpen()){
				criteria.add(Restrictions.eq("open", true));
			}else{
				criteria.add(Restrictions.eq("open", false));
			}
			
			criteria.addOrder(Order.desc("openDate"));
		}
		
		
		return criteria;
	}

	@Override
	protected Class<MPHotScenicSpot> getEntityClass() {
		
		return MPHotScenicSpot.class;
	}

}
