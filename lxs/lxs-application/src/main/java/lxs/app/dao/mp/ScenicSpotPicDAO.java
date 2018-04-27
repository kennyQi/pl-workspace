package lxs.app.dao.mp;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import lxs.domain.model.mp.ScenicSpotPic;
import lxs.pojo.qo.mp.ScenicSpotPicQO;
import hg.common.component.BaseDao;

@Repository
public class ScenicSpotPicDAO extends BaseDao<ScenicSpotPic, ScenicSpotPicQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, ScenicSpotPicQO qo) {
		if(qo!=null){
			if(qo.getVersionNO()!=null){
				if(qo.getVersionType()==ScenicSpotPicQO.GREATER_THAN){
					criteria.add(Restrictions.gt("versionNO", qo.getVersionNO()));
				}else if(qo.getVersionType()==ScenicSpotPicQO.LESS_THAN){
					criteria.add(Restrictions.lt("versionNO", qo.getVersionNO()));
				}else{
					criteria.add(Restrictions.eq("versionNO", qo.getVersionNO()));
				}
			}
			if(StringUtils.isNotBlank(qo.getScenicSpotID())){
				criteria.add(Restrictions.eq("scenicSpotID", qo.getScenicSpotID()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<ScenicSpotPic> getEntityClass() {
		return ScenicSpotPic.class;
	}

}
