package zzpl.app.dao.log;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.log.SystemCommunicationLog;
import zzpl.pojo.qo.log.SystemCommunicationLogQO;
import hg.common.component.BaseDao;

@Repository
public class SystemCommunicationLogDAO extends BaseDao<SystemCommunicationLog, SystemCommunicationLogQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria,
			SystemCommunicationLogQO qo) {
		return criteria;
	}

	@Override
	protected Class<SystemCommunicationLog> getEntityClass() {
		return SystemCommunicationLog.class;
	}

}
