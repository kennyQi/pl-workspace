package hsl.app.dao;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import hsl.domain.model.jp.JPOrderOperationLog;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class JPOrderOperationLogDao extends BaseDao<JPOrderOperationLog, BaseQo> {

	@Override
	protected Class<JPOrderOperationLog> getEntityClass() {

		return JPOrderOperationLog.class;
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return null;
	}

}
