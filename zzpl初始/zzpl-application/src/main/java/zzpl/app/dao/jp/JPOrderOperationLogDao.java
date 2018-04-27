package zzpl.app.dao.jp;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.jp.JPOrderOperationLog;

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
