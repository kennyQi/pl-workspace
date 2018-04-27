package hg.system.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.system.model.backlog.BacklogLog;
import hg.system.qo.BacklogLogQo;

@Repository
public class BacklogLogDao extends BaseDao<BacklogLog, BacklogLogQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BacklogLogQo qo) {
		// TODO Auto-generated method stub
		return criteria;
	}

	@Override
	protected Class<BacklogLog> getEntityClass() {
		// TODO Auto-generated method stub
		return BacklogLog.class;
	}

}
