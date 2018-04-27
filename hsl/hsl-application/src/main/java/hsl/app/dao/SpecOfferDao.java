package hsl.app.dao;

import hg.common.component.BaseDao;
import hsl.domain.model.mp.ad.SpecialOfferMp;
import hsl.pojo.qo.mp.HslSpecOfferMpQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SpecOfferDao extends BaseDao<SpecialOfferMp, HslSpecOfferMpQO>{


	@Override
	protected Criteria buildCriteria(Criteria criteria, HslSpecOfferMpQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getAdId())){
				criteria.add(Restrictions.eq("adId", qo.getAdId()));
			}
			/*if(qo.getScenicType()!=0){
				criteria.add(Restrictions.eq("scenicType",qo.getScenicType()));
			}*/
			if(StringUtils.isNotBlank(qo.getContentId())){
				criteria.add(Restrictions.eq("programaContent.id", qo.getContentId()));
			}
			
		}
		
		return criteria;
	}

	@Override
	protected Class<SpecialOfferMp> getEntityClass() {
		return SpecialOfferMp.class;
	}
	
}
