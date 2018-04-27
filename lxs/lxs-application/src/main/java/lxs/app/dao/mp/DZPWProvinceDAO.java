package lxs.app.dao.mp;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import lxs.domain.model.mp.DzpwProvince;
import lxs.pojo.qo.mp.DZPWProvinceQO;
import hg.common.component.BaseDao;

@Repository
public class DZPWProvinceDAO extends BaseDao<DzpwProvince, DZPWProvinceQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, DZPWProvinceQO qo) {
		return criteria;
	}

	@Override
	protected Class<DzpwProvince> getEntityClass() {
		return DzpwProvince.class;
	}

}
