package zzpl.app.dao.app;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.app.APP;
import zzpl.pojo.qo.app.APPQO;
import hg.common.component.BaseDao;

@Repository
public class APPDAO extends BaseDao<APP, APPQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, APPQO qo) {
		return criteria;
	}

	@Override
	protected Class<APP> getEntityClass() {
		return APP.class;
	}

}
