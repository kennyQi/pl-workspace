package hg.system.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import hg.common.component.BaseDao;
import hg.system.model.meta.City;
import hg.system.qo.CityQo;

@Repository
public class CityDao extends BaseDao<City, CityQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, CityQo qo) {
		if (qo != null) {
			// 省份代码
			if (StringUtils.isNotBlank(qo.getProvinceCode())) {
				criteria.add(Restrictions.eq("parentCode", qo.getProvinceCode()));
			}
			// 城市名称
			if (StringUtils.isNotBlank(qo.getName())) {
				if (qo.getNameFuzzy()) {
					criteria.add(Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("name", qo.getName()));
				}
			}
		}
		return criteria;
	}

	@Override
	protected Class<City> getEntityClass() {
		return City.class;
	}

}
