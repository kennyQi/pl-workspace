package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.OperationLogQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.OperationLog;

@Repository("operationLogDAO")
public class OperationLogDAO extends BaseHibernateDAO<OperationLog, OperationLogQO>{

	@Override
	protected Class<OperationLog> getEntityClass() {
		return OperationLog.class;
	}

	@Override
	protected void queryEntityComplete(OperationLogQO qo,
			List<OperationLog> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, OperationLogQO qo) {
		return criteria;
	}

}
