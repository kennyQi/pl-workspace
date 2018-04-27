package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.model.meta.Province;
import hg.system.qo.ProvinceQo;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ProvinceDao extends BaseDao<Province, ProvinceQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, ProvinceQo qo) {
		if (qo != null) {
			// 省份名称
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
	protected Class<Province> getEntityClass() {
		return Province.class;
	}

}
