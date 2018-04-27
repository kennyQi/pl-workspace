package hsl.app.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.alibaba.dubbo.common.utils.StringUtils;

import hg.common.component.BaseDao;
import hsl.domain.model.preferential.Preferential;
import hsl.pojo.qo.preferential.HslPreferentialQO;
@Repository
public class PreferentialDao extends BaseDao<Preferential, HslPreferentialQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria,
			HslPreferentialQO qo) {
		if(qo!=null){
			if(!(StringUtils.isBlank(qo.getAdId()))){
				criteria.add(Restrictions.eq("adId", qo.getAdId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Preferential> getEntityClass() {
		return Preferential.class;
	}


}
