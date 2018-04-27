package zzpl.app.dao.jp;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.order.CityAirCode;
import zzpl.pojo.qo.sys.CityAirCodeQO;

@Repository
public class CityAirCodeDAO extends BaseDao<CityAirCode, CityAirCodeQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, CityAirCodeQO qo) {
		if (qo != null) {
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.eq("name", qo.getName()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<CityAirCode> getEntityClass() {
		return CityAirCode.class;
	}
}
