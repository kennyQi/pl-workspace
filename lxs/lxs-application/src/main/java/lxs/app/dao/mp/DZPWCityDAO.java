package lxs.app.dao.mp;

import hg.common.component.BaseDao;
import lxs.domain.model.mp.DzpwCity;
import lxs.pojo.qo.mp.DZPWCityQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DZPWCityDAO extends BaseDao<DzpwCity, DZPWCityQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, DZPWCityQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getCode())){
				criteria.add(Restrictions.eq("parentCode", qo.getCode()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<DzpwCity> getEntityClass() {
		return DzpwCity.class;
	}

}
