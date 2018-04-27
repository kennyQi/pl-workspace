package hg.system.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.system.model.meta.Area;
import hg.system.qo.AreaQo;

@Repository
public class AreaDao extends BaseDao<Area, AreaQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, AreaQo qo) {
		if (qo != null) {
			// 城市代码
			if (StringUtils.isNotBlank(qo.getCityCode())) {
				criteria.add(Restrictions.eq("parentCode", qo.getCityCode()));
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
	protected Class<Area> getEntityClass() {
		return Area.class;
	}

}
